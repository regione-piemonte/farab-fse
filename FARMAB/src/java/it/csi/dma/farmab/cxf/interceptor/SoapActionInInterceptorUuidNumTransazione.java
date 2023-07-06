/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;

@Component
public class SoapActionInInterceptorUuidNumTransazione extends SoapActionInInterceptorBase {

	@Override
	protected String getUuid(String soapMessage) {
		String xRequestId = StringUtils.substringBetween(soapMessage, "<numeroTransazione>", "</numeroTransazione>");
		return xRequestId;
	}

	@Override
	protected LMessaggiDto riempilLMessaggio(String soapMessage, String xRequestId, String uuid, String ipChiamante) {
		LMessaggiDto lMessaggio = new LMessaggiDto();
		lMessaggio.setUuid(xRequestId);
		lMessaggio.setWso2Id(uuid);
		lMessaggio.setIpRichiedente(ipChiamante);
		String requestSenzaNamespace = getRequestSenzaNamespace(soapMessage); 
		lMessaggio.setServizioXml(getNomeServizioFromRequest(requestSenzaNamespace));
		lMessaggio.setCfAssistito(getCfAssistito(requestSenzaNamespace));
		lMessaggio.setCfUtente(getCfUtente(requestSenzaNamespace));
		lMessaggio.setRuoloUtente(getRuoloUtente(requestSenzaNamespace));
		lMessaggio.setChiamante(getChiamante(requestSenzaNamespace));
		lMessaggio.setIdMessaggioOrigin(xRequestId);
		
		return lMessaggio;
	}
	
	private static String getRequestSenzaNamespace(String soapMessage){
		String request = StringUtils.substringBetween(soapMessage, "<soap:Body>",  "</soap:Body>");
		if(request == null){
			request = StringUtils.substringBetween(soapMessage, "<soapenv:Body>",  "</soapenv:Body>");
		}
		String requestSenzaNamespace1 = request.replaceAll("<\\/[a-z0-9]*:", "<\\/");
		String requestSenzaNamespace = requestSenzaNamespace1.replaceAll("<[a-z0-9]*:", "<");
		
		return requestSenzaNamespace;
	}
	
	private static String getNomeServizioFromRequest(String request){
		/*final String SETFARMACIAABITUALE = "setFarmaciaAbituale";
		if(request.contains(SETFARMACIAABITUALE)){
			return SETFARMACIAABITUALE;
		}*/
		String nomeServizio = StringUtils.substringBetween(request, "<", " ");
		nomeServizio = StringUtils.remove(nomeServizio, ">");
		
		return StringUtils.trim(nomeServizio);
	}

	private static String getCfAssistito(String request){
		String cfAssistito = StringUtils.substringBetween(request, "<cfCittadino>", "</cfCittadino>");
		//nomeServizio = StringUtils.remove(nomeServizio, ">");
		return cfAssistito;
		
	}
	
	
	private static String getCfUtente(String request){
		String richiedente = StringUtils.substringBetween(request, "<richiedente>", "</richiedente>");
		if(richiedente == null){
			return null;
		}
		return StringUtils.substringBetween(richiedente,  "<codiceFiscale>", "</codiceFiscale>");		
	}
	
	
	private static String getRuoloUtente(String request){
		String richiedente = StringUtils.substringBetween(request, "<richiedente>", "</richiedente>");
		if(richiedente == null){
			return null;
		}
		String ruolo =  StringUtils.substringBetween(richiedente, "<ruolo>", "</ruolo>");
		if (ruolo == null){
			return null;
		}
		
		return StringUtils.substringBetween(ruolo,  "<codice>", "</codice>");		
	}
	
	
	private static String getChiamante(String request){
		String richiedente = StringUtils.substringBetween(request, "<richiedente>", "</richiedente>");
		if(richiedente == null){
			return null;
		}
		String applicazione =  StringUtils.substringBetween(richiedente, "<applicazione>", "</applicazione>");
		if (applicazione == null){
			return null;
		}
		
		return StringUtils.substringBetween(applicazione,  "<codice>", "</codice>");		
	}	
	
