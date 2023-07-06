/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.dmafarma.ElencoErrori;
import it.csi.dma.dmafarma.ElencoRicetteFarmaciaResponse;
import it.csi.dma.dmafarma.Errore;
import it.csi.dma.dmafarma.GetDelegantiFarmaciaResponse;
import it.csi.dma.farmab.integration.dao.FarmabTLogDao;
import it.csi.dma.farmab.integration.dao.dto.LErroreDto;
import it.csi.dma.farmab.integration.dao.dto.LMessaggiDto;
import it.csi.dma.farmab.util.Constants;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class TraceFarmacieOutBodyInterceptor extends AbstractSoapInterceptor{
	private SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);


	@Autowired
	FarmabTLogDao farmabTLogDao;

	public TraceFarmacieOutBodyInterceptor() {
        super(Phase.PRE_MARSHAL);
        getBefore().add(SAAJOutInterceptor.class.getName());
    }

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		SOAPMessage doc = message.getContent(SOAPMessage.class);
		String wso2_id = (String) message.getExchange().get(Constants.LOGGING_WSO2ID);
		//id da usare nell'update della l_messaggi
		Long idLMessaggi = (Long) message.getExchange().get(Constants.LOGGING_KEY_L_MESSAGGI);
		String tipoErrore=Constants.LOG_BLOCCANTE;

        if (doc == null) {
            saajOut.handleMessage(message);
            doc = message.getContent(SOAPMessage.class);
            // it is executed
            MessageContentsList outObjects = MessageContentsList.getContentsList(message);
            if(outObjects!=null) {
            	if (outObjects.get(0)!=null && outObjects.get(0) instanceof ElencoRicetteFarmaciaResponse) {
            		//Gestione ElencoRicetteFarmaciaResponse
            		ElencoRicetteFarmaciaResponse elencoRicetteFarmaciaResponse=(ElencoRicetteFarmaciaResponse)outObjects.get(0);
            		if(!elencoRicetteFarmaciaResponse.getCodEsito().trim().isEmpty() && Constants.SUCCESS_CODE_0000.equalsIgnoreCase(elencoRicetteFarmaciaResponse.getCodEsito())) {
            			//TODO
            			tipoErrore=Constants.LOG_SUCCESSO;
            		}
            		//abbiamo deciso che l'update lo facciamo per tutti in soapOutInterceptor updateLMessaggi(idLMessaggi, elencoRicetteFarmaciaResponse.getCodEsito());
            		// a prescindere da cod esito salviamo gli errori
            		if (elencoRicetteFarmaciaResponse.getElencoErrori()!=null) {
            			salvaErrori(wso2_id,elencoRicetteFarmaciaResponse.getElencoErrori(),tipoErrore);
            		}
            	} else if (outObjects!=null && outObjects.get(0) instanceof GetDelegantiFarmaciaResponse) {
            		//Gestione GetDelegantiFarmaciaResponse
            		GetDelegantiFarmaciaResponse getDelegantiFarmaciaResponse=(GetDelegantiFarmaciaResponse)outObjects.get(0);

            		if(!getDelegantiFarmaciaResponse.getCodEsito().trim().isEmpty() && Constants.SUCCESS_CODE_0000.equalsIgnoreCase(getDelegantiFarmaciaResponse.getCodEsito())) {
            			//TODO
            			tipoErrore=Constants.LOG_SUCCESSO;
            		}
            		//abbiamo deciso che l'update lo facciamo per tutti in soapOutInterceptor updateLMessaggi(idLMessaggi, getDelegantiFarmaciaResponse.getCodEsito());
            		// a prescindere da cod esito salviamo gli errori
            		if (getDelegantiFarmaciaResponse.getElencoErrori()!=null) {
            			salvaErrori(wso2_id,getDelegantiFarmaciaResponse.getElencoErrori(),tipoErrore);
            		}
            	}

            }


        }

	}
	public void salvaErrori(String wso2_id,ElencoErrori elencoErrori,String tipoErrore) {
		log.info("TraceFarmacieOutBodyInterceptor::salvaErrori");
		if (elencoErrori!=null) {
			List<Errore> errori=elencoErrori.getErrore();
			List<LErroreDto> lerrori=new ArrayList<LErroreDto>(errori.size());
			for (Errore errore : errori) {
				log.debug("creo LErroreDto errore per:"+errore.getCodErrore());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodErrore(), errore.getDescErrore(), tipoErrore,null));
			}
			farmabTLogDao.insertLErrori(lerrori);
		}
	}

	public void updateLMessaggi(Long idLMessaggi, String codEsito) {
		log.info("TraceFarmacieOutBodyInterceptor::updateLMessaggi");
		LMessaggiDto messaggio= new LMessaggiDto();
		messaggio.setIdXml(idLMessaggi);
		messaggio.setCodEsitoRispostaEervizio(codEsito);
		farmabTLogDao.updateLMessaggi(messaggio);
	}

}
