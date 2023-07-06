/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class RuoloLowDto implements Serializable {

    /**
     * @generated
     */
    private Long id;

    /**
     * @generated
     */
     public final void setId(Long val) {
        this.id = val;
    }

    /**
     * @generated
     */
     public final Long getId() {
        return this.id;
    }

    /**
     * @generated
     */
    private String codiceRuolo;

    /**
     * @generated
     */
     public final void setCodiceRuolo(String val) {
        this.codiceRuolo = val;
    }

    /**
     * @generated
     */
     public final String getCodiceRuolo() {
        return this.codiceRuolo;
    }

    /**
     * @generated
     */
    private String descrizioneRuolo;

    /**
     * @generated
     */
     public final void setDescrizioneRuolo(String val) {
        this.descrizioneRuolo = val;
    }

    /**
     * @generated
     */
     public final String getDescrizioneRuolo() {
        return this.descrizioneRuolo;
    }

    /**
     * @generated
     */
    private java.sql.Timestamp dataInserimento;

    /**
     * @generated
     */
     public final void setDataInserimento(java.sql.Timestamp val) {
        this.dataInserimento = val;
    }

    /**
     * @generated
     */
     public final java.sql.Timestamp getDataInserimento() {
        return this.dataInserimento;
    }

    /**
     * @generated
     */
    private String flagVisibilePerConsenso;

    /**
     * @generated
     */
     public final void setFlagVisibilePerConsenso(String val) {
        this.flagVisibilePerConsenso = val;
    }

    private java.sql.Timestamp dataAggiornamento;

    public final void setDataAggiornamento(java.sql.Timestamp val) {
        this.dataAggiornamento = val;
    }

    public final java.sql.Timestamp getDataAggiornamento() {
         return this.dataAggiornamento;
    }

    private String codiceRuoloINI;

    public final void setCodiceRuoloINI(String val) {
	    this.codiceRuoloINI = val;
	}

    public final String getCodiceRuoloINI() {
        return this.codiceRuoloINI;
    }
    
    private String descrizioneRuoloINI;

    public final void setDescrizioneRuoloINI(String val) {
        this.descrizioneRuoloINI = val;
    }

    public final String getDescrizioneRuoloINI() {
        return this.descrizioneRuoloINI;
    }
    
    private String ruoloDPCM;
    
    public final void setRuoloDPCM(String val) {
        this.ruoloDPCM = val;
    }

    public final String getRuoloDPCM() {
        return this.ruoloDPCM;
    }
    
    private String categoriaRuolo;
    
    public final void setCategoriaRuolo(String val) {
        this.categoriaRuolo = val;
    }

    public final String getCategoriaRuolo() {
        return this.categoriaRuolo;
    }
    
    
    
     
    /**
     * @generated
     */
     public final String getFlagVisibilePerConsenso() {
        return this.flagVisibilePerConsenso;
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

    /**
     * Method 'createPk'
     * 
     * @return RuoloLowPk
     * @generated
     */
     public final RuoloLowPk createPk() {
        return new RuoloLowPk(id);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RuoloLowDto [id=" + id + ", codiceRuolo=" + codiceRuolo + ", descrizioneRuolo=" + descrizioneRuolo
				+ ", dataInserimento=" + dataInserimento + ", flagVisibilePerConsenso=" + flagVisibilePerConsenso
				+ ", dataAggiornamento=" + dataAggiornamento + ", codiceRuoloINI=" + codiceRuoloINI
				+ ", descrizioneRuoloINI=" + descrizioneRuoloINI + ", ruoloDPCM=" + ruoloDPCM + ", categoriaRuolo="
				+ categoriaRuolo + "]";
	}


}
