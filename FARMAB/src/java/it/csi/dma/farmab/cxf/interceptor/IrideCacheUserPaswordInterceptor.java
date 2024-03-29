/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.dmadd.log.dao.LogGeneralDao;
import it.csi.dma.dmafarma.ElencoRicetteFarmaciaRequest;
import it.csi.dma.dmafarma.GetDelegantiFarmaciaRequest;
import it.csi.dma.dmairidecache.IdentificaUserPasswordPin;
import it.csi.dma.dmairidecache.IdentificaUserPasswordPinResponse;
import it.csi.dma.dmairidecache.IrideCache;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.integration.dao.FarmabTLogDao;
import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.SecurityUtil;


@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IrideCacheUserPaswordInterceptor extends SoapHeaderInterceptor{

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	IrideCache irideService;

	@Autowired
	FarmabLog farmabLog;
	@Autowired
	LogGeneralDao daoLog;


	@Autowired
	SecurityUtil securityUtil;

	@Autowired
	FarmabTLogDao farmabTLogDao;

	public IrideCacheUserPaswordInterceptor() {
		super();
	}


	@Override
	public void handleMessage(Message message) throws Fault {
		log.info("IrideCacheUserPaswordInterceptor::handleMessage");
		GetDelegantiFarmaciaRequest getDelegantiFarmaciaRequest= new GetDelegantiFarmaciaRequest();
		ElencoRicetteFarmaciaRequest elencoRicetteFarmaciaRequest = new ElencoRicetteFarmaciaRequest();
		String erroreCifratura=null;
		String wso2_id = (String) message.getExchange().get(Constants.LOGGING_WSO2ID);
		long idLMessaggi;

		try {

			//List<Errore> errori= new ArrayList<Errore>();

			 AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);

			  // If the policy is not set, the user did not specify credentials.
			  // 401 is sent to the client to indicate that authentication is required.
			  if (policy == null) {
				  log.error("Policy non ricevute!");
			     sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
			  return;
			  }
			  log.info(message);

			  String user = policy.getUserName();
			  String password = policy.getPassword();

			  //SoapMessage messageRequest = (SoapMessage) message;
			  //SoapVersion soapVersion = messageRequest.getVersion();

			  MessageContentsList parameters = MessageContentsList.getContentsList(message);

			  //cleanUpOutInterceptors(message);

			  Long id = (Long)message.getExchange().get(Constants.LOGGING_KEY_L_XML_MESSAGGI);
			  log.info("IrideCacheUserPaswordInterceptor::handleMessage id: " + id);

			  if(parameters.get(0) instanceof GetDelegantiFarmaciaRequest) {
				  log.info("instanceof GetDelegantiFarmaciaRequest");
				  getDelegantiFarmaciaRequest= (GetDelegantiFarmaciaRequest)parameters.get(0);

				  // Gestione log su db
				  if(getDelegantiFarmaciaRequest!=null && getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente()!=null) {
					  idLMessaggi=traceOnLMessaggi(wso2_id, "getDelegantiFarmacia",getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getApplicazione(), null, getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista(), getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getRuolo(), getDelegantiFarmaciaRequest.getCfCittadinoDelegato(),getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getApplicativoVerticale());
				  } else {
					  idLMessaggi=traceOnLMessaggi(wso2_id, "getDelegantiFarmacia","NULLO",null,null,null,null,null);
				  }
				  //usato per update
				  message.getExchange().put(Constants.LOGGING_KEY_L_MESSAGGI, new Long(idLMessaggi));

				  String pinCode = getDelegantiFarmaciaRequest.getPinCode();
				  String cfFarmacista = getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista();

				  log.debug("USER AUTH: "+user);
				  log.debug("PASSWORD AUTH: "+password);
				  log.debug("PIN CODE cifrato:"+pinCode);
				  String pinCodeDecript="";
				  try {
					  pinCodeDecript=SecurityUtil.decrypt(pinCode);
					  log.debug("PIN CODE decifrato:"+pinCodeDecript);
				  }catch (Exception ex) {
					  erroreCifratura=new String(Constants.FAR_CC_0076);
					  throw ex;
				  }
				  String risultato =identificaUserPasswordPIN (pinCodeDecript, password, user, cfFarmacista);
				  log.debug("risultato: "+risultato);
				  getDelegantiFarmaciaRequest.setPinCode(risultato);
			  }
			  else if(parameters.get(0) instanceof ElencoRicetteFarmaciaRequest) {
				  log.info("instanceof ElencoRicetteFarmaciaRequest");
				  elencoRicetteFarmaciaRequest= (ElencoRicetteFarmaciaRequest)parameters.get(0);

				  // Gestione log su db
				  if(elencoRicetteFarmaciaRequest!=null && elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente()!=null) {
					  idLMessaggi=traceOnLMessaggi(wso2_id, "elencoRicetteFarmacia",elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente().getApplicazione(),elencoRicetteFarmaciaRequest.getCfAssistito(),elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista(),elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente().getRuolo(),elencoRicetteFarmaciaRequest.getCfCittadinoDelegato(),elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente().getApplicativoVerticale());
				  } else {
					  idLMessaggi=traceOnLMessaggi(wso2_id, "elencoRicetteFarmacia","NULLO",null,null,null,null,null);
				  }
				  //usato per update
				  message.getExchange().put(Constants.LOGGING_KEY_L_MESSAGGI, new Long(idLMessaggi));

				  String pinCode = elencoRicetteFarmaciaRequest.getPinCode();
				  String cfFarmacista = elencoRicetteFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista();

				  log.debug("USER AUTH: "+user);
				  log.debug("PASSWORD AUTH: "+password);
				  log.debug("PIN CODE cifrato: "+pinCode);
				  String pinCodeDecript="";

				  try {
					  pinCodeDecript=SecurityUtil.decrypt(pinCode);
					  log.debug("PIN CODE decifrato:"+pinCodeDecript);
				  }catch (Exception ex) {
					  erroreCifratura=new String(Constants.FAR_CC_0076);
					  throw ex;
				  }

				  String risultato =identificaUserPasswordPIN (pinCodeDecript, password, user, cfFarmacista);
				  log.info("risultato: "+risultato);
				  elencoRicetteFarmaciaRequest.setPinCode(risultato);
			  }
			  else {
				  //AD OGGI ESISTONO SOLO DUE METODI: IN CASO DI AGGIUNTE NON PREVISTE ESCE
				  throw new Fault((org.apache.cxf.common.i18n.Message) message);
			  }
		}catch(Fault f) {
			log.error(f.getMessage());
			if (getDelegantiFarmaciaRequest!=null) {
				getDelegantiFarmaciaRequest.setPinCode(Constants.AUTH_ER_505);
			}
			if(elencoRicetteFarmaciaRequest!=null) {
				elencoRicetteFarmaciaRequest.setPinCode(Constants.AUTH_ER_505);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			if (erroreCifratura==null) {
				getDelegantiFarmaciaRequest.setPinCode(Constants.AUTH_ER_505);
			} else {
				getDelegantiFarmaciaRequest.setPinCode(erroreCifratura);
			}
			if(erroreCifratura==null) {
				elencoRicetteFarmaciaRequest.setPinCode(Constants.AUTH_ER_505);
			} else {
				elencoRicetteFarmaciaRequest.setPinCode(erroreCifratura);
			}
		}

	}

/*	protected void cleanUpOutInterceptors(Message outMessage) {

		  Iterator<Interceptor<? extends Message>> iterator = outMessage.getInterceptorChain().iterator();
		  while (iterator.hasNext()) {
		    Interceptor<? extends Message> inInterceptor = iterator.next();
		    if (inInterceptor.getClass().equals(SoapActionInInterceptorUuidCodSessione.class)
		        ) {
		      outMessage.getInterceptorChain().remove(inInterceptor);
		    }
		  }
		}
	*/

	private void sendErrorResponse(Message message, int responseCode) {
		   Message outMessage = getOutMessage(message);
		   outMessage.put(Message.RESPONSE_CODE, responseCode);

		   // Set the response headers
		   @SuppressWarnings("unchecked")
		   Map<String, List<String>> responseHeaders =  (Map<String, List<String>>)    message.get(Message.PROTOCOL_HEADERS);

		   if (responseHeaders != null) {
		     responseHeaders.put("WWW-Authenticate", Arrays.asList(new String[] { "Basic realm=realm" }));
		     responseHeaders.put("Content-Length", Arrays.asList(new String[] { "0" }));
		  }
		  message.getInterceptorChain().abort();
		   try {
		    getConduit(message).prepare(outMessage);
		   close(outMessage);
		   } catch (IOException e) {
		      e.printStackTrace();
		   }
	}

	private Message getOutMessage(Message inMessage) {
		   Exchange exchange = inMessage.getExchange();
		   Message outMessage = exchange.getOutMessage();
		   if (outMessage == null) {
		    Endpoint endpoint = exchange.get(Endpoint.class);
		    outMessage = endpoint.getBinding().createMessage();
		    exchange.setOutMessage(outMessage);
		   }
		    outMessage.putAll(inMessage);
		     return outMessage;
	}

	private Conduit getConduit(Message inMessage) throws IOException {
		   Exchange exchange = inMessage.getExchange();
		   EndpointReferenceType target = exchange.get(EndpointReferenceType.class);
		   Conduit conduit = exchange.getDestination().getBackChannel(inMessage, null, target);
		   exchange.setConduit(conduit);
		   return conduit;
	}

	private void close(Message outMessage) throws IOException {
		   OutputStream os = outMessage.getContent(OutputStream.class);
		   os.flush();
		   os.close();
	}

	private String identificaUserPasswordPIN (String pinCode, String password, String user, String cfFarmacista) {
		log.info("1.IrideCacheUserPaswordInterceptor::identificaUserPasswordPIN");
		String risultato = Constants.AUTH_ER_501;// Agiunto per non andare in errore quando si presenta un errrore del tipo -> Utenza bloccata per superamento limite numero tentativi (3).Sblocco previsto fra 10 secondi.

		//MANCANTE: CRIPTARE E DECRIPTARE IL PIN

		IdentificaUserPasswordPin identificaUserPasswordPin = new IdentificaUserPasswordPin();
		identificaUserPasswordPin.setPassword(password);
		identificaUserPasswordPin.setPin(pinCode);
		identificaUserPasswordPin.setUser(user);


		try {
			long startTime = System.currentTimeMillis();
			log.info("RICHIAMO SERVIZIO ESTERNO IrideCacheUserPaswordInterceptor.identificaUserPasswordPin");
			IdentificaUserPasswordPinResponse identificaUserPasswordPinResponse =irideService.identificaUserPasswordPin(identificaUserPasswordPin);
			log.info("RISPOSTA SERVIZIO ESTERNO IrideCacheUserPaswordInterceptor.identificaUserPasswordPin:"+(System.currentTimeMillis()-startTime)+" Millis");

			if(identificaUserPasswordPinResponse!=null) {
				if(identificaUserPasswordPinResponse.getIdentitaIride()!=null) {

					String codiceFiscaleIride = identificaUserPasswordPinResponse.getIdentitaIride().getCodFiscale();
					log.info("codiceFiscaleIride: "+codiceFiscaleIride);
					log.info("cfFarmacista: "+cfFarmacista);

					//MANCANTE: CONTROLLO SOSPESO: PARLARE CON NICOLA
					/*if(codiceFiscaleIride != cfFarmacista) {
						risultato=Constants.FAR_CC_0061; CODICE DI ERRORE DA DEFINIRE
					}else {
						risultato="SUCCESSO";
					}	*/
					risultato="SUCCESSO";
					log.info("OK.IrideCacheUserPaswordInterceptor::identificaUserPasswordPIN risultato="+risultato);
				}

			}
		} catch (SOAPFaultException e) {
			String messaggioErrore = e.getMessage();
			log.info("SOAP FAULT MESSAGE: "+e.getMessage());
			risultato = Constants.AUTH_ER_501;
			if (StringUtils.isNotEmpty(messaggioErrore)) {
				if(messaggioErrore.equalsIgnoreCase("MalformedUsernameException") || messaggioErrore.equalsIgnoreCase("AuthException")){
					//CREDENZIALI INVALIDE
					risultato=Constants.AUTH_ER_502;
				}
				else if(messaggioErrore.equalsIgnoreCase("PasswordExpiredException")) {
					//MANCANTE: SCRIVERE IN DMACC_L_MESSAGGI e DMACC_L_ERRORI
					//NELLA TABELLA DMACC_L_ERRORI SCRIVERE PASSWORD SCADUTA: AUTH_ER_503
					//A VIDEO RESTITUIRE CREDENZIALI INVALIDE
					risultato=Constants.AUTH_ER_502;
				}
				else if(messaggioErrore.equalsIgnoreCase("InactiveAccountException")) {
					//MANCANTE: SCRIVERE IN DMACC_L_MESSAGGI e DMACC_L_ERRORI
					//NELLA TABELLA DMACC_L_ERRORI SCRIVERE ACCOUNT INATTIVO: AUTH_ER_504
					//A VIDEO RESTITUIRE CREDENZIALI INVALIDE
					risultato=Constants.AUTH_ER_504;//ex 502
				}
				else if(messaggioErrore.equalsIgnoreCase("InternalException") || messaggioErrore.equalsIgnoreCase("SystemException") || messaggioErrore.equalsIgnoreCase("IdProviderNotFoundException")){
					//ERRORE AUTENTICAZIONE
					risultato=Constants.AUTH_ER_501;
				}
				//Utenza bloccata per superamento limite numero tentativi (3).Sblocco previsto fra X secondi.
				else if (messaggioErrore.toLowerCase().contains("tentativi")) {
					risultato=Constants.AUTH_ER_506;
				}
				//gestione Exception come 505
				else if(messaggioErrore.equalsIgnoreCase("Exception")){
					risultato=Constants.AUTH_ER_505;
				}

			}
			// nel caso non sia nessuno dei precedenti restituiamo un AUTH_ER_501
			log.info("SOAPFaultException.IrideCacheUserPaswordInterceptor::identificaUserPasswordPIN risultato="+risultato);

		}
		catch (Exception e) {
			//SERVIZIO AUTENTICAZIONE NON DISPONIBILE
			risultato=Constants.AUTH_ER_505;
		}
		//log.info("RESPONSE.IrideCacheUserPaswordInterceptor::identificaUserPasswordPIN risultato="+risultato);
		return risultato;
	}

	public IrideCache getIrideService() {
		return irideService;
	}

	public void setIrideService(IrideCache irideService) {
		this.irideService = irideService;
	}

	public long traceOnLMessaggi(String wso2Id, String servizioXML, String applicazione, String cfAssistito, String cfFarmacista, String ruolo, String cfCittadinoDelegato, String applicativoVerticale) {
		log.info("IrideCacheUserPaswordInterceptor::traceOnLMessaggi");
		//id da usare nell'update della l_messaggi da assegnare dopo la insert
		Long idLMessaggi = null;

		LMessaggiDto messaggio= new LMessaggiDto();
		messaggio.setWso2Id(wso2Id);
		messaggio.setUuid(messaggio.getWso2Id());
		messaggio.setServizioXml(servizioXML);
		messaggio.setChiamante(applicazione);
		//in analisi c'Ã¨ scritto di mettere null per applicativoVerticale
		try {
			if(cfAssistito!=null && !cfAssistito.trim().isEmpty()) {
				messaggio.setCfAssistito(SecurityUtil.decrypt(cfAssistito));
			}

		} catch (Exception e) {
			log.debug("Non riesco a decifrare il cfAssistito");
			messaggio.setCfAssistito("Cf non decifrabile");
		}
		messaggio.setCfUtente(cfFarmacista);
		messaggio.setRuoloUtente(ruolo);
		idLMessaggi=farmabTLogDao.insertLMessaggi(messaggio);
		return idLMessaggi;
	}

}
