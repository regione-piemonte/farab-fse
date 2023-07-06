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
import it.csi.dma.farmab.domain.DmaccTDispositivoCertificazioneOtpRichDomain;
import it.csi.dma.farmab.interfacews.msg.farab.util.AuditFarmabServiceComponent;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CertificaDeviceConOtpServiceImpl {
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
	public CertificaDeviceConOtpResponse certificaDeviceConOtp(CertificaDeviceConOtp certificaDeviceConOtpRequest, String remoteClientAddr) {
		log.info("CertificaDeviceConOtpServiceImpl::certificaDeviceConOtp");
		CertificaDeviceConOtpResponse response=new CertificaDeviceConOtpResponse();
		DmaccTDispositivoCertificazioneOtpRichDomain device=null;
		response.setEsito(Constants.FAIL_CODE);
		List<Errore> errori=ValidateRequest(certificaDeviceConOtpRequest);
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
			//modifica introdotta il 14/03/2023, verifico che la count non restituisca nessun record altrimenti do errore 64
			if((farmabGestioneDeviceOTPController.countDeviceCert(certificaDeviceConOtpRequest.getCfCittadino())>0)) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0064);
				e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
				errori.add(e);
			} else {
				//fare refactoring se si vuole mettere il valore in Constants
				//List<Integer> idStato=farmabGestioneDeviceOTPController.decodeStatoDevice("VAL");
				//log.debug("idStato="+idStato!=null&&!idStato.isEmpty()?idStato.get(0)+"":"NON TROVATO tabella dmacc_d_dispositivo_certificazione_otp_rich_stato per codice=VAL");
				//TODO questo controllo viene fatto SOLO per avere un errore diverso, ELIMINARLO Per ottimizzare i tempi di risposta!!!!!!
				List<DmaccTDispositivoCertificazioneOtpRichDomain> esistOtp=farmabGestioneDeviceOTPController.esistOTP(certificaDeviceConOtpRequest);
				if (esistOtp==null || esistOtp.isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0070);
					e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
					errori.add(e);
				} else {
					List<DmaccTDispositivoCertificazioneOtpRichDomain> foundOtpValid=farmabGestioneDeviceOTPController.verifyOTP(certificaDeviceConOtpRequest);
					if (foundOtpValid==null || foundOtpValid.isEmpty()) {
						Errore e = new Errore();
						e.setCodice(Constants.FAR_CC_0071);
						e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
						errori.add(e);
					} else {
						//trovato almeno un otp valido
						boolean found=false;
						for (DmaccTDispositivoCertificazioneOtpRichDomain otp: foundOtpValid) {
							if(certificaDeviceConOtpRequest.getCodiceOtp().equals(otp.getOtp())) {
								found=true;
								device=otp;
								break;
							}
						}
						if(!found) {
							Errore e = new Errore();
							e.setCodice(Constants.FAR_CC_0071);
							e.setDescrizione(farmabLog.findMesaggiErrore(e.getCodice()));
							errori.add(e);
						}
					}
				}
			}
			if(!errori.isEmpty()) {
				//restituisco fallimento con i controlli di business
				response.setEsito(Constants.FAIL_CODE);
				ElencoErroriType elencoErrori= new ElencoErroriType();
				elencoErrori.errore=errori;
				response.setElencoErrori(elencoErrori);
				return response;
			}
			//nessun errore procedo con l'inserimento del device in dmacc_t_dispositivo_certificato
			try {
				farmabGestioneDeviceController.inserisciNewDispositivoCert(certificaDeviceConOtpRequest, device);
				//invalido otp
				farmabGestioneDeviceOTPController.invalidateOTP(certificaDeviceConOtpRequest.getCfCittadino(), certificaDeviceConOtpRequest.getRichiedente().getCodiceFiscale(), device.getDeviceOtpRichId());

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
			//nessun errore
			response.setEsito(Constants.SUCCESS_CODE);
			//traccio l'audit
			final String NOME_SERVIZIO = "certificaDeviceConOtp";
			Long idPaziente= null;
			try {
				idPaziente = farmabController.getIdIrecPaziente(certificaDeviceConOtpRequest.getCfCittadino());
			} catch (Exception e) {
				log.info("Il codice fiscale:"+certificaDeviceConOtpRequest.getCfCittadino()+" fuori regione");
				idPaziente =null;
			}
			try {
				auditController.componiAuditESalvaAudit(Constants.CATALOG_CERT_DEV_OTP, certificaDeviceConOtpRequest.getRichiedente(), remoteClientAddr, idPaziente,
						certificaDeviceConOtpRequest.getCfCittadino(), NOME_SERVIZIO);
			} catch (Exception e) {
				log.error(NOME_SERVIZIO +  "Errore nella scrittura dell'audit: ", e);
			}


		}catch (Exception ex) {
			log.error(ex.getMessage());
			response.setEsito(Constants.FAIL_CODE);
		}

		return response;
	}

	private List<Errore> ValidateRequest(CertificaDeviceConOtp request){
		log.info("CertificaDeviceConOtpServiceImpl::ValidateRequest");
		List<Errore> errori= new ArrayList<Errore>();
		errori=this.ValidateRichiedente(request.getRichiedente());
		//--------------------------------------------------------------------------
		//controlli per tag uuidDevice
		if(request.getUuidDevice() ==null || request.getUuidDevice().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.UUID_DEVICE_0050, request.getUuidDevice()));
			errori.add(e);
		} else if (request.getUuidDevice().length()<Constants.IMEI_LENGHT) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0051);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.UUID_DEVICE_0051, request.getUuidDevice()));
			errori.add(e);
		}
		//controlli per tag cfCittadino
		if(request.getCfCittadino()==null || request.getCfCittadino().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_CITTADINO_0050, request.getCfCittadino()));
			errori.add(e);
		} else {
			if(FarmabUtils.isNotValidCf(request.getCfCittadino())) {
				Errore e = new Errore();
				e.setCodice(Constants.FAR_CC_0051);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_CITTADINO_0051, request.getCfCittadino()));
				errori.add(e);
			}
		}
		//controlli per tag codiceOtp
		if(request.getCodiceOtp()==null || request.getCodiceOtp().isEmpty()) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0050);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_OTP_0050, request.getCodiceOtp()));
			errori.add(e);
		} else if (request.getCodiceOtp().length()!=Constants.OTP_LENGHT) {
			Errore e = new Errore();
			e.setCodice(Constants.FAR_CC_0051);
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_OTP_0051, request.getCodiceOtp()));
			errori.add(e);
		}
		//controllo che esista il Dispositivo come tag altrimenti lo creo
		if(request.getDispositivo()==null) {
			request.dispositivo=new Dispositivo();
		}

		return errori;
	}

	private List<Errore> ValidateRichiedente(Richiedente richiedente) {
		log.info("CertificaDeviceConOtpServiceImpl::ValidateRichiedente");
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
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICAZIONE_0050, richiedente.getApplicazione().getCodice()));
					errori.add(e);
				} else {
					//controllo che sia formalmente corretto ovvero che valga SANSOL o WEBAPP_CM
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
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.APPLICAZIONE_0050, richiedente.getApplicazione().getCodice()));
				errori.add(e);
			}
			//end controlli per tag applicazione
