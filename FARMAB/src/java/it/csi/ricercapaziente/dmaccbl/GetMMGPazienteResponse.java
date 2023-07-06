/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.ricercapaziente.dmaccbl;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import it.csi.ricercapaziente.dma.Medico;
import it.csi.ricercapaziente.dma.SiNo;


/**
 * <p>Classe Java per getMMGPazienteResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getMMGPazienteResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dmaccbl.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="haMedico" type="{http://dma.csi.it/}siNo" minOccurs="0"/>
 *         &lt;element name="medico" type="{http://dma.csi.it/}medico" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMMGPazienteResponse", propOrder = {
    "haMedico",
    "medico"
})
public class GetMMGPazienteResponse
    extends ServiceResponse
{

    protected SiNo haMedico;
    protected Medico medico;

    /**
     * Recupera il valore della proprietÃ  haMedico.
     * 
     * @return
     *     possible object is
     *     {@link SiNo }
     *     
     */
    public SiNo getHaMedico() {
        return haMedico;
    }

    /**
     * Imposta il valore della proprietÃ  haMedico.
     * 
     * @param value
     *     allowed object is
     *     {@link SiNo }
     *     
     */
    public void setHaMedico(SiNo value) {
        this.haMedico = value;
    }

    /**
     * Recupera il valore della proprietÃ  medico.
     * 
     * @return
     *     possible object is
     *     {@link Medico }
     *     
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Imposta il valore della proprietÃ  medico.
     * 
     * @param value
     *     allowed object is
     *     {@link Medico }
     *     
     */
    public void setMedico(Medico value) {
        this.medico = value;
    }

}