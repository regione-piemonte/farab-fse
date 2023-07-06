/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class CatalogoLogLowDto implements Serializable {

	/**
	 * @generated
	 */
	private Long _id;

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
	private String _codice;

	/**
	 * @generated
	 */
	public void setCodice(String val) {
		_codice = val;
	}

	/**
	 * @generated
	 */
	public String getCodice() {
		return _codice;
	}

	/**
	 * @generated
	 */
	private String _descrizioneErrore;

	/**
	 * @generated
	 */
	public void setDescrizioneErrore(String val) {
		_descrizioneErrore = val;
	}

	/**
	 * @generated
	 */
	public String getDescrizioneErrore() {
		return _descrizioneErrore;
	}

	/**
	 * @generated
	 */
	private String _descrizioneLog;

	/**
	 * @generated
	 */
	public void setDescrizioneLog(String val) {
		_descrizioneLog = val;
	}

	/**
	 * @generated
	 */
	public String getDescrizioneLog() {
		return _descrizioneLog;
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
	private String _flgErrore;

	/**
	 * @generated
	 */
	public void setFlgErrore(String val) {
		_flgErrore = val;
	}

	/**
	 * @generated
	 */
	public String getFlgErrore() {
		return _flgErrore;
	}
	
	private String _codFse;
	
	

	public String getCodFse() {
		return _codFse;
	}

	public void setCodFse(String codFse) {
		this._codFse = codFse;
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
	 * @return CatalogoLogLowPk
	 * @generated
	 */
	public CatalogoLogLowPk createPk() {
		return new CatalogoLogLowPk(_id);
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
