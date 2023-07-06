/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.ErroriServiziRichiamatiLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriServiziRichiamatiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriServiziRichiamatiLowPk;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ErroriServiziRichiamatiLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ErroriServiziRichiamatiLowDto>, ErroriServiziRichiamatiLowDao {
    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    /**
     * @generated
     */
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected ErroriServiziRichiamatiLowDaoRowMapper findByCodiceTipoConsenso = new ErroriServiziRichiamatiLowDaoRowMapper(
            null, ErroriServiziRichiamatiLowDto.class);
    /**
     * @generated
     */
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

 
    public ErroriServiziRichiamatiLowPk insert(ErroriServiziRichiamatiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());

        final String sql = "INSERT INTO "
                + getTableName()
                + " (ID,ID_SERVIZIO_RICHIAMATO,ID_CATALOGO_LOG,CODICE_ERRORE,DESCRIZIONE_ERRORE,CONTROLLORE,INFORMAZIONI_AGGIUNTIVE,DATA_INSERIMENTO) "
                + "VALUES ( :ID,:ID_SERVIZIO_RICHIAMATO,:ID_CATALOGO_LOG,:CODICE_ERRORE,:DESCRIZIONE_ERRORE,:CONTROLLORE,:INFORMAZIONI_AGGIUNTIVE,:DATA_INSERIMENTO )";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", newKey, java.sql.Types.BIGINT);

        params.addValue("ID_SERVIZIO_RICHIAMATO", dto.getIdServizioRichiamato(),
                java.sql.Types.BIGINT);

        params.addValue("ID_CATALOGO_LOG", dto.getIdCatalogoLog(),
                java.sql.Types.BIGINT);
        
        params.addValue("CODICE_ERRORE", dto.getCodiceErrore(),
                java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE_ERRORE", dto.getDescrizioneErrore(),
                java.sql.Types.VARCHAR);
        
        params.addValue("CONTROLLORE", dto.getControllore(),
                java.sql.Types.VARCHAR);

        params.addValue("INFORMAZIONI_AGGIUNTIVE", dto.getInformazioni_aggiuntive(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_INSERIMENTO", dto.getDataInserimento(),
                java.sql.Types.TIMESTAMP);

        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ErroriServiziRichiamatiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("ErroriServiziRichiamatiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[ErroriServiziRichiamatiLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto.createPk();

    }

  
   

    public ErroriServiziRichiamatiLowDto mapRow(ResultSet rs, int row) throws SQLException {
    	ErroriServiziRichiamatiLowDto dto = new ErroriServiziRichiamatiLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }
    
    

  
    public ErroriServiziRichiamatiLowDto mapRow_internal(ErroriServiziRichiamatiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException {
    	ErroriServiziRichiamatiLowDto dto = objectToFill;

    	ErroriServiziRichiamatiLowDaoRowMapper ErroriServiziRichiamatiLowDaoRowMapper = new ErroriServiziRichiamatiLowDaoRowMapper(
                null, ErroriServiziRichiamatiLowDto.class);

        return ErroriServiziRichiamatiLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }
    
    
    protected ErroriServiziRichiamatiLowDaoRowMapper findLogRowMapper = new ErroriServiziRichiamatiLowDaoRowMapper(
            null, ErroriServiziRichiamatiLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_L_ERRORI_SERVIZI_RICHIAMATI";
    }

    


    // / flexible row mapper.
    public class ErroriServiziRichiamatiLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public ErroriServiziRichiamatiLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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

            if (dto instanceof ErroriServiziRichiamatiLowDto) {
                return mapRow_internal((ErroriServiziRichiamatiLowDto) dto, rs, row);
            }

            return dto;
        }
        
    
        public ErroriServiziRichiamatiLowDto mapRow_internal(ErroriServiziRichiamatiLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
        	ErroriServiziRichiamatiLowDto dto = objectToFill;

            return dto;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
