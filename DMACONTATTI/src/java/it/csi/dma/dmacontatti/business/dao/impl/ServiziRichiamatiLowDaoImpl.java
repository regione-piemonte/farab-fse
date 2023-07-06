/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;


import it.csi.dma.dmacontatti.business.dao.ServiziRichiamatiLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ServiziRichiamatiLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.ServiziRichiamatiLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiziRichiamatiLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ServiziRichiamatiLowDto>, ServiziRichiamatiLowDao {
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

 
    public ServiziRichiamatiLowDto insert(ServiziRichiamatiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());

        final String sql = "INSERT INTO "
                + getTableName() +
                " (ID, ID_TRANSAZIONE, ID_SERVIZIO, REQUEST, RESPONSE, DATA_CHIAMATA, DATA_RISPOSTA, ESITO, DATA_INSERIMENTO, DATA_AGGIORNAMENTO) VALUES " +
                " (:id, :idTransazione, :idServizio, pgp_sym_encrypt(:request, :encryption_key), pgp_sym_encrypt(:response, :encryption_key), :dataChiamata," +
                " :dataRisposta, :esito, :dataInserimento, :dataAggiornamento)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", newKey, java.sql.Types.BIGINT);

        params.addValue("idTransazione", dto.getIdTransazione(),
                java.sql.Types.VARCHAR);

        params.addValue("idServizio", dto.getIdServizio(),
                java.sql.Types.BIGINT);
        
        params.addValue("request", dto.getRequest(),
                java.sql.Types.VARCHAR);

        params.addValue("response", dto.getResponse(),
                java.sql.Types.VARCHAR);
        
        params.addValue("dataChiamata", dto.getDataChiamata(),
                java.sql.Types.TIMESTAMP);

        params.addValue("dataRisposta", dto.getDataRisposta(),
                java.sql.Types.TIMESTAMP);
        
        
        params.addValue("esito", dto.getEsito(),
                java.sql.Types.VARCHAR);

        params.addValue("dataInserimento", dto.getDataInserimento(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("dataAggiornamento", dto.getDataAggiornamento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("encryption_key", dto.getEncryptionKey(), java.sql.Types.VARCHAR);
        

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ServiziRichiamatiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("ServiziRichiamatiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[ServiziRichiamatiLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto;

    }
    
    public void update(ServiziRichiamatiLowDto dto) throws ServiziRichiamatiLowDaoException {
        final String sql = "UPDATE "
            	+ getTableName()
		+ " SET ESITO = :esito, RESPONSE = pgp_sym_encrypt(:response, :encryption_key),"
		+"  DATA_RISPOSTA=:dataRisposta, DATA_AGGIORNAMENTO=:DATA_AGGIORNAMENTO"
                + " WHERE  ID = :ID ";

        if (dto.getId() == null) {
            log.error("[ServiziRichiamatiLowDaoImpl::update] chiave primaria non impostata");

            return;
        }

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", dto.getId(), java.sql.Types.BIGINT);

        params.addValue("response", dto.getResponse(),
                java.sql.Types.VARCHAR);

        params.addValue("dataRisposta", dto.getDataRisposta(),
                java.sql.Types.TIMESTAMP);

        params.addValue("esito", dto.getEsito(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_AGGIORNAMENTO", dto.getDataAggiornamento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("encryption_key", dto.getEncryptionKey(), java.sql.Types.VARCHAR);


        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ServiziRichiamatiCCELowDaoImpl::update] esecuzione query", ex);
            throw new ServiziRichiamatiLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ServiziRichiamatiCCELowDaoImpl", "update",
                    "esecuzione query", sql);
            log.debug("[ServiziRichiamatiCCELowDaoImpl::update] END");
        }
    }


    public ServiziRichiamatiLowDto mapRow(ResultSet rs, int row) throws SQLException {
    	ServiziRichiamatiLowDto dto = new ServiziRichiamatiLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }

    public ServiziRichiamatiLowDto mapRow_internal(ServiziRichiamatiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException {
    	ServiziRichiamatiLowDto dto = objectToFill;

    	ServiziRichiamatiLowDaoRowMapper ServiziRichiamatiLowDaoRowMapper = new ServiziRichiamatiLowDaoRowMapper(
                null, ServiziRichiamatiLowDto.class);

        return ServiziRichiamatiLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }


    protected ServiziRichiamatiLowDaoRowMapper findLogRowMapper = new ServiziRichiamatiLowDaoRowMapper(
            null, ServiziRichiamatiLowDto.class);

    /**
     * Method 'getTableName'
     *
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_L_SERVIZI_RICHIAMATI";
    }

    // / flexible row mapper.
    public class ServiziRichiamatiLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public ServiziRichiamatiLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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

            if (dto instanceof ServiziRichiamatiLowDto) {
                return mapRow_internal((ServiziRichiamatiLowDto) dto, rs, row);
            }

            return dto;
        }


        public ServiziRichiamatiLowDto mapRow_internal(ServiziRichiamatiLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
        	ServiziRichiamatiLowDto dto = objectToFill;

        	 if (mapAllColumns || columnsToReadMap.get("ID") != null)
                 dto.setId((Long) rs.getObject("ID"));


             if (mapAllColumns
                     || columnsToReadMap.get("ID_TRANSAZIONE") != null)
                 dto.setIdTransazione(rs
                         .getString("ID_TRANSAZIONE"));

             if (mapAllColumns
                     || columnsToReadMap.get("ID_SERVIZIO") != null)
                 dto.setIdServizio((Long) rs.getObject("ID_SERVIZIO"));


             if (mapAllColumns
                     || columnsToReadMap.get("REQUEST") != null)
                 dto.setRequest(rs
                         .getString("REQUEST"));

             if (mapAllColumns
                     || columnsToReadMap.get("RESPONSE") != null)
                 dto.setResponse(rs.getString("RESPONSE"));


             if (mapAllColumns
                     || columnsToReadMap.get("DATA_CHIAMATA") != null)
                 dto.setDataChiamata(rs.getTimestamp("DATA_CHIAMATA"));

             if (mapAllColumns || columnsToReadMap.get("DATA_RISPOSTA") != null)
                 dto.setDataRisposta(rs.getTimestamp("DATA_RISPOSTA"));


             if (mapAllColumns
                     || columnsToReadMap.get("ESITO") != null)
                 dto.setEsito(rs
                         .getString("ESITO"));

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_INSERIMENTO") != null)
                 dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_AGGIORNAMENTO") != null)
                 dto.setDataAggiornamento(rs
                         .getTimestamp("DATA_AGGIORNAMENTO"));


            return dto;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
