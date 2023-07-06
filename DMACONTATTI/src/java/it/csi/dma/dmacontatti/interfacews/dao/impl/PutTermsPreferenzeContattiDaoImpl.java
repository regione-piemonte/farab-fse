/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao.impl;

import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.integration.rest.RestClient;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadEmailDTO;
import it.csi.dma.dmacontatti.integration.rest.client.postTopic.NotificaPayloadSmsDTO;
import it.csi.dma.dmacontatti.integration.rest.client.putContacts.ContactsRequestBody;
import it.csi.dma.dmacontatti.integration.rest.client.putPreferences.PreferencesRequestBody;
import it.csi.dma.dmacontatti.integration.rest.client.putTerms.TermsRequestBody;
import it.csi.dma.dmacontatti.integration.rest.util.RestUtil;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.Contatto;
import it.csi.dma.dmacontatti.interfacews.contatti.GeneraOtp;
import it.csi.dma.dmacontatti.interfacews.contatti.PreferenzeServizio;
import it.csi.dma.dmacontatti.interfacews.contatti.PutTermsPreferenzeContatti;
import it.csi.dma.dmacontatti.interfacews.dao.PutTermsPreferenzeContattiDao;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutTermsPreferenzeContattiDaoImpl implements PutTermsPreferenzeContattiDao {

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");

    private RestClient restClient;
    private LogGeneralDao logGeneralDao;
    private String tokenPreferences;

    //urls
    private String urlRestPutTerms;
    private String urlRestPutContacts;
    private String urlRestPutPreferences;

    @Override
    public List<Errore> putTermsPreferenzeContatti(PutTermsPreferenzeContatti putTermsPreferenzeContatti, List<Errore> errori) throws Exception {

        //Chiamata putTerms
        String urlRestPutTermsFormatted = null;
        urlRestPutTermsFormatted = RestUtil.getFormattedUrl(urlRestPutTerms, putTermsPreferenzeContatti.getCodiceFiscalePaziente());
        ResponseEntity<String> responsePutTerms = callPutTerms(putTermsPreferenzeContatti, urlRestPutTermsFormatted);
        if(responsePutTerms == null || responsePutTerms.getStatusCode() != HttpStatus.OK){
            errori.add(logGeneralDao.getErroreCatalogo(Constants.ERRORE_GENERICO_REST));
            return errori;
        }

        //Chiamata putContacts
        String urlRestPutContactsFormatted = null;
        urlRestPutContactsFormatted = RestUtil.getFormattedUrl(urlRestPutContacts, putTermsPreferenzeContatti.getCodiceFiscalePaziente());
        ResponseEntity<String> responsePutContacts = callPutContacts(putTermsPreferenzeContatti, urlRestPutContactsFormatted);
        if(responsePutContacts == null || responsePutContacts.getStatusCode() != HttpStatus.OK){
            errori.add(logGeneralDao.getErroreCatalogo(Constants.ERRORE_GENERICO_REST));
            return errori;
        }

        //Chiamata putPreferences
        if(putTermsPreferenzeContatti.getListaPreferenzeServizi() != null && putTermsPreferenzeContatti.getListaPreferenzeServizi().getPreferenzeServizio() != null
        		&& putTermsPreferenzeContatti.getListaPreferenzeServizi().getPreferenzeServizio().size() > 0) {
            String urlRestPutPreferencesFormatted = null;
            urlRestPutPreferencesFormatted = RestUtil.getFormattedUrl(urlRestPutPreferences, putTermsPreferenzeContatti.getCodiceFiscalePaziente());
        	ResponseEntity<String> responsePutPreferences = callPutPreferences(putTermsPreferenzeContatti, urlRestPutPreferencesFormatted);
        	if(responsePutPreferences == null || responsePutPreferences.getStatusCode() != HttpStatus.OK){
        		errori.add(logGeneralDao.getErroreCatalogo(Constants.ERRORE_GENERICO_REST));
        		return errori;
        	}
        }

        return errori;
    }

    private ResponseEntity<String> callPutTerms(PutTermsPreferenzeContatti putTermsPreferenzeContatti, String urlRestPutTermsFormatted) throws Exception {
        TermsRequestBody termsRequestBody = generaRequestPutTerms(putTermsPreferenzeContatti);

        return restClient.chiamataRestAuth(termsRequestBody, putTermsPreferenzeContatti.getRichiedente().getUUID(),
                Constants.PUT_TERMS, urlRestPutTermsFormatted, HttpMethod.PUT, tokenPreferences, putTermsPreferenzeContatti.getRichiedente().getCodiceFiscaleRichiedente());
    }

    private TermsRequestBody generaRequestPutTerms(PutTermsPreferenzeContatti putTermsPreferenzeContatti) {
        TermsRequestBody termsRequestBody = new TermsRequestBody();
        termsRequestBody.setHash(putTermsPreferenzeContatti.getHash());
        return termsRequestBody;
    }

    private ResponseEntity<String> callPutContacts(PutTermsPreferenzeContatti putTermsPreferenzeContatti, String urlRestPutContactsFormatted) throws Exception {
        ContactsRequestBody contactsRequestBody = generaRequestPutContacs(putTermsPreferenzeContatti);

        return restClient.chiamataRestAuth(contactsRequestBody, putTermsPreferenzeContatti.getRichiedente().getUUID(),
                Constants.PUT_CONTACTS, urlRestPutContactsFormatted, HttpMethod.PUT, tokenPreferences, putTermsPreferenzeContatti.getRichiedente().getCodiceFiscaleRichiedente());
    }

    private ContactsRequestBody generaRequestPutContacs(PutTermsPreferenzeContatti putTermsPreferenzeContatti) {
        ContactsRequestBody contactsRequestBody = new ContactsRequestBody();
        for(Contatto contatto : putTermsPreferenzeContatti.getListaContatti().getContatto()){
            if(contatto.getCanale().equalsIgnoreCase(Constants.CANALE_MAIL)){
                contactsRequestBody.setEmail(contatto.getValoreContatto());
            }else{
                contactsRequestBody.setSms(Constants.PREFISSO_PHONE + contatto.getValoreContatto());
            }
        }
        contactsRequestBody.setLanguage(Constants.LANGUAGE_IT);
        return contactsRequestBody;
    }

    private ResponseEntity<String> callPutPreferences(PutTermsPreferenzeContatti putTermsPreferenzeContatti, String urlRestPutPreferencesFormatted) throws Exception {
        PreferencesRequestBody preferencesRequestBody = generaRequestPutPreferences(putTermsPreferenzeContatti);

        return restClient.chiamataRestAuth(preferencesRequestBody, putTermsPreferenzeContatti.getRichiedente().getUUID(),
                Constants.PUT_PREFERENCES, urlRestPutPreferencesFormatted, HttpMethod.PUT, tokenPreferences, putTermsPreferenzeContatti.getRichiedente().getCodiceFiscaleRichiedente());
    }

    private PreferencesRequestBody generaRequestPutPreferences(PutTermsPreferenzeContatti putTermsPreferenzeContatti) {
        PreferencesRequestBody preferencesRequestBody = new PreferencesRequestBody();
//        List<String> serviceNames = new ArrayList<>();
//        for(PreferenzeServizio preferenzeServizio : putTermsPreferenzeContatti.getListaPreferenzeServizi().getPreferenzeServizio()){
//            serviceNames.add(preferenzeServizio.getServizio().getCodice() + ": " + preferenzeServizio.getCanaliAttiviCittadino());
//        }
        Map<String, String> serviceNames = new HashMap<>();
        for(PreferenzeServizio preferenzeServizio : putTermsPreferenzeContatti.getListaPreferenzeServizi().getPreferenzeServizio()){
            serviceNames.put(preferenzeServizio.getServizio().getCodice(), preferenzeServizio.getCanaliAttiviCittadino());
        }
        preferencesRequestBody.setServiceNames(serviceNames);
        return preferencesRequestBody;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public LogGeneralDao getLogGeneralDao() {
        return logGeneralDao;
    }

    public void setLogGeneralDao(LogGeneralDao logGeneralDao) {
        this.logGeneralDao = logGeneralDao;
    }

    public String getTokenPreferences() {
        return tokenPreferences;
    }

    public void setTokenPreferences(String tokenPreferences) {
        this.tokenPreferences = tokenPreferences;
    }

    public String getUrlRestPutTerms() {
        return urlRestPutTerms;
    }

    public void setUrlRestPutTerms(String urlRestPutTerms) {
        this.urlRestPutTerms = urlRestPutTerms;
    }

    public String getUrlRestPutContacts() {
        return urlRestPutContacts;
    }

    public void setUrlRestPutContacts(String urlRestPutContacts) {
        this.urlRestPutContacts = urlRestPutContacts;
    }

    public String getUrlRestPutPreferences() {
        return urlRestPutPreferences;
    }

    public void setUrlRestPutPreferences(String urlRestPutPreferences) {
        this.urlRestPutPreferences = urlRestPutPreferences;
    }
}
