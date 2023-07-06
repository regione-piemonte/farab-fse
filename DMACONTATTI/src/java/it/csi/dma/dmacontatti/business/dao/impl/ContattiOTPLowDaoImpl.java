/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import it.csi.dma.dmacontatti.business.dao.ContattiOTPLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ContattiOTPLowDaoException;
import it.csi.dma.dmacontatti.business.dao.exceptions.RuoloLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.dma.dmacontatti.util.Utils;
import it.csi.util.performance.StopWatch;

public class ContattiOTPLowDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ContattiOTPLowDto>, ContattiOTPLowDao {
	
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

	@Override
	public ContattiOTPLowPk insert(ContattiOTPLowDto dto) {
		
		Long newKey = new Long(incrementer.nextIntValue());
		
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO " +getTableName());
		queryBuilder.append(" (ID, ID_PAZIENTE, CANALE, OTP, DATA_INIZIO_VALIDITA, DATA_FINE_VALIDITA, CODICE_FISCALE_PAZIENTE)");
		queryBuilder.append(" VALUES (:ID, :ID_PAZIENTE, :CANALE, :OTP, :DATAINIZIO, :DATAFINE, :CODICE_FISCALE_PAZIENTE)");
		final String query = queryBuilder.toString();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("ID", newKey);
		params.addValue("ID_PAZIENTE", dto.getIdPaziente());
		params.addValue("CANALE", dto.getCanale());
		params.addValue("OTP", dto.getOtp());
		params.addValue("DATAINIZIO", Utils.sysdate());
		params.addValue("DATAFINE", dto.getDataFineValidita());
		params.addValue("CODICE_FISCALE_PAZIENTE", dto.getCodiceFiscalePaziente());
		
		 StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
	        try {
	            stopWatch.start();
	            jdbcTemplate.update(query.toString(), params);
	        } catch (RuntimeException ex) {
	            log.error("[ContattiDTOLowDaoImpl::insert] esecuzione query", ex);
	            throw ex;
	        } finally {
	            stopWatch.dumpElapsed("ContattiDTOLowDaoImpl", "insert",
	                    "esecuzione query", query);
	            log.debug("[ContattiDTOLowDaoImpl::insert] END");
	        }
	        dto.setId(newKey);
	        return dto.createPk();
	}

	@Override
	public void update(ContattiOTPLowDto dto) throws ContattiOTPLowDaoException {
		StringBuilder queryBuilder = new StringBuilder("UPDATE " +getTableName());
		queryBuilder.append(" SET ");
		queryBuilder.append(" ID_PAZIENTE = :ID_PAZIENTE,");
		queryBuilder.append(" CANALE = :CANALE,");
		queryBuilder.append(" OTP = :OTP,");
		queryBuilder.append(" DATA_INIZIO_VALIDITA = :DATAINIZIO,");
		queryBuilder.append(" DATA_FINE_VALIDITA = :DATAFINE, ");
		queryBuilder.append(" CODICE_FISCALE_PAZIENTE = :CODICE_FISCALE_PAZIENTE");
		queryBuilder.append(" WHERE ID = :ID");
		final String query = queryBuilder.toString();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		
		if (dto.getId() == null) {
            log.error("[ContattiDTOLowDaoImpl::update] chiave primaria non impostata");
            throw new ContattiOTPLowDaoException("Chiave primaria non impostata");
        }
		
		params.addValue("ID_PAZIENTE", dto.getIdPaziente());
		params.addValue("CANALE", dto.getCanale());
		params.addValue("OTP", dto.getOtp());
		params.addValue("DATAINIZIO", dto.getDataInizioValidita(), java.sql.Types.TIMESTAMP);
		params.addValue("DATAFINE", dto.getDataFineValidita(), java.sql.Types.TIMESTAMP);
		params.addValue("ID", dto.getId());
		params.addValue("CODICE_FISCALE_PAZIENTE", dto.getCodiceFiscalePaziente());
		
		
		 StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
	        try {
	            stopWatch.start();
	            jdbcTemplate.update(query.toString(), params);
	        } catch (RuntimeException ex) {
	            log.error("[ContattiDTOLowDaoImpl::update] esecuzione query", ex);
	            throw ex;
	        } finally {
	            stopWatch.dumpElapsed("ContattiDTOLowDaoImpl", "update",
	                    "esecuzione query", query);
	            log.debug("[ContattiDTOLowDaoImpl::update] END");
	        }

	}

