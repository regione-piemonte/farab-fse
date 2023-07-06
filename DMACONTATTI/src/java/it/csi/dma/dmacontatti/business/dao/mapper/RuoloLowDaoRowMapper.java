/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.mapper;

import it.csi.dma.dmacontatti.business.dao.RuoloLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.RuoloLowDto;
import it.csi.dma.dmacontatti.business.dao.impl.RuoloLowDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper specifico del DAO RuoloLowDao
 * 
 * @generated
 */
public class RuoloLowDaoRowMapper extends BaseDaoRowMapper implements
        org.springframework.jdbc.core.RowMapper {

    /**
     * Dao associato al RowMapper. Serve per i supplier DAO
     * 
     * @generated
     */
    RuoloLowDaoImpl dao;

    /**
     * costruttore
     * 
     * @param columnsToRead
     *            elenco delle colonne da includere nel mapping (per query
     *            incomplete, esempio distinct, custom select...) nella classe
     *            padre
     */
    public RuoloLowDaoRowMapper(String[] columnsToRead, Class dtoClass,
            RuoloLowDao dao) {
        super(columnsToRead, dtoClass);
        this.dao = (RuoloLowDaoImpl) dao;
    }

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return RuoloLowDto
     * @generated
     */
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Object dtoInstance = getNewDto();

        if (dtoInstance instanceof RuoloLowDto) {
            return mapRow_internal((RuoloLowDto) dtoInstance, rs, row);
        }

        return dtoInstance;
    }

    public RuoloLowDto mapRow_internal(RuoloLowDto objectToFill, ResultSet rs,
            int row) throws SQLException

    {
        RuoloLowDto dto = objectToFill;

        if (mapAllColumns || columnsToReadMap.get("ID") != null)
            dto.setId((Long) rs.getObject("ID"));

        if (mapAllColumns || columnsToReadMap.get("CODICE_RUOLO") != null)
            dto.setCodiceRuolo(rs.getString("CODICE_RUOLO"));

        if (mapAllColumns || columnsToReadMap.get("DESCRIZIONE_RUOLO") != null)
            dto.setDescrizioneRuolo(rs.getString("DESCRIZIONE_RUOLO"));

        if (mapAllColumns || columnsToReadMap.get("DATA_INSERIMENTO") != null)
            dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

        if (mapAllColumns
                || columnsToReadMap.get("FLAG_VISIBILE_PER_CONSENSO") != null)
            dto.setFlagVisibilePerConsenso(rs
                    .getString("FLAG_VISIBILE_PER_CONSENSO"));

        if (mapAllColumns || columnsToReadMap.get("DATAAGGIORNAMENTO") != null)
            dto.setDataAggiornamento(rs.getTimestamp("DATAAGGIORNAMENTO"));
        
        if (mapAllColumns || columnsToReadMap.get("CODICE_RUOLO_INI") != null)
            dto.setCodiceRuoloINI(rs.getString("CODICE_RUOLO_INI"));

        if (mapAllColumns || columnsToReadMap.get("DESCRIZIONE_RUOLO_INI") != null)
            dto.setDescrizioneRuoloINI(rs.getString("DESCRIZIONE_RUOLO_INI"));
        
        if (mapAllColumns || columnsToReadMap.get("RUOLO_DPCM") != null)
            dto.setRuoloDPCM(rs.getString("RUOLO_DPCM"));
        
        if (mapAllColumns || columnsToReadMap.get("CATEGORIA_RUOLO") != null)
            dto.setCategoriaRuolo(rs.getString("CATEGORIA_RUOLO"));
        
        return dto;
    }

}
