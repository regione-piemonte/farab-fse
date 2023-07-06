/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class ConfigurazioneLowPk implements Serializable {

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
    public ConfigurazioneLowPk() {
    }

    /**
     * @generated
     */
    public ConfigurazioneLowPk(

    final Long id

    ) {

        this.id = id;

    }

    /**
     * Method 'equals'
     * 
     * @param other
     * @return boolean
     */
    public final boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof ConfigurazioneLowPk)) {
            return false;
        }

        final ConfigurazioneLowPk cast = (ConfigurazioneLowPk) other;

        if (id == null ? cast.getId() != id : !id.equals(cast.getId())) {
            return false;
        }

        return true;
    }

    /**
     * Method 'hashCode'
     * 
     * @return int
     */
    public final int hashCode() {
        int hashCode = 0;

        if (id != null) {
            hashCode = 29 * hashCode + id.hashCode();
        }

        return hashCode;
    }

    /**
     * Method 'toString'
     * 
     * @return String
     */
    public final String toString() {
        StringBuffer ret = new StringBuffer();

        ret.append("it.csi.dma.dmaccbl.business.dao.dmaccbl.dto.ConfigurazioneLowPk: ");
        ret.append("id=" + id);

        return ret.toString();
    }
}
