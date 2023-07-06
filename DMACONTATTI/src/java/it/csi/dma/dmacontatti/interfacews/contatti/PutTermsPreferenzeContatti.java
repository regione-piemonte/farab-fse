/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PutTermsPreferenzeContatti")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PutTermsPreferenzeContatti",propOrder = {
	"richiedente",
	"codiceFiscalePaziente",
	"hash",
	"listaContatti",
	"listaPreferenzeServizi"
})
public class PutTermsPreferenzeContatti {

	Richiedente richiedente;
	
	String codiceFiscalePaziente;

	String hash;
	
	Contatti listaContatti;
	
	ListaPreferenzeServizi listaPreferenzeServizi;

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

	public Contatti getListaContatti() {
		return listaContatti;
	}

	public void setListaContatti(Contatti listaContatti) {
		this.listaContatti = listaContatti;
	}

	public ListaPreferenzeServizi getListaPreferenzeServizi() {
		return listaPreferenzeServizi;
	}

	public void setListaPreferenzeServizi(ListaPreferenzeServizi listaPreferenzeServizi) {
		this.listaPreferenzeServizi = listaPreferenzeServizi;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
