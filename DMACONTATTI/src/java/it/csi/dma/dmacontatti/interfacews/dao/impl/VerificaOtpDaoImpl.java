/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.ContattiOTPLowDao;
import it.csi.dma.dmacontatti.business.dao.PazienteLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtpResponse;
import it.csi.dma.dmacontatti.interfacews.dao.VerificaOtpDao;
import it.csi.dma.dmacontatti.util.Utils;
import it.csi.dma.dmacontatti.validator.BaseValidator;

public class VerificaOtpDaoImpl implements VerificaOtpDao {


	private LogGeneralDao logGeneralDao;
	
	private PazienteLowDao pazienteLowDao;
	
	private ContattiOTPLowDao contattiOTPLowDao;
	
	private ConfigurazioneLowDao configurazioneLowDao;
	
	@Override
	public VerificaOtpResponse verificaOtp(VerificaOtp parameters, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean)
			throws Exception {
		
		VerificaOtpResponse response = new VerificaOtpResponse();

		//Jira 3757
		//Verifica Paziente
//		PazienteLowDto pazienteLowDto = null;
//		pazienteLowDto = Utils.getFirstRecord(pazienteLowDao.findByCodiceFiscale(parameters.getCodiceFiscalePaziente()));
//    	if(pazienteLowDto == null) {
//    		errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.PAZIENTE_NON_TROVATO));
//    		response.setErrori(errori);
//    		return response;
//    	}

    	//Ricerca OTP
    	ContattiOTPLowDto contattiOTPLowDto = null;
    	contattiOTPLowDto = Utils.getFirstRecord(
    			contattiOTPLowDao.ricercaOTP(parameters.getCodiceFiscalePaziente() ,parameters.getCanale(), parameters.getCodiceOTP()));
    	
    	//Verifica OTP
    	if(contattiOTPLowDto != null && contattiOTPLowDto.getDataFineValidita() == null) {
    		
    		//Verifica Scandenza OTP
    		Timestamp validitaOTP = contattiOTPLowDto.getDataInizioValidita();
    		ConfigurazioneLowDto dto = Utils.getFirstRecord(configurazioneLowDao.findByCodice(Constants.VALIDITA_OTP));
    		long minuti = Long.parseLong(dto.getValore());
    		Instant instantValiditaOTP = validitaOTP.toInstant();
    		Instant test = instantValiditaOTP.plus(minuti, ChronoUnit.MINUTES);
    		validitaOTP = Timestamp.from(test);
			
    		if(validitaOTP.before(Utils.sysdate())) {
    			errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.OTP_NON_VALIDO));
    			response.setErrori(errori);
    			contattiOTPLowDto.setDataFineValidita(Utils.sysdate());
    			contattiOTPLowDao.update(contattiOTPLowDto);
        		return response;
    		}
    		
    		//Invalidazione OTP
    		contattiOTPLowDto.setDataFineValidita(Utils.sysdate());
    		contattiOTPLowDao.update(contattiOTPLowDto);
    		
    		response.setDataInserimento(contattiOTPLowDto.getDataInizioValidita().toString());
    		response.setDataScadenza(validitaOTP.toString());
    		
    	} else {
    		errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.OTP_NON_VALIDO));
    		response.setErrori(errori);
    		return response;
    	}
	    	
	   	return response;
	}

	public LogGeneralDao getLogGeneralDao() {
		return logGeneralDao;
	}


	public void setLogGeneralDao(LogGeneralDao logGeneralDao) {
		this.logGeneralDao = logGeneralDao;
	}


	public PazienteLowDao getPazienteLowDao() {
		return pazienteLowDao;
	}


	public void setPazienteLowDao(PazienteLowDao pazienteLowDao) {
		this.pazienteLowDao = pazienteLowDao;
	}


	public ContattiOTPLowDao getContattiOTPLowDao() {
		return contattiOTPLowDao;
	}


	public void setContattiOTPLowDao(ContattiOTPLowDao contattiOTPLowDao) {
		this.contattiOTPLowDao = contattiOTPLowDao;
	}


	public ConfigurazioneLowDao getConfigurazioneLowDao() {
		return configurazioneLowDao;
	}


	public void setConfigurazioneLowDao(ConfigurazioneLowDao configurazioneLowDao) {
		this.configurazioneLowDao = configurazioneLowDao;
	}
	
	
	

}
