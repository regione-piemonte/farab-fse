/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.dao.impl;

import java.util.ArrayList;
import java.util.List;

import it.csi.dma.dmacontatti.business.log.CreateLogBeanDao;
import it.csi.dma.dmacontatti.business.log.LogGeneralDaoBean;
import it.csi.dma.dmacontatti.integration.rest.util.RestUtil;
import it.csi.dma.dmacontatti.interfacews.RisultatoCodice;
import it.csi.dma.dmacontatti.util.Utils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.integration.rest.RestClient;
import it.csi.dma.dmacontatti.integration.rest.client.getPreferences.Preferences;
import it.csi.dma.dmacontatti.integration.rest.client.getServices.Service;
import it.csi.dma.dmacontatti.integration.rest.util.RestMapper;
import it.csi.dma.dmacontatti.integration.rest.util.RestUtil;
import it.csi.dma.dmacontatti.integration.soap.client.DelegheBackOfficeService;
import it.csi.dma.dmacontatti.integration.soap.client.RicercaServizi;
import it.csi.dma.dmacontatti.integration.soap.client.RicercaServiziResponse;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenze;
import it.csi.dma.dmacontatti.interfacews.contatti.GetPreferenzeResponse;
import it.csi.dma.dmacontatti.interfacews.contatti.ListaPreferenzeServizi;
import it.csi.dma.dmacontatti.interfacews.contatti.PreferenzeServizio;
import it.csi.dma.dmacontatti.interfacews.contatti.Servizio;
import it.csi.dma.dmacontatti.interfacews.dao.GetPreferenzeDao;
import it.csi.dma.dmacontatti.validator.BaseValidator;

import javax.xml.ws.BindingProvider;

public class GetPreferenzeDaoImpl implements GetPreferenzeDao {
	
	private final static Logger _log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");
	private static final String SANSOL = "SANSOL";
	private RestClient restClient;
	private String urlRestServicesPreferences;
	private LogGeneralDao logGeneralDao;
	private String tokenPreferences;
	private String urlRestServicesCittadino;
	private DelegheBackOfficeService delegheBackOfficeClient;
	private CreateLogBeanDao createLogBeanDao;

	private String userDeleghe;
	private String passwordDeleghe;
	private String encryption_key;

