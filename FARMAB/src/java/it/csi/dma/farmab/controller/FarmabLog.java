/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.util.Constants;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FarmabLog extends JdbcDaoSupport{
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
	
	@Autowired
	private NamedParameterJdbcTemplate  jdbcTemplate;
	
	private static final String QUERY_FIND_D_CATALOGO_LOG="SELECT descrizione_errore "
			+ " FROM dmacc_d_catalogo_log WHERE codice=:codice;";
	
	@Transactional
	public String findMesaggiErrore(String codice) {
		log.info("FarmabLog::findMesaggiErrore");
	    String messaggioErrore=null;
		
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("codice", codice);
		
		try {
			messaggioErrore = jdbcTemplate.queryForObject(QUERY_FIND_D_CATALOGO_LOG, namedParameters, (rs, rowNum) -> rs.getString(1));
		} catch (Exception e) {
			log.error("Non ho trovato nulla per codice="+codice);
			messaggioErrore="DB Incongruente";
		}	
		
		log.debug(messaggioErrore);
		
		return messaggioErrore;
	}
}
