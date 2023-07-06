/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;


import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.RisultatoCodice;
import it.csi.dma.dmacontatti.interfacews.ServiceResponse;

@XmlRootElement(name="verificaOtpResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificaOtpResponse",propOrder = {
	"dataInserimento",
	"dataScadenza"
})
public class VerificaOtpResponse extends ServiceResponse{
	
	private String dataInserimento;
	
	private String dataScadenza;

	public VerificaOtpResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerificaOtpResponse(Errore errore) {
		super(errore);
		// TODO Auto-generated constructor stub
	}

	public VerificaOtpResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
		// TODO Auto-generated constructor stub
	}

	public VerificaOtpResponse(String dataInserimento, String dataScadenza) {
		super();
		this.dataInserimento = dataInserimento;
		this.dataScadenza = dataScadenza;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	

}
