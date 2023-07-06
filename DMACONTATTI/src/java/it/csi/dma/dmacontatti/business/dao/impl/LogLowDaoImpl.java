/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.LogLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.LogLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.LogLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.LogLowDaoException;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*PROTECTED REGION ID(R2085867785) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class LogLowDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<LogLowDto>, LogLowDao {
	private static String APPLICATION_CODE_AUDIT = "AUDIT";
	protected static Logger log = Logger.getLogger(APPLICATION_CODE_AUDIT);
	
	protected String tableName;//was "DMACL_T_LOG"
	/**
	 * @generated
	 */
	protected NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @generated
	 */
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return LogLowPk
	 * @generated
	 */

	public LogLowPk insert(LogLowDto dto)

	{
		Long newKey = new Long(incrementer.nextLongValue());
		
		final String sql = "INSERT INTO "
				+ getTableName()
				+ " ( 	ID,ID_TRANSAZIONE,CODICE_TOKEN_OPERAZIONE,INFORMAZIONI_TRACCIATE,DATA_INSERIMENTO,ID_PAZIENTE,ID_CATALOGO_LOG,CODICE_SERVIZIO )"
				+ " VALUES (  :ID , :ID_TRANSAZIONE , :CODICE_TOKEN_OPERAZIONE , :INFORMAZIONI_TRACCIATE , :DATA_INSERIMENTO , :ID_PAZIENTE , :ID_CATALOGO_LOG,:CODICE_SERVIZIO  )";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("ID", newKey, java.sql.Types.BIGINT);
		params.addValue("ID_TRANSAZIONE", dto.getIdTransazione(),
				java.sql.Types.VARCHAR);
		params.addValue("CODICE_TOKEN_OPERAZIONE",
				dto.getCodiceTokenOperazione(), java.sql.Types.VARCHAR);
		
		String informazioniTracciate = dto.getInformazioniTracciate();
		params.addValue("INFORMAZIONI_TRACCIATE",
				informazioniTracciate!=null?informazioniTracciate:" - ", java.sql.Types.VARCHAR);
		params.addValue("DATA_INSERIMENTO", dto.getDataInserimento(),
				java.sql.Types.TIMESTAMP);
		params.addValue("ID_PAZIENTE", dto.getIdPaziente(),
				java.sql.Types.BIGINT);
		params.addValue("ID_CATALOGO_LOG", dto.getIdCatalogoLog(),
				java.sql.Types.BIGINT);
		params.addValue("CODICE_SERVIZIO", dto.getCodiceServizio(),
				java.sql.Types.VARCHAR);
		StopWatch stopWatch = new StopWatch(APPLICATION_CODE_AUDIT);
		try {
			stopWatch.start();
			jdbcTemplate.update(sql.toString(), params);
		} catch (RuntimeException ex) {
			log.error("[LogLowDaoImpl::insert] esecuzione query", ex);
			throw ex;
		} finally {
			stopWatch.dumpElapsed("LogLowDaoImpl", "insert",
					"esecuzione query", sql);
			log.debug("[LogLowDaoImpl::insert] END");
		}

		return dto.createPk();

	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return LogLowDto
	 * @generated
	 */
	public LogLowDto mapRow(ResultSet rs, int row) throws SQLException {
		LogLowDto dto = new LogLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	/**
	 * Method 'mapRow_internal'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return LogLowDto
	 * @generated
	 */
	public LogLowDto mapRow_internal(LogLowDto objectToFill, ResultSet rs,
			int row) throws SQLException {
		LogLowDto dto = objectToFill;

		dto.setId(rs.getLong("ID"));

		dto.setIdTransazione(rs.getString("ID_TRANSAZIONE"));

		dto.setCodiceTokenOperazione(rs.getString("CODICE_TOKEN_OPERAZIONE"));

		dto.setInformazioniTracciate(rs.getString("INFORMAZIONI_TRACCIATE"));

		dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

		dto.setIdPaziente(rs.getLong("ID_PAZIENTE"));

		dto.setIdCatalogoLog(rs.getLong("ID_CATALOGO_LOG"));
		
		dto.setCodiceServizio(rs.getString("CODICE_SERVIZIO"));

		return dto;
	}

	protected LogLowDaoRowMapper findAllRowMapper = new LogLowDaoRowMapper(null);

	protected LogLowDaoRowMapper findByPrimaryKeyRowMapper = new LogLowDaoRowMapper(
			null);

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 * @generated
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Returns all rows from the DMACL_T_LOG table that match the criteria ''.
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<LogLowDto> findAll() throws LogLowDaoException {
		final StringBuilder sql = new StringBuilder(
				"SELECT ID,ID_TRANSAZIONE,CODICE_TOKEN_OPERAZIONE,INFORMAZIONI_TRACCIATE,DATA_INSERIMENTO,ID_PAZIENTE,ID_CATALOGO_LOG FROM "
						+ getTableName());

		MapSqlParameterSource params = new MapSqlParameterSource();

		List<LogLowDto> list = null;

		StopWatch stopWatch = new StopWatch(APPLICATION_CODE_AUDIT);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), params, findAllRowMapper);

		} catch (RuntimeException ex) {
			log.error("[LogLowDaoImpl::findAll] esecuzione query", ex);
			throw new LogLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("LogLowDaoImpl", "findAll",
					"esecuzione query", sql.toString());
			log.debug("[LogLowDaoImpl::findAll] END");
		}

		return list;

	}

	/**
	 * Returns all rows from the DMACL_T_LOG table that match the primary key
	 * criteria
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public LogLowDto findByPrimaryKey(LogLowPk pk) throws LogLowDaoException {

		final StringBuilder sql = new StringBuilder(
				"SELECT ID,ID_TRANSAZIONE,CODICE_TOKEN_OPERAZIONE,INFORMAZIONI_TRACCIATE,DATA_INSERIMENTO,ID_PAZIENTE,ID_CATALOGO_LOG FROM "
						+ getTableName() + " WHERE ID = :ID ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ID", pk.getId(), java.sql.Types.BIGINT);

		List<LogLowDto> list = null;

		StopWatch stopWatch = new StopWatch(APPLICATION_CODE_AUDIT);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), params,
					findByPrimaryKeyRowMapper);

		} catch (RuntimeException ex) {
			log.error("[LogLowDaoImpl::findByPrimaryKey] esecuzione query", ex);
			throw new LogLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("LogLowDaoImpl", "findByPrimaryKey",
					"esecuzione query", sql.toString());
			log.debug("[LogLowDaoImpl::findByPrimaryKey] END");
		}

		return list.size() == 0 ? null : list.get(0);

	}

	// / flexible row mapper.
	public class LogLowDaoRowMapper implements
			org.springframework.jdbc.core.RowMapper {

		private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
		private boolean mapAllColumns = true;

		/**
		 * @param columnsToRead
		 *            elenco delle colonne da includere nel mapping (per query
		 *            incomplete, esempio distinct, custom select...)
		 */
		public LogLowDaoRowMapper(String[] columnsToRead) {
			if (columnsToRead != null) {
				mapAllColumns = false;
				for (int i = 0; i < columnsToRead.length; i++)
					columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
			}
		}

		/**
		 * Method 'mapRow'
		 * 
		 * @param rs
		 * @param row
		 * @throws SQLException
		 * @return LogLowDto
		 * @generated
		 */
		public LogLowDto mapRow(ResultSet rs, int row) throws SQLException {
			LogLowDto dto = new LogLowDto();
			dto = mapRow_internal(dto, rs, row);
			return dto;
		}

		/**
		 * Method 'mapRow_internal'
		 * 
		 * @param rs
		 * @param row
		 * @throws SQLException
		 * @return LogLowDto
		 * @generated
		 */
		public LogLowDto mapRow_internal(LogLowDto objectToFill, ResultSet rs,
				int row) throws SQLException

		{
			LogLowDto dto = objectToFill;

			if (mapAllColumns || columnsToReadMap.get("ID") != null)
				dto.setId(rs.getLong("ID"));

			if (mapAllColumns || columnsToReadMap.get("ID_TRANSAZIONE") != null)
				dto.setIdTransazione(rs.getString("ID_TRANSAZIONE"));

			if (mapAllColumns
					|| columnsToReadMap.get("CODICE_TOKEN_OPERAZIONE") != null)
				dto.setCodiceTokenOperazione(rs
						.getString("CODICE_TOKEN_OPERAZIONE"));

			if (mapAllColumns
					|| columnsToReadMap.get("INFORMAZIONI_TRACCIATE") != null)
				dto.setInformazioniTracciate(rs
						.getString("INFORMAZIONI_TRACCIATE"));

			if (mapAllColumns
					|| columnsToReadMap.get("DATA_INSERIMENTO") != null)
				dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

			if (mapAllColumns || columnsToReadMap.get("ID_PAZIENTE") != null)
				dto.setIdPaziente(rs.getLong("ID_PAZIENTE"));

			if (mapAllColumns
					|| columnsToReadMap.get("ID_CATALOGO_LOG") != null)
				dto.setIdCatalogoLog(rs.getLong("ID_CATALOGO_LOG"));
			
			if (mapAllColumns
					|| columnsToReadMap.get("CODICE_SERVIZIO") != null)
				dto.setCodiceServizio(rs
						.getString("CODICE_SERVIZIO"));
			
			

			return dto;
		}

	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
