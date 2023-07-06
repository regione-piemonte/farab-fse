/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.ricercadocumentiini.dma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ricercaDocumentiIN complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaDocumentiIN">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contestoOperativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataRicercaA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataRicercaDA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizioneOrganizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elencoCFAssistito" type="{http://dma.csi.it/}elencoCFAssistitoType" minOccurs="0"/>
 *         &lt;element name="identificativoAssistito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoAssistitoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="identificativoOrganizzazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoUtente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="opzioniRequest" type="{http://dma.csi.it/}opzioniType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="opzioniRisposta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pinCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presaInCarico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruoloUtente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strutturaUtente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAttivita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumento" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDocumentiIN", propOrder = {
    "contestoOperativo",
    "dataRicercaA",
    "dataRicercaDA",
    "descrizioneOrganizzazione",
    "elencoCFAssistito",
    "identificativoAssistito",
    "identificativoAssistitoDoc",
    "identificativoDocumento",
    "identificativoOrganizzazione",
    "identificativoUtente",
    "opzioniRequest",
    "opzioniRisposta",
    "pinCode",
    "presaInCarico",
    "ruoloUtente",
    "statoDocumento",
    "strutturaUtente",
    "tipoAttivita",
    "tipoDocumento",
    "tipologiaDocumento"
})
public class RicercaDocumentiIN {

    protected String contestoOperativo;
    protected String dataRicercaA;
    protected String dataRicercaDA;
    protected String descrizioneOrganizzazione;
    protected ElencoCFAssistitoType elencoCFAssistito;
    protected String identificativoAssistito;
    protected String identificativoAssistitoDoc;
    @XmlElement(nillable = true)
    protected List<String> identificativoDocumento;
    protected String identificativoOrganizzazione;
    protected String identificativoUtente;
    @XmlElement(nillable = true)
    protected List<OpzioniType> opzioniRequest;
    protected String opzioniRisposta;
    protected String pinCode;
    protected String presaInCarico;
    protected String ruoloUtente;
    protected String statoDocumento;
    protected String strutturaUtente;
    protected String tipoAttivita;
    protected String tipoDocumento;
    @XmlElement(nillable = true)
    protected List<String> tipologiaDocumento;

    /**
     * Recupera il valore della proprietÃ  contestoOperativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContestoOperativo() {
        return contestoOperativo;
    }

    /**
     * Imposta il valore della proprietÃ  contestoOperativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContestoOperativo(String value) {
        this.contestoOperativo = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataRicercaA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRicercaA() {
        return dataRicercaA;
    }

    /**
     * Imposta il valore della proprietÃ  dataRicercaA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRicercaA(String value) {
        this.dataRicercaA = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataRicercaDA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRicercaDA() {
        return dataRicercaDA;
    }

    /**
     * Imposta il valore della proprietÃ  dataRicercaDA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRicercaDA(String value) {
        this.dataRicercaDA = value;
    }

    /**
     * Recupera il valore della proprietÃ  descrizioneOrganizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneOrganizzazione() {
        return descrizioneOrganizzazione;
    }

    /**
     * Imposta il valore della proprietÃ  descrizioneOrganizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneOrganizzazione(String value) {
        this.descrizioneOrganizzazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  elencoCFAssistito.
     * 
     * @return
     *     possible object is
     *     {@link ElencoCFAssistitoType }
     *     
     */
    public ElencoCFAssistitoType getElencoCFAssistito() {
        return elencoCFAssistito;
    }

    /**
     * Imposta il valore della proprietÃ  elencoCFAssistito.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoCFAssistitoType }
     *     
     */
    public void setElencoCFAssistito(ElencoCFAssistitoType value) {
        this.elencoCFAssistito = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoAssistito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoAssistito() {
        return identificativoAssistito;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoAssistito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoAssistito(String value) {
        this.identificativoAssistito = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoAssistitoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoAssistitoDoc() {
        return identificativoAssistitoDoc;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoAssistitoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoAssistitoDoc(String value) {
        this.identificativoAssistitoDoc = value;
    }

    /**
     * Gets the value of the identificativoDocumento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificativoDocumento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificativoDocumento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIdentificativoDocumento() {
        if (identificativoDocumento == null) {
            identificativoDocumento = new ArrayList<String>();
        }
        return this.identificativoDocumento;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoOrganizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoOrganizzazione() {
        return identificativoOrganizzazione;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoOrganizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoOrganizzazione(String value) {
        this.identificativoOrganizzazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUtente() {
        return identificativoUtente;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUtente(String value) {
        this.identificativoUtente = value;
    }

    /**
     * Gets the value of the opzioniRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the opzioniRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpzioniRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OpzioniType }
     * 
     * 
     */
    public List<OpzioniType> getOpzioniRequest() {
        if (opzioniRequest == null) {
            opzioniRequest = new ArrayList<OpzioniType>();
        }
        return this.opzioniRequest;
    }

    /**
     * Recupera il valore della proprietÃ  opzioniRisposta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpzioniRisposta() {
        return opzioniRisposta;
    }

    /**
     * Imposta il valore della proprietÃ  opzioniRisposta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpzioniRisposta(String value) {
        this.opzioniRisposta = value;
    }

    /**
     * Recupera il valore della proprietÃ  pinCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Imposta il valore della proprietÃ  pinCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinCode(String value) {
        this.pinCode = value;
    }

    /**
     * Recupera il valore della proprietÃ  presaInCarico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresaInCarico() {
        return presaInCarico;
    }

    /**
     * Imposta il valore della proprietÃ  presaInCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresaInCarico(String value) {
        this.presaInCarico = value;
    }

    /**
     * Recupera il valore della proprietÃ  ruoloUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuoloUtente() {
        return ruoloUtente;
    }

    /**
     * Imposta il valore della proprietÃ  ruoloUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuoloUtente(String value) {
        this.ruoloUtente = value;
    }

    /**
     * Recupera il valore della proprietÃ  statoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoDocumento() {
        return statoDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  statoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoDocumento(String value) {
        this.statoDocumento = value;
    }

    /**
     * Recupera il valore della proprietÃ  strutturaUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrutturaUtente() {
        return strutturaUtente;
    }

    /**
     * Imposta il valore della proprietÃ  strutturaUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrutturaUtente(String value) {
        this.strutturaUtente = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipoAttivita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAttivita() {
        return tipoAttivita;
    }

    /**
     * Imposta il valore della proprietÃ  tipoAttivita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAttivita(String value) {
        this.tipoAttivita = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the tipologiaDocumento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipologiaDocumento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipologiaDocumento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTipologiaDocumento() {
        if (tipologiaDocumento == null) {
            tipologiaDocumento = new ArrayList<String>();
        }
        return this.tipologiaDocumento;
    }

}
