/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest;

import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.CreateLogBeanDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.interfacews.Errore;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RestClient<T> {

    private CreateLogBeanDao createLogBeanDao;
    private LogGeneralDao logGeneralDaoMed;
    private String encryptionKey;
    private TokenClient tokenClient;

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
    
    static {
    	Security.addProvider(new BouncyCastleProvider());
    }

    public ResponseEntity<String> chiamataRest(T request, String UUID, String codiceServizio, String urlServizio,
                                               HttpMethod httpMethod, String token, String shibIdentitaCf) throws Exception {
        ResponseEntity<String> response = null;
        try {
            
            //String url = "http://localhost:3000/notify-mb/api/v1/topics/messages";
            HttpEntity<T> requestRest;
            
            requestRest = getRequest(request, token, shibIdentitaCf);
            //Creazione e scrittura Log per chiamata servizi esterni
            response = call(UUID, codiceServizio, urlServizio, httpMethod, requestRest);
        }catch(HttpClientErrorException ex) {
            log.error("Errore in invio notifica:\n");
            log.error(ex);
            throw ex;
        }catch(Exception ex) {
            log.error("Errore generico in invio notifica", ex);
            throw ex;
        }
        return response;
    }

    
    public ResponseEntity<String> chiamataRestAuth(T request, String UUID, String codiceServizio, String urlServizio,
    		HttpMethod httpMethod, String token, String shibIdentitaCf) throws Exception {
    	ResponseEntity<String> response = null;
    	try {
    		HttpEntity<T> requestRest;
    		String tokenAuth = tokenClient.getToken(UUID);
    		requestRest = getRequestAuthentication(request, token, shibIdentitaCf, tokenAuth);
    		response = call(UUID, codiceServizio, urlServizio, httpMethod, requestRest);
    		if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
    			tokenClient.refreshToken(UUID);
    			call(UUID, codiceServizio, urlServizio, httpMethod, requestRest);
    		}
    	}catch(HttpClientErrorException ex) {
    		log.error("Errore in invio notifica:\n");
    		log.error(ex);
    		throw ex;
    	}catch(Exception ex) {
    		log.error("Errore generico in invio notifica", ex);
    		throw ex;
    	}
    	return response;
    }
    
	/**
	 * @param UUID
	 * @param codiceServizio
	 * @param urlServizio
	 * @param httpMethod
	 * @param restTemplate
	 * @param requestRest
	 * @return
	 * @throws Exception
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 */
	public ResponseEntity<String> call(String UUID, String codiceServizio, String urlServizio, HttpMethod httpMethod, HttpEntity<T> requestRest)
			throws Exception, IOException, JsonGenerationException, JsonMappingException, RestClientException {
		RestTemplate restTemplate = RestTemplateConstructor.getRestTemplate();
		ResponseEntity<String> response;
		LogGeneralDaoBean logGeneralDaoBean = new LogGeneralDaoBean();
		ObjectMapper objectMapper = new ObjectMapper();
		logGeneralDaoBean =
		        createLogBeanDao.prepareLogBeanServiziRichiamatiStart(logGeneralDaoBean, UUID, codiceServizio,
		                "url: " + urlServizio + " request: " + objectMapper.writeValueAsString(requestRest), this.getEncryptionKey());
		logGeneralDaoMed.logServiziRichiamati(logGeneralDaoBean);
		log.info("> URL REST: " + urlServizio);

		org.springframework.http.converter.json.MappingJacksonHttpMessageConverter jsonHttpMessageConverter = new org.springframework.http.converter.json.MappingJacksonHttpMessageConverter();
//			jsonHttpMessageConverter.getObjectMapper().configure(Feature.FAIL_ON_EMPTY_BEANS, false);	// gestione null
		restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

        response = restTemplate.exchange(urlServizio, httpMethod, requestRest, String.class);

		//Update log chiamata servizi esterni
		List<Errore> errori = mapErroriRest(response);

		logGeneralDaoMed.logServiziRichiamatiEnd(logGeneralDaoBean, response!=null?response.getStatusCode().toString():null,
		        response!=null?objectMapper.writeValueAsString(response):null, errori, codiceServizio, this.getEncryptionKey());
		log.info("> RESPONSE REST: " + response);
		return response;
	}

    private HttpEntity<T> getRequest(T request, String token, String shibIdentitaCf){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("x-authentication", token);
        if(shibIdentitaCf != null){
            headers.add("Shib-Iride-IdentitaDigitale", shibIdentitaCf);
        }
        HttpEntity<T> requestRest = null;
        if(request != null){
            requestRest = new HttpEntity<T>(request, headers);
        }else{
            requestRest = new HttpEntity<T>(headers);
        }
        return requestRest;
    }
    
    private HttpEntity<T> getRequestAuthentication(T request, String token, String shibIdentitaCf, String tokenAuth){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("x-authentication", token);
        if(shibIdentitaCf != null){
            headers.add("Shib-Iride-IdentitaDigitale", shibIdentitaCf);
        }
        headers.add("Authorization", "Bearer "+ tokenAuth);
        HttpEntity<T> requestRest = null;
        if(request != null){
            requestRest = new HttpEntity<T>(request, headers);
        }else{
            requestRest = new HttpEntity<T>(headers);
        }
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


	public TokenClient getTokenClient() {
		return tokenClient;
	}


	public void setTokenClient(TokenClient tokenClient) {
		this.tokenClient = tokenClient;
	}
    
}
