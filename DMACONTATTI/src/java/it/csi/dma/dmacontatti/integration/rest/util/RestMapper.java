/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.integration.rest.client.getPreferences.Preferences;
import it.csi.dma.dmacontatti.integration.rest.client.getServices.Service;

public class RestMapper {
	
	private final static Logger _log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
	static String className = "RestMapper";
	
	 public static List<Service> createModelService(String jsonSource) throws JSONException {
		String methodName = "createModelService";
	       List<Service> result = new ArrayList<Service>();
	       try {
	       	if(!"".equals(jsonSource)) {
	       		JSONArray jsonArray = new JSONArray(jsonSource);
	
	       		for (int i = 0; i < jsonArray.length(); i++) {
	       			JSONObject jsonObject = jsonArray.getJSONObject(i);
	       			Service modelService = new Service();
	       			modelService.setChannels(getStringFromJsonObject(jsonObject, "channels"));
	       			modelService.setName(getStringFromJsonObject(jsonObject, "name"));
	       			modelService.setTags(getStringFromJsonObject(jsonObject, "tags"));
	       			modelService.setUuid(getStringFromJsonObject(jsonObject, "uuid"));
	       			result.add(modelService);
	       		}
	       	}
	       } catch (Exception e) {
	           _log.error("["+className+"::"+methodName+"] ", e);
	       }
	       return result;
	   }
	 
	 public static List<Preferences> createModelPreferences(String jsonSource) throws JSONException {
			String methodName = "createModelPreferences";
		       List<Preferences> result = new ArrayList<Preferences>();
		       try {
		       	if(!"".equals(jsonSource)) {
		       		JSONArray jsonArray = new JSONArray(jsonSource);
		
		       		for (int i = 0; i < jsonArray.length(); i++) {
		       			JSONObject jsonObject = jsonArray.getJSONObject(i);
		       			Preferences modelPreferences = new Preferences();
		       			modelPreferences.setChannels(getStringFromJsonObject(jsonObject, "channels"));
		       			modelPreferences.setService_name(getStringFromJsonObject(jsonObject, "service_name"));
		       			modelPreferences.setUser_id(getStringFromJsonObject(jsonObject, "user_id"));
		       			modelPreferences.setUuid(getStringFromJsonObject(jsonObject, "uuid"));
		       			result.add(modelPreferences);
		       		}
		       	}
		       } catch (Exception e) {
		           _log.error("["+className+"::"+methodName+"] ", e);
		       }
		       return result;
		   }
	 
	 private static String getStringFromJsonObject(JSONObject jsonObject, String tipoCampo) throws JSONException {
	        return (jsonObject.has(tipoCampo) && !jsonObject.isNull(tipoCampo)) ? jsonObject.getString(tipoCampo) : null;
	    }

}
