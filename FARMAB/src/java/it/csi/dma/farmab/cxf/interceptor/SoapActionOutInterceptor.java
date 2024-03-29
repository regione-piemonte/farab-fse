/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.farmab.domain.DmaccLXmlMessaggi;
import it.csi.dma.farmab.integration.dao.FarmabTLogDao;
import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;
import it.csi.dma.farmab.util.Constants;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SoapActionOutInterceptor extends AbstractPhaseInterceptor<Message> {
	@Autowired
	FarmabTLogDao  logDao;
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	public SoapActionOutInterceptor() {
		super(Phase.PRE_STREAM);
		//addBefore(SoapPreProtocolOutInterceptor.class.getName());

	}



	@Override
    public void handleMessage(Message message) throws Fault {
        OutputStream out = message.getContent(OutputStream.class);
        //Long id = (Long)message.getExchange().get(LoggingMessage.ID_KEY);
        final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(out);
        message.setContent(OutputStream.class, newOut);
        newOut.registerCallback(new LoggingCallback());

    }

    public class LoggingCallback implements CachedOutputStreamCallback {
        public void onFlush(CachedOutputStream cos) {
        }

        public void onClose(CachedOutputStream cos) {
            try {
                StringBuilder builder = new StringBuilder();
                cos.writeCacheTo(builder);
                // here comes my xml:
                String soapXml = builder.toString();

                Message message = PhaseInterceptorChain.getCurrentMessage();
                Long id = (Long)message.getExchange().get(Constants.LOGGING_KEY_L_XML_MESSAGGI);
                Long idL = (Long)message.getExchange().get(Constants.LOGGING_KEY_L_MESSAGGI);

                String wso2_id = (String) message.getExchange().get(Constants.LOGGING_WSO2ID);
                log.info("SoapActionOutInterceptor::onClose id ricavato da message:"+id);
                log.info("********************************************************");
                log.info("SoapActionOutInterceptor::onClose wso2_id: "+wso2_id);
                log.info("********************************************************");
                updateLMessaggi(idL, getEsito(soapXml));

                DmaccLXmlMessaggi messaggio = new DmaccLXmlMessaggi();
                messaggio.setXmlOut(new String( soapXml.getBytes()));
                logDao.updatetLXmlMessaggi(messaggio, id);



            } catch (Exception e) {
            	log.error("[SoapActionOutInterceptor::LoggingCallback] ERROR", e);
            }
        }
    }

    private String getEsito(String response){
    	String responseSenzaNamespace1 = response.replaceAll("<\\/[a-z0-9]*:", "<\\/");
		String responseSenzaNamespace = responseSenzaNamespace1.replaceAll("<[a-z0-9]*:", "<");
		String esito = StringUtils.substringBetween(responseSenzaNamespace, "<esito>", "</esito>");
		if(esito==null) {
			//cerco codEsito e lo trasformo in SUCCESSO se vale 0000 altrimenti FALLIMENTO
			esito = StringUtils.substringBetween(responseSenzaNamespace, "<codEsito>", "</codEsito>");
			if ("0000".equalsIgnoreCase(esito)) {
				esito="SUCCESSO";
			} else if (esito!=null) {
				esito="FALLIMENTO";
			}
			//nel caso non si riesca a ricavare esito lo riportiamo a null per agevolare il troubleshouting
		}
		return esito;
    }

	public void updateLMessaggi(Long idLMessaggi, String codEsito) {
		log.info("SoapActionOutInterceptor::updateLMessaggi");
		LMessaggiDto messaggio= new LMessaggiDto();
		messaggio.setIdXml(idLMessaggi);
		messaggio.setCodEsitoRispostaEervizio(codEsito);
		logDao.updateLMessaggi(messaggio);
	}



}
