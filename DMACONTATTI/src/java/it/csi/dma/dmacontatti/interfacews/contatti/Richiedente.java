/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement()
@XmlType(namespace="http://dma.csi.it/")
public class Richiedente implements Serializable {

	private static final long serialVersionUID = 6123012386525924653L;

	private String indirizzoIp;

	private String UUID;

	private String applicazioneChiamante;

	private String codiceFiscaleRichiedente;

	private String codiceTokenOperazione;

	public String getIndirizzoIp() {
		return indirizzoIp;
	}

	public void setIndirizzoIp(String indirizzoIp) {
		this.indirizzoIp = indirizzoIp;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getCodiceTokenOperazione() {
		return codiceTokenOperazione;
	}

	public void setCodiceTokenOperazione(String codiceTokenOperazione) {
		this.codiceTokenOperazione = codiceTokenOperazione;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
    
}
