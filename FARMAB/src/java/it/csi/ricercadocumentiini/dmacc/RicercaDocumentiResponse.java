/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.ricercadocumentiini.dmacc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import it.csi.ricercadocumentiini.dma.RicercaDocumentiOUT;


/**
 * <p>Classe Java per ricercaDocumentiResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaDocumentiResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dmacc.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="ricercaDocumentiOUT" type="{http://dma.csi.it/}ricercaDocumentiOUT" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDocumentiResponse", propOrder = {
    "ricercaDocumentiOUT"
})
public class RicercaDocumentiResponse
    extends ServiceResponse
{

    protected RicercaDocumentiOUT ricercaDocumentiOUT;

    /**
     * Recupera il valore della proprietÃ  ricercaDocumentiOUT.
     * 
     * @return
     *     possible object is
     *     {@link RicercaDocumentiOUT }
     *     
     */
    public RicercaDocumentiOUT getRicercaDocumentiOUT() {
        return ricercaDocumentiOUT;
    }

    /**
     * Imposta il valore della proprietÃ  ricercaDocumentiOUT.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaDocumentiOUT }
     *     
     */
    public void setRicercaDocumentiOUT(RicercaDocumentiOUT value) {
        this.ricercaDocumentiOUT = value;
    }

}
