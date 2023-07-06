/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.dma.dmacontatti.business.dao.ErroriLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriLowPk;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import it.csi.util.performance.StopWatch;



public class ErroriLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ErroriLowDto>, ErroriLowDao {
    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    /**
     * @generated
     */
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected ErroriLowDaoRowMapper findByCodiceTipoConsenso = new ErroriLowDaoRowMapper(
            null, ErroriLowDto.class);
    /**
     * @generated
     */
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

 
    public ErroriLowPk insert(ErroriLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());
        
        
    


        final String sql = "INSERT INTO "
                + getTableName()
                + " (ID,WSO2_ID,COD_ERRORE,DESCR_ERRORE,TIPO_ERRORE,DATA_INS,INFORMAZIONI_AGGIUNTIVE) "
                + "VALUES (  :ID,:WSO2_ID,:COD_ERRORE,:DESCR_ERRORE,:TIPO_ERRORE,:DATA_INS,:INFORMAZIONI_AGGIUNTIVE )";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", newKey, java.sql.Types.BIGINT);

   
        params.addValue("WSO2_ID", dto.getWso2_id(),
                java.sql.Types.VARCHAR);

        params.addValue("COD_ERRORE", dto.getCod_errore(),
                java.sql.Types.VARCHAR);
        
        params.addValue("DESCR_ERRORE", dto.getDescr_errore(),
                java.sql.Types.VARCHAR);

        params.addValue("TIPO_ERRORE", dto.getTipo_errore(),
                java.sql.Types.VARCHAR);
        
        params.addValue("DATA_INS", dto.getData_ins(),
                java.sql.Types.TIMESTAMP);

        params.addValue("INFORMAZIONI_AGGIUNTIVE", dto.getInformazioni_aggiuntive(),
                java.sql.Types.VARCHAR);
      
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ErroriLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("ErroriLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[ErroriLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto.createPk();

    }

  
   

    public ErroriLowDto mapRow(ResultSet rs, int row) throws SQLException {
    	ErroriLowDto dto = new ErroriLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }
    
    

  
    public ErroriLowDto mapRow_internal(ErroriLowDto objectToFill,
            ResultSet rs, int row) throws SQLException {
    	ErroriLowDto dto = objectToFill;

    	ErroriLowDaoRowMapper ErroriLowDaoRowMapper = new ErroriLowDaoRowMapper(
                null, ErroriLowDto.class);

        return ErroriLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }
    
    
    protected ErroriLowDaoRowMapper findLogRowMapper = new ErroriLowDaoRowMapper(
            null, ErroriLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_L_ERRORI";
    }

    


    // / flexible row mapper.
    public class ErroriLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public ErroriLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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
         * @return InvioNotificaConsensiLowDto
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

            if (dto instanceof ErroriLowDto) {
                return mapRow_internal((ErroriLowDto) dto, rs, row);
            }

            return dto;
        }
        
    
        public ErroriLowDto mapRow_internal(ErroriLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
        	ErroriLowDto dto = objectToFill;
            
        	
        	
        
            
          
            if (mapAllColumns || columnsToReadMap.get("ID") != null)
                dto.setId((Long) rs.getObject("ID"));
            

            if (mapAllColumns
                    || columnsToReadMap.get("COD_ERRORE") != null)
                dto.setCod_errore(rs
                        .getString("COD_ERRORE"));

            if (mapAllColumns
                    || columnsToReadMap.get("DESCR_ERRORE") != null)
                dto.setDescr_errore(rs.getString("DESCR_ERRORE"));
            
            
            if (mapAllColumns
                    || columnsToReadMap.get("TIPO_ERRORE") != null)
                dto.setTipo_errore(rs
                        .getString("TIPO_ERRORE"));

            if (mapAllColumns
                    || columnsToReadMap.get("DATA_INS") != null)
                dto.setData_ins(rs.getTimestamp("DATA_INS"));           
            
                        
            if (mapAllColumns
                    || columnsToReadMap.get("INFORMAZIONI_AGGIUNTIVE") != null)
                dto.setInformazioni_aggiuntive(rs
                        .getString("INFORMAZIONI_AGGIUNTIVE"));

           


            return dto;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
