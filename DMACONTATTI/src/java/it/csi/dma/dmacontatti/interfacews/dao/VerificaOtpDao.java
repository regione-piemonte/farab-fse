/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao;

import java.util.List;

import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtpResponse;

public interface VerificaOtpDao {
	
	public VerificaOtpResponse verificaOtp(VerificaOtp verificaOtp, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) throws Exception;

}