	@Override
	public ContattiOTPLowDto mapRow(ResultSet rs, int row) throws SQLException {
		ContattiOTPLowDto dto = new ContattiOTPLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	@Override
	public ContattiOTPLowDto mapRow_internal(ContattiOTPLowDto objectToFill, ResultSet rs, int row) throws SQLException {
		ContattiOTPLowDto dto = objectToFill;
		
		ContattiOTPLowDaoRowMapper contattiOTPLowDaoRowMapper = new ContattiOTPLowDaoRowMapper(null, ContattiOTPLowDto.class);
		
		return contattiOTPLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
	}
	
	protected ContattiOTPLowDaoRowMapper findByPrimaryKeyRowMapper = 
			new ContattiOTPLowDaoRowMapper(null, ContattiOTPLowDto.class);
	
	protected ContattiOTPLowDaoRowMapper findAllRowMapper = 
			new ContattiOTPLowDaoRowMapper(null, ContattiOTPLowDto.class);	
	
	protected ContattiOTPLowDaoRowMapper ricercaOTPRowMapper = 
			new ContattiOTPLowDaoRowMapper(null, ContattiOTPLowDto.class);

	@SuppressWarnings("unchecked")
	@Override
	public ContattiOTPLowDto findByPrimaryKey(ContattiOTPLowPk pk) throws ContattiOTPLowDaoException {
		StringBuilder queryBuilder = new StringBuilder(" SELECT ID,ID_PAZIENTE,CANALE,OTP,DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA,CODICE_FISCALE_PAZIENTE FROM " + getTableName());
		queryBuilder.append(" WHERE ID = :ID");
		String sql = queryBuilder.toString();
		
		MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", pk.getId(), java.sql.Types.BIGINT);
        
        List<ContattiOTPLowDto> list = null;
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params,
                    findByPrimaryKeyRowMapper);
        } catch (RuntimeException ex) {
            log.error("[ContattiOTPLowDaoImpl::findByPrimaryKey] esecuzione query",
                    ex);
            throw new ContattiOTPLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ContattiOTPLowDaoImpl", "findByPrimaryKey",
                    "esecuzione query", sql.toString());
            log.debug("[ContattiOTPLowDaoImpl::findByPrimaryKey] END");
        }

        return list.size() == 0 ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContattiOTPLowDto> findAll() throws ContattiOTPLowDaoException {
		StringBuilder queryBuilder = new StringBuilder(" SELECT ID,ID_PAZIENTE,CANALE,OTP,DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA,CODICE_FISCALE_PAZIENTE FROM " + getTableName());
		String sql = queryBuilder.toString();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<ContattiOTPLowDto> list = null;
		
		 StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
	        try {
	            stopWatch.start();
	            list = jdbcTemplate.query(sql.toString(), params, findAllRowMapper);
	        } catch (RuntimeException ex) {
	            log.error("[ContattiOTPLowDaoImpl::findAll] esecuzione query", ex);
	            throw new ContattiOTPLowDaoException("Query failed", ex);
	        } finally {
	            stopWatch.dumpElapsed("ContattiOTPLowDaoImpl", "findAll",
	                    "esecuzione query", sql.toString());
	            log.debug("[ContattiOTPLowDaoImpl::findAll] END");
	        }

	        return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ContattiOTPLowDto> ricercaOTP(String cfPaziente, String canale, String codiceOTP) throws ContattiOTPLowDaoException{
		StringBuilder queryBuilder = new StringBuilder(" SELECT ID,ID_PAZIENTE,CANALE,OTP,DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA,CODICE_FISCALE_PAZIENTE FROM " + getTableName());
		queryBuilder.append(" WHERE 1 = 1");
		queryBuilder.append(" AND CODICE_FISCALE_PAZIENTE = :CODICE_FISCALE_PAZIENTE");
		queryBuilder.append(" AND CANALE = :CANALE");
		queryBuilder.append(" AND OTP = :OTP");
		String sql = queryBuilder.toString();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<ContattiOTPLowDto> list = null;
		
		params.addValue("CODICE_FISCALE_PAZIENTE", cfPaziente);
		params.addValue("CANALE", canale);
		params.addValue("OTP", codiceOTP);
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params, ricercaOTPRowMapper);
        } catch (RuntimeException ex) {
            log.error("[ContattiOTPLowDaoImpl::ricercaOTP] esecuzione query", ex);
            throw new ContattiOTPLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ContattiOTPLowDaoImpl", "ricercaOTP",
                    "esecuzione query", sql.toString());
            log.debug("[ContattiOTPLowDaoImpl::ricercaOTP] END");
        }

        return list;
	}

	@Override
	public List<ContattiOTPLowDto> findByFilterValidi(ContattiOTPLowDto contattiOTPLowDto) throws ContattiOTPLowDaoException{
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder queryBuilder = new StringBuilder(" SELECT ID,ID_PAZIENTE,CANALE,OTP,DATA_INIZIO_VALIDITA,DATA_FINE_VALIDITA,CODICE_FISCALE_PAZIENTE FROM " + getTableName());
		queryBuilder.append(" WHERE 1 = 1");

		if(contattiOTPLowDto.getCodiceFiscalePaziente() != null){
			queryBuilder.append(" AND CODICE_FISCALE_PAZIENTE = :CODICE_FISCALE_PAZIENTE");
		}

		queryBuilder.append(" AND DATA_FINE_VALIDITA is null");

		String sql = queryBuilder.toString();


		List<ContattiOTPLowDto> list = null;

		params.addValue("CODICE_FISCALE_PAZIENTE", contattiOTPLowDto.getCodiceFiscalePaziente());
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), params, ricercaOTPRowMapper);
		} catch (RuntimeException ex) {
			log.error("[ContattiOTPLowDaoImpl::findByFilterValidi] esecuzione query", ex);
			throw new ContattiOTPLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("ContattiOTPLowDaoImpl", "findByFilterValidi",
					"esecuzione query", sql.toString());
			log.debug("[ContattiOTPLowDaoImpl::findByFilterValidi] END");
		}

		return list;
	}
	
	public String getTableName() {
		return "DMACC_T_CONTATTI_OTP";
	}
	
	/**
	 * @return
	 */
	public String getSequenceName() {
		return "SEQ_DMACC_T_CONTATTI_OTP";
	}
	
	// flexible row mapper
	public class ContattiOTPLowDaoRowMapper implements	org.springframework.jdbc.core.RowMapper {

		 private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
	     private boolean mapAllColumns = true;
	     private Class dtoClass;
	     
	     /**
	         * @param columnsToRead
	         *            elenco delle colonne da includere nel mapping (per query
	         *            incomplete, esempio distinct, custom select...)
	         */
        public ContattiOTPLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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
         * @return ContattiOTPLowDto
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

            if (dto instanceof ContattiOTPLowDto) {
                return mapRow_internal((ContattiOTPLowDto) dto, rs, row);
            }

            return dto;
        }
        
        public ContattiOTPLowDto mapRow_internal(ContattiOTPLowDto objectToFill,
        		ResultSet rs, int row) throws SQLException

        {
        	ContattiOTPLowDto dto = objectToFill;
        	
        	 if (mapAllColumns || columnsToReadMap.get("ID") != null) {
        		 dto.setId((Long) rs.getObject("ID"));
        	 }
        	 
        	 if (mapAllColumns || columnsToReadMap.get("ID_PAZIENTE") != null) {
        		 dto.setIdPaziente((Long) rs.getObject("ID_PAZIENTE"));
        	 }
        	 
        	 if (mapAllColumns || columnsToReadMap.get("CANALE") != null) {
        		 dto.setCanale(rs.getString("CANALE"));
        	 }

        	 if (mapAllColumns || columnsToReadMap.get("OTP") != null) {
        		 dto.setOtp(rs.getString("OTP"));
        	 }

        	 if (mapAllColumns || columnsToReadMap.get("DATA_INIZIO_VALIDITA") != null) {
        		 dto.setDataInizioValidita(rs.getTimestamp("DATA_INIZIO_VALIDITA"));
        	 }

        	 if (mapAllColumns || columnsToReadMap.get("DATA_FINE_VALIDITA") != null) {
        		 dto.setDataFineValidita(rs.getTimestamp("DATA_FINE_VALIDITA"));
        	 }

			if (mapAllColumns || columnsToReadMap.get("CODICE_FISCALE_PAZIENTE") != null) {
				dto.setCodiceFiscalePaziente(rs.getString("CODICE_FISCALE_PAZIENTE"));
			}

        	
        	return dto;
        }
	}
}
