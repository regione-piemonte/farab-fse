/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.csi.dma.farmab.domain.DmaccLXmlMessaggi;
import it.csi.dma.farmab.integration.dao.dto.LErroreDto;
import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.NamedParameterJdbcTemplateWithQueryDebug;

@Repository
public class FarmabTLogDao extends JdbcDaoSupport {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
	@Autowired
	private NamedParameterJdbcTemplateWithQueryDebug namedJdbcTemplate;

	private static final Map<String, String> immutableMap = new HashMap<String, String>() {{
		put("CERTIFICADEVICE", "SET_DEV_CERT");
		put("GETFARMACIAOCCASIONALE", "GET_FAR_OCC");
		put("AUTORIZZAFARMACIAOCCASIONALE", "AUT_FAR_OCC");
		put("CERTIFICADEVICECONOTP", "CER_DEV_OTP");
		put("ELENCORICETTEFARMACIA", "ELE_RIC_FARM");
		put("GETDELEGANTIFARMACIA", "GET_DEL_FARM");
	    put("GETDEVICECERTIFICATO", "GET_DEV_CERT");
	    put("GETELENCOFARMACIEABITUALI", "GET_FAR_ABI");
	    put("GETGENERAOTPDEVICE", "GET_GEN_OTP_DEV");
	    put("SETDISASSOCIADEVICE", "SET_DIS_DEV");
	    put("SETFARMACIAABITUALE", "SET_FAR_ABI");
	}};

	private static final String SQL_INSERT_L_XML_MESSAGGI =
			"insert into dmacc_l_xml_messaggi (id, wso2_id, xml_in, data_inserimento) "
					+"values(nextval('seq_dmacc_l_xml_messaggi'), :x_request_id, pgp_sym_encrypt( :xml_in, :encryption_key ), now())";

	private static final String SQL_UPDATE_T_LOG = "update dmacc_l_xml_messaggi SET  xml_out= pgp_sym_encrypt( :xml_out, :encryption_key ),"
			+" data_inserimento= now() WHERE id= :id;";

	private static final String INSERT_L_ERRORI = "INSERT INTO dmacc_l_errori"
			+ " (id, wso2_id, cod_errore, descr_errore, tipo_errore, data_ins, informazioni_aggiuntive) VALUES "
			+"( nextval('seq_dmacc_l_errori'), :wso2_id, :cod_errore, :descr_errore, :tipo_errore, now(), :informazioni_aggiuntive)";

	private static final String SQL_INSERT_L_MESSAGGI = "INSERT INTO dmacc_l_messaggi"
					 +" (id_xml, wso2_id, servizio_xml, uuid, chiamante, stato_xml, data_ricezione, data_ins, cf_assistito, cf_utente, ruolo_utente,  "
					 +"  data_invio_servizio,  ip_richiedente, codice_servizio, appl_verticale)"
					 + "VALUES(  nextval('seq_dmacc_l_messaggi'), :wso2_id, :servizio_xml, :uuid, :chiamante, 1, now(), now(), "
					 + ":cf_assistito, :cf_utente, :ruolo_utente, now(), :ip_richiedente, :codice_servizio,'FARAB')";

	private static final String SQL_UPDATE_L_MESSAGGI ="UPDATE dmacc_l_messaggi SET  stato_xml=4, data_risposta=now(), data_mod=now(), data_risposta_servizio=now(), cod_esito_risposta_servizio=:codEsito WHERE id_xml=:idXML;";

	//introdotto in data 26/01/2023 unicamente per il servizio FarmacieService.elencoRicetteFarmacia
	private static final String SQL_UPDATE_L_MESSAGGI_ELENCORICETTAFARMACIA_CODSESSIONE="UPDATE dmacc_l_messaggi SET UUID=:uuid WHERE id_xml=:id;";

	@Value("${encryptionkey}")
	private String encryptionkey;



	@Autowired
	FarmabTLogDao(DataSource dataSource){
		setDataSource(dataSource);
	}


