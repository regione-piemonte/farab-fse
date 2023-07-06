/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.interfacews.contatti;

import it.csi.dma.dmacontatti.interfacews.Errore;
import it.csi.dma.dmacontatti.interfacews.RisultatoCodice;
import it.csi.dma.dmacontatti.interfacews.ServiceResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name="generaOtpResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaOtpResponse")
public class GeneraOtpResponse extends ServiceResponse {

    private String otp;

    private String dataInserimento;

    private String dataScadenza;

    public GeneraOtpResponse() {
        super();
    }

    public GeneraOtpResponse(ServiceResponse res) {
        super(res.getErrori(), res.getEsito());
    }

    public GeneraOtpResponse(List<Errore> errori, RisultatoCodice esito) {
        super(errori, esito);
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(String dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
