/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;


import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.CreateLogBeanDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.RisultatoCodice;
import it.csi.dma.dmacontatti.interfacews.dao.GeneraOtpDao;
import it.csi.dma.dmacontatti.interfacews.dao.GetPreferenzeDao;
import it.csi.dma.dmacontatti.interfacews.dao.PutTermsPreferenzeContattiDao;
import it.csi.dma.dmacontatti.interfacews.dao.VerificaOtpDao;
import it.csi.dma.dmacontatti.util.Utils;
import it.csi.dma.dmacontatti.validator.BaseValidator;
import it.csi.dma.dmacontatti.validator.ContattiServiceValidator;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "ContattiService", portName = "ContattiServicePort", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmacontatti.interfacews.contatti.ContattiService")
public class ContattiServiceImpl implements ContattiService {

	private final static Logger logger = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
	private CreateLogBeanDao createLogBeanDao;
	private LogGeneralDao logGeneralDaoMed;
	private VerificaOtpDao verificaOtpDao;
	public String className = "ContattiServiceImpl";
	private String encryption_key;
	private ContattiServiceValidator contattiServiceValidator;
	private GeneraOtpDao generaOtpDao;
	private GetPreferenzeDao getPreferenzeDao;
	private PutTermsPreferenzeContattiDao putTermsPreferenzeContattiDao;

	public final static String NOME_SERVIZIO = "ContattiService";
    public final static String NOME_OPERATION_GENERA_OTP = "generaOtp";
	private static final String NOME_OPERATION_VERIFICA_OTP = "verificaOtp";
	private static final String NOME_OPERATION_GET_PREFERENZE = "getPreferenze";
	private static final String NOME_OPERATION_PUT_TERMS_PREFERENZE_CONTATTI = "putTermsPreferenzeContatti";
	
