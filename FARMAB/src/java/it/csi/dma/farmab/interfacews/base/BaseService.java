/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;

import it.csi.dma.farmab.util.Constants;

public class BaseService {

	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Resource
	protected WebServiceContext wsContext;

	public WebServiceContext getWsContext() {
		return wsContext;
	}
	
	protected String getIpAddress(){
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest request = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
		String remoteClientAddr = request.getRemoteAddr();
		return remoteClientAddr;
	}
	
	private static Message getMessage(WebServiceContext wsContext) {
		MessageContext mContext = wsContext.getMessageContext();

		WrappedMessageContext wmc = (WrappedMessageContext) mContext;
		Message msg = wmc.getWrappedMessage();
		return msg;
	}

	public static Exchange getExchange(WebServiceContext wsContext) {
		try {
			Message msg = getMessage(wsContext);

			Exchange exc = msg.getExchange();
			return exc;
		} catch (Exception e) {
			return null;
		}
	}		


//	public Errore getErrore(String codiceErrore) {
//
//		Errore errore = new Errore();
//		errore.setCodice(codiceErrore);
//
//		DmaclDCatalogoLogDto inputDto = new DmaclDCatalogoLogDto();
//		inputDto.setCodice(codiceErrore);
//		DmaclDCatalogoLogDto resultDto = null;
//		try {
//			resultDto = dmaclDCatalogoLogDao.find(inputDto).get(0);
//		} catch (DmaclDCatalogoLogDaoException e) {
//			log.error("[CommonFacade::getErrore] errore registrazione log", e);
//		}
//
//		errore.setDescrizione(resultDto.getDescrizioneLog()); // FIXME: descrizioneLog o descrizioneErrore ?
//
//		return errore;
//	}
//
//	public ServiceResponse fatalError(ServiceResponse response, String codiceErrore) {
//		ArrayList<Errore> elencoErrori = new ArrayList<Errore>();
//		elencoErrori.add(getErrore(codiceErrore));
//
//		response.setEsito(RisultatoCodice.FALLIMENTO);
//		response.setErrore(elencoErrori);
//
//		return response;
//	}
}
