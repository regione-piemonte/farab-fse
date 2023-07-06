/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class CatalogoLogLowPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2974477974694686771L;
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
	public CatalogoLogLowPk() {
	}

	/**
	 * @generated
	 */
	public CatalogoLogLowPk(

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

		if (!(_other instanceof CatalogoLogLowPk)) {
			return false;
		}

		final CatalogoLogLowPk _cast = (CatalogoLogLowPk) _other;

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

		ret.append("it.csi.dma.dmaclbl.business.dao.dmaclbl.dto.CatalogoLogLowPk: ");
		ret.append("_id=" + _id);

		return ret.toString();
	}
}
