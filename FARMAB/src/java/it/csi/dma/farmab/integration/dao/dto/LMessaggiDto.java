/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LMessaggiDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8528678713907966652L;
	Long idXml;
	private String wso2Id;
	private String servizioXml;
	private String uuid;
	private String chiamante;
	private String statoXml;
	private Timestamp dataRicezione;
	private Timestamp dataRisposta;
	private Timestamp dataIns;
	private Timestamp dataMod;
	private String cfAssistito;
	private String cfUtente;
	private String ruoloUtente;
	private Timestamp dataInvioServizio;
	private Timestamp dataRispostaServizio;
	private String codEsitoRispostaEervizio;
	private String codiceServizio;
	private String ipRichiedente;
	private String idMessaggioOrigin;
	
	public Long getIdXml() {
		return idXml;
	}
	public void setIdXml(Long idXml) {
		this.idXml = idXml;
	}
	public String getWso2Id() {
		return wso2Id;
	}
	public void setWso2Id(String wso2Id) {
		this.wso2Id = wso2Id;
	}
	public String getServizioXml() {
		return servizioXml;
	}
	public void setServizioXml(String servizioXml) {
		this.servizioXml = servizioXml;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getChiamante() {
		return chiamante;
	}
	public void setChiamante(String chiamante) {
		this.chiamante = chiamante;
	}
	public String getStatoXml() {
		return statoXml;
	}
	public void setStatoXml(String statoXml) {
		this.statoXml = statoXml;
	}
	public Timestamp getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Timestamp dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Timestamp getDataRisposta() {
		return dataRisposta;
	}
	public void setDataRisposta(Timestamp dataRisposta) {
		this.dataRisposta = dataRisposta;
	}
	public Timestamp getDataIns() {
		return dataIns;
	}
	public void setDataIns(Timestamp dataIns) {
		this.dataIns = dataIns;
	}
	public Timestamp getDataMod() {
		return dataMod;
	}
	public void setDataMod(Timestamp dataMod) {
		this.dataMod = dataMod;
	}
	public String getCfAssistito() {
		return cfAssistito;
	}
	public void setCfAssistito(String cfAssistito) {
		this.cfAssistito = cfAssistito;
	}
	public String getCfUtente() {
		return cfUtente;
	}
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}
	public String getRuoloUtente() {
		return ruoloUtente;
	}
	public void setRuoloUtente(String ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}
	public Timestamp getDataInvioServizio() {
		return dataInvioServizio;
	}
	public void setDataInvioServizio(Timestamp dataInvioServizio) {
		this.dataInvioServizio = dataInvioServizio;
	}
	public Timestamp getDataRispostaServizio() {
		return dataRispostaServizio;
	}
	public void setDataRispostaServizio(Timestamp dataRispostaServizio) {
		this.dataRispostaServizio = dataRispostaServizio;
	}
	public String getCodEsitoRispostaEervizio() {
		return codEsitoRispostaEervizio;
	}
	public void setCodEsitoRispostaEervizio(String codEsitoRispostaEervizio) {
		this.codEsitoRispostaEervizio = codEsitoRispostaEervizio;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public String getIpRichiedente() {
		return ipRichiedente;
	}
	public void setIpRichiedente(String ipRichiedente) {
		this.ipRichiedente = ipRichiedente;
	}
	public String getIdMessaggioOrigin() {
		return idMessaggioOrigin;
	}
	public void setIdMessaggioOrigin(String idMessaggioOrigin) {
		this.idMessaggioOrigin = idMessaggioOrigin;
	}
	
	
	
}
