/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getdevicecertificato;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getDeviceCertificatoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getDeviceCertificatoResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="statoCertificazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="datiCertificazione" type="{http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/}certificazioneType" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDeviceCertificatoResponse", propOrder = {
    "statoCertificazione",
    "datiCertificazione"
})
public class GetDeviceCertificatoResponse
    extends ServiceResponse
{

    @XmlElement(namespace = "http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/")
    protected String statoCertificazione;
    @XmlElement(namespace = "http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/")
    protected CertificazioneType datiCertificazione;

    /**
     * Recupera il valore della proprietÃ  statoCertificazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoCertificazione() {
        return statoCertificazione;
    }

    /**
     * Imposta il valore della proprietÃ  statoCertificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoCertificazione(String value) {
        this.statoCertificazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  datiCertificazione.
     * 
     * @return
     *     possible object is
     *     {@link CertificazioneType }
     *     
     */
    public CertificazioneType getDatiCertificazione() {
        return datiCertificazione;
    }

    /**
     * Imposta il valore della proprietÃ  datiCertificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificazioneType }
     *     
     */
    public void setDatiCertificazione(CertificazioneType value) {
        this.datiCertificazione = value;
    }

}
