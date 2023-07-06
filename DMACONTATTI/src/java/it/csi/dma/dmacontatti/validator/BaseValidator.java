/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.validator;

import it.csi.dma.dmacontatti.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.CredenzialiServiziLowDto;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.business.log.LogGeneralDao;
import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.util.Utils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BaseValidator {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business");

	public static final String CODICE_LOG_START = "CC_LOG_003";
	public static final String LOG_RISPOSTA_SERVIZIO = "CC_LOG_002";

	//Constants errori
	public static final String CREDENZIALI_NON_VALIDE = "CC_ER_187";
	public static final String CAMPO_OBBLIGATORIO = "CC_ER_001";
	public static final String CAMPO_NON_CORRETTO = "CC_ER_002";
	public static final String ERRORE_INTERNO = "CC_ER_999";
	public static final String PAZIENTE_NON_TROVATO = "CC_ER_155";
	public static final String OTP_NON_VALIDO = "CC_ER_208";
	public static final String CONTATTO_OBBLIGATORIO = "CC_ER_211";
	public static final String CANALE_O_CONTATTO_OBBLIGATORIO = "CC_ER_212";
	public static final String SERVIZIO_E_CANALE_ATTIVO_OBBLIGATORI = "CC_ER_213";
	public static final String ERRORE_INTERNO_REST = "CC_ER_209";
	public static final String ERRORE_INTERNO_SOAP = "CC_ER_210";


	private CredenzialiServiziLowDao credenzialiServiziLowDao;
	public LogGeneralDao logGeneralDao;

	public List<Errore> validateCredenziali(WebServiceContext wsContext, List<Errore> errori, String codiceServizio,
											String className) {

		String methodName = "validateCredenziali";

		String username = null;
		String passwordDigest = null;
		String created = null;
		String nonce = null;
		try {
			MessageContext mctx = wsContext.getMessageContext();

			Message message = ((WrappedMessageContext) mctx).getWrappedMessage();

			List<Header> headers = CastUtils.cast((List<?>) message.get(Header.HEADER_LIST));
			Header header = Utils.getFirstRecord(headers);

			CredenzialiServiziLowDto credenzialiServiziDto = new CredenzialiServiziLowDto();

			if (errori == null) {
				errori = new ArrayList<Errore>();
			}

			if (header != null) {
				Element e = (Element) header.getObject();
				NodeList usernameToken = Utils.getUsernameTokenFromHeader(e.getChildNodes());
				username = Utils.getValueFromHeader(usernameToken, "Username");
				passwordDigest = Utils.getValueFromHeader(usernameToken, "Password");
				created = Utils.getValueFromHeader(usernameToken, "Created");
				nonce = Utils.getValueFromHeader(usernameToken, "Nonce");
			} else {
				errori.add(logGeneralDao.getErroreCatalogo(CREDENZIALI_NON_VALIDE, codiceServizio));
				return errori;
			}

			credenzialiServiziDto.setCodiceServizio(codiceServizio);
			credenzialiServiziDto.setUsername(username);
//			credenzialiServiziDto.setPassword(password);

			List<CredenzialiServiziLowDto> listaCredenziali = credenzialiServiziLowDao
					.findByCodiceServizioUser(credenzialiServiziDto);

			boolean isValidAccess = false;

			for (CredenzialiServiziLowDto dto : listaCredenziali) {

				if (verify(nonce, created, dto.getPassword(), passwordDigest, username, dto.getUsername())) {
					isValidAccess = true;
					return errori;
				}

//				if (dto.getUsername().equals(username) && dto.getPassword().equals(password)) {
//					isValidAccess = true;
//					return errori;
//				}
			}
			if (!isValidAccess) {
				errori.add(logGeneralDao.getErroreCatalogo(CREDENZIALI_NON_VALIDE, codiceServizio));
			}

		} catch (Exception e) {
			log.error("Errore bloccante recupero credenziali: ", e);
			errori.add(logGeneralDao.getErroreCatalogo(CREDENZIALI_NON_VALIDE, codiceServizio));
		}

		return errori;
	}

	private boolean verify(String nonce, String created,
							  String pwd, String passwordDigest, String usernameHeader, String username) {
		try {
			byte[] nonceBytes = Base64.getDecoder().decode(nonce);
			byte[] createdBytes = created.getBytes("UTF-8");
			byte[] passwordBytes = pwd.getBytes("UTF-8");
			ByteArrayOutputStream outputStream =
					new ByteArrayOutputStream( );
			outputStream.write(nonceBytes);
			outputStream.write(createdBytes);
			outputStream.write(passwordBytes);
			byte[] concatenatedBytes = outputStream.toByteArray();
			MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
			digest.update(concatenatedBytes, 0, concatenatedBytes.length);
			byte[] digestBytes = digest.digest();
			String digestString = Base64.getEncoder().encodeToString(digestBytes);

 			if (digestString.equals(passwordDigest) && usernameHeader.equalsIgnoreCase(username)) {
				return true;
			}
		} catch (Exception e) {
			log.error("Errore durante la verifica della passwordDigest: ", e);
		}
		return false;
	}

	public CredenzialiServiziLowDao getCredenzialiServiziLowDao() {
		return credenzialiServiziLowDao;
	}

	public void setCredenzialiServiziLowDao(CredenzialiServiziLowDao credenzialiServiziLowDao) {
		this.credenzialiServiziLowDao = credenzialiServiziLowDao;
	}

	public LogGeneralDao getLogGeneralDao() {
		return logGeneralDao;
	}

	public void setLogGeneralDao(LogGeneralDao logGeneralDao) {
		this.logGeneralDao = logGeneralDao;
	}
}
