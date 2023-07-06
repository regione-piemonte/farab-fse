/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.filter;

/**
 * Classe usata per passare i parametri "filtro" nella query findByCF del
 * PazienteDao
 * 
 * @author Alberto Lagna
 * 
 */
public class PazienteFiltroByCF {
    private String cf;

    public PazienteFiltroByCF(String cf) {
        super();
        this.cf = cf;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
}