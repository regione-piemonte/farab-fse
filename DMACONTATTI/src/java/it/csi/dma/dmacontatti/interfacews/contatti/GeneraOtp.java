/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="generaOtp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaOtp")
public class GeneraOtp {

	private Richiedente richiedente;

	private String codiceFiscalePaziente;

	private Canale canale;

	private String contatto;

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}

	public String getCodiceFiscalePaziente() {
		return codiceFiscalePaziente;
	}

	public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
		this.codiceFiscalePaziente = codiceFiscalePaziente;
	}

	public String getCanale() {
		if(canale == null)
			return null;
		return canale.getValue();
	}

	public void setCanale(Canale canale) {
		this.canale = canale;
	}

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}

	public GeneraOtp() {
		super();
	}
	
}
