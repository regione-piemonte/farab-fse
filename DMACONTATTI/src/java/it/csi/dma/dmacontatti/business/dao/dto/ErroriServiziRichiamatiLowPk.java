/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class ErroriServiziRichiamatiLowPk implements Serializable {

    private Long id;

	public Long getId() {
		return id;
	}

	public void setIdLog(Long id) {
		this.id = id;
	}

	/**
     * @generated
     */
    public ErroriServiziRichiamatiLowPk() {
    }

    /**
     * @generated
     */
    public ErroriServiziRichiamatiLowPk(

    final Long id

    ) {

        this.id = id;

    }

    public final boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof ErroriServiziRichiamatiLowPk)) {
            return false;
        }

        final ErroriServiziRichiamatiLowPk cast = (ErroriServiziRichiamatiLowPk) other;

        if (id == null ? cast.getId() != id
                : !id.equals(cast.getId())) {
            return false;
        }

        return true;
    }

 
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

        ret.append("it.csi.dma.dmaccbl.business.dao.dmaccbl.dto.ErroriServiziRichiamatiLowPk: ");
        ret.append("idErrore=" + id);

        return ret.toString();
    }
}
