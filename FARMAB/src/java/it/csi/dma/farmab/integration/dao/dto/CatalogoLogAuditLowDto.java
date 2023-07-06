/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CatalogoLogAuditLowDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6541652355823365788L;
	
	private Long _id;
	private String _codice;
	private String _descrizione;
	private Timestamp _dataInserimento;
	private String _descrizioneCodice;

	public void setId(Long val) {
		this._id = val;
	}

	public Long getId() {
		return this._id;
	}

	public void setCodice(String val) {
		this._codice = val;
	}

	public String getCodice() {
		return this._codice;
	}

	public void setDescrizione(String val) {
		this._descrizione = val;
	}

	public String getDescrizione() {
		return this._descrizione;
	}

	public void setDataInserimento(Timestamp val) {
		this._dataInserimento = val;
	}

	public Timestamp getDataInserimento() {
		return this._dataInserimento;
	}

	public void setDescrizioneCodice(String val) {
		this._descrizioneCodice = val;
	}

	public String getDescrizioneCodice() {
		return this._descrizioneCodice;
	}

	public boolean equals(Object _other) {
		return super.equals(_other);
	}

	public int hashCode() {
		return super.hashCode();
	}

	

	public String toString() {
		return super.toString();
	}
}
