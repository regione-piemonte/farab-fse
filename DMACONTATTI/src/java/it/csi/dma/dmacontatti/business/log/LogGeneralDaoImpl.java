/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.log;

import it.csi.dma.dmacontatti.business.dao.*;
import it.csi.dma.dmacontatti.business.dao.dto.*;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.integration.rest.CatalogoErroriRest;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class LogGeneralDaoImpl implements LogGeneralDao {

	public final static String TIPO_ERRORE_BLOCCANTE = "Bloccante";

	private MessaggiLowDao messaggiLowDao;
	private XmlMessaggiLowDao xmlmessaggiLowDao;
	private LogLowDao logLowDao;
	private CatalogoLogLowDao catalogoLogDao;
	private ErroriLowDao erroriLowDao;
	private ServiziRichiamatiLowDao serviziRichiamatiLowDao;
	private ErroriServiziRichiamatiLowDao erroriServiziRichiamatiLowDao;
	private String encryption_key;

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".dao");

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void logStart(LogGeneralDaoBean logGeneralDaoBean, String codiceLog, Object... args) throws Exception {

		try{
			logGeneralDaoBean.setMessaggiDto(messaggiLowDao.insertLog(logGeneralDaoBean.getMessaggiDto()));

			LogLowDto logDto = getLogDto(logGeneralDaoBean, codiceLog, args);
			logLowDao.insert(logDto);

			XmlMessaggiLowDto messaggiXmlDto = logGeneralDaoBean.getMessaggiXmlDto();
			messaggiXmlDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
			logGeneralDaoBean.setMessaggiXmlDto(xmlmessaggiLowDao.insertLog(messaggiXmlDto));
		}catch (Exception e) {
			log.error("Errore durante update dei Log di fine servizio: ", e);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void logEnd(LogGeneralDaoBean logGeneralDaoBean, String esito, String xmlOut, List<Errore> errori,
					   String codiceLog, Object... args) {

		try {
			if(logGeneralDaoBean != null && logGeneralDaoBean.getLogDto() != null) {
				logGeneralDaoBean = this.prepareLogBeanEnd(logGeneralDaoBean, esito, xmlOut, errori);
				if (logGeneralDaoBean.getMessaggiErroreDto() != null) {
					List<ErroriLowDto> messaggiErroreDtoLista = logGeneralDaoBean.getMessaggiErroreDto();

					for (ErroriLowDto erroriLowDto : messaggiErroreDtoLista) {
						erroriLowDao.insert(erroriLowDto);
					}
				}

				LogLowDto logDto = getLogDto(logGeneralDaoBean, codiceLog, args);
				logLowDao.insert(logDto);

				xmlmessaggiLowDao.updateXmlServizio(logGeneralDaoBean.getMessaggiXmlDto());

				messaggiLowDao.update(logGeneralDaoBean.getMessaggiDto());
			}
		} catch (Exception e) {
			log.error("Errore durante update dei Log di fine servizio: ", e);
		}

	}

	private LogGeneralDaoBean prepareLogBeanErrori(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
			String xmlOut) {

		List<ErroriLowDto> messaggiErroreDtoLista = new ArrayList<ErroriLowDto>();

		if (errori != null && !errori.isEmpty()) {
			for (Errore errore : errori) {
				ErroriLowDto erroreLog = new ErroriLowDto();
				erroreLog.setCod_errore(errore.getCodice());
				erroreLog.setDescr_errore(errore.getDescrizione());
				erroreLog.setWso2_id(logGeneralDaoBean.getLogDto().getIdTransazione());
				erroreLog.setTipo_errore(TIPO_ERRORE_BLOCCANTE);
				erroreLog.setData_ins(Utils.sysdate());

				messaggiErroreDtoLista.add(erroreLog);
			}
		}

		// aggiorna xml out
		logGeneralDaoBean.getMessaggiXmlDto().setXml_out(xmlOut);

		MessaggiLowDto messaggi = logGeneralDaoBean.getMessaggiDto();
		messaggi.setData_risposta(Utils.sysdate());
		messaggi.setStato_xml(2);
		messaggi.setCod_esito_risposta_promemoria("9999");
		messaggi.setData_mod(Utils.sysdate());

		logGeneralDaoBean.setMessaggiDto(messaggi);

		logGeneralDaoBean.setMessaggiErroreDto(messaggiErroreDtoLista);

		return logGeneralDaoBean;

	}

	private LogGeneralDaoBean prepareLogBeanEnd(LogGeneralDaoBean logGeneralDaoBean, String esito, String xmlOut,
			List<Errore> errori) {

		List<ErroriLowDto> messaggiErroreDtoLista = new ArrayList<ErroriLowDto>();

		if (errori != null && !errori.isEmpty()) {
			for (Errore errore : errori) {
				ErroriLowDto erroreLog = new ErroriLowDto();
				erroreLog.setCod_errore(errore.getCodice());
				erroreLog.setDescr_errore(errore.getDescrizione());
				erroreLog.setWso2_id(logGeneralDaoBean.getLogDto().getIdTransazione());
				erroreLog.setTipo_errore(TIPO_ERRORE_BLOCCANTE);
				erroreLog.setData_ins(Utils.sysdate());

				messaggiErroreDtoLista.add(erroreLog);
			}

			logGeneralDaoBean.setMessaggiErroreDto(messaggiErroreDtoLista);
		}

		// aggiorna xml out
		logGeneralDaoBean.getMessaggiXmlDto().setXml_out(xmlOut);

		Timestamp todayDate = Utils.sysdate();

		MessaggiLowDto messaggi = logGeneralDaoBean.getMessaggiDto();
		messaggi.setData_risposta(todayDate);
		messaggi.setStato_xml(2);
		messaggi.setCod_esito_risposta_servizio(esito);
		messaggi.setData_mod(todayDate);
		messaggi.setData_risposta_servizio(todayDate);

		logGeneralDaoBean.setMessaggiDto(messaggi);

		return logGeneralDaoBean;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void logServiziRichiamati(LogGeneralDaoBean logGeneralDaoBean) {
		try{
			logGeneralDaoBean.setServiziRichiamatiLowDto(serviziRichiamatiLowDao.insert(logGeneralDaoBean.getServiziRichiamatiLowDto()));

		}catch (Exception e) {
			e.printStackTrace();
			log.error("Errore durante insert dei Log dei servizi richiamati: ", e);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void logServiziRichiamatiEnd(LogGeneralDaoBean logGeneralDaoBean, String esito, String response,
										List<Errore> errori, String controllore, String encryption_key) {

		try {
			if(logGeneralDaoBean != null) {
				if (errori != null && !errori.isEmpty()) {
					for (Errore errore : errori) {
						ErroriServiziRichiamatiLowDto erroreLog = new ErroriServiziRichiamatiLowDto();
						erroreLog.setCodiceErrore(errore.getCodice());
						erroreLog.setDescrizioneErrore(errore.getDescrizione());
						erroreLog.setIdServizioRichiamato(logGeneralDaoBean.getServiziRichiamatiLowDto().getId());
						erroreLog.setControllore(controllore);
						erroreLog.setDataInserimento(Utils.sysdate());
						erroriServiziRichiamatiLowDao.insert(erroreLog);
					}
				}

				ServiziRichiamatiLowDto serviziRichiamatiLowDto =
						logGeneralDaoBean.getServiziRichiamatiLowDto();

				serviziRichiamatiLowDto.setResponse(response);
				serviziRichiamatiLowDto.setEsito(esito);
				serviziRichiamatiLowDto.setDataRisposta(Utils.sysdate());
				serviziRichiamatiLowDto.setDataAggiornamento(Utils.sysdate());
				serviziRichiamatiLowDto.setEncryptionKey(encryption_key);

				serviziRichiamatiLowDao.update(serviziRichiamatiLowDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Errore durante update dei Log di fine servizio: ", e);
		}

	}

	@Override
	public Errore getErroreCatalogo(String codiceErrore, Object... args){
		Errore errore = new Errore();
		CatalogoLogLowDto catalogoLogLowDto;

		errore.setCodice(codiceErrore);
		try{
			catalogoLogLowDto = catalogoLogDao.findByCodice(codiceErrore);

			if (catalogoLogLowDto != null) {
				MessageFormat fmt = new MessageFormat(catalogoLogLowDto.getDescrizioneLog());

				errore.setDescrizione(fmt.format(args));
			}
		}catch(Exception e){
			log.error("Errore durante recupero descrizione catalogoLog: ", e);
		}

		return errore;
	}

	@Override
	public String getDescrizioneErroreRest(String esito) {
		String descrizioneErrore = null;

		if("400".equalsIgnoreCase(esito)){
			descrizioneErrore = CatalogoErroriRest.INVALID_INPUT.getValue();
		}else if("401".equalsIgnoreCase(esito)){
			descrizioneErrore = CatalogoErroriRest.UNAUTHORIZED.getValue();
		}else if("403".equalsIgnoreCase(esito)){
			descrizioneErrore = CatalogoErroriRest.TOKEN_BLACKLIST.getValue();
		}else if("404".equalsIgnoreCase(esito)){
			descrizioneErrore = CatalogoErroriRest.NOT_FOUND.getValue();
		}else if("500".equalsIgnoreCase(esito)){
			descrizioneErrore = CatalogoErroriRest.INTERNAL_ERROR.getValue();
		}

		return descrizioneErrore;
	}

	private LogLowDto getLogDto(LogGeneralDaoBean logGeneralDaoBean, String codiceLog, Object... args)
			throws Exception {
		LogLowDto logDto = logGeneralDaoBean.getLogDto();
		CatalogoLogLowDto catalogoLogLowDto;

		catalogoLogLowDto = catalogoLogDao.findByCodice(codiceLog);

		if (catalogoLogLowDto != null) {
			MessageFormat fmt = new MessageFormat(catalogoLogLowDto.getDescrizioneLog());

			logDto.setIdCatalogoLog(catalogoLogLowDto.getId());
			logDto.setInformazioniTracciate(fmt.format(args));
		}

		return logDto;
	}

	public MessaggiLowDao getMessaggiLowDao() {
		return messaggiLowDao;
	}

	public void setMessaggiLowDao(MessaggiLowDao messaggiLowDao) {
		this.messaggiLowDao = messaggiLowDao;
	}

	public XmlMessaggiLowDao getXmlmessaggiLowDao() {
		return xmlmessaggiLowDao;
	}

	public void setXmlmessaggiLowDao(XmlMessaggiLowDao xmlmessaggiLowDao) {
		this.xmlmessaggiLowDao = xmlmessaggiLowDao;
	}

	public LogLowDao getLogLowDao() {
		return logLowDao;
	}

	public void setLogLowDao(LogLowDao logLowDao) {
		this.logLowDao = logLowDao;
	}

	public String getEncryption_key() {
		return encryption_key;
	}

	public void setEncryption_key(String encryption_key) {
		this.encryption_key = encryption_key;
	}

	public CatalogoLogLowDao getCatalogoLogDao() {
		return catalogoLogDao;
	}

	public void setCatalogoLogDao(CatalogoLogLowDao catalogoLogDao) {
		this.catalogoLogDao = catalogoLogDao;
	}

	public ErroriLowDao getErroriLowDao() {
		return erroriLowDao;
	}

	public void setErroriLowDao(ErroriLowDao erroriLowDao) {
		this.erroriLowDao = erroriLowDao;
	}

	public ServiziRichiamatiLowDao getServiziRichiamatiLowDao() {
		return serviziRichiamatiLowDao;
	}

	public void setServiziRichiamatiLowDao(ServiziRichiamatiLowDao serviziRichiamatiLowDao) {
		this.serviziRichiamatiLowDao = serviziRichiamatiLowDao;
	}

	public ErroriServiziRichiamatiLowDao getErroriServiziRichiamatiLowDao() {
		return erroriServiziRichiamatiLowDao;
	}

	public void setErroriServiziRichiamatiLowDao(ErroriServiziRichiamatiLowDao erroriServiziRichiamatiLowDao) {
		this.erroriServiziRichiamatiLowDao = erroriServiziRichiamatiLowDao;
	}
}