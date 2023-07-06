/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContattiOTPLowDto implements Serializable{
	
	private Long id;
	
	private Long idPaziente;
	
	private String canale;
	
	private String otp;
	
	private Timestamp dataInizioValidita;
	
	private Timestamp dataFineValidita;

	private String codiceFiscalePaziente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(Long idPaziente) {
		this.idPaziente = idPaziente;
	}

	public String getCanale() {
		return canale;
	}

	public void setCanale(String canale) {
		this.canale = canale;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getCodiceFiscalePaziente() {
		return codiceFiscalePaziente;
	}

	public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
		this.codiceFiscalePaziente = codiceFiscalePaziente;
	}

	/**
     * Method 'equals'
     * 
     * @param other
     * @return boolean
     * @generated
     */
    public final boolean equals(Object other) {
        // TODO
        return super.equals(other);
    }

    /**
     * Method 'hashCode'
     * 
     * @return int
     * @generated
     */
    public final int hashCode() {
        // TODO
        return super.hashCode();
    }
    
    public ContattiOTPLowPk createPk() {
    	return new ContattiOTPLowPk(id);
    }

	@Override
	public String toString() {
		return "ContattiLowDto [id=" + id + ", id_Paziente=" + idPaziente + ", canale=" + canale + ", otp=" + otp
				+ ", dataInizioValidita=" + dataInizioValidita + ", dataFineValidita=" + dataFineValidita + "]";
	}

}
