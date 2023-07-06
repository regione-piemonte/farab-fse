/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.ErroriLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriLowPk;

import java.sql.ResultSet;
import java.sql.SQLException;



public interface ErroriLowDao {

 

    public ErroriLowPk insert(ErroriLowDto dto);

   
 
    public ErroriLowDto mapRow(ResultSet rs, int row) throws SQLException;

  
    public ErroriLowDto mapRow_internal(ErroriLowDto objectToFill,
            ResultSet rs, int row) throws SQLException;
    
    
}
