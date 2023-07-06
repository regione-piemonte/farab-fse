/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.mapper;


import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper specifico del DAO PazienteLowDao
 * 
 * @generated
 */
public class PazienteLowDaoRowMapper extends BaseDaoRowMapper implements
        org.springframework.jdbc.core.RowMapper {

    /**
     * costruttore
     * 
     * @param columnsToRead
     *            elenco delle colonne da includere nel mapping (per query
     *            incomplete, esempio distinct, custom select...) nella classe
     *            padre
     */
    public PazienteLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
        super(columnsToRead, dtoClass);
    }

    /**
     * Method 'mapRow'
     * 
     * @param rs
     * @param row
     * @throws SQLException
     * @return PazienteLowDto
     * @generated
     */
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Object dtoInstance = getNewDto();

        if (dtoInstance instanceof PazienteLowDto) {
            return mapRow_internal((PazienteLowDto) dtoInstance, rs, row);
        }

        return dtoInstance;
    }

    public PazienteLowDto mapRow_internal(PazienteLowDto objectToFill,
            ResultSet rs, int row) throws SQLException

    {
        PazienteLowDto dto = objectToFill;

        if (mapAllColumns || columnsToReadMap.get("ID_PAZIENTE") != null)
            dto.setIdPaziente((Long) rs.getObject("ID_PAZIENTE"));

        if (mapAllColumns || columnsToReadMap.get("ID_AURA") != null)
            dto.setIdAura((Long) rs.getObject("ID_AURA"));

        if (mapAllColumns || columnsToReadMap.get("COGNOME") != null)
            dto.setCognome(rs.getString("COGNOME"));

        if (mapAllColumns || columnsToReadMap.get("NOME") != null)
            dto.setNome(rs.getString("NOME"));

        if (mapAllColumns || columnsToReadMap.get("DATA_NASCITA") != null)
            dto.setDataNascita(rs.getDate("DATA_NASCITA"));

        if (mapAllColumns || columnsToReadMap.get("CODICE_FISCALE") != null)
            dto.setCodiceFiscale(rs.getString("CODICE_FISCALE"));

        if (mapAllColumns || columnsToReadMap.get("SESSO") != null)
            dto.setSesso(rs.getString("SESSO"));

        if (mapAllColumns || columnsToReadMap.get("DATA_DECESSO") != null)
            dto.setDataDecesso(rs.getDate("DATA_DECESSO"));

        if (mapAllColumns || columnsToReadMap.get("DATA_INSERIMENTO") != null)
            dto.setDataInserimento(rs.getTimestamp("DATA_INSERIMENTO"));

        if (mapAllColumns || columnsToReadMap.get("DATA_AGGIORNAMENTO") != null)
            dto.setDataAggiornamento(rs.getTimestamp("DATA_AGGIORNAMENTO"));

        if (mapAllColumns || columnsToReadMap.get("ID_COMUNE_NASCITA") != null)
            dto.setIdComuneNascita((Long) rs.getObject("ID_COMUNE_NASCITA"));

        if (mapAllColumns || columnsToReadMap.get("ID_STATO_NASCITA") != null)
            dto.setIdStatoNascita((Long) rs.getObject("ID_STATO_NASCITA"));

        if (mapAllColumns || columnsToReadMap.get("INDIRIZZO_EMAIL") != null)
            dto.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));

        if (mapAllColumns || columnsToReadMap.get("NUMERO_TELEFONO") != null)
            dto.setNumeroTelefono(rs.getString("NUMERO_TELEFONO"));

        if (mapAllColumns || columnsToReadMap.get("FLAG_EMAIL_ACCESSO") != null)
            dto.setFlagEmailAccesso(rs.getString("FLAG_EMAIL_ACCESSO"));

        if (mapAllColumns || columnsToReadMap.get("CODICE_FISCALE_MMG") != null)
            dto.setCodiceFiscaleMmg(rs.getString("CODICE_FISCALE_MMG"));

        if (mapAllColumns || columnsToReadMap.get("ID_AURA_MMG") != null)
            dto.setIdAuraMmg((Long) rs.getObject("ID_AURA_MMG"));

        if (mapAllColumns || columnsToReadMap.get("COGNOME_MMG") != null)
            dto.setCognomeMmg(rs.getString("COGNOME_MMG"));

        if (mapAllColumns || columnsToReadMap.get("NOME_MMG") != null)
            dto.setNomeMmg(rs.getString("NOME_MMG"));

        if (mapAllColumns || columnsToReadMap.get("DATA_ANNULLAMENTO") != null)
            dto.setDataAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));

        return dto;
    }

}
