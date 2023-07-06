/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import it.csi.dma.dmacontatti.business.dao.dto.CatalogoLogLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.CatalogoLogLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @generated
 */
public interface CatalogoLogLowDao {

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return CatalogoLogLowDto
	 * @generated
	 */
	public CatalogoLogLowDto mapRow(ResultSet rs, int row) throws SQLException;

	/**
	 * Method 'mapRow_internal'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return CatalogoLogLowDto
	 * @generated
	 */
	public CatalogoLogLowDto mapRow_internal(CatalogoLogLowDto objectToFill,
                                             ResultSet rs, int row) throws SQLException;
	/** 
	 * Implementazione del finder byCodice
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public CatalogoLogLowDto findByCodice(String input)
			throws CatalogoLogLowDaoException;

}
