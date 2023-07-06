/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.CredenzialiServiziLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.CredenzialiServiziLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @generated
 */
public interface CredenzialiServiziLowDao {

    public CredenzialiServiziLowDto mapRow(ResultSet rs, int row) throws SQLException;

    /**
     * Method 'mapRow_internal'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return ConsensoCCDto
     * @generated
     */
    public CredenzialiServiziLowDto mapRow_internal(CredenzialiServiziLowDto objectToFill,
                                                    ResultSet rs, int row) throws SQLException;

    /**
     * Implementazione del finder ByIdPaziente
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    public List<CredenzialiServiziLowDto> findByCodiceServizioUserPassword(
            CredenzialiServiziLowDto input)
            throws CredenzialiServiziLowDaoException;


    List<CredenzialiServiziLowDto> findByCodiceServizioUser(
            CredenzialiServiziLowDto input)
            throws CredenzialiServiziLowDaoException;
}
