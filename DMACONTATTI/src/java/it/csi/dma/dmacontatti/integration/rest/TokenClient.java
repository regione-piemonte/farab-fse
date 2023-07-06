/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.CreateLogBeanDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.integration.rest.client.getPreferences.Preferences;
import it.csi.dma.dmacontatti.interfacews.Errore;

public class TokenClient<T> {

	static String className = "TokenClient";
	private final static Logger _log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
	private CreateLogBeanDao createLogBeanDao;
    private LogGeneralDao logGeneralDaoMed;
    private String encryptionKey;

    //key per basic auth
    private String consumerKey;
    private String consumerSecret;
    //url per token service
    private String urlRestToken;
    private String token;
    
    

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
	private static final String grant_type = "client_credentials";

    public void chiamataToken(String UUID) throws Exception {
        ResponseEntity<String> response = null;
        try {
            RestTemplate restTemplate = RestTemplateConstructor.getRestTemplate();
            //String url = "http://localhost:3000/notify-mb/api/v1/topics/messages";

            HttpEntity<T> requestRest = getRequest();

            //Creazione e scrittura Log per chiamata servizi esterni
            LogGeneralDaoBean logGeneralDaoBean = new LogGeneralDaoBean();
            ObjectMapper objectMapper = new ObjectMapper();
            logGeneralDaoBean =
                    createLogBeanDao.prepareLogBeanServiziRichiamatiStart(logGeneralDaoBean, UUID, Constants.TOKEN,
                            "url: " + urlRestToken + " request: " + objectMapper.writeValueAsString(requestRest), this.getEncryptionKey());
            logGeneralDaoMed.logServiziRichiamati(logGeneralDaoBean);
            log.info("> URL REST: " + urlRestToken);

            org.springframework.http.converter.json.MappingJacksonHttpMessageConverter jsonHttpMessageConverter = new org.springframework.http.converter.json.MappingJacksonHttpMessageConverter();
//			jsonHttpMessageConverter.getObjectMapper().configure(Feature.FAIL_ON_EMPTY_BEANS, false);	// gestione null
            restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

//			String response = restTemplate.postForObject(urlrest, request, String.class);
//            Map<String,String> mapParameterGrandType = new HashMap<String, String>();
//            mapParameterGrandType.put("grant_type", grant_type);
            response = restTemplate.exchange(urlRestToken, HttpMethod.POST, requestRest, String.class);

            //Update log chiamata servizi esterni
            List<Errore> errori = mapErroriRest(response);

            logGeneralDaoMed.logServiziRichiamatiEnd(logGeneralDaoBean, response!=null?response.getStatusCode().toString():null,
                    response!=null?objectMapper.writeValueAsString(response):null, errori,  Constants.TOKEN, this.getEncryptionKey());
            log.info("> RESPONSE REST: " + response);
        }catch(HttpClientErrorException ex) {
            log.error("Errore in invio notifica:\n");
            log.error(ex);
            throw ex;
        }catch(Exception ex) {
            log.error("Errore generico in invio notifica", ex);
            throw ex;
        }
        setToken(createModelToken(response.getBody()));
    }

    private HttpEntity<T> getRequest(){

    	HttpHeaders headers = new HttpHeaders();

    	String auth = consumerKey + ":" + consumerSecret;
    	headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));

    	HttpEntity<T> requestRest = null;

    	requestRest = new HttpEntity<T>(headers);

    	return requestRest;
    }

    private List<Errore> mapErroriRest(ResponseEntity<String> response) {
        String esito = null;
        List<Errore> errori = null;
        if(response != null ){
            esito = response.getStatusCode().toString();
            String descrizioneErrore = logGeneralDaoMed.getDescrizioneErroreRest(esito);
            if(descrizioneErrore != null){
                errori = new ArrayList<Errore>();
                Errore errore = new Errore(esito, descrizioneErrore);
                errori.add(errore);
            }
        }
        return errori;
    }
    
    public static String createModelToken(String jsonSource) throws JSONException {
		String methodName = "createModelToken";
	       String result = new String();
	       try {
	       	if(!"".equals(jsonSource)) {
	       		JSONObject jsonObject = new JSONObject(jsonSource);
	       		result = getStringFromJsonObject(jsonObject, "access_token");
	       	}
	       } catch (Exception e) {
	           _log.error("["+className+"::"+methodName+"] ", e);
	       }
	       return result;
	   }
	
	private static String getStringFromJsonObject(JSONObject jsonObject, String tipoCampo) throws JSONException {
		return (jsonObject.has(tipoCampo) && !jsonObject.isNull(tipoCampo)) ? jsonObject.getString(tipoCampo) : null;
	}
	
	public String getToken(String UUID) throws Exception {
		if(token == null || token.isEmpty()) {
			chiamataToken(UUID);
		}
		return token;
	}
	
	public String refreshToken(String UUID) throws Exception {
		chiamataToken(UUID);
		return token;
	}

    public CreateLogBeanDao getCreateLogBeanDao() {
        return createLogBeanDao;
    }

    public void setCreateLogBeanDao(CreateLogBeanDao createLogBeanDao) {
        this.createLogBeanDao = createLogBeanDao;
    }

    public LogGeneralDao getLogGeneralDaoMed() {
        return logGeneralDaoMed;
    }

    public void setLogGeneralDaoMed(LogGeneralDao logGeneralDaoMed) {
        this.logGeneralDaoMed = logGeneralDaoMed;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

	public String getUrlRestToken() {
		return urlRestToken;
	}

	public void setUrlRestToken(String urlRestToken) {
		this.urlRestToken = urlRestToken;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
