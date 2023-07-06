/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import java.util.List;

public class ListaPreferenzeServizi {
	
	List<PreferenzeServizio> preferenzeServizio;

	public List<PreferenzeServizio> getPreferenzeServizio() {
		return preferenzeServizio;
	}

	public void setPreferenzeServizio(List<PreferenzeServizio> preferenzeServizio) {
		this.preferenzeServizio = preferenzeServizio;
	}
}
