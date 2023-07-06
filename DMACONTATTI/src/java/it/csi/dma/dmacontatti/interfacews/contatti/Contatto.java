/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "contatto",propOrder = {
		"canale",
		"valoreContatto"
	})
public class Contatto {
	
	@XmlElement
	Canale canale;
	
	String valoreContatto;

	public String getCanale() {
		if(canale == null)
			return null;
		return canale.getValue();
	}

	public void setCanale(Canale canale) {
		this.canale = canale;
	}

	public String getValoreContatto() {
		return valoreContatto;
	}

	public void setValoreContatto(String valoreContatto) {
		this.valoreContatto = valoreContatto;
	}
	
	

}
