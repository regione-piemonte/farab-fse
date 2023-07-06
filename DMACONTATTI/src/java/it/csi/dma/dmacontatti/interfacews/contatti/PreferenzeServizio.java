/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

public class PreferenzeServizio {

    Servizio servizio;

    String canaliAttiviCittadino;

    public Servizio getServizio() {
        return servizio;
    }

    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    public String getCanaliAttiviCittadino() {
        return canaliAttiviCittadino;
    }

    public void setCanaliAttiviCittadino(String canaliAttiviCittadino) {
        this.canaliAttiviCittadino = canaliAttiviCittadino;
    }
}
