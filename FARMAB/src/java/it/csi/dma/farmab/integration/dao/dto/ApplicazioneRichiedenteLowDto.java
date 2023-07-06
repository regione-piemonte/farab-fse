/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class ApplicazioneRichiedenteLowDto implements Serializable {

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
    private String codiceApplicazione;

    /**
     * @generated
     */
     public final void setCodiceApplicazione(String val) {
        this.codiceApplicazione = val;
    }

    /**
     * @generated
     */
     public final String getCodiceApplicazione() {
        return this.codiceApplicazione;
    }

    /**
     * @generated
     */
    private String descrizioneApplicazione;

    /**
     * @generated
     */
     public final void setDescrizioneApplicazione(String val) {
        this.descrizioneApplicazione = val;
    }

    /**
     * @generated
     */
     public final String getDescrizioneApplicazione() {
        return this.descrizioneApplicazione;
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
    private String flgSupportaRuoloCittadino;

    /**
     * @generated
     */
     public final void setFlgSupportaRuoloCittadino(String val) {
        this.flgSupportaRuoloCittadino = val;
    }

    /**
     * @generated
     */
     public final String getFlgSupportaRuoloCittadino() {
        return this.flgSupportaRuoloCittadino;
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
     * @return ApplicazioneRichiedenteLowPk
     * @generated
     */
  /*   public final ApplicazioneRichiedenteLowPk createPk() {
        return new ApplicazioneRichiedenteLowPk(id);
    }
*/
    /**
     * Method 'toString'
     * 
     * @return String
     * @generated
     */
    public final String toString() {
        // TODO
        return super.toString();
    }

}