//--------------------------------------------------------------------------
			//controlli per tag codiceFiscale
			if (richiedente.getCodiceFiscale() != null) {
				//IL CODICE FISCALE RICHIEDENTE NON E' OBBLIGATORIO
				/*if (richiedente.getCodiceFiscale().isEmpty() || richiedente.getCodiceFiscale().trim().isEmpty()) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0050);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0050, richiedente.getCodiceFiscale()));
					errori.add(e);
				}*/
				if (FarmabUtils.isNotValidCf(richiedente.getCodiceFiscale())) {
					Errore e = new Errore();
					e.setCodice(Constants.FAR_CC_0051);
					e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0051, richiedente.getCodiceFiscale()));
					errori.add(e);
				}
			}
			//IL CODICE FISCALE RICHIEDENTE NON E' OBBLIGATORIO
			/*else {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CF_RICHIEDENTE_0050, richiedente.getCodiceFiscale()));
				errori.add(e);
			}*/
			//end controlli per tag codiceFiscale
//--------------------------------------------------------------------------
			//controlli per tag numeroTransazione
			if(richiedente.getNumeroTransazione().isEmpty()||richiedente.getNumeroTransazione().trim().isEmpty()||richiedente.getNumeroTransazione().length()<1) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.NUMERO_TRANSAZIONE_0050, richiedente.getNumeroTransazione()));
				errori.add(e);
			}
			//end controlli per tag numeroTransazione
//--------------------------------------------------------------------------
			//Controlli ruolo
			if(richiedente.getRuolo()==null || richiedente.getRuolo().getCodice()==null || richiedente.getRuolo().getCodice().trim().isEmpty()) {
				Errore e =new Errore();
				e.setCodice(Constants.FAR_CC_0050);
				e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0050, richiedente.getRuolo().getCodice()));
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
			e.setDescrizione(MessageFormat.format(farmabLog.findMesaggiErrore(e.getCodice()), Constants.CODICE_RUOLO_0050, "RICHIEDENTE NULLO"));
			errori.add(e);
		}


		return errori;
	}

}
