/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.dma.dmacontatti.business.dao.XmlMessaggiLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.XmlMessaggiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.XmlMessaggiLowPk;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import it.csi.util.performance.StopWatch;



public class XmlMessaggiLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<XmlMessaggiLowDto>, XmlMessaggiLowDao {
    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    /**
     * @generated
     */
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected XmlMessaggiLowDaoRowMapper findByCodiceTipoConsenso = new XmlMessaggiLowDaoRowMapper(
            null, XmlMessaggiLowDto.class);
    /**
     * @generated
     */
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

 
    public XmlMessaggiLowPk insert(XmlMessaggiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());
        
        

        final String sql = "INSERT INTO "
                + getTableName()
                
                + " (ID,WSO2_ID,XML_IN,XML_OUT,XML_IN_PROMEMORIA,XML_OUT_PROMEMORIA	,DATA_INSERIMENTO	,XML_IN_SERVIZIO	,XML_OUT_SERVIZIO) "
               
                 + " VALUES (:ID,:WSO2_ID,:XML_IN,:XML_OUT,:XML_IN_PROMEMORIA,:XML_OUT_PROMEMORIA	,:DATA_INSERIMENTO	,:XML_IN_SERVIZIO	,:XML_OUT_SERVIZIO) ";
               
               

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", newKey, java.sql.Types.BIGINT);

   
        params.addValue("WSO2_ID", dto.getWso2_id(),
                java.sql.Types.VARCHAR);

        params.addValue("XML_IN", dto.getXml_in(),
                java.sql.Types.VARCHAR);
        
        params.addValue("XML_OUT", dto.getXml_out(),
                java.sql.Types.VARCHAR);

        params.addValue("XML_IN_PROMEMORIA", dto.getXml_in_promemoria(),
                java.sql.Types.VARCHAR);
        
