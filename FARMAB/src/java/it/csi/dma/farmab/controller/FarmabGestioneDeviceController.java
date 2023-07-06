/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.domain.DmaccTDispositivoCertificatoStato;
import it.csi.dma.farmab.domain.DmaccTDispositivoCertificazioneOtpRichDomain;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDevice;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDeviceConOtp;
import it.csi.dma.farmab.util.Constants;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabGestioneDeviceController extends JdbcDaoSupport{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final String QUERY_CONTROLLA_DISPOSITIVO_CERTIFICATO_PER_CF ="SELECT count(*) "
			+ "	FROM dmacc_t_dispositivo_certificato d, dmacc_d_dispositivo_certificato_stato s "
			+ "	where cittadino_cf=:cittadinoCf and d.device_stato_id=s.device_stato_id and s.device_stato_cod='CERT';";

	private static final String QUERY_FIND_ALL_DISP_OLD_25012022 = "SELECT t.device_id, t.device_uuid, d.device_stato_cod, d.device_stato_desc, pgp_sym_decrypt(t.device_numero_telefono, (:encryptionkey)) as device_numero_telefono, t.device_so, t.device_browser, t.device_modello, t.id_paziente, t.device_stato_id, t.fonte_cert_id, cittadino_cf, to_char(data_certificazione, 'YYYY-MM-DD') as data_certificazione, to_char(t.data_inserimento, 'YYYY-MM-DD') as data_inserimento, to_char(t.data_aggiornamento, 'YYYY-MM-DD') as  data_aggiornamento, utente_inserimento, utente_aggiornamento, fonte_cert_cod, fonte_cert_desc "
			+ "	FROM dmacc_t_dispositivo_certificato t, dmacc_d_dispositivo_certificato_stato d, dmacc_d_fonte_certificazione f where t.device_stato_id=d.device_stato_id and cittadino_cf=:cf and f.fonte_cert_id=t.fonte_cert_id "+
					" and t.device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:scod);";

	//modificato analisi il 25/01/2022
	private static final String QUERY_FIND_ALL_DISP = "SELECT t.device_id, t.device_uuid, d.device_stato_cod, d.device_stato_desc, pgp_sym_decrypt(t.device_numero_telefono, (:encryptionkey)) as device_numero_telefono, t.device_so, t.device_browser, t.device_modello, t.id_paziente, t.device_stato_id, t.fonte_cert_id, cittadino_cf, to_char(data_certificazione, 'YYYY-MM-DD') as data_certificazione, to_char(t.data_inserimento, 'YYYY-MM-DD') as data_inserimento, to_char(t.data_aggiornamento, 'YYYY-MM-DD') as  data_aggiornamento, utente_inserimento, utente_aggiornamento, fonte_cert_cod, fonte_cert_desc "
			+ "	FROM dmacc_t_dispositivo_certificato t, dmacc_d_dispositivo_certificato_stato d, dmacc_d_fonte_certificazione f where t.device_stato_id=d.device_stato_id and cittadino_cf=:cf and f.fonte_cert_id=t.fonte_cert_id "+
					" and t.device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:scod);";


	private static final String QUERY_FIND_DESCR_FONTE = "SELECT fonte_cert_desc "
			+ "	FROM dmacc_d_fonte_certificazione where fonte_cert_cod=:fonteCertCod;";

	//Inserito a fronte della modifica dell'analisi in certificaDevice step 3
	private static final String QUERY_ESISTE_DISPOSITIVO_CON_STATO ="SELECT count(*) FROM dmacc_t_dispositivo_certificato WHERE cittadino_cf=:cfCittadino AND device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:statoCert AND data_cancellazione is null AND now() BETWEEN validita_inizio and COALESCE(validita_fine, now()));";

	//Aggiunto controllo per jira DMA-3719
	private static final String QUERY_CONTROLLA_DISPOSITIVO_CERTIFICATO_PER_UUID = "SELECT device_id FROM dmacc_t_dispositivo_certificato WHERE cittadino_cf=:cfCittadino AND device_uuid=:deviceUuid AND device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:statoCert AND data_cancellazione is null AND now() BETWEEN validita_inizio and COALESCE(validita_fine, now()))";

	private static final String QUERY_CONTROLLACF_DISPOSITIVO_CERTIFICATO_PER_UUID = "SELECT cittadino_cf FROM dmacc_t_dispositivo_certificato WHERE device_uuid=:deviceUuid;";
	private static final String QUERY_CONTROLLA_PAZID_DISPOSITIVO_CERTIFICATO_PER_UUID = "SELECT id_paziente FROM dmacc_t_dispositivo_certificato WHERE device_uuid=:deviceUuid ;";

	private static final String QUERY_CONTROLLA_UNIVOCITA_UUID = "SELECT device_id, data_inserimento FROM dmacc_t_dispositivo_certificato WHERE device_uuid=:deviceUuid";

	private static final String DISASSOCIA_DISP_CERT ="UPDATE dmacc_t_dispositivo_certificato SET device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:sCodDis), utente_aggiornamento=:cfRichiedente, data_aggiornamento=now()"+
		" WHERE cittadino_cf=:cf and device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:sCodCert);";

	private static final String INSERT_DISP_CERT="INSERT INTO dmacc_t_dispositivo_certificato(device_uuid, device_numero_telefono, device_so, device_browser, device_modello, id_paziente, device_stato_id, fonte_cert_id, cittadino_cf, data_certificazione, data_inserimento, data_aggiornamento, utente_inserimento, utente_aggiornamento) "
			+ "	VALUES (:uuidDevice, pgp_sym_encrypt(:deviceNumeroTelefono,(:encryptionkey)), :sistemaOperativo, :browser, :modello, (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cittadinoCf and data_annullamento is null and data_decesso is null order by 1 desc limit 1), (SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:sCodCert and now() BETWEEN validita_inizio and COALESCE(validita_fine, now())), :fonteCertId, :cittadinoCf, now(), now(), null, :uteInserimento, null);";//cfRichiedente->uteInserimento 03/02/2023

	private static final String INSERT_DISP_CERT_DEVICE="INSERT INTO dmacc_t_dispositivo_certificato (device_uuid, device_numero_telefono, device_so, device_browser, device_modello, id_paziente, device_stato_id, fonte_cert_id, cittadino_cf, data_certificazione, data_inserimento, utente_inserimento) "
			+ "	VALUES (:uuidDevice, pgp_sym_encrypt(:deviceNumeroTelefono,(:encryptionkey)), :sistemaOperativo, :browser, :modello, (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cittadinoCf and data_annullamento is null and data_decesso is null order by 1 desc limit 1), (SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod=:sCodCert and now() BETWEEN validita_inizio and COALESCE(validita_fine, now())), (SELECT fonte_cert_id FROM dmacc_d_fonte_certificazione WHERE fonte_cert_cod=:codiceFonte), :cittadinoCf, now(), now(), :cfRichiedente);";

	@Value("${encryptionkey}")
	private String encryptionkey;

	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;

	@Transactional
	public List<DmaccTDispositivoCertificatoStato> findDispositivi(String cf, String scod) {
		log.info("FarmabGestioneDeviceController::findDispositivi cf="+cf+" stato disp="+scod);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf).addValue("scod", scod).addValue("encryptionkey", encryptionkey);
		List<DmaccTDispositivoCertificatoStato> dispositivi=jdbcTemplate.query(QUERY_FIND_ALL_DISP, namedParameters,(rs, rowNum) ->
		new DmaccTDispositivoCertificatoStato(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
				rs.getString(7),rs.getString(8),rs.getLong(9),rs.getLong(10),rs.getLong(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
				rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19)));
		log.debug(dispositivi);
		return dispositivi;
	}

	@Transactional
	public int disassociaDispositiviCert(String cf, String cfRichiedente) {
		log.info("FarmabGestioneDeviceController::disassociaDispositiviCert cf="+cf+" sCodDis="+Constants.DEVICE_DISSOCIATO+" sCodCert="+Constants.DEVICE_CERTIFICATO);
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cf", cf).addValue("sCodDis", Constants.DEVICE_DISSOCIATO).addValue("sCodCert", Constants.DEVICE_CERTIFICATO).addValue("cfRichiedente", cf);
		int n=jdbcTemplate.update(DISASSOCIA_DISP_CERT, namedParameters);
		log.debug("Disassociato "+n +"Device");
		return n;
	}

	public int inserisciNewDispositivoCert(CertificaDeviceConOtp certificaDeviceConOtpRequest,DmaccTDispositivoCertificazioneOtpRichDomain device) {
		log.info("FarmabGestioneDeviceController::inserisciNewDispositivoCert cf="+certificaDeviceConOtpRequest.getCfCittadino());
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("uuidDevice", certificaDeviceConOtpRequest.getUuidDevice())
				.addValue("deviceNumeroTelefono", device.getDeviceNumeroTelefono()).addValue("sistemaOperativo", certificaDeviceConOtpRequest.getDispositivo().getSistemaOperativo())
				.addValue("browser", certificaDeviceConOtpRequest.getDispositivo().getBrowser()).addValue("modello", certificaDeviceConOtpRequest.getDispositivo().getModello())
				.addValue("cittadinoCf", certificaDeviceConOtpRequest.getCfCittadino()).addValue("sCodCert", Constants.DEVICE_CERTIFICATO).addValue("fonteCertId", device.getFonteCertId()).addValue("cittadinoCf", certificaDeviceConOtpRequest.getCfCittadino()).addValue("cfRichiedente", certificaDeviceConOtpRequest.getRichiedente().getCodiceFiscale()).addValue("encryptionkey", encryptionkey)
				.addValue("uteInserimento", device.getUtenteInserimento());//modifica effettuata per change in analisi 03/02/2023 tom prima salvavamo cfRichiedente ho lasciato addValue per un eventuale cambio

		int n=jdbcTemplate.update(INSERT_DISP_CERT, namedParameters);
		log.debug("Inserito "+n +" Device");
		return n;
	}

	public int inserisciNewDispositivoCertDevice(CertificaDevice certificaDevice) {
		log.info("FarmabGestioneDeviceController::inserisciNewDispositivoCertDevice cf="+certificaDevice.getCfCittadino());
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("uuidDevice", certificaDevice.getUuidDevice())
				.addValue("deviceNumeroTelefono", certificaDevice.getTelCittadino()).addValue("sistemaOperativo", certificaDevice.getDispositivo().getSistemaOperativo())
				.addValue("browser", certificaDevice.getDispositivo().getBrowser()).addValue("modello", certificaDevice.getDispositivo().getModello())
				.addValue("cittadinoCf", certificaDevice.getCfCittadino()).addValue("sCodCert", Constants.DEVICE_CERTIFICATO).addValue("codiceFonte", certificaDevice.getFonte().getCodice())
				.addValue("cittadinoCf", certificaDevice.getCfCittadino()).addValue("cfRichiedente", certificaDevice.getRichiedente().getCodiceFiscale()).addValue("encryptionkey", encryptionkey);
		int n=jdbcTemplate.update(INSERT_DISP_CERT_DEVICE, namedParameters);
		log.debug("Inserito "+n +" Device");
		return n;
	}

	@Transactional
	public Long esisteDispositivoAssociatoConStato(String cfCittadino, String statoCert) {
		log.info("FarmabGestioneDeviceController::controllaDispositivoAssociato");
		Long numDevice=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cfCittadino", cfCittadino).addValue("statoCert", statoCert);

		try {
			numDevice = jdbcTemplate.queryForObject(QUERY_ESISTE_DISPOSITIVO_CON_STATO, namedParameters, (rs, rowNum) -> rs.getLong(1));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per cfCittadino="+cfCittadino + " e statoCert: "+statoCert);
		}

		log.debug(numDevice);

		return numDevice;
	}

	@Transactional
	public BigDecimal controllaDispositivoAssociato(String cfCittadino, String deviceUuid, String statoCert) {
		log.info("FarmabGestioneDeviceController::controllaDispositivoAssociato");
	    BigDecimal idDevice=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cfCittadino", cfCittadino).addValue("deviceUuid", deviceUuid).addValue("statoCert", statoCert);

		try {
			idDevice = jdbcTemplate.queryForObject(QUERY_CONTROLLA_DISPOSITIVO_CERTIFICATO_PER_UUID, namedParameters, (rs, rowNum) -> rs.getBigDecimal(1));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per cfCittadino="+cfCittadino + " e deviceUuid: "+deviceUuid + " e statoCert:"+statoCert);
		}

		log.debug(idDevice);

		return idDevice;
	}

	@Transactional
	public String cercaDescrizioneFonte(String fonteCertCod) {
		log.info("FarmabGestioneDeviceController::cercaDescrizioneFonte");
	    String fonteCertDesc=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("fonteCertCod", fonteCertCod);

		try {
			fonteCertDesc = jdbcTemplate.queryForObject(QUERY_FIND_DESCR_FONTE, namedParameters, (rs, rowNum) -> rs.getString(1));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per fonteCertCod: "+fonteCertCod);
		}

		log.debug(fonteCertDesc);

		return fonteCertDesc;
	}

	@Transactional
	public String controllaCodiceFiscaleDispositivoAssociato(String deviceUuid) {
		log.info("FarmabGestioneDeviceController::controllaCodiceFiscaleDispositivoAssociato");
	    String codiceFiscale=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("deviceUuid", deviceUuid);

		try {
			codiceFiscale = jdbcTemplate.queryForObject(QUERY_CONTROLLACF_DISPOSITIVO_CERTIFICATO_PER_UUID, namedParameters, (rs, rowNum) -> rs.getString(1));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per deviceUuid: "+deviceUuid);
		}

		log.debug(codiceFiscale);

		return codiceFiscale;
	}

	@Transactional
	public Long controllaidPazienteDispositivoAssociato(String deviceUuid) {
		log.info("FarmabGestioneDeviceController::controllaidPazienteDispositivoAssociato");
	    Long idPaziente=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("deviceUuid", deviceUuid);

		try {
			idPaziente = jdbcTemplate.queryForObject(QUERY_CONTROLLA_PAZID_DISPOSITIVO_CERTIFICATO_PER_UUID, namedParameters, (rs, rowNum) -> rs.getLong(1));
		} catch (Exception e) {
			log.debug("Non ho trovato nulla per deviceUuid: "+deviceUuid);
		}

		log.debug(idPaziente);

		return idPaziente;
	}

	@Transactional
	public List<DmaccTDispositivoCertificatoStato> controllaUnivocitaUuid(String deviceUuid) {
		log.info("FarmabGestioneDeviceController::controllaUnivocitaUuid");

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("deviceUuid", deviceUuid);

		List<DmaccTDispositivoCertificatoStato> dispositivi = jdbcTemplate.query(QUERY_CONTROLLA_UNIVOCITA_UUID, namedParameters, (rs, rowNum) ->
		new DmaccTDispositivoCertificatoStato(rs.getLong(1),rs.getString(2)));

		log.debug(dispositivi);

		return dispositivi;
	}

	public List<Long> controllaDispositivoAssociatoPerCF(String cittadinoCf) {
		log.info("10.FarmabFarmacieAbitualiController::controllaDispositivoAssociatoPerCF");
		List<Long> countRecord=null;

		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf);
			log.info(QUERY_CONTROLLA_DISPOSITIVO_CERTIFICATO_PER_CF);
			log.info("Params:farmaciaCod=cittadinoCf: "+cittadinoCf);
			countRecord= jdbcTemplate.query(QUERY_CONTROLLA_DISPOSITIVO_CERTIFICATO_PER_CF, namedParameters,(rs, rowNum) ->
			rs.getLong(1));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return countRecord;
	}

}
