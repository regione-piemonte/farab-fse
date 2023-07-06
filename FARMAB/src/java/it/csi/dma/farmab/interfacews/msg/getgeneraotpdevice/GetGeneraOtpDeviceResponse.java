/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getGeneraOtpDeviceResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getGeneraOtpDeviceResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="codiceOtp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getGeneraOtpDeviceResponse", propOrder = {
    "codiceOtp",
    "link",
    "dataInizioValidita",
    "dataFineValidita"
})
public class GetGeneraOtpDeviceResponse
    extends ServiceResponse
{

    @XmlElement(namespace = "http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/")
    protected String codiceOtp;
    @XmlElement(namespace = "http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/")
    protected String link;
    @XmlElement(namespace = "http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/")
    protected String dataInizioValidita;
    @XmlElement(namespace = "http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/")
    protected String dataFineValidita;

    /**
     * Recupera il valore della proprietÃ  codiceOtp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceOtp() {
        return codiceOtp;
    }

    /**
     * Imposta il valore della proprietÃ  codiceOtp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceOtp(String value) {
        this.codiceOtp = value;
    }

    /**
     * Recupera il valore della proprietÃ  link.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Imposta il valore della proprietÃ  link.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietÃ  dataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioValidita(String value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della proprietÃ  dataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineValidita(String value) {
        this.dataFineValidita = value;
    }

}
