/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest.util;

import java.text.MessageFormat;

public class RestUtil {

    public static String getFormattedUrl(String url, Object... args){
        MessageFormat fmt = new MessageFormat(url);
        return fmt.format(args);
    }
}
