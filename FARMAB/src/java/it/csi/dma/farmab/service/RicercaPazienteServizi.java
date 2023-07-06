/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.csi.dma.dmafarma.ElencoRicetteFarmaciaRequest;
import it.csi.dma.farmab.util.Constants;
import it.csi.ricercapaziente.dmaccbl.PazienteService;
import it.csi.ricercapaziente.dmaccbl.RicercaPazienteResponse;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RicercaPazienteServizi {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private PazienteService pazienteService;

	private String pazienteServiceUser;
	private String pazienteServicePassword;

	public RicercaPazienteResponse ricercaPaziente(ElencoRicetteFarmaciaRequest elencoRicetteRequest) {
		log.info("DelegheElencoServizi::provaRicercaPaziente");
		RicercaPazienteResponse ricercaPazienteResponse = new RicercaPazienteResponse();

				//CREAZIONE DI UN UUID RANDOM
				String uuid = UUID.randomUUID().toString();

				it.csi.ricercapaziente.dma.Richiedente richiedente = new it.csi.ricercapaziente.dma.Richiedente();
				richiedente.setNumeroTransazione(uuid);
				richiedente.setTokenOperazione(uuid); //DA CAPIRE TOKEN OPERAZIONE
				richiedente.setCodiceFiscale(elencoRicetteRequest.getDatiFarmaciaRichiedente().getCfFarmacista());

				//CAMPO OBBLIGATORIO PER LA REQUEST
				it.csi.ricercapaziente.dma.ApplicazioneRichiedente applicazione = new it.csi.ricercapaziente.dma.ApplicazioneRichiedente();
				applicazione.setCodice("FSE");
				richiedente.setApplicazione(applicazione);

				it.csi.ricercapaziente.dma.RuoloDMA ruolo =  new it.csi.ricercapaziente.dma.RuoloDMA();
				ruolo.setCodice(elencoRicetteRequest.getDatiFarmaciaRichiedente().getRuolo());
				richiedente.setRuolo(ruolo);

				it.csi.ricercapaziente.dma.Paziente paziente = new it.csi.ricercapaziente.dma.Paziente();
				paziente.setCodiceFiscale(elencoRicetteRequest.getCfAssistito());

				it.csi.ricercapaziente.dmaccbl.RicercaPaziente ricercaPaziente = new it.csi.ricercapaziente.dmaccbl.RicercaPaziente();
				ricercaPaziente.setPaziente(paziente);
				ricercaPaziente.setRichiedente(richiedente);

				long startTime = System.currentTimeMillis();
				log.info("RICHIAMO SERVIZIO ESTERNO RicercaPazienteServizi.ricercaPaziente");
				ricercaPazienteResponse = getPazienteService().ricercaPaziente(ricercaPaziente);
				log.info("RISPOSTA SERVIZIO ESTERNO RicercaPazienteServizi.ricercaPaziente:"+(System.currentTimeMillis()-startTime)+" Millis");

		return ricercaPazienteResponse;
	}



	public String getPazienteServiceUser() {
		return pazienteServiceUser;
	}

	public void setPazienteServiceUser(String pazienteServiceUser) {
		this.pazienteServiceUser = pazienteServiceUser;
	}

	public String getPazienteServicePassword() {
		return pazienteServicePassword;
	}

	public void setPazienteServicePassword(String pazienteServicePassword) {
		this.pazienteServicePassword = pazienteServicePassword;
	}



	public PazienteService getPazienteService() {
		return pazienteService;
	}



	public void setPazienteService(PazienteService pazienteService) {
		this.pazienteService = pazienteService;
	}

}
