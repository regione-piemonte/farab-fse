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
 * <p>Classe Java per metadatoRicercaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="metadatoRicercaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assettoOrganizzativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscaleAutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conservazioneSost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFinePrestazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInizioPrestazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataValidazioneDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hashDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoAssistito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoOrgDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoPaziente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoRepository" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoUnivocoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="istituzioneAutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linguaDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="livelloConfidenzialita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rappresentanteLegale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="regoleAccesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riferimentoDocPrescr" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ruoloAutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sizeDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialitaAutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telecAutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumentoAlto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumentoBasso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumentoMedio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaStrutturaProdDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versioneOggettoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadatoRicercaType", propOrder = {
    "assettoOrganizzativo",
    "codiceFiscaleAutore",
    "conservazioneSost",
    "dataFinePrestazione",
    "dataInizioPrestazione",
    "dataValidazioneDocumento",
    "hashDoc",
    "identificativoAssistito",
    "identificativoDocumento",
    "identificativoOrgDoc",
    "identificativoPaziente",
    "identificativoRepository",
    "identificativoUnivocoDocumento",
    "istituzioneAutore",
    "lid",
    "linguaDocumento",
    "livelloConfidenzialita",
    "rappresentanteLegale",
    "regoleAccesso",
    "riferimentoDocPrescr",
    "ruoloAutore",
    "sizeDoc",
    "specialitaAutore",
    "statoDocumento",
    "telecAutore",
    "tipoMime",
    "tipologiaDocumentoAlto",
    "tipologiaDocumentoBasso",
    "tipologiaDocumentoMedio",
    "tipologiaStrutturaProdDoc",
    "versioneOggettoDocumento"
})
public class MetadatoRicercaType {

    protected String assettoOrganizzativo;
    protected String codiceFiscaleAutore;
    protected String conservazioneSost;
    protected String dataFinePrestazione;
    protected String dataInizioPrestazione;
    protected String dataValidazioneDocumento;
    protected String hashDoc;
    protected String identificativoAssistito;
    protected String identificativoDocumento;
    protected String identificativoOrgDoc;
    protected String identificativoPaziente;
    protected String identificativoRepository;
    protected String identificativoUnivocoDocumento;
    protected String istituzioneAutore;
    protected String lid;
    protected String linguaDocumento;
    protected String livelloConfidenzialita;
    protected String rappresentanteLegale;
    protected String regoleAccesso;
    @XmlElement(nillable = true)
    protected List<String> riferimentoDocPrescr;
    protected String ruoloAutore;
    protected String sizeDoc;
    protected String specialitaAutore;
    protected String statoDocumento;
    protected String telecAutore;
    protected String tipoMime;
    protected String tipologiaDocumentoAlto;
    protected String tipologiaDocumentoBasso;
    protected String tipologiaDocumentoMedio;
    protected String tipologiaStrutturaProdDoc;
    protected String versioneOggettoDocumento;