	@Resource
	private WebServiceContext wsContext;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public GeneraOtpResponse generaOtp(GeneraOtp parameters) {

		final String methodName = "generaOtp";
		GeneraOtpResponse response = new GeneraOtpResponse();
		List<Errore> errori = null;
		LogGeneralDaoBean logGeneralDaoBean = null;
		try {
			//Validazione credenziali
			errori = contattiServiceValidator.validateCredenziali(wsContext, errori, Constants.GENERA_OTP,
					className);
			if (errori != null &&  !errori.isEmpty()) {
				response = new GeneraOtpResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}

			//Log start
			logGeneralDaoBean = createLogBeanDao.prepareLogBeanStart(parameters,this.getEncryption_key());
			logGeneralDaoMed.logStart(logGeneralDaoBean, BaseValidator.CODICE_LOG_START,operation(NOME_OPERATION_GENERA_OTP));

			//Validazione campi obbligatori e codifiche
			errori = contattiServiceValidator.validateGeneraOtp(parameters, errori);
			if (errori != null &&  !errori.isEmpty()) {
				response = new GeneraOtpResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}

			PazienteLowDto pazienteLowDto = contattiServiceValidator.verificaPaziente(parameters.getCodiceFiscalePaziente(), errori);
//			if (pazienteLowDto == null){
//				response = new GeneraOtpResponse(errori, RisultatoCodice.FALLIMENTO);
//				return response;
//			}

			//logica dao
			response = generaOtpDao.generaOtp(parameters, pazienteLowDto, errori, response);
			if (response.getErrori() != null &&  !response.getErrori().isEmpty()) {
				response = new GeneraOtpResponse(response.getErrori(), RisultatoCodice.FALLIMENTO);
				return response;
			}

			response.setEsito(RisultatoCodice.SUCCESSO);
		} catch (Exception e) {
			logger.error("[ContattiService::generaOtp] ", e);
			errori.add(logGeneralDaoMed.getErroreCatalogo(BaseValidator.ERRORE_INTERNO, Constants.GENERA_OTP));
			response = new GeneraOtpResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			logGeneralDaoMed.logEnd(logGeneralDaoBean, response.getEsito().getValue(),
					Utils.xmlMessageFromObject(response),response.getErrori(),BaseValidator.LOG_RISPOSTA_SERVIZIO,
					null,null,null,NOME_SERVIZIO,
					NOME_OPERATION_GENERA_OTP,response.getEsito());
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public VerificaOtpResponse verificaOtp(VerificaOtp parameters) {
		final String methodName = "verificaOtp";
		VerificaOtpResponse response = new VerificaOtpResponse();
		List<Errore> errori = new ArrayList<Errore>();
		LogGeneralDaoBean logGeneralDaoBean = null;
		try {
			//Validazione credenziali
			errori = contattiServiceValidator.validateCredenziali(wsContext, errori, Constants.VERIFICA_OTP,
					className);
			if (errori != null && !errori.isEmpty()) {
				response = new VerificaOtpResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}
			//Log start
			logGeneralDaoBean = createLogBeanDao.prepareLogBeanStart(parameters,this.getEncryption_key());
			logGeneralDaoMed.logStart(logGeneralDaoBean, BaseValidator.CODICE_LOG_START,operation(NOME_OPERATION_VERIFICA_OTP));

			//Validazione campi obbligatori e codifiche
			errori = contattiServiceValidator.validateVerificaOtp(parameters, errori, logGeneralDaoBean);
			if (errori != null && !errori.isEmpty()) {
				response = new VerificaOtpResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}

			//Verifica OTP
			response = verificaOtpDao.verificaOtp(parameters, errori, logGeneralDaoBean);
			if (errori != null && !errori.isEmpty()) {
				response = new VerificaOtpResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			} else {
				response.setEsito(RisultatoCodice.SUCCESSO);
			}
		} catch(Exception e) {
			logger.error("[ContattiService::verificaOtp] ", e);
			errori.add(logGeneralDaoMed.getErroreCatalogo(BaseValidator.ERRORE_INTERNO, Constants.VERIFICA_OTP));
			response = new VerificaOtpResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			logGeneralDaoMed.logEnd(logGeneralDaoBean, response.getEsito().getValue(),
					Utils.xmlMessageFromObject(response),response.getErrori(),BaseValidator.LOG_RISPOSTA_SERVIZIO,
					null,null,null,NOME_SERVIZIO,
					NOME_OPERATION_VERIFICA_OTP,response.getEsito());
		}


		return response;
	}

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public GetPreferenzeResponse getPreferenze(GetPreferenze parameters) {
		final String methodName = "getPreferenze";
		GetPreferenzeResponse response = new GetPreferenzeResponse();
		List<Errore> errori = new ArrayList<Errore>();
		LogGeneralDaoBean logGeneralDaoBean = null;
		try {
			//Validazione credenziali
			errori = contattiServiceValidator.validateCredenziali(wsContext, errori, Constants.GET_PREFERENZE,
					className);
			if (errori != null && !errori.isEmpty()) {
				response = new GetPreferenzeResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}
			//Log start
			logGeneralDaoBean = createLogBeanDao.prepareLogBeanStart(parameters,this.getEncryption_key());
			logGeneralDaoMed.logStart(logGeneralDaoBean, BaseValidator.CODICE_LOG_START,operation(NOME_OPERATION_GET_PREFERENZE));
			
			//Validazione campi obbligatori e codifiche
			errori = contattiServiceValidator.validateGetPreferenze(parameters, errori, logGeneralDaoBean);
			if (errori != null && !errori.isEmpty()) {
				response = new GetPreferenzeResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}
			//Acquisizione Preferenze
			response = getPreferenzeDao.recuperaServiziAttivi(parameters, errori);
			if (errori != null && !errori.isEmpty()) {
				response = new GetPreferenzeResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}
		} catch(Exception e) {
			logger.error("[ContattiService::getPreferenze] ", e);
			errori.add(logGeneralDaoMed.getErroreCatalogo(BaseValidator.ERRORE_INTERNO, Constants.GET_PREFERENZE));
			response = new GetPreferenzeResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			logGeneralDaoMed.logEnd(logGeneralDaoBean, response.getEsito().getValue(),
					Utils.xmlMessageFromObject(response),response.getErrori(),BaseValidator.LOG_RISPOSTA_SERVIZIO,
					null,null,null,NOME_SERVIZIO,
					NOME_OPERATION_GET_PREFERENZE,response.getEsito());
		}
		return response;
	}

	@Override
	public PutTermsPreferenzeContattiResponse putTermsPreferenzeContatti(PutTermsPreferenzeContatti parameters) {
		final String methodName = "putTermsPreferenzeContatti";
		PutTermsPreferenzeContattiResponse response = new PutTermsPreferenzeContattiResponse();
		List<Errore> errori = new ArrayList<Errore>();
		LogGeneralDaoBean logGeneralDaoBean = null;
		try {
			//Validazione credenziali
			errori = contattiServiceValidator.validateCredenziali(wsContext, errori, Constants.PUT_TERMS_PREFERENZE_CONTATTI,
					className);
			if (errori != null && !errori.isEmpty()) {
				response = new PutTermsPreferenzeContattiResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}
			//Log start
			logGeneralDaoBean = createLogBeanDao.prepareLogBeanStart(parameters,this.getEncryption_key());
			logGeneralDaoMed.logStart(logGeneralDaoBean, BaseValidator.CODICE_LOG_START,operation(NOME_OPERATION_PUT_TERMS_PREFERENZE_CONTATTI));
			
			//Validazione campi obbligatori e codifiche
			errori = contattiServiceValidator.validateputTermsPreferenzeContatti(parameters, errori, logGeneralDaoBean);
			if (errori != null && !errori.isEmpty()) {
				response = new PutTermsPreferenzeContattiResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}

			//Validazione paziente
//			PazienteLowDto pazienteLowDto = contattiServiceValidator.verificaPaziente(parameters.getCodiceFiscalePaziente(), errori);
//			if (pazienteLowDto == null){
//				response = new PutTermsPreferenzeContattiResponse(errori, RisultatoCodice.FALLIMENTO);
//				return response;
//			}

			//logica dao
			errori = putTermsPreferenzeContattiDao.putTermsPreferenzeContatti(parameters, errori);
			if (errori != null &&  !errori.isEmpty()) {
				response = new PutTermsPreferenzeContattiResponse(errori, RisultatoCodice.FALLIMENTO);
				return response;
			}

			response.setEsito(RisultatoCodice.SUCCESSO);


		} catch(Exception e) {
			logger.error("[ContattiService::putTermsPreferenzeContatti] ", e);
			errori.add(logGeneralDaoMed.getErroreCatalogo(BaseValidator.ERRORE_INTERNO, Constants.PUT_TERMS_PREFERENZE_CONTATTI));
			response = new PutTermsPreferenzeContattiResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			logGeneralDaoMed.logEnd(logGeneralDaoBean, response.getEsito().getValue(),
					Utils.xmlMessageFromObject(response),response.getErrori(),BaseValidator.LOG_RISPOSTA_SERVIZIO,
					null,null,null,NOME_SERVIZIO,
					NOME_OPERATION_PUT_TERMS_PREFERENZE_CONTATTI,response.getEsito());
		}
		return response;
	}
	
	/**
	 * @return
	 */
	public String operation(String nomeOperation) {
		return NOME_SERVIZIO+"."+
				nomeOperation;
	}

	public CreateLogBeanDao getCreateLogBeanDao() {
		return createLogBeanDao;
	}

	public void setCreateLogBeanDao(CreateLogBeanDao createLogBeanDao) {
		this.createLogBeanDao = createLogBeanDao;
	}

	public LogGeneralDao getLogGeneralDaoMed() {
		return logGeneralDaoMed;
	}

	public void setLogGeneralDaoMed(LogGeneralDao logGeneralDaoMed) {
		this.logGeneralDaoMed = logGeneralDaoMed;
	}

	public String getEncryption_key() {
		return encryption_key;
	}

	public void setEncryption_key(String encryption_key) {
		this.encryption_key = encryption_key;
	}

	public ContattiServiceValidator getContattiServiceValidator() {
		return contattiServiceValidator;
	}

	public void setContattiServiceValidator(ContattiServiceValidator contattiServiceValidator) {
		this.contattiServiceValidator = contattiServiceValidator;
	}

	public VerificaOtpDao getVerificaOtpDao() {
		return verificaOtpDao;
	}

	public void setVerificaOtpDao(VerificaOtpDao verificaOtpDao) {
		this.verificaOtpDao = verificaOtpDao;
	}
	public GeneraOtpDao getGeneraOtpDao() {
		return generaOtpDao;
	}

	public void setGeneraOtpDao(GeneraOtpDao generaOtpDao) {
		this.generaOtpDao = generaOtpDao;
	}

	public GetPreferenzeDao getGetPreferenzeDao() {
		return getPreferenzeDao;
	}

	public void setGetPreferenzeDao(GetPreferenzeDao getPreferenzeDao) {
		this.getPreferenzeDao = getPreferenzeDao;
	}

	public PutTermsPreferenzeContattiDao getPutTermsPreferenzeContattiDao() {
		return putTermsPreferenzeContattiDao;
	}

	public void setPutTermsPreferenzeContattiDao(PutTermsPreferenzeContattiDao putTermsPreferenzeContattiDao) {
		this.putTermsPreferenzeContattiDao = putTermsPreferenzeContattiDao;
	}
}
