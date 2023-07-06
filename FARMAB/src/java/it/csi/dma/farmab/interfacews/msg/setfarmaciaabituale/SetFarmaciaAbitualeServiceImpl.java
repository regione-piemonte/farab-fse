/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.annotations.SchemaValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.dma.dmadd.audit.exceptions.CatalogoLogAuditLowDaoException;
import it.csi.dma.dmadd.deleghebe.DelegaCittadino;
import it.csi.dma.dmadd.deleghebe.DelegaServizio;
import it.csi.dma.dmadd.deleghebe.GetDelegantiResponse;
import it.csi.dma.farmab.SpringApplicationContextProvider;
import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabFarmacieAbitualiController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTFarmaciaAbitualeDescDomain;
import it.csi.dma.farmab.integration.dao.LogAuditDao;
import it.csi.dma.farmab.integration.dao.dto.AuditDto;
import it.csi.dma.farmab.integration.dao.dto.CatalogoLogAuditLowDto;
import it.csi.dma.farmab.interfacews.IF.SetFarmaciaAbitualeService;
import it.csi.dma.farmab.service.DelegheElencoServizi;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;
import it.csi.iccws.dmacc.FarmaciaService;
import it.csi.iccws.dmacc.GetFarmacieAderentiRequest;
import it.csi.iccws.dmacc.GetFarmacieAderentiResponse;


@SchemaValidation(enabled = false)
@WebService(targetNamespace = "http://setfarmaciaabituale.msg.interfacews.farmab.dma.csi.it/", name = "SetFarmaciaAbitualeService", portName = "SetFarmaciaAbitualePort", endpointInterface = "it.csi.dma.farmab.interfacews.IF.SetFarmaciaAbitualeService")
public class SetFarmaciaAbitualeServiceImpl implements SetFarmaciaAbitualeService {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private DelegheElencoServizi delegheElencoServizi;

	@Autowired
	SpringApplicationContextProvider springApplicationContextProvider;

	@Autowired
	FarmabFarmacieAbitualiController farmabFarmacieAbitualiController;

	@Autowired
	FarmabController farmabController;

	@Autowired
	FarmabLog farmabLog;

	@Autowired
	FarmaciaService farmacieServiceLCCE;

	@Autowired
	LogAuditDao logAudit;

	@Resource
	protected WebServiceContext wsContext;

