/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;


public class XmlMessaggiLowDto implements Serializable {

  
	private static final long serialVersionUID = 1L;

	private Long id;   
    private String wso2_id;
    private String xml_in;
    private String xml_out;
    private String xml_in_promemoria;    
    private String  xml_out_promemoria;    
    private java.sql.Timestamp data_inserimento;
    private String xml_in_servizio;
    private String xml_out_servizio;
    private String ecryption;
    private MessaggiLowDto messaggiDto;
	

	public MessaggiLowDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiLowDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public String getEcryption() {
		return ecryption;
	}

	public void setEcryption(String ecryption) {
		this.ecryption = ecryption;
	}
  

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXml_in() {
		return xml_in;
	}

	public void setXml_in(String xml_in) {
		this.xml_in = xml_in;
	}

	public String getXml_out() {
		return xml_out;
	}

	public void setXml_out(String xml_out) {
		this.xml_out = xml_out;
	}

	public String getXml_in_promemoria() {
		return xml_in_promemoria;
	}

	public void setXml_in_promemoria(String xml_in_promemoria) {
		this.xml_in_promemoria = xml_in_promemoria;
	}

	public String getXml_out_promemoria() {
		return xml_out_promemoria;
	}

	public void setXml_out_promemoria(String xml_out_promemoria) {
		this.xml_out_promemoria = xml_out_promemoria;
	}

	public java.sql.Timestamp getData_inserimento() {
		return data_inserimento;
	}

	public void setData_inserimento(java.sql.Timestamp data_inserimento) {
		this.data_inserimento = data_inserimento;
	}

	public String getXml_in_servizio() {
		return xml_in_servizio;
	}

	public void setXml_in_servizio(String xml_in_servizio) {
		this.xml_in_servizio = xml_in_servizio;
	}

	public String getXml_out_servizio() {
		return xml_out_servizio;
	}

	public void setXml_out_servizio(String xml_out_servizio) {
		this.xml_out_servizio = xml_out_servizio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWso2_id() {
		return wso2_id;
	}

	public void setWso2_id(String wso2_id) {
		this.wso2_id = wso2_id;
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

   
     public final XmlMessaggiLowPk createPk() {
        return new XmlMessaggiLowPk(id);
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

}
