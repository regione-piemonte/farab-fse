/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;


import it.csi.dma.dmacontatti.business.dao.dto.LogLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.LogLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.LogLowDaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @generated
 */
public interface LogLowDao {

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return LogLowPk
	 * @generated
	 */

	public LogLowPk insert(LogLowDto dto);

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return LogLowDto
	 * @generated
	 */
	public LogLowDto mapRow(ResultSet rs, int row) throws SQLException;

	/**
	 * Method 'mapRow_internal'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return LogLowDto
	 * @generated
	 */
	public LogLowDto mapRow_internal(LogLowDto objectToFill, ResultSet rs,
                                     int row) throws SQLException;

	/**
	 * Returns all rows from the DMACL_T_LOG table that match the criteria ''.
	 * 
	 * @generated
	 */
	public List<LogLowDto> findAll() throws LogLowDaoException;

	/**
	 * Returns all rows from the DMACL_T_LOG table that match the primary key
	 * criteria
	 * 
	 * @generated
	 */
	public LogLowDto findByPrimaryKey(LogLowPk pk) throws LogLowDaoException;

}
