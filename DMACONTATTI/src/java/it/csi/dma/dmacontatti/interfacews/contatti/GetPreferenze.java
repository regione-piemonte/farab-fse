/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="getPreferenze")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPreferenze", propOrder = {
	"richiedente",
	"codiceFiscalePaziente"
})
public class GetPreferenze {
	
	public Richiedente richiedente;
	
	public String codiceFiscalePaziente;

	public GetPreferenze() {
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
	
	

}
