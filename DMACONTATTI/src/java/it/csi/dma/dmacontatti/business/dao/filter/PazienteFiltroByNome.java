/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.filter;

import java.util.Date;

public class PazienteFiltroByNome {
    private String cognome;
    private String nome;
    private Date dataNascita;
    private Long idComuneNascita;
    private Long idStatoNascita;
    private Integer maxNumRecord;
    private String email;
    private String numeroTelefono;
    private String codFiscMMG;

    public PazienteFiltroByNome(String cognome, String nome, Date dataNascita,
            Long idComuneNascita, Long idStatoNascita, Integer maxNumRecord,
            String email, String numeroTelefono) {
        super();
        this.cognome = cognome;
        this.nome = nome;
        this.dataNascita = dataNascita;
        this.idComuneNascita = idComuneNascita;
        this.idStatoNascita = idStatoNascita;
        this.maxNumRecord = maxNumRecord;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
    }

    public PazienteFiltroByNome() {
        super();
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome
     *            the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * @param dataNascita
     *            the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * @return the idComuneNascita
     */
    public Long getIdComuneNascita() {
        return idComuneNascita;
    }

    /**
     * @param idComuneNascita
     *            the idComuneNascita to set
     */
    public void setIdComuneNascita(Long idComuneNascita) {
        this.idComuneNascita = idComuneNascita;
    }

    /**
     * @return the idStatoNascita
     */
    public Long getIdStatoNascita() {
        return idStatoNascita;
    }

    /**
     * @param idStatoNascita
     *            the idStatoNascita to set
     */
    public void setIdStatoNascita(Long idStatoNascita) {
        this.idStatoNascita = idStatoNascita;
    }

    /**
     * @return the maxNumRecord
     */
    public Integer getMaxNumRecord() {
        return maxNumRecord;
    }

    /**
     * @param maxNumRecord
     *            the maxNumRecord to set
     */
    public void setMaxNumRecord(Integer maxNumRecord) {
        this.maxNumRecord = maxNumRecord;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the numeroTelefono
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * @param numeroTelefono
     *            the numeroTelefono to set
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCodFiscMMG() {
        return codFiscMMG;
    }

    public void setCodFiscMMG(String codFiscMMG) {
        this.codFiscMMG = codFiscMMG;
    }

}
