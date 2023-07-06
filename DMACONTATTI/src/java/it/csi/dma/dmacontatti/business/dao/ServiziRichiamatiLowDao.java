/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.ServiziRichiamatiLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.ServiziRichiamatiLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface ServiziRichiamatiLowDao {



    public ServiziRichiamatiLowDto insert(ServiziRichiamatiLowDto dto);

    public ServiziRichiamatiLowDto mapRow(ResultSet rs, int row) throws SQLException;


    public ServiziRichiamatiLowDto mapRow_internal(ServiziRichiamatiLowDto objectToFill,
                                                   ResultSet rs, int row) throws SQLException;
    
    public void update(ServiziRichiamatiLowDto ServiziRichiamatio) throws ServiziRichiamatiLowDaoException;
   
   
}
