/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.domain;

public class DmaccLXmlMessaggi {
	private String xRequestId;
	private String xmlIn;
	private String xmlOut;
	
	
	public String getxRequestId() {
		return xRequestId;
	}
	public void setxRequestId(String xRequestId) {
		this.xRequestId = xRequestId;
	}
	public String getXmlIn() {
		return xmlIn;
	}
	public void setXmlIn(String xmlIn) {
		this.xmlIn = xmlIn;
	}
	public String getXmlOut() {
		return xmlOut;
	}
	public void setXmlOut(String xmlOut) {
		this.xmlOut = xmlOut;
	}
	
	
	
}
