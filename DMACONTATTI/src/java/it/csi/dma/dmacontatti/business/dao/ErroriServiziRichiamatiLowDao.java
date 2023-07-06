/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.ErroriServiziRichiamatiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ErroriServiziRichiamatiLowPk;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface ErroriServiziRichiamatiLowDao {



    public ErroriServiziRichiamatiLowPk insert(ErroriServiziRichiamatiLowDto dto);



    public ErroriServiziRichiamatiLowDto mapRow(ResultSet rs, int row) throws SQLException;


    public ErroriServiziRichiamatiLowDto mapRow_internal(ErroriServiziRichiamatiLowDto objectToFill,
                                                         ResultSet rs, int row) throws SQLException;
    
    
}
