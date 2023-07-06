/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao;

import java.util.List;

import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenze;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenzeResponse;

public interface GetPreferenzeDao {
	GetPreferenzeResponse recuperaServiziAttivi(GetPreferenze request, List<Errore> errori) throws Exception;
}
