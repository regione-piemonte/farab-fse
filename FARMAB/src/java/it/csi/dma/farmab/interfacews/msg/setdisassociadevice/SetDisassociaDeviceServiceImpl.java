/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.setdisassociadevice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.annotations.SchemaValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.dma.farmab.SpringApplicationContextProvider;
import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabGestioneDeviceController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTDispositivoCertificatoStato;
import it.csi.dma.farmab.integration.dao.LogAuditDao;
import it.csi.dma.farmab.integration.dao.dto.AuditDto;
import it.csi.dma.farmab.integration.dao.dto.CatalogoLogAuditLowDto;
import it.csi.dma.farmab.interfacews.IF.SetDisassociaDeviceService;
import it.csi.dma.farmab.interfacews.base.BaseService;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@SchemaValidation(enabled = false)
@WebService(targetNamespace = "http://setdisassociadevice.msg.interfacews.farmab.dma.csi.it/", name = "SetDisassociaDeviceService", portName = "SetDisassociaDevicePort", endpointInterface = "it.csi.dma.farmab.interfacews.IF.SetDisassociaDeviceService")
public class SetDisassociaDeviceServiceImpl extends BaseService implements SetDisassociaDeviceService{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	SpringApplicationContextProvider springApplicationContextProvider;

	@Autowired
	FarmabGestioneDeviceController farmabGestioneDeviceController;

	@Autowired
	FarmabController farmabController;

	@Autowired
	FarmabLog farmabLog;

	@Autowired
	LogAuditDao logAudit;


	@Override
	public SetDisassociaDeviceResponse setDisassociaDevice(SetDisassociaDeviceRequest setDisassociaDevice) {
		log.info("SetDisassociaDeviceServiceImpl::setDisassociaDevice");
		SetDisassociaDeviceResponse response= new SetDisassociaDeviceResponse();
		try {
			List<Errore> errore=this.ValidateRequest(setDisassociaDevice);

			/*Modificato analisi 25/01/2022
			//verifico che esista almeno un idPaziente valido
	    	if(farmabController.esistePazienteValido(setDisassociaDevice.getCfCittadino())<1) {
	    		Errore e= new Errore();
				e.setCodice(Constants.FAR_CC_0072);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errore.add(e);
	    	}
	    	*/
			if(!errore.isEmpty()) {
				//restituisco fallimento con la lista degli errori contenuti nella request
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errore;
				response.setElencoErrori(elencoErrori);
				return response;
			}
			//errori di business
			List<DmaccTDispositivoCertificatoStato> dispositivi= farmabGestioneDeviceController.findDispositivi(setDisassociaDevice.getCfCittadino(), Constants.DEVICE_CERTIFICATO);
			if(dispositivi!=null && dispositivi.size()>0) {
				int u=farmabGestioneDeviceController.disassociaDispositiviCert(setDisassociaDevice.getCfCittadino(),setDisassociaDevice.getRichiedente().getCodiceFiscale());
				log.debug("Update ha restituito="+u);
				response.setEsito(Constants.SUCCESS_CODE);

			} else {
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0069);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errore.add(e);
				elencoErrori.errore=errore;
				response.setElencoErrori(elencoErrori);
				return response;
			}
		} catch(Exception e) {
    		//TODO riportare eccezioni per malfunzionamento dei server
    		log.error(e.getMessage());
    		response.setEsito(Constants.FAIL_CODE);
    	}
		//inserire la parte relativa al log di audit
		try {
			log.info("[SetDisassociaDeviceServiceImpl::setDisassociaDevice] prima di inserire in logaudit");
			if(Constants.SUCCESS_CODE.equalsIgnoreCase(response.getEsito())){
				log.info("[SetDisassociaDeviceServiceImpl::setDisassociaDevice] prima di inserire in logaudit2");

				Long idIrec;
				try {
					idIrec = farmabController.getIdIrecPaziente(setDisassociaDevice.getCfCittadino());
				}catch(Exception e) {
					log.info("Codice fiscale:"+setDisassociaDevice.getCfCittadino()+" di un fuori regione");
					idIrec=null;
				}

				log.info("[SetDisassociaDeviceServiceImpl::setDisassociaDevice] prima di inserire in logaudit3");
				logAudit.insertTlogAudit(getLogAudit(Constants.CATALOG_SET_DIS_DEV, setDisassociaDevice, idIrec));
			}
		} catch (Exception e) {
			log.error("[SetDisassociaDeviceServiceImpl::setDisassociaDevice] errore nel salvare su logaudit: " + e.getMessage());
		}


