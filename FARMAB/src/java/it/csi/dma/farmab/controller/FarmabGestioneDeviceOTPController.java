/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

import java.util.List;
import java.util.SplittableRandom;

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

import it.csi.dma.farmab.domain.DmaccTDispositivoCertificazioneOtpRichDomain;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDeviceConOtp;
import it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice.GetGeneraOtpDeviceRequest;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.EventoPerNotificatore;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabGestioneDeviceOTPController extends JdbcDaoSupport{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	//private static final String QUERY_FIND_ALL_DISP ="select now(),(NOW() + interval '60 second') AS an_minute_later  ";
	private static final String QUERY_FIND_OTP_DEVICE_TIMETOLIVE ="SELECT valore::int FROM dmacc_t_configurazione where chiave ='"+Constants.OTP_DEVICE_TIMETOLIVE+"'";
	private static final String QUERY_FIND_OTP_DEVICE_LINK ="SELECT valore FROM dmacc_t_configurazione where chiave ='"+Constants.OTP_DEVICE_LINK+"'";
	private static final String QUERY_FIND_OLD_OTP ="SELECT device_otp_rich_id FROM dmacc_t_dispositivo_certificazione_otp_rich where cittadino_cf=:cittadinoCf and device_otp_rich_stato_id=(SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='VAL' and validita_fine is null);";
	private static final String QUERY_FIND_VALID_OTP ="SELECT device_otp_rich_id, pgp_sym_decrypt(device_numero_telefono, (:encryptionkey)) as device_numero_telefono, otp, id_paziente, cittadino_cf, fonte_cert_id, device_otp_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, utente_inserimento, utente_aggiornamento,  "
			+ " to_char((data_inserimento+ '%1$s minute'::INTERVAL), 'YYYY-MM-DD HH24:MI:ss') as dataFineValidita"
			+ " FROM dmacc_t_dispositivo_certificazione_otp_rich where cittadino_cf=:cittadinoCf and device_otp_rich_stato_id=(SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='VAL' and validita_fine is null)"
			+ " and data_inserimento <= now() and NOW() <= (data_inserimento + interval '%2$s minute');";
	private static final String UPDATE_INVALIDATE_OTP = "UPDATE dmacc_t_dispositivo_certificazione_otp_rich "
			+ "	SET  device_otp_rich_stato_id=(SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='INV' and validita_fine is null), data_aggiornamento=now(), utente_aggiornamento=:cfRichiedente "
			+ "	WHERE cittadino_cf=:cittadinoCf and device_otp_rich_stato_id in (SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='VAL' and validita_fine is null);";
	private static final String INSERT_VALID_OTP ="INSERT INTO dmacc_t_dispositivo_certificazione_otp_rich("
			+ "	device_numero_telefono, otp, id_paziente, cittadino_cf, fonte_cert_id, device_otp_rich_stato_id, data_inserimento, data_aggiornamento, utente_inserimento, utente_aggiornamento) "
			+ "	VALUES ( pgp_sym_encrypt(:deviceNumeroTelefono,(:encryptionkey)), :otp, (SELECT id_paziente FROM dmacc_t_paziente where codice_fiscale=:cittadinoCf and data_annullamento is null and data_decesso is null order by 1 desc limit 1), :cittadinoCf, 2, (SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='VAL' and validita_fine is null), now(), null, :cfRichiedente, null);";

	//--------------------------
	private static final String QUERY_ID_STATO_VAL="SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod=:statoCod and validita_fine is null limit 1;";
	private static final String QUERY_VERIFY_ESIST_OTP="SELECT device_otp_rich_id, pgp_sym_decrypt(device_numero_telefono, (:encryptionkey)) as device_numero_telefono, otp, id_paziente, cittadino_cf, fonte_cert_id, device_otp_rich_stato_id, to_char(data_inserimento, 'YYYY-MM-DD HH24:MI:ss') as data_inserimento, to_char(data_aggiornamento, 'YYYY-MM-DD HH24:MI:ss') as data_aggiornamento, utente_inserimento, utente_aggiornamento,   "
			+ " to_char(now(), 'YYYY-MM-DD HH24:MI:ss') as dataFineValidita "
			+ " FROM dmacc_t_dispositivo_certificazione_otp_rich where cittadino_cf=:cittadinoCf and otp=:codiceOtp ";
			//+ " and device_otp_rich_stato_id=:deviceOtpRichStatoId;";

	//------------------------------------
	private static final String UPDATE_INVALIDATE_SINGLE_OTP = "UPDATE dmacc_t_dispositivo_certificazione_otp_rich "
			+ "	SET  device_otp_rich_stato_id=(SELECT device_otp_rich_stato_id FROM dmacc_d_dispositivo_certificazione_otp_rich_stato where device_otp_rich_stato_cod='INV' and validita_fine is null), data_aggiornamento=now(), utente_aggiornamento=:cfRichiedente "
			+ "	WHERE cittadino_cf=:cittadinoCf and device_otp_rich_id=:deviceOtpRichId";

	/*cancellare non serve piu' modificata analisi
	private static final String QUERY_DATI_PER_NOTIFICATORE_OLD ="SELECT id, codice_evento, descrizione_evento, msg_mail, msg_sms, msg_push, msg_mex, data_inserimento, data_aggiornamento"
			+ " FROM dmacc_d_evento_per_notificatore WHERE codice_evento=:codiceEvento";
	*/
	private static final String QUERY_DATI_PER_NOTIFICATORE="SELECT d.id, codice_evento, descrizione_evento, d.msg_mail, d.msg_sms, d.msg_push, d.msg_mex, d.data_inserimento, d.data_aggiornamento"
	+ "	FROM dmacc_r_evnot_dest_msg d, dmacc_d_evento_per_notificatore n where d.id_evento=n.id"
	+ "	 AND d.id_evento=(SELECT id FROM dmacc_d_evento_per_notificatore WHERE codice_evento=:codiceEvento)"
	+ " AND d.id_tipo_destinatario=(SELECT id FROM dmacc_d_tipo_destinatario WHERE codice=:codiceDestinatario);";//si potrebbe mettere LIMIT 1 per avere un solo record

	//14/03/2023 verifica esistenza di un device certificato
	private static final String QUERY_COUNT_DEVICE_CERT="SELECT count(*) FROM dmacc_t_dispositivo_certificato where cittadino_cf=:cittadinoCf and device_stato_id=(SELECT device_stato_id FROM dmacc_d_dispositivo_certificato_stato where device_stato_cod='CERT' and validita_fine is null);";

	@Value("${encryptionkey}")
	private String encryptionkey;

	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;

	private String getIntervalMin() {
		log.info("FarmabGestioneDeviceOTPController::getIntervalMin");
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		String intervalMin=jdbcTemplate.queryForObject(QUERY_FIND_OTP_DEVICE_TIMETOLIVE, namedParameters, (rs, rowNum) ->new String(rs.getString(1)));
		//TODO che facciamo se non Ã¨ definito?
		log.debug("intervalMin="+intervalMin);
		return intervalMin;
	}

	public String getLinkOtp() {
		log.info("FarmabGestioneDeviceOTPController::getLinkOtp");
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		String link=jdbcTemplate.queryForObject(QUERY_FIND_OTP_DEVICE_LINK, namedParameters, (rs, rowNum) ->new String(rs.getString(1)));
		log.debug("link="+link);
		return link;
	}

	private List<Long> getOldIDOTP(GetGeneraOtpDeviceRequest getGeneraOtpDevice) {
		log.info("FarmabGestioneDeviceOTPController::getOldIDOTP");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", getGeneraOtpDevice.getCfCittadino());
		List<Long> listId=jdbcTemplate.query(QUERY_FIND_OLD_OTP, namedParameters, (rs, rowNum) ->new Long(rs.getString(1)));
		//listId.forEach(element -> log.debug(element));
		return listId;
	}

	private List<DmaccTDispositivoCertificazioneOtpRichDomain> getValidOTP(String cittadinoCf) {
		log.info("FarmabGestioneDeviceOTPController::getValidOTP");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("encryptionkey", encryptionkey);
		String newSql=String.format(QUERY_FIND_VALID_OTP, getIntervalMin(), getIntervalMin());
		log.debug(newSql);
		List<DmaccTDispositivoCertificazioneOtpRichDomain> otpAncoraValidi=null;
		try {
			otpAncoraValidi=jdbcTemplate.query(newSql, namedParameters,(rs, rowNum) -> new DmaccTDispositivoCertificazioneOtpRichDomain(rs.getLong(1),rs.getString(2),
				rs.getString(3),rs.getLong(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			log.debug(otpAncoraValidi.get(0).getDataFineValidita()+" "+otpAncoraValidi.get(0).getDataInserimento());
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	    return otpAncoraValidi;
	}

	public List<Integer> decodeStatoDevice(String statoCod){
		log.info("FarmabGestioneDeviceOTPController::decodeStatoDevice");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("statoCod", statoCod);
		List<Integer> ids=null;
		try {
			ids=jdbcTemplate.query(QUERY_ID_STATO_VAL, namedParameters,(rs, rowNum) ->rs.getInt(1));
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		return ids;
	}
	/**
	 * Ricerca otp validi e non discrimina per otp scaduto, serve solo per catturare errore
	 * @param certificaDeviceConOtpRequest
	 * @return List<DmaccTDispositivoCertificazioneOtpRichDomain>
	 */
	public List<DmaccTDispositivoCertificazioneOtpRichDomain> esistOTP(CertificaDeviceConOtp certificaDeviceConOtpRequest){
		log.info("FarmabGestioneDeviceOTPController::esistOTP");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", certificaDeviceConOtpRequest.getCfCittadino()).addValue("codiceOtp", certificaDeviceConOtpRequest.getCodiceOtp()).addValue("encryptionkey", encryptionkey);
		List<DmaccTDispositivoCertificazioneOtpRichDomain> otpTrovati=null;
		try {
			otpTrovati=jdbcTemplate.query(QUERY_VERIFY_ESIST_OTP, namedParameters,(rs, rowNum) -> new DmaccTDispositivoCertificazioneOtpRichDomain(rs.getLong(1),rs.getString(2),
					rs.getString(3),rs.getLong(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			if(otpTrovati!=null && !otpTrovati.isEmpty())
				log.debug("esistOTP now="+otpTrovati.get(0).getDataFineValidita()+" DataInserimento="+otpTrovati.get(0).getDataInserimento());
		} catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return otpTrovati;
	}

	//cerco la corrispondenza tra otp e cf e aggiorno le tabelle
	public List<DmaccTDispositivoCertificazioneOtpRichDomain> verifyOTP(CertificaDeviceConOtp certificaDeviceConOtpRequest){
		log.info("FarmabGestioneDeviceOTPController::verifyOTP");
		//SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", certificaDeviceConOtpRequest.getCfCittadino()).addValue("codiceOtp", certificaDeviceConOtpRequest.getCodiceOtp());
		List<DmaccTDispositivoCertificazioneOtpRichDomain> otpTrovati=null;
		try {
			otpTrovati=getValidOTP(certificaDeviceConOtpRequest.getCfCittadino());
			if(otpTrovati!=null && !otpTrovati.isEmpty())
				log.debug("verifyOTP now="+otpTrovati.get(0).getDataFineValidita()+" DataInserimento="+otpTrovati.get(0).getDataInserimento());
		} catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return otpTrovati;
	}

	@Transactional
	public DmaccTDispositivoCertificazioneOtpRichDomain getOTP(GetGeneraOtpDeviceRequest getGeneraOtpDevice) {
		log.info("FarmabGestioneDeviceOTPController::getOTP");
		List<Long> otpAncoraValidi=getOldIDOTP(getGeneraOtpDevice);
		if(otpAncoraValidi!=null && otpAncoraValidi.size()>0) {
			log.debug("Elimino OTP ancora validi ma non utilizabili="+otpAncoraValidi.size());
			//String inParams = String.join(",", otpAncoraValidi.stream().map(id -> id).collect(Collectors.toList()));
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", getGeneraOtpDevice.getCfCittadino()).addValue("cfRichiedente", getGeneraOtpDevice.getRichiedente().getCodiceFiscale());
			//String newUpdate=String.format(UPDATE_INVALIDATE_OTP, inParams);
			//log.debug(newUpdate);
			log.debug("Update riuscita="+jdbcTemplate.update(UPDATE_INVALIDATE_OTP,namedParameters));
		}
		log.debug("Inserisco nuovo OTP");
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("deviceNumeroTelefono", getGeneraOtpDevice.getTelCittadino()).addValue("otp", getOTP(Constants.OTP_LENGHT)).addValue("cittadinoCf", getGeneraOtpDevice.getCfCittadino()).addValue("cfRichiedente", getGeneraOtpDevice.getRichiedente().getCodiceFiscale()).addValue("delMin",getIntervalMin()).addValue("encryptionkey", encryptionkey);
			jdbcTemplate.update(INSERT_VALID_OTP, namedParameters);
			log.debug("Inserito nuovo otp per il cittadino="+getGeneraOtpDevice.getCfCittadino());
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		List<DmaccTDispositivoCertificazioneOtpRichDomain> listOtp= getValidOTP(getGeneraOtpDevice.getCfCittadino());
		if (listOtp!=null && listOtp.size()>0) {
			return listOtp.get(0);
		}
		log.error("Errore durante lettura ultimo inserito");
		return null;
	}

	private String getOTP(final int lengthOfOTP) {
		//https://www.websparrow.org/java/java-8-generating-one-time-password-otp
		log.info("FarmabGestioneDeviceOTPController::getOTP");
		StringBuilder generatedOTP = new StringBuilder();
		SplittableRandom splittableRandom = new SplittableRandom();

		for (int i = 0; i < lengthOfOTP; i++) {
			int randomNumber = splittableRandom.nextInt(0, 9);
			generatedOTP.append(randomNumber);
		}
		log.debug("Otp generato="+generatedOTP.toString());
		return generatedOTP.toString();
	}

	public int invalidateOTP(String cittadinoCf,String cfRichiedente, long deviceOtpRichId)	{
		log.info("FarmabGestioneDeviceOTPController::invalidateOTP");
		int ret=0;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf).addValue("cfRichiedente", cfRichiedente).addValue("deviceOtpRichId", deviceOtpRichId);
		ret=jdbcTemplate.update(UPDATE_INVALIDATE_SINGLE_OTP,namedParameters);
		log.info("Invalidato deviceOtpRichId="+deviceOtpRichId+" riuscita="+ret);
		return ret;
	}

	public List<EventoPerNotificatore> cercaDatiPerNotficatore(String codiceEvento, String codiceDestinatario){
		log.info("FarmabGestioneDeviceOTPController::cercaDatiPerNotficatore");
		List<EventoPerNotificatore> datiNotificatore=null;

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("codiceEvento", codiceEvento).addValue("codiceDestinatario", codiceDestinatario);

		try {
			datiNotificatore=jdbcTemplate.query(QUERY_DATI_PER_NOTIFICATORE, namedParameters,(rs, rowNum) -> new EventoPerNotificatore(rs.getLong(1),rs.getString(2),
					rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));

		} catch(Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return datiNotificatore;
	}

	//14/03/2023
	public int countDeviceCert(String cittadinoCf)	{
		log.info("FarmabGestioneDeviceOTPController::countDeviceCert");
		int ret=0;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cittadinoCf", cittadinoCf);
		ret=jdbcTemplate.queryForObject(QUERY_COUNT_DEVICE_CERT,namedParameters,Integer.class);
		log.info("Per il cf:"+cittadinoCf+" trovati "+ret +"device certificati");
		return ret;
	}
}
