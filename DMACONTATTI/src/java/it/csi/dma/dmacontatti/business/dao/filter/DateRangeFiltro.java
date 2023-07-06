/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Filename : DateInitEndDto.java
 */
package it.csi.dma.dmacontatti.business.dao.filter;

import java.util.Date;

/**
 * 
 * Filtro generico per passare range di date.
 * 
 * Project dmaccbl
 * 
 * @author Cristiano Masiero
 * @Date 23/dic/2013
 * @version $Id: $
 * 
 */
public class DateRangeFiltro {

    private Date dataInizioRicerca;
    private Date dataFineRicerca;

    public DateRangeFiltro() {
    }

    public DateRangeFiltro(Date dataInizioRicerca, Date dataFineRicerca) {
        this.dataInizioRicerca = dataInizioRicerca;
        this.dataFineRicerca = dataFineRicerca;
    }

    public Date getDataInizioRicerca() {
        return dataInizioRicerca;
    }

    public void setDataInizioRicerca(Date dataInizioRicerca) {
        this.dataInizioRicerca = dataInizioRicerca;
    }

    public Date getDataFineRicerca() {
        return dataFineRicerca;
    }

    public void setDataFineRicerca(Date dataFineRicerca) {
        this.dataFineRicerca = dataFineRicerca;
    }

}
