/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="verificaOtp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificaOtp",propOrder = {
		"richiedente",
		"codiceFiscalePaziente",
		"canale",
		"codiceOTP"
})
public class VerificaOtp {
	
	Richiedente richiedente;
	
	String codiceFiscalePaziente;
	
	Canale canale;
	
	String codiceOTP;

	public VerificaOtp() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getCodiceOTP() {
		return codiceOTP;
	}

	public void setCodiceOTP(String codiceOTP) {
		this.codiceOTP = codiceOTP;
	}

}
