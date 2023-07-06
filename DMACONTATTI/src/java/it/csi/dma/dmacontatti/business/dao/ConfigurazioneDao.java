/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ConfigurazioneLowDaoException;

import java.util.List;

public interface ConfigurazioneDao {
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ConfigurazioneLowPk
	 * @generated
	 */

	public ConfigurazioneLowPk insert(ConfigurazioneLowDto dto)

	;

	/**
	 * Updates a single row in the DMACC_T_CONFIGURAZIONE table.
	 * 
	 * @generated
	 */
	public void update(ConfigurazioneLowDto dto)
			throws ConfigurazioneLowDaoException;

	/**
	 * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
	 * criteria ''.
	 * 
	 * @generated
	 */
	public List<ConfigurazioneLowDto> findAll()
			throws ConfigurazioneLowDaoException;

	/**
	 * Returns all rows from the DMACC_T_CONFIGURAZIONE table that match the
	 * primary key criteria
	 * 
	 * @generated
	 */

	public ConfigurazioneLowDto findByPrimaryKey(ConfigurazioneLowPk pk)
			throws ConfigurazioneLowDaoException;

	/**
	 * Implementazione del finder ByCodice
	 * 
	 * @generated
	 */
	public ConfigurazioneLowDto findByCodice(String input)
			throws ConfigurazioneLowDaoException;
	
	public ConfigurazioneLowDto findByCodiceNoCache(String input)
			throws ConfigurazioneLowDaoException;

	
	public  void updateConf(String codice, String value) 
			throws ConfigurazioneLowDaoException;
}
