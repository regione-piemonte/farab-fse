/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.CatalogoServiziOperationLowDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @generated
 */
public interface CatalogoServiziOperationLowDao {

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return CatalogoLogAuditCCLowDto
     * @generated
     */
    public CatalogoServiziOperationLowDto mapRow(ResultSet rs, int row)
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
    public CatalogoServiziOperationLowDto mapRow_internal(
            CatalogoServiziOperationLowDto objectToFill, ResultSet rs, int row)
            throws SQLException;

  

    public CatalogoServiziOperationLowDto findByNomeSevrvizioAndNomeOperation(CatalogoServiziOperationLowDto input) throws Exception;
    
    
    public CatalogoServiziOperationLowDto findByCodiceServizio(CatalogoServiziOperationLowDto input) throws Exception;

}
