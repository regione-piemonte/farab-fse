/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.farab;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.controller.FarmabController;
import it.csi.dma.farmab.controller.FarmabGestioneDeviceController;
import it.csi.dma.farmab.controller.FarmabGestioneDeviceOTPController;
import it.csi.dma.farmab.controller.FarmabLog;
import it.csi.dma.farmab.domain.DmaccTDispositivoCertificatoStato;
import it.csi.dma.farmab.interfacews.msg.farab.util.AuditFarmabServiceComponent;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CertificaDeviceServiceImpl {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	FarmabController farmabController;

	@Autowired
	FarmabGestioneDeviceOTPController farmabGestioneDeviceOTPController;

	@Autowired
	FarmabLog farmabLog;

	@Autowired
	FarmabGestioneDeviceController farmabGestioneDeviceController;

	@Autowired
	AuditFarmabServiceComponent auditController;

	@Transactional(propagation = Propagation.REQUIRED)
	public CertificaDeviceResponse certificaDevice(CertificaDevice certificaDeviceRequest, String remoteClientAddr) {
		log.info("CertificaDeviceServiceImpl::certificaDevice");

		CertificaDeviceResponse response=new CertificaDeviceResponse();
		response.setEsito(Constants.FAIL_CODE);

		List<Errore> errori=ValidateRequest(certificaDeviceRequest);

		if(!errori.isEmpty()) {
			//restituisco fallimento con la lista degli errori contenuti nella request
			response.setEsito(Constants.FAIL_CODE);
			ElencoErroriType elencoErrori= new ElencoErroriType();
			elencoErrori.errore=errori;
			response.setElencoErrori(elencoErrori);
			return response;
		}
		//errori di Business
		try {
			//CONTROLLO PER VERIFICARE SE IL DISPOSITIVO HA GIA' IL CERTIFICATO. SE SI: ERRORE
			Long esisteDeviceCertificato=farmabGestioneDeviceController.esisteDispositivoAssociatoConStato(certificaDeviceRequest.getCfCittadino(), Constants.DEVICE_CERTIFICATO);
			if (esisteDeviceCertificato!=null && esisteDeviceCertificato>0) {
				//MANCA SALVTAGGIO NELLA TABELLA DEI LOGS
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0064);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0064);
				e.setDescrizione(messaggioErrore);
				errori.add(e);
			}
			//CONTROLLO PER VERIFICARE UNIVOLCITA' UUID. SE SI: ERRORE
			List<DmaccTDispositivoCertificatoStato> IdUnivoco=farmabGestioneDeviceController.controllaUnivocitaUuid(certificaDeviceRequest.getUuidDevice());
			if (!IdUnivoco.isEmpty()) {
				//MANCA SALVTAGGIO NELLA TABELLA DEI LOGS
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0065);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0065);
				e.setDescrizione(messaggioErrore);
				errori.add(e);
			}
			if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
			}
			//I CAMPI SISTEMA OPERATIVO; BROWSER E MODELLO NON SONO OBBLIGATORI
			//SE MANCANTE L'OGGETTO L'INSERT FALLISCE
			//SI CREA ALLORA L'OGGETTO MANCANTE CON VALORI NULLI
			if(certificaDeviceRequest.getDispositivo()==null) {
				Dispositivo dispositivo = new Dispositivo();
				dispositivo.setBrowser(null);
				dispositivo.setModello(null);
				dispositivo.setSistemaOperativo(null);
				certificaDeviceRequest.setDispositivo(dispositivo);
			}else {
				Dispositivo dispositivo = new Dispositivo();
				if(certificaDeviceRequest.getDispositivo().getBrowser()==null || certificaDeviceRequest.getDispositivo().getBrowser().isEmpty()) {
					dispositivo.setBrowser(null);
				}
				if(certificaDeviceRequest.getDispositivo().getModello()==null || certificaDeviceRequest.getDispositivo().getModello().isEmpty()) {
					dispositivo.setModello(null);
				}
				if(certificaDeviceRequest.getDispositivo().getSistemaOperativo()==null || certificaDeviceRequest.getDispositivo().getSistemaOperativo().isEmpty()) {
					dispositivo.setSistemaOperativo(null);
				}
			}
			//nessun errore procedo con l'inserimento del device in dmacc_t_dispositivo_certificato
			try {

				//TODO Breakpoint utili per verificare la transazione, verificare se appare record su dmacc_t_dispositivo_certificato
				farmabGestioneDeviceController.inserisciNewDispositivoCertDevice(certificaDeviceRequest);
				//traccio l'audit
				final String NOME_SERVIZIO = "certificaDevice";
				Long idPaziente;
				try {
					idPaziente = farmabController.getIdIrecPaziente(certificaDeviceRequest.getCfCittadino());
				}catch(Exception e) {
					log.info("Codice fiscale:"+certificaDeviceRequest.getCfCittadino()+" di un fuori regione");
					idPaziente=null;
				}

				try {
					auditController.componiAuditESalvaAudit(Constants.CATALOG_CERT_DEV, certificaDeviceRequest.getRichiedente(), remoteClientAddr, idPaziente,
							certificaDeviceRequest.getCfCittadino(), NOME_SERVIZIO);
				} catch (Exception e) {
					log.error(NOME_SERVIZIO +  "Errore nella scrittura dell'audit: " + e.getMessage());
				}


			} catch (Exception ex) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_FATAL);
				e.setDescrizione(ex.getMessage());
				errori.add(e);
			}
			//genero risposta
			if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
			}
			//nessun errore: SUCCESSO
			response =prepareSuccessResponse(certificaDeviceRequest);

		}catch (Exception ex) {
			log.error(ex.getMessage());
			response.setEsito(Constants.FAIL_CODE);
		}

		return response;
	}

	private List<Errore> ValidateRequest(CertificaDevice request){
		List<Errore> errori= new ArrayList<Errore>();

		errori=this.ValidateRichiedente(request.getRichiedente());

		//--------------------------------------------------------------------------
		//controlli per tag cfCittadino
		if(request.getCfCittadino()==null || request.getCfCittadino().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0050);
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}
		if(FarmabUtils.isNotValidCf(request.getCfCittadino())) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0051);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_CITTADINO_0051, request.getCfCittadino());
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}
		if(request.getRichiedente().getCodiceFiscale().equalsIgnoreCase(request.getCfCittadino())) {
			//SITUAZIONE CORRETTA NON FA NULLA
		}else {
			//IL CF CITTADINO E' DIVERSO DAL CF RICHIEDENTE: OPERAZIONE NON POSSIBILE
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0066);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0066);
			e.setDescrizione(messaggioErrore);
			errori.add(e);
		}
		//END controlli per tag cfCittadino
		//--------------------------------------------------------------------------
		//controlli per tag UUID DEVICE
		if(request.getUuidDevice()==null || request.getUuidDevice().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.UUID_DEVICE_0050);
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag UUID DEVICE
		//--------------------------------------------------------------------------
		//controlli per tag CODICE FONTE
		if(request.getFonte()!=null) {
				if(request.getFonte().codice==null || request.getFonte().codice.isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.FONTE_0050);
					e.setDescrizione(messaggioErroreCompleto);
					errori.add(e);
				} else {
					List <String> descrizFontelist=farmabController.getFonteCertificazionePresente(request.getFonte().codice);
					if(descrizFontelist==null || descrizFontelist.size()<1) {
						Errore e = new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.FONTE_0051, request.getFonte().codice);
						e.setDescrizione(messaggioErroreCompleto);
						errori.add(e);
					}
				}
		}else {
			Errore e =new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.FONTE_0050);
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag CODICE FONTE
		//--------------------------------------------------------------------------
		//controlli per tag NUMERO TELEFONO
		if(request.getTelCittadino()==null || request.getTelCittadino().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.NUMERO_TELEFONO_0050);
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}
		//END controlli per tag NUMERO TELEFONO
		//--------------------------------------------------------------------------

		return errori;
	}

	private List<Errore> ValidateRichiedente(Richiedente richiedente) {
		List<Errore> errori= new ArrayList<Errore>();

		if (richiedente!=null) {
			//controlli per tag applicazione
			if(richiedente.getApplicazione()!=null) {
				if(richiedente.getApplicazione().getCodice()==null || richiedente.getApplicazione().getCodice().isEmpty()) {
					Errore e =new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050);
					e.setDescrizione(messaggioErroreCompleto);
					errori.add(e);
				} else {
					//controllo che sia formalmente corretto ovvero che valga SANSOL
					if("SANSOL".compareTo(richiedente.getApplicazione().getCodice())!=0) {
						Errore e =new Errore();
						e.setCodice(Constants.FAR_CC_0051);
						String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
						String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_APPLICAZIONE_0051, richiedente.getApplicazione().getCodice());
						e.setDescrizione(messaggioErroreCompleto);
						errori.add(e);
					}
				}
			} else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.APPLICAZIONE_0050);
				e.setDescrizione(messaggioErroreCompleto);
				errori.add(e);
			}
			//end controlli per tag applicazione