    /**
     * Recupera il valore della proprietÃ  assettoOrganizzativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssettoOrganizzativo() {
        return assettoOrganizzativo;
    }

    /**
     * Imposta il valore della proprietÃ  assettoOrganizzativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssettoOrganizzativo(String value) {
        this.assettoOrganizzativo = value;
    }

    /**
     * Recupera il valore della proprietÃ  codiceFiscaleAutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleAutore() {
        return codiceFiscaleAutore;
    }

    /**
     * Imposta il valore della proprietÃ  codiceFiscaleAutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleAutore(String value) {
        this.codiceFiscaleAutore = value;
    }

    /**
     * Recupera il valore della proprietÃ  conservazioneSost.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConservazioneSost() {
        return conservazioneSost;
    }

    /**
     * Imposta il valore della proprietÃ  conservazioneSost.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConservazioneSost(String value) {
        this.conservazioneSost = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataFinePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFinePrestazione() {
        return dataFinePrestazione;
    }

    /**
     * Imposta il valore della proprietÃ  dataFinePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFinePrestazione(String value) {
        this.dataFinePrestazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataInizioPrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioPrestazione() {
        return dataInizioPrestazione;
    }

    /**
     * Imposta il valore della proprietÃ  dataInizioPrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioPrestazione(String value) {
        this.dataInizioPrestazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataValidazioneDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataValidazioneDocumento() {
        return dataValidazioneDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  dataValidazioneDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataValidazioneDocumento(String value) {
        this.dataValidazioneDocumento = value;
    }

    /**
     * Recupera il valore della proprietÃ  hashDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashDoc() {
        return hashDoc;
    }

    /**
     * Imposta il valore della proprietÃ  hashDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashDoc(String value) {
        this.hashDoc = value;
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
     * Recupera il valore della proprietÃ  identificativoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDocumento() {
        return identificativoDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDocumento(String value) {
        this.identificativoDocumento = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoOrgDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoOrgDoc() {
        return identificativoOrgDoc;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoOrgDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoOrgDoc(String value) {
        this.identificativoOrgDoc = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoPaziente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPaziente() {
        return identificativoPaziente;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoPaziente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPaziente(String value) {
        this.identificativoPaziente = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoRepository.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoRepository() {
        return identificativoRepository;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoRepository.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoRepository(String value) {
        this.identificativoRepository = value;
    }

    /**
     * Recupera il valore della proprietÃ  identificativoUnivocoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoDocumento() {
        return identificativoUnivocoDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  identificativoUnivocoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoDocumento(String value) {
        this.identificativoUnivocoDocumento = value;
    }

    /**
     * Recupera il valore della proprietÃ  istituzioneAutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstituzioneAutore() {
        return istituzioneAutore;
    }

    /**
     * Imposta il valore della proprietÃ  istituzioneAutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstituzioneAutore(String value) {
        this.istituzioneAutore = value;
    }

    /**
     * Recupera il valore della proprietÃ  lid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLid() {
        return lid;
    }

    /**
     * Imposta il valore della proprietÃ  lid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLid(String value) {
        this.lid = value;
    }

    /**
     * Recupera il valore della proprietÃ  linguaDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinguaDocumento() {
        return linguaDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  linguaDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinguaDocumento(String value) {
        this.linguaDocumento = value;
    }

    /**
     * Recupera il valore della proprietÃ  livelloConfidenzialita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloConfidenzialita() {
        return livelloConfidenzialita;
    }

    /**
     * Imposta il valore della proprietÃ  livelloConfidenzialita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloConfidenzialita(String value) {
        this.livelloConfidenzialita = value;
    }

    /**
     * Recupera il valore della proprietÃ  rappresentanteLegale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRappresentanteLegale() {
        return rappresentanteLegale;
    }

    /**
     * Imposta il valore della proprietÃ  rappresentanteLegale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRappresentanteLegale(String value) {
        this.rappresentanteLegale = value;
    }

    /**
     * Recupera il valore della proprietÃ  regoleAccesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegoleAccesso() {
        return regoleAccesso;
    }

    /**
     * Imposta il valore della proprietÃ  regoleAccesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegoleAccesso(String value) {
        this.regoleAccesso = value;
    }

    /**
     * Gets the value of the riferimentoDocPrescr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riferimentoDocPrescr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiferimentoDocPrescr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRiferimentoDocPrescr() {
        if (riferimentoDocPrescr == null) {
            riferimentoDocPrescr = new ArrayList<String>();
        }
        return this.riferimentoDocPrescr;
    }

    /**
     * Recupera il valore della proprietÃ  ruoloAutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuoloAutore() {
        return ruoloAutore;
    }

    /**
     * Imposta il valore della proprietÃ  ruoloAutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuoloAutore(String value) {
        this.ruoloAutore = value;
    }

    /**
     * Recupera il valore della proprietÃ  sizeDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSizeDoc() {
        return sizeDoc;
    }

    /**
     * Imposta il valore della proprietÃ  sizeDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSizeDoc(String value) {
        this.sizeDoc = value;
    }

    /**
     * Recupera il valore della proprietÃ  specialitaAutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialitaAutore() {
        return specialitaAutore;
    }

    /**
     * Imposta il valore della proprietÃ  specialitaAutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialitaAutore(String value) {
        this.specialitaAutore = value;
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
     * Recupera il valore della proprietÃ  telecAutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelecAutore() {
        return telecAutore;
    }

    /**
     * Imposta il valore della proprietÃ  telecAutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelecAutore(String value) {
        this.telecAutore = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipoMime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMime() {
        return tipoMime;
    }

    /**
     * Imposta il valore della proprietÃ  tipoMime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMime(String value) {
        this.tipoMime = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipologiaDocumentoAlto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaDocumentoAlto() {
        return tipologiaDocumentoAlto;
    }

    /**
     * Imposta il valore della proprietÃ  tipologiaDocumentoAlto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaDocumentoAlto(String value) {
        this.tipologiaDocumentoAlto = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipologiaDocumentoBasso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaDocumentoBasso() {
        return tipologiaDocumentoBasso;
    }

    /**
     * Imposta il valore della proprietÃ  tipologiaDocumentoBasso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaDocumentoBasso(String value) {
        this.tipologiaDocumentoBasso = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipologiaDocumentoMedio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaDocumentoMedio() {
        return tipologiaDocumentoMedio;
    }

    /**
     * Imposta il valore della proprietÃ  tipologiaDocumentoMedio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaDocumentoMedio(String value) {
        this.tipologiaDocumentoMedio = value;
    }

    /**
     * Recupera il valore della proprietÃ  tipologiaStrutturaProdDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaStrutturaProdDoc() {
        return tipologiaStrutturaProdDoc;
    }

    /**
     * Imposta il valore della proprietÃ  tipologiaStrutturaProdDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaStrutturaProdDoc(String value) {
        this.tipologiaStrutturaProdDoc = value;
    }

    /**
     * Recupera il valore della proprietÃ  versioneOggettoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneOggettoDocumento() {
        return versioneOggettoDocumento;
    }

    /**
     * Imposta il valore della proprietÃ  versioneOggettoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneOggettoDocumento(String value) {
        this.versioneOggettoDocumento = value;
    }

}
