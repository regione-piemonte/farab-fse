/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.dma.farmab.domain.DmaccDCredenzialiServizi;

public class DmaccDCredenzialiServiziRowMapper implements RowMapper<DmaccDCredenzialiServizi> {

	@Override
	public DmaccDCredenzialiServizi mapRow(ResultSet resultSet, int row) throws SQLException {
		DmaccDCredenzialiServizi servizio = new  DmaccDCredenzialiServizi();
		
		servizio.setId(resultSet.getLong("id") );
		servizio.setUsername(resultSet.getString("username") );
		servizio.setDataFineValidita(resultSet.getTimestamp("data_fine_validita"));
		servizio.setDataInizioValidita(resultSet.getTimestamp("data_inizio_validita"));
		servizio.setDataInserimento(resultSet.getTimestamp("data_inserimento"));
		servizio.setPassword(new String( resultSet.getBytes("pwd")));
		servizio.setCodiceServizio(resultSet.getString("codice_servizio"));
		
		return servizio;
	}

}