	public void insertLErrori(List<LErroreDto> errori){
		log.debug("[FarmabTLogDao::insertLErrori] BEGIN");

		try {
			for(LErroreDto errore : errori){
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("wso2_id", errore.getWso2Id(), Types.VARCHAR);
				params.addValue("cod_errore", errore.getCodErrore(), Types.VARCHAR);
				params.addValue("descr_errore", errore.getDescrErrore(), Types.VARCHAR);
				params.addValue("tipo_errore", errore.getTipoErrore(), Types.VARCHAR);
				params.addValue("informazioni_aggiuntive", errore.getInformazioniAggiuntive(), Types.VARCHAR);

				namedJdbcTemplate.update(INSERT_L_ERRORI, params );
			}
		} catch (DataAccessException e) {
			logger.error("[FarmabTLogDao::insertLErrori] ERROR", e );
			throw e;
		}
	}


	public long insertLMessaggi(LMessaggiDto messaggio){
		log.debug("[FarmabTLogDao::insertLMessaggi] BEGIN");

		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("wso2_id", messaggio.getWso2Id(), Types.VARCHAR);
			params.addValue("servizio_xml", messaggio.getServizioXml(), Types.VARCHAR);
			params.addValue("uuid", messaggio.getUuid(),  Types.VARCHAR);
			params.addValue("chiamante", messaggio.getChiamante(),  Types.VARCHAR);
			params.addValue("cf_assistito", messaggio.getCfAssistito(), Types.VARCHAR);
			params.addValue("cf_utente" , messaggio.getCfUtente(), Types.VARCHAR);
			params.addValue("ruolo_utente", messaggio.getRuoloUtente(), Types.VARCHAR);
			//params.addValue("codice_servizio", messaggio.getCodiceServizio(), Types.VARCHAR);
			params.addValue("ip_richiedente", messaggio.getIpRichiedente(),  Types.VARCHAR);
			params.addValue("codice_servizio", getCodiceServizio(messaggio.getServizioXml()),  Types.VARCHAR);

			namedJdbcTemplate.update(SQL_INSERT_L_MESSAGGI, params, keyHolder, new String[]{"id_xml"} );
			return keyHolder.getKey().longValue();
		} catch (InvalidDataAccessApiUsageException e) {
			logger.error("[FarmabTLogDao::insertLMessaggi] ERROR: ", e);
			throw e;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error("[FarmabTLogDao::insertLMessaggi] ERROR: ", e);
			throw e;
		}
	}


	public int updateLMessaggi(LMessaggiDto messaggio){
		log.debug("[FarmabTLogDao::updateLMessaggi] per id_xml="+messaggio.getIdXml());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idXML", messaggio.getIdXml()).addValue("codEsito", messaggio.getCodEsitoRispostaEervizio());
		return namedJdbcTemplate.update(SQL_UPDATE_L_MESSAGGI, params);
	}

	public long insertLXmlMessaggi(DmaccLXmlMessaggi messaggio){

		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("x_request_id", messaggio.getxRequestId(), Types.VARCHAR);
		params.addValue("xml_in", messaggio.getXmlIn(), Types.VARCHAR);
		params.addValue("encryption_key", encryptionkey, Types.VARCHAR);

		namedJdbcTemplate.update(SQL_INSERT_L_XML_MESSAGGI, params, keyHolder, new String[]{"id"} );
		return keyHolder.getKey().longValue();

	}


	public int updatetLXmlMessaggi(DmaccLXmlMessaggi messaggio, Long id){

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("xml_out",  messaggio.getXmlOut() , Types.VARCHAR);
		params.addValue("encryption_key",  encryptionkey , Types.VARCHAR);

		params.addValue("id", id, Types.BIGINT);

		return namedJdbcTemplate.update(SQL_UPDATE_T_LOG, params);
	}

	public int updatetLMessaggiUUID(String uuid, Long id){

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("uuid",  uuid , Types.VARCHAR);
		params.addValue("id", id, Types.BIGINT);

		return namedJdbcTemplate.update(SQL_UPDATE_L_MESSAGGI_ELENCORICETTAFARMACIA_CODSESSIONE, params);
	}

	private String getCodiceServizio(String servizioXML) {
		String ret="ERR_COD_SERV";
		if (!StringUtils.isEmpty(servizioXML)) {
			ret= immutableMap.get(servizioXML.trim().toUpperCase());
		}
		return ret;
	}
}
