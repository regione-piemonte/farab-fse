/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.log;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.dmacontatti.interfacews.Errore;

public interface LogGeneralDao {

	void logStart(LogGeneralDaoBean logGeneralDaoBean, String codiceLog, Object... args) throws Exception;
	
	void logEnd(LogGeneralDaoBean logGeneralDaoBean, String esito, String xmlOut,
				List<Errore> errori, String codiceLog, Object... args);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    void logServiziRichiamati(LogGeneralDaoBean logGeneralDaoBean);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void logServiziRichiamatiEnd(LogGeneralDaoBean logGeneralDaoBean, String esito, String response,
                                 List<Errore> errori, String controllore, String encryptionKey);

	Errore getErroreCatalogo(String codiceErrore, Object... args) ;

	String getDescrizioneErroreRest(String esito);
}