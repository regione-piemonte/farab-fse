/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.log;


import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenze;
import it.csi.dma.dmacontatti.interfacews.contatti.PutTermsPreferenzeContatti;
import it.csi.dma.dmacontatti.interfacews.contatti.VerificaOtp;

public interface CreateLogBeanDao {

	LogGeneralDaoBean prepareLogBeanStart(GeneraOtp req, String encryptionKey) throws Exception;
	
	LogGeneralDaoBean prepareLogBeanStart(VerificaOtp parameters, String encryption_key) throws Exception;

	LogGeneralDaoBean prepareLogBeanServiziRichiamatiStart(LogGeneralDaoBean logGeneralDaoBean,
														   String numeroTransazione, String servizioRichiamato, String request, String encryptionKey)
			throws Exception;

	LogGeneralDaoBean prepareLogBeanStart(GetPreferenze parameters, String encryption_key) throws Exception;

	LogGeneralDaoBean prepareLogBeanStart(PutTermsPreferenzeContatti parameters, String encryption_key) throws Exception;

	
}
