/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.integration.rest.client.postTopic;

public class NotificaPayloadDTO {

	private String id = "";
	private String user_id = "";
	private NotificaPayloadEmailDTO email;
	private NotificaPayloadSmsDTO sms;
	private String tag = "";
	private boolean trusted;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public NotificaPayloadEmailDTO getEmail() {
		return email;
	}
	public void setEmail(NotificaPayloadEmailDTO email) {
		this.email = email;
	}
	public NotificaPayloadSmsDTO getSms() {
		return sms;
	}
	public void setSms(NotificaPayloadSmsDTO sms) {
		this.sms = sms;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the trusted
	 */
	public boolean isTrusted() {
		return trusted;
	}
	/**
	 * @param trusted the trusted to set
	 */
	public void setTrusted(boolean trusted) {
		this.trusted = trusted;
	}
	
}
