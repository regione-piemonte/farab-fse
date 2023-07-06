/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {
    protected List<Errore> errori = null;
    protected RisultatoCodice esito = RisultatoCodice.SUCCESSO;

    public ServiceResponse() {
        super();
    }

    public ServiceResponse(List<Errore> errori, RisultatoCodice esito) {
        this.errori = errori;
        this.esito = esito;
    }

    public ServiceResponse(Errore errore) {
        this(null, RisultatoCodice.FALLIMENTO);
        errori = new ArrayList<Errore>();
        errori.add(errore);
    }

    public List<Errore> getErrori() {
        return errori;
    }

    public void setErrori(List<Errore> errori) {
        this.errori = errori;
    }

    public RisultatoCodice getEsito() {
        return esito;
    }

    public void setEsito(RisultatoCodice esito) {
        this.esito = esito;
    }
}