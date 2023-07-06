/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.CatalogoServiziOperationLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.CatalogoServiziOperationLowDto;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServiziOperationLowDaoImpl extends AbstractDAO implements
ParameterizedRowMapper<CatalogoServiziOperationLowDto>,
		CatalogoServiziOperationLowDao {

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
	public CatalogoServiziOperationLowDto mapRow(ResultSet rs, int row) throws SQLException {
		CatalogoServiziOperationLowDto dto = new CatalogoServiziOperationLowDto();
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
	public CatalogoServiziOperationLowDto mapRow_internal(CatalogoServiziOperationLowDto objectToFill, ResultSet rs, int row)
			throws SQLException {
		CatalogoServiziOperationLowDto dto = objectToFill;

		CatalogoServiziOperationLowDaoRowMapper CatalogoServiziOperationLowDaoRowMapper = new CatalogoServiziOperationLowDaoRowMapper(
				null, CatalogoServiziOperationLowDto.class);

		return CatalogoServiziOperationLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
	}

	/** The find by codice row mapper. */
	protected CatalogoServiziOperationLowDaoRowMapper findByCodiceRowMapper = new CatalogoServiziOperationLowDaoRowMapper(
			null, CatalogoServiziOperationLowDto.class);

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		return "DMACC_D_CATALOGO_SERVIZI_OPERATION";
	}

	/**
	 * Find by codice.
	 *
	 * @param input the input
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public CatalogoServiziOperationLowDto findByNomeSevrvizioAndNomeOperation(CatalogoServiziOperationLowDto input) throws Exception {
		final StringBuilder sql = new StringBuilder(
				"SELECT  CODICE_SERVIZIO, NOME_SERVIZIO, NOME_OPERATION  FROM "
						+ getTableName());
		sql.append(" WHERE ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		sql.append(" NOME_SERVIZIO = :NOME_SERVIZIO ");
		sql.append(" AND NOME_OPERATION= :NOME_OPERATION");

		params.addValue("NOME_SERVIZIO", input.getNomeServizio());
		params.addValue("NOME_OPERATION", input.getNomeOperation());

		
		List<CatalogoServiziOperationLowDto> list = null;

		
		try {
			list = jdbcTemplate.query(sql.toString(), params, findByCodiceRowMapper);
		} catch (RuntimeException ex) {
			log.error("[CatalogoServiziOperationLowDaoImpl::findByCodice] esecuzione query", ex);
			throw new Exception("Query failed", ex);
		} finally {
			log.debug("[CatalogoServiziOperationLowDaoImpl::findByCodice] END");
		}
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}
	
	
	@SuppressWarnings("unchecked")
	public CatalogoServiziOperationLowDto findByCodiceServizio(CatalogoServiziOperationLowDto input) throws Exception {
		final StringBuilder sql = new StringBuilder(
				"SELECT  CODICE_SERVIZIO, NOME_SERVIZIO, NOME_OPERATION  FROM "
						+ getTableName());
		sql.append(" WHERE ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		sql.append(" CODICE_SERVIZIO = :CODICE_SERVIZIO ");
	

		params.addValue("CODICE_SERVIZIO", input.getCodiceServizio());
		

		
		List<CatalogoServiziOperationLowDto> list = null;

		
		try {
			list = jdbcTemplate.query(sql.toString(), params, findByCodiceRowMapper);
		} catch (RuntimeException ex) {
			log.error("[CatalogoServiziOperationLowDaoImpl::findByCodice] esecuzione query", ex);
			throw new Exception("Query failed", ex);
		} finally {
			log.debug("[CatalogoServiziOperationLowDaoImpl::findByCodice] END");
		}
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
		
		
		
		
//		 final StringBuilder sql = new StringBuilder(
//	                "SELECT ID,CODICE,DESCRIZIONE,DATA_INSERIMENTO,DESCRIZIONE_CODICE,TIPO FROM "
//	                        + getTableName() + " WHERE ID = :ID ");
//
//	        MapSqlParameterSource params = new MapSqlParameterSource();
//
//	        params.addValue("ID", pk.getId(), java.sql.Types.BIGINT);
//
//	        List<CatalogoLogAuditCCLowDto> list = null;
//
//	        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
//	        try {
//	            stopWatch.start();
//	            list = jdbcTemplate.query(sql.toString(), params,
//	                    findByPrimaryKeyRowMapper);
//	        } catch (RuntimeException ex) {
//	            log.error(
//	                    "[CatalogoLogAuditCCLowDaoImpl::findByPrimaryKey] esecuzione query",
//	                    ex);
//	            throw new CatalogoLogAuditCCLowDaoException("Query failed", ex);
//	        } finally {
//	            stopWatch.dumpElapsed("CatalogoLogAuditCCLowDaoImpl",
//	                    "findByPrimaryKey", "esecuzione query", sql.toString());
//	            log.debug("[CatalogoLogAuditCCLowDaoImpl::findByPrimaryKey] END");
//	        }
//
//	        return list.size() == 0 ? null : list.get(0);
		
		
		
		
		
		

	}

	/**
	 * The Class CatalogoServiziOperationLowDaoRowMapper.
	 */
	public class CatalogoServiziOperationLowDaoRowMapper implements org.springframework.jdbc.core.RowMapper {

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
		public CatalogoServiziOperationLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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

			if (dto instanceof CatalogoServiziOperationLowDto) {
				return mapRow_internal((CatalogoServiziOperationLowDto) dto, rs, row);
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
		public CatalogoServiziOperationLowDto mapRow_internal(CatalogoServiziOperationLowDto objectToFill, ResultSet rs,
				int row) throws SQLException

		{
			
			CatalogoServiziOperationLowDto dto = objectToFill;


			if (mapAllColumns || columnsToReadMap.get("CODICE_SERVIZIO") != null)
				dto.setCodiceServizio(rs.getString("CODICE_SERVIZIO"));

			if (mapAllColumns || columnsToReadMap.get("NOME_SERVIZIO") != null)
				dto.setNomeServizio(rs.getString("NOME_SERVIZIO"));
			
			if (mapAllColumns || columnsToReadMap.get("NOME_OPERATION") != null)
				dto.setNomeOperation(rs.getString("NOME_OPERATION"));
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
