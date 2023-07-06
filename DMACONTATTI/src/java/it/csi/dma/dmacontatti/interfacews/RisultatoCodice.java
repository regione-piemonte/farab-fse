/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews;

import javax.xml.bind.annotation.XmlType;


/**
 * Risultato di un'operazione
 * 
 * @author Alberto Lagna
 *
 */
@XmlType(namespace="http://dma.csi.it/")
public enum RisultatoCodice {

    SUCCESSO("SUCCESSO"),
    FALLIMENTO("FALLIMENTO");
   
    private final String value;

    RisultatoCodice(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static RisultatoCodice fromValue(String v) {
        for (RisultatoCodice c: RisultatoCodice.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static boolean isValid(String v) {
    	boolean valid = false;
        for (RisultatoCodice c: RisultatoCodice.values()) {
            if (c.value.equals(v)) {
                valid = true;
            }
        }
        return valid;
    }
    
}
