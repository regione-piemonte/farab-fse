/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.IF;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.csi.dma.farmab.interfacews.msg.getdevicecertificato.GetDeviceCertificatoRequest;
import it.csi.dma.farmab.interfacews.msg.getdevicecertificato.GetDeviceCertificatoResponse;

@WebService(targetNamespace = "http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/", name = "GetDeviceCertificatoService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GetDeviceCertificatoService {

	@WebMethod
	public @WebResult GetDeviceCertificatoResponse getDeviceCertificato(@WebParam GetDeviceCertificatoRequest parameters);	
	
}
