/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmacontatti.integration.soap.client;

import com.sun.xml.txw2.annotation.XmlNamespace;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for servizio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="servizio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://deleghebe.csi.it/}codifica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="serId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneEstesa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="arruolabile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="delegabile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="notificabile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="obbligatorioArruolamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="minore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="validitaServizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroGiorniDelegabili" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="codSerPadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fraseDebole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fraseForte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "servizio", propOrder = {
    "serId",
    "descrizioneEstesa",
    "arruolabile",
    "delegabile",
    "notificabile",
    "obbligatorioArruolamento",
    "minore",
    "url",
    "validitaServizio",
    "numeroGiorniDelegabili",
    "dataInizioValidita",
    "dataFineValidita",
    "codSerPadre",
    "fraseDebole",
    "fraseForte"
})
public class Servizio
    extends Codifica
{

    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Integer serId;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String descrizioneEstesa;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Boolean arruolabile;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Boolean delegabile;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Boolean notificabile;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Boolean obbligatorioArruolamento;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Boolean minore;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String url;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String validitaServizio;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected Integer numeroGiorniDelegabili;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String codSerPadre;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String fraseDebole;
    @XmlElement(namespace="http://deleghebe.csi.it/")
    protected String fraseForte;

    /**
     * Gets the value of the serId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSerId() {
        return serId;
    }

    /**
     * Sets the value of the serId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSerId(Integer value) {
        this.serId = value;
    }

    /**
     * Gets the value of the descrizioneEstesa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    /**
     * Sets the value of the descrizioneEstesa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEstesa(String value) {
        this.descrizioneEstesa = value;
    }

    /**
     * Gets the value of the arruolabile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isArruolabile() {
        return arruolabile;
    }

    /**
     * Sets the value of the arruolabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setArruolabile(Boolean value) {
        this.arruolabile = value;
    }

    /**
     * Gets the value of the delegabile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDelegabile() {
        return delegabile;
    }

    /**
     * Sets the value of the delegabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDelegabile(Boolean value) {
        this.delegabile = value;
    }

    /**
     * Gets the value of the notificabile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNotificabile() {
        return notificabile;
    }

    /**
     * Sets the value of the notificabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotificabile(Boolean value) {
        this.notificabile = value;
    }

    /**
     * Gets the value of the obbligatorioArruolamento property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isObbligatorioArruolamento() {
        return obbligatorioArruolamento;
    }

    /**
     * Sets the value of the obbligatorioArruolamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setObbligatorioArruolamento(Boolean value) {
        this.obbligatorioArruolamento = value;
    }

    /**
     * Gets the value of the minore property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMinore() {
        return minore;
    }

    /**
     * Sets the value of the minore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMinore(Boolean value) {
        this.minore = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the validitaServizio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValiditaServizio() {
        return validitaServizio;
    }

    /**
     * Sets the value of the validitaServizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValiditaServizio(String value) {
        this.validitaServizio = value;
    }

    /**
     * Gets the value of the numeroGiorniDelegabili property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroGiorniDelegabili() {
        return numeroGiorniDelegabili;
    }

    /**
     * Sets the value of the numeroGiorniDelegabili property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroGiorniDelegabili(Integer value) {
        this.numeroGiorniDelegabili = value;
    }

    /**
     * Gets the value of the dataInizioValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets the value of the dataInizioValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Gets the value of the codSerPadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSerPadre() {
        return codSerPadre;
    }

    /**
     * Sets the value of the codSerPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSerPadre(String value) {
        this.codSerPadre = value;
    }

    /**
     * Gets the value of the fraseDebole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFraseDebole() {
        return fraseDebole;
    }

    /**
     * Sets the value of the fraseDebole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFraseDebole(String value) {
        this.fraseDebole = value;
    }

    /**
     * Gets the value of the fraseForte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFraseForte() {
        return fraseForte;
    }

    /**
     * Sets the value of the fraseForte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFraseForte(String value) {
        this.fraseForte = value;
    }

}
