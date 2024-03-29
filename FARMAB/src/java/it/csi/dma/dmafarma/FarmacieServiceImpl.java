/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmafarma;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.csi.consensoini.dma.RichiedenteExt;
import it.csi.consensoini.dma.StatoConsensiIN;
import it.csi.consensoini.dma.StatoConsensiOUT;
import it.csi.consensoini.dmacc.CCConsensoINIExtServicePortType;
import it.csi.consensoini.dmacc.StatoConsensiExtRequeste;
import it.csi.consensoini.dmacc.StatoConsensiResponse;
import it.csi.dma.dmadd.audit.exceptions.CatalogoLogAuditLowDaoException;
import it.csi.dma.dmadd.deleghebe.DelegaCittadino;
import it.csi.dma.dmadd.deleghebe.DelegaCittadino.Deleghe;
import it.csi.dma.dmadd.deleghebe.DelegaServizio;
import it.csi.dma.dmadd.deleghebe.GetDelegantiResponse;
import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabFarmOccasionaliController;
import it.csi.dma.farmab.controller.FarmabFarmacieAbitualiController;
import it.csi.dma.farmab.controller.FarmabFarmacieServiceController;
import it.csi.dma.farmab.controller.FarmabGestioneDeviceController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTFarmaciaOccasionaleRich;
import it.csi.dma.farmab.domain.DmaccTabelleIdx;
import it.csi.dma.farmab.integration.dao.FarmabTLogDao;
import it.csi.dma.farmab.integration.dao.LogAuditDao;
import it.csi.dma.farmab.integration.dao.dto.AuditDto;
import it.csi.dma.farmab.integration.dao.dto.CatalogoLogAuditLowDto;
import it.csi.dma.farmab.service.DelegheElencoServizi;
import it.csi.dma.farmab.service.RicercaPazienteServizi;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;
import it.csi.dma.farmab.util.SecurityUtil;
import it.csi.iccws.dmacc.Errori;
import it.csi.iccws.dmacc.Farmacia;
import it.csi.iccws.dmacc.FarmaciaService;
import it.csi.iccws.dmacc.RisultatoCodice;
import it.csi.iccws.dmacc.VerificaFarmacistaRequest;
import it.csi.iccws.dmacc.VerificaFarmacistaResponse;
import it.csi.ricercadocumentiini.dma.CfAssistitoType;
import it.csi.ricercadocumentiini.dma.ElencoCFAssistitoType;
import it.csi.ricercadocumentiini.dma.RicercaDocumentiIN;
import it.csi.ricercadocumentiini.dma.Richiedente;
import it.csi.ricercadocumentiini.dmacc.RicercaDocumentiINIService;
import it.csi.ricercadocumentiini.dmacc.RicercaDocumentiRequeste;
import it.csi.ricercadocumentiini.dmacc.RicercaDocumentiResponse;
import it.csi.ricercapaziente.dma.Paziente;
import it.csi.ricercapaziente.dmaccbl.RicercaPazienteResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FarmacieServiceImpl implements FarmacieService {
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private DelegheElencoServizi delegheElencoServizi;

	private RicercaPazienteServizi ricercaPazienteServizi;

	@Autowired
	FarmabLog farmabLog;

	@Autowired
	FarmabFarmacieAbitualiController farmabFarmacieAbitualiController;

	@Autowired
	FarmabGestioneDeviceController farmabGestioneDeviceController;

	@Autowired
	FarmabFarmOccasionaliController farmabFarmOccasionaliController;

	@Autowired
	FarmabFarmacieServiceController farmabFarmacieServiceController;

	@Autowired
	FarmaciaService farmacieServiceLCCE;

	@Autowired
	CCConsensoINIExtServicePortType statoConsensiINI;

	@Autowired
	SecurityUtil securityUtil;

	@Autowired
	LogAuditDao logAudit;

	@Autowired
	FarmabController farmabController;

	//@Autowired
	RicercaDocumentiINIService ricercaDocumentiINIService;

	@Autowired
	ApplicationContext context;

	@Resource
	protected WebServiceContext wsContext;

	@Autowired
	FarmabTLogDao farmabTLogDao;

	//usare @DependsOn
	@PostConstruct
	public void init () {
		//pork around per un problema di caricamento del bean all'interno del context, DA RISOLVERE e eliminare Retrieving Bean by Name
		ricercaDocumentiINIService= (RicercaDocumentiINIService) context.getBean("ricercaDocumentiINIService");
	}

	/******************************************************************************************************************
	 * GET DELEGANTI FARMACIA
	 ******************************************************************************************************************/
	@Override
	public GetDelegantiFarmaciaResponse getDelegantiFarmacia(GetDelegantiFarmaciaRequest getDelegantiFarmaciaRequest) {
		log.info("GetDelegantiFarmaciaServiceImpl::getDelegantiFarmacia");

		List<Errore> errori=new ArrayList<Errore>();

		GetDelegantiFarmaciaResponse response=new GetDelegantiFarmaciaResponse();
		response.setCodEsito(Constants.FAIL_CODE_9999);
		GetDelegantiResponse responseDeleghe= null;

		//1 IRIDE: identificaUserPasswordPIN
		//VIENE CHIAMATO DALL'INTERCEPTOR: IrideCacheUserPaswordInterceptor
		//ESITO identificaUserPasswordPIN
		log.debug("pin code dopo FILTRO: "+getDelegantiFarmaciaRequest.getPinCode());
		String risultatoChiamataIride = getDelegantiFarmaciaRequest.getPinCode();
		if(Constants.SUCCESS_CODE.equalsIgnoreCase(risultatoChiamataIride)) {
			//NON FA NULLA E PROSEGUE
		}else {
			Errore e= new Errore();
			e.setCodErrore(risultatoChiamataIride);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
		}

		if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setCodEsito(Constants.FAIL_CODE_9999);
				ElencoErrori elencoErrori= new ElencoErrori();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
		}

		//2 LOG CHIAMATA AL SERVIZIO: TO DO
		errori=ValidateRequestGetDeleganti(getDelegantiFarmaciaRequest);
		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setCodEsito(Constants.FAIL_CODE_9999);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}

		//3 Chiama servizio LCCE.VerificaFarmacista
		try {
			VerificaFarmacistaResponse verFarResp = verificaFarmacistaLCCE(getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getCodFarmacia(), getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getPIvaFarmacia(), getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista());
			//VALORIZZAZIONE DESCRIZIONE ERRORE CON ERRORE PROVENIENTE DAL SERVIZIO LCCE
			RisultatoCodice risultatoCodice= verFarResp.getEsito();
			log.info("risultatoCodice: "+risultatoCodice);

			if(Constants.FAIL_CODE.equalsIgnoreCase(risultatoCodice.value())) {
				Errori erroriLCCE = verFarResp.getErrori();
				List <it.csi.iccws.dma.Errore> erroreLCCE= erroriLCCE.getErrore();
				if(erroreLCCE!=null && !erroreLCCE.isEmpty() && erroreLCCE.size()>0) {
					Errore e =new Errore();
					e.setCodErrore(erroreLCCE.get(0).getCodice());
					e.setDescErrore(erroreLCCE.get(0).getDescrizione());
					log.info("DOPO DEL SET DESCRIZIONE: "+erroreLCCE.get(0).getDescrizione());
					errori.add(e);
				}
			}
		}catch (Exception ex) {
			log.error(ex.getMessage());
			Errore e =new Errore();
			e.setCodErrore(Constants.FAR_CC_FATAL);
			e.setDescErrore("Fallimento chiamata LCCE");
			errori.add(e);
		}
		if(!errori.isEmpty()) {
					//restituisco fallimento con i controlli di business
					response.setCodEsito(Constants.FAIL_CODE_9999);
					ElencoErrori elencoErrori= new ElencoErrori();
					elencoErrori.errore=errori;
					response.setElencoErrori(elencoErrori);
					return response;
					//CON ESITO NEGATIVO verificaFarmacistaLCCE IL FLUSSO SI FERMA
		}

		//errori di Business
		try {
			//LANCIA LA CHIAMATA A DELEGHE E LE VERIFICHE
			responseDeleghe=verificaRispostaDelegheGetDeleganti(getDelegantiFarmaciaRequest, errori);
			log.info("RESPONSE DELEGHE ESITO: "+responseDeleghe.getEsito());

			if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setCodEsito(Constants.FAIL_CODE_9999);
				ElencoErrori elencoErrori= new ElencoErrori();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
			}

			//nessun errore: SUCCESSO
			response =prepareSuccessResponse(responseDeleghe);
			//Inserire qui il log di audit
			if(Constants.SUCCESS_CODE_0000.equalsIgnoreCase(response.getCodEsito())){
				try {
					final String NOME_SERVIZIO = "getDelegantiFarmacia";
					final String CODICE_SERVIZIO = "GET_DEL_FARM";
					Long idIrec = null;
					try {
						//idIrec = farmabController.getIdIrecPaziente(getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente().getCfFarmacista());
						//Jira DMA-3784 --> mettere come id paziente l'id del cittadino delegato
						if(!StringUtils.isEmpty(getDelegantiFarmaciaRequest.getCfCittadinoDelegato())){
							idIrec = farmabController.getIdIrecPaziente(getDelegantiFarmaciaRequest.getCfCittadinoDelegato());
						}

					}catch(Exception e) {
						log.info("Cittadino fuori regione:"+getDelegantiFarmaciaRequest.getCfCittadinoDelegato());
						idIrec=null;
					}
					logAudit.insertTlogAudit(getLogAudit(Constants.CATALOG_GET_DEL_FAR,
							getDelegantiFarmaciaRequest.getDatiFarmaciaRichiedente(),
							getDelegantiFarmaciaRequest.cfCittadinoDelegato, idIrec, NOME_SERVIZIO, CODICE_SERVIZIO, null ));
				} catch (CatalogoLogAuditLowDaoException e) {
					log.error(e.getMessage());
				}
			}

		}catch (Exception ex) {
			log.error(ex.getMessage());
			response.setCodEsito(Constants.FAIL_CODE_9999);
		}

		return response;
	}

	private GetDelegantiResponse verificaRispostaDelegheGetDeleganti(GetDelegantiFarmaciaRequest requestDelegantiFarmacia,List<Errore> errore) throws Exception {
		//recupero il wso2id
		String wso2id = getWso2Id(wsContext.getMessageContext());;

		GetDelegantiResponse delegaResponse=getDelegheElencoServizi().getDelegantiFarmacia(requestDelegantiFarmacia, wso2id);

    	if(delegaResponse!=null && Constants.SUCCESS_CODE.equalsIgnoreCase(delegaResponse.getEsito())) {
    		log.info("Esito della chiamata a deleghe="+delegaResponse.getEsito());

			if(delegaResponse.getDeleganti()!=null && delegaResponse.getDeleganti().getDelegante()!=null) {

				List <DelegaCittadino> delegheCittadino =delegaResponse.getDeleganti().getDelegante();
				if(delegheCittadino != null && !delegheCittadino.isEmpty()) {

					delegheCittadino.forEach(delegaCittadino -> {
						Deleghe deleghe = delegaCittadino.getDeleghe();
						List <DelegaServizio> delegheServizio =deleghe.getDelega();

						delegheServizio.forEach(delegaServizio -> {
							String gradoDelega=null;

							if(Constants.CODICE_SERVIZIO_IN_DELEGHE.equalsIgnoreCase(delegaServizio.getCodiceServizio())) {
								if("ATTIVA".equalsIgnoreCase(delegaServizio.getStatoDelega()))  {
									if(delegaServizio.getGradoDelega()== null || delegaServizio.getGradoDelega().trim().isEmpty()) {
										gradoDelega=Constants.GRADO_DELEGA;
									} else {
										gradoDelega=delegaServizio.getGradoDelega();
									}
									//CONTROLLO AUTORIZZAZIONE DEL RICHIEDENTE
									if(gradoDelega.length()>1) {
										//verifico i dati sulla tabella dmacc_r_autorizzazione_deleghe
										List<Long> ids=farmabFarmacieAbitualiController.getIdVerificaAutorizDeleghe(requestDelegantiFarmacia.getDatiFarmaciaRichiedente().getRuolo(), gradoDelega, "GET_DEL_FAR");
										if(ids==null || ids.size()<1) {
											delegaResponse.setEsito(Constants.FAIL_CODE);
											Errore e= new Errore();
							    			e.setCodErrore(Constants.FAR_CC_0058);
							    			String messaggioErrore = farmabLog.findMesaggiErrore(e.getCodErrore());
							    			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, e.getCodErrore());
							    			e.setDescErrore(messaggioErroreCompleto);
							    			errore.add(e);
										}
									} else {
										delegaResponse.setEsito(Constants.FAIL_CODE);
										Errore e= new Errore();
						    			e.setCodErrore(Constants.FAR_CC_0074);
						    			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0074);
						    			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.FAR_CC_0074);
						    			e.setDescErrore(messaggioErroreCompleto);
						    			errore.add(e);
									}
								}
							}
						});
				      });

				}
			}else{
				log.info("DELEGA RESPONSE getDeleganti NULLA");
				delegaResponse.setEsito(Constants.FAIL_CODE);
				Errore e= new Errore();
    			e.setCodErrore(Constants.FAR_CC_0074);
    			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0074);
    			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.FAR_CC_0074);
    			e.setDescErrore(messaggioErroreCompleto);
    			errore.add(e);
			}
		} else {
			//TODO errore nel richaime del servizio deleghe
			delegaResponse.setEsito(Constants.FAIL_CODE);
			Errore e= new Errore();
			e.setCodErrore(Constants.FAR_CC_0057);
			String messaggioErrore = farmabLog.findMesaggiErrore(e.getCodErrore());
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, e.getCodErrore());
			e.setDescErrore(messaggioErroreCompleto);
			errore.add(e);
		}

		return delegaResponse;
    }

	private List<Errore> ValidateRequestGetDeleganti(GetDelegantiFarmaciaRequest request){
		List<Errore> errori= new ArrayList<Errore>();

		errori=this.ValidateDatiFarmacistaDeleganti(request);

		//--------------------------------------------------------------------------
		//controlli per tag cfCittadino delegato
		if(request.getCfCittadinoDelegato() ==null || request.getCfCittadinoDelegato().isEmpty()) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		if(FarmabUtils.isNotValidCf(request.getCfCittadinoDelegato())) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0051);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0051, request.getCfCittadinoDelegato());
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag cfCittadino delegato
		//--------------------------------------------------------------------------
		//controlli per tag PIN CODE
		if(request.getPinCode()==null || request.getPinCode().isEmpty()) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.PIN_CODE_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag PIN CODE
		//--------------------------------------------------------------------------

		return errori;
	}

	private List<Errore> ValidateDatiFarmacistaDeleganti(GetDelegantiFarmaciaRequest request) {
		List<Errore> errori= new ArrayList<Errore>();

		if (request!=null) {
			//controlli per Presenza dati farmacista
			if (request.getDatiFarmaciaRichiedente() != null) {
				//controlli per tag codiceFiscale FARMACISTA
				if (request.getDatiFarmaciaRichiedente().getCfFarmacista().isEmpty() || request.getDatiFarmaciaRichiedente().getCfFarmacista().trim().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				if (FarmabUtils.isNotValidCf(request.getDatiFarmaciaRichiedente().getCfFarmacista())) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0051);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0051, request.getDatiFarmaciaRichiedente().getCfFarmacista());
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//end controlli per tag codiceFiscale FARMACISTA
				//--------------------------------------------------------------------------
				//controlli per tag applicazione
				if(request.getDatiFarmaciaRichiedente().getApplicazione().isEmpty()||request.getDatiFarmaciaRichiedente().getApplicazione().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!request.getDatiFarmaciaRichiedente().getApplicazione().contains("GESTFARM")) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050, request.getDatiFarmaciaRichiedente().getApplicazione());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}
				//end controlli per tag applicazione
				//--------------------------------------------------------------------------
				//controlli per tag applicativoVerticale
				if(request.getDatiFarmaciaRichiedente().getApplicativoVerticale().isEmpty()||request.getDatiFarmaciaRichiedente().getApplicativoVerticale().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICATIVO_VERTICALE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!request.getDatiFarmaciaRichiedente().getApplicativoVerticale().contains("FARAB")) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICATIVO_VERTICALE_0050, request.getDatiFarmaciaRichiedente().getApplicativoVerticale());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}
				//end controlli per tag applicativoVerticale
				//--------------------------------------------------------------------------
				//controlli per tag ruolo
				if(request.getDatiFarmaciaRichiedente().getRuolo().isEmpty()||request.getDatiFarmaciaRichiedente().getRuolo().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!request.getDatiFarmaciaRichiedente().getRuolo().contains("FAR")) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050, request.getDatiFarmaciaRichiedente().getRuolo());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}
				//end controlli per tag ruolo
				//--------------------------------------------------------------------------
				//controlli per tag COD FARMACIA
				if(request.getDatiFarmaciaRichiedente().getCodFarmacia()==null || request.getDatiFarmaciaRichiedente().getCodFarmacia().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_FARMACIA_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag COD FARMACIA
				//--------------------------------------------------------------------------
				//controlli per tag PIVA FARMACIA
				if(request.getDatiFarmaciaRichiedente().getPIvaFarmacia()==null || request.getDatiFarmaciaRichiedente().getPIvaFarmacia().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.PIVA_FARMACIA_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag PIVA FARMACIA
				//--------------------------------------------------------------------------
				//controlli per tag GESTIONALE
				if(request.getDatiFarmaciaRichiedente().getGestionale()==null || request.getDatiFarmaciaRichiedente().getGestionale().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.GESTIONALE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag GESTIONALE
				//--------------------------------------------------------------------------
			} else {
				Errore e =new Errore();
				e.setCodErrore(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
				e.setDescErrore(messaggioErroreCompleto);
				errori.add(e);
			}
			//end controlli per Presenza dati farmacista
			//--------------------------------------------------------------------------
		} else {
			Errore e =new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}

		return errori;
	}

	//GET E SET PER CARICARE LA CLASSE DelegheElencoServizi
	public DelegheElencoServizi getDelegheElencoServizi() {
		return delegheElencoServizi;
	}

	public void setDelegheElencoServizi(DelegheElencoServizi delegheElencoServizi) {
		this.delegheElencoServizi = delegheElencoServizi;
	}

	//RESPONSE: SUCCESS
	private GetDelegantiFarmaciaResponse prepareSuccessResponse(GetDelegantiResponse responseDeleghe){

		GetDelegantiFarmaciaResponse response=new GetDelegantiFarmaciaResponse();
		ElencoDeleganti elencoDeleganti = new ElencoDeleganti();


		List<Delegante> listaDeleganti= elencoDeleganti.getDelegante();
		for (DelegaCittadino delegaCittadino :responseDeleghe.getDeleganti().getDelegante()) {
			Delegante deleganteResponse= new Delegante();

			deleganteResponse.setCodiceFiscale(delegaCittadino.getCodiceFiscale());
			deleganteResponse.setCognome(delegaCittadino.getCognome());
			deleganteResponse.setDataNascita(delegaCittadino.getDataDiNascita());
			deleganteResponse.setLuogoNascita(delegaCittadino.getLuogoNascita());
			deleganteResponse.setNome(delegaCittadino.getNome());
			deleganteResponse.setSesso(delegaCittadino.getSesso());

			listaDeleganti.add(deleganteResponse);

			//SOLO PER SVILUPPO
			//stampaResponse(deleganteResponse);
		}
		elencoDeleganti.delegante=listaDeleganti;
		response.elencoDeleganti=elencoDeleganti;
		response.setCodEsito(Constants.SUCCESS_CODE_0000);

		return response;
	}

	private void stampaResponse(Delegante deleganteResponse) {
		log.info("DELEGANTE CF: "+deleganteResponse.getCodiceFiscale());
		log.info("DELEGANTE COGNOME: "+deleganteResponse.getCognome());
		log.info("DELEGANTE DATA NASCITA: "+deleganteResponse.getDataNascita());
		log.info("DELEGANTE LUOGO NASCITA: "+deleganteResponse.getLuogoNascita());
		log.info("DELEGANTE NOME: "+deleganteResponse.getNome());
		log.info("DELEGANTE SESSO: "+deleganteResponse.getSesso());

	}
	/******************************************************************************************************************
	 * FINE GET DELEGANTI FARMACIA
	 ******************************************************************************************************************/


	/******************************************************************************************************************
	 * ELENCO RICETTE FARMACIA
	 ******************************************************************************************************************/
	@Override
	public ElencoRicetteFarmaciaResponse elencoRicetteFarmacia(ElencoRicetteFarmaciaRequest elencoRicetteRequest) {
		log.info("FarmacieServiceImpl::elencoRicetteFarmacia");


		List<Errore> errori=new ArrayList<Errore>();

		//decifro il campo e lo reinserisco nella request decifrato
		if (elencoRicetteRequest.getCfAssistito()!=null) {
			String cfDecriptato;
			try {
				cfDecriptato = SecurityUtil.decrypt(elencoRicetteRequest.getCfAssistito());
				elencoRicetteRequest.setCfAssistito(cfDecriptato);
			} catch (Exception ex) {
				Errore e= new Errore();
				e.setCodErrore(Constants.FAR_CC_0075);
				e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
				errori.add(e);
			}
		}

		ElencoRicetteFarmaciaResponse response=new ElencoRicetteFarmaciaResponse();
		GetDelegantiResponse responseDeleghe= null;
		List<Long> esisteFarmaciaAbituale=null;
		long idIrec=0;//QUESTO ID ARRIVA DAI DATI DEL SERVIZIO CC.RicercaPaziente
		List<DmaccTabelleIdx> ricette = new ArrayList<DmaccTabelleIdx>();
		String codiceSessione = null;

		//1 IRIDE: identificaUserPasswordPIN
		//VIENE CHIAMATO DALL'INTERCEPTOR: IrideCacheUserPaswordInterceptor
		//ESITO identificaUserPasswordPIN
		log.debug("pin code dopo FILTRO: "+elencoRicetteRequest.getPinCode());
		String risultatoChiamataIride = elencoRicetteRequest.getPinCode();
		if(!("SUCCESSO".equalsIgnoreCase(risultatoChiamataIride))) {
			Errore e= new Errore();
			e.setCodErrore(risultatoChiamataIride);
            //TODO che errore riportiamo per il fallimento di iride????
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
		}

		if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setCodEsito(Constants.FAIL_CODE_9999);
				ElencoErrori elencoErrori= new ElencoErrori();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
				modificaUUIDLMessaggi(response.getCodSessione());
				return response;
				//CON ESITO NEGATIVO identificaUserPasswordPIN IL FLUSSO SI FERMA
		}

		//2 LOG CHIAMATA AL SERVIZIO: TO DO

		//3.VALIDAZIONE DELLA REQUEST
		errori = validateRequestElencoRicette(elencoRicetteRequest);
		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setCodEsito(Constants.FAIL_CODE_9999);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
			modificaUUIDLMessaggi(response.getCodSessione());
			return response;
			//CON ESITO NEGATIVO validateRequestElencoRicette IL FLUSSO SI FERMA
		}

		//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
		modificaUUIDLMessaggi(elencoRicetteRequest.getCodSessione());

		//4 CHIAMATA AL METODO CC.RicercaPaziente
		RicercaPazienteResponse ricercaPazienteResponse = ricercaPazienteService(elencoRicetteRequest);
		if("FALLIMENTO".equalsIgnoreCase(ricercaPazienteResponse.getEsito().value()) ||ricercaPazienteResponse.getPazienti()==null || ricercaPazienteResponse.getPazienti().size()<1) {
			Errore e= new Errore();
			e.setCodErrore(Constants.FAR_CC_0060);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);

		}else {
			//RECUPERO IL CAMPO IDIREC
			if(ricercaPazienteResponse.getPazienti()!=null || !ricercaPazienteResponse.getPazienti().isEmpty()) {
				List <Paziente> pazienti= ricercaPazienteResponse.getPazienti();
				Paziente paziente =pazienti.get(0);
				idIrec =paziente.getIdIrec().longValue();
			}
		}
		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setCodEsito(Constants.FAIL_CODE_9999);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
			modificaUUIDLMessaggi(response.getCodSessione());
			return response;
			//CON ESITO NEGATIVO ricercaPazienteResponse IL FLUSSO SI FERMA
		}

		//5 CHIAMATA AL METODO INI POStatoConsensi esteso
		try {
			String risultatoConsensi =fseStatoConsensi(elencoRicetteRequest);
			log.info("risultatoConsensi: "+risultatoConsensi);
			if("SUCCESSO".equals(risultatoConsensi)) {
				//Invio richiesto per tracciare su ini le richieste, modifica del 27/01/2023 tom
				tracciaRicercaDocumenti(elencoRicetteRequest);
			}else {
				Errore e= new Errore();
				e.setCodErrore(risultatoConsensi);
				e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
				errori.add(e);
			}
			if(!errori.isEmpty()) {
				//restituisco fallimento con la lista degli errori contenuti nella request
				response.setCodEsito(Constants.FAIL_CODE_9999);
				ElencoErrori elencoErrori= new ElencoErrori();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
				//CON ESITO NEGATIVO risultatoConsensi IL FLUSSO SI FERMA
			}
		} catch (javax.xml.ws.WebServiceException we) {
			//gestione time
			log.error("Errore di rete nel richiamare CCConsensoINIExtService:"+we.getMessage());
			response.setCodEsito(Constants.FAIL_CODE_9999);
			//errore durante la chiamata verso CCConsensoINIExtService
			Errore e= new Errore();
			e.setCodErrore(Constants.FAR_CC_0078);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
		} catch (Exception ex) {
			log.error("Errore generico nel richiamare CCConsensoINIExtService:"+ex.getMessage());
			response.setCodEsito(Constants.FAIL_CODE_9999);
			//errore durante la chiamata verso CCConsensoINIExtService
			Errore e= new Errore();
			e.setCodErrore(Constants.FAR_CC_0077);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
		}

		//QUESTO CONTROLLO NEL FLUSSO E' COMPLETAMENTE ERRATO DA ANALISI: NON FARE (PARLATO CON NICOLA)
		//SE CF RICHIEDENTE = CF ASSISTITO CHIAMARE LCCE

		//6 LANCIA LA CHIAMATA A DELEGHE E LE VERIFICHE
		if (elencoRicetteRequest.getCfCittadinoDelegato() !=null && !elencoRicetteRequest.getCfCittadinoDelegato().trim().isEmpty()) {
			try {

				responseDeleghe=verificaRispostaDelegheRicette(elencoRicetteRequest, errori);

				if(!errori.isEmpty()) {
					//restituisco fallimento con i controlli di business
					response.setCodEsito(Constants.FAIL_CODE_9999);
					ElencoErrori elencoErrori= new ElencoErrori();
					elencoErrori.errore=errori;
					response.setElencoErrori(elencoErrori);
					//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
					modificaUUIDLMessaggi(response.getCodSessione());
					return response;
					//CON ESITO NEGATIVO responseDeleghe IL FLUSSO SI FERMA
				}
			}catch (Exception ex) {
				//FUNZIONA: VERIFICATO
				log.error(ex.getMessage());
				response.setCodEsito(Constants.FAIL_CODE_9999);
				//errore durante la chiamata verso DELEGHE
				Errore e= new Errore();
				e.setCodErrore(Constants.FAR_CC_FATAL);
				e.setDescErrore("Errore nel richiamare DELEGHE:"+ex.getMessage());
				errori.add(e);
				ElencoErrori elencoErrori= new ElencoErrori();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
				modificaUUIDLMessaggi(response.getCodSessione());
				return response;
			}
		}
		//7 Chiama servizio LCCE.VerificaFarmacista
		try {
			VerificaFarmacistaResponse verFarResp = verificaFarmacistaLCCE (elencoRicetteRequest.getDatiFarmaciaRichiedente().getCodFarmacia(), elencoRicetteRequest.getDatiFarmaciaRichiedente().getPIvaFarmacia(), elencoRicetteRequest.getDatiFarmaciaRichiedente().getCfFarmacista());
			//VALORIZZAZIONE DESCRIZIONE ERRORE CON ERRORE PROVENIENTE DAL SERVIZIO LCCE
			RisultatoCodice risultatoCodice= verFarResp.getEsito();
			log.info("risultatoCodice: "+risultatoCodice);


			if(Constants.FAIL_CODE.equalsIgnoreCase(risultatoCodice.value())) {
				Errori erroriLCCE = verFarResp.getErrori();
				List <it.csi.iccws.dma.Errore> erroreLCCE= erroriLCCE.getErrore();

				Errore e =new Errore();
				e.setCodErrore(erroreLCCE.get(0).getCodice());
				e.setDescErrore(erroreLCCE.get(0).getDescrizione());
				log.info("DOPO DEL SET DESCRIZIONE: "+erroreLCCE.get(0).getDescrizione());
				errori.add(e);

			}
		}catch (Exception ex) {
			log.error(ex.getMessage());
			Errore e =new Errore();
			e.setCodErrore("9999");
			e.setDescErrore("Fallimento chiamata LCCE");
			errori.add(e);
		}
		// TEMPORANEAMENTE COMMENTATO PERMETTE DI ARRIVARE ALLA RESPONSE FINALE
		if(!errori.isEmpty()) {
			//restituisco fallimento con i controlli di business
			response.setCodEsito(Constants.FAIL_CODE_9999);
			ElencoErrori elencoErrori= new ElencoErrori();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
			modificaUUIDLMessaggi(response.getCodSessione());
			return response;
			//CON ESITO NEGATIVO verificaFarmacistaLCCE IL FLUSSO SI FERMA
		}

		//8 VERIFICA DELLA FARMACIA ABITUALE O OCCASIONALE
		esisteFarmaciaAbituale=farmabFarmacieAbitualiController.isValidRangeForToday(elencoRicetteRequest.getCfAssistito(), elencoRicetteRequest.getDatiFarmaciaRichiedente().getCodFarmacia());

		if(esisteFarmaciaAbituale==null || esisteFarmaciaAbituale.get(0)==null || esisteFarmaciaAbituale.get(0)==0) {
			//PERCORSO FARMACIA OCCASIONALE
			//9. Controllo codice sessione
			if(elencoRicetteRequest.getCodSessione()==null ||elencoRicetteRequest.getCodSessione().isEmpty()) {
				log.info("9.CODICE SESSIONE NON VALORIZZATO");
				//SE CODICE SESSIONE NON VALORIZZATO:
				//10. Verifica se richiedente ha un dispositivo certificato
				String codiceFiscalePerDispCert=null;
				if(!(elencoRicetteRequest.getCfAssistito().equals(elencoRicetteRequest.getCfCittadinoDelegato()))&&StringUtils.isNotEmpty(elencoRicetteRequest.getCfCittadinoDelegato())) {
					codiceFiscalePerDispCert=elencoRicetteRequest.getCfCittadinoDelegato();
				}
				else {
					codiceFiscalePerDispCert=elencoRicetteRequest.getCfAssistito();
				}
				List<Long> dispAss =farmabGestioneDeviceController.controllaDispositivoAssociatoPerCF(codiceFiscalePerDispCert);
				if(dispAss.isEmpty() || dispAss==null || dispAss.get(0).longValue()==0) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0002);
					e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
					errori.add(e);
				}
				if(!errori.isEmpty()) {
					//restituisco fallimento con i controlli di business
					response.setCodEsito(Constants.FAIL_CODE_9999);
					ElencoErrori elencoErrori= new ElencoErrori();
					elencoErrori.errore=errori;
					response.setElencoErrori(elencoErrori);
					//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
					modificaUUIDLMessaggi(response.getCodSessione());
					return response;
					//CON ESITO NEGATIVO controllaDispositivoAssociatoPerCF IL FLUSSO SI FERMA
				}

				//11. Registra dati farmacia occasionale
				DmaccTFarmaciaOccasionaleRich farmaciaOccRequest =preparaRequestPerInsert(elencoRicetteRequest,  idIrec);
				//VIENE GENERATO IL CODICE SESSIONE
				String sessioneCod = UUID.randomUUID().toString();
				farmaciaOccRequest.setSessioneCod(sessioneCod);
				try {
					int success=farmabFarmOccasionaliController.inserisciFarmacieOccasionali(farmaciaOccRequest);
					if(success >0) {
						//IL CITTADINO DEVE AUTORIZZARE LA FARMACIA OCCASIONALE
						Errore e =new Errore();
						e.setCodErrore(Constants.FAR_CC_0001);
						e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
						errori.add(e);
						response.setCodSessione(farmaciaOccRequest.getSessioneCod());
					}
				}catch (Exception ex) {
					log.error(ex.getMessage());
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_FATAL);
					e.setDescErrore(ex.getMessage());
					errori.add(e);
				}

				//14. Compone la risposta ERRORE TO DO
				if(!errori.isEmpty()) {
					//restituisco fallimento con i controlli di business
					response.setCodEsito(Constants.FAIL_CODE_9999);
					ElencoErrori elencoErrori= new ElencoErrori();
					elencoErrori.errore=errori;
					response.setElencoErrori(elencoErrori);
					//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
					modificaUUIDLMessaggi(response.getCodSessione());
					return response;
					//CON ESITO NEGATIVO farmaciaOccRequest IL FLUSSO SI FERMA
				}
			} else {
				log.info("9.CODICE SESSIONE VALORIZZATO");
				//SE CODICE SESSIONE VALORIZZATO:
				//13. Verifica codice sessione scaduto e in caso di successo trova le RICETTE
				ricette= verificaCodiceSessioneScaduto(elencoRicetteRequest.getCodSessione(), elencoRicetteRequest.getCfAssistito(), errori, idIrec, response);
				codiceSessione = elencoRicetteRequest.getCodSessione();
				if(!errori.isEmpty()) {
					//restituisco fallimento con i controlli di business
					response.setCodEsito(Constants.FAIL_CODE_9999);
					ElencoErrori elencoErrori= new ElencoErrori();
					elencoErrori.errore=errori;
					response.setElencoErrori(elencoErrori);
					//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
					modificaUUIDLMessaggi(response.getCodSessione());
					return response;
					//CON ESITO NEGATIVO verificaCodiceSessioneScaduto IL FLUSSO SI FERMA
				}
				//14. Compone la risposta: VEDI prepareSuccessResponseElencoRicette
			}
		}else {
			//PERCORSO FARMACIA ABITUALE
			//12. Ricerca ricette
			ricette= ricercaRicette (idIrec);
			//14. Compone la risposta: VEDI prepareSuccessResponseElencoRicette
		}

		//14. Compone la risposta: NESSUN ERRORE SUCCESSO
		//MANCANTE: NEL CASO DI RICETTA VUOTA COSA FARE? MANCA IN ANALISI
		response =prepareSuccessResponseElencoRicette(ricette, codiceSessione); //DA FINIRE

		//15. Log: risposta servizio: TO DO
		//AUDIT (SE ESITO = OK
		//idpaziente = idIrec
		if(Constants.SUCCESS_CODE_0000.equalsIgnoreCase(response.getCodEsito())){
			try {
				//Modifica: se sono un delegato devo usare un codice diverso
				String catalogoServizio = Constants.CATALOG_ELE_RIC_FAR;
				String citDelegato = null;
				if(isRequestDelegato(elencoRicetteRequest)){
					catalogoServizio = Constants.CATALOG_ELE_RIC_FAR_DEL;
					citDelegato = elencoRicetteRequest.getCfCittadinoDelegato();
				}
				Long idIrecAudit = farmabController.getIdIrecPaziente(elencoRicetteRequest.getCfAssistito());

				final String NOME_SERVIZIO = "elencoRicetteFarmacia";
				final String CODICE_SERVIZIO = "ELE_RIC_FARM";
				logAudit.insertTlogAudit(getLogAudit(catalogoServizio,
						elencoRicetteRequest.getDatiFarmaciaRichiedente(),
						elencoRicetteRequest.cfAssistito, idIrecAudit, NOME_SERVIZIO, CODICE_SERVIZIO,  citDelegato));
			} catch (CatalogoLogAuditLowDaoException e) {
				log.error(e.getMessage());
			}
		}
		//Aggiunto salvataggio codsessione ove presente 27/01/2023 tom
		modificaUUIDLMessaggi(response.getCodSessione());
		return response;
	}

	private void modificaUUIDLMessaggi(String codiceSessione) {
		if (StringUtils.isNotEmpty(codiceSessione)) {
			log.info("FarmacieServiceImpl::elencoRicetteFarmacia.modificaUUIDLMessaggi");
			Message message = PhaseInterceptorChain.getCurrentMessage();
			Long idLMessaggi = (Long) message.getExchange().get(Constants.LOGGING_KEY_L_MESSAGGI);
			if (idLMessaggi != null) {
				farmabTLogDao.updatetLMessaggiUUID(codiceSessione, idLMessaggi);
			}
		}
	}

	private void tracciaRicercaDocumenti(ElencoRicetteFarmaciaRequest elencoRicetteRequest) {
		log.info("FarmacieServiceImpl::elencoRicetteFarmacia.tracciaRicercaDocumenti");
		if (elencoRicetteRequest != null) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(new Runnable() {
				@Override
				public void run() {
					// Richiamo servizio ini RicercaDocumentiINIService per tracciare richiesta
					try {
						//non mi passo uuid di wso2 perche' pare non interessi tenere traccia del messaggio ma se essite il campo uso codsessione, deciso da me Tom
						String uuid = StringUtils.isNotEmpty(elencoRicetteRequest.codSessione)?elencoRicetteRequest.codSessione:UUID.randomUUID().toString();
						// ricercaDocumenti
						RicercaDocumentiIN ricercaDocumentiIN = new RicercaDocumentiIN();
						ricercaDocumentiIN.setContestoOperativo("TREATMENT");
						ElencoCFAssistitoType elencoCFAssistitoType = new ElencoCFAssistitoType();
						CfAssistitoType assistito = new CfAssistitoType();
						assistito.setAttivo("S");
						assistito.setCf(elencoRicetteRequest.cfAssistito);
						elencoCFAssistitoType.getCfAssistito().add(assistito);
						ricercaDocumentiIN.setElencoCFAssistito(elencoCFAssistitoType);
						ricercaDocumentiIN.setIdentificativoAssistito(elencoRicetteRequest.cfAssistito);
						ricercaDocumentiIN.setIdentificativoOrganizzazione(Constants.COD_REGIONALE_INI);
						ricercaDocumentiIN.setIdentificativoUtente(elencoRicetteRequest.cfAssistito);
						ricercaDocumentiIN.setPresaInCarico("true");
						ricercaDocumentiIN.setRuoloUtente(Constants.COD_RUOLO_UTENTE_INI);
						ricercaDocumentiIN.setStatoDocumento("APPROVED");
						ricercaDocumentiIN.setStrutturaUtente(Constants.COD_REGIONALE_INI);
						ricercaDocumentiIN.setTipoAttivita("READ");
						ricercaDocumentiIN.setTipoDocumento(Constants.COD_DOCUMENTO_INI);
						/*
						 * ricercaDocumentiIN.setDataRicercaDA(null);
						 * ricercaDocumentiIN.setDataRicercaA(null)
						 */
						// richiedente
						Richiedente richiedente = new Richiedente();
						richiedente.setCodiceFiscale(elencoRicetteRequest.datiFarmaciaRichiedente.cfFarmacista);
						richiedente.setNumeroTransazione(uuid);
						richiedente.setTokenOperazione(uuid);
						RicercaDocumentiRequeste ricercaDocumentiRequeste = new RicercaDocumentiRequeste();
						ricercaDocumentiRequeste.setRichiedente(richiedente);
						ricercaDocumentiRequeste.setRicercaDocumentiIN(ricercaDocumentiIN);
						log.info("RICHIAMO SERVIZIO ESTERNO RicercaDocumentiINIService.ricercaDocumenti");
						long startTime = System.currentTimeMillis();
						modifyHTTPConduitTimeout(ricercaDocumentiINIService);
						RicercaDocumentiResponse resp = ricercaDocumentiINIService
								.ricercaDocumenti(ricercaDocumentiRequeste);
						log.info("RISPOSTA SERVIZIO ESTERNO RicercaDocumentiINIService.ricercaDocumenti:"
								+ (System.currentTimeMillis() - startTime) + " Millis");
						if (resp != null) {
							resp.getEsito();
						}
					} catch (Exception e) {
						log.info("Errore nel richiamare ricercaDocumentiINIService.ricercaDocumenti da non considerare"
								+ e.getMessage());
						executor.shutdown();
					}
				}
			});
			executor.shutdown();
		}
	}

	private void modifyHTTPConduitTimeout( final Object o ) {
		//Ho provato a configurarlo tramite ApplicationContext, vedi parte remmate ma jboss non carica le classi in modo corretto
		Client client = ClientProxy.getClient( o );
	  	HTTPConduit conduit = (HTTPConduit)client.getConduit();
	  	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	  	httpClientPolicy.setConnectionTimeout(2000);
	  	httpClientPolicy.setReceiveTimeout(3000);
	  	conduit.setClient( httpClientPolicy );
	}

	private void modifyHTTPRetry( final Object o, String numeroMaxTentivi ) {
		Integer numeroMaxTentativiInt=2;
		if (StringUtils.isNumeric(numeroMaxTentivi)) {
			numeroMaxTentativiInt = new Integer(numeroMaxTentivi);
		}
		Client client = ClientProxy.getClient( o );
	  	HTTPConduit conduit = (HTTPConduit)client.getConduit();
	  	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	  	httpClientPolicy.setMaxRetransmits(numeroMaxTentativiInt.intValue());
	  	conduit.setClient( httpClientPolicy );
	}

	private boolean isRequestDelegato(ElencoRicetteFarmaciaRequest request){
		return (!StringUtils.isEmpty(request.getCfCittadinoDelegato())   && !request.getCfCittadinoDelegato().equalsIgnoreCase(request.getCfAssistito()));
	}

	private AuditDto getLogAudit(String codiceCatalogo, DatiFarmaciaRichiedente req, String cfAssistitoOrDelegato, Long idPaziente, String nomeServizio, String codiceServizio,
			 String citDelegato) throws CatalogoLogAuditLowDaoException{

		//se il cf cittadino delegato valorizzato scrive un codice di audit diverso scrive anche cf cittadino delegato e prende un id di audit diverso
		final String collocazione = "Farmacia ";
		MessageContext mc = wsContext.getMessageContext();
	    HttpServletRequest request = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);

	    Long idIrecUtente = farmabController.getIdIrecUtente(req.getCfFarmacista());

		AuditDto audit = new AuditDto();
		audit.setApplicativoVerticale(Constants.APPLICATION_CODE);
		audit.setIdUtente(idIrecUtente);


		CatalogoLogAuditLowDto catalogo = logAudit.geCatalogoLogAudittByCodice(codiceCatalogo);
		if(!StringUtils.isEmpty(citDelegato)){
			audit.setInformazioniTracciate(MessageFormat.format(catalogo.getDescrizione(), cfAssistitoOrDelegato, req.getCfFarmacista(), citDelegato));
		}else{
			audit.setInformazioniTracciate(MessageFormat.format(catalogo.getDescrizione(), cfAssistitoOrDelegato, req.getCfFarmacista()));
		}

		audit.setIdPaziente(idPaziente);
		audit.setIdCatalogoLogAudit(catalogo.getId());

		//audit.setIdTransazione(UUID.randomUUID().toString());
		audit.setCodiceApplicazioneRichiedente(req.getApplicazione());
		audit.setVisibileAlCittadino("S");
		audit.setIpChiamante(request.getRemoteAddr());

		audit.setCollocazione(collocazione+req.getCodFarmacia());
		audit.setCodiceServizio(codiceServizio);
		audit.setNomeServizio(nomeServizio);
		audit.setCodiceRuolo(req.getRuolo());
		audit.setApplicativoVerticale(req.getApplicativoVerticale());
		audit.setCodiceFiscaleUtente(req.getCfFarmacista());

		return audit;
	}



	private List<Errore> validateRequestElencoRicette(ElencoRicetteFarmaciaRequest request){
		log.info("3.FarmacieServiceImpl::validateRequestElencoRicette");
		List<Errore> errori= new ArrayList<Errore>();

		errori=this.validateDatiFarmacistaRicette(request);

		//--------------------------------------------------------------------------
		//controlli per tag cfCittadino delegato campo opzionale
		/*if(request.getCfCittadinoDelegato() ==null || request.getCfCittadinoDelegato().isEmpty()) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}*/
		if(request.getCfCittadinoDelegato() !=null && !request.getCfCittadinoDelegato().trim().isEmpty() && FarmabUtils.isNotValidCf(request.getCfCittadinoDelegato()) ) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0051);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0051, request.getCfCittadinoDelegato());
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag cfCittadino delegato

		//--------------------------------------------------------------------------
		//controlli per tag PIN CODE
		if(request.getPinCode()==null || request.getPinCode().isEmpty()) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.PIN_CODE_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag PIN CODE

		//--------------------------------------------------------------------------

		//!!MANCANTE: il campo CFASSISTITO cifrato con il certificato fornito alle farmacie!!

		//controlli per tag codiceFiscale ASSISTITO
		if (StringUtils.isEmpty(request.getCfAssistito())) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_ASSISTITO_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		} else if (FarmabUtils.isNotValidCf(request.getCfAssistito())) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0051);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_ASSISTITO_0050, request.getCfAssistito());
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}
		//end controlli per tag codiceFiscale ASSISTITO
		//--------------------------------------------------------------------------

		//controlli per confronto tra cfAssistito e cfCittadinoDelegato
		if (request.getCfAssistito()!=null && request.getCfCittadinoDelegato()!=null && request.getCfAssistito().equalsIgnoreCase(request.getCfCittadinoDelegato())) {
			Errore e = new Errore();
			e.setCodErrore(Constants.FAR_CC_0073);
			e.setDescErrore(farmabLog.findMesaggiErrore(Constants.FAR_CC_0073));
			errori.add(e);
		}
		//END controlli per cfAssistito e cfCittadinoDelegato
		//--------------------------------------------------------------------------

		return errori;
	}

	private List<Errore> validateDatiFarmacistaRicette(ElencoRicetteFarmaciaRequest request) {
		log.info("3.FarmacieServiceImpl::validateDatiFarmacistaRicette");
		List<Errore> errori= new ArrayList<Errore>();

		if (request!=null) {
			//controlli per Presenza dati farmacista
			if (request.getDatiFarmaciaRichiedente() != null) {
				//controlli per tag codiceFiscale FARMACISTA
				if (request.getDatiFarmaciaRichiedente().getCfFarmacista().isEmpty() || request.getDatiFarmaciaRichiedente().getCfFarmacista().trim().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_FARMACISTA_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				if (FarmabUtils.isNotValidCf(request.getDatiFarmaciaRichiedente().getCfFarmacista())) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0051);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_FARMACISTA_0051, request.getDatiFarmaciaRichiedente().getCfFarmacista());
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//end controlli per tag codiceFiscale FARMACISTA
				//--------------------------------------------------------------------------

				//controlli per tag applicazione
				if(request.getDatiFarmaciaRichiedente().getApplicazione().isEmpty()||request.getDatiFarmaciaRichiedente().getApplicazione().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!request.getDatiFarmaciaRichiedente().getApplicazione().contains("GESTFARM")) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050, request.getDatiFarmaciaRichiedente().getApplicazione());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}

				//end controlli per tag applicazione
				//--------------------------------------------------------------------------
				//controlli per tag applicativoVerticale
				if(request.getDatiFarmaciaRichiedente().getApplicativoVerticale().isEmpty()||request.getDatiFarmaciaRichiedente().getApplicativoVerticale().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICATIVO_VERTICALE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!request.getDatiFarmaciaRichiedente().getApplicativoVerticale().contains("FARAB")) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICATIVO_VERTICALE_0050, request.getDatiFarmaciaRichiedente().getApplicativoVerticale());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}
				//end controlli per tag applicativoVerticale

				//--------------------------------------------------------------------------
				//controlli per tag ruolo
				if(request.getDatiFarmaciaRichiedente().getRuolo().isEmpty()||request.getDatiFarmaciaRichiedente().getRuolo().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}else {
					if(!"FAR".equals(request.getDatiFarmaciaRichiedente().getRuolo())) {
						Errore e = new Errore();
						e.setCodErrore(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050, request.getDatiFarmaciaRichiedente().getRuolo());
						e.setDescErrore(messaggioErroreCompleto);
						errori.add(e);
					}
				}
				//end controlli per tag ruolo

				//--------------------------------------------------------------------------
				//controlli per tag COD FARMACIA
				if(request.getDatiFarmaciaRichiedente().getCodFarmacia()==null || request.getDatiFarmaciaRichiedente().getCodFarmacia().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_FARMACIA_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag COD FARMACIA

				//--------------------------------------------------------------------------
				//controlli per tag PIVA FARMACIA
				if(request.getDatiFarmaciaRichiedente().getPIvaFarmacia()==null || request.getDatiFarmaciaRichiedente().getPIvaFarmacia().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.PIVA_FARMACIA_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag PIVA FARMACIA

				//--------------------------------------------------------------------------
				//controlli per tag GESTIONALE
				if(request.getDatiFarmaciaRichiedente().getGestionale()==null || request.getDatiFarmaciaRichiedente().getGestionale().isEmpty()) {
					Errore e = new Errore();
					e.setCodErrore(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.GESTIONALE_0050);
					e.setDescErrore(messaggioErroreCompleto);
					errori.add(e);
				}
				//END controlli per tag GESTIONALE

				//--------------------------------------------------------------------------
			} else {
				Errore e =new Errore();
				e.setCodErrore(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
				e.setDescErrore(messaggioErroreCompleto);
				errori.add(e);
			}
			//end controlli per Presenza dati farmacista
			//--------------------------------------------------------------------------
		} else {
			Errore e =new Errore();
			e.setCodErrore(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
			e.setDescErrore(messaggioErroreCompleto);
			errori.add(e);
		}

		return errori;
	}

	private String getWso2Id(MessageContext mContext){
		//recupero il wso2id
		WrappedMessageContext wmc = (WrappedMessageContext)mContext;
		Message m = wmc.getWrappedMessage();
		Exchange ex = m.getExchange();
		String wso2id = (String)ex.get(Constants.LOGGING_WSO2ID);
		return wso2id;
	}

	private GetDelegantiResponse verificaRispostaDelegheRicette(ElencoRicetteFarmaciaRequest requestRicetteFarmacia,List<Errore> errore) throws Exception {
		log.info("6.FarmacieServiceImpl::verificaRispostaDelegheRicette");
		String wso2id = getWso2Id(wsContext.getMessageContext());

    	GetDelegantiResponse delegaResponse=getDelegheElencoServizi().elencoRicetteFarmacia(requestRicetteFarmacia, wso2id);
    	boolean trovato=false;
    	if(delegaResponse!=null && Constants.SUCCESS_CODE.equalsIgnoreCase(delegaResponse.getEsito())) {
    		log.info("Esito della chiamata a deleghe="+delegaResponse.getEsito());
			if(delegaResponse.getDeleganti()!=null && delegaResponse.getDeleganti().getDelegante()!=null) {

				List <DelegaCittadino> delegheCittadino =delegaResponse.getDeleganti().getDelegante();
				if(delegheCittadino != null && !delegheCittadino.isEmpty() && delegheCittadino.size()>0) {
					//delegheCittadino.forEach(delegaCittadino -> {
					for (DelegaCittadino delegaCittadino :delegheCittadino) {
						Deleghe deleghe = delegaCittadino.getDeleghe();
						List <DelegaServizio> delegheServizio =deleghe.getDelega();
						//PROSEGUO SOLO SE IL CF ASSISTITO CORRIPONDE AL CF DELEGANTE
						if(requestRicetteFarmacia.getCfAssistito().equalsIgnoreCase(delegaCittadino.getCodiceFiscale())) {
							//delegheServizio.forEach(delegaServizio -> {
							if (delegheServizio!=null && delegheServizio.size()>0) {
								for (DelegaServizio delegaServizio: delegheServizio) {
									String gradoDelega=null;
									if(Constants.CODICE_SERVIZIO_IN_DELEGHE.equalsIgnoreCase(delegaServizio.getCodiceServizio())) {
										if(delegaServizio.getStatoDelega().equalsIgnoreCase("ATTIVA")) {
											trovato=true;
											if(delegaServizio.getGradoDelega()== null || delegaServizio.getGradoDelega().trim().isEmpty()) {
												gradoDelega=Constants.GRADO_DELEGA;
											} else {
												gradoDelega=delegaServizio.getGradoDelega();
											}
											//CONTROLLO AUTORIZZAZIONE DEL RICHIEDENTE
											if(gradoDelega.length()>1) {
												//verifico i dati sulla tabella dmacc_r_autorizzazione_deleghe
												List<Long> ids=farmabFarmacieAbitualiController.getIdVerificaAutorizDeleghe(requestRicetteFarmacia.getDatiFarmaciaRichiedente().getRuolo(), gradoDelega, "ELE_RIC_FAR");
												if(ids==null || ids.size()<1) {
													Errore e= new Errore();
									    			e.setCodErrore(Constants.FAR_CC_0058);
									    			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
									    			errore.add(e);
												}
											} else {
												Errore e= new Errore();
								    			e.setCodErrore(Constants.FAR_CC_0058);
								    			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
								    			errore.add(e);
											}
										}
									}
								}
							}
							//});
						}
					}
				      //});
				}
			}
			if(!trovato) {
				Errore e= new Errore();
    			e.setCodErrore(Constants.FAR_CC_0057);
    			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
    			errore.add(e);
			}

		} else {
			//TODO errore nel richaime del servizio deleghe
			Errore e= new Errore();
			e.setCodErrore(Constants.FAR_CC_0057);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errore.add(e);
		}

		return delegaResponse;
    }

	private DmaccTFarmaciaOccasionaleRich preparaRequestPerInsert(ElencoRicetteFarmaciaRequest elencoRicetteRequest, long idIrec){
		log.info("FarmacieServiceImpl::preparaRequestPerInsert");
		//CREAZIONE DI UN UUID RANDOM
		String uuid = UUID.randomUUID().toString();

		long farmOccRichId=0;
		long idPaziente=idIrec;
		String farmaciaCod=elencoRicetteRequest.getDatiFarmaciaRichiedente().getCodFarmacia();
		String farmaciaPiva=elencoRicetteRequest.getDatiFarmaciaRichiedente().getPIvaFarmacia();
		String farmaciaGestionale=elencoRicetteRequest.getDatiFarmaciaRichiedente().getGestionale();
		String farmacistaCf=elencoRicetteRequest.getDatiFarmaciaRichiedente().getCfFarmacista();
		String cittadinoDelegatoCf=elencoRicetteRequest.getCfCittadinoDelegato();
		String sessioneCod=uuid;
		int farmOccRichStatoId=1;//DA AUTORIZZARE
		String dataInserimento=null;
		String dataAggiornamento=null;
		String dataCancellazione=null;

		DmaccTFarmaciaOccasionaleRich farmaciaOccRequest= new DmaccTFarmaciaOccasionaleRich(farmOccRichId, idPaziente, farmaciaCod, farmaciaPiva,
				farmaciaGestionale, farmacistaCf, cittadinoDelegatoCf, sessioneCod,
				farmOccRichStatoId, dataInserimento, dataAggiornamento, dataCancellazione);

		return farmaciaOccRequest;

	}

	private List<DmaccTabelleIdx> ricercaRicette (long idPazIrec) {
		log.info("12.FarmacieServiceImpl::ricercaRicette");
		List<DmaccTabelleIdx> response= new ArrayList<DmaccTabelleIdx>();

		//1 RICERCA SULLA DMACCIDX_T_DOCUMENTO
		List<DmaccTabelleIdx> listaidxTDoc = farmabFarmacieServiceController.cercaDmaccidxTDocumento(idPazIrec);

		if(listaidxTDoc !=null && listaidxTDoc.size() > 0) {
			log.info("listaidxTDoc: PIENA");

			//devo eliminare dalla lista i documenti che hanno la ricetta oscurata per nre
			//TODO rivedere la logica
			for(DmaccTabelleIdx tabidx: listaidxTDoc) {
				log.info("tabidx  CICLO PER eliminare NREOscurati");
				//3 RICERCA SULLA DMACCIDX_T_DOC_NRE
				Long countNre = farmabFarmacieServiceController.esisteDocumentiNREOscurati(tabidx.getNreRifDoc(), idPazIrec);
				if (countNre==0) {
					//cerco i dati del medico da mettere nella response
					List<String> medicol=farmabFarmacieServiceController.cercaDocumentoMedico(tabidx.getIdDocumentoIlec(), tabidx.getCodCl());
					if (medicol!=null && !medicol.isEmpty()) {
						tabidx.setCodiceIdMedico(medicol.get(0));
					} else {
						tabidx.setCodiceIdMedico("");
					}
					response.add(tabidx);
				}
			}
		}else {
			log.info("listaidxTDoc: VUOTA NESSUNA RICETTA TROVATA per idPaziente="+idPazIrec);
		}

		return response;
	}

	private List<DmaccTabelleIdx> verificaCodiceSessioneScaduto(String sessioneCod, String cfAssistito, List<Errore> errori, long idIrec, ElencoRicetteFarmaciaResponse response){
		log.info("13.FarmacieServiceImpl::verificaCodiceSessioneScaduto");
		List<DmaccTabelleIdx> ricette=null;

		//RICERCA CODICE SESSIONE VALIDO
		List<Long> idRichiesteFarmOcc =farmabFarmOccasionaliController.cercaCodSessione(sessioneCod, cfAssistito);
		if(idRichiesteFarmOcc.isEmpty() || idRichiesteFarmOcc==null) {
			Errore e =new Errore();
			e.setCodErrore(Constants.FAR_CC_0004);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
			return ricette;
		}

		//RICERCA VALIDITA SESSIONE
		List<Long> farmOccRichStatoId =farmabFarmOccasionaliController.cercaValiditaSessione(sessioneCod, cfAssistito);
		if(farmOccRichStatoId.isEmpty() || farmOccRichStatoId==null) {
			log.info("13A LA SESSIONE E' SCADUTA");
			//LA SESSIONE E' SCADUTA: SONO STATI SUPERATI 10 MINUTI DI ATTESA
			//VIENE AGGIORNATO IL RECORD CON LO STATO SCADUTA
			try {
				farmabFarmOccasionaliController.updateStatoFarmacieOccasionali(idRichiesteFarmOcc, "SCAD");
			}catch (Exception ex) {
				//EVENTUALE ECCEZIONE SULL'UPDATE
				log.error(ex.getMessage());
				Errore e =new Errore();
				e.setCodErrore(Constants.FAR_CC_0000);
				e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore())+ex.getMessage());
				errori.add(e);
			}
			//ERRORE CODICE SESSIONE SCADUTO
			Errore e =new Errore();
			e.setCodErrore(Constants.FAR_CC_0003);
			e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
			errori.add(e);
		}else {
			log.info("13A LA SESSIONE NON E' SCADUTA");
			//SESSIONE VALIDA ENTRO I 10 MINUTI
			for (Long statoId : farmOccRichStatoId) {
				//statoId==1 (DA_AUT)
				if(statoId==1) {
					log.info("STATO ID:1 DA_AUT");
					//ERRORE IL CITTADINO DEVE AUTORIZZARE LA FARMACIA ABITUALE
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0001);
					e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
					errori.add(e);
					response.setCodSessione(sessioneCod);
				}
				//statoId==2 (AUT)
				if(statoId==2) {
					log.info("STATO ID:2 AUT");
					//12. Ricerca ricette
					ricette= ricercaRicette (idIrec);

					try {
						//STATO AUTORIZZATO, RICETTA RECUPERATA. VIENE AGGIORNATO LO STATO DELLA PRATICA IN EVASO
						farmabFarmOccasionaliController.updateStatoFarmacieOccasionali(idRichiesteFarmOcc, "EVASO");
					}catch (Exception ex) {
						//EVENTUALE ECCEZIONE SULL'UPDATE
						log.error(ex.getMessage());
						Errore e =new Errore();
						e.setCodErrore(Constants.FAR_CC_0000);
						e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore())+ex.getMessage());
						errori.add(e);
					}
				}
				//statoId==4 (EVASO)
				if(statoId==4) {
					log.info("STATO ID:4 EVASO");
					//ERRORE DATI GIA RESTITUITI PER IL CODICE SESSIONE
					Errore e =new Errore();
					e.setCodErrore(Constants.FAR_CC_0005);
					e.setDescErrore(farmabLog.findMesaggiErrore(e.getCodErrore()));
					errori.add(e);
				}
			}
		}

		return ricette;
	}

	//RESPONSE: SUCCESS
	private ElencoRicetteFarmaciaResponse prepareSuccessResponseElencoRicette(List<DmaccTabelleIdx> ricette, String codiceSessione){
		log.info("14.FarmacieServiceImpl::prepareSuccessResponseElencoRicette");
		ElencoRicetteFarmaciaResponse response=new ElencoRicetteFarmaciaResponse();
		ElencoRicette elencoRicette = new ElencoRicette();
		XMLGregorianCalendar xmlGregCal = null;


		List <Ricetta> listaricette=elencoRicette.getRicetta();
		if(ricette!=null && ricette.size()>0 ) {
			if(ricette.get(0)!=null) {
				for (DmaccTabelleIdx ricetta :ricette) {
					Ricetta ricettaSingola=new Ricetta();

					log.info("DATA VALIDAZIONE: "+ricetta.getDataValidazione());

					if(ricetta.getDataValidazione()!=null){
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date date = null;
						try {
							date = format.parse(ricetta.getDataValidazione());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(date);

						try {
							xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
						} catch (DatatypeConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						log.info("data convertita: "+xmlGregCal.toString());

					}

					ricettaSingola.setDataRicetta(xmlGregCal); //CONVERSIONE DELLA STRINGA IN XML GREGORIAN CALENDAR
					ricettaSingola.setNre(ricetta.getNreRifDoc());
					ricettaSingola.setCfMedicoPrescrittore(ricetta.getCodiceIdMedico()); //IN ANALISI SI PARLA DEL CF DEL MEDICO MA ARRIVA SOLO L'ID

					listaricette.add(ricettaSingola);

					//SOLO PER SVILUPPO
					//stampaResponseElencoRicette(listaricette);

					response.setElencoRicette(elencoRicette);
					response.setCodSessione(codiceSessione);
					response.setCodEsito(Constants.SUCCESS_CODE_0000);
				}
			}else {
				response.setCodEsito(Constants.FAIL_CODE_9999);
			}
		}else {
			response.setCodEsito(Constants.SUCCESS_CODE_0000);
		}

		return response;
	}
	/******************************************************************************************************************
	 * FINE ELENCO RICETTE FARMACIA
	 ******************************************************************************************************************/

	/******************************************************************************************************************
	 * AGGANCIO A SERVIZI ESTERNI
	 ******************************************************************************************************************/
	private VerificaFarmacistaResponse verificaFarmacistaLCCE (String codFaramcia, String partitaIvaFarmacia, String cfFaramcista) {
		log.info("7.FarmacieServiceImpl::verificaFarmacistaLCCE");
		VerificaFarmacistaResponse verificaFarmacistaResponse = new VerificaFarmacistaResponse();
		VerificaFarmacistaRequest verificaFarmacistaRequest = new VerificaFarmacistaRequest();
		Farmacia farmacia = new Farmacia();

		farmacia.setCodice(codFaramcia);
		farmacia.setPartitaIVA(partitaIvaFarmacia);

		verificaFarmacistaRequest.setCodiceFiscaleFarmacista(cfFaramcista);
		verificaFarmacistaRequest.setFarmacia(farmacia);

		log.info("RICHIAMO SERVIZIO ESTERNO FarmacieServiceImpl farmacieServiceLCCE.verificaFarmacista");
		long startTime = System.currentTimeMillis();
		verificaFarmacistaResponse = farmacieServiceLCCE.verificaFarmacista(verificaFarmacistaRequest);
		log.info("RISPOSTA SERVIZIO ESTERNO FarmacieServiceImpl farmacieServiceLCCE.verificaFarmacista:"+(System.currentTimeMillis()-startTime)+" Millis");

		return verificaFarmacistaResponse;

	}

	private RicercaPazienteResponse ricercaPazienteService (ElencoRicetteFarmaciaRequest elencoRicetteRequest) {
		log.info("4.FarmacieServiceImpl::ricercaPazienteService");

		RicercaPazienteResponse ricercaPazienteResponse = getRicercaPazienteServizi().ricercaPaziente(elencoRicetteRequest);
		log.info("ricercaPazienteResponse ESITO: "+ricercaPazienteResponse.getEsito().value());

		return ricercaPazienteResponse;
	}

	private String fseStatoConsensi(ElencoRicetteFarmaciaRequest elencoRicetteRequest) {
		log.info("5.FarmacieServiceImpl::fseStatoConsensi");

		String risultato=Constants.FAR_CC_0077;

		//CREAZIONE DI UN UUID RANDOM
		String uuid = UUID.randomUUID().toString();

		//REQUEST
		StatoConsensiExtRequeste statoConsensiExtRequeste = new StatoConsensiExtRequeste();

		//COMPONENTI REQUEST (PAZIENTE; RICHIEDENTE; STATO CONSENSI)
		it.csi.consensoini.dma.Paziente paziente = new it.csi.consensoini.dma.Paziente();
		RichiedenteExt richiedenteExt = new RichiedenteExt();
		StatoConsensiIN statoConsensiIN = new StatoConsensiIN();

		//COMPONENTI DEL RICHIEDENTE
		it.csi.consensoini.dma.ApplicazioneRichiedente applicazioneRichiedente = new it.csi.consensoini.dma.ApplicazioneRichiedente();
		it.csi.consensoini.dma.RegimeDMA regimeDMA = new it.csi.consensoini.dma.RegimeDMA();
		it.csi.consensoini.dma.RuoloDMA ruoloDMA = new it.csi.consensoini.dma.RuoloDMA();

		//SET DEI DATI PAZIENTE
		paziente.setCodiceFiscale(elencoRicetteRequest.getCfAssistito());

		//SET DEI DATI RICHIEDENTE
		applicazioneRichiedente.setCodice("WEBAPP_CM");
		richiedenteExt.setApplicazione(applicazioneRichiedente);
		richiedenteExt.setCodiceFiscale(elencoRicetteRequest.getCfAssistito());
		richiedenteExt.setNumeroTransazione(uuid);
		regimeDMA.setCodice("AMB");
		richiedenteExt.setRegime(regimeDMA);
		ruoloDMA.setCodice("CIT");
		richiedenteExt.setRuolo(ruoloDMA);
		richiedenteExt.setTokenOperazione(uuid); //DA CAPIRE TOKEN OPERAZIONE

		//SET DATI STATO CONSENSI
		statoConsensiIN.setIdentificativoAssistitoConsenso(elencoRicetteRequest.getCfAssistito());
		statoConsensiIN.setIdentificativoAssistitoGenitoreTutore(elencoRicetteRequest.getCfAssistito());
		statoConsensiIN.setIdentificativoOrganizzazione("010");
		statoConsensiIN.setIdentificativoUtente(elencoRicetteRequest.getDatiFarmaciaRichiedente().getCfFarmacista());
		statoConsensiIN.setStrutturaUtente("------");
		statoConsensiIN.setTipoAttivita("READ");

		//POPOLAMENTO DELLA REQUEST
		statoConsensiExtRequeste.setPaziente(paziente);
		statoConsensiExtRequeste.setRichiedente(richiedenteExt);
		statoConsensiExtRequeste.setStatoConsensiIN(statoConsensiIN);


		log.info("RICHIAMO SERVIZIO ESTERNO FarmacieServiceImpl.statoConsensi INI");
		//PRENDE DALLA TABELLA dmacc_t_configurazione.chiave = NUM_MAX_TENTATIVI_INI_PROM PER RIPETERE ALTRE 3 VOLTE LA CHIAMATA
		String numeroMaxTentivi = farmabFarmOccasionaliController.getNumeroMassimoTentaivi();
		modifyHTTPRetry(getStatoConsensiINI(), numeroMaxTentivi);
		long startTime = System.currentTimeMillis();
		StatoConsensiResponse statoConsensiResponse =getStatoConsensiINI().statoConsensi(statoConsensiExtRequeste);
		log.info("RISPOSTA SERVIZIO ESTERNO FarmacieServiceImpl.statoConsensi INI:"+(System.currentTimeMillis()-startTime)+" Millis");

		if(statoConsensiResponse!=null && statoConsensiResponse.getEsito()!=null) {
			it.csi.consensoini.dma.RisultatoCodice risultatoCodice =statoConsensiResponse.getEsito();
			log.info("5.FarmacieServiceImpl::risultatoCodice: "+risultatoCodice.value());

			//DA CAPIRE SE ARRIVANO QUESTI ERRORI OPPURE NO
			//MANCANTE: Se viene restituito errore (esito=9999 e codEsito=2007 - Consenso alla consultazione negato) il sistema restituisce lâerrore FAR-CC-0052.
			//MANCANTE: Se viene restituito errore (esito=9999 e codEsito=2035 - il sistema restituisce lâerrore FAR-CC-0055.
			if(Constants.FAIL_CODE.equalsIgnoreCase(risultatoCodice.value())) {
				risultato=Constants.FAR_CC_0054;
			}else {
				risultato=risultatoCodice.value();
				if(statoConsensiResponse.getStatoConsensiOUT()!=null) {
					StatoConsensiOUT statoConsensiOUT =statoConsensiResponse.getStatoConsensiOUT();
					String consensoAlimentazione = statoConsensiOUT.getConsensoAlimentazione();
					String consensoConsultazione = statoConsensiOUT.getConsensoConsultazione();
					String identificativoInformativaCorrente = statoConsensiOUT.getIdentificativoInformativaCorrente();
					if(!identificativoInformativaCorrente.startsWith("010")) {
						risultato=Constants.FAR_CC_0053;
					}
					else if(!"true".equalsIgnoreCase(consensoAlimentazione)) {
						risultato=Constants.FAR_CC_0055;
					}
					else if(!"true".equalsIgnoreCase(consensoConsultazione)) {
						risultato=Constants.FAR_CC_0052;
					}
				}
			}
		}
		return risultato;
	}
	/******************************************************************************************************************
	 * FINE AGGANCIO A SERVIZI ESTERNI
	 ******************************************************************************************************************/

	/******************************************************************************************************************
	 * GET E SET SERVIZI ESTERNI TRAMITE APPLICATION CONTEXT
	 ******************************************************************************************************************/
	public FarmaciaService getFarmacieServiceLCCE() {
		return farmacieServiceLCCE;
	}

	public void setFarmacieServiceLCCE(FarmaciaService farmacieServiceLCCE) {
		this.farmacieServiceLCCE = farmacieServiceLCCE;
	}

	public CCConsensoINIExtServicePortType getStatoConsensiINI() {
		return statoConsensiINI;
	}

	public void setStatoConsensiINI(CCConsensoINIExtServicePortType statoConsensiINI) {
		this.statoConsensiINI = statoConsensiINI;
	}

	public RicercaPazienteServizi getRicercaPazienteServizi() {
		return ricercaPazienteServizi;
	}

	public void setRicercaPazienteServizi(RicercaPazienteServizi ricercaPazienteServizi) {
		this.ricercaPazienteServizi = ricercaPazienteServizi;
	}


}