	@Override
	public SetFarmaciaAbitualeResponse setFarmaciaAbituale(SetFarmaciaAbitualeRequest setFarmaciaAbituale)  {
		log.info("SetFarmaciaAbitualeServiceImpl::setFarmaciaAbituale");

		SetFarmaciaAbitualeResponse response =new SetFarmaciaAbitualeResponse();
		// variabile che contiene la farmacia ricavata da LCCE per ottimizzare la chiamo senza fare controlli se il parametro Ã¨ valorizzato
		GetFarmacieAderentiResponse farmResp=null;
		Errore eccezioneLcce= null;
		//controllo che il campo non sia troppo grande per lcce
		if(setFarmaciaAbituale != null && setFarmaciaAbituale.getCodiceFarmacia()!=null && setFarmaciaAbituale.getCodiceFarmacia().length()>Constants.CODICE_FARMACIA_LENGHT) {
			eccezioneLcce= new Errore();
			eccezioneLcce.setCodice(Constants.FAR_CC_0051);
			eccezioneLcce.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(eccezioneLcce.getCodice()), Constants.CODICE_FARMACIA_0050, setFarmaciaAbituale.getCodiceFarmacia()));
		}else if (setFarmaciaAbituale != null && setFarmaciaAbituale.getCodiceFarmacia()!=null && !setFarmaciaAbituale.getCodiceFarmacia().trim().isEmpty()) {
			try {
				farmResp=this.getFarmacieAderenteLLCE(setFarmaciaAbituale);
			}catch(Exception ex) {
				eccezioneLcce= new Errore();
				eccezioneLcce.setCodice(Constants.FAR_CC_FATAL);
				eccezioneLcce.setDescrizione("Eccezione inaspettata nel richiamare farmacieServiceLCCE.getFarmacieAderenti"+ex.getMessage());
			}
		}

		List<Errore> errore=this.ValidateRequest(setFarmaciaAbituale,farmResp);
		if (eccezioneLcce!=null) {
			errore.add(eccezioneLcce);
		}
		response.setEsito(Constants.FAIL_CODE);
		if(!errore.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errore;
			response.setElencoErrori(elencoErrori);
			return response;
		}

		//CHIAMATA A DELEGHE
    	try {
    		if (Constants.CITTADINO.equalsIgnoreCase(setFarmaciaAbituale.richiedente.getRuolo().getCodice()) && !setFarmaciaAbituale.getCfCittadino().equalsIgnoreCase(setFarmaciaAbituale.richiedente.getCodiceFiscale())) {
    			verificaRAutorizzazioneDeleghe(setFarmaciaAbituale,errore);
    		}
    	}catch(Exception ex) {
			ex.printStackTrace();
			Errore e= new Errore();
			e.setCodice(Constants.FAR_CC_0056);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errore.add(e);
		}
    	//verifico che esista almeno un idPaziente valido
    	if(farmabController.esistePazienteValido(setFarmaciaAbituale.getCfCittadino())<1) {
    		Errore e= new Errore();
			e.setCodice(Constants.FAR_CC_0072);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errore.add(e);
    	}

    	// risposta comune
    	if(errore.size()>0) {
    			ElencoErroriType elencoErrori= new ElencoErroriType();
    			elencoErrori.errore=errore;
    			response.setElencoErrori(elencoErrori);
    			return response;
    	}

		//in base all'operazione richiamo metodi diversi
		log.debug("controllo errori superato");
		//Gesione azione
		try {
			DmaccTFarmaciaAbitualeDescDomain farm=null;

				if ("C".equalsIgnoreCase(setFarmaciaAbituale.getAzione())) {
					farm=opInserimento(response,errore,setFarmaciaAbituale);
				} else if ("M".equalsIgnoreCase(setFarmaciaAbituale.getAzione())) {
					farm=opModifica(response, errore, setFarmaciaAbituale);
					//TODO la logica di restituzione di opModifica deve essere rivista!!!!!!
					//funzionasse il passaggio di valori byreference!!!!
					if (farm!=null) {
						farmResp=farm.getFarmResp();
					}
				} else if ("R".equalsIgnoreCase(setFarmaciaAbituale.getAzione())) {
					return opRimuovi(errore, setFarmaciaAbituale);
				} else {
					log.error("Azione non ammessa "+setFarmaciaAbituale.getAzione());
				}
			if(errore.size()>0) {
	    			ElencoErroriType elencoErrori= new ElencoErroriType();
	    			elencoErrori.errore=errore;
	    			response.setElencoErrori(elencoErrori);
	    			return response;
	    	}
			componiResponse(farm,response,farmResp);
			if(Constants.SUCCESS_CODE.equalsIgnoreCase(response.getEsito())){
				try {
					Long idIrec =  null;
					if(farm != null   ){
						idIrec = Long.valueOf(farm.getIdPaziente()) ;
					}
					if(idIrec == null){
						try {
							idIrec = farmabController.getIdIrecPaziente(setFarmaciaAbituale.getCfCittadino());
						}catch(Exception e) {
							log.info("Codice fiscale:"+setFarmaciaAbituale.getCfCittadino()+" di un fuori regione");
							idIrec=null;
						}
					}


					logAudit.insertTlogAudit(getLogAudit(Constants.CATALOG_SET_FARAB,
							setFarmaciaAbituale ,
							idIrec ));
				} catch (CatalogoLogAuditLowDaoException e) {
					log.error(e.getMessage());
				}
			}


		}catch(Exception e) {
			log.error(e.getMessage());
			response.setEsito(Constants.FAIL_CODE);
		}
		return response;
	}


	private AuditDto getLogAudit(String codiceCatalogo, SetFarmaciaAbitualeRequest req,  Long idPaziente
			 ) throws CatalogoLogAuditLowDaoException{

		MessageContext mc = wsContext.getMessageContext();
	    HttpServletRequest request = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
	    final String NOME_SERVIZIO = "SetFarmaciaAbitualeService";
		AuditDto audit = new AuditDto();
		if(req.getRichiedente() != null && req.getRichiedente().getApplicativoVerticale() != null){
			audit.setApplicativoVerticale(req.getRichiedente().getApplicativoVerticale().getCodice());
		}


		CatalogoLogAuditLowDto catalogo = null;
		if(isRuoloDelForAudit(req)){
			catalogo = logAudit.geCatalogoLogAudittByCodice(Constants.CATALOG_SET_FARAB_DEL);
			audit.setInformazioniTracciate(MessageFormat.format(catalogo.getDescrizione(), req.getCfCittadino(), req.getRichiedente().getCodiceFiscale()));
		}else{
			catalogo = logAudit.geCatalogoLogAudittByCodice(codiceCatalogo);
			audit.setInformazioniTracciate(catalogo.getDescrizione());
		}

		audit.setIdPaziente(idPaziente);
		audit.setIdCatalogoLogAudit(catalogo.getId());

		//audit.setIdTransazione(UUID.randomUUID().toString());
		if(req.getRichiedente() != null && req.getRichiedente().getApplicazione() != null){
			audit.setCodiceApplicazioneRichiedente(req.getRichiedente().getApplicazione().getCodice());
		}
		audit.setVisibileAlCittadino("S");
		audit.setIpChiamante(request.getRemoteAddr());

		audit.setCodiceServizio(codiceCatalogo);
		//if(req.getRichiedente() != null && !req.getCfCittadino().equals(req.getRichiedente().getCodiceFiscale() )) {
		if(isRuoloDelForAudit(req)){
			audit.setCodiceRuolo(Constants.RUOLO_DEL);
		}else if(req.getRichiedente() != null && req.getRichiedente().getRuolo() != null){
			audit.setCodiceRuolo(req.getRichiedente().getRuolo().getCodice());
		}
		audit.setNomeServizio(NOME_SERVIZIO);
		
		if(req.getRichiedente() != null){
			audit.setCodiceFiscaleUtente(req.getRichiedente().getCodiceFiscale());
		}else{
			audit.setCodiceFiscaleUtente(req.getCfCittadino());
		}
		
		

		return audit;
	}

	private boolean isRuoloDelForAudit(SetFarmaciaAbitualeRequest req){
		boolean ok = (req.getRichiedente() != null && !req.getCfCittadino().equals(req.getRichiedente().getCodiceFiscale()))
				&& (req.getRichiedente() != null && req.getRichiedente().getApplicazione() != null && req.getRichiedente().getApplicazione().getCodice().equalsIgnoreCase(Constants.SANSOL));

		return ok;
	}

	private void verificaRAutorizzazioneDeleghe(SetFarmaciaAbitualeRequest request,List<Errore> errore) throws Exception {
		GetDelegantiResponse delegaResponse=getDelegheElencoServizi().getDelegantiPerSetFarmaciaAbituale(request);
		if(delegaResponse!=null && Constants.SUCCESS_CODE.equalsIgnoreCase(delegaResponse.getEsito())) {
			log.debug("Esito della chiamata a deleghe="+delegaResponse.getEsito());
			String gradoDelega=null;
			if(delegaResponse.getDeleganti()!=null && delegaResponse.getDeleganti().getDelegante()!=null) {
				for (DelegaCittadino d :delegaResponse.getDeleganti().getDelegante()) {
					if(d.getCodiceFiscale().equalsIgnoreCase(request.getCfCittadino())){
						//verifico se delega forte
						if (d.getDeleghe()!=null && d.getDeleghe().getDelega()!=null){
							for (DelegaServizio de :d.getDeleghe().getDelega()) {
								if(Constants.CODICE_SERVIZIO_IN_DELEGHE.equalsIgnoreCase(de.getCodiceServizio())) {
									//se grado delega Ã¨ null lo forzo a FORTE
									if(de.getGradoDelega()== null || de.getGradoDelega().trim().isEmpty()) {
										gradoDelega=Constants.GRADO_DELEGA;
									} else {
										gradoDelega=de.getGradoDelega();
									}
								}
							}
						}
					}
				}
			}
			//
			if(gradoDelega!=null && gradoDelega.length()>1) {
				//verifico i dati sulla tabella dmacc_r_autorizzazione_deleghe
				List<Long> ids=farmabFarmacieAbitualiController.getIdVerificaAutorizDeleghe(request.getRichiedente().getRuolo().getCodice(), gradoDelega, "SET_FAR_ABI");
				if(ids==null || ids.size()<1) {
					Errore e= new Errore();
	    			e.setCodice(Constants.FAR_CC_0058);
	    			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
	    			errore.add(e);
				}
			} else {
				Errore e= new Errore();
				e.setCodice(Constants.FAR_CC_0057);
    			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
    			errore.add(e);
			}
		} else {
			//TODO errore nel richaime del servizio deleghe
			Errore e= new Errore();
			e.setCodice(Constants.FAR_CC_0057);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errore.add(e);
		}
		return;
	}

	private void componiResponse(DmaccTFarmaciaAbitualeDescDomain fa, SetFarmaciaAbitualeResponse response, GetFarmacieAderentiResponse farmResp) {
		log.info("SetFarmaciaAbitualeServiceImpl::componiResponse");
		response.setEsito(Constants.SUCCESS_CODE);
		if(fa!=null && fa.getFarmaciaCod()!=null && !fa.getFarmaciaCod().trim().isEmpty()) {
			Farmacia farm=new Farmacia();
			farm.setId(fa.getFarmAbitId()+"");
			farm.setCodFarmacia(fa.getFarmaciaCod());
			farm.setDataInizioVal(fa.getDataAssociazioneInizio());
			farm.setDataFineVal(fa.getDataAssociazioneFine());
			// restituisco la FarmaciaAderente
			if(farmResp!=null && farmResp.getFarmacie()!=null && farmResp.getFarmacie().getFarmaciaAderente()!=null) {
				it.csi.iccws.dmacc.FarmaciaAderente farmaciaAderente=farmResp.getFarmacie().getFarmaciaAderente().get(0);
				if (farmaciaAderente!=null) {
					farm.setDescrFarmacia(farmaciaAderente.getNome());
					IndirizzoType indirizzo= new IndirizzoType();
					if (farmaciaAderente.getIndirizzo()!=null) {
						indirizzo.setProvincia(farmaciaAderente.getIndirizzo().getProvincia());
						indirizzo.setComune(farmaciaAderente.getIndirizzo().getComune());
						indirizzo.setCap(farmaciaAderente.getIndirizzo().getCap());
						indirizzo.setIndirizzo(farmaciaAderente.getIndirizzo().getDescrizioneIndirizzo());
					}
					farm.setIndirizzoFarmacia(indirizzo);
				}
			}
			response.setFarmaciaAbituale(farm);
		}
	}

	private DmaccTFarmaciaAbitualeDescDomain opInserimento(SetFarmaciaAbitualeResponse response, List<Errore> errore, SetFarmaciaAbitualeRequest setFarmaciaAbituale) {
		log.info("SetFarmaciaAbitualeServiceImpl::opInserimento");
		response.setEsito(Constants.FAIL_CODE);

		// verifico che non esista una farmacia giÃ  inserita per il medesino cittadino in primis per dataInizioValidita
		if (getValidFarmAb(setFarmaciaAbituale.getCodiceFarmacia(),setFarmaciaAbituale.getCfCittadino(),setFarmaciaAbituale.getDataInizioValidita(),setFarmaciaAbituale.getDataFineValidita())>0) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0063);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errore.add(e);
			return null;
		}

		// ho controllato nella validate che il codice esista e quindi mi carico la farmacia
		DmaccTFarmaciaAbitualeDescDomain newFarm=null;
		try {
			newFarm=farmabFarmacieAbitualiController.setNewFarmaciaAbituale(setFarmaciaAbituale.getCfCittadino(), setFarmaciaAbituale.getRichiedente().getCodiceFiscale(), setFarmaciaAbituale.getCodiceFarmacia(), setFarmaciaAbituale.getDataInizioValidita(), setFarmaciaAbituale.getDataFineValidita());
	    }catch (Exception ex){
	    	log.error(ex.getMessage());
	    	Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0072);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errore.add(e);
	    }
		return newFarm;
	}

	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private DmaccTFarmaciaAbitualeDescDomain opModifica(SetFarmaciaAbitualeResponse response, List<Errore> errore, SetFarmaciaAbitualeRequest setFarmaciaAbituale)  {
		log.info("SetFarmaciaAbitualeServiceImpl::opModifica");
		response.setEsito(Constants.FAIL_CODE);
		DmaccTFarmaciaAbitualeDescDomain farmResponse=null;
		GetFarmacieAderentiResponse farmResp=null;
		//verifico che id sia pertinenza del cittadino
		try {
			if(setFarmaciaAbituale.getIdFarmaciaAbituale()==null || setFarmaciaAbituale.getIdFarmaciaAbituale().isEmpty()) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.ID_FARMACIA_ABITUALE_0050, setFarmaciaAbituale.getIdFarmaciaAbituale()));
				errore.add(e);
				return null;
			}
			//verifico che il tag dataFineValidita sia presente il controllo di validitÃ  Ã¨ fatto nella validate
			if(setFarmaciaAbituale.getDataFineValidita()==null || setFarmaciaAbituale.getDataFineValidita().isEmpty()) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_FINE_VALIDITA_0050, setFarmaciaAbituale.getDataFineValidita()));
				errore.add(e);
				return null;
			}

			DmaccTFarmaciaAbitualeDescDomain farmEsistente=farmabFarmacieAbitualiController.findFarmaciaAbitualeById(setFarmaciaAbituale.getIdFarmaciaAbituale(), setFarmaciaAbituale.getCfCittadino());
			if (farmEsistente == null) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0062);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				log.info("Errore di congruenza tra cittadino "+setFarmaciaAbituale.getCfCittadino()+" e id "+setFarmaciaAbituale.getIdFarmaciaAbituale());
				errore.add(e);
				return null;
			}
			//verifico che la data sia maggiore di
			if(!FarmabUtils.isDataGreaterThan(farmEsistente.getDataAssociazioneInizio(),setFarmaciaAbituale.getDataFineValidita())) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0051);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_FINE_VALIDITA_0051, setFarmaciaAbituale.getDataFineValidita()));
				errore.add(e);
				return null;
			}
			// verifico che la farmacia sia ancora valida
			try {
				//forzo codFarmacia nella request
				setFarmaciaAbituale.setCodiceFarmacia(farmEsistente.getFarmaciaCod());
				farmResp=this.getFarmacieAderenteLLCE(setFarmaciaAbituale);
				if (farmResp ==null || !Constants.SUCCESS_CODE.equalsIgnoreCase(farmResp.getEsito().value()) || farmResp.getFarmacie()==null || farmResp.getFarmacie().getFarmaciaAderente()==null ||farmResp.getFarmacie().getFarmaciaAderente().size()==0) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_FATAL);//non ho trovato nessun codice per famacia scaduta
					e.setDescrizione("Nessuna farmacia trovata per il Codice Farmacia"+farmEsistente.getFarmaciaCod());
					errore.add(e);
					return null;
				}
			}catch(Exception ex) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_FATAL);
				e.setDescrizione("Eccezione inaspettata nel richiamare farmacieServiceLCCE.getFarmacieAderenti"+ex.getMessage());
				errore.add(e);
				return null;
			}

			//nessun errore faccio update
			farmResponse=farmabFarmacieAbitualiController.updateFarmaciaAbitualeById(setFarmaciaAbituale.getIdFarmaciaAbituale(),setFarmaciaAbituale.getCfCittadino(),setFarmaciaAbituale.getRichiedente().getCodiceFiscale(), setFarmaciaAbituale.getDataFineValidita());
			if (farmResponse!=null) {
				farmResponse.setFarmacieAderentiResponse(farmResp);
			}
			  } catch (Exception ex) {
				  Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_FATAL);
					e.setDescrizione("Errore durante la modifica :"+ex.getMessage());
					errore.add(e);
					return null;
			  }

		return farmResponse;
	}

	private SetFarmaciaAbitualeResponse opRimuovi(List<Errore> errore, SetFarmaciaAbitualeRequest setFarmaciaAbituale) {
		log.info("SetFarmaciaAbitualeServiceImpl::opRimuovi");
		SetFarmaciaAbitualeResponse response = new SetFarmaciaAbitualeResponse();
		response.setEsito(Constants.FAIL_CODE);
		//verifico che id sia pertinenza del cittadino
		try {
			if(setFarmaciaAbituale.getCodiceFarmacia()==null || setFarmaciaAbituale.getCodiceFarmacia().isEmpty()) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_FARMACIA_0050, setFarmaciaAbituale.getCodiceFarmacia()));
				errore.add(e);
				return response;
			}
			/*DmaccTFarmaciaAbitualeDescDomain farmEsistente=farmabFarmacieAbitualiController.findFarmaciaAbitualeById(setFarmaciaAbituale.getIdFarmaciaAbituale(), setFarmaciaAbituale.getCfCittadino());
			if (farmEsistente == null) {
				Errore e =new Errore();
				e.setCodice("FAR-CC-0062");
				e.setDescrizione("Errore di congruenza tra cittadino "+setFarmaciaAbituale.getCfCittadino()+" e id "+setFarmaciaAbituale.getIdFarmaciaAbituale());
				errore.add(e);
				return response;
			}*/
			//nessun errore faccio rimozione logica
			if(farmabFarmacieAbitualiController.removeFarmaciaAbitualeByCod(setFarmaciaAbituale.getCodiceFarmacia(), setFarmaciaAbituale.getCfCittadino(),setFarmaciaAbituale.getRichiedente().getCodiceFiscale())) {
				response.setEsito(Constants.SUCCESS_CODE);
				//scrivo nell'audit
				if(Constants.SUCCESS_CODE.equalsIgnoreCase(response.getEsito())){
					try {
						Long idIrec = null;
						try {
							idIrec = farmabController.getIdIrecPaziente(setFarmaciaAbituale.getCfCittadino());
						}catch(Exception e) {
							log.info("Codice fiscale:"+setFarmaciaAbituale.getCfCittadino()+" di un fuori regione");
							idIrec=null;
						}

						logAudit.insertTlogAudit(getLogAudit(Constants.CATALOG_SET_FARAB,
								setFarmaciaAbituale ,
								idIrec ));
					} catch (CatalogoLogAuditLowDaoException e) {
						log.error(e.getMessage());
					}
				}
			} else {
				//Nessun record trovato
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0062);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errore.add(e);
			}
		}catch (Exception ex) {
			log.error(ex.getMessage());
	    	Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_FATAL);
			e.setDescrizione("Errore durante la remove"+ex.getMessage());
			errore.add(e);
		}
		if(!errore.isEmpty()) {
				//restituisco fallimento con la lista degli errori contenuti nella request
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errore;
				response.setElencoErrori(elencoErrori);
		}
		return response;
	}

	/*eliminami
	private long getValidFarmAb_old(String codFarmacia, String cittadinoCf) {
		log.info("SetFarmaciaAbitualeServiceImpl::getValidFarmAb_old");
		//verifico che non sia giÃ  stata inserita una farmacia valida con lo stesso codice farmacia
		List<DmaccTFarmaciaAbitualeDescDomain> farmab=farmabFarmacieAbitualiController.getFarmacieAbituali(cittadinoCf);
		if (farmab!=null) {
			for (DmaccTFarmaciaAbitualeDescDomain fa :farmab) {
				if(fa.getFarmaciaCod().equalsIgnoreCase(codFarmacia)) {
					return fa.getFarmAbitId();
				}
			}
		}
		return -1;
	}
    */

	private long getValidFarmAb(String codFarmacia, String cittadinoCf, String dataMinToCheck, String dataMaxToCheck) {
		log.info("SetFarmaciaAbitualeServiceImpl::getValidFarmAb");
		//verifico che non sia giÃ  stata inserita una farmacia valida con lo stesso codice farmacia
		List<Long> inRange=null;
		try {
			if (dataMinToCheck!=null && !dataMinToCheck.trim().isEmpty()) {
			 inRange=farmabFarmacieAbitualiController.isValidRangeForDate(codFarmacia, cittadinoCf, dataMinToCheck, dataMaxToCheck);
			}
		}catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
		log.debug("Record trovati:"+(inRange!=null&&inRange.size()>0?inRange.get(0):0));
		return inRange!=null&&inRange.size()>0?inRange.get(0):0;
	}

	//GET E SET PER CARICARE LA CLASSE DelegheElencoServizi
	public DelegheElencoServizi getDelegheElencoServizi() {
		return delegheElencoServizi;
	}

	public void setDelegheElencoServizi(DelegheElencoServizi delegheElencoServizi) {
		this.delegheElencoServizi = delegheElencoServizi;
	}

	 private List<Errore> ValidateRequest(SetFarmaciaAbitualeRequest request,GetFarmacieAderentiResponse farmResp){
	    	log.info("SetFarmaciaAbitualeServiceImpl::ValidateRequest");
			List<Errore> errori= new ArrayList<Errore>();
			errori=this.ValidateRichiedente(request.getRichiedente());
			//--------------------------------------------------------------------------
			//--------------------------------------------------------------------------
			//controlli per tag cfCittadino
			if(request.getCfCittadino()==null || request.getCfCittadino().isEmpty()) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_CITTADINO_0050));
				errori.add(e);
			} else {
				if(FarmabUtils.isNotValidCf(request.getCfCittadino())) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_CITTADINO_0051, request.getCfCittadino()));
					errori.add(e);
				}
			}
			//controlli per tag azione
			if(request.getAzione()==null || request.getAzione().isEmpty()) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "azione"));
				errori.add(e);
			} else {
				//verifico cha abbia un valore ammissibile
				/*String azione=Arrays.stream(Constants.AZIONI)
                        .filter(x -> x.startsWith("C"))
                        .findFirst()
                        .orElse(null);*/
				if(Arrays.stream(Constants.AZIONI)
                .filter(x -> x.equalsIgnoreCase(request.getAzione()))
                .findFirst()
                .orElse(null) == null) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "azione",request.getAzione()));
					errori.add(e);
				}
			}
			//controlli per tag codiceFarmacia
			if (!("M".equalsIgnoreCase(request.getAzione()))){
				if(request.getCodiceFarmacia()==null || request.getCodiceFarmacia().isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "codiceFarmacia"));
					errori.add(e);
				} else {
					log.debug("Verifico che esista il codiceFarmacia="+request.getCodiceFarmacia());
					if (errori.isEmpty()) {
						if(farmResp==null ){
							Errore e =new Errore();
							e.setCodice(Constants.FAR_CC_0051);
							e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "codiceFarmacia",request.getCodiceFarmacia()));
							errori.add(e);
						}
					}
				}
			}
			//controlli per tag dataInizioValidita solo per Inserimento
			if ("C".equalsIgnoreCase(request.getAzione())){
				if(request.getDataInizioValidita()==null || request.getDataInizioValidita().isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_INIZIO_VALIDITA_0050));
					errori.add(e);
				} else {
					if(!FarmabUtils.isValidDate(request.getDataInizioValidita())) {
						Errore e =new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_INIZIO_VALIDITA_0051, request.getDataInizioValidita()));
						errori.add(e);
					} else if(!FarmabUtils.isGreaterThanToday(request.getDataInizioValidita())) {
							//verifico che la dataInizioValidita non sia nel passato
							Errore e =new Errore();
							e.setCodice(Constants.FAR_CC_0051);
							e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_INIZIO_VALIDITA_0051, request.getDataInizioValidita()));
							errori.add(e);
						}

				}
			}
			//controlli data fine se Ã¨ presente
			if(request.getDataFineValidita() !=null && !request.getDataFineValidita().trim().isEmpty()) {
				if(!FarmabUtils.isValidDate(request.getDataFineValidita()) ||!FarmabUtils.isGreaterThanToday(request.getDataFineValidita())) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.DATA_FINE_VALIDITA_0051, request.getDataFineValidita()));
					errori.add(e);
				}
			}
			// cfCittadino<> Richiedente.codiceFiscale e applicazione.codice=SANSOL restituisce errore FAR-CCC-0061
			/*if(errori.isEmpty() && (!request.getCfCittadino().equalsIgnoreCase(request.getRichiedente().getCodiceFiscale()) && "SANSOL".equalsIgnoreCase(request.getRichiedente().getApplicazione().getCodice()))) {
				Errore e = new Errore();
				e.setCodice("FAR-CC-0061");
				e.setDescrizione("Il codice fiscale dell'assistito deve coincidere con quello del richiedente");
				errori.add(e);
			}*/
			return errori;
		}

		private List<Errore> ValidateRichiedente(Richiedente richiedente) {
	    	log.info("SetFarmaciaAbitualeServiceImpl::ValidateRichiedente");

	    	List<Errore> errori= new ArrayList<Errore>();
			if (richiedente!=null) {
				//controlli per tag ApplicativoVerticale
				//verifico che esista tag applicazione altrimenti delego l'errore al tag successivo
				if (richiedente.getApplicazione()!=null && ("SANSOL".compareTo(richiedente.getApplicazione().getCodice())==0)) {//utile per non avere null pointer exception
					if(richiedente.getApplicativoVerticale()!=null ) {
						if(richiedente.getApplicativoVerticale().getCodice()==null || richiedente.getApplicativoVerticale().getCodice().isEmpty()) {
							Errore e =new Errore();
							e.setCodice(Constants.FAR_CC_0050);
							e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICATIVO_VERTICALE_0050));
							errori.add(e);
						} else {
							//controllo che sia formalmente corretto ovvero che valga SANSOL quando
							if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())==0 && "FARAB".compareTo(richiedente.getApplicativoVerticale().getCodice())!=0) {
								Errore e =new Errore();
								e.setCodice(Constants.FAR_CC_0051);
								e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICATIVO_VERTICALE_0051, richiedente.getApplicativoVerticale().getCodice()));
								errori.add(e);
							}
						}
					} else {
							Errore e =new Errore();
							e.setCodice(Constants.FAR_CC_0050);
							e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICATIVO_VERTICALE_0050));
							errori.add(e);
					}
				}
				// end controlli per tag ApplicativoVerticale
	//--------------------------------------------------------------------------
				//controlli per tag applicazione
				if(richiedente.getApplicazione()!=null) {
					if(richiedente.getApplicazione().getCodice()==null || richiedente.getApplicazione().getCodice().isEmpty()) {
						Errore e =new Errore();
						e.setCodice(Constants.FAR_CC_0050);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICAZIONE_0050));
						errori.add(e);
					} else {
						//controllo che sia formalmente corretto ovvero che valga SANSOL o WEBAPP_CM
						if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())!=0 && "WEBAPP_CM".compareTo(richiedente.getApplicazione().getCodice())!=0) {
							Errore e =new Errore();
							e.setCodice(Constants.FAR_CC_0051);
							e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_APPLICAZIONE_0051,richiedente.getApplicazione().getCodice()));
							errori.add(e);
						}
					}
				} else {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICAZIONE_0050));
					errori.add(e);
				}
				//end controlli per tag applicazione
	//--------------------------------------------------------------------------
				//controlli per tag codiceFiscale
				if (richiedente.getCodiceFiscale() != null) {
					if (richiedente.getCodiceFiscale().isEmpty() || richiedente.getCodiceFiscale().trim().isEmpty()) {
						Errore e = new Errore();
						e.setCodice(Constants.FAR_CC_0050);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0050));
						errori.add(e);
					}
					if (FarmabUtils.isNotValidCf(richiedente.getCodiceFiscale())) {
						Errore e = new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0051,richiedente.getCodiceFiscale()));
						errori.add(e);
					}
				} else {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0050));
					errori.add(e);
				}
				//end controlli per tag codiceFiscale
				//controlli per ip non previsti in analisi sarebbe il caso almeno di controllare che non si rompa insert.....
	//--------------------------------------------------------------------------
				//controlli per tag numeroTransazione
				if(richiedente.getNumeroTransazione().isEmpty()||richiedente.getNumeroTransazione().trim().isEmpty()||richiedente.getNumeroTransazione().length()<1) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.NUMERO_TRANSAZIONE_0050));
					errori.add(e);
				} else if (richiedente.getNumeroTransazione().length()>Constants.NUMERO_TRANSAZIONE_LENGHT){
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.NUMERO_TRANSAZIONE_0050,richiedente.getNumeroTransazione()));
					errori.add(e);
				}
				//end controlli per tag numeroTransazione
	//--------------------------------------------------------------------------
				//Controlli ruolo
				if(richiedente.getRuolo()==null || richiedente.getRuolo().getCodice()==null || richiedente.getRuolo().getCodice().trim().isEmpty()) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0050));
					errori.add(e);
				} else {
					List <String> descrizRuololist=farmabController.getDescrizioneRuoloByCod(richiedente.getRuolo().getCodice());
					if(descrizRuololist==null || descrizRuololist.size()<1) {
						Errore e = new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0051,richiedente.getRuolo().getCodice()));
						errori.add(e);
					} else {
						//imposto la descrizione del ruolo nella richiesta
						richiedente.getRuolo().setDescrizione(descrizRuololist.get(0));
					}
				}
				// end controlli richiedente vaolorizzato
			} else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0051);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "richiedente"));
				errori.add(e);
			}


			return errori;
		}

		private GetFarmacieAderentiResponse getFarmacieAderenteLLCE(SetFarmaciaAbitualeRequest request) {
			log.info("SetFarmaciaAbitualeServiceImpl::getFarmacieAderenteLLCE");
			GetFarmacieAderentiResponse farmResp=null;
			//mi ricavo il dettagio dei dati della farmacia dal servizio LCCE
			try {
				GetFarmacieAderentiRequest getFarmacieAderentiRequest= new GetFarmacieAderentiRequest();
				it.csi.iccws.dmacc.Richiedente richiedente=new it.csi.iccws.dmacc.Richiedente();
				richiedente.setApplicazioneChiamante(request.getRichiedente().getApplicazione().getCodice());
				richiedente.setCodiceFiscaleRichiedente(request.getRichiedente().getCodiceFiscale());
				richiedente.setUuid(request.getRichiedente().getNumeroTransazione());
				getFarmacieAderentiRequest.setRichiedente(richiedente);
				it.csi.iccws.dmacc.DatiFarmacia datiFarmacia= new it.csi.iccws.dmacc.DatiFarmacia();
				datiFarmacia.getCodiceFarmacia().add(request.getCodiceFarmacia());
				getFarmacieAderentiRequest.setDatiFarmacia(datiFarmacia);
				log.info("RICHIAMO SERVIZIO ESTERNO farmacieServiceLCCE.getFarmacieAderenti per codFarmacia="+request.getCodiceFarmacia());
				long startTime = System.currentTimeMillis();
				farmResp=farmacieServiceLCCE.getFarmacieAderenti(getFarmacieAderentiRequest);
				log.info("RISPOSTA SERVIZIO ESTERNO farmacieServiceLCCE.getFarmacieAderenti:"+(System.currentTimeMillis()-startTime)+" Millis");
				if(farmResp!=null && farmResp.getFarmacie()!=null && farmResp.getFarmacie().getFarmaciaAderente()!=null) {
					it.csi.iccws.dmacc.FarmaciaAderente farmaciaAderente=farmResp.getFarmacie().getFarmaciaAderente().get(0);
					if (farmaciaAderente!=null) {
    					return farmResp;
					}
				}
				return null;
			}catch (Exception e) {
				log.error("Eccezione nel richiamre farmacieServiceLCCE.getFarmacieAderenti"+e.getMessage());
				throw e;
			}

		}

		public FarmaciaService getFarmacieServiceLCCE() {
			return farmacieServiceLCCE;
		}

		public void setFarmacieServiceLCCE(FarmaciaService farmacieServiceLCCE) {
			this.farmacieServiceLCCE = farmacieServiceLCCE;
		}

}
