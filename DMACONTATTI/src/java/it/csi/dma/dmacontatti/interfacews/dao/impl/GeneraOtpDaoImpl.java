/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao.impl;

import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.ContattiOTPLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.ContattiOTPLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.integration.rest.RestClient;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadEmailDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadSmsDTO;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtpResponse;
import it.csi.dma.dmacontatti.interfacews.dao.GeneraOtpDao;
import it.csi.dma.dmacontatti.util.Configurazione;
import it.csi.dma.dmacontatti.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GeneraOtpDaoImpl implements GeneraOtpDao {

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");

    private String urlRestPostTopics;
    private RestClient restClient;
    private Configurazione configurazione;
    private ContattiOTPLowDao contattiOTPLowDao;
    private LogGeneralDao logGeneralDao;
    private String token;
    private ConfigurazioneLowDao configurazioneLowDao;

    @Override
    public GeneraOtpResponse generaOtp(GeneraOtp generaOtp, PazienteLowDto pazienteLowDto,
                                       List<Errore> errori, GeneraOtpResponse generaOtpResponse) throws Exception {

        //Invalidare tutti i codici precedenti associati al paziente
        invalidateCodiciOtp(generaOtp.getCodiceFiscalePaziente());

        String generatedOtp = Utils.genrateSecureOtp(6);
        Timestamp dataCreazione = Utils.sysdate();

        //Inserisci otp generato in tabella
        inserimentoOtpGenerato(generaOtp, pazienteLowDto, generatedOtp, dataCreazione);

        //Chiamata notificatore
        ResponseEntity<String> response = callNotificatore(generaOtp, generatedOtp);
        if(response == null || response.getStatusCode() != HttpStatus.CREATED){
            errori.add(logGeneralDao.getErroreCatalogo(Constants.ERRORE_GENERICO_REST));
            generaOtpResponse.setErrori(errori);
            return generaOtpResponse;
        }

        //Calcolo dataScadenza
        Timestamp validitaOTP = dataCreazione;
        ConfigurazioneLowDto dto = Utils.getFirstRecord(configurazioneLowDao.findByCodice(Constants.VALIDITA_OTP));
        long minuti = Long.parseLong(dto.getValore());
        Instant instantValiditaOTP = validitaOTP.toInstant();
        Instant test = instantValiditaOTP.plus(minuti, ChronoUnit.MINUTES);
        validitaOTP = Timestamp.from(test);

        generaOtpResponse.setOtp(generatedOtp);
        generaOtpResponse.setDataInserimento(dataCreazione.toString());
        generaOtpResponse.setDataScadenza(validitaOTP.toString());

        return generaOtpResponse;
    }

    private ResponseEntity<String> callNotificatore(GeneraOtp generaOtp, String generatedOtp) throws Exception {
        NotificaDTO notificaDTO = generaRequestRest(generaOtp, generatedOtp);

        return restClient.chiamataRest(notificaDTO, generaOtp.getRichiedente().getUUID(), Constants.POST_TOPIC, urlRestPostTopics,
                HttpMethod.POST, token, null);

    }

    private NotificaDTO generaRequestRest(GeneraOtp generaOtp, String generatedOtp) {
        NotificaDTO notificaDTO = new NotificaDTO();
        notificaDTO.setUuid(generaOtp.getRichiedente().getUUID());
        notificaDTO.setTrusted(true);
        NotificaPayloadDTO notificaPayloadDTO = new NotificaPayloadDTO();
        notificaPayloadDTO.setId(generaOtp.getRichiedente().getUUID());
        notificaPayloadDTO.setUser_id(generaOtp.getCodiceFiscalePaziente());
        notificaPayloadDTO.setTag(configurazione.get(Constants.CONF_OTP_NOTIFICA_TAG));
        notificaPayloadDTO.setTrusted(true);
        if(generaOtp.getCanale().equalsIgnoreCase(Constants.CANALE_MAIL)){
            NotificaPayloadEmailDTO notificaPayloadEmailDTO = new NotificaPayloadEmailDTO();
            notificaPayloadEmailDTO.setTo(generaOtp.getContatto());
            notificaPayloadEmailDTO.setSubject(configurazione.get(Constants.CONF_OTP_NOTIFICA_OGGETTO_MAIL));
            notificaPayloadEmailDTO.setBody(MessageFormat.format(configurazione.get(Constants.CONF_OTP_NOTIFICA_TESTO_MAIL), generatedOtp));
            notificaPayloadEmailDTO.setTemplate_id(configurazione.get(Constants.CONF_OTP_NOTIFICA_TEMPLATE_MAIL));
            notificaPayloadDTO.setEmail(notificaPayloadEmailDTO);
        }else{
            NotificaPayloadSmsDTO notificaPayloadSmsDTO = new NotificaPayloadSmsDTO();
            notificaPayloadSmsDTO.setPhone(Constants.PREFISSO_PHONE + generaOtp.getContatto());
            notificaPayloadSmsDTO.setContent(MessageFormat.format(configurazione.get(Constants.CONF_OTP_NOTIFICA_TEST_SMS), generatedOtp));
            notificaPayloadDTO.setSms(notificaPayloadSmsDTO);
        }
        notificaDTO.setPayload(notificaPayloadDTO);
        return notificaDTO;
    }

    private void inserimentoOtpGenerato(GeneraOtp generaOtp, PazienteLowDto pazienteLowDto, String generatedOtp, Timestamp dataCreazione) {
        ContattiOTPLowDto contattiOTPLowDto = new ContattiOTPLowDto();
        contattiOTPLowDto.setIdPaziente(pazienteLowDto != null? pazienteLowDto.getIdPaziente() : null);
        contattiOTPLowDto.setCanale(generaOtp.getCanale());
        contattiOTPLowDto.setOtp(generatedOtp);
        contattiOTPLowDto.setDataInizioValidita(dataCreazione);
        contattiOTPLowDto.setCodiceFiscalePaziente(generaOtp.getCodiceFiscalePaziente());
        contattiOTPLowDao.insert(contattiOTPLowDto);
    }

    private void invalidateCodiciOtp(String cfPaziente) throws ContattiOTPLowDaoException {
        ContattiOTPLowDto contattiOTPLowDto = new ContattiOTPLowDto();
        contattiOTPLowDto.setCodiceFiscalePaziente(cfPaziente);
        List<ContattiOTPLowDto> contattiOTPLowDtoList = contattiOTPLowDao.findByFilterValidi(contattiOTPLowDto);
        if(contattiOTPLowDtoList != null && !contattiOTPLowDtoList.isEmpty()){
            for(ContattiOTPLowDto dto : contattiOTPLowDtoList){
                dto.setDataFineValidita(Utils.sysdate());
                contattiOTPLowDao.update(dto);
            }
        }
    }

    public String getUrlRestPostTopics() {
        return urlRestPostTopics;
    }

    public void setUrlRestPostTopics(String urlRestPostTopics) {
        this.urlRestPostTopics = urlRestPostTopics;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Configurazione getConfigurazione() {
        return configurazione;
    }

    public void setConfigurazione(Configurazione configurazione) {
        this.configurazione = configurazione;
    }

    public ContattiOTPLowDao getContattiOTPLowDao() {
        return contattiOTPLowDao;
    }

    public void setContattiOTPLowDao(ContattiOTPLowDao contattiOTPLowDao) {
        this.contattiOTPLowDao = contattiOTPLowDao;
    }

    public LogGeneralDao getLogGeneralDao() {
        return logGeneralDao;
    }

    public void setLogGeneralDao(LogGeneralDao logGeneralDao) {
        this.logGeneralDao = logGeneralDao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ConfigurazioneLowDao getConfigurazioneLowDao() {
        return configurazioneLowDao;
    }

    public void setConfigurazioneLowDao(ConfigurazioneLowDao configurazioneLowDao) {
        this.configurazioneLowDao = configurazioneLowDao;
    }
}
