/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.domain.DmaccDCredenzialiServizi;
import it.csi.dma.farmab.integration.dao.rowmapper.DmaccDCredenzialiServiziRowMapper;
import it.csi.dma.farmab.util.NamedParameterJdbcTemplateWithQueryDebug;

@Repository
public class FarmabDCredenzialiServiziDao extends JdbcDaoSupport {

	@Autowired
	private NamedParameterJdbcTemplateWithQueryDebug namedJdbcTemplate;
	private static final String SELECT_ALL = "SELECT id, codice_servizio, username, "
				+ " pgp_sym_decrypt_bytea(\"password\", (:encryptionkey)) as pwd, data_inizio_validita, data_fine_validita, data_inserimento ";
	private static final String FROM = " FROM dmacc_d_credenziali_servizi ";

	@Value("${encryptionkey}")
	private String encryptionkey;


	@Autowired
	FarmabDCredenzialiServiziDao(DataSource dataSource){

		setDataSource(dataSource);
	}

	@Transactional(readOnly = true)
	public List<DmaccDCredenzialiServizi> getcredenzialiFromCodiceServizio(String codiceServizio){

		List<DmaccDCredenzialiServizi> lista = new ArrayList<>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(SELECT_ALL);
			sql.append(FROM);


			sql.append(" WHERE codice_servizio = (:CODICE_SERVIZIO) and data_fine_validita is null");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("encryptionkey", encryptionkey);
			paramSource.addValue("CODICE_SERVIZIO", codiceServizio);
			lista = namedJdbcTemplate.query(sql.toString(), paramSource, new DmaccDCredenzialiServiziRowMapper());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}
}