//--------------------------------------------------------------------------
			//controlli per tag codiceFiscale
			if (richiedente.getCodiceFiscale() != null) {
				if (richiedente.getCodiceFiscale().isEmpty() || richiedente.getCodiceFiscale().trim().isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
					e.setDescrizione(messaggioErroreCompleto);
					errori.add(e);
				}
				if (FarmabUtils.isNotValidCf(richiedente.getCodiceFiscale())) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0051, richiedente.getCodiceFiscale());
					e.setDescrizione(messaggioErroreCompleto);
					errori.add(e);
				}
			} else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CF_RICHIEDENTE_0050);
				e.setDescrizione(messaggioErroreCompleto);
				errori.add(e);
			}
			//end controlli per tag codiceFiscale
//--------------------------------------------------------------------------
			//controlli per tag numeroTransazione
			if(richiedente.getNumeroTransazione().isEmpty()||richiedente.getNumeroTransazione().trim().isEmpty()||richiedente.getNumeroTransazione().length()<1) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.NUMERO_TRANSAZIONE_0050);
				e.setDescrizione(messaggioErroreCompleto);
				errori.add(e);
			}
			//end controlli per tag numeroTransazione
//--------------------------------------------------------------------------
			//Controlli ruolo
			if(richiedente.getRuolo()==null || richiedente.getRuolo().getCodice()==null || richiedente.getRuolo().getCodice().trim().isEmpty()) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
				String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050);
				e.setDescrizione(messaggioErroreCompleto);
				errori.add(e);
			} else {
				List <String> descrizRuololist=farmabController.getDescrizioneRuoloByCod(richiedente.getRuolo().getCodice());
				if(descrizRuololist==null || descrizRuololist.size()<1) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0051);
					String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0051, richiedente.getRuolo().getCodice());
					e.setDescrizione(messaggioErroreCompleto);
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
			String messaggioErrore = farmabLog.findMesaggiErrore(Constants.FAR_CC_0050);
			String messaggioErroreCompleto = MessageFormat.format(messaggioErrore, Constants.CODICE_RUOLO_0050);
			e.setDescrizione(messaggioErroreCompleto);
			errori.add(e);
		}


		return errori;
	}

	private CertificaDeviceResponse prepareSuccessResponse(CertificaDevice request){
		CertificaDeviceResponse response=new CertificaDeviceResponse();
		CertificazioneType certificazioneType = new CertificazioneType();
		Codifica codifica = new Codifica();
		Dispositivo dispositivo = new Dispositivo();

		List<DmaccTDispositivoCertificatoStato> certificatoinserito=farmabGestioneDeviceController.controllaUnivocitaUuid(request.getUuidDevice());
		if (certificatoinserito!=null && !certificatoinserito.isEmpty()) {
			certificatoinserito.forEach(certinserito -> {
				Long idDevice = certinserito.getDeviceId();
				certificazioneType.setId(idDevice.toString());
				certificazioneType.setDataCertificazione(certinserito.getDataInserimento().toString());
			});
		}

		codifica.setCodice(request.getFonte().getCodice());
		String descrizioneFonte =farmabGestioneDeviceController.cercaDescrizioneFonte(codifica.getCodice());
		codifica.setDescrizione(descrizioneFonte);

		dispositivo.setBrowser(request.getDispositivo().getBrowser());
		dispositivo.setModello(request.getDispositivo().getModello());
		dispositivo.setSistemaOperativo(request.getDispositivo().getSistemaOperativo());

		certificazioneType.setCfCittadino(request.getCfCittadino());
		certificazioneType.setDispositivo(dispositivo);
		certificazioneType.setFonte(codifica);
		certificazioneType.setNumTelefono(request.getTelCittadino());
		certificazioneType.setUuidDevice(request.getUuidDevice());

		//SOLO PER SVILUPPO
		//stampaResponse(certificazioneType);

		response.setEsito(Constants.SUCCESS_CODE);
		response.setDatiCertificazione(certificazioneType);

		return response;
	}

	private void stampaResponse (CertificazioneType certificazioneType) {
		log.info("stampaResponse");
		log.info("RESPONSE ID: "+certificazioneType.getId());
		log.info("RESPONSE UUID DEVICE: "+certificazioneType.getUuidDevice());
		log.info("RESPONSE DATA CERTIFICAZIONE: "+certificazioneType.getDataCertificazione());
		log.info("RESPONSE CODICE FONTE: "+certificazioneType.getFonte().getCodice());
		log.info("RESPONSE DESCRIZIONE FONTE: "+certificazioneType.getFonte().getDescrizione());
		log.info("RESPONSE NUMERO TELEFONO: "+certificazioneType.getNumTelefono());
		log.info("RESPONSE CF CITTADINO: "+certificazioneType.getCfCittadino());
		log.info("RESPONSE SISTEMA OP: "+certificazioneType.getDispositivo().getSistemaOperativo());
		log.info("RESPONSE BROWSER: "+certificazioneType.getDispositivo().getBrowser());
		log.info("RESPONSE MODELLO: "+certificazioneType.getDispositivo().getModello());
	}

}
