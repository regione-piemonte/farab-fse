/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class ConfigurazioneLowDto implements Serializable {

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
    private String chiave;

    /**
     * @generated
     */
     public final void setChiave(String val) {
        this.chiave = val;
    }

    /**
     * @generated
     */
     public final String getChiave() {
        return this.chiave;
    }

    /**
     * @generated
     */
    private String valore;

    /**
     * @generated
     */
     public final void setValore(String val) {
        this.valore = val;
    }

    /**
     * @generated
     */
     public final String getValore() {
        return this.valore;
    }

    /**
     * @generated
     */
    private String descrizione;

    /**
     * @generated
     */
     public final void setDescrizione(String val) {
        this.descrizione = val;
    }

    /**
     * @generated
     */
     public final String getDescrizione() {
        return this.descrizione;
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
     * @return ConfigurazioneLowPk
     * @generated
     */
     public final ConfigurazioneLowPk createPk() {
        return new ConfigurazioneLowPk(id);
    }

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
