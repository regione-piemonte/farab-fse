/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ConfigurazioneLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @generated
 */
public interface ConfigurazioneLowDao {

    /**
     * Method 'insert'
     * 
     * @param dto
     * @return ConfigurazioneLowPk
     * @generated
     */

    public ConfigurazioneLowPk insert(ConfigurazioneLowDto dto)

    ;

    /**
     * Updates a single row in the DMACC_T_CONFIGURAZIONE table.
     * 
     * @generated
     */
    public void update(ConfigurazioneLowDto dto)
            throws ConfigurazioneLowDaoException;

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
            throws SQLException;

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
            throws SQLException;

    /**
     * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
     * criteria ''.
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<ConfigurazioneLowDto> findAll()
            throws ConfigurazioneLowDaoException;

    /**
     * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
     * primary key criteria
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public ConfigurazioneLowDto findByPrimaryKey(ConfigurazioneLowPk pk)
            throws ConfigurazioneLowDaoException;

    /**
     * Implementazione del finder ByCodice
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<ConfigurazioneLowDto> findByCodice(String input)
            throws ConfigurazioneLowDaoException;

}
