/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmairidecache;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identitaIride" minOccurs="0" form="qualified">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="livelloAutenticazione" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/>
 *                   &lt;element name="codFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="mac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="idProvider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="rappresentazioneInterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                   &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identitaIride"
})
@XmlRootElement(name = "IdentificaUserPasswordPinRicevuta")
public class IdentificaUserPasswordPinRicevuta {

    protected IdentificaUserPasswordPinRicevuta.IdentitaIride identitaIride;

    /**
     * Recupera il valore della propriet identitaIride.
     * 
     * @return
     *     possible object is
     *     {@link IdentificaUserPasswordPinRicevuta.IdentitaIride }
     *     
     */
    public IdentificaUserPasswordPinRicevuta.IdentitaIride getIdentitaIride() {
        return identitaIride;
    }

    /**
     * Imposta il valore della propriet identitaIride.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificaUserPasswordPinRicevuta.IdentitaIride }
     *     
     */
    public void setIdentitaIride(IdentificaUserPasswordPinRicevuta.IdentitaIride value) {
        this.identitaIride = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="livelloAutenticazione" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/>
     *         &lt;element name="codFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="mac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="idProvider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="rappresentazioneInterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "nome",
        "livelloAutenticazione",
        "codFiscale",
        "timestamp",
        "mac",
        "idProvider",
        "rappresentazioneInterna",
        "cognome"
    })
    public static class IdentitaIride {

        protected String nome;
        protected int livelloAutenticazione;
        protected String codFiscale;
        protected String timestamp;
        protected String mac;
        protected String idProvider;
        protected String rappresentazioneInterna;
        protected String cognome;

        /**
         * Recupera il valore della propriet nome.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNome() {
            return nome;
        }

        /**
         * Imposta il valore della propriet nome.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNome(String value) {
            this.nome = value;
        }

        /**
         * Recupera il valore della propriet livelloAutenticazione.
         * 
         */
        public int getLivelloAutenticazione() {
            return livelloAutenticazione;
        }

        /**
         * Imposta il valore della propriet livelloAutenticazione.
         * 
         */
        public void setLivelloAutenticazione(int value) {
            this.livelloAutenticazione = value;
        }

        /**
         * Recupera il valore della propriet codFiscale.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodFiscale() {
            return codFiscale;
        }

        /**
         * Imposta il valore della propriet codFiscale.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodFiscale(String value) {
            this.codFiscale = value;
        }

        /**
         * Recupera il valore della propriet timestamp.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTimestamp() {
            return timestamp;
        }

        /**
         * Imposta il valore della propriet timestamp.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTimestamp(String value) {
            this.timestamp = value;
        }

        /**
         * Recupera il valore della propriet mac.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMac() {
            return mac;
        }

        /**
         * Imposta il valore della propriet mac.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMac(String value) {
            this.mac = value;
        }

        /**
         * Recupera il valore della propriet idProvider.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdProvider() {
            return idProvider;
        }

        /**
         * Imposta il valore della propriet idProvider.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdProvider(String value) {
            this.idProvider = value;
        }

        /**
         * Recupera il valore della propriet rappresentazioneInterna.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRappresentazioneInterna() {
            return rappresentazioneInterna;
        }

        /**
         * Imposta il valore della propriet rappresentazioneInterna.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRappresentazioneInterna(String value) {
            this.rappresentazioneInterna = value;
        }

        /**
         * Recupera il valore della propriet cognome.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCognome() {
            return cognome;
        }

        /**
         * Imposta il valore della propriet cognome.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCognome(String value) {
            this.cognome = value;
        }

    }

}