	@Override
	public GetPreferenzeResponse recuperaServiziAttivi(GetPreferenze request, List<Errore> errori) throws Exception {
		// TODO Auto-generated method stub
		GetPreferenzeResponse getPreferenzeResponse = new GetPreferenzeResponse();
		//Chiamata a Get Canali Attivi
		ResponseEntity<String> responseServicesPreferences = restClient.chiamataRestAuth(null, request.getRichiedente().getUUID(),
				Constants.GET_SER, urlRestServicesPreferences, HttpMethod.GET, tokenPreferences, request.getRichiedente().getCodiceFiscaleRichiedente());
		if(responseServicesPreferences == null || responseServicesPreferences.getStatusCode() != HttpStatus.OK) {
			 errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.ERRORE_INTERNO_REST));
			 getPreferenzeResponse.setErrori(errori);
			 return getPreferenzeResponse;
		}
		//Chiamata a Get Servizi Attivi per il Cittadino
		String urlRestServicesCittadinoFormatted = null;
		urlRestServicesCittadinoFormatted = RestUtil.getFormattedUrl(urlRestServicesCittadino, request.getCodiceFiscalePaziente());
		ResponseEntity<String> responsePreferences = restClient.chiamataRestAuth(null, request.getRichiedente().getUUID(),
				Constants.GET_PREF, urlRestServicesCittadinoFormatted, HttpMethod.GET, tokenPreferences, request.getRichiedente().getCodiceFiscaleRichiedente());
		if(responsePreferences == null || (responsePreferences.getStatusCode() != HttpStatus.OK && responsePreferences.getStatusCode() != HttpStatus.NOT_FOUND)){
			 errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.ERRORE_INTERNO_REST));
			 getPreferenzeResponse.setErrori(errori);
			 return getPreferenzeResponse;
		}
		
		RicercaServiziResponse ricercaServiziResponse = null;
		//Creazione request soap modulo deleghe
		RicercaServizi ricercaServiziRequest = mapGetRicercaServiziClient(request);

		LogGeneralDaoBean logGeneralDaoBean = new LogGeneralDaoBean();
		//Aggiunta credenziali chiamata
		BindingProvider prov = (BindingProvider)delegheBackOfficeClient;
		prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,this.getUserDeleghe());
		prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.getPasswordDeleghe());

		//Creazione e scrittura Log per chiamata servizi esterni
		logGeneralDaoBean =
				createLogBeanDao.prepareLogBeanServiziRichiamatiStart(logGeneralDaoBean, request.getRichiedente().getUUID(), Constants.RIC_SER,
						Utils.xmlMessageFromObject(ricercaServiziRequest), this.getEncryption_key());
		logGeneralDao.logServiziRichiamati(logGeneralDaoBean);
		//Chiamata soap
		ricercaServiziResponse = delegheBackOfficeClient.ricercaServiziService(ricercaServiziRequest);
		//Gestione errori
		if(ricercaServiziResponse.getErrori() != null && !ricercaServiziResponse.getErrori().isEmpty()
				&& RisultatoCodice.FALLIMENTO.getValue().equals(ricercaServiziResponse.getEsito())) {
			errori.add(logGeneralDao.getErroreCatalogo(BaseValidator.ERRORE_INTERNO_SOAP));
			getPreferenzeResponse.setErrori(errori);
			logGeneralDao.logServiziRichiamatiEnd(logGeneralDaoBean, ricercaServiziResponse.getEsito(),
					Utils.xmlMessageFromObject(ricercaServiziResponse), errori,  Constants.RIC_SER, this.getEncryption_key());
			return getPreferenzeResponse;
		}

		//Update log chiamata servizi esterni senza errori
		logGeneralDao.logServiziRichiamatiEnd(logGeneralDaoBean, RisultatoCodice.SUCCESSO.getValue(),
				Utils.xmlMessageFromObject(ricercaServiziResponse), null, Constants.RIC_SER, this.getEncryption_key());

		getPreferenzeResponse = setServiziAttivi(getPreferenzeResponse, responseServicesPreferences, ricercaServiziResponse, responsePreferences);
	
		return getPreferenzeResponse;
	}


	/**
	 * @param request
	 */
	public RicercaServizi mapGetRicercaServiziClient(GetPreferenze request) {
		RicercaServizi ricercaServiziRequest = new RicercaServizi();
		it.csi.dma.dmacontatti.integration.soap.client.Richiedente richiedenteGetServizi = new it.csi.dma.dmacontatti.integration.soap.client.Richiedente();
		richiedenteGetServizi.setCodiceFiscale(request.getRichiedente().getCodiceFiscaleRichiedente());
		it.csi.dma.dmacontatti.integration.soap.client.ApplicazioneRichiedente applicazioneRichiedenteGetServizi = new it.csi.dma.dmacontatti.integration.soap.client.ApplicazioneRichiedente();
		applicazioneRichiedenteGetServizi.setCodice(SANSOL);
		applicazioneRichiedenteGetServizi.setIdRequest(request.getRichiedente().getUUID());
		richiedenteGetServizi.setServizio(applicazioneRichiedenteGetServizi);
		ricercaServiziRequest.setRichiedente(richiedenteGetServizi);
		return ricercaServiziRequest;
	}


	/**
	 * @param responseEntity
	 * @return
	 * @throws JSONException
	 */
	public GetPreferenzeResponse setServiziAttivi(GetPreferenzeResponse getPreferenzeResponse, ResponseEntity<String> responseEntity, RicercaServiziResponse ricercaServiziResponse, ResponseEntity<String> responsePreferences) throws JSONException {
		List<Service> modelServiceList = RestMapper.createModelService(responseEntity.getBody());
		List<Preferences> modelPreferencesList = RestMapper.createModelPreferences(responsePreferences.getBody());
		ListaPreferenzeServizi listaPreferenzeServizi = new ListaPreferenzeServizi();
		List<PreferenzeServizio> preferenzeServizioList = new ArrayList<PreferenzeServizio>();
		List<it.csi.dma.dmacontatti.integration.soap.client.Servizio> servizioDelegheList = ricercaServiziResponse.getServizi().getServizio();
		boolean flg_ctr_pagopa=true;
		
		for(Service service : modelServiceList) {
			PreferenzeServizio preferenzeServizio = new PreferenzeServizio();
			Servizio servizio = new Servizio();
			servizio.setCanaliAttiviServizio(service.getChannels());
			servizio.setCodice(service.getName());
			preferenzeServizio.setServizio(servizio);
			if(modelPreferencesList != null && !modelPreferencesList.isEmpty()){
				for(Preferences preferences : modelPreferencesList) {
					if(service.getName().equals(preferences.getService_name())) {
						preferenzeServizio.setCanaliAttiviCittadino(preferences.getChannels());
					}
				}
			}
			
			for(it.csi.dma.dmacontatti.integration.soap.client.Servizio servizioDel : servizioDelegheList) {
				if (servizioDel.getCodice().equalsIgnoreCase(service.getName())) {
					servizio.setDescrizione(servizioDel.getDescrizione());
					if (servizioDel.isNotificabile() != null && servizioDel.isNotificabile().booleanValue()) {
						preferenzeServizioList.add(preferenzeServizio);
					}
				} else if (flg_ctr_pagopa && "ptw-pagopa".equalsIgnoreCase(service.getName())) {
					flg_ctr_pagopa=false;
					servizio.setDescrizione("Pagamenti");
					preferenzeServizioList.add(preferenzeServizio);
				}
			}			
		}
		
		listaPreferenzeServizi.setPreferenzeServizio(preferenzeServizioList);
		getPreferenzeResponse.setListaPreferenzeServizi(listaPreferenzeServizi);
		return getPreferenzeResponse;
	}
	
	
	

	public String getUrlRestServicesPreferences() {
		return urlRestServicesPreferences;
	}

	public void setUrlRestServicesPreferences(String urlRestServicesPreferences) {
		this.urlRestServicesPreferences = urlRestServicesPreferences;
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

	public String getUrlRestServicesCittadino() {
		return urlRestServicesCittadino;
	}


	public void setUrlRestServicesCittadino(String urlRestServicesCittadino) {
		this.urlRestServicesCittadino = urlRestServicesCittadino;
	}

	public DelegheBackOfficeService getDelegheBackOfficeClient() {
		return delegheBackOfficeClient;
	}


	public void setDelegheBackOfficeClient(DelegheBackOfficeService delegheBackOfficeClient) {
		this.delegheBackOfficeClient = delegheBackOfficeClient;
	}

	public CreateLogBeanDao getCreateLogBeanDao() {
		return createLogBeanDao;
	}

	public void setCreateLogBeanDao(CreateLogBeanDao createLogBeanDao) {
		this.createLogBeanDao = createLogBeanDao;
	}

	public String getUserDeleghe() {
		return userDeleghe;
	}

	public void setUserDeleghe(String userDeleghe) {
		this.userDeleghe = userDeleghe;
	}

	public String getPasswordDeleghe() {
		return passwordDeleghe;
	}

	public void setPasswordDeleghe(String passwordDeleghe) {
		this.passwordDeleghe = passwordDeleghe;
	}

	public String getEncryption_key() {
		return encryption_key;
	}

	public void setEncryption_key(String encryption_key) {
		this.encryption_key = encryption_key;
	}
}
