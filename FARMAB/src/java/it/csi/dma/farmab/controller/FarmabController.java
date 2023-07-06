/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;

import it.csi.dma.farmab.domain.DmaccTDispositivoCertificatoStato;
import it.csi.dma.farmab.util.Constants;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabController extends JdbcDaoSupport{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	/*private static final String QUERY_DISPOSITIVI_CERTIFICATI_OLD =
			"SELECT t.device_id, t.device_uuid, d.device_stato_cod, d.device_stato_desc, t.device_numero_telefono, t.device_so, t.device_browser, t.device_modello, t.id_paziente, t.device_stato_id, t.fonte_cert_id, cittadino_cf, to_char(data_certificazione, 'YYYY-MM-DD HH24:MI:ss') as data_certificazione, to_char(t.data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(t.data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as  data_aggiornamento, utente_inserimento, utente_aggiornamento, fonte_cert_cod, fonte_cert_desc\r\n"
			+ "	FROM dmacc_t_dispositivo_certificato t, dmacc_d_dispositivo_certificato_stato d, dmacc_d_fonte_certificazione f where t.device_stato_id=d.device_stato_id and cittadino_cf=:cf and f.fonte_cert_id=t.fonte_cert_id "+
					" and (t.device_id,data_certificazione) in (select device_id, max(data_certificazione) from dmacc_t_dispositivo_certificato where device_stato_cod='CERT' and cittadino_cf=:cf  group by 1);";
	*/
	private static final String QUERY_DISPOSITIVI_CERTIFICATI =
			"SELECT t.device_id, t.device_uuid, d.device_stato_cod, d.device_stato_desc, pgp_sym_decrypt(t.device_numero_telefono, (:encryptionkey)) as device_numero_telefono, t.device_so, t.device_browser, t.device_modello, t.id_paziente, t.device_stato_id, t.fonte_cert_id, cittadino_cf, to_char(data_certificazione, 'YYYY-MM-DD HH24:MI:ss') as data_certificazione, to_char(t.data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(t.data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as  data_aggiornamento, utente_inserimento, utente_aggiornamento, fonte_cert_cod, fonte_cert_desc\r\n"
			+ "	FROM dmacc_t_dispositivo_certificato t, dmacc_d_dispositivo_certificato_stato d, dmacc_d_fonte_certificazione f where t.device_stato_id=d.device_stato_id and cittadino_cf=:cf and f.fonte_cert_id=t.fonte_cert_id "+
					" and device_stato_cod='CERT' and cittadino_cf=:cf order by data_certificazione DESC LIMIT 1;";


	private static final String QUERY_DESCRIZIONE_RUOLO="SELECT descrizione_ruolo FROM dmacc_d_ruolo where codice_ruolo=:codiceRuolo limit 1";

	private static final String QUERY_FONTE_CERTIFICAZIONE_PRESENTE="SELECT fonte_cert_cod FROM dmacc_d_fonte_certificazione WHERE fonte_cert_cod =:fonteCertCod and now() BETWEEN validita_inizio and COALESCE(validita_fine, now())";

	private static final String QUERY_ID_PAZIENTE_VALIDO="SELECT count(id_paziente) FROM dmacc_t_paziente where codice_fiscale=:cf and data_annullamento is null and data_decesso is null order by 1 desc limit 1;";
	
	private static final String QUERY_ID_IREC_PAZIENTE_VALIDO="SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cf and data_annullamento is null and data_decesso is null order by 1 desc limit 1;";
	
	private static final String QUERY_ID_IREC_UTENTE =
			"SELECT id "
		+   " FROM dmacc_t_utente where codice_fiscale = :codice_fiscale and now() between data_attivazione and coalesce(data_chiusura, to_timestamp('01/01/2999', 'dd/mm/yyyy'))";
	
	

	@Value("${encryptionkey}")
	private String encryptionkey;

	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;
	
	
	public Long getIdIrecPaziente(String cf){
		log.info("getIdIrecPaziente cf="+cf);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf);
		Long idIrec = null;
		try{
			idIrec = jdbcTemplate.queryForObject(QUERY_ID_IREC_PAZIENTE_VALIDO, namedParameters, Long.class);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return idIrec;
	}
	
	public Long getIdIrecUtente(String cf){
		log.info("getIdIrecUtente cf="+cf);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("codice_fiscale", cf);
		Long idIrec = null;
		try{
			idIrec = jdbcTemplate.queryForObject(QUERY_ID_IREC_UTENTE, namedParameters, Long.class);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return idIrec;		
	}
	
	/*
	 *

	String utenteCancellazione;
	 */

	public List<DmaccTDispositivoCertificatoStato> getDispositivoCertificato(String cf) {
		//List<String> s=jdbcTemplateCC.query(QUERY_DISPOSITIVI_CERTIFICATI,String.class);
		log.info("getDispositivoCertificato cf="+cf);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf).addValue("encryptionkey", encryptionkey);
		List<DmaccTDispositivoCertificatoStato> dispositivi=null;
		try {
		dispositivi=jdbcTemplate.query(QUERY_DISPOSITIVI_CERTIFICATI, namedParameters,(rs, rowNum) ->
		new DmaccTDispositivoCertificatoStato(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
				rs.getString(7),rs.getString(8),rs.getLong(9),rs.getLong(10),rs.getLong(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
				rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19)));
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		log.debug(dispositivi);
		return dispositivi;
	}

	public List<String> getDescrizioneRuoloByCod (String codiceRuolo){
		log.info("FarmabController::getDescrizioneRuoloByCod codiceRuolo="+codiceRuolo);
		List<String> lDescr=null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("codiceRuolo", codiceRuolo);
		try {
			lDescr=jdbcTemplate.query(QUERY_DESCRIZIONE_RUOLO, namedParameters,(rs, rowNum) ->
			rs.getString(1));
		}catch(Exception e) {
				log.error(e.getMessage());
				throw e;
		}
		log.debug(lDescr);
		return lDescr;
	}

	public List<String> getFonteCertificazionePresente (String fonteCertCod){
		log.info("FarmabController::getFonteCertificazionePresente fonteCertCod="+fonteCertCod);
		List<String> lDescr=null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("fonteCertCod", fonteCertCod);
		try {
			lDescr=jdbcTemplate.query(QUERY_FONTE_CERTIFICAZIONE_PRESENTE, namedParameters,(rs, rowNum) ->
			rs.getString(1));
		}catch(Exception e) {
				log.error(e.getMessage());
				throw e;
		}
		log.debug(lDescr);
		return lDescr;
	}

	public int esistePazienteValido(String cf) {
		log.info("FarmabController::EsistePazienteValido cf="+cf);
		List<Integer> lCountPaz=null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf);
		try {
			lCountPaz=jdbcTemplate.query(QUERY_ID_PAZIENTE_VALIDO, namedParameters,(rs, rowNum) ->
			rs.getInt(1));
		}catch(Exception e) {
				log.error(e.getMessage());
				throw e;
		}
		log.debug(lCountPaz.get(0).intValue());
		return lCountPaz.get(0).intValue();
	}
}
