/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.consensoini.dmacc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

/**
 * This class was generated by Apache CXF 2.7.14
 * 2022-01-13T11:17:58.654+01:00
 * Generated source version: 2.7.14
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "CCConsensoINIExtServicePortType")
@XmlSeeAlso({ObjectFactory.class, it.csi.consensoini.dma.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CCConsensoINIExtServicePortType {

    @WebMethod(action = "http://dmacc.csi.it/statoConsensiExt")
    @Action(input = "http://dmacc.csi.it/statoConsensiExt", output = "http://dmacc.csi.it/ConsensoINIExtService/statoConsensiResponse")
    @WebResult(name = "statoConsensiResponse", targetNamespace = "http://dmacc.csi.it/", partName = "statoConsensiResponse")
    public StatoConsensiResponse statoConsensi(
        @WebParam(partName = "statoConsensi", name = "statoConsensi", targetNamespace = "http://dmacc.csi.it/")
        StatoConsensiExtRequeste statoConsensi
    );

    @WebMethod(action = "http://dmacc.csi.it/comunicazioneConsensiExt")
    @Action(input = "http://dmacc.csi.it/comunicazioneConsensiExt", output = "http://dmacc.csi.it/ConsensoINIExtService/comunicazioneConsensiResponse")
    @WebResult(name = "comunicazioneConsensiResponse", targetNamespace = "http://dmacc.csi.it/", partName = "comunicazioneConsensiResponse")
    public ComunicazioneConsensiResponse comunicazioneConsensi(
        @WebParam(partName = "comunicazioneConsensi", name = "comunicazioneConsensi", targetNamespace = "http://dmacc.csi.it/")
        ComunicazioneConsensiExtRequeste comunicazioneConsensi
    );

    @WebMethod(action = "http://dmacc.csi.it/recuperoInformativaExt")
    @Action(input = "http://dmacc.csi.it/recuperoInformativaExt", output = "http://dmacc.csi.it/ConsensoINIExtService/recuperoInformativaResponse")
    @WebResult(name = "recuperoInformativaResponse", targetNamespace = "http://dmacc.csi.it/", partName = "recuperoInformativaResponse")
    public RecuperoInformativaResponse recuperoInformativa(
        @WebParam(partName = "recuperoInformativa", name = "recuperoInformativa", targetNamespace = "http://dmacc.csi.it/")
        RecuperoInformativaExtRequeste recuperoInformativa
    );
}
