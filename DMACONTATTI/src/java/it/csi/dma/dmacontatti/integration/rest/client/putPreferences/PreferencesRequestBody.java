/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest.client.putPreferences;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;

import java.util.Map;

public class PreferencesRequestBody {

    private Map<String, String> serviceNames;

    public Map<String, String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(Map<String, String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    @JsonAnySetter
    public void add(String key, String value) {
        serviceNames.put(key, value);
    }

    @JsonAnyGetter
    public Map<String,String> getMap() {
        return serviceNames;
    }
}
