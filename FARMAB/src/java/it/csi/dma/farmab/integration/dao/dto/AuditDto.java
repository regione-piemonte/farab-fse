/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class AuditDto implements Serializable {
	
	private Long _id;
	private Long idDocumento;
	private String visibileAlCittadino;
	private String componenteLocale;
	private String nomeServizio;
	private String codiceServizio;
	private String ipChiamante;
	private String cfUtente;
	private String encryptKey;
	private byte[] cfUtenteByte;
	private Long idContestoOperativo;
	private String applicativoVerticale;
	private String codiceFiscaleAssistito;
	private String _codiceTokenOperazione;
	private String _informazioniTracciate;
	private Long _idPaziente;
	private Long _idCatalogoLogAudit;
	private String _idTransazione;
	private Timestamp _dataInserimento;
	private Long _idRegime;
	private Long _idRuolo;
	private String _codiceFiscaleUtente;
	private String collocazione;
	private String codiceApplicazioneRichiedente;
	private Long idUtente;
	private String consolidato;
	private String _codiceRuolo;



	public String getApplicativoVerticale() {
		return this.applicativoVerticale;
	}

	public void setApplicativoVerticale(String applicativoVerticale) {
		this.applicativoVerticale = applicativoVerticale;
	}

	public String getCodiceServizio() {
		return this.codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

	public Long getIdContestoOperativo() {
		return this.idContestoOperativo;
	}

	public void setIdContestoOperativo(Long idContestoOperativo) {
		this.idContestoOperativo = idContestoOperativo;
	}



	public byte[] getCfUtenteByte() {
		return this.cfUtenteByte;
	}

	public void setCfUtenteByte(byte[] cfUtenteByte) {
		this.cfUtenteByte = cfUtenteByte;
	}

	public String getEncryptKey() {
		return this.encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getCfUtente() {
		return this.cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getNomeServizio() {
		return this.nomeServizio;
	}

	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}

	public String getIpChiamante() {
		return this.ipChiamante;
	}

	public void setIpChiamante(String ipChiamante) {
		this.ipChiamante = ipChiamante;
	}

	public Long getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getVisibileAlCittadino() {
		return this.visibileAlCittadino;
	}

	public void setVisibileAlCittadino(String visibileAlCittadino) {
		this.visibileAlCittadino = visibileAlCittadino;
	}

	public void setId(Long val) {
		this._id = val;
	}

	public Long getId() {
		return this._id;
	}

	public void setCodiceTokenOperazione(String val) {
		this._codiceTokenOperazione = val;
	}

	public String getCodiceTokenOperazione() {
		return this._codiceTokenOperazione;
	}

	public void setInformazioniTracciate(String val) {
		this._informazioniTracciate = val;
	}

	public String getInformazioniTracciate() {
		return this._informazioniTracciate;
	}

	public void setIdPaziente(Long val) {
		this._idPaziente = val;
	}

	public Long getIdPaziente() {
		return this._idPaziente;
	}

	public void setIdCatalogoLogAudit(Long val) {
		this._idCatalogoLogAudit = val;
	}

	public Long getIdCatalogoLogAudit() {
		return this._idCatalogoLogAudit;
	}

	public void setIdTransazione(String val) {
		this._idTransazione = val;
	}

	public String getIdTransazione() {
		return this._idTransazione;
	}



	public void setIdRegime(Long val) {
		this._idRegime = val;
	}

	public Long getIdRegime() {
		return this._idRegime;
	}

	public Long getIdRuolo() {
		return this._idRuolo;
	}

	public void setIdRuolo(Long _idRuolo) {
		this._idRuolo = _idRuolo;
	}

	public void setCodiceFiscaleUtente(String val) {
		this._codiceFiscaleUtente = val;
	}

	public String getCodiceFiscaleUtente() {
		return this._codiceFiscaleUtente;
	}

	public void setCollocazione(String val) {
		this.collocazione = val;
	}

	public String getCollocazione() {
		return this.collocazione;
	}

	

	public String getCodiceApplicazioneRichiedente() {
		return codiceApplicazioneRichiedente;
	}

	public void setCodiceApplicazioneRichiedente(String codiceApplicazioneRichiedente) {
		this.codiceApplicazioneRichiedente = codiceApplicazioneRichiedente;
	}

	public Long getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getConsolidato() {
		return this.consolidato;
	}

	public void setConsolidato(String consolidato) {
		this.consolidato = consolidato;
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

	public void setCodiceRuolo(String val) {
		this._codiceRuolo = val;
	}

	public String getCodiceRuolo() {
		return this._codiceRuolo;
	}

	public String getComponenteLocale() {
		return this.componenteLocale;
	}

	public void setComponenteLocale(String componenteLocale) {
		this.componenteLocale = componenteLocale;
	}

	public String getCodiceFiscaleAssistito() {
		return this.codiceFiscaleAssistito;
	}

	public void setCodiceFiscaleAssistito(String codiceFiscaleAssistito) {
		this.codiceFiscaleAssistito = codiceFiscaleAssistito;
	}
}