	public static void main(String[] args) {

		try {
			
			String s = null;
			System.out.println("prova StringUtils: " + StringUtils.trim(s));
			
			//elencofarmacieabitualirequest.xml
			String setFarmacieAbituali1 = new String( Files.readAllBytes(Paths.get("D:\\setFarmacieAbituale.xml")));
			
			String setDisassociaDevice = new String( Files.readAllBytes(Paths.get("D:\\setDisassociaDevice.xml")));
			
			
			String getDevicecertificato = new String( Files.readAllBytes(Paths.get("D:\\getDevicecertificato.xml")));
			
			String elencofarmacieabitualirequest =  new String( Files.readAllBytes(Paths.get("D:\\getElencoFarmacieAbituali.xml")));
			
			String getFarmaciaOccasionale =  new String( Files.readAllBytes(Paths.get("D:\\getFarmaciaOccasionale.xml")));		
			
			/**
			 * SET FARMACIE ABITUALI
			 */
			
			String reqsetFarmacieAbituali1 = getRequestSenzaNamespace(setFarmacieAbituali1);
			
			System.out.println("Nome Servizio setFarmacieAbituali1: " + getNomeServizioFromRequest(reqsetFarmacieAbituali1) 
			+ " cfAssistito: " + getCfAssistito(reqsetFarmacieAbituali1) + " cfUtente: " + getCfUtente(reqsetFarmacieAbituali1)
			+ " ruolo: " + getRuoloUtente(reqsetFarmacieAbituali1) );
			
		
			/**
			 * SET DISASSOCIA DEVICE
			 */
			
			String reqsetDisassociaDevice = getRequestSenzaNamespace(setDisassociaDevice);
			
			System.out.println("Nome Servizio setDisassociaDevice: " + getNomeServizioFromRequest(reqsetDisassociaDevice)
			+  " cfAssistito: " + getCfAssistito(reqsetDisassociaDevice) + " cfUtente: " + getCfUtente(reqsetDisassociaDevice)
			+ " ruolo: " + getRuoloUtente(reqsetDisassociaDevice) );
			
			
			/**
			 * GET DEVICE CERTIFICATO
			 */
			
			String reqgetDevicecertificato = getRequestSenzaNamespace(getDevicecertificato);
			
			System.out.println("Nome Servizio getDevicecertificato: " + getNomeServizioFromRequest(reqgetDevicecertificato)
			+  " cfAssistito: " + getCfAssistito(reqgetDevicecertificato) + " cfUtente: " + getCfUtente(reqgetDevicecertificato)
			+ " ruolo: " + getRuoloUtente(reqgetDevicecertificato));		
			
			/**
			 * ELENCO FARMACIEABITUALI REQUEST
			 */
			
			String reqelencofarmacieabitualirequest = getRequestSenzaNamespace(elencofarmacieabitualirequest);
			
			System.out.println("Nome Servizio elencofarmacieabitualirequest: " + getNomeServizioFromRequest(reqelencofarmacieabitualirequest)
			 +  " cfAssistito: " + getCfAssistito(reqelencofarmacieabitualirequest)+ " cfUtente: " + getCfUtente(reqelencofarmacieabitualirequest)
			 + " ruolo: " + getRuoloUtente(reqelencofarmacieabitualirequest));
			
			
			/**
			 * GET FARMACIA OCCASIONALE
			*/ 
			
			String getFarmaciaOccasionalerequest = getRequestSenzaNamespace(getFarmaciaOccasionale);
			
			System.out.println("Nome Servizio getFarmaciaOccasionale: " + getNomeServizioFromRequest(getFarmaciaOccasionalerequest)
			+   " cfAssistito: " + getCfAssistito(getFarmaciaOccasionalerequest)+ " cfUtente: " + getCfUtente(getFarmaciaOccasionalerequest) 
			+ " ruolo: " + getRuoloUtente(getFarmaciaOccasionalerequest) );						
			

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
