/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.mapper;


import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.impl.ConfigurazioneLowDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper specifico del DAO ConfigurazioneLowDao
 * 
 * @generated
 */
public class ConfigurazioneLowDaoRowMapper extends BaseDaoRowMapper implements
        org.springframework.jdbc.core.RowMapper {

    /**
     * Dao associato al RowMapper. Serve per i supplier DAO
     * 
     * @generated
     */
    ConfigurazioneLowDaoImpl dao;

    /**
     * costruttore
     * 
     * @param columnsToRead
     *            elenco delle colonne da includere nel mapping (per query
     *            incomplete, esempio distinct, custom select...) nella classe
     *            padre
     */
    public ConfigurazioneLowDaoRowMapper(String[] columnsToRead,
            Class dtoClass, ConfigurazioneLowDao dao) {
        super(columnsToRead, dtoClass);
        this.dao = (ConfigurazioneLowDaoImpl) dao;
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
        Object dtoInstance = getNewDto();

        if (dtoInstance instanceof ConfigurazioneLowDto) {
            return mapRow_internal((ConfigurazioneLowDto) dtoInstance, rs, row);
        }

        return dtoInstance;
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
