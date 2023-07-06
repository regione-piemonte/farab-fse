/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class ServiziRichiamatiLowDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
    private String idTransazione;
    private Long idServizio;
    private String request;
    private String response;
    private Timestamp dataChiamata;
    private Timestamp dataRisposta;
    private String esito;
	private Timestamp dataInserimento;
	private Timestamp dataAggiornamento;

	private String encryptionKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdTransazione() {
		return idTransazione;
	}

	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}

	public Long getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(Long idServizio) {
		this.idServizio = idServizio;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Timestamp getDataChiamata() {
		return dataChiamata;
	}

	public void setDataChiamata(Timestamp dataChiamata) {
		this.dataChiamata = dataChiamata;
	}

	public Timestamp getDataRisposta() {
		return dataRisposta;
	}

	public void setDataRisposta(Timestamp dataRisposta) {
		this.dataRisposta = dataRisposta;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
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
     public final ServiziRichiamatiLowPk createPk() {
        return new ServiziRichiamatiLowPk(id);
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