		return response;
	}
	private AuditDto getLogAudit(String codiceCatalogo, SetDisassociaDeviceRequest req,  Long idPaziente
			 ) throws Exception{

		MessageContext mc = wsContext.getMessageContext();
	    HttpServletRequest request = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
	    final String NOME_SERVIZIO = "SetDisassociaDeviceService";
		AuditDto audit = new AuditDto();
		audit.setApplicativoVerticale(Constants.CODICE_SERVIZIO_IN_DELEGHE);

		CatalogoLogAuditLowDto catalogo = logAudit.geCatalogoLogAudittByCodice(codiceCatalogo);
		audit.setInformazioniTracciate(catalogo.getDescrizione());
		audit.setIdPaziente(idPaziente);
		audit.setIdCatalogoLogAudit(catalogo.getId());

		//audit.setIdTransazione(UUID.randomUUID().toString());
		if(req.getRichiedente() != null && req.getRichiedente().getApplicazione() != null){
			audit.setCodiceApplicazioneRichiedente(req.getRichiedente().getApplicazione().getCodice());
		}
		audit.setVisibileAlCittadino("S");
		audit.setIpChiamante(request.getRemoteAddr());

		audit.setCodiceServizio(codiceCatalogo);
		//if(req.getRichiedente() != null && !req.getCfCittadino().equals(req.getRichiedente().getCodiceFiscale())) {
		//	audit.setCodiceRuolo(Constants.RUOLO_DEL);
		//}else if(req.getRichiedente() != null && req.getRichiedente().getRuolo() != null){
		audit.setCodiceRuolo(req.getRichiedente().getRuolo().getCodice());
		//}
		audit.setNomeServizio(NOME_SERVIZIO);
		audit.setCodiceFiscaleUtente(req.getCfCittadino());

		return audit;
	}

	private List<Errore> ValidateRequest(SetDisassociaDeviceRequest request){
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
		// cfCittadino<> Richiedente.codiceFiscale e applicazione.codice=SANSOL restituisce errore FAR_CC_0061
		if(errori.isEmpty() && (!request.getCfCittadino().equalsIgnoreCase(request.getRichiedente().getCodiceFiscale()) && "SANSOL".equalsIgnoreCase(request.getRichiedente().getApplicazione().getCodice()))) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0061);
			e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
			errori.add(e);
		}
		return errori;
	}

	private List<Errore> ValidateRichiedente(Richiedente richiedente) {
		List<Errore> errori= new ArrayList<Errore>();
		//trasformare in una lambda
		/*Map<String, Object> myObjectAsDict = new HashMap<>();
		Field[] allFields = Richiedente.class.getDeclaredFields();
		for (Field field : allFields) {
	        Class<?> targetType = field.getType();
	        Object objectValue = targetType.newInstance();
	        Object value = field.get(objectValue);
	        myObjectAsDict.put(field.getName(), value);
	    }*/
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
					//controllo che sia formalmente corretto ovvero che valga SANSOL o WEBAPP_CM
					if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())!=0 && "WEBAPP_CM".compareTo(richiedente.getApplicazione().getCodice())!=0) {
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
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0051, richiedente.getCodiceFiscale()));
					errori.add(e);
				}
			} else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0050));
				errori.add(e);
			}
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
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), "richiedente"));
			errori.add(e);
		}

		return errori;
	}



}
