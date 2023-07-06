/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class LogLowDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3186708022529016117L;
	/**
	 * @generated
	 */
	private Long _id;
	private String codiceServizio;
	private String datiRecuperatiDaCachePromemoria;
	
	
	
	
	public String getDatiRecuperatiDaCachePromemoria() {
		return datiRecuperatiDaCachePromemoria;
	}

	public void setDatiRecuperatiDaCachePromemoria(String datiRecuperatiDaCachePromemoria) {
		this.datiRecuperatiDaCachePromemoria = datiRecuperatiDaCachePromemoria;
	}

	public String getCodiceServizio() {
		return codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

	/**
	 * @generated
	 */
	public void setId(Long val) {
		_id = val;
	}

	/**
	 * @generated
	 */
	public Long getId() {
		return _id;
	}

	/**
	 * @generated
	 */
	private String _idTransazione;

	/**
	 * @generated
	 */
	public void setIdTransazione(String val) {
		_idTransazione = val;
	}

	/**
	 * @generated
	 */
	public String getIdTransazione() {
		return _idTransazione;
	}

	/**
	 * @generated
	 */
	private String _codiceTokenOperazione;

	/**
	 * @generated
	 */
	public void setCodiceTokenOperazione(String val) {
		_codiceTokenOperazione = val;
	}

	/**
	 * @generated
	 */
	public String getCodiceTokenOperazione() {
		return _codiceTokenOperazione;
	}

	/**
	 * @generated
	 */
	private String _informazioniTracciate;

	/**
	 * @generated
	 */
	public void setInformazioniTracciate(String val) {
		_informazioniTracciate = val;
	}

	/**
	 * @generated
	 */
	public String getInformazioniTracciate() {
		return _informazioniTracciate;
	}

	/**
	 * @generated
	 */
	private java.sql.Timestamp _dataInserimento;

	/**
	 * @generated
	 */
	public void setDataInserimento(java.sql.Timestamp val) {
		_dataInserimento = val;
	}

	/**
	 * @generated
	 */
	public java.sql.Timestamp getDataInserimento() {
		return _dataInserimento;
	}

	/**
	 * @generated
	 */
	private Long _idPaziente;

	/**
	 * @generated
	 */
	public void setIdPaziente(Long val) {
		_idPaziente = val;
	}

	/**
	 * @generated
	 */
	public Long getIdPaziente() {
		return _idPaziente;
	}

	/**
	 * @generated
	 */
	private Long _idCatalogoLog;

	/**
	 * @generated
	 */
	public void setIdCatalogoLog(Long val) {
		_idCatalogoLog = val;
	}

	/**
	 * @generated
	 */
	public Long getIdCatalogoLog() {
		return _idCatalogoLog;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 * @generated
	 */
	public boolean equals(Object _other) {
		// TODO
		return super.equals(_other);
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 * @generated
	 */
	public int hashCode() {
		// TODO
		return super.hashCode();
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return LogLowPk
	 * @generated
	 */
	public LogLowPk createPk() {
		return new LogLowPk(_id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 * @generated
	 */
	public String toString() {
		// TODO
		return super.toString();
	}

}
