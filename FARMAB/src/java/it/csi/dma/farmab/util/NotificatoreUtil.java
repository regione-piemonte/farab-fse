/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import it.csi.dma.farmab.controller.FarmabGestioneDeviceOTPController;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotificatoreUtil {
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final Charset STD_CHARSET = Charset.forName("UTF-8");
	//public static final String  endpoint="http://tst-notify.csi.it/notify-mb/api/v1/topics/messages";
	//public static final String  token="IPPa/Q6jA7cLHsc6KjyCRpQ/WjLMQp24jbIuAWTJzcApfxsHz9XPlkKn0KrVQQ02wZn4zgtdIM3B9uB7+1z88PbQXi2XDHY6D1jNl7GkYKMlrdStc/2w3BweffmwBtP+p1GIs4z3E5tZX3jNBmXxEcUrZMYZMhVPqyPg676T7izWDTiEuGkKeUrw3GSJb9FT17+ruWPJDUF+/BLQvoR1OiLT5icdNqth5nbC9L2ckFiWcB56g+cnxdo5/Y0tujF2yOaeK/Dai941Oz9zTTeZSelJZzbT0fvIVtiuI7Le6edpXGMs85ZUg06yZIYUOj9hUbCbUIfhwzBVhl0I264eNCEuA7lE2Ha5bNMQDUmGS+2gmdyj+2a5u+mQWGR10ETMPbHTT+XPIQTpVRZOdU+A9ldj86pVUfNBLDroiHEpQezQOqBWyCcrIo6KzElReq5xtp12j8r+sq5pJIZi7mms1LsFgtBp1i29xDRNnD9is9MCdsjdyTkNnWm/1QfWWRzjLsn1dY7lyVLuuAVWhiAFwbpf64jHuC7LFugzkXRjyPi+6rSrVEuM0KBFN/MSwJCnyGd1tBTcDJNvBLQFKJqrOF0oHHd+eXyCIo/BqIGmJ97sJ5vCqjsIYUH1tkMQgKcr46oQxMgrnGjJEykP/LkAn3ikB7MiXh7sakj+MO1i0KMaNgvFn5JVQAYM9l/Ej7iKtoI6pfgm2xspcGSHuaF/C2+OwakCwafge59OLW5GoaU=";
	//public static final String  urlSansol="https://sansol.isan.csi.it/la-mia-salute/farmacia-abituale/#/valida-otp"; ARRIVA DA DB
	public static final String  bodyNotif="{\r\n"
			+ "	\"uuid\": \"%1$s\",\r\n"
			+ "    \"payload\": {\r\n"
			+ "		\"id\": \"%2$s\",\r\n"
			+ "        \"user_id\": \"%3$s\",\r\n"
			+ "		\"sms\": {\r\n"
			+ "			\"phone\":\"%4$s\",\r\n"
			+ "			\"content\": \"%5$s\"\r\n"
			+ "		},\r\n"
			+ "		\"trusted\":true\r\n"
			+ "	}\r\n"
			+ "}";


	public static final String  bodyNotif_mail="{\r\n"
			+ "	\"uuid\": \"67e79131-d1b4-46bb-8e7f-e69eaeb807a6\",\r\n"
			+ "    \"payload\": {\r\n"
			+ "		\"id\": \"df5fc799-4ebd-403f-8145-af164fd158a5\",\r\n"
			+ "        \"user_id\": \"RGGTMS69T11h703Y\",\r\n"
			+ "		\"email\": {\r\n"
			+ "			\"to\":\"tommaso.ruggiero@gmail.com\",\r\n"
			+ "			\"subject\":\"Preferenza di notifica: conferma indirizzo email\",\r\n"
			+ "			\"body\":\"Ciao, per validare il tuo indirizzo email copia il codice ABC123 sul sistema di validazione contatti.\",\r\n"
			+ "			\"template_id\":\"default-template.html\"\r\n"
			+ "		},\r\n"
			+ "		\"trusted\":true\r\n"
			+ "	}\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "";

	public String notificatoreEndPoint;
	public String notificatoreToken;

	//Questa proprieta viene popolata da DB potrebbe essere eliminata da *.properties
	public String notificatoreURLSansol;

	public String getNotificatoreEndPoint() {
		return notificatoreEndPoint;
	}

	public void setNotificatoreEndPoint(String notificatoreEndPoint) {
		this.notificatoreEndPoint = notificatoreEndPoint;
	}

	public String getNotificatoreToken() {
		return notificatoreToken;
	}

	public void setNotificatoreToken(String notificatoreToken) {
		this.notificatoreToken = notificatoreToken;
	}

	public String getNotificatoreURLSansol() {
		return notificatoreURLSansol;
	}

	public void setNotificatoreURLSansol(String notificatoreURLSansol) {
		this.notificatoreURLSansol = notificatoreURLSansol;
	}

	public String callNotificatore(String cfCittadino,String uuid, String cfRichiedente, String numTel, String otp, String  urlSansol, String messaggioSms) {
		final String METHOD_NAME = "callNotificatore";
		//messaggioSms DA USARE TO DO

		log.info("Entro in "+METHOD_NAME+" notifichiamo al cf:"+cfCittadino);
		log.debug(METHOD_NAME+ " endpoint notificatore: "+ notificatoreEndPoint);

		log.info(METHOD_NAME+ " token: "+ notificatoreToken);
		try {
			URL url = new URL(notificatoreEndPoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-authentication", notificatoreToken);
			conn.setRequestProperty("Shib-Iride-IdentitaDigitale", cfRichiedente);
			conn.setConnectTimeout(10000);

			String newContent=String.format(messaggioSms,urlSansol);
			String jsonBody = replaceStr(cfCittadino,uuid, numTel, otp, newContent);
			log.info(METHOD_NAME+ " jsonBody: "+ jsonBody);
			OutputStream os = conn.getOutputStream();
			os.write(jsonBody.getBytes(STD_CHARSET));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new IllegalStateException("Errore del servizio di notifica: HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), STD_CHARSET));

			String output;
			log.info(METHOD_NAME+"Output from Server ....");
			while ((output = br.readLine()) != null) {
				log.info(METHOD_NAME+ output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			log.error(METHOD_NAME+ "Errore url endpoint: "+e.getMessage());
			throw new IllegalStateException("Errore url endpoint: "+ e);
		} catch (IOException e) {
			log.error(METHOD_NAME+ "Errore richiamo servizio notificatore:"+e.getMessage());
			throw new IllegalStateException("Errore richiamo servizio notificatore: "+ e);
		}
		log.info("Esco da "+METHOD_NAME);

		//return urlSansol+"?otp="+otp+"&cf="+cfCittadino;ARRIVA DA DB
		return urlSansol;
	}

	private String replaceStr(String cfCittadino,String uuid,  String numTel, String otp, String urlSansol) {
		/*
		 * {
		"uuid": "67e79131-d2b4-46bb-8e7f-e69eaeb807a6",1
	    "payload": {
			"id": "df5fc799-4ebd-403f-8145-af164fd158a5",2
	        "user_id": "RGGTMS69T11H703Y",3
			"sms": {
				"phone":"00393386175911",4
				"content": "Il codice di conferma contatto Ã¨ ABC123"5
			},
			"trusted":true
		}
	}

		 */
		String newBody=String.format(bodyNotif,uuid,uuid,cfCittadino,numTel,urlSansol);
		return newBody;
	}

	public static void main(String[] args) {
		NotificatoreUtil n= new NotificatoreUtil();
		System.out.println(
				n.callNotificatore("RGGTMS69T11H703Y", java.util.UUID.randomUUID().toString(), "RGGTMS69T11h703Y", "00393386175911", "123456","", ""));
	}
}
