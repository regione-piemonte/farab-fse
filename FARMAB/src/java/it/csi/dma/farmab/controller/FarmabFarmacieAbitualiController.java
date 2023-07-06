/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.domain.DmaccTCatalogoFarmacie;
import it.csi.dma.farmab.domain.DmaccTFarmaciaAbitualeDescDomain;
import it.csi.dma.farmab.exception.FarmabRollbackException;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabFarmacieAbitualiController extends JdbcDaoSupport{

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final String QUERY_FARMACIE_ABITUALI_VALIDE_TODAY ="SELECT count(*) "
			+ "	FROM dmacc_t_farmacia_abituale f, dmacc_t_paziente t "
			+ "	where t.id_paziente=f.id_paziente and t.codice_fiscale=:cittadinoCf AND farmacia_cod=:farmaciaCod and f.data_cancellazione is null and f.associazione_inizio<=now() and (now() <= f.associazione_fine or f.associazione_fine is null);";

	private static final String QUERY_COUNT_FARMACIE_ABITUALI_IN_RANGE="SELECT count(*) FROM dmacc_t_farmacia_abituale f, dmacc_t_paziente t "
			+ "	where t.id_paziente=f.id_paziente and t.codice_fiscale=:cittadinoCf and f.data_cancellazione is null and farmacia_cod=:farmaciaCod "+
			"  and f.associazione_inizio <=TO_TIMESTAMP(:dataMaxToCheck,'YYYY-MM-DD HH24:MI:ss') and (f.associazione_fine is null or (f.associazione_fine>=TO_TIMESTAMP(:dataMinToCheck,'YYYY-MM-DD HH24:MI:ss')) );";

	private static final String QUERY_FARMACIE_ABITUALI_VALIDE ="SELECT farm_abit_id, t.id_paziente, farmacia_cod, to_char(associazione_inizio, 'YYYY-MM-DD HH24:MI:ss') as associazione_inizio, to_char(associazione_fine, 'YYYY-MM-DD HH24:MI:ss') as associazione_fine, to_char(f.data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(f.data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(f.data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione, utente_inserimento, utente_aggiornamento, utente_cancellazione \r\n"
			+ "	FROM dmacc_t_farmacia_abituale f, dmacc_t_paziente t "
			+ "	where t.id_paziente=f.id_paziente and t.codice_fiscale=:cittadinoCf and f.data_cancellazione is null and "
			+ "	(t.id_paziente,f.farmacia_cod,f.associazione_inizio) in"
			+ "	(SELECT  id_paziente, farmacia_cod, max(associazione_inizio) FROM dmacc_t_farmacia_abituale where id_paziente=t.id_paziente and data_cancellazione is null group by 1,2);";

	private static final String QUERY_FARMACIE_A_CATALOGO = "SELECT catalogo_farm_id, cod_azienda, denominazione, cod_farmacia, denom_farmacia, desc_natura, partita_iva, desc_tipo_far, cognome_dir, nome_dir, codice_fiscale_dir, indirizzo, numero_civico, cap, comune, denom_provincia, data_inizio_validita_farm, data_fine_validita_farm, data_inizio_farab, data_fine_farab\r\n"
			+ "	FROM dmacc_t_catalogo_farmacie WHERE cod_farmacia=:codFarmacia and data_inizio_validita_farm <= now() and (now() < data_fine_validita_farm or data_fine_validita_farm is null) and data_inizio_farab <= now() and (now() <data_fine_farab or data_fine_farab is null) limit 1;";

	private static final String NEXTVAL_T_FARMACIA_ABITUALE="select nextval('dmacc_t_farmacia_abituale_farm_abit_id_seq');";
	private static final String TEMPLATE_INSERT_NEW_FARMACIA_ABITUALE ="INSERT INTO dmacc_t_farmacia_abituale(farm_abit_id, id_paziente, farmacia_cod, associazione_inizio, associazione_fine, data_inserimento, data_aggiornamento, data_cancellazione, utente_inserimento, utente_aggiornamento, utente_cancellazione) "
			+ "	VALUES (:farmAbitid, (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cittadinoCf and data_annullamento is null and data_decesso is null order by 1 desc limit 1), "
			+ "	:codFarmacia, TO_TIMESTAMP(:dataInizioValidita,'YYYY-MM-DD HH24:MI:ss'), %1$s, now(), null, null, :cfRichiedente, null, null);";

	private static final String QUERY_FARMACIA_ABITUALE_BY_ID ="SELECT farm_abit_id, t.id_paziente, farmacia_cod, to_char(associazione_inizio, 'YYYY-MM-DD HH24:MI:ss') as associazione_inizio, to_char(associazione_fine, 'YYYY-MM-DD HH24:MI:ss') as associazione_fine, to_char(f.data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(f.data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, to_char(f.data_cancellazione, 'YYYY-MM-DD HH24:MI:ss') as data_cancellazione, utente_inserimento, utente_aggiornamento, utente_cancellazione "
			+ " FROM dmacc_t_farmacia_abituale f, dmacc_t_paziente t "
			+ " where t.id_paziente=f.id_paziente and t.codice_fiscale=:cittadinoCf and farm_abit_id=:farmAbitid and data_cancellazione is null";

	private static final String UPDATE_FARMACIA_ABITUALE_BY_ID ="UPDATE dmacc_t_farmacia_abituale "
			+ "	SET  associazione_fine=%1$s, data_aggiornamento=now(), utente_aggiornamento=:cfRichiedente "
			+ "	WHERE farm_abit_id=:farmAbitid ;";

	private static final String INSERT_STORICO_FARMACIA_ABITUALE="INSERT INTO dmacc_s_farmacia_abituale select nextval('dmacc_s_farmacia_abituale_s_farm_abit_id_seq'::regclass),farm_abit_id, id_paziente, farmacia_cod, associazione_inizio, associazione_fine, data_inserimento, data_aggiornamento, data_cancellazione, utente_inserimento, utente_aggiornamento, utente_cancellazione, now() from dmacc_t_farmacia_abituale where farm_abit_id=:farmAbitid";

	/* da eliminare
	private static final String REMOVE_FARMACIA_ABITUALE_BY_ID_OLD ="UPDATE dmacc_t_farmacia_abituale "
			+ "	SET   data_cancellazione=now(), utente_cancellazione=:cfRichiedente "
			+ "	WHERE farm_abit_id=:farmAbitid ;";*/

	private static final String REMOVE_FARMACIA_ABITUALE_BY_COD ="UPDATE dmacc_t_farmacia_abituale "
			+ "	SET   data_cancellazione=now(), utente_cancellazione=:cfRichiedente "
			+ "	WHERE farmacia_cod=:farmaciaCod and id_paziente=(SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cittadinoCf and data_annullamento is null and data_decesso is null order by 1 desc limit 1) and data_cancellazione is null;";

	private static final String VERIFICA_R_AUTORIZZAZIONE_DELEGHE="SELECT id FROM dmacc_r_autorizzazione_deleghe "
			+ "	where id_ruolo=(SELECT id FROM dmacc_d_ruolo where codice_ruolo=:ruolo) "
			+ "	and autorizzazione='S' and grado_delega=:gradoDelega and codice_servizio=:codiceServizio;";

	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;

	public List<DmaccTFarmaciaAbitualeDescDomain> getFarmacieAbituali(String cittadinoCf){
		log.info("FarmabFarmacieAbitualiController::getFarmacieAbituali cf="+cittadinoCf);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf);
		log.debug(QUERY_FARMACIE_ABITUALI_VALIDE);
		log.debug(namedParameters.toString());
		List<DmaccTFarmaciaAbitualeDescDomain> farmAbituali=jdbcTemplate.query(QUERY_FARMACIE_ABITUALI_VALIDE, namedParameters,(rs, rowNum) ->
			new DmaccTFarmaciaAbitualeDescDomain(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
		return farmAbituali;
	}

	/**
	 * da eliminare la tabella non esiste piu'
	 * @param codFarmacia
	 * @return
	 */
	public DmaccTCatalogoFarmacie getFirstFarmaciaFromCatalogoFarmacie_deprecato(String codFarmacia) {
		log.info("FarmabFarmacieAbitualiController::getFirstFarmaciaFromCatalogoFarmacie_deprecato da non richiamare codFarmacia="+codFarmacia);
		log.debug(QUERY_FARMACIE_A_CATALOGO);
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("codFarmacia", codFarmacia);
			return jdbcTemplate.queryForObject(QUERY_FARMACIE_A_CATALOGO, namedParameters, (rs, rowNum) -> new DmaccTCatalogoFarmacie(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20)));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per codFarmacia="+codFarmacia);
		}
		return null;
	}

	@Transactional
	public DmaccTFarmaciaAbitualeDescDomain setNewFarmaciaAbituale(String cittadinoCf, String cfRichiedente, String codFarmacia, String dataInizioValidita, String dataFineValidita){// DmaccTCatalogoFarmacie farmCat) {
		log.info("FarmabFarmacieAbitualiController::setNewFarmaciaAbituale");
		DmaccTFarmaciaAbitualeDescDomain response=null;

		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource();
			long farmAbitid = jdbcTemplate.queryForObject(NEXTVAL_T_FARMACIA_ABITUALE,namedParameters, (rs, rowNum) -> rs.getLong(1));
			//parametri per la insert
			namedParameters = new MapSqlParameterSource().addValue("farmAbitid", farmAbitid).addValue("cittadinoCf", cittadinoCf).addValue("codFarmacia", codFarmacia).addValue("dataInizioValidita", dataInizioValidita).addValue("dataFineValidita", dataFineValidita).addValue("cfRichiedente", cfRichiedente);
			//gestione dataFineValidita
			String newSql=null;
			if (FarmabUtils.isValidDate(dataFineValidita)) {
				newSql=String.format(TEMPLATE_INSERT_NEW_FARMACIA_ABITUALE," TO_TIMESTAMP(:dataFineValidita,'YYYY-MM-DD HH24:MI:SS') ");
			} else {
				newSql=String.format(TEMPLATE_INSERT_NEW_FARMACIA_ABITUALE," (TO_TIMESTAMP(:dataInizioValidita,'YYYY-MM-DD HH24:MI:SS') +'1 year'::INTERVAL) ");
			}
			log.debug(newSql);
			log.debug("Inseriti"+jdbcTemplate.update(newSql, namedParameters));
			namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmAbitid", farmAbitid);

			response=jdbcTemplate.queryForObject(QUERY_FARMACIA_ABITUALE_BY_ID, namedParameters,(rs, rowNum) ->
			 new DmaccTFarmaciaAbitualeDescDomain(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return response;
	}

	public DmaccTFarmaciaAbitualeDescDomain findFarmaciaAbitualeById(String farmAbitid, String cittadinoCf) {
		log.info("FarmabFarmacieAbitualiController::findFarmaciaAbitualeById");
		List<DmaccTFarmaciaAbitualeDescDomain> response=null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmAbitid", Long.parseLong(farmAbitid));
		log.debug(QUERY_FARMACIA_ABITUALE_BY_ID);
		try {
			response=jdbcTemplate.query(QUERY_FARMACIA_ABITUALE_BY_ID, namedParameters,(rs, rowNum) ->
			  new DmaccTFarmaciaAbitualeDescDomain(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));

		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return response==null||response.isEmpty()?null:response.get(0);
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public DmaccTFarmaciaAbitualeDescDomain updateFarmaciaAbitualeById(String farmAbitid, String cittadinoCf, String cfRichiedente, String dataFineValidita) {
		log.info("FarmabFarmacieAbitualiController::updateFarmaciaAbitualeById");
		DmaccTFarmaciaAbitualeDescDomain response=null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmAbitid", Long.parseLong(farmAbitid)).addValue("cfRichiedente", cfRichiedente).addValue("dataFineValidita", dataFineValidita);
		String newSql=null;
		try {
			if (FarmabUtils.isValidDate(dataFineValidita)) {
				newSql=String.format(UPDATE_FARMACIA_ABITUALE_BY_ID," TO_TIMESTAMP(:dataFineValidita,'YYYY-MM-DD HH24:MI:SS') ");
			} else {
				newSql=String.format(UPDATE_FARMACIA_ABITUALE_BY_ID," (associazione_inizio +'1 year'::INTERVAL) ");
			}
			log.debug("Salvo nella tabella dmacc_s_farmacia_abituale il record storico"+jdbcTemplate.update(INSERT_STORICO_FARMACIA_ABITUALE, namedParameters));
			log.debug(newSql);
			log.debug("updateFarmaciaAbitualeById="+jdbcTemplate.update(newSql, namedParameters));
			response=jdbcTemplate.queryForObject(QUERY_FARMACIA_ABITUALE_BY_ID, namedParameters,(rs, rowNum) ->
			  new DmaccTFarmaciaAbitualeDescDomain(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
		}catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return response;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean removeFarmaciaAbitualeByCod(String farmaciaCod, String cittadinoCf, String cfRichiedente) {
		log.info("FarmabFarmacieAbitualiController::removeFarmaciaAbitualeByCod");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmaciaCod", farmaciaCod).addValue("cfRichiedente", cfRichiedente);//.addValue("farmAbitid", Long.parseLong(farmAbitid))
		log.debug(REMOVE_FARMACIA_ABITUALE_BY_COD);
		if (jdbcTemplate.update(REMOVE_FARMACIA_ABITUALE_BY_COD, namedParameters)>0) {
			return true;
		}
		return false;
	}

	public List<Long> isValidRangeForDate(String farmaciaCod, String cittadinoCf, String dataMinToCheck, String dataMaxToCheck) {
		log.info("FarmabFarmacieAbitualiController::isValidRangeForDate");
		List<Long> countRecord=null;
		String defaulMaxDateifNull=dataMaxToCheck==null||dataMaxToCheck.trim().isEmpty()?"2099-12-31 10:00:00":dataMaxToCheck;
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmaciaCod", farmaciaCod).addValue("dataMinToCheck", dataMinToCheck).addValue("dataMaxToCheck", defaulMaxDateifNull);
			log.debug(QUERY_COUNT_FARMACIE_ABITUALI_IN_RANGE);
			log.debug("Params:farmaciaCod="+farmaciaCod +" cittadinoCf"+cittadinoCf+" dataMinToCheck"+dataMinToCheck+" dataMaxToCheck"+defaulMaxDateifNull);
			countRecord= jdbcTemplate.query(QUERY_COUNT_FARMACIE_ABITUALI_IN_RANGE, namedParameters,(rs, rowNum) ->
			rs.getLong(1));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return countRecord;
	}

	public List<Long> isValidRangeForToday(String cittadinoCf, String farmaciaCod) {
		log.info("8.FarmabFarmacieAbitualiController::isValidRangeForToday");
		List<Long> countRecord=null;

		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("farmaciaCod", farmaciaCod);
			log.info(QUERY_FARMACIE_ABITUALI_VALIDE_TODAY);
			log.info("Params:farmaciaCod="+farmaciaCod +" cittadinoCf"+cittadinoCf);
			countRecord= jdbcTemplate.query(QUERY_FARMACIE_ABITUALI_VALIDE_TODAY, namedParameters,(rs, rowNum) ->
			rs.getLong(1));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return countRecord;
	}

	public List<Long> getIdVerificaAutorizDeleghe(String ruolo, String gradoDelega, String codiceServizio){
		log.info("FarmabFarmacieAbitualiController::getIdVerificaAutorizDeleghe ruolo:"+ruolo+" gradoDelega:"+gradoDelega+" codiceServizio:"+codiceServizio);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("ruolo", ruolo).addValue("gradoDelega", gradoDelega).addValue("codiceServizio", codiceServizio);
		log.debug(VERIFICA_R_AUTORIZZAZIONE_DELEGHE);
		log.debug(namedParameters.toString());
		return jdbcTemplate.query(VERIFICA_R_AUTORIZZAZIONE_DELEGHE, namedParameters,(rs, rowNum) ->
			rs.getLong(1));
	}

}
