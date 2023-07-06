/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.cxf.interceptor;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.springframework.context.ApplicationContext;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import it.csi.dma.farmab.SpringApplicationContextProvider;

public class ServiceFeature  extends AbstractFeature {

	private IrideCacheUserPaswordInterceptor irideCacheUserPaswordInterceptor;
	private SoapActionInInterceptorUuidCodSessione soapActionInInterceptorUuidCodSessione;
	private SoapActionOutInterceptor soapOutInterceptor;
	private TraceFarmacieOutBodyInterceptor traceFarmacieOutBodyInterceptor;

	private LoggingInInterceptor loggingIn;
	private LoggingOutInterceptor loggingOut;

	public ServiceFeature() {
		ApplicationContext appContext = SpringApplicationContextProvider.getApplicationContext();
		irideCacheUserPaswordInterceptor = appContext.getBean(IrideCacheUserPaswordInterceptor.class);
		soapActionInInterceptorUuidCodSessione = appContext.getBean(SoapActionInInterceptorUuidCodSessione.class);
		soapOutInterceptor = appContext.getBean(SoapActionOutInterceptor.class);
		traceFarmacieOutBodyInterceptor= appContext.getBean(TraceFarmacieOutBodyInterceptor.class);

		loggingIn = appContext.getBean(LoggingInInterceptor.class);
		loggingOut = appContext.getBean(LoggingOutInterceptor.class);
	}

	@Override
	protected void initializeProvider(InterceptorProvider provider, Bus bus) {
		// IN
		provider.getInInterceptors().add(soapActionInInterceptorUuidCodSessione);
		provider.getInInterceptors().add(irideCacheUserPaswordInterceptor);
		provider.getInInterceptors().add(loggingIn);
		//OUT
		provider.getOutInterceptors().add(soapOutInterceptor);
		provider.getOutInterceptors().add(traceFarmacieOutBodyInterceptor);
		provider.getOutInterceptors().add(loggingOut);
	}
}
