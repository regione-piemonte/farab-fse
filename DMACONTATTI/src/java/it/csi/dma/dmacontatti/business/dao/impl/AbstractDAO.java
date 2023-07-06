/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import it.csi.util.performance.StopWatch;

/**
 * Base class for Oracle DAO classes.
 * 
 * @generated
 */
public abstract class AbstractDAO {
    /**
     * @generated
     */
    protected DataFieldMaxValueIncrementer incrementer;

    /**
     * @generated
     */
    public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
        this.incrementer = incrementer;
    }

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public abstract String getTableName();

    public Long getSequence(NamedParameterJdbcTemplate jdbcTemplate, String sequence) throws Exception{
		
	 	MapSqlParameterSource paramMap = new MapSqlParameterSource();
	 	final Long seq;
	 	
		final String sql = "SELECT nextval('" + sequence + "') seq";
		
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		List sequOut = null;
        try {
            stopWatch.start();
            sequOut = jdbcTemplate.query(sql.toString(), paramMap,
                    new RowMapper() {

						@Override
						public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
							
							return new Long(arg0.getLong("seq"));
						}});
        } catch (Exception ex) {
            throw ex;
        } finally {
            stopWatch.dumpElapsed("ConfigurazioneLowDaoImpl", "insert",
                    "esecuzione query", sql);
        }
        
        return sequOut != null? (Long)sequOut.get(0):null;
	}

}
