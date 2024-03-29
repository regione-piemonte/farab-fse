/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.getdevicecertificato;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.annotations.SchemaValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.dma.farmab.SpringApplicationContextProvider;
import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTDispositivoCertificatoStato;
import it.csi.dma.farmab.interfacews.IF.GetDeviceCertificatoService;
import it.csi.dma.farmab.interfacews.base.BaseService;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@SchemaValidation(enabled = false)
@WebService(targetNamespace = "http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/", name = "GetDeviceCertificatoService", portName = "GetDeviceCertificatoPort", endpointInterface = "it.csi.dma.farmab.interfacews.IF.GetDeviceCertificatoService")
public class GetDeviceCertificatoServiceImpl extends BaseService implements GetDeviceCertificatoService {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	SpringApplicationContextProvider springApplicationContextProvider;

	@Autowired
	FarmabController farmabController;

	@Autowired
	FarmabLog farmabLog;

	/**
	 * @param GetDeviceCertificatoRequest
	 * @return GetDeviceCertificatoResponse
	 */
	@Override
	public GetDeviceCertificatoResponse getDeviceCertificato(GetDeviceCertificatoRequest parameters) {
		log.info("GetDeviceCertificatoServiceImpl::getDeviceCertificato");
		GetDeviceCertificatoResponse response = new GetDeviceCertificatoResponse();
		try {
			List<Errore> errore=this.ValidateRequest(parameters);
			if(!errore.isEmpty()) {
				//restituisco fallimento con la lista degli errori
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errore;
				response.setElencoErrori(elencoErrori);
				return response;
			} else {
				response.elencoErrori=null;
			}
			/*
			 * FarmabController farmabController=(FarmabController)
			 * springApplicationContextProvider.getApplicationContext().getBean(
			 * "FarmabController");
			 *
			 * farmabController.getDispositivoCertificato();
			 */
			List<DmaccTDispositivoCertificatoStato> dispositivi= farmabController.getDispositivoCertificato(parameters.getCfCittadino());
			response.setEsito(Constants.SUCCESS_CODE);
			if(dispositivi!=null && !dispositivi.isEmpty()) {
				DmaccTDispositivoCertificatoStato disp =dispositivi.get(0);
				CertificazioneType cert= new CertificazioneType();
				cert.setId(disp.getDeviceId()+"");
				cert.setUuidDevice(disp.getDeviceUuid());
				cert.setDataCertificazione(disp.getDataCertificazione());
				//fonte
				if (disp.getFonteCertId() > 0) {
					Codifica f=new Codifica();
					f.setCodice(disp.getFonteCertCod());
					f.setDescrizione(disp.getFonteCertDesc());
					cert.setFonte(f);
				}
				cert.setNumTelefono(disp.getDeviceNumeroTelefono());
				cert.setCfCittadino(disp.getCittadinoCf());
				//dispositivo
				Dispositivo d=new Dispositivo();
				d.setSistemaOperativo(disp.getDeviceSO());
				d.setBrowser(disp.getDeviceBrowser());
				d.setModello(disp.getDeviceModello());
				cert.setDispositivo(d);


				response.setDatiCertificazione(cert);
				response.setStatoCertificazione(Constants.DEVICE_CERTIFICATO);
			} else {
				//DISPOSITIVO NON_CERT
				response.setStatoCertificazione(Constants.DEVICE_NON_CERT);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			response.setEsito(Constants.FAIL_CODE);
		}
		return response;
	}

	private List<Errore> ValidateRequest(GetDeviceCertificatoRequest request){
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
			//controlli per tag ApplicativoVerticale n.a.
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
