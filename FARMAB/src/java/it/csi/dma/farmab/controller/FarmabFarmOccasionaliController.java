/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;

import it.csi.dma.farmab.domain.DmaccTDispositivoCertificazioneOtpRichDomain;
import it.csi.dma.farmab.domain.DmaccTFarmaciaOccasionaleRich;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDeviceConOtp;
import it.csi.dma.farmab.util.Constants;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabFarmOccasionaliController extends JdbcDaoSupport{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final String QUERY_FIND_NUM_MAX_TENTATIVI_INI_PROM  ="SELECT valore::int FROM dmacc_t_configurazione where chiave ='"+Constants.NUM_MAX_TENTATIVI_INI_PROM +"'";
	private static final String QUERY_FIND_FAROCC_CODSES_TIMETOLIVE  ="SELECT valore::int FROM dmacc_t_configurazione where chiave ='"+Constants.FAROCC_CODSES_TIMETOLIVE +"'";
	private static final String QUERY_FIND_FARM_OCCASIO_RICH_OLD="SELECT farm_occ_rich_id, id_paziente, farmacia_cod, farmacia_piva, farmacia_gestionale, farmacista_cf, cittadino_delegato_cf, sessione_cod, farm_occ_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione "
			+ "	FROM dmacc_t_farmacia_occasionale_rich "
			+ "	WHERE id_paziente in (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cf and data_annullamento is null and data_decesso is null order by 1 desc limit 1) "
			+ "	and now() <=(data_inserimento + interval '%1$s minute') "
			+ "	and farm_occ_rich_stato_id in (SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod='DA_AUT' and now() BETWEEN validita_inizio and coalesce(validita_fine,now())) "
			+ "UNION "
			+ "SELECT farm_occ_rich_id, id_paziente, farmacia_cod, farmacia_piva, farmacia_gestionale, farmacista_cf, cittadino_delegato_cf, sessione_cod, farm_occ_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione "
			+ "	FROM dmacc_t_farmacia_occasionale_rich WHERE cittadino_delegato_cf=:cf "
			+ "	 and now() <=(data_inserimento + interval '%2$s minute') "
			+ "	 and farm_occ_rich_stato_id in (SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod='DA_AUT' and now() BETWEEN validita_inizio and coalesce(validita_fine,now()))"
			+ " ORDER BY data_inserimento DESC;";

	private static final String QUERY_FIND_FARM_OCCASIO_RICH_PER_CF="SELECT farm_occ_rich_id, id_paziente, farmacia_cod, farmacia_piva, farmacia_gestionale, farmacista_cf, cittadino_delegato_cf, sessione_cod, farm_occ_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione "
			+ "	FROM dmacc_t_farmacia_occasionale_rich "
			+ "	WHERE (id_paziente in (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cf and data_annullamento is null and data_decesso is null order by 1 desc limit 1) or cittadino_delegato_cf=:cf )"
			+ "	and now()<=(data_inserimento + interval '%1$s minute') "
			+ "	and farm_occ_rich_stato_id in (SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod='DA_AUT' and now() BETWEEN validita_inizio and coalesce(validita_fine,now()));";

	private static final String QUERY_FIND_FARM_OCCASIO_RICH_PER_ID="SELECT farm_occ_rich_id, id_paziente, farmacia_cod, farmacia_piva, farmacia_gestionale, farmacista_cf, cittadino_delegato_cf, sessione_cod, farm_occ_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione "
			+ "	FROM dmacc_t_farmacia_occasionale_rich "
			+ "	WHERE id_paziente=:idPaziente "
			+ "	and now()<=(data_inserimento + interval '%1$s minute') "
			+ "	and farm_occ_rich_stato_id in (SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod='DA_AUT' and now() BETWEEN validita_inizio and coalesce(validita_fine,now()));";

	private static final String UPDATE_FARM_OCCASIO_RICH="UPDATE dmacc_t_farmacia_occasionale_rich SET farm_occ_rich_stato_id=(SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod='AUT' and now() BETWEEN validita_inizio and coalesce(validita_fine,now()) LIMIT 1), data_aggiornamento=now() WHERE farm_occ_rich_id in (:IDS);";

	private static final String UPDATE_STATO_FARM_OCCASIO_RICH="UPDATE dmacc_t_farmacia_occasionale_rich SET farm_occ_rich_stato_id=(SELECT farm_occ_rich_stato_id FROM dmacc_d_farmacia_occasionale_rich_stato WHERE farm_occ_rich_stato_cod=:stato), data_aggiornamento=now() WHERE farm_occ_rich_id=:idRich;";

	private static final String INSERT_FARM_OCCASIO_RICH="INSERT INTO dmacc_t_farmacia_occasionale_rich "
			+ "	(id_paziente, farmacia_cod, farmacia_piva, farmacia_gestionale, farmacista_cf, cittadino_delegato_cf, sessione_cod, farm_occ_rich_stato_id, data_inserimento, data_aggiornamento, data_cancellazione) "
			+ "	VALUES(:id_paziente, :farmacia_cod, :farmacia_piva, :farmacia_gestionale, :farmacista_cf, :cittadino_delegato_cf, :sessione_cod, :farm_occ_rich_stato_id, now(), null, null);";

	private static final String QUERY_FARM_OCCASIO_RICH_COD_SESSIONE="SELECT faoc.farm_occ_rich_id "
			+ " FROM dmacc_t_farmacia_occasionale_rich faoc, dmacc_t_paziente dtp "
			+ " WHERE faoc.sessione_cod=:sessioneCod "
			+ " AND faoc.id_paziente = dtp.id_paziente "
			+ " AND dtp.codice_fiscale=:cfAssistito";

	private static final String QUERY_FARM_OCCASIO_RICH_VALIDITA_SESSIONE="SELECT faoc.farm_occ_rich_stato_id "
			+ " FROM dmacc_t_farmacia_occasionale_rich faoc, dmacc_t_paziente dtp "
			+ " WHERE faoc.sessione_cod=:sessioneCod "
			+ " AND faoc.id_paziente = dtp.id_paziente "
			+ " AND dtp.codice_fiscale=:cfAssistito"
			+ " and now() >= faoc.data_inserimento  and now() <=faoc.data_inserimento + interval '%1$s minute'";

	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;

	public List<DmaccTFarmaciaOccasionaleRich> findFarmacieOccasionali(String cf, Long idPaziente){
		log.info("FarmabFarmOccasionaliController::findFarmacieOccasionali");

		List<DmaccTFarmaciaOccasionaleRich> farmacieOcc=null;
		if(cf!=null) {
			farmacieOcc=findFarmacieOccasionaliPerCF(cf);
		} else if(idPaziente!=null) {
			farmacieOcc=findFarmacieOccasionaliPerID(idPaziente);
		}
		return farmacieOcc;

	}

	public List<DmaccTFarmaciaOccasionaleRich> findFarmacieOccasionaliPerCF(String cf) {
		log.info("FarmabFarmOccasionaliController::findFarmacieOccasionaliPerCF");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf);
		String newSql=String.format(QUERY_FIND_FARM_OCCASIO_RICH_PER_CF, getIntervalSessionFarmaciaMin());
		log.debug(newSql);
		List<DmaccTFarmaciaOccasionaleRich> farmacieOcc=null;
		try {
			farmacieOcc=jdbcTemplate.query(newSql, namedParameters,(rs, rowNum) -> new DmaccTFarmaciaOccasionaleRich(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4)
					,rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
					,rs.getInt(9),rs.getString(10),rs.getString(11),rs.getString(12)));
		}catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		return farmacieOcc;
	}

	public List<DmaccTFarmaciaOccasionaleRich> findFarmacieOccasionaliPerID(Long idPaziente) {
		log.info("FarmabFarmOccasionaliController::findFarmacieOccasionaliPerID");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("idPaziente", idPaziente);
		String newSql=String.format(QUERY_FIND_FARM_OCCASIO_RICH_PER_ID, getIntervalSessionFarmaciaMin());
		log.debug(newSql);
		List<DmaccTFarmaciaOccasionaleRich> farmacieOcc=null;
		try {
			farmacieOcc=jdbcTemplate.query(newSql, namedParameters,(rs, rowNum) -> new DmaccTFarmaciaOccasionaleRich(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4)
					,rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
					,rs.getInt(9),rs.getString(10),rs.getString(11),rs.getString(12)));
		}catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		return farmacieOcc;
	}

	public int updateFarmacieOccasionali(List<DmaccTFarmaciaOccasionaleRich> listaFarmacieOcc) {
		log.info("FarmabFarmOccasionaliController::updateFarmacieOccasionali");
		int record=0;
		List<Long> ids=new ArrayList<Long>();
		if 	(listaFarmacieOcc !=null && !listaFarmacieOcc.isEmpty()) {
			for (DmaccTFarmaciaOccasionaleRich f: listaFarmacieOcc) {
				ids.add(f.getFarmOccRichId());
			}
		}
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("IDS", ids);
		try {
			record=jdbcTemplate.update(UPDATE_FARM_OCCASIO_RICH, parameters);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return record;
	}

	public int updateStatoFarmacieOccasionali(List<Long> idRichiesteFarmOcc, String stato) {
		log.info("13B.FarmabFarmOccasionaliController::updateFarmacieOccasionaliScadute");
		int record=0;
		Long idRich=null;
		if 	(idRichiesteFarmOcc !=null && !idRichiesteFarmOcc.isEmpty()) {
			for (Long idRichiestaFarmOcc : idRichiesteFarmOcc) {
				idRich = idRichiestaFarmOcc;
			}
		}
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idRich", idRich).addValue("stato", stato);
		try {
			record=jdbcTemplate.update(UPDATE_STATO_FARM_OCCASIO_RICH, parameters);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return record;
	}

	public String getNumeroMassimoTentaivi() {
		log.info("FarmabFarmOccasionaliController::getNumeroMassimoTentaivi");
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		String numeroMaxTentativi="";
		try {
			numeroMaxTentativi=jdbcTemplate.queryForObject(QUERY_FIND_NUM_MAX_TENTATIVI_INI_PROM, namedParameters, (rs, rowNum) ->new String(rs.getString(1)));
		} catch (Exception ex) {
			log.error("Errore DB Incongruente"+ex);
			throw ex;
		}
		log.debug("numeroMaxTentativi="+numeroMaxTentativi);
		return numeroMaxTentativi;
	}

	public String getIntervalSessionFarmaciaMin() {
		log.info("FarmabFarmOccasionaliController::getIntervalSessionFarmaciaMin");
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		String sessioneMax="";
		try {
			sessioneMax=jdbcTemplate.queryForObject(QUERY_FIND_FAROCC_CODSES_TIMETOLIVE, namedParameters, (rs, rowNum) ->new String(rs.getString(1)));
		} catch (Exception ex) {
			log.error("Errore DB Incongruente"+ex);
			throw ex;
		}
		log.debug("sessioneMax="+sessioneMax);
		return sessioneMax;
	}

	public int inserisciFarmacieOccasionali(DmaccTFarmaciaOccasionaleRich farmaciaOccRequest) {
		log.info("11.FarmabFarmOccasionaliController::inserisciFarmacieOccasionali cf FARMACISTA="+farmaciaOccRequest.getFarmacistaCf());

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("id_paziente", farmaciaOccRequest.getIdPaziente()).addValue("farmacia_cod", farmaciaOccRequest.getFarmaciaCod())
				.addValue("farmacia_piva", farmaciaOccRequest.getFarmaciaPiva()).addValue("farmacia_gestionale", farmaciaOccRequest.getFarmaciaGestionale())
				.addValue("farmacista_cf", farmaciaOccRequest.getFarmacistaCf()).addValue("cittadino_delegato_cf", farmaciaOccRequest.getCittadinoDelegatoCf()).addValue("sessione_cod", farmaciaOccRequest.getSessioneCod())
				.addValue("farm_occ_rich_stato_id", farmaciaOccRequest.getFarmOccRichStatoId()).addValue("data_inserimento", farmaciaOccRequest.getDataInserimento())
				.addValue("data_aggiornamento", farmaciaOccRequest.getDataAggiornamento()).addValue("data_cancellazione", farmaciaOccRequest.getDataCancellazione());

		int n=jdbcTemplate.update(INSERT_FARM_OCCASIO_RICH, namedParameters);
		log.debug("Inserito "+n +" Device");
		return n;
	}

	public List<Long> cercaCodSessione(String sessioneCod, String cfAssistito){
		log.info("FarmabFarmOccasionaliController::cercaCodSessione ");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("sessioneCod", sessioneCod).addValue("cfAssistito", cfAssistito);
		return jdbcTemplate.query(QUERY_FARM_OCCASIO_RICH_COD_SESSIONE, namedParameters,(rs, rowNum) ->
		rs.getLong(1));
	}

	public List<Long> cercaValiditaSessione(String sessioneCod, String cfAssistito){
		log.info("13A.FarmabFarmOccasionaliController::cercaValiditaSessione ");

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("sessioneCod", sessioneCod).addValue("cfAssistito", cfAssistito);

		String newSql=String.format(QUERY_FARM_OCCASIO_RICH_VALIDITA_SESSIONE, getIntervalSessionFarmaciaMin());
		log.debug(newSql);

		try {
			return jdbcTemplate.query(newSql, namedParameters,(rs, rowNum) ->
			rs.getLong(1));
		}catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

}
