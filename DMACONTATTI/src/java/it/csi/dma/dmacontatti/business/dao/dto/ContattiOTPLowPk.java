/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

public class ContattiOTPLowPk {
	
	private Long id;

	public ContattiOTPLowPk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContattiOTPLowPk(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "ContattiLowPk [id=" + id + "]";
	}

}
