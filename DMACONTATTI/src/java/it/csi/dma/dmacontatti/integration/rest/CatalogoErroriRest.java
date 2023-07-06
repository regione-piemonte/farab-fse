/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest;

public enum CatalogoErroriRest {

    INVALID_INPUT("Invalid input"),
    USER_NOT_FOUND("User not found"),
    UNAUTHORIZED("Unauthorized"),
    TOKEN_BLACKLIST ("The token has been blacklisted"),
    NOT_FOUND ("Not Found"),
    INTERNAL_ERROR ("Internal Server Error");

    private final String value;

    CatalogoErroriRest(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static CatalogoErroriRest fromValue(String v) {
        for (CatalogoErroriRest c: CatalogoErroriRest.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }
}
