/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService(targetNamespace = "http://dmacontatti.csi.it/", name = "ContattiService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ContattiService {

	/**
	 *
	 * 
	 * @param parameters
	 * @return
	 */
	@WebMethod
	public @WebResult(targetNamespace = "http://dmacontatti.csi.it/")
	GeneraOtpResponse generaOtp(
            @WebParam(targetNamespace = "http://dmacontatti.csi.it/") GeneraOtp parameters);
	
	
	@WebMethod
	public @WebResult(targetNamespace = "http://dmacontatti.csi.it/")
	VerificaOtpResponse verificaOtp(
			 @WebParam(targetNamespace = "http://dmacontatti.csi.it/") VerificaOtp parameters);
	
	@WebMethod
	public @WebResult(targetNamespace = "http://dmacontatti.csi.it/")
	GetPreferenzeResponse getPreferenze(
			 @WebParam(targetNamespace = "http://dmacontatti.csi.it/") GetPreferenze parameters);

	@WebMethod
	public @WebResult(targetNamespace = "http://dmacontatti.csi.it/")
	PutTermsPreferenzeContattiResponse putTermsPreferenzeContatti(
			 @WebParam(targetNamespace = "http://dmacontatti.csi.it/") PutTermsPreferenzeContatti parameters);
	
	
}
