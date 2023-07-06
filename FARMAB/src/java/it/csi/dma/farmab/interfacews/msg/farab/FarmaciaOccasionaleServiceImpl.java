/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.farab;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabFarmOccasionaliController;
import it.csi.dma.farmab.controller.FarmabFarmacieAbitualiController;
import it.csi.dma.farmab.controller.FarmabGestioneDeviceController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTFarmaciaOccasionaleRich;
import it.csi.dma.farmab.interfacews.msg.farab.util.AuditFarmabServiceComponent;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;
import it.csi.iccws.dmacc.FarmaciaService;
import it.csi.iccws.dmacc.GetFarmacieAderentiRequest;
import it.csi.iccws.dmacc.GetFarmacieAderentiResponse;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmaciaOccasionaleServiceImpl {
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	FarmabLog farmabLog;

	@Autowired
	FarmabController farmabController;

	@Autowired
	FarmabGestioneDeviceController farmabGestioneDeviceController;

	@Autowired
	FarmabFarmOccasionaliController farmabFarmOccasionaliController;

	@Autowired
	FarmabFarmacieAbitualiController farmabFarmacieAbitualiController;

	@Autowired
	FarmaciaService farmacieServiceLCCE;

	@Autowired
	AuditFarmabServiceComponent auditController;


	public FarmaciaService getFarmacieServiceLCCE() {
		return farmacieServiceLCCE;
	}

	public void setFarmacieServiceLCCE(FarmaciaService farmacieServiceLCCE) {
		this.farmacieServiceLCCE = farmacieServiceLCCE;
	}

	public GetFarmaciaOccasionaleResponse getFarmaciaOccasionale(GetFarmaciaOccasionale getFarmaciaOccasionaleRequest) {
		log.info("FarmaciaOccasionaleServiceImpl::getFarmaciaOccasionale");
		GetFarmaciaOccasionaleResponse response= new GetFarmaciaOccasionaleResponse();
		response.setEsito(Constants.FAIL_CODE);
		Long idPazienteCertificato=null;
		List<Errore> errori=ValidateRequestGet(getFarmaciaOccasionaleRequest);
		String SessioneFarmaciaInMin="";
		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}
		//controllo che esista uuid device per quel cittadino
		String cf=farmabGestioneDeviceController.controllaCodiceFiscaleDispositivoAssociato(getFarmaciaOccasionaleRequest.getUuidDevice());
		if(cf==null ||cf.trim().isEmpty()) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0067);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errori.add(e);
		} else {
			BigDecimal device=farmabGestioneDeviceController.controllaDispositivoAssociato(cf, getFarmaciaOccasionaleRequest.getUuidDevice(), Constants.DEVICE_CERTIFICATO);
			if(device==null) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0067);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errori.add(e);
			}
		}
		SessioneFarmaciaInMin=getSessionIntervalFarm();
		if(SessioneFarmaciaInMin==null||SessioneFarmaciaInMin.isEmpty()) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0000);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice())+" DB incongruente :"+Constants.FAROCC_CODSES_TIMETOLIVE);
			errori.add(e);
		}
		if(!errori.isEmpty()) {
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}
		//device trovato
		List<DmaccTFarmaciaOccasionaleRich> farmacieOcc=farmabFarmOccasionaliController.findFarmacieOccasionali(cf, idPazienteCertificato);
		if(farmacieOcc==null || farmacieOcc.isEmpty() || farmacieOcc.size()<1) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0068);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errori.add(e);
		} else {
			try {
				FarmaciaOccasionaleType fo=getIndirizzoFarmaciaOccasionale(getFarmaciaOccasionaleRequest.getRichiedente().getApplicazione().getCodice(),cf,getFarmaciaOccasionaleRequest.getRichiedente().getNumeroTransazione(),farmacieOcc.get(0),errori);
				response.setEsito(Constants.SUCCESS_CODE);
				if (fo!=null) {
					fo.setIdRich(farmacieOcc.get(0).getFarmOccRichId()+"");
					fo.setDataRichiesta(farmacieOcc.get(0).getDataInserimento());
					response.setFarmaciaOccasionale(fo);
				}
			}catch (Exception ex) {
				log.error(ex.getMessage());
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0000);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice())+ex.getMessage());
				errori.add(e);
			}
		}
		if(!errori.isEmpty()) {
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
		}
		return response;
	}

	public AutorizzaFarmaciaOccasionaleResponse autorizzaFarmaciaOccasionale(
			AutorizzaFarmaciaOccasionale autorizzaFarmaciaOccasionaleRequest, String remoteClientAddr) {
		log.info("FarmaciaOccasionaleServiceImpl::autorizzaFarmaciaOccasionale");
		AutorizzaFarmaciaOccasionaleResponse response=new AutorizzaFarmaciaOccasionaleResponse();
		response.setEsito(Constants.FAIL_CODE);
		List<Errore> errori=ValidateRequestAutoriz(autorizzaFarmaciaOccasionaleRequest);
		String SessioneFarmaciaInMin="";
		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}
		//controllo che esista uuid device per quel cittadino
		Long idPazienteCertificato=null;
		String codiceFiscaleCertificato=farmabGestioneDeviceController.controllaCodiceFiscaleDispositivoAssociato(autorizzaFarmaciaOccasionaleRequest.getUuidDevice());
		if(codiceFiscaleCertificato==null) {
			idPazienteCertificato = farmabGestioneDeviceController.controllaidPazienteDispositivoAssociato(autorizzaFarmaciaOccasionaleRequest.getUuidDevice());
			if(idPazienteCertificato==null) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0067);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errori.add(e);
			}
		} else {
			//step 3 analisi
			//prendo idPaziente pet audit
			idPazienteCertificato = farmabController.getIdIrecPaziente(codiceFiscaleCertificato);
			List<Long> countRecord=farmabGestioneDeviceController.controllaDispositivoAssociatoPerCF(codiceFiscaleCertificato);
			if(countRecord==null || countRecord.isEmpty()) {
				if (countRecord.get(0) > 0) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0064);
					e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
					errori.add(e);
				}
			}
		}
		SessioneFarmaciaInMin=getSessionIntervalFarm();
		if(SessioneFarmaciaInMin==null||SessioneFarmaciaInMin.isEmpty()) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0000);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice())+" DB incongruente :"+Constants.FAROCC_CODSES_TIMETOLIVE);
			errori.add(e);
		}
		if(!errori.isEmpty()) {
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}
		//device trovato
		List<DmaccTFarmaciaOccasionaleRich> farmacieOcc=farmabFarmOccasionaliController.findFarmacieOccasionali(codiceFiscaleCertificato, idPazienteCertificato);
		if(farmacieOcc==null || farmacieOcc.isEmpty() || farmacieOcc.size() < 1) {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0068);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errori.add(e);
		} else {
			try {
				//update
				int success=farmabFarmOccasionaliController.updateFarmacieOccasionali(farmacieOcc);
				if (success==0){
					log.error("Update non riuscito in FarmaciaOccasionaleServiceImpl::autorizzaFarmaciaOccasionale");
				}
				/*
				DmaccTCatalogoFarmacie farmacia=farmabFarmacieAbitualiController.getFirstFarmaciaFromCatalogoFarmacie(farmacieOcc.get(0).getFarmaciaCod());

				FarmaciaOccasionaleType fo=new FarmaciaOccasionaleType();
				fo.setIdRich(farmacieOcc.get(0).getFarmOccRichId()+"");
				fo.setCodFarmacia(farmacia.getCodFarmacia());
				fo.setDescrFarmacia(farmacia.getDenomFarmacia());
				fo.setDataRichiesta(farmacieOcc.get(0).getDataInserimento());
				IndirizzoType ind =new IndirizzoType();
				ind.setIndirizzo(farmacia.getIndirizzo()+ " "+farmacia.getNumeroCivico());
				ind.setCap(farmacia.getCap());
				ind.setComune(farmacia.getComune());
				ind.setProvincia(farmacia.getDenomProvincia());
				fo.setIndirizzoFarmacia(ind);
				*/
				FarmaciaOccasionaleType fo=getIndirizzoFarmaciaOccasionale(autorizzaFarmaciaOccasionaleRequest.getRichiedente().getApplicazione().getCodice(),autorizzaFarmaciaOccasionaleRequest.getRichiedente().getCodiceFiscale(),autorizzaFarmaciaOccasionaleRequest.getRichiedente().getNumeroTransazione(),farmacieOcc.get(0),errori);
				//mettere nella getIndirizzoFarmaciaOccasionale un controllo a null per eventuale errore indirizzo non trovato, vedi metodo getIndirizzoFarmaciaOccasionale
				if (fo!=null) {
					//se lcce ha restituito un indirizzo completiamo la response con i dati mancanti
					fo.setIdRich(farmacieOcc.get(0).getFarmOccRichId()+"");
					fo.setDataRichiesta(farmacieOcc.get(0).getDataInserimento());
					response.setFarmaciaOccasionale(fo);
				}
				response.setEsito(Constants.SUCCESS_CODE);
				log.info("[FarmaciaOccasionaleServiceImpl::autorizzaFarmaciaOccasionale] prima di scriveree su log di audit");
				final String NOME_SERVIZIO = "autorizzaFarmaciaOccasionale";
				//operazione ok faccio audit
				try {

					log.info("[FarmaciaOccasionaleServiceImpl::autorizzaFarmaciaOccasionale] prima di componiauditEsavlaaudit");
					auditController.componiAuditESalvaAudit(Constants.CATALOG_AUT_FAR_OCC, autorizzaFarmaciaOccasionaleRequest.getRichiedente(), remoteClientAddr, idPazienteCertificato,
							codiceFiscaleCertificato, NOME_SERVIZIO);
				} catch (Exception e) {
					log.error(NOME_SERVIZIO +  "Errore nella scrittura dell'audit: " + e.getMessage());
				}


			}catch (Exception ex) {
				log.error(ex.getMessage());
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0000);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice())+ex.getMessage());
				errori.add(e);
			}
		}



		if(!errori.isEmpty()) {
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
		}
		return response;
	}

	private FarmaciaOccasionaleType getIndirizzoFarmaciaOccasionale(String appChiamante, String codiceFiscale, String uuid, DmaccTFarmaciaOccasionaleRich fa,List<Errore> errori) {
		log.info("FarmaciaOccasionaleServiceImpl::getIndirizzoFarmaciaOccasionale");

		FarmaciaOccasionaleType farm=new FarmaciaOccasionaleType();
		try {
			GetFarmacieAderentiRequest getFarmacieAderentiRequest= new GetFarmacieAderentiRequest();
			it.csi.iccws.dmacc.Richiedente richiedente=new it.csi.iccws.dmacc.Richiedente();
			//richiedente.setApplicazioneChiamante(autorizzaFarmaciaOccasionaleRequest.getRichiedente().getApplicazione().getCodice());
			richiedente.setApplicazioneChiamante(appChiamante);
			//richiedente.setCodiceFiscaleRichiedente(autorizzaFarmaciaOccasionaleRequest.getRichiedente().getCodiceFiscale());
			richiedente.setCodiceFiscaleRichiedente(codiceFiscale);
			//richiedente.setUuid(autorizzaFarmaciaOccasionaleRequest.getRichiedente().getNumeroTransazione());
			richiedente.setUuid(uuid);
			getFarmacieAderentiRequest.setRichiedente(richiedente);
			it.csi.iccws.dmacc.DatiFarmacia datiFarmacia= new it.csi.iccws.dmacc.DatiFarmacia();
			datiFarmacia.getCodiceFarmacia().add(fa.getFarmaciaCod());
			getFarmacieAderentiRequest.setDatiFarmacia(datiFarmacia);
			log.info("RICHIAMO SERVIZIO ESTERNO farmacieServiceLCCE.getFarmacieAderenti per codFarmacia="+fa.getFarmaciaCod());
			long startTime = System.currentTimeMillis();
			GetFarmacieAderentiResponse farmResp=farmacieServiceLCCE.getFarmacieAderenti(getFarmacieAderentiRequest);
			log.info("RISPOSTA SERVIZIO ESTERNO farmacieServiceLCCE.getFarmacieAderenti:"+(System.currentTimeMillis()-startTime)+" Millis");

			if(farmResp!=null && farmResp.getFarmacie()!=null && farmResp.getFarmacie().getFarmaciaAderente()!=null) {
				it.csi.iccws.dmacc.FarmaciaAderente farmaciaAderente=farmResp.getFarmacie().getFarmaciaAderente().get(0);
				if (farmaciaAderente!=null) {
					farm.setDescrFarmacia(farmaciaAderente.getNome());
					farm.setCodFarmacia(farmaciaAderente.getCodice());
					IndirizzoType indirizzo= new IndirizzoType();
					if (farmaciaAderente.getIndirizzo()!=null) {
						indirizzo.setProvincia(farmaciaAderente.getIndirizzo().getProvincia());
						indirizzo.setComune(farmaciaAderente.getIndirizzo().getComune());
						indirizzo.setCap(farmaciaAderente.getIndirizzo().getCap());
						indirizzo.setIndirizzo(farmaciaAderente.getIndirizzo().getDescrizioneIndirizzo());
					}
					farm.setIndirizzoFarmacia(indirizzo);
				}
			}/* else {
				//controllare se restituisce null , nel caso accada bisogna ?????
				return null;
			}*/

		}catch (Exception ex) {
			log.error("Eccezione nel richiamre farmacieServiceLCCE.getFarmacieAderenti"+ex.getMessage());
			Errore e= new Errore();
			e.setCodice("FAR-CC-0056");
			e.setDescrizione("Eccezione inaspettata nel richiamare farmacieServiceLCCE.getFarmacieAderenti"+ex.getMessage());
			errori.add(e);
			//se vogliamo intercettare l'errore "indirizzo non trovato" basta impostare farm=null; e controllarlo dopo il richiamo
		}
		return farm;
	}

	private String getSessionIntervalFarm() {
		log.info("FarmaciaOccasionaleServiceImpl::getSessionIntervalFarm");
		String tempo="";
		try {
			tempo=farmabFarmOccasionaliController.getIntervalSessionFarmaciaMin();
		}catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return tempo;
	}

	private List<Errore> ValidateRequestGet(GetFarmaciaOccasionale request){
		log.info("FarmaciaOccasionaleServiceImpl::ValidateRequestGet");
		List<Errore> errori= new ArrayList<Errore>();
		if(request==null) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_FATAL);
			e.setDescrizione("Richiesta non valida");
			errori.add(e);
			return errori;
		}
		errori=this.ValidateRichiedente(request.getRichiedente());
		//TODO controlli di merito
		if(request.getUuidDevice()==null || request.getUuidDevice().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.UUID_DEVICE_0050));
			errori.add(e);
		}
		return errori;
	}

	private List<Errore> ValidateRequestAutoriz(AutorizzaFarmaciaOccasionale request){
		log.info("FarmaciaOccasionaleServiceImpl::ValidateRequestAutoriz");
		List<Errore> errori= new ArrayList<Errore>();
		if(request==null) {
			Errore e = new Errore();
			e.setCodice("FAR_CC_XXXX");
			e.setDescrizione("Richiesta non valida");
			errori.add(e);
			return errori;
		}
		errori=this.ValidateRichiedente(request.getRichiedente());
		//TODO controlli di merito
		return errori;
	}

	private List<Errore> ValidateRichiedente(Richiedente richiedente) {
		log.info("FarmaciaOccasionaleServiceImpl::ValidateRichiedente");
		List<Errore> errori= new ArrayList<Errore>();
		if (richiedente!=null) {
			//controlli per tag ApplicativoVerticale N.A.
			/*
			if(richiedente.getApplicativoVerticale()!=null) {
				if(richiedente.getApplicativoVerticale().getCodice()==null || richiedente.getApplicativoVerticale().getCodice().isEmpty()) {
					Errore e =new Errore();
					e.setCodice("FAR-CC-0050");
					e.setDescrizione("Il parametro applicativoVerticale.codice deve essere valorizzato");
					errori.add(e);
				} else {
					//controllo che sia formalmente corretto ovvero che valga SANSOL quando
					if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())==0 && "FARAB".compareTo(richiedente.getApplicativoVerticale().getCodice())!=0) {
						Errore e =new Errore();
						e.setCodice("FAR-CC-0051");
						e.setDescrizione("Il parametro applicativoVerticale.codice contiene il valore "+richiedente.getApplicativoVerticale().getCodice()+" non corretto");
						errori.add(e);
					}
				}
			} else {
				Errore e =new Errore();
				e.setCodice("FAR-CC-0050");
				e.setDescrizione("Il parametro applicativoVerticale deve essere valorizzato");
				errori.add(e);
			}
			*/
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
					//controllo che sia formalmente corretto ovvero che valga SANSOL
					if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())!=0) {
						Errore e =new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_APPLICAZIONE_0051, richiedente.getApplicazione().getCodice()));
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
			if (richiedente.getCodiceFiscale() != null && !richiedente.getCodiceFiscale().trim().isEmpty()) {
				//IL CF NON E' CONSIDERATO OBBLIGATORIO
				/*if (richiedente.getCodiceFiscale().isEmpty() || richiedente.getCodiceFiscale().trim().isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
					errori.add(e);
				}*/
				if (FarmabUtils.isNotValidCf(richiedente.getCodiceFiscale())) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0051, richiedente.getCodiceFiscale()));
					errori.add(e);
				}
			}
			//IL CF NON E' CONSIDERATO OBBLIGATORIO
			/*else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errori.add(e);
			}*/
			//end controlli per tag codiceFiscale
//--------------------------------------------------------------------------
			//controlli per tag numeroTransazione
			if(richiedente.getNumeroTransazione().isEmpty()||richiedente.getNumeroTransazione().trim().isEmpty()||richiedente.getNumeroTransazione().length()<1) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.NUMERO_TRANSAZIONE_0050));
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
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0051, richiedente.getRuolo().getCodice()));
					errori.add(e);
				} else {
					//imposto la descrizione del ruolo nella richiesta
					richiedente.getRuolo().setDescrizione(descrizRuololist.get(0));
				}
			}
			// end controlli richiedente vaolorizzato
		} else {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0050));
			errori.add(e);
		}


		return errori;
	}

}
