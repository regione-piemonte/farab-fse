/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.setfarmaciaabituale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per farmacia complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="farmacia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioVal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFineVal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codFarmacia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrFarmacia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoFarmacia" type="{http://setfarmaciaabituale.msg.interfacews.farmab.dma.csi.it/}indirizzoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "farmacia", propOrder = {
    "id",
    "dataInizioVal",
    "dataFineVal",
    "codFarmacia",
    "descrFarmacia",
    "indirizzoFarmacia"
})
public class Farmacia {

    @XmlElement(required = true)
    protected String id;
    protected String dataInizioVal;
    protected String dataFineVal;
    protected String codFarmacia;
    protected String descrFarmacia;
    @XmlElement(nillable = true)
    protected IndirizzoType indirizzoFarmacia;

    /**
     * Recupera il valore della proprietÃ  id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietÃ  id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataInizioVal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioVal() {
        return dataInizioVal;
    }

    /**
     * Imposta il valore della proprietÃ  dataInizioVal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioVal(String value) {
        this.dataInizioVal = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataFineVal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineVal() {
        return dataFineVal;
    }

    /**
     * Imposta il valore della proprietÃ  dataFineVal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineVal(String value) {
        this.dataFineVal = value;
    }

    /**
     * Recupera il valore della proprietÃ  codFarmacia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFarmacia() {
        return codFarmacia;
    }

    /**
     * Imposta il valore della proprietÃ  codFarmacia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFarmacia(String value) {
        this.codFarmacia = value;
    }

    /**
     * Recupera il valore della proprietÃ  descrFarmacia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrFarmacia() {
        return descrFarmacia;
    }

    /**
     * Imposta il valore della proprietÃ  descrFarmacia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrFarmacia(String value) {
        this.descrFarmacia = value;
    }

    /**
     * Recupera il valore della proprietÃ  indirizzoFarmacia.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoType }
     *     
     */
    public IndirizzoType getIndirizzoFarmacia() {
        return indirizzoFarmacia;
    }

    /**
     * Imposta il valore della proprietÃ  indirizzoFarmacia.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoType }
     *     
     */
    public void setIndirizzoFarmacia(IndirizzoType value) {
        this.indirizzoFarmacia = value;
    }

}
