/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao;

import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtpResponse;

import java.util.List;

public interface GeneraOtpDao {

    GeneraOtpResponse generaOtp(GeneraOtp generaOtp, PazienteLowDto pazienteLowDto, List<Errore> errori, GeneraOtpResponse generaOtpResponse) throws Exception;
}
