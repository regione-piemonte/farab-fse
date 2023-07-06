/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class LogLowPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3822302240051913077L;
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
	public LogLowPk() {
	}

	/**
	 * @generated
	 */
	public LogLowPk(

	final Long id

	) {

		this._id = id;

	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}

		if (_other == this) {
			return true;
		}

		if (!(_other instanceof LogLowPk)) {
			return false;
		}

		final LogLowPk _cast = (LogLowPk) _other;

		if (_id == null ? _cast.getId() != _id : !_id.equals(_cast.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;

		if (_id != null) {
			_hashCode = 29 * _hashCode + _id.hashCode();
		}

		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();

		ret.append("it.csi.dma.dmaclbl.business.dao.dmaclbl.dto.LogLowPk: ");
		ret.append("_id=" + _id);

		return ret.toString();
	}
}
