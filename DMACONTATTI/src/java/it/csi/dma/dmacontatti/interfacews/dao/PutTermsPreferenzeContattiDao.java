/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao;

import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.PutTermsPreferenzeContatti;

import java.util.List;

public interface PutTermsPreferenzeContattiDao {

    List<Errore> putTermsPreferenzeContatti(PutTermsPreferenzeContatti putTermsPreferenzeContatti,
                                            List<Errore> errori) throws Exception;
}
