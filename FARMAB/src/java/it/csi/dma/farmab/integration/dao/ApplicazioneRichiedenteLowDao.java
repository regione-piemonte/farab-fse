/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import it.csi.dma.farmab.integration.dao.dto.ApplicazioneRichiedenteLowDto;
import it.csi.dma.farmab.integration.dao.rowmapper.ApplicazioneRichiedenteLowDaoRowMapper;
import it.csi.dma.farmab.util.NamedParameterJdbcTemplateWithQueryDebug;

@Repository
public class ApplicazioneRichiedenteLowDao extends JdbcDaoSupport {
	
	@Autowired
	private NamedParameterJdbcTemplateWithQueryDebug namedJdbcTemplate;
	
	
	@Autowired
	ApplicazioneRichiedenteLowDao(DataSource dataSource){
		
		setDataSource(dataSource);
	}
	
	 public ApplicazioneRichiedenteLowDto findByCodiceApplicazione(String codiceApplicazione){
		 List<ApplicazioneRichiedenteLowDto> list = new ArrayList<>();
		 
		 StringBuilder sql = new StringBuilder();
		 sql.append("SELECT ID,CODICE_APPLICAZIONE,DESCRIZIONE_APPLICAZIONE,DATA_INSERIMENTO,FLG_SUPPORTA_RUOLO_CITTADINO ");
		 sql.append(" FROM DMACC_D_APPLICAZIONE_RICHIEDENTE");
		 sql.append(" WHERE ");

		 sql.append("CODICE_APPLICAZIONE = :codiceApplicazione");
		 
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("codiceApplicazione", codiceApplicazione);
		 
		 list = namedJdbcTemplate.query(sql.toString(), paramSource, new ApplicazioneRichiedenteLowDaoRowMapper());
		 
		 if(list == null || list.size() == 0){
			 return null;
		 }
		 
		 return list.get(0);
	 }
	
			
   
  
    


}
