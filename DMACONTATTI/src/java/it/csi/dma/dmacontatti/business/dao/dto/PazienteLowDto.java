/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;

/**
 * @generated
 */
public class PazienteLowDto implements Serializable {

    /**
     * @generated
     */
    private Long idPaziente;

    public Long getIdPaziente() {
	return idPaziente;
    }

    public void setIdPaziente(Long idPaziente) {
	this.idPaziente = idPaziente;
    }

    private Long idPazienteRicondotto;

    public Long getIdPazienteRicondotto() {
	return idPazienteRicondotto;
    }

    public void setIdPazienteRicondotto(Long idPazienteRicondotto) {
	this.idPazienteRicondotto = idPazienteRicondotto;
    }

    /**
     * @generated
     */
    private Long idAura;

    /**
     * @generated
     */
    public final void setIdAura(Long val) {
	this.idAura = val;
    }

    /**
     * @generated
     */
    public final Long getIdAura() {
	return this.idAura;
    }

    /**
     * @generated
     */
    private String cognome;

    /**
     * @generated
     */
    public final void setCognome(String val) {
	this.cognome = val;
    }

    /**
     * @generated
     */
    public final String getCognome() {
	return this.cognome;
    }

    /**
     * @generated
     */
    private String nome;

    /**
     * @generated
     */
    public final void setNome(String val) {
	this.nome = val;
    }

    /**
     * @generated
     */
    public final String getNome() {
	return this.nome;
    }

    /**
     * @generated
     */
    private java.sql.Timestamp dataRiconduzione;

    public java.sql.Timestamp getDataRiconduzione() {
	return dataRiconduzione;
    }

    public void setDataRiconduzione(java.sql.Timestamp dataRiconduzione) {
	this.dataRiconduzione = dataRiconduzione;
    }

    private java.sql.Date dataNascita;

    /**
     * @generated
     */
    public final void setDataNascita(java.sql.Date val) {
	this.dataNascita = val;
    }

    /**
     * @generated
     */
    public final java.sql.Date getDataNascita() {
	return this.dataNascita;
    }

    /**
     * @generated
     */
    private String codiceFiscale;

    /**
     * @generated
     */
    public final void setCodiceFiscale(String val) {
	this.codiceFiscale = val;
    }

    /**
     * @generated
     */
    public final String getCodiceFiscale() {
	return this.codiceFiscale;
    }

    /**
     * @generated
     */
    private String sesso;

    /**
     * @generated
     */
    public final void setSesso(String val) {
	this.sesso = val;
    }

    /**
     * @generated
     */
    public final String getSesso() {
	return this.sesso;
    }

    /**
     * @generated
     */
    private java.sql.Date dataDecesso;

    /**
     * @generated
     */
    public final void setDataDecesso(java.sql.Date val) {
	this.dataDecesso = val;
    }

    /**
     * @generated
     */
    public final java.sql.Date getDataDecesso() {
	return this.dataDecesso;
    }

    /**
     * @generated
     */
    private java.sql.Timestamp dataInserimento;

    /**
     * @generated
     */
    public final void setDataInserimento(java.sql.Timestamp val) {
	this.dataInserimento = val;
    }

    /**
     * @generated
     */
    public final java.sql.Timestamp getDataInserimento() {
	return this.dataInserimento;
    }

    /**
     * @generated
     */
    private java.sql.Timestamp dataAggiornamento;

    /**
     * @generated
     */
    public final void setDataAggiornamento(java.sql.Timestamp val) {
	this.dataAggiornamento = val;
    }

    /**
     * @generated
     */
    public final java.sql.Timestamp getDataAggiornamento() {
	return this.dataAggiornamento;
    }

    /**
     * @generated
     */
    private Long idComuneNascita;

    /**
     * @generated
     */
    public final void setIdComuneNascita(Long val) {
	this.idComuneNascita = val;
    }

    /**
     * @generated
     */
    public final Long getIdComuneNascita() {
	return this.idComuneNascita;
    }

    /**
     * @generated
     */
    private Long idStatoNascita;

    /**
     * @generated
     */
    public final void setIdStatoNascita(Long val) {
	this.idStatoNascita = val;
    }

