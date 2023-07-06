/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

//SB_FASE_3_PREGRESSO
public class ErroriLowDto implements Serializable {

    /**
     * @generated
     */
	private Long id;   
    private String wso2_id;
    private String cod_errore;    
    private String descr_errore;
    private String tipo_errore;    
    private String informazioni_aggiuntive;
    private java.sql.Timestamp data_ins;
    
   
    
   

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	

	public final boolean equals(Object other) {
        // TODO
        return super.equals(other);
    }

    /**
     * Method 'hashCode'
     * 
     * @return int
     * @generated
     */
    public final int hashCode() {
        // TODO
        return super.hashCode();
    }

    /**
     * Method 'createPk'
     * 
     * @return ConsensoLowPk
     * @generated
     */
     public final ErroriLowPk createPk() {
        return new ErroriLowPk(id);
    }

    /**
     * Method 'toString'
     * 
     * @return String
     * @generated
     */
    public final String toString() {
        // TODO
        return super.toString();
    }

	public String getWso2_id() {
		return wso2_id;
	}

	public void setWso2_id(String wso2_id) {
		this.wso2_id = wso2_id;
	}

	public String getCod_errore() {
		return cod_errore;
	}

	public void setCod_errore(String cod_errore) {
		this.cod_errore = cod_errore;
	}

	public String getDescr_errore() {
		return descr_errore;
	}

	public void setDescr_errore(String descr_errore) {
		this.descr_errore = descr_errore;
	}

	public String getTipo_errore() {
		return tipo_errore;
	}

	public void setTipo_errore(String tipo_errore) {
		this.tipo_errore = tipo_errore;
	}

	public String getInformazioni_aggiuntive() {
		return informazioni_aggiuntive;
	}

	public void setInformazioni_aggiuntive(String informazioni_aggiuntive) {
		this.informazioni_aggiuntive = informazioni_aggiuntive;
	}

	public java.sql.Timestamp getData_ins() {
		return data_ins;
	}

	public void setData_ins(java.sql.Timestamp data_ins) {
		this.data_ins = data_ins;
	}
    
    

}
