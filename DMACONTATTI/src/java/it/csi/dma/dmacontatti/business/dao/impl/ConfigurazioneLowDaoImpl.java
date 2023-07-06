/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ConfigurazioneLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.util.performance.StopWatch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/*PROTECTED REGION ID(R1710634988) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class ConfigurazioneLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ConfigurazioneLowDto>, ConfigurazioneLowDao {
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
     * @return ConfigurazioneLowPk
     * @generated
     */

    public ConfigurazioneLowPk insert(ConfigurazioneLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());

        final String sql = "INSERT INTO "
                + getTableName()
                + " ( 	ID,CHIAVE,VALORE,DESCRIZIONE ) VALUES (  :ID , :CHIAVE , :VALORE , :DESCRIZIONE  )";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", newKey, java.sql.Types.BIGINT);

        params.addValue("CHIAVE", dto.getChiave(), java.sql.Types.VARCHAR);

        params.addValue("VALORE", dto.getValore(), java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE", dto.getDescrizione(),
                java.sql.Types.VARCHAR);

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ConfigurazioneLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[ConfigurazioneLowDaoImpl::insert] END");
        }

        dto.setId(newKey);
        return dto.createPk();

    }

    /**
     * Updates a single row in the DMACC_T_CONFIGURAZIONE table.
     * 
     * @generated
     */
    public void update(ConfigurazioneLowDto dto)
            throws ConfigurazioneLowDaoException {
        final String sql = "UPDATE "
                + getTableName()
                + " SET CHIAVE = :CHIAVE ,VALORE = :VALORE ,DESCRIZIONE = :DESCRIZIONE  WHERE ID = :ID ";

        if (dto.getId() == null) {
            log.error("[ConfigurazioneLowDaoImpl::update] chiave primaria non impostata");
            throw new ConfigurazioneLowDaoException(
                    "Chiave primaria non impostata");
        }

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", dto.getId(), java.sql.Types.BIGINT);

        params.addValue("CHIAVE", dto.getChiave(), java.sql.Types.VARCHAR);

        params.addValue("VALORE", dto.getValore(), java.sql.Types.VARCHAR);

        params.addValue("DESCRIZIONE", dto.getDescrizione(),
                java.sql.Types.VARCHAR);

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[ConfigurazioneLowDaoImpl::update] esecuzione query", ex);
            throw new ConfigurazioneLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl", "update",
                    "esecuzione query", sql);
            log.debug("[ConfigurazioneLowDaoImpl::update] END");
        }
    }

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return ConfigurazioneLowDto
     * @generated
     */
    public ConfigurazioneLowDto mapRow(ResultSet rs, int row)
            throws SQLException {
        ConfigurazioneLowDto dto = new ConfigurazioneLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }

    /**
     * Method 'mapRow_internal'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return ConfigurazioneLowDto
     * @generated
     */
    public ConfigurazioneLowDto mapRow_internal(
            ConfigurazioneLowDto objectToFill, ResultSet rs, int row)
            throws SQLException {
        ConfigurazioneLowDto dto = objectToFill;

        ConfigurazioneLowDaoRowMapper configurazioneLowDaoRowMapper = new ConfigurazioneLowDaoRowMapper(
                null, ConfigurazioneLowDto.class);

        return configurazioneLowDaoRowMapper.mapRow_internal(objectToFill, rs,
                row);
    }

    protected ConfigurazioneLowDaoRowMapper findAllRowMapper = new ConfigurazioneLowDaoRowMapper(
            null, ConfigurazioneLowDto.class);

    protected ConfigurazioneLowDaoRowMapper findByPrimaryKeyRowMapper = new ConfigurazioneLowDaoRowMapper(
            null, ConfigurazioneLowDto.class);

    protected ConfigurazioneLowDaoRowMapper ByCodiceRowMapper = new ConfigurazioneLowDaoRowMapper(
            null, ConfigurazioneLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_T_CONFIGURAZIONE";
    }

    /**
     * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
     * criteria ''.
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<ConfigurazioneLowDto> findAll()
            throws ConfigurazioneLowDaoException {
        final StringBuilder sql = new StringBuilder(
                "SELECT ID,CHIAVE,VALORE,DESCRIZIONE FROM " + getTableName());

        MapSqlParameterSource params = new MapSqlParameterSource();

        List<ConfigurazioneLowDto> list = null;

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params, findAllRowMapper);
        } catch (RuntimeException ex) {
            log.error("[ConfigurazioneLowDaoImpl::findAll] esecuzione query",
                    ex);
            throw new ConfigurazioneLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl", "findAll",
                    "esecuzione query", sql.toString());
            log.debug("[ConfigurazioneLowDaoImpl::findAll] END");
        }

        return list;

    }

    /**
     * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
     * primary key criteria
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public ConfigurazioneLowDto findByPrimaryKey(ConfigurazioneLowPk pk)
            throws ConfigurazioneLowDaoException {

        final StringBuilder sql = new StringBuilder(
                "SELECT ID,CHIAVE,VALORE,DESCRIZIONE FROM " + getTableName()
                        + " WHERE ID = :ID ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID", pk.getId(), java.sql.Types.BIGINT);

        List<ConfigurazioneLowDto> list = null;

        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), params,
                    findByPrimaryKeyRowMapper);
        } catch (RuntimeException ex) {
            log.error(
                    "[ConfigurazioneLowDaoImpl::findByPrimaryKey] esecuzione query",
                    ex);
            throw new ConfigurazioneLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl",
                    "findByPrimaryKey", "esecuzione query", sql.toString());
            log.debug("[ConfigurazioneLowDaoImpl::findByPrimaryKey] END");
        }

        return list.size() == 0 ? null : list.get(0);

    }

    /**
     * Implementazione del finder ByCodice
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<ConfigurazioneLowDto> findByCodice(String input)
            throws ConfigurazioneLowDaoException {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource();

        sql.append("SELECT ID,CHIAVE,VALORE,DESCRIZIONE ");
        sql.append(" FROM DMACC_T_CONFIGURAZIONE");
        sql.append(" WHERE ");
        /* PROTECTED REGION ID(R-1143339751) ENABLED START */
        // personalizzare la query SQL relativa al finder

        // personalizzare l'elenco dei parametri da passare al jdbctemplate
        // (devono corrispondere in tipo e
        // numero ai parametri definiti nella queryString)
        sql.append("chiave = :nome");
        /* PROTECTED REGION END */
		log.info("Codice configurazione nome parametro: " + input + " query: " + sql.toString());
        /* PROTECTED REGION ID(R-958907663) ENABLED START */
        // ***aggiungere tutte le condizioni

        paramMap.addValue("nome", input);

        /* PROTECTED REGION END */
        List<ConfigurazioneLowDto> list = null;
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            list = jdbcTemplate.query(sql.toString(), paramMap,
                    ByCodiceRowMapper);

        } catch (RuntimeException ex) {
            log.error(
                    "[ConfigurazioneLowDaoImpl::findByCodice] esecuzione query",
                    ex);
            throw new ConfigurazioneLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl", "findByCodice",
                    "esecuzione query", sql.toString());
            log.debug("[ConfigurazioneLowDaoImpl::findByCodice] END");
        }
        return list;
    }

    // / flexible row mapper.
    public class ConfigurazioneLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public ConfigurazioneLowDaoRowMapper(String[] columnsToRead,
                Class dtoClass) {
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
         * @return ConfigurazioneLowDto
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

            if (dto instanceof ConfigurazioneLowDto) {
                return mapRow_internal((ConfigurazioneLowDto) dto, rs, row);
            }

            return dto;
        }

        public ConfigurazioneLowDto mapRow_internal(
                ConfigurazioneLowDto objectToFill, ResultSet rs, int row)
                throws SQLException

        {
            ConfigurazioneLowDto dto = objectToFill;

            if (mapAllColumns || columnsToReadMap.get("ID") != null)
                dto.setId((Long) rs.getObject("ID"));

            if (mapAllColumns || columnsToReadMap.get("CHIAVE") != null)
                dto.setChiave(rs.getString("CHIAVE"));

            if (mapAllColumns || columnsToReadMap.get("VALORE") != null)
                dto.setValore(rs.getString("VALORE"));

            if (mapAllColumns || columnsToReadMap.get("DESCRIZIONE") != null)
                dto.setDescrizione(rs.getString("DESCRIZIONE"));

            return dto;
        }

    }

}
