/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.XmlMessaggiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.XmlMessaggiLowPk;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface XmlMessaggiLowDao {

 

    public XmlMessaggiLowPk insert(XmlMessaggiLowDto dto);
    
    public XmlMessaggiLowDto insertLog(XmlMessaggiLowDto dto);
    
 
    public XmlMessaggiLowDto mapRow(ResultSet rs, int row) throws SQLException;

  
    public XmlMessaggiLowDto mapRow_internal(XmlMessaggiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException;
    
    public XmlMessaggiLowDto updateXmlServizio(XmlMessaggiLowDto dto);

   
   
}
