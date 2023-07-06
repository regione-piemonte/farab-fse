/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ContattiOTPLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ContattiOTPLowDaoException;

public interface ContattiOTPLowDao {

	public ContattiOTPLowPk insert(ContattiOTPLowDto dto);
	
	public void update(ContattiOTPLowDto dto) throws ContattiOTPLowDaoException;
	
	public ContattiOTPLowDto mapRow(ResultSet rs, int row) throws SQLException;
	
	public ContattiOTPLowDto mapRow_internal(ContattiOTPLowDto objectToFill, ResultSet rs, int row) throws SQLException;
	
	@SuppressWarnings("unchecked")
	public ContattiOTPLowDto findByPrimaryKey(ContattiOTPLowPk pk) throws ContattiOTPLowDaoException;
	
	@SuppressWarnings("unchecked")
	public List<ContattiOTPLowDto> findAll() throws ContattiOTPLowDaoException;

	public List<ContattiOTPLowDto> ricercaOTP(String cfPaziente, String canale, String codiceOTP) throws ContattiOTPLowDaoException;

    List<ContattiOTPLowDto> findByFilterValidi(ContattiOTPLowDto contattiOTPLowDto) throws ContattiOTPLowDaoException;
}
