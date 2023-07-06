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
import org.springframework.stereotype.Component;

import it.csi.dma.farmab.integration.dao.FarmabTLogDao;
import it.csi.dma.farmab.integration.dao.dto.LErroreDto;
import it.csi.dma.farmab.interfacews.msg.farab.AutorizzaFarmaciaOccasionaleResponse;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDeviceConOtpResponse;
import it.csi.dma.farmab.interfacews.msg.farab.CertificaDeviceResponse;
import it.csi.dma.farmab.interfacews.msg.farab.GetFarmaciaOccasionaleResponse;
import it.csi.dma.farmab.interfacews.msg.getdevicecertificato.GetDeviceCertificatoResponse;
import it.csi.dma.farmab.interfacews.msg.getelencofarmacieabituali.GetElencoFarmacieAbitualiResponse;
import it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice.GetGeneraOtpDeviceResponse;
import it.csi.dma.farmab.interfacews.msg.setdisassociadevice.SetDisassociaDeviceResponse;
import it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale.SetFarmaciaAbitualeResponse;
import it.csi.dma.farmab.util.Constants;

@Component
public class TraceOutBodyInterceptor extends AbstractSoapInterceptor{
	private SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	FarmabTLogDao farmabTLogDao;


	public TraceOutBodyInterceptor() {
        super(Phase.PRE_MARSHAL);
        getBefore().add(SAAJOutInterceptor.class.getName());
    }


	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		//String tipoErrore=Constants.LOG_BLOCCANTE;
		SOAPMessage doc = message.getContent(SOAPMessage.class);
		String wso2_id = (String) message.getExchange().get(Constants.LOGGING_WSO2ID);
		String tipoErrore=Constants.LOG_BLOCCANTE;

        if (doc == null) {
            saajOut.handleMessage(message);
            doc = message.getContent(SOAPMessage.class);
            // it is executed
            MessageContentsList outObjects = MessageContentsList.getContentsList(message);
            outObjects.isEmpty();
            Object outObject = outObjects.get(0);
            //outObject.toString();
            if(outObject!=null && outObjects.get(0)!=null) {
            	//SetFarmaciaAbitualeResponse
            	if (outObjects.get(0) instanceof SetFarmaciaAbitualeResponse &&((SetFarmaciaAbitualeResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		List<it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale.Errore> le=((SetFarmaciaAbitualeResponse)outObjects.get(0)).getElencoErrori().getErrore();
            		salvaErrori(convert(wso2_id,le,tipoErrore));
            	}
            	//SetDisassociaDeviceResponse
            	if (outObjects.get(0) instanceof SetDisassociaDeviceResponse &&((SetDisassociaDeviceResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((SetDisassociaDeviceResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//GetGeneraOtpDeviceResponse
            	if (outObjects.get(0) instanceof GetGeneraOtpDeviceResponse &&((GetGeneraOtpDeviceResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((GetGeneraOtpDeviceResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//GetElencoFarmacieAbitualiResponse
            	if (outObjects.get(0) instanceof GetElencoFarmacieAbitualiResponse &&((GetElencoFarmacieAbitualiResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((GetElencoFarmacieAbitualiResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//GetDeviceCertificatoResponse
            	if (outObjects.get(0) instanceof GetDeviceCertificatoResponse &&((GetDeviceCertificatoResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((GetDeviceCertificatoResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}

            	/**
            	 * farab
            	 */
            	// CertificaDeviceResponse
            	if (outObjects.get(0) instanceof CertificaDeviceResponse &&((CertificaDeviceResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((CertificaDeviceResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//CertificaDeviceConOtpResponse
            	if (outObjects.get(0) instanceof CertificaDeviceConOtpResponse &&((CertificaDeviceConOtpResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((CertificaDeviceConOtpResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//GetFarmaciaOccasionaleResponse
            	if (outObjects.get(0) instanceof GetFarmaciaOccasionaleResponse &&((GetFarmaciaOccasionaleResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((GetFarmaciaOccasionaleResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            	//AutorizzaFarmaciaOccasionaleResponse
            	if (outObjects.get(0) instanceof AutorizzaFarmaciaOccasionaleResponse &&((AutorizzaFarmaciaOccasionaleResponse)outObjects.get(0)).getElencoErrori()!=null) {
            		salvaErrori(convert(wso2_id,((AutorizzaFarmaciaOccasionaleResponse)outObjects.get(0)).getElencoErrori(),tipoErrore));
            	}
            }


        }

	}

	protected void salvaErrori(List<LErroreDto> lerrori) {
    	log.info("TraceOutBodyInterceptor::salvaErrori");
    	//TODO nel caso bisogni eliminare un errore da non riportare all'utente agire qui!!!
    	if(lerrori!=null) {
    		farmabTLogDao.insertLErrori(lerrori);
    	}

    }

    List<LErroreDto> convert (String wso2_id,List<it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale.Errore> le,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(le!=null && le.size()>0) {
    		lerrori=new ArrayList<LErroreDto>(le.size());
    		for (it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale.Errore errore : le) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }

    List<LErroreDto> convert (String wso2_id,it.csi.dma.farmab.interfacews.msg.setdisassociadevice.ElencoErroriType ee,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(ee!=null && ee.getErrore()!=null && ee.getErrore().size()>0) {
    		lerrori=new ArrayList<LErroreDto>(ee.getErrore().size());
    		for (it.csi.dma.farmab.interfacews.msg.setdisassociadevice.Errore errore : ee.getErrore()) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }

    List<LErroreDto> convert (String wso2_id,it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice.ElencoErroriType ee,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(ee!=null && ee.getErrore()!=null && ee.getErrore().size()>0) {
    		lerrori=new ArrayList<LErroreDto>(ee.getErrore().size());
    		for (it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice.Errore errore : ee.getErrore()) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }

    List<LErroreDto> convert (String wso2_id,it.csi.dma.farmab.interfacews.msg.getelencofarmacieabituali.ElencoErroriType ee,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(ee!=null && ee.getErrore()!=null && ee.getErrore().size()>0) {
    		lerrori=new ArrayList<LErroreDto>(ee.getErrore().size());
    		for (it.csi.dma.farmab.interfacews.msg.getelencofarmacieabituali.Errore errore : ee.getErrore()) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }

    List<LErroreDto> convert (String wso2_id,it.csi.dma.farmab.interfacews.msg.getdevicecertificato.ElencoErroriType ee,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(ee!=null && ee.getErrore()!=null && ee.getErrore().size()>0) {
    		lerrori=new ArrayList<LErroreDto>(ee.getErrore().size());
    		for (it.csi.dma.farmab.interfacews.msg.getdevicecertificato.Errore errore : ee.getErrore()) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }
    //farab
    List<LErroreDto> convert (String wso2_id,it.csi.dma.farmab.interfacews.msg.farab.ElencoErroriType ee,String tipoErrore){
    	List<LErroreDto> lerrori=null;
    	if(ee!=null && ee.getErrore()!=null && ee.getErrore().size()>0) {
    		lerrori=new ArrayList<LErroreDto>(ee.getErrore().size());
    		for (it.csi.dma.farmab.interfacews.msg.farab.Errore errore : ee.getErrore()) {
				log.debug("creo LErroreDto errore per:"+errore.getCodice());
				lerrori.add(new LErroreDto(wso2_id, errore.getCodice(), errore.getDescrizione(), tipoErrore,null));
			}
    	}
    	return lerrori;
    }
}
