/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

public class Servizio {
	
	String codice;
	
	String descrizione;
	
	String canaliAttiviServizio;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCanaliAttiviServizio() {
		return canaliAttiviServizio;
	}

	public void setCanaliAttiviServizio(String canaliAttiviServizio) {
		this.canaliAttiviServizio = canaliAttiviServizio;
	}

}
