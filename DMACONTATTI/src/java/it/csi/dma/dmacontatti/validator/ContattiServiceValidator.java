/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.validator;

import java.util.List;

import it.csi.dma.dmacontatti.business.dao.PazienteLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.PazienteLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.*;
import it.csi.dma.dmacontatti.util.Utils;

public class ContattiServiceValidator extends BaseValidator{
	
	
	public PazienteLowDao pazienteLowDao;
	
    public List<Errore> validateGeneraOtp(GeneraOtp generaOtp, List<Errore> errori) throws Exception {

        //Controlli obbligatorieta'
       errori = validaRichiedente(generaOtp.getRichiedente(), errori);
       if(errori.isEmpty()) {
		   if(generaOtp.getCodiceFiscalePaziente() == null || generaOtp.getCodiceFiscalePaziente().isEmpty()) {
			   errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE));
			   return errori;
		   }
    	   if(generaOtp.getCanale() == null || generaOtp.getCanale().isEmpty()) {
	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CANALE));
	        	return errori;
	        }
    	   if(generaOtp.getContatto() == null || generaOtp.getContatto().isEmpty()) {
   	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CONTATTO));
   	        	return errori;
   	       }

		   if(generaOtp.getCodiceFiscalePaziente() != null && generaOtp.getCodiceFiscalePaziente().length() != Constants.CORRETTA_LONGHEZZA_CF) {
			   errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE, generaOtp.getCodiceFiscalePaziente()));
			   return errori;
		   }

       }
       return errori;
    }
    
    public List<Errore> validateVerificaOtp(VerificaOtp parameters, List<Errore> errori,LogGeneralDaoBean logGeneralDaoBean) throws PazienteLowDaoException {

    	//Controlli obbligatorieta'
        errori = validaRichiedente(parameters.getRichiedente(), errori);
        if(errori.isEmpty()) {
	        if(parameters.getCodiceFiscalePaziente() == null || parameters.getCodiceFiscalePaziente().isEmpty()) {
	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE));
	        	return errori;
	        }
	        if(parameters.getCanale() == null || parameters.getCanale().isEmpty()) {
	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CANALE));
	        	return errori;
	        }
	        if(parameters.getCodiceOTP() == null || parameters.getCodiceOTP().isEmpty()) {
	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_OTP));
	        	return errori;
	        }

			if(parameters.getCodiceFiscalePaziente() != null && parameters.getCodiceFiscalePaziente().length() != Constants.CORRETTA_LONGHEZZA_CF) {
				errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE, parameters.getCodiceFiscalePaziente()));
				return errori;
			}
        }

		return errori;
	}
    
    public List<Errore> validateGetPreferenze(GetPreferenze parameters, List<Errore> errori,
			LogGeneralDaoBean logGeneralDaoBean) throws Exception{
    	 errori = validaRichiedente(parameters.getRichiedente(), errori);
         if(errori.isEmpty()) {
        	 if(parameters.getCodiceFiscalePaziente() == null || parameters.getCodiceFiscalePaziente().isEmpty()) {
 	        	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE));
 	        	return errori;
 	      	 }

			 if(parameters.getCodiceFiscalePaziente() != null && parameters.getCodiceFiscalePaziente().length() != Constants.CORRETTA_LONGHEZZA_CF) {
				 errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE, parameters.getCodiceFiscalePaziente()));
				 return errori;
			 }
			} 
//         if (verificaPaziente(parameters.getCodiceFiscalePaziente(), errori) == null) return errori;

		return errori;
	}

	public PazienteLowDto verificaPaziente(String codiceFiscalePaziente, List<Errore> errori) throws PazienteLowDaoException {
		PazienteLowDto pazienteLowDto = null;
		pazienteLowDto = Utils.getFirstRecord(pazienteLowDao.findByCodiceFiscale(codiceFiscalePaziente));
//		if(pazienteLowDto == null) {
//			errori.add(getLogGeneralDao().getErroreCatalogo(BaseValidator.PAZIENTE_NON_TROVATO));
//			return pazienteLowDto;
//		}

		
		return pazienteLowDto;
	}

	public List<Errore> validateputTermsPreferenzeContatti(PutTermsPreferenzeContatti parameters, List<Errore> errori,
			LogGeneralDaoBean logGeneralDaoBean) {
    	
    	errori = validaRichiedente(parameters.getRichiedente(), errori);
        if(errori.isEmpty()) {
			if(parameters.getCodiceFiscalePaziente() == null || parameters.getCodiceFiscalePaziente().isEmpty()) {
		    	errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE));
		    	return errori;
			}
			if(parameters.getHash() == null || parameters.getHash().isEmpty()) {
				errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.HASH));
				return errori;
			}
			if (validaListaContatti(parameters, errori)) return errori;

			if(parameters.getCodiceFiscalePaziente() != null && parameters.getCodiceFiscalePaziente().length() != Constants.CORRETTA_LONGHEZZA_CF) {
				errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE, parameters.getCodiceFiscalePaziente()));
				return errori;
			}
