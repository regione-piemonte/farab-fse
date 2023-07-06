/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.ricercapaziente.dma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per accessoOperazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="accessoOperazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicazioneVerticale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscaleOperatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognomeOperatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="collocazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataAccesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idAccesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeOperatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regime" type="{http://dma.csi.it/}regimeDMA" minOccurs="0"/>
 *         &lt;element name="ruolo" type="{http://dma.csi.it/}ruoloDMA" minOccurs="0"/>
 *         &lt;element name="tipoOperazione" type="{http://dma.csi.it/}tipoAccessoOperazione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessoOperazione", propOrder = {
    "applicazioneVerticale",
    "codiceFiscaleOperatore",
    "cognomeOperatore",
    "collocazione",
    "dataAccesso",
    "descrizione",
    "idAccesso",
    "nomeOperatore",
    "regime",
    "ruolo",
    "tipoOperazione"
})
public class AccessoOperazione {

    protected String applicazioneVerticale;
    protected String codiceFiscaleOperatore;
    protected String cognomeOperatore;
    protected String collocazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccesso;
    protected String descrizione;
    protected String idAccesso;
    protected String nomeOperatore;
    protected RegimeDMA regime;
    protected RuoloDMA ruolo;
    protected TipoAccessoOperazione tipoOperazione;

    /**
     * Recupera il valore della proprietÃ  applicazioneVerticale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicazioneVerticale() {
        return applicazioneVerticale;
    }

    /**
     * Imposta il valore della proprietÃ  applicazioneVerticale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicazioneVerticale(String value) {
        this.applicazioneVerticale = value;
    }

    /**
     * Recupera il valore della proprietÃ  codiceFiscaleOperatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleOperatore() {
        return codiceFiscaleOperatore;
    }

    /**
     * Imposta il valore della proprietÃ  codiceFiscaleOperatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleOperatore(String value) {
        this.codiceFiscaleOperatore = value;
    }

    /**
     * Recupera il valore della proprietÃ  cognomeOperatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeOperatore() {
        return cognomeOperatore;
    }

    /**
     * Imposta il valore della proprietÃ  cognomeOperatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeOperatore(String value) {
        this.cognomeOperatore = value;
    }

    /**
     * Recupera il valore della proprietÃ  collocazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollocazione() {
        return collocazione;
    }

    /**
     * Imposta il valore della proprietÃ  collocazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollocazione(String value) {
        this.collocazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataAccesso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAccesso() {
        return dataAccesso;
    }

    /**
     * Imposta il valore della proprietÃ  dataAccesso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAccesso(XMLGregorianCalendar value) {
        this.dataAccesso = value;
    }

    /**
     * Recupera il valore della proprietÃ  descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietÃ  descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietÃ  idAccesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAccesso() {
        return idAccesso;
    }

    /**
     * Imposta il valore della proprietÃ  idAccesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAccesso(String value) {
        this.idAccesso = value;
    }

    /**
     * Recupera il valore della proprietÃ  nomeOperatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeOperatore() {
        return nomeOperatore;
    }

    /**
     * Imposta il valore della proprietÃ  nomeOperatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeOperatore(String value) {
        this.nomeOperatore = value;
    }

    /**
     * Recupera il valore della proprietÃ  regime.
     * 
     * @return
     *     possible object is
     *     {@link RegimeDMA }
     *     
     */
    public RegimeDMA getRegime() {
        return regime;
    }

    /**
     * Imposta il valore della proprietÃ  regime.
     * 
     * @param value
     *     allowed object is
     *     {@link RegimeDMA }
     *     
     */
    public void setRegime(RegimeDMA value) {
        this.regime = value;
    }

    /**
     * Recupera il valore della proprietÃ  ruolo.
     * 
     * @return
     *     possible object is
     *     {@link RuoloDMA }
     *     
     */
    public RuoloDMA getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il valore della proprietÃ  ruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloDMA }
     *     
     */
    public void setRuolo(RuoloDMA value) {
        this.ruolo = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link TipoAccessoOperazione }
     *     
     */
    public TipoAccessoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietÃ  tipoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAccessoOperazione }
     *     
     */
    public void setTipoOperazione(TipoAccessoOperazione value) {
        this.tipoOperazione = value;
    }

}
