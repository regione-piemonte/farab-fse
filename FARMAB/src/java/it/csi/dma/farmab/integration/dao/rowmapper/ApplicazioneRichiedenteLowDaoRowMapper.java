/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.dma.farmab.integration.dao.dto.ApplicazioneRichiedenteLowDto;

public class ApplicazioneRichiedenteLowDaoRowMapper implements RowMapper<ApplicazioneRichiedenteLowDto> {

	@Override
	public ApplicazioneRichiedenteLowDto mapRow(ResultSet rs, int row) throws SQLException {
		ApplicazioneRichiedenteLowDto dto = new ApplicazioneRichiedenteLowDto();
		dto.setId((Long) rs.getObject("ID"));
		dto.setCodiceApplicazione(rs.getString("CODICE_APPLICAZIONE"));
		dto.setDescrizioneApplicazione(rs
				.getString("DESCRIZIONE_APPLICAZIONE"));
		dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));
		dto.setFlgSupportaRuoloCittadino(rs
				.getString("FLG_SUPPORTA_RUOLO_CITTADINO"));

		return dto;
	}

}
