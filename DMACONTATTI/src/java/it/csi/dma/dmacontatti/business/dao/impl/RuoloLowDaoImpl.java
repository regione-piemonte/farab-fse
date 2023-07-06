/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.RuoloLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.RuoloLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.RuoloLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.RuoloLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import it.csi.util.performance.StopWatch;

/*PROTECTED REGION ID(R-1244051714) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class RuoloLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<RuoloLowDto>, RuoloLowDao {
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

    /**
     * Method 'insert'
     * 
     * @param dto
     * @return RuoloLowPk
     * @generated
     */

    public RuoloLowPk insert(RuoloLowDto dto)

    {
        final String sql = "INSERT INTO "
                + getTableName()
                + " ( 	ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO, DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO ) VALUES (  :ID , :CODICE_RUOLO , :DESCRIZIONE_RUOLO , :DATA_INSERIMENTO , :FLAG_VISIBILE_PER_CONSENSO , :DATAAGGIORNAMENTO, :CODICE_RUOLO_INI, :DESCRIZIONE_RUOLO_INI, :RUOLO_DPCM, :CATEGORIA_RUOLO  )";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", dto.getId(), java.sql.Types.BIGINT);

        params.addValue("CODICE_RUOLO", dto.getCodiceRuolo(),
                java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE_RUOLO", dto.getDescrizioneRuolo(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_INSERIMENTO", dto.getDataInserimento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("FLAG_VISIBILE_PER_CONSENSO",
                dto.getFlagVisibilePerConsenso(), java.sql.Types.VARCHAR);

        params.addValue("DATAAGGIORNAMENTO", dto.getDataAggiornamento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("CODICE_RUOLO_INI", dto.getCodiceRuoloINI(),
                java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE_RUOLO_INI", dto.getDescrizioneRuoloINI(),
                java.sql.Types.VARCHAR);
        
        params.addValue("RUOLO_DPCM", dto.getRuoloDPCM(),
                java.sql.Types.VARCHAR);
        
        params.addValue("CATEGORIA_RUOLO", dto.getCategoriaRuolo(),
                java.sql.Types.VARCHAR);
        
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[RuoloLowDaoImpl::insert] END");
        }

        return dto.createPk();

    }

    /**
     * Updates a single row in the DMACC_D_RUOLO table.
     * 
     * @generated
     */
    public void update(RuoloLowDto dto) throws RuoloLowDaoException {
        final String sql = "UPDATE "
                + getTableName()
                + " SET CODICE_RUOLO = :CODICE_RUOLO ,"
                + "DESCRIZIONE_RUOLO = :DESCRIZIONE_RUOLO ,"
                + "DATA_INSERIMENTO = :DATA_INSERIMENTO ,"
                + "FLAG_VISIBILE_PER_CONSENSO = :FLAG_VISIBILE_PER_CONSENSO , "
                + "DATAAGGIORNAMENTO = :DATAAGGIORNAMENTO, "
                + "CODICE_RUOLO_INI = :CODICE_RUOLO_INI, "
                + "DESCRIZIONE_RUOLO_INI = :DESCRIZIONE_RUOLO_INI ,"
                + "RUOLO_DPCM = :RUOLO_DPCM , "
                + "CATEGORIA_RUOLO = :CATEGORIA_RUOLO  "
                + " WHERE ID = :ID ";

        if (dto.getId() == null) {
            log.error("[RuoloLowDaoImpl::update] chiave primaria non impostata");
            throw new RuoloLowDaoException("Chiave primaria non impostata");
        }

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", dto.getId(), java.sql.Types.BIGINT);

        params.addValue("CODICE_RUOLO", dto.getCodiceRuolo(),
                java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE_RUOLO", dto.getDescrizioneRuolo(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_INSERIMENTO", dto.getDataInserimento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("FLAG_VISIBILE_PER_CONSENSO",
                dto.getFlagVisibilePerConsenso(), java.sql.Types.VARCHAR);

        params.addValue("DATAAGGIORNAMENTO", dto.getDataAggiornamento(),
                java.sql.Types.TIMESTAMP);

        params.addValue("CODICE_RUOLO_INI", dto.getCodiceRuoloINI(),
                java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE_RUOLO_INI", dto.getDescrizioneRuoloINI(),
                java.sql.Types.VARCHAR);
        
        params.addValue("RUOLO_DPCM", dto.getRuoloDPCM(),
                java.sql.Types.VARCHAR);
        
        params.addValue("CATEGORIA_RUOLO", dto.getCategoriaRuolo(),
                java.sql.Types.VARCHAR);

        
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::update] esecuzione query", ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "update",
                    "esecuzione query", sql);
            log.debug("[RuoloLowDaoImpl::update] END");
        }
    }

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return RuoloLowDto
     * @generated
     */
    public RuoloLowDto mapRow(ResultSet rs, int row) throws SQLException {
        RuoloLowDto dto = new RuoloLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }

    /**
     * Method 'mapRow_internal'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return RuoloLowDto
     * @generated
     */
    public RuoloLowDto mapRow_internal(RuoloLowDto objectToFill, ResultSet rs,
            int row) throws SQLException {
        RuoloLowDto dto = objectToFill;

        RuoloLowDaoRowMapper ruoloLowDaoRowMapper = new RuoloLowDaoRowMapper(
                null, RuoloLowDto.class);

        return ruoloLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }

    protected RuoloLowDaoRowMapper findByPrimaryKeyRowMapper = new RuoloLowDaoRowMapper(
            null, RuoloLowDto.class);

    protected RuoloLowDaoRowMapper ByCodiceRowMapper = new RuoloLowDaoRowMapper(
            null, RuoloLowDto.class);

    protected RuoloLowDaoRowMapper findAllRowMapper = new RuoloLowDaoRowMapper(
            null, RuoloLowDto.class);

    protected RuoloLowDaoRowMapper RuoliPerConsensoRowMapper = new RuoloLowDaoRowMapper(
            null, RuoloLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_D_RUOLO";
    }

    /**
     * Returns all rows from the DMACC_D_RUOLO table that match the primary key
     * criteria
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public RuoloLowDto findByPrimaryKey(RuoloLowPk pk)
            throws RuoloLowDaoException {

        final StringBuilder sql = new StringBuilder(
                "SELECT ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO,DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO  FROM "
                        + getTableName() + " WHERE ID = :ID ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", pk.getId(), java.sql.Types.BIGINT);

        List<RuoloLowDto> list = null;

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params,
                    findByPrimaryKeyRowMapper);
        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::findByPrimaryKey] esecuzione query",
                    ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "findByPrimaryKey",
                    "esecuzione query", sql.toString());
            log.debug("[RuoloLowDaoImpl::findByPrimaryKey] END");
        }

        return list.size() == 0 ? null : list.get(0);

    }

    /**
     * Implementazione del finder ByCodice
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findByCodice(String input)
            throws RuoloLowDaoException {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource();

        sql.append("SELECT ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO, DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO  ");
        sql.append(" FROM DMACC_D_RUOLO");
        sql.append(" WHERE ");
        /* PROTECTED REGION ID(R637884211) ENABLED START */
        // personalizzare la query SQL relativa al finder

        // personalizzare l'elenco dei parametri da passare al jdbctemplate
        // (devono corrispondere in tipo e
        // numero ai parametri definiti nella queryString)
        sql.append("CODICE_RUOLO = :codice");
        /* PROTECTED REGION END */
        //CONTROLLO ANCHE CODICE INI
        sql.append(" OR CODICE_RUOLO_INI = :codiceINI");

        /* PROTECTED REGION ID(R-1575539689) ENABLED START */
        // ***aggiungere tutte le condizioni

        paramMap.addValue("codice", input);
        paramMap.addValue("codiceINI", input);

        /* PROTECTED REGION END */
        List<RuoloLowDto> list = null;
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), paramMap,
                    ByCodiceRowMapper);

        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::findByCodice] esecuzione query", ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "findByCodice",
                    "esecuzione query", sql.toString());
            log.debug("[RuoloLowDaoImpl::findByCodice] END");
        }
        return list;
    }

    public List<RuoloLowDto> findByCodiceFSE(String input)
            throws RuoloLowDaoException {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource();

        sql.append("SELECT ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO, DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO  ");
        sql.append(" FROM DMACC_D_RUOLO");
        sql.append(" WHERE ");
        /* PROTECTED REGION ID(R637884211) ENABLED START */
        // personalizzare la query SQL relativa al finder

        // personalizzare l'elenco dei parametri da passare al jdbctemplate
        // (devono corrispondere in tipo e
        // numero ai parametri definiti nella queryString)
        sql.append(" CODICE_RUOLO = :codice");
        /* PROTECTED REGION END */
        //CONTROLLO ANCHE CODICE INI

        /* PROTECTED REGION ID(R-1575539689) ENABLED START */
        // ***aggiungere tutte le condizioni

        paramMap.addValue("codice", input);

        /* PROTECTED REGION END */
        List<RuoloLowDto> list = null;
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), paramMap,
                    ByCodiceRowMapper);

        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::findByCodice] esecuzione query", ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "findByCodice",
                    "esecuzione query", sql.toString());
            log.debug("[RuoloLowDaoImpl::findByCodice] END");
        }
        return list;
    }

    /**
     * Returns all rows from the DMACC_D_RUOLO table that match the criteria ''.
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findAll() throws RuoloLowDaoException {
        final StringBuilder sql = new StringBuilder(
                "SELECT ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO , DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO FROM "
                        + getTableName());

        MapSqlParameterSource params = new MapSqlParameterSource();

        List<RuoloLowDto> list = null;

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params, findAllRowMapper);
        } catch (RuntimeException ex) {
            log.error("[RuoloLowDaoImpl::findAll] esecuzione query", ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "findAll",
                    "esecuzione query", sql.toString());
            log.debug("[RuoloLowDaoImpl::findAll] END");
        }

        return list;

    }

    /**
     * Implementazione del finder RuoliPerConsenso
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findRuoliPerConsenso(java.lang.String input)
            throws RuoloLowDaoException {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource();

        sql.append("SELECT ID,CODICE_RUOLO,DESCRIZIONE_RUOLO,DATA_INSERIMENTO,FLAG_VISIBILE_PER_CONSENSO , DATAAGGIORNAMENTO, CODICE_RUOLO_INI, DESCRIZIONE_RUOLO_INI, RUOLO_DPCM, CATEGORIA_RUOLO ");
        sql.append(" FROM DMACC_D_RUOLO");
        sql.append(" WHERE ");
        /* PROTECTED REGION ID(R-967490413) ENABLED START */
        // personalizzare la query SQL relativa al finder

        // personalizzare l'elenco dei parametri da passare al jdbctemplate
        // (devono corrispondere in tipo e
        // numero ai parametri definiti nella queryString)
        sql.append("FLAG_VISIBILE_PER_CONSENSO = :flagConsenso");
        /* PROTECTED REGION END */

        /* PROTECTED REGION ID(R197454519) ENABLED START */
        // ***aggiungere tutte le condizioni

        paramMap.addValue("flagConsenso", input);

        /* PROTECTED REGION END */
        List<RuoloLowDto> list = null;
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), paramMap,
                    RuoliPerConsensoRowMapper);

        } catch (RuntimeException ex) {
            log.error(
                    "[RuoloLowDaoImpl::findRuoliPerConsenso] esecuzione query",
                    ex);
            throw new RuoloLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("RuoloLowDaoImpl", "findRuoliPerConsenso",
                    "esecuzione query", sql.toString());
            log.debug("[RuoloLowDaoImpl::findRuoliPerConsenso] END");
        }
        return list;
    }

    // / flexible row mapper.
    public class RuoloLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public RuoloLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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
         * @return RuoloLowDto
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

            if (dto instanceof RuoloLowDto) {
                return mapRow_internal((RuoloLowDto) dto, rs, row);
            }

            return dto;
        }

        public RuoloLowDto mapRow_internal(RuoloLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
            RuoloLowDto dto = objectToFill;

            if (mapAllColumns || columnsToReadMap.get("ID") != null)
                dto.setId((Long) rs.getObject("ID"));

            if (mapAllColumns || columnsToReadMap.get("CODICE_RUOLO") != null)
                dto.setCodiceRuolo(rs.getString("CODICE_RUOLO"));

            if (mapAllColumns
                    || columnsToReadMap.get("DESCRIZIONE_RUOLO") != null)
                dto.setDescrizioneRuolo(rs.getString("DESCRIZIONE_RUOLO"));

            if (mapAllColumns
                    || columnsToReadMap.get("DATA_INSERIMENTO") != null)
                dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

            if (mapAllColumns
                    || columnsToReadMap.get("FLAG_VISIBILE_PER_CONSENSO") != null)
                dto.setFlagVisibilePerConsenso(rs
                        .getString("FLAG_VISIBILE_PER_CONSENSO"));

            if (mapAllColumns
                    || columnsToReadMap.get("DATAAGGIORNAMENTO") != null)
                dto.setDataAggiornamento(rs.getTimestamp("DATAAGGIORNAMENTO"));
            
            if (mapAllColumns
                    || columnsToReadMap.get("CODICE_RUOLO_INI") != null)
                dto.setCodiceRuoloINI(rs.getString("CODICE_RUOLO_INI"));
            
            if (mapAllColumns
                    || columnsToReadMap.get("DESCRIZIONE_RUOLO_INI") != null)
                dto.setDescrizioneRuoloINI(rs.getString("DESCRIZIONE_RUOLO_INI"));
            
            if (mapAllColumns
                    || columnsToReadMap.get("RUOLO_DPCM") != null)
                dto.setRuoloDPCM(rs.getString("RUOLO_DPCM"));
            

            if (mapAllColumns || columnsToReadMap.get("CATEGORIA_RUOLO") != null)
                dto.setCategoriaRuolo(rs.getString("CATEGORIA_RUOLO"));

            return dto;
        }

    }

}
