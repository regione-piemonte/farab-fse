/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.log;

import it.csi.dma.dmacontatti.business.dao.CatalogoServiziOperationLowDao;
import it.csi.dma.dmacontatti.business.dao.ServiziEsterniLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.*;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenze;
import it.csi.dma.dmacontatti.interfacews.contatti.PutTermsPreferenzeContatti;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtp;
import it.csi.dma.dmacontatti.util.Utils;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.UUID;

public class CreateLogBeanDaoImpl implements CreateLogBeanDao {

	private ServiziEsterniLowDao serviziEsterniLowDao;
	private CatalogoServiziOperationLowDao catalogoServiziOperationLowDao;
	
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".dao");

	private final static String CODICE_TOKEN_OPERAZIONE = "1";


	@Override
	public LogGeneralDaoBean prepareLogBeanStart(GeneraOtp req, String encryptionKey) throws Exception {

		XmlMessaggiLowDto messaggiXmlDto = new XmlMessaggiLowDto();

		// Creo MessaggiXmlDto
		messaggiXmlDto.setXml_in(Utils.xmlMessageFromObject(req));
		LogGeneralDaoBean logGeneralDaoBean = prepareLogBeanStartGenericSol(Constants.GENERA_OTP, messaggiXmlDto,
				encryptionKey, req.getRichiedente().getIndirizzoIp(), req.getRichiedente().getUUID(),
				req.getRichiedente().getApplicazioneChiamante(), req.getRichiedente().getCodiceFiscaleRichiedente(),
				req.getRichiedente().getCodiceTokenOperazione());
		logGeneralDaoBean.getMessaggiDto().setCf_assistito(req.getCodiceFiscalePaziente());
		return logGeneralDaoBean;
	}
	
	@Override
	public LogGeneralDaoBean prepareLogBeanStart(VerificaOtp req, String encryptionKey) throws Exception {
		XmlMessaggiLowDto messaggiXmlDto = new XmlMessaggiLowDto();

		// Creo MessaggiXmlDto
		messaggiXmlDto.setXml_in(Utils.xmlMessageFromObject(req));
		LogGeneralDaoBean logGeneralDaoBean = prepareLogBeanStartGenericSol(Constants.VERIFICA_OTP, messaggiXmlDto,
				encryptionKey, req.getRichiedente().getIndirizzoIp(), req.getRichiedente().getUUID(),
				req.getRichiedente().getApplicazioneChiamante(), req.getRichiedente().getCodiceFiscaleRichiedente(),
				req.getRichiedente().getCodiceTokenOperazione());
		logGeneralDaoBean.getMessaggiDto().setCf_assistito(req.getCodiceFiscalePaziente());
		return logGeneralDaoBean;
	}
	
	@Override
	public LogGeneralDaoBean prepareLogBeanStart(GetPreferenze req, String encryption_key) throws Exception {
		
		XmlMessaggiLowDto messaggiXmlDto = new XmlMessaggiLowDto();

		// Creo MessaggiXmlDto
		messaggiXmlDto.setXml_in(Utils.xmlMessageFromObject(req));
		LogGeneralDaoBean logGeneralDaoBean = prepareLogBeanStartGenericSol(Constants.GET_PREFERENZE, messaggiXmlDto,
				encryption_key, req.getRichiedente().getIndirizzoIp(), req.getRichiedente().getUUID(),
				req.getRichiedente().getApplicazioneChiamante(), req.getRichiedente().getCodiceFiscaleRichiedente(),
				req.getRichiedente().getCodiceTokenOperazione());
		logGeneralDaoBean.getMessaggiDto().setCf_assistito(req.getCodiceFiscalePaziente());
		return logGeneralDaoBean;
				
	}
	
	@Override
	public LogGeneralDaoBean prepareLogBeanStart(PutTermsPreferenzeContatti req, String encryption_key)
			throws Exception {
		
		XmlMessaggiLowDto messaggiXmlDto = new XmlMessaggiLowDto();

		// Creo MessaggiXmlDto
		messaggiXmlDto.setXml_in(Utils.xmlMessageFromObject(req));
		LogGeneralDaoBean logGeneralDaoBean = prepareLogBeanStartGenericSol(Constants.PUT_TERMS_PREFERENZE_CONTATTI, messaggiXmlDto,
				encryption_key, req.getRichiedente().getIndirizzoIp(), req.getRichiedente().getUUID(),
				req.getRichiedente().getApplicazioneChiamante(), req.getRichiedente().getCodiceFiscaleRichiedente(),
				req.getRichiedente().getCodiceTokenOperazione());
		logGeneralDaoBean.getMessaggiDto().setCf_assistito(req.getCodiceFiscalePaziente());
		return logGeneralDaoBean;
	}

	public LogGeneralDaoBean prepareLogBeanStartGenericSol(String codiceServizio, XmlMessaggiLowDto messaggiXmlDto, String encryptionKey,
														   String indirizzoIp, String uuid, String applicazioneChiamante,
														   String codiceFiscaleRichiedente, String codiceTokenOperazione)
			throws Exception {

		LogLowDto logDto = new LogLowDto();
		MessaggiLowDto messaggiDto = new MessaggiLowDto();

		String idTransazione =  UUID.randomUUID().toString();

		// //Creo LogDto
		logDto = createLogDto(codiceServizio, idTransazione, codiceTokenOperazione);

		// //Creo MessaggiDto
		messaggiDto.setChiamante(applicazioneChiamante);
		messaggiDto.setIp(indirizzoIp);
		messaggiDto.setCf_utente(codiceFiscaleRichiedente);


		messaggiDto.setUuid(idTransazione);
		messaggiDto.setWso2_id(idTransazione);
		messaggiDto.setId_messaggio_orig(uuid);
		messaggiDto = createMessaggiDto(messaggiDto, codiceServizio, applicazioneChiamante, indirizzoIp);
		messaggiXmlDto = createMessaggiXmlDto(messaggiXmlDto, idTransazione, encryptionKey);

		return new LogGeneralDaoBean(logDto, messaggiDto, messaggiXmlDto, null);

	}
	


	@Override
	public LogGeneralDaoBean prepareLogBeanServiziRichiamatiStart(LogGeneralDaoBean logGeneralDaoBean,
			String numeroTransazione, String servizioRichiamato, String request, String encryptionKey)
			throws Exception {
		ServiziRichiamatiLowDto serviziRichiamatiLowDto = new ServiziRichiamatiLowDto();

		ServiziEsterniLowDto serviziEsterniLowDto = serviziEsterniLowDao.findByCodiceServizio(servizioRichiamato);
		if (serviziEsterniLowDto != null)
			serviziRichiamatiLowDto.setIdServizio(serviziEsterniLowDto.getId());
		serviziRichiamatiLowDto.setIdTransazione(numeroTransazione);
		serviziRichiamatiLowDto.setDataChiamata(Utils.sysdate());
		serviziRichiamatiLowDto.setDataInserimento(Utils.sysdate());
		serviziRichiamatiLowDto.setRequest(request);
		serviziRichiamatiLowDto.setEncryptionKey(encryptionKey);

		logGeneralDaoBean.setServiziRichiamatiLowDto(serviziRichiamatiLowDto);
		return logGeneralDaoBean;
	}

	private LogLowDto createLogDto(String codiceServizio, String idTransazione, String codiceTokenOperazione) {

		LogLowDto logDto = new LogLowDto();
		logDto.setDataInserimento(Utils.sysdate());
		logDto.setCodiceTokenOperazione(codiceTokenOperazione);
		logDto.setCodiceServizio(codiceServizio);
		logDto.setIdTransazione(idTransazione);
		return logDto;
	}

	private MessaggiLowDto createMessaggiDto(MessaggiLowDto messaggiDto,
			String codiceServizio, String codiceApplicativoVerticale, String ip) throws Exception{

		CatalogoServiziOperationLowDto catalogoServiziOperation = new CatalogoServiziOperationLowDto();
		catalogoServiziOperation.setCodiceServizio(codiceServizio);

		catalogoServiziOperation = catalogoServiziOperationLowDao.findByCodiceServizio(catalogoServiziOperation);

		Timestamp todayDate = Utils.sysdate();

		messaggiDto.setServizio_xml(catalogoServiziOperation.getNomeOperation());
		messaggiDto.setStato_xml(1);
		messaggiDto.setData_ricezione(todayDate);
		messaggiDto.setData_ins(todayDate);
		messaggiDto.setData_invio_servizio(todayDate);
		messaggiDto.setCodiceServizio(catalogoServiziOperation.getCodiceServizio());
		messaggiDto.setIp(ip);
		messaggiDto.setApplicativoVerticale(codiceApplicativoVerticale);
		return messaggiDto;
	}

	//
	private XmlMessaggiLowDto createMessaggiXmlDto(XmlMessaggiLowDto messaggiXmlDto, String idTransazione,
			String encryptionKey) {

		// messaggiXmlDto.setId(id); DA SEQ
		messaggiXmlDto.setWso2_id(idTransazione);
		// messaggiXmlDto.setXml_in(Utils.xmlMessageFromObject(reportOperazioniConsensiRequest));//DA
		// CIFRARE IN FASE DI INSERT
		messaggiXmlDto.setData_inserimento(Utils.sysdate());

		messaggiXmlDto.setEcryption(encryptionKey);
		return messaggiXmlDto;
	}

	public static String xmlMessageFromObject(Object obj) {
		String xmlString = null;
		if (obj != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(obj.getClass());
				javax.xml.bind.Marshaller m = context.createMarshaller();

				m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				StringWriter sw = new StringWriter();
				m.marshal(obj, sw);
				xmlString = sw.toString();

			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return xmlString;
	}


	public ServiziEsterniLowDao getServiziEsterniLowDao() {
		return serviziEsterniLowDao;
	}

	public void setServiziEsterniLowDao(ServiziEsterniLowDao serviziEsterniLowDao) {
		this.serviziEsterniLowDao = serviziEsterniLowDao;
	}

	public CatalogoServiziOperationLowDao getCatalogoServiziOperationLowDao() {
		return catalogoServiziOperationLowDao;
	}

	public void setCatalogoServiziOperationLowDao(CatalogoServiziOperationLowDao catalogoServiziOperationLowDao) {
		this.catalogoServiziOperationLowDao = catalogoServiziOperationLowDao;
	}

	
}