/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.ServiziEsterniLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ServiziEsterniLowDto;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ServiziEsterniLowDaoImpl extends AbstractDAO implements
ParameterizedRowMapper<ServiziEsterniLowDto>,
		ServiziEsterniLowDao {

	/** The Constant APPLICATION_CODE. */
	private static final String APPLICATION_CODE = "dmaccbl";

	/** The log. */
	protected static Logger log = Logger.getLogger(APPLICATION_CODE);

	/** The data source. */
	private DataSource dataSource;
	
	/** The jdbc template. */
	protected NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate the new jdbc template
	 */
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public ServiziEsterniLowDto mapRow(ResultSet rs, int row) throws SQLException {
		ServiziEsterniLowDto dto = new ServiziEsterniLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	/**
	 * Map row internal.
	 *
	 * @param objectToFill the object to fill
	 * @param rs the rs
	 * @param row the row
	 * @return the autorizzazione deleghe low dto
	 * @throws SQLException the SQL exception
	 */
	public ServiziEsterniLowDto mapRow_internal(ServiziEsterniLowDto objectToFill, ResultSet rs, int row)
			throws SQLException {
		ServiziEsterniLowDto dto = objectToFill;

		ServiziEsterniLowDaoRowMapper ServiziEsterniLowDaoRowMapper = new ServiziEsterniLowDaoRowMapper(
				null, ServiziEsterniLowDto.class);

		return ServiziEsterniLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
	}

	/** The find by codice row mapper. */
	protected ServiziEsterniLowDaoRowMapper findByCodiceRowMapper = new ServiziEsterniLowDaoRowMapper(
			null, ServiziEsterniLowDto.class);

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		return "DMACC_D_SERVIZI_ESTERNI";
	}

	
	
	@SuppressWarnings("unchecked")
	public ServiziEsterniLowDto findByCodiceServizio(String codiceServizio) throws Exception {
		final StringBuilder sql = new StringBuilder(
				"SELECT  ID, CODICE, DESCRIZIONE, DATA_INSERIMENTO, DATA_AGGIORNAMENTO  FROM "
						+ getTableName());
		sql.append(" WHERE ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		sql.append(" CODICE = :CODICE_SERVIZIO ");
	

		params.addValue("CODICE_SERVIZIO", codiceServizio);

		List<ServiziEsterniLowDto> list = null;

		
		try {
			list = jdbcTemplate.query(sql.toString(), params, findByCodiceRowMapper);
		} catch (RuntimeException ex) {
			log.error("[ServiziEsterniLowDaoImpl::findByCodice] esecuzione query", ex);
			throw new Exception("Query failed", ex);
		} finally {
			log.debug("[ServiziEsterniLowDaoImpl::findByCodice] END");
		}
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * The Class ServiziEsterniLowDaoRowMapper.
	 */
	public class ServiziEsterniLowDaoRowMapper implements org.springframework.jdbc.core.RowMapper {

		/** The columns to read map. */
		private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
		
		/** The map all columns. */
		private boolean mapAllColumns = true;
		
		/** The dto class. */
		private Class dtoClass;

		/**
		 * Instantiates a new autorizzazione deleghe low dao row mapper.
		 *
		 * @param columnsToRead the columns to read
		 * @param dtoClass the dto class
		 */
		public ServiziEsterniLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
			if (columnsToRead != null) {
				mapAllColumns = false;
				for (int i = 0; i < columnsToRead.length; i++)
					columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
			}
			this.dtoClass = dtoClass;
		}

		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public Object mapRow(ResultSet rs, int row) throws SQLException {

			Object dto = null;
			try {
				dto = dtoClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Impossibile istanziare la classe " + dto.getClass().getCanonicalName() + " ," + e.getCause());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Impossibile accedere alla classe " + dto.getClass().getCanonicalName() + " ," + e.getCause());
			}

			if (dto instanceof ServiziEsterniLowDto) {
				return mapRow_internal((ServiziEsterniLowDto) dto, rs, row);
			}

			return dto;
		}

		/**
		 * Map row internal.
		 *
		 * @param objectToFill the object to fill
		 * @param rs the rs
		 * @param row the row
		 * @return the autorizzazione deleghe low dto
		 * @throws SQLException the SQL exception
		 */
		public ServiziEsterniLowDto mapRow_internal(ServiziEsterniLowDto objectToFill, ResultSet rs,
				int row) throws SQLException

		{
			
			ServiziEsterniLowDto dto = objectToFill;

			if (mapAllColumns || columnsToReadMap.get("ID") != null)
				dto.setId((Long) rs.getObject("ID"));

			if (mapAllColumns || columnsToReadMap.get("CODICE") != null)
				dto.setCodice(rs.getString("CODICE"));

			if (mapAllColumns || columnsToReadMap.get("DESCRIZIONE") != null)
				dto.setDescrizione(rs.getString("DESCRIZIONE"));
			
			if (mapAllColumns || columnsToReadMap.get("DATA_INSERIMENTO") != null)
				dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

			if (mapAllColumns || columnsToReadMap.get("DATA_AGGIORNAMENTO") != null)
				dto.setDataAggiornamento(rs.getTimestamp("DATA_AGGIORNAMENTO"));
			return dto;
		}

	}

	/**
	 * Checks for column.
	 *
	 * @param rs the rs
	 * @param columnName the column name
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
