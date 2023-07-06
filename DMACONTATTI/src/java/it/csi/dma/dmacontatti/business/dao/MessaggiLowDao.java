/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.MessaggiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.MessaggiLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.MessaggiLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;



public interface MessaggiLowDao {

 

    public MessaggiLowPk insert(MessaggiLowDto dto);
    
    public MessaggiLowDto insertLog(MessaggiLowDto dto);
 
    public MessaggiLowDto mapRow(ResultSet rs, int row) throws SQLException;

  
    public MessaggiLowDto mapRow_internal(MessaggiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException;
    
    public void update(MessaggiLowDto messaggio) throws MessaggiLowDaoException;
   
   
}