        params.addValue("XML_OUT_PROMEMORIA", dto.getXml_out_promemoria(),
                java.sql.Types.VARCHAR);
        
      
        params.addValue("DATA_INSERIMENTO", dto.getData_inserimento(),
                java.sql.Types.TIMESTAMP);
        
    
        params.addValue("XML_IN_SERVIZIO", dto.getXml_in_servizio(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("XML_OUT_SERVIZIO", dto.getXml_out_servizio(),
                java.sql.Types.VARCHAR);
                
        
      
        
        
      
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[XmlMessaggiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("XmlMessaggiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[XmlMessaggiLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto.createPk();

    }
    
    
    public XmlMessaggiLowDto insertLog(XmlMessaggiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());
        
        

        final String sql = "INSERT INTO "
                + getTableName()
                
                + " (ID,WSO2_ID,XML_IN,XML_OUT,XML_IN_PROMEMORIA,XML_OUT_PROMEMORIA	,DATA_INSERIMENTO	,XML_IN_SERVIZIO	,XML_OUT_SERVIZIO) "
               
                 + " VALUES (:ID,:WSO2_ID,pgp_sym_encrypt(:XML_IN, '@encryption_key@'),:XML_OUT,:XML_IN_PROMEMORIA,:XML_OUT_PROMEMORIA	,:DATA_INSERIMENTO	,:XML_IN_SERVIZIO	,:XML_OUT_SERVIZIO) ";
               
               

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", newKey, java.sql.Types.BIGINT);

   
        params.addValue("WSO2_ID", dto.getWso2_id(),
                java.sql.Types.VARCHAR);

        params.addValue("XML_IN", dto.getXml_in(),
                java.sql.Types.VARCHAR);
        
        params.addValue("XML_OUT", dto.getXml_out(),
                java.sql.Types.VARCHAR);

        params.addValue("XML_IN_PROMEMORIA", dto.getXml_in_promemoria(),
                java.sql.Types.VARCHAR);
        
        params.addValue("XML_OUT_PROMEMORIA", dto.getXml_out_promemoria(),
                java.sql.Types.VARCHAR);
        
      
        params.addValue("DATA_INSERIMENTO", dto.getData_inserimento(),
                java.sql.Types.TIMESTAMP);
        
    
        params.addValue("XML_IN_SERVIZIO", dto.getXml_in_servizio(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("XML_OUT_SERVIZIO", dto.getXml_out_servizio(),
                java.sql.Types.VARCHAR);
                
        
      
        
        
      
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[XmlMessaggiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("XmlMessaggiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[XmlMessaggiLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto;

    }


    
    public XmlMessaggiLowDto updateXmlServizio(XmlMessaggiLowDto dto)
    {
    	String sql = "";
    	
    	if("N".equals(dto.getEcryption())) {
    		  sql = "UPDATE "
    	                + getTableName()
    	                
    	                + " SET XML_IN=:XML_IN, XML_OUT=:XML_OUT "
    	                + "WHERE WSO2_ID=:WSO2_ID";
    	}else {
    		  sql = "UPDATE "
    	                + getTableName()
    	                
    	                + " SET XML_IN=pgp_sym_encrypt(:XML_IN, '@encryption_key@'), XML_OUT=pgp_sym_encrypt(:XML_OUT, '@encryption_key@') "
    	                + "WHERE WSO2_ID=:WSO2_ID";
    	}
        
      

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("WSO2_ID", dto.getWso2_id(), java.sql.Types.VARCHAR);


        params.addValue("XML_IN", dto.getXml_in(),
                java.sql.Types.VARCHAR);
        
        params.addValue("XML_OUT", dto.getXml_out(),
                java.sql.Types.VARCHAR);

        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[XmlMessaggiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("XmlMessaggiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[XmlMessaggiLowDaoImpl::insert] END");
        }

        return dto;

    }
  
   

    public XmlMessaggiLowDto mapRow(ResultSet rs, int row) throws SQLException {
    	XmlMessaggiLowDto dto = new XmlMessaggiLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }
    
    

  
    public XmlMessaggiLowDto mapRow_internal(XmlMessaggiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException {
    	XmlMessaggiLowDto dto = objectToFill;

    	XmlMessaggiLowDaoRowMapper MessaggiLowDaoRowMapper = new XmlMessaggiLowDaoRowMapper(
                null, XmlMessaggiLowDto.class);

        return MessaggiLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }
    
    
    protected XmlMessaggiLowDaoRowMapper findLogRowMapper = new XmlMessaggiLowDaoRowMapper(
            null, XmlMessaggiLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_L_XML_MESSAGGI";
    }

    


    // / flexible row mapper.
    public class XmlMessaggiLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public XmlMessaggiLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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

            if (dto instanceof XmlMessaggiLowDto) {
                return mapRow_internal((XmlMessaggiLowDto) dto, rs, row);
            }

            return dto;
        }
        
    
        public XmlMessaggiLowDto mapRow_internal(XmlMessaggiLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
        	XmlMessaggiLowDto dto = objectToFill;
        	
        	
        	
        	 if (mapAllColumns || columnsToReadMap.get("ID") != null)
                 dto.setId((Long) rs.getObject("ID"));
             

             if (mapAllColumns
                     || columnsToReadMap.get("WSO2_ID") != null)
                 dto.setWso2_id(rs
                         .getString("WSO2_ID"));
             
        
             if (mapAllColumns
                     || columnsToReadMap.get("XML_IN") != null)
                 dto.setXml_in(rs.getString("XML_IN"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("XML_OUT") != null)
                 dto.setXml_out(rs
                         .getString("XML_OUT"));

             if (mapAllColumns
                     || columnsToReadMap.get("XML_IN_PROMEMORIA") != null)
                 dto.setXml_in_promemoria(rs.getString("XML_IN_PROMEMORIA"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("XML_OUT_PROMEMORIA") != null)
                 dto.setXml_out_promemoria(rs.getString("XML_OUT_PROMEMORIA"));

        	
        
             if (mapAllColumns || columnsToReadMap.get("DATA_INSERIMENTO") != null)
                 dto.setData_inserimento(rs.getTimestamp("DATA_INSERIMENTO"));
             

             if (mapAllColumns
                     || columnsToReadMap.get("XML_IN_SERVIZIO") != null)
                 dto.setXml_in_servizio(rs
                         .getString("XML_IN_SERVIZIO"));

             if (mapAllColumns
                     || columnsToReadMap.get("XML_OUT_SERVIZIO") != null)
                 dto.setXml_out_servizio(rs.getString("XML_OUT_SERVIZIO"));
             
             
            
           
           


            return dto;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
