/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getelencofarmacieabituali;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per elencoFarmacieType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="elencoFarmacieType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="farmaciaAbituale" type="{http://getelencofarmacieabituali.msg.interfacews.farmab.dma.csi.it/}farmacia" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoFarmacieType", propOrder = {
    "farmaciaAbituale"
})
public class ElencoFarmacieType {

    @XmlElement(required = true)
    protected List<Farmacia> farmaciaAbituale;

    /**
     * Gets the value of the farmaciaAbituale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the farmaciaAbituale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFarmaciaAbituale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Farmacia }
     * 
     * 
     */
    public List<Farmacia> getFarmaciaAbituale() {
        if (farmaciaAbituale == null) {
            farmaciaAbituale = new ArrayList<Farmacia>();
        }
        return this.farmaciaAbituale;
    }

}