//			if (validaListaPreferenzeServizi(parameters, errori)) return errori;
		}
        
        return errori;
	}

	private boolean validaListaPreferenzeServizi(PutTermsPreferenzeContatti parameters, List<Errore> errori) {
		if(parameters.getListaPreferenzeServizi() != null &&
				parameters.getListaPreferenzeServizi().getPreferenzeServizio() != null &&
				!parameters.getListaPreferenzeServizi().getPreferenzeServizio().isEmpty()){
			for(PreferenzeServizio preferenzeServizio : parameters.getListaPreferenzeServizi().getPreferenzeServizio()){
				if(preferenzeServizio.getServizio() == null || preferenzeServizio.getServizio().getCodice() == null ||
				preferenzeServizio.getServizio().getCodice().isEmpty() || preferenzeServizio.getCanaliAttiviCittadino() == null ||
				preferenzeServizio.getCanaliAttiviCittadino().isEmpty()){
					errori.add(getLogGeneralDao().getErroreCatalogo(SERVIZIO_E_CANALE_ATTIVO_OBBLIGATORI, null, null, null, null, null, Constants.CODICE_FISCALE_PAZIENTE));
					return true;
				}
			}
		}
		return false;
	}

	private boolean validaListaContatti(PutTermsPreferenzeContatti parameters, List<Errore> errori) {
		if(parameters.getListaContatti() == null || parameters.getListaContatti().getContatto() == null ||
				parameters.getListaContatti().getContatto().isEmpty()) {
			errori.add(getLogGeneralDao().getErroreCatalogo(CONTATTO_OBBLIGATORIO));
			return true;
		}else{
			for(Contatto contatto : parameters.getListaContatti().getContatto()){
				if(contatto.getCanale() == null || contatto.getCanale().isEmpty() ||
					contatto.getValoreContatto() == null || contatto.getValoreContatto().isEmpty()){
					errori.add(getLogGeneralDao().getErroreCatalogo(CANALE_O_CONTATTO_OBBLIGATORIO));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param generaOtp
	 * @param errori
	 */
	public List<Errore> validaRichiedente(Richiedente richiedente, List<Errore> errori) {
		if(richiedente != null){
           if(richiedente.getApplicazioneChiamante() == null || richiedente.getApplicazioneChiamante().isEmpty()) {
        	   errori.add(logGeneralDao.getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.APPLICAZIONE_CHIAMANTE));
        	   return errori;
           }
           if(richiedente.getCodiceFiscaleRichiedente() == null || richiedente.getCodiceFiscaleRichiedente().isEmpty()) {
        	   errori.add(logGeneralDao.getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.CODICE_FISCALE_RICHIEDENTE));
        	   return errori;
           }           
           if(richiedente.getUUID() == null || richiedente.getUUID().isEmpty()) {
        	   errori.add(logGeneralDao.getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.UUID));
        	   return errori;
           }
           if(richiedente.getIndirizzoIp() == null || richiedente.getIndirizzoIp().isEmpty()) {
				errori.add(logGeneralDao.getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.INDIRIZZO_IP));
				return errori;
           }
           if(!Constants.SANSOL.equals(richiedente.getApplicazioneChiamante()) && !Constants.WEBAPP_CM.equals(richiedente.getApplicazioneChiamante())) {
        	   errori.add(logGeneralDao.getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.APPLICAZIONE_CHIAMANTE, richiedente.getApplicazioneChiamante()));
        	   return errori;
           }
           if(richiedente.getCodiceFiscaleRichiedente().length() != Constants.CORRETTA_LONGHEZZA_CF) {  		 
         		 errori.add(getLogGeneralDao().getErroreCatalogo(CAMPO_NON_CORRETTO, null, null, null, null, null, Constants.CODICE_FISCALE_RICHIEDENTE, richiedente.getCodiceFiscaleRichiedente()));	
         		 return errori; 	
           }
        } else {
        	errori.add(logGeneralDao.getErroreCatalogo(CAMPO_OBBLIGATORIO, null, null, null, null, null, Constants.RICHIEDENTE));
        	return errori;
        }
		return errori;
	}

	public PazienteLowDao getPazienteLowDao() {
		return pazienteLowDao;
	}

	public void setPazienteLowDao(PazienteLowDao pazienteLowDao) {
		this.pazienteLowDao = pazienteLowDao;
	}
	
}
