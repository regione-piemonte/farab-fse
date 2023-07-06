/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.*;
import it.csi.dma.dmacontatti.business.dao.exceptions.PazienteLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @generated
 */
public interface PazienteLowDao {

    /**
     * Method 'insert'
     * 
     * @param dto
     * @return PazienteLowPk
     * @generated
     */

    public PazienteLowPk insert(PazienteLowDto dto)

    ;

    /**
     * Custom updater in the DMACC_T_PAZIENTE table.
     * 
     * @generated
     */
    public void customUpdaterUpdatePaziente(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto filter,
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto value)
            throws PazienteLowDaoException;

    /**
     * Updates a single row in the DMACC_T_PAZIENTE table.
     * 
     * @generated
     */
    public void update(PazienteLowDto dto) throws PazienteLowDaoException;

    /**
     * Deletes a single row in the DMACC_T_PAZIENTE table.
     * 
     * @generated
     */

    public void delete(PazienteLowPk pk) throws PazienteLowDaoException;

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return PazienteLowDto
     * @generated
     */
    public PazienteLowDto mapRow(ResultSet rs, int row) throws SQLException;

    /**
     * Method 'mapRow_internal'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return PazienteLowDto
     * @generated
     */
    public PazienteLowDto mapRow_internal(PazienteLowDto objectToFill,
            ResultSet rs, int row) throws SQLException;

    /**
     * Returns all rows from the DMACC_T_PAZIENTE table that match the primary
     * key criteria
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public PazienteLowDto findByPrimaryKey(PazienteLowPk pk)
            throws PazienteLowDaoException;

    /**
     * Implementazione del finder findByCF
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<PazienteLowDto> findFindByCF(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;

    
    @SuppressWarnings("unchecked")
    public List<PazienteLowDto> findFindByCF_INI(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;

   
    /**
     * Implementazione del finder byIdAura
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<PazienteLowDto> findByIdAura(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;

    /**
     * Implementazione del finder ByIdAuraAll
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<PazienteLowDto> findByIdAuraAll(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;

    /**
     * Implementazione del finder byParams
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<PazienteLowDto> findByParams(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;
    
    public List<PazienteLowDto> findByCFAll(
            it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
            throws PazienteLowDaoException;

	public List<PazienteLowDto> findByCodiceFiscale(String codiceFiscale) throws PazienteLowDaoException;
}
