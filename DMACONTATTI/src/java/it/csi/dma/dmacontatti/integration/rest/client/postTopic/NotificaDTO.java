/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest.client.postTopic;

public class NotificaDTO {

	private String uuid = "";
	//Settare a true per l'invio singolo diretto con quanto valorizzato in oggetto
	private boolean trusted = true;
	//Se valorizzato con "high" allora il messaggio sarï¿½ inviato prima dei messaggi con prioritï¿½ inferiore.
	private String priority = "";
	private NotificaPayloadDTO payload;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public NotificaPayloadDTO getPayload() {
		return payload;
	}
	public void setPayload(NotificaPayloadDTO payload) {
		this.payload = payload;
	}
	public boolean isTrusted() {
		return trusted;
	}
	public void setTrusted(boolean trusted) {
		this.trusted = trusted;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
