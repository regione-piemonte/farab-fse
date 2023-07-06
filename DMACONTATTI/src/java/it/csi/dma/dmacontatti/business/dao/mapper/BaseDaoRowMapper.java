/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.mapper;


import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;

/**
 * Classe base di tutti i row mapper utilizzati nello strato DAO.
 * 
 * @generated
 */
public class BaseDaoRowMapper {
    protected static final Logger log = Logger
            .getLogger(Constants.APPLICATION_CODE);
    /**
     * @generated
     */
    public final java.util.Map<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
    /**
     * @generated
     */
    public boolean mapAllColumns = true;
    /**
     * @generated
     */
    public Class dtoClass;

    /**
     * @generated
     */
    /**
     * @param columnsToRead
     *            elenco delle colonne da includere nel mapping (per query
     *            incomplete, esempio distinct, custom select...)
     */
    public BaseDaoRowMapper(String[] columnsToRead, Class dtoClass) {
        if (columnsToRead != null) {
            mapAllColumns = false;
            for (int i = 0; i < columnsToRead.length; i++)
                columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
        }
        this.dtoClass = dtoClass;
    }

    /**
     * @generated
     */
    public Object getNewDto() {
        Object dtoInstance = null;

        try {
            dtoInstance = dtoClass.newInstance();
            return dtoInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile istanziare la classe "
                    + dtoClass.getName() + " ," + e.getCause());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile accedere alla classe "
                    + dtoClass.getName() + " ," + e.getCause());
        }
    }

    // there is no specific code for POSGRESQL

}
