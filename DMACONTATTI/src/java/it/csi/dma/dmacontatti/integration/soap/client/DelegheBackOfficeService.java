/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2021-11-08T15:35:07.261+01:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://deleghebe.csi.it/", name = "DelegheBackOfficeService")
public interface DelegheBackOfficeService {

    @WebMethod
    @RequestWrapper(localName = "ricercaServiziService", targetNamespace = "http://deleghebe.csi.it/", className = "it.csi.deleghebe.RicercaServiziService")
    @ResponseWrapper(localName = "ricercaServiziServiceResponse", targetNamespace = "http://deleghebe.csi.it/", className = "it.csi.deleghebe.RicercaServiziServiceResponse")
    @WebResult(name = "ricercaServiziResponse", targetNamespace = "http://deleghebe.csi.it/")
    public RicercaServiziResponse ricercaServiziService(

        @WebParam(name = "getServizi", targetNamespace = "http://deleghebe.csi.it/")
        RicercaServizi getServizi
    );

    
}
