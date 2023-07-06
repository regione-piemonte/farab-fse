/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/**
 * Filename : PazienteFiltroByIdAndContattoAnagrafe.java
 */
package it.csi.dma.dmacontatti.business.dao.filter;

/**
 * Project dmaccbl
 * 
 * @author Cristiano Masiero
 * @Date 10/gen/2014
 * @version $Id: $
 * 
 */
public class PazienteFiltroByIdAndContattoAnagrafeEvento {

    private Long id;
    private Long id_catalogo_evento_notifica;

    public PazienteFiltroByIdAndContattoAnagrafeEvento() {
    };

    public PazienteFiltroByIdAndContattoAnagrafeEvento(Long id,
            Long id_catalogo_evento_notifica) {
        this.id = id;
        this.id_catalogo_evento_notifica = id_catalogo_evento_notifica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_catalogo_evento_notifica() {
        return id_catalogo_evento_notifica;
    }

    public void setId_catalogo_evento_notifica(Long id_catalogo_evento_notifica) {
        this.id_catalogo_evento_notifica = id_catalogo_evento_notifica;
    }

}
