/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;


import it.csi.dma.dmacontatti.business.dao.CatalogoLogLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.CatalogoLogLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.CatalogoLogLowDaoException;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*PROTECTED REGION ID(R-496836449) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class CatalogoLogLowDaoImpl extends AbstractDAO
		implements
			ParameterizedRowMapper<CatalogoLogLowDto>,
		CatalogoLogLowDao, BeanFactoryAware {
	private static String APPLICATION_CODE_AUDIT = "AUDIT";
	protected static Logger log = Logger.getLogger(APPLICATION_CODE_AUDIT);
	
	protected String tableName;
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

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.jdbcTemplate = beanFactory.getBean("namedJdbcTemplate", NamedParameterJdbcTemplate.class);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return CatalogoLogLowDto
	 * @generated
	 */
	public CatalogoLogLowDto mapRow(ResultSet rs, int row) throws SQLException {
		CatalogoLogLowDto dto = new CatalogoLogLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	/**
	 * Method 'mapRow_internal'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return CatalogoLogLowDto
	 * @generated
	 */
	public CatalogoLogLowDto mapRow_internal(CatalogoLogLowDto objectToFill,
			ResultSet rs, int row) throws SQLException {
		CatalogoLogLowDto dto = objectToFill;

		CatalogoLogLowDaoRowMapper catalogoLogLowDaoRowMapper = new CatalogoLogLowDaoRowMapper(
				null, CatalogoLogLowDto.class);

		return catalogoLogLowDaoRowMapper
				.mapRow_internal(objectToFill, rs, row);
	}

	protected CatalogoLogLowDaoRowMapper findByPrimaryKeyRowMapper = new CatalogoLogLowDaoRowMapper(
			null, CatalogoLogLowDto.class);

	protected CatalogoLogLowDaoRowMapper byCodiceRowMapper = new CatalogoLogLowDaoRowMapper(
			null, CatalogoLogLowDto.class);

	protected CatalogoLogLowDaoRowMapper findAllRowMapper = new CatalogoLogLowDaoRowMapper(
			null, CatalogoLogLowDto.class);

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 * @generated
	 */
	public String getTableName() {
		return "DMACC_D_CATALOGO_LOG";
	}


	/** 
	 * Implementazione del finder byCodice
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public CatalogoLogLowDto findByCodice(String input)
			throws CatalogoLogLowDaoException {
		StringBuilder sql = new StringBuilder();
		
		CatalogoLogLowDto retVal = null;
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		

		sql.append("SELECT ID,CODICE,DESCRIZIONE_ERRORE,DESCRIZIONE_LOG,DATA_INSERIMENTO,FLG_ERRORE, COD_FSE FROM ");
		sql.append(getTableName());
		sql.append(" WHERE ");
		/*PROTECTED REGION ID(R1271837938) ENABLED START*/
		// personalizzare la query SQL relativa al finder

		// personalizzare l'elenco dei parametri da passare al jdbctemplate (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		sql.append("CODICE = :nome");
		/*PROTECTED REGION END*/

		/*PROTECTED REGION ID(R897156664) ENABLED START*/
		//***aggiungere tutte le condizioni

		paramMap.addValue("nome", input);

		/*PROTECTED REGION END*/
		List<CatalogoLogLowDto> list = null;
		StopWatch stopWatch = new StopWatch(APPLICATION_CODE_AUDIT);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap,
					byCodiceRowMapper);

			if (list != null && list.size() > 0) {
				retVal = list.get(0);
			}

		} catch (RuntimeException ex) {
			log.error("[CatalogoLogLowDaoImpl::findByCodice] esecuzione query",
					ex);
			throw new CatalogoLogLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("CatalogoLogLowDaoImpl", "findByCodice",
					"esecuzione query", sql.toString());
			log.debug("[CatalogoLogLowDaoImpl::findByCodice] END");
		}
		return retVal;
	}


	/// flexible row mapper. 
	public class CatalogoLogLowDaoRowMapper
			implements
				org.springframework.jdbc.core.RowMapper {

		private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
		private boolean mapAllColumns = true;
		private Class dtoClass;

		/**
		 * @param columnsToRead elenco delle colonne da includere nel mapping (per query
		 *        incomplete, esempio distinct, custom select...)
		 */
		public CatalogoLogLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
			if (columnsToRead != null) {
				mapAllColumns = false;
				for (int i = 0; i < columnsToRead.length; i++)
					columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
			}
			this.dtoClass = dtoClass;
		}

		/**
		 * Method 'mapRow'
		 * 
		 * @param rs
		 * @param row
		 * @throws SQLException
		 * @return CatalogoLogLowDto
		 * @generated
		 */
		public Object mapRow(ResultSet rs, int row) throws SQLException {

			Object dto = null;
			try {
				dto = dtoClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException("Impossibile istanziare la classe "
						+ dto.getClass().getCanonicalName() + " ,"
						+ e.getCause());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException("Impossibile accedere alla classe "
						+ dto.getClass().getCanonicalName() + " ,"
						+ e.getCause());
			}

			if (dto instanceof CatalogoLogLowDto) {
				return mapRow_internal((CatalogoLogLowDto) dto, rs, row);
			}

			return dto;
		}

		public CatalogoLogLowDto mapRow_internal(
				CatalogoLogLowDto objectToFill, ResultSet rs, int row)
				throws SQLException

		{
			CatalogoLogLowDto dto = objectToFill;

			if (mapAllColumns || columnsToReadMap.get("ID") != null)
				dto.setId(rs.getLong("ID"));

			if (mapAllColumns || columnsToReadMap.get("CODICE") != null)
				dto.setCodice(rs.getString("CODICE"));

			if (mapAllColumns
					|| columnsToReadMap.get("DESCRIZIONE_ERRORE") != null)
				dto.setDescrizioneErrore(rs.getString("DESCRIZIONE_ERRORE"));

			if (mapAllColumns
					|| columnsToReadMap.get("DESCRIZIONE_LOG") != null)
				dto.setDescrizioneLog(rs.getString("DESCRIZIONE_LOG"));

			if (mapAllColumns
					|| columnsToReadMap.get("DATA_INSERIMENTO") != null)
				dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

			if (mapAllColumns || columnsToReadMap.get("FLG_ERRORE") != null)
				dto.setFlgErrore(rs.getString("FLG_ERRORE"));
			
			if (mapAllColumns || columnsToReadMap.get("COD_FSE") != null)
				dto.setCodFse(rs.getString("COD_FSE"));

			return dto;
		}

	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
