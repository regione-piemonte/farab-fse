/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.log;


import it.csi.dma.dmacontatti.business.dao.dto.*;

import java.util.List;


/*
 * Classe contenente i Dto utili per l'inserimento dei log
 */
public class LogGeneralDaoBean {

	LogLowDto logDto;
	
	MessaggiLowDto messaggiDto;
	
	XmlMessaggiLowDto messaggiXmlDto;
	
	List<ErroriLowDto> messaggiErroreDto;

	ServiziRichiamatiLowDto serviziRichiamatiLowDto;

	
	public LogGeneralDaoBean() {}

	public LogGeneralDaoBean(LogLowDto logDto, MessaggiLowDto messaggiDto,
                             XmlMessaggiLowDto messaggiXmlDto, List<ErroriLowDto> messaggiErroreDto) {
		super();
		this.logDto = logDto;
		this.messaggiDto = messaggiDto;
		this.messaggiXmlDto = messaggiXmlDto;
		this.messaggiErroreDto = messaggiErroreDto;
	}

	public LogLowDto getLogDto() {
		return logDto;
	}

	public void setLogDto(LogLowDto logDto) {
		this.logDto = logDto;
	}

	public MessaggiLowDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiLowDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public XmlMessaggiLowDto getMessaggiXmlDto() {
		return messaggiXmlDto;
	}

	public void setMessaggiXmlDto(XmlMessaggiLowDto messaggiXmlDto) {
		this.messaggiXmlDto = messaggiXmlDto;
	}

	public List<ErroriLowDto> getMessaggiErroreDto() {
		return messaggiErroreDto;
	}

	public void setMessaggiErroreDto(List<ErroriLowDto> messaggiErroreDto) {
		this.messaggiErroreDto = messaggiErroreDto;
	}

	public ServiziRichiamatiLowDto getServiziRichiamatiLowDto() {
		return serviziRichiamatiLowDto;
	}

	public void setServiziRichiamatiLowDto(ServiziRichiamatiLowDto serviziRichiamatiLowDto) {
		this.serviziRichiamatiLowDto = serviziRichiamatiLowDto;
	}
}
