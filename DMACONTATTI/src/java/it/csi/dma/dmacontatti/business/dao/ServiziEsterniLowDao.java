/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.ServiziEsterniLowDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @generated
 */
public interface ServiziEsterniLowDao {

    /**
     * Method 'mapRow'
     *
     * @param rs
     * @param row
     * @throws SQLException
     * @return CatalogoLogAuditCCLowDto
     * @generated
     */
    public ServiziEsterniLowDto mapRow(ResultSet rs, int row)
            throws SQLException;

    /**
     * Method 'mapRow_internal'
     *
     * @param rs
     * @param row
     * @throws SQLException
     * @return CatalogoLogAuditCCLowDto
     * @generated
     */
    public ServiziEsterniLowDto mapRow_internal(
            ServiziEsterniLowDto objectToFill, ResultSet rs, int row)
            throws SQLException;
    
    
    public ServiziEsterniLowDto findByCodiceServizio(String codiceServizio) throws Exception;

}
