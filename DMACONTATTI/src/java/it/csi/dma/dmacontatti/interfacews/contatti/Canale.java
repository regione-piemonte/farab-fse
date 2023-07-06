/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

public enum Canale {
	SMS("SMS"),
	EMAIL("EMAIL");

	private final String value;

	Canale(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
    
    public static Canale fromValue(String v) {
        for (Canale c: Canale.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static boolean isValid(String v) {
    	boolean valid = false;
        for (Canale c: Canale.values()) {
            if (c.value.equals(v)) {
                valid = true;
            }
        }
        return valid;
    }
    
	
}
