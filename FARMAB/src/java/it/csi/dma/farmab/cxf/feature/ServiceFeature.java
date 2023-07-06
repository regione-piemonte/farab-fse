/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.springframework.context.ApplicationContext;

import it.csi.dma.farmab.SpringApplicationContextProvider;
import it.csi.dma.farmab.cxf.interceptor.SoapActionInInterceptorUuidNumTransazione;
import it.csi.dma.farmab.cxf.interceptor.SoapActionOutInterceptor;
import it.csi.dma.farmab.cxf.interceptor.TraceOutBodyInterceptor;

public class ServiceFeature extends AbstractFeature {
	private SoapActionInInterceptorUuidNumTransazione  soapInInterceptor;
	private SoapActionOutInterceptor soapOutInterceptor;
	private TraceOutBodyInterceptor traceOutBodyInterceptor;

	public ServiceFeature() {
		ApplicationContext appContext = SpringApplicationContextProvider.getApplicationContext();
		soapInInterceptor = appContext.getBean(SoapActionInInterceptorUuidNumTransazione.class);
		soapOutInterceptor = appContext.getBean(SoapActionOutInterceptor.class);
		traceOutBodyInterceptor= appContext.getBean(TraceOutBodyInterceptor.class);
	}

	@Override
	protected void initializeProvider(InterceptorProvider provider, Bus bus) {
		provider.getInInterceptors().add(soapInInterceptor);
		provider.getOutInterceptors().add(soapOutInterceptor);
		provider.getOutInterceptors().add(traceOutBodyInterceptor);
	}
}

