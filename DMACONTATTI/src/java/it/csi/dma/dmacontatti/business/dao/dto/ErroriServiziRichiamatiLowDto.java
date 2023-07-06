/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.dto;

import java.io.Serializable;
import java.sql.Timestamp;

//SB_FASE_3_PREGRESSO
public class ErroriServiziRichiamatiLowDto implements Serializable {

    /**
     * @generated
     */
	private Long id;
    private Long idServizioRichiamato;
    private Long idCatalogoLog;
    private String codiceErrore;
    private String descrizioneErrore;
    private String controllore;
    private String informazioni_aggiuntive;
    private Timestamp dataInserimento;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}





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
     * @return ConsensoLowPk
     * @generated
     */
     public final ErroriServiziRichiamatiLowPk createPk() {
        return new ErroriServiziRichiamatiLowPk(id);
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

	public Long getIdServizioRichiamato() {
		return idServizioRichiamato;
	}

	public void setIdServizioRichiamato(Long idServizioRichiamato) {
		this.idServizioRichiamato = idServizioRichiamato;
	}

	public Long getIdCatalogoLog() {
		return idCatalogoLog;
	}

	public void setIdCatalogoLog(Long idCatalogoLog) {
		this.idCatalogoLog = idCatalogoLog;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public String getControllore() {
		return controllore;
	}

	public void setControllore(String controllore) {
		this.controllore = controllore;
	}

	public String getInformazioni_aggiuntive() {
		return informazioni_aggiuntive;
	}

	public void setInformazioni_aggiuntive(String informazioni_aggiuntive) {
		this.informazioni_aggiuntive = informazioni_aggiuntive;
	}

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
}