    /**
     * @generated
     */
    public final Long getIdStatoNascita() {
	return this.idStatoNascita;
    }

    /**
     * @generated
     */
    private String indirizzoEmail;

    /**
     * @generated
     */
    public final void setIndirizzoEmail(String val) {
	this.indirizzoEmail = val;
    }

    /**
     * @generated
     */
    public final String getIndirizzoEmail() {
	return this.indirizzoEmail;
    }

    /**
     * @generated
     */
    private String numeroTelefono;

    /**
     * @generated
     */
    public final void setNumeroTelefono(String val) {
	this.numeroTelefono = val;
    }

    /**
     * @generated
     */
    public final String getNumeroTelefono() {
	return this.numeroTelefono;
    }

    /**
     * @generated
     */
    private String flagEmailAccesso;

    /**
     * @generated
     */
    public final void setFlagEmailAccesso(String val) {
	this.flagEmailAccesso = val;
    }

    /**
     * @generated
     */
    public final String getFlagEmailAccesso() {
	return this.flagEmailAccesso;
    }

    /**
     * @generated
     */
    private String codiceFiscaleMmg;

    /**
     * @generated
     */
    public final void setCodiceFiscaleMmg(String val) {
	this.codiceFiscaleMmg = val;
    }

    /**
     * @generated
     */
    public final String getCodiceFiscaleMmg() {
	return this.codiceFiscaleMmg;
    }

    /**
     * @generated
     */
    private Long idAuraMmg;

    /**
     * @generated
     */
    public final void setIdAuraMmg(Long val) {
	this.idAuraMmg = val;
    }

    /**
     * @generated
     */
    public final Long getIdAuraMmg() {
	return this.idAuraMmg;
    }

    /**
     * @generated
     */
    private String cognomeMmg;

    /**
     * @generated
     */
    public final void setCognomeMmg(String val) {
	this.cognomeMmg = val;
    }

    /**
     * @generated
     */
    public final String getCognomeMmg() {
	return this.cognomeMmg;
    }

    /**
     * @generated
     */
    private String nomeMmg;

    /**
     * @generated
     */
    public final void setNomeMmg(String val) {
	this.nomeMmg = val;
    }

    /**
     * @generated
     */
    public final String getNomeMmg() {
	return this.nomeMmg;
    }

    /**
     * @generated
     */
    private java.sql.Timestamp dataAnnullamento;

    /**
     * @generated
     */
    public final void setDataAnnullamento(java.sql.Timestamp val) {
	this.dataAnnullamento = val;
    }

    /**
     * @generated
     */
    public final java.sql.Timestamp getDataAnnullamento() {
	return this.dataAnnullamento;
    }

    /**
     * @generated
     */
    private String flagNotificaMmg;

    /**
     * @generated
     */
    public final void setFlagNotificaMmg(String val) {
	this.flagNotificaMmg = val;
    }

    /**
     * @generated
     */
    public final String getFlagNotificaMmg() {
	return this.flagNotificaMmg;
    }

    private String flagRegistryIncaricoMmg;

    public String getFlagRegistryIncaricoMmg() {
	return this.flagRegistryIncaricoMmg;
    }

    public void setFlagRegistryIncaricoMmg(String val) {
	this.flagRegistryIncaricoMmg = val;
    }

    private Long idAsr;

    public Long getIdAsr() {
	return idAsr;
    }

    public void setIdAsr(Long idAsr) {
	this.idAsr = idAsr;
    }

    /**
     * Method 'equals'
     * 
     * @param other
     * @return boolean
     * @generated
     */
    public final boolean equals(Object other) {
	// TODO
	return super.equals(other);
    }

    /**
     * Method 'hashCode'
     * 
     * @return int
     * @generated
     */
    public final int hashCode() {
	// TODO
	return super.hashCode();
    }

    /**
     * Method 'createPk'
     * 
     * @return PazienteLowPk
     * @generated
     */
    public final PazienteLowPk createPk() {
	return new PazienteLowPk(idPaziente);
    }

    /**
     * Method 'toString'
     * 
     * @return String
     * @generated
     */
    public final String toString() {
	// TODO
	return super.toString();
    }

}
