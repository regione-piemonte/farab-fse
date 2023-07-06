/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.domain;

public class DmaccTDispositivoCertificatoStato {
	
	long deviceId;
	String deviceUuid;
	String deviceStatoCod;
	String deviceStatoDesc;
	String deviceNumeroTelefono;
	String deviceSO;
	String deviceBrowser;
	String deviceModello;
	long idPaziente;
	long deviceStatoId;
	long fonteCertId;
	String cittadinoCf;
	String dataCertificazione;
	String dataInserimento;
	String dataAggiornamento;
	String utenteInserimento;
	String utenteAggiornamento;
	String fonteCertCod;
	String fonteCertDesc;
	
	public DmaccTDispositivoCertificatoStato(long deviceId, String deviceUuid, String deviceStatoCod,
			String deviceStatoDesc, String deviceNumeroTelefono, String deviceSO, String deviceBrowser,
			String deviceModello, long idPaziente, long deviceStatoId, long fonteCertId, String cittadinoCf,
			String dataCertificazione, String dataInserimento, String dataAggiornamento,
			String utenteInserimento, String utenteAggiornamento,String fonteCertCod,String fonteCertDesc) {
		super();
		this.deviceId = deviceId;
		this.deviceUuid = deviceUuid;
		this.deviceStatoCod = deviceStatoCod;
		this.deviceStatoDesc = deviceStatoDesc;
		this.deviceNumeroTelefono = deviceNumeroTelefono;
		this.deviceSO = deviceSO;
		this.deviceBrowser = deviceBrowser;
		this.deviceModello = deviceModello;
		this.idPaziente = idPaziente;
		this.deviceStatoId = deviceStatoId;
		this.fonteCertId = fonteCertId;
		this.cittadinoCf = cittadinoCf;
		this.dataCertificazione = dataCertificazione;
		this.dataInserimento = dataInserimento;
		this.dataAggiornamento = dataAggiornamento;
		this.utenteInserimento = utenteInserimento;
		this.utenteAggiornamento = utenteAggiornamento;
		this.fonteCertCod=fonteCertCod;
		this.fonteCertDesc=fonteCertDesc;
	}
	
	public DmaccTDispositivoCertificatoStato(long deviceId, String dataInserimento) {
		super();
		this.deviceId = deviceId;
		this.dataInserimento = dataInserimento;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getDeviceStatoCod() {
		return deviceStatoCod;
	}

	public void setDeviceStatoCod(String deviceStatoCod) {
		this.deviceStatoCod = deviceStatoCod;
	}

	public String getDeviceStatoDesc() {
		return deviceStatoDesc;
	}

	public void setDeviceStatoDesc(String deviceStatoDesc) {
		this.deviceStatoDesc = deviceStatoDesc;
	}

	public String getDeviceNumeroTelefono() {
		return deviceNumeroTelefono;
	}

	public void setDeviceNumeroTelefono(String deviceNumeroTelefono) {
		this.deviceNumeroTelefono = deviceNumeroTelefono;
	}

	public String getDeviceSO() {
		return deviceSO;
	}

	public void setDeviceSO(String deviceSO) {
		this.deviceSO = deviceSO;
	}

	public String getDeviceBrowser() {
		return deviceBrowser;
	}

	public void setDeviceBrowser(String deviceBrowser) {
		this.deviceBrowser = deviceBrowser;
	}

	public String getDeviceModello() {
		return deviceModello;
	}

	public void setDeviceModello(String deviceModello) {
		this.deviceModello = deviceModello;
	}

	public long getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(long idPaziente) {
		this.idPaziente = idPaziente;
	}

	public long getDeviceStatoId() {
		return deviceStatoId;
	}

	public void setDeviceStatoId(long deviceStatoId) {
		this.deviceStatoId = deviceStatoId;
	}

	public long getFonteCertId() {
		return fonteCertId;
	}

	public void setFonteCertId(long fonteCertId) {
		this.fonteCertId = fonteCertId;
	}

	public String getCittadinoCf() {
		return cittadinoCf;
	}

	public void setCittadinoCf(String cittadinoCf) {
		this.cittadinoCf = cittadinoCf;
	}

	public String getDataCertificazione() {
		return dataCertificazione;
	}

	public void setDataCertificazione(String dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(String dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(String utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public String getUtenteAggiornamento() {
		return utenteAggiornamento;
	}

	public void setUtenteAggiornamento(String utenteAggiornamento) {
		this.utenteAggiornamento = utenteAggiornamento;
	}

	public String getFonteCertCod() {
		return fonteCertCod;
	}

	public void setFonteCertCod(String fonteCertCod) {
		this.fonteCertCod = fonteCertCod;
	}

	public String getFonteCertDesc() {
		return fonteCertDesc;
	}

	public void setFonteCertDesc(String fonteCertDesc) {
		this.fonteCertDesc = fonteCertDesc;
	}
}
