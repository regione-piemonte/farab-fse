/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.RuoloLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.RuoloLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.RuoloLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @generated
 */
public interface RuoloLowDao {

    /**
     * Method 'insert'
     * 
     * @param dto
     * @return RuoloLowPk
     * @generated
     */

    public RuoloLowPk insert(RuoloLowDto dto)

    ;

    /**
     * Updates a single row in the DMACC_D_RUOLO table.
     * 
     * @generated
     */
    public void update(RuoloLowDto dto) throws RuoloLowDaoException;

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return RuoloLowDto
     * @generated
     */
    public RuoloLowDto mapRow(ResultSet rs, int row) throws SQLException;

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
            int row) throws SQLException;

    /**
     * Returns all rows from the DMACC_D_RUOLO table that match the primary key
     * criteria
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public RuoloLowDto findByPrimaryKey(RuoloLowPk pk)
            throws RuoloLowDaoException;

    /**
     * Implementazione del finder ByCodice
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findByCodice(String input)
            throws RuoloLowDaoException;

    public List<RuoloLowDto> findByCodiceFSE(String input)
            throws RuoloLowDaoException;

    /**
     * Returns all rows from the DMACC_D_RUOLO table that match the criteria ''.
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findAll() throws RuoloLowDaoException;

    /**
     * Implementazione del finder RuoliPerConsenso
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<RuoloLowDto> findRuoliPerConsenso(java.lang.String input)
            throws RuoloLowDaoException;

}
