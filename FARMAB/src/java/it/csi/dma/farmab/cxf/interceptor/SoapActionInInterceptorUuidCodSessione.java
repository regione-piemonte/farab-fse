/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;
import it.csi.dma.farmab.util.Constants;
import it.csi.dma.farmab.util.FarmabUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SoapActionInInterceptorUuidCodSessione extends SoapActionInInterceptorBase {

	@Override
	protected  String getUuid(String soapMessage) {
		String newStrPattern = soapMessage.replaceAll("<\\/[a-z0-9]*:", "<\\/");
		String newStrPattern2 = newStrPattern.replaceAll("<[a-z0-9]*:", "<");
		String codSessione = StringUtils.substringBetween(newStrPattern2, "<codSessione>", "</codSessione>");
		return codSessione;
	}


	@Override
	protected LMessaggiDto riempilLMessaggio(String soapMessage, String xRequestId, String uuid, String ipChiamante) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {

		try {
			//elencofarmacieabitualirequest.xml
			String elencoFarmacie = new String( Files.readAllBytes(Paths.get("D:\\getDelegantiFarmacie.xml")));
			String request = StringUtils.substringBetween(elencoFarmacie, "<soap:Body>",  "</soap:Body>");
			if(request == null){
				request = StringUtils.substringBetween(elencoFarmacie, "<soapenv:Body>", "</soapenv:Body>");
			}

			String requestSenzaNamespace1 = request.replaceAll("<\\/[a-z0-9]*:", "<\\/");
			String requestSenzaNamespace = requestSenzaNamespace1.replaceAll("<[a-z0-9]*:", "<");
			String nomeServizio = StringUtils.substringBetween(requestSenzaNamespace, "<", " ");
			System.out.println("nomeservizio: " + nomeServizio);



			//prova con messageformat:
			String patt = "Un farmacista ha visualizzato lï¿½elenco delle ricette presenti nel tuo Fascicolo Sanitario. Riferimenti tecnici: codice fiscale assistito={0} , farmacista richiedente {1}";
			String formattata = MessageFormat.format(patt, "cf1", "cf2");
			System.out.println("formattata: " + formattata);


			//String source = new String( Files.readAllBytes(Paths.get("D:\\request.xml")));
			String source = new String( Files.readAllBytes(Paths.get("D:\\elencoRIcetteFarmacieFarmacia.xml")));
			String newStr = source.replaceAll(".*\\<dmaf:", "<");
			//String newStr2 = newStr.replaceAll(".*\\<[\\\\/]dmaf:", "<");
			String newStr2 = newStr.replaceAll("<\\/dmaf:", "<\\/");

			String prova = source.replaceAll("<dmaf:", "<");



			//String newStr2 = newStr.replaceAll(".*\\<[\\\\/]dmaf:", "<");
			String newStrPattern = source.replaceAll("<\\/[a-z0-9]*:", "<\\/");

			String newStrPattern2 = newStrPattern.replaceAll("<[a-z0-9]*:", "<");

			String codSessione = StringUtils.substringBetween(newStrPattern2, "<codSessione>", "</codSessione>");

			System.out.println(source);

			System.out.println("--------PRIMA SOSTITUZIONE---------------");

			System.out.println(newStr);

			System.out.println("---------SECONDA SOSTITUZIONE--------------");

			System.out.println( newStr2);



			System.out.println("--------TERZA SOSTITUZIONE---------------");

			System.out.println(newStrPattern);

			System.out.println("---------QUARTA SOSTITUZIONE--------------");

			System.out.println( newStrPattern2);


			System.out.println("---------Cod Sessione--------------");

			System.out.println( codSessione);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






}
