/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

public class CatalogoServiziOperationLowDto implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String codiceServizio;
	private String nomeServizio;
	private String nomeOperation;
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public String getNomeServizio() {
		return nomeServizio;
	}
	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}
	public String getNomeOperation() {
		return nomeOperation;
	}
	public void setNomeOperation(String nomeOperation) {
		this.nomeOperation = nomeOperation;
	}
	
	

}
