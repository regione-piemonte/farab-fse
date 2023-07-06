/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.CredenzialiServiziLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.CredenzialiServiziLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import it.csi.util.performance.StopWatch;

/*PROTECTED REGION ID(R-1410310709) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class CredenzialiServiziLowDaoImpl extends AbstractDAO implements ParameterizedRowMapper<CredenzialiServiziLowDto>, CredenzialiServiziLowDao {
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
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



	

	
	public CredenzialiServiziLowDto mapRow(ResultSet rs, int row) throws SQLException {
		CredenzialiServiziLowDto dto = new CredenzialiServiziLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	
	public CredenzialiServiziLowDto mapRow_internal(CredenzialiServiziLowDto objectToFill, ResultSet rs, int row) throws SQLException {
		CredenzialiServiziLowDto dto = objectToFill;

		CredenzialiServiziLowDaoRowMapper credenzialiServiziLowDaoRowMapper = new CredenzialiServiziLowDaoRowMapper(null, CredenzialiServiziLowDto.class);

		return credenzialiServiziLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
	}

	protected CredenzialiServiziLowDaoRowMapper ByCodiceServizioUserPassword = new CredenzialiServiziLowDaoRowMapper(null, CredenzialiServiziLowDto.class);

	

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 * @generated
	 */
	public String getTableName() {
		return "DMACC_D_CREDENZIALI_SERVIZI";
	}

	/**
	 * Implementazione del finder ByIdPaziente
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<CredenzialiServiziLowDto> findByCodiceServizioUserPassword(
    		CredenzialiServiziLowDto input)
			throws CredenzialiServiziLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		
		

		sql.append("SELECT"
				+ "	ID,CODICE_SERVIZIO,USERNAME,(pgp_sym_decrypt(PASSWORD::bytea, '@encryption_key@'))::varchar AS PASSWORD,"
				+ "DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA, "
				+ "DATA_INSERIMENTO");

		sql.append(" FROM DMACC_D_CREDENZIALI_SERVIZI");

		sql.append(" WHERE ");

		sql.append("CODICE_SERVIZIO = :CODICE_SERVIZIO");
		sql.append(" AND USERNAME = :USERNAME ");
		sql.append(" AND (pgp_sym_decrypt(PASSWORD::bytea, '@encryption_key@'))::varchar = :PASSWORD ");
		
		sql.append(" AND DATA_INIZIO_VALIDITA <= now()  ");
		sql.append(" AND (DATA_FINE_VALIDITA >= now() or DATA_FINE_VALIDITA is null)");
		
		
		paramMap.addValue("CODICE_SERVIZIO", input.getCodiceServizio());
		paramMap.addValue("USERNAME", input.getUsername());
		paramMap.addValue("PASSWORD", input.getPassword());
		

		/* PROTECTED REGION END */
		List<CredenzialiServiziLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, ByCodiceServizioUserPassword);

		} catch (RuntimeException ex) {
			log.error("[CredenzialiServiziLowDaoImpl::findByCodiceServizioUserPassword] esecuzione query", ex);
			throw new CredenzialiServiziLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("CredenzialiServiziLowDaoImpl", "findByCodiceServizioUserPassword", "esecuzione query", sql.toString());
			log.debug("[CredenzialiServiziLowDaoImpl::findByCodiceServizioUserPassword] END");
		}
		return list;
	}

	@Override
	public List<CredenzialiServiziLowDto> findByCodiceServizioUser(
			CredenzialiServiziLowDto input)
			throws CredenzialiServiziLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();



		sql.append("SELECT"
				+ "	ID,CODICE_SERVIZIO,USERNAME,(pgp_sym_decrypt(PASSWORD::bytea, '@encryption_key@'))::varchar AS PASSWORD,"
				+ "DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA, "
				+ "DATA_INSERIMENTO");

		sql.append(" FROM DMACC_D_CREDENZIALI_SERVIZI");

		sql.append(" WHERE ");

		sql.append("CODICE_SERVIZIO = :CODICE_SERVIZIO");
		sql.append(" AND USERNAME = :USERNAME ");
//		sql.append(" AND (pgp_sym_decrypt(PASSWORD::bytea, '@encryption_key@'))::varchar = :PASSWORD ");

		sql.append(" AND DATA_INIZIO_VALIDITA <= now()  ");
		sql.append(" AND (DATA_FINE_VALIDITA >= now() or DATA_FINE_VALIDITA is null)");


		paramMap.addValue("CODICE_SERVIZIO", input.getCodiceServizio());
		paramMap.addValue("USERNAME", input.getUsername());
//		paramMap.addValue("PASSWORD", input.getPassword());


		/* PROTECTED REGION END */
		List<CredenzialiServiziLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, ByCodiceServizioUserPassword);

		} catch (RuntimeException ex) {
			log.error("[CredenzialiServiziLowDaoImpl::findByCodiceServizioUserPassword] esecuzione query", ex);
			throw new CredenzialiServiziLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("CredenzialiServiziLowDaoImpl", "findByCodiceServizioUserPassword", "esecuzione query", sql.toString());
			log.debug("[CredenzialiServiziLowDaoImpl::findByCodiceServizioUserPassword] END");
		}
		return list;
	}
	

	

	// / flexible row mapper.
	public class CredenzialiServiziLowDaoRowMapper implements org.springframework.jdbc.core.RowMapper {

		private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
		private boolean mapAllColumns = true;
		private Class dtoClass;

		/**
		 * @param columnsToRead elenco delle colonne da includere nel mapping (per query
		 *                      incomplete, esempio distinct, custom select...)
		 */
		public CredenzialiServiziLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
			if (columnsToRead != null) {
				mapAllColumns = false;
				for (int i = 0; i < columnsToRead.length; i++)
					columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
			}
			this.dtoClass = dtoClass;
		}

	
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

			if (dto instanceof CredenzialiServiziLowDto) {
				return mapRow_internal((CredenzialiServiziLowDto) dto, rs, row);
			}

			return dto;
		}

		public CredenzialiServiziLowDto mapRow_internal(CredenzialiServiziLowDto objectToFill, ResultSet rs, int row) throws SQLException

		{
			CredenzialiServiziLowDto dto = objectToFill;
			
		

			if (mapAllColumns || columnsToReadMap.get("ID") != null)
				dto.setId((Long) rs.getObject("ID"));


			if (mapAllColumns || columnsToReadMap.get("CODICE_SERVIZIO") != null)
				dto.setCodiceServizio(rs.getString("CODICE_SERVIZIO"));
			
			if (mapAllColumns || columnsToReadMap.get("USERNAME") != null)
				dto.setUsername(rs.getString("USERNAME"));
			
			if (mapAllColumns || columnsToReadMap.get("PASSWORD") != null)
				dto.setPassword(rs.getString("PASSWORD"));

			if (mapAllColumns || columnsToReadMap.get("DATA_INIZIO_VALIDITA") != null)
				dto.setDataInizioValidita(rs.getTimestamp("DATA_INIZIO_VALIDITA"));
			
			if (mapAllColumns || columnsToReadMap.get("DATA_FINE_VALIDITA") != null)
				dto.setDataFineValidita(rs.getTimestamp("DATA_FINE_VALIDITA"));

			if (mapAllColumns || columnsToReadMap.get("DATA_INSERIMENTO") != null)
				dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

			
			return dto;
		}

	}

}
