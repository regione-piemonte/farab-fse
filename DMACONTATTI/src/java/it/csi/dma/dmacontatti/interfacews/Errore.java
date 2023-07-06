/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews;

import javax.xml.bind.annotation.XmlType;

/**
 * L'Errore ha un codice, una descrizione e il contesto in cui si Ã¨ verificato.
 * 
 * @author Alberto Lagna
 * 
 */
@XmlType(namespace = "http://dma.csi.it/")
public class Errore {

	private String codice;

	private String descrizione;

	private static final long serialVersionUID = 3747860845860179038L;

	public Errore() {
		super();
	}

	public Errore(String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}

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
}