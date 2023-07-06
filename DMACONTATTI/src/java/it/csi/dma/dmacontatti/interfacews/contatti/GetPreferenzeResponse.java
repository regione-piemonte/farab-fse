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

import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.RisultatoCodice;
import it.csi.dma.dmacontatti.interfacews.ServiceResponse;

@XmlRootElement(name="GetPreferenzeResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPreferenzeResponse",propOrder = {
	"listaPreferenzeServizi"
})
public class GetPreferenzeResponse extends ServiceResponse{
	
	ListaPreferenzeServizi listaPreferenzeServizi;

	public GetPreferenzeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetPreferenzeResponse(Errore errore) {
		super(errore);
		// TODO Auto-generated constructor stub
	}

	public GetPreferenzeResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
		// TODO Auto-generated constructor stub
	}

	public ListaPreferenzeServizi getListaPreferenzeServizi() {
		return listaPreferenzeServizi;
	}

	public void setListaPreferenzeServizi(ListaPreferenzeServizi listaPreferenzeServizi) {
		this.listaPreferenzeServizi = listaPreferenzeServizi;
	}
}
