/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class PazienteLowPk implements Serializable {

    /**
     * @generated
     */
    private Long idPaziente;

    /**
     * @generated
     */
     public final void setIdPaziente(Long val) {
        this.idPaziente = val;
    }

    /**
     * @generated
     */
     public final Long getIdPaziente() {
        return this.idPaziente;
    }

    /**
     * @generated
     */
    public PazienteLowPk() {
    }

    /**
     * @generated
     */
    public PazienteLowPk(

    final Long idPaziente

    ) {

        this.idPaziente = idPaziente;

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

        if (!(other instanceof PazienteLowPk)) {
            return false;
        }

        final PazienteLowPk cast = (PazienteLowPk) other;

        if (idPaziente == null ? cast.getIdPaziente() != idPaziente
                : !idPaziente.equals(cast.getIdPaziente())) {
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

        if (idPaziente != null) {
            hashCode = 29 * hashCode + idPaziente.hashCode();
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

        ret.append("it.csi.dma.dmaccbl.business.dao.dmaccbl.dto.PazienteLowPk: ");
        ret.append("idPaziente=" + idPaziente);

        return ret.toString();
    }
}
