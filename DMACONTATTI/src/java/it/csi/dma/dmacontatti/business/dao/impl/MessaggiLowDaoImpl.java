/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import it.csi.dma.dmacontatti.business.dao.MessaggiLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.MessaggiLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.MessaggiLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.MessaggiLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import it.csi.util.performance.StopWatch;



public class MessaggiLowDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<MessaggiLowDto>, MessaggiLowDao {
    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    /**
     * @generated
     */
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected MessaggiLowDaoRowMapper findByCodiceTipoConsenso = new MessaggiLowDaoRowMapper(
            null, MessaggiLowDto.class);
    /**
     * @generated
     */
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

 
    public MessaggiLowPk insert(MessaggiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());
        
        
    


        final String sql = "INSERT INTO "
                + getTableName()
                
                + " (ID_XML ,WSO2_ID   ,SERVIZIO_XML   ,UUID   ,CHIAMANTE  ,  STATO_XML    , DATA_RICEZIONE    , DATA_RISPOSTA    , DATA_INVIO_A_PROMEMORIA) "
                 + " (, DATA_RISPOSTA_A_PROMEMORIA)"
                 + " (, DATA_INS    , DATA_MOD      ,ID_MESSAGGIO_ORIG   ,CF_ASSISTITO   ,CF_UTENTE   ,RUOLO_UTENTE   ,NRE   ,COD_ESITO_RISPOSTA_PROMEMORIA) "
                + " (,TIPO_PRESCRIZIONE   ,REGIONE_PRESCRIZIONE   ,INFO_AGGIUNTIVE_ERRORE   ,LISTA_CODICI_SERVIZIO   ,STATO_DELEGA       , DATA_INVIO_SERVIZIO) "
                 + " (, DATA_RISPOSTA_SERVIZIO       ,COD_ESITO_RISPOSTA_SERVIZIO) "               
                 + " VALUES (:ID_XML ,:WSO2_ID   ,:SERVIZIO_XML   ,:UUID   ,:CHIAMANTE  ,:  STATO_XML    ,: DATA_RICEZIONE    ,: DATA_RISPOSTA    ,: DATA_INVIO_A_PROMEMORIA) "
				 + " (,: DATA_RISPOSTA_A_PROMEMORIA)"
				 + " (,: DATA_INS    ,: DATA_MOD      ,:ID_MESSAGGIO_ORIG   ,:CF_ASSISTITO   ,:CF_UTENTE   ,:RUOLO_UTENTE   ,:NRE   ,:COD_ESITO_RISPOSTA_PROMEMORIA) "
				+ " (,:TIPO_PRESCRIZIONE   ,:REGIONE_PRESCRIZIONE   ,:INFO_AGGIUNTIVE_ERRORE   ,:LISTA_CODICI_SERVIZIO   ,:STATO_DELEGA       ,: DATA_INVIO_SERVIZIO) "
				 + " (,: DATA_RISPOSTA_SERVIZIO       ,:COD_ESITO_RISPOSTA_SERVIZIO) ";
               
               

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID_XML", newKey, java.sql.Types.BIGINT);

   
        params.addValue("WSO2_ID", dto.getWso2_id(),
                java.sql.Types.VARCHAR);

        params.addValue("SERVIZIO_XML", dto.getServizio_xml(),
                java.sql.Types.VARCHAR);
        
        params.addValue("UUID", dto.getUuid(),
                java.sql.Types.VARCHAR);

        params.addValue("CHIAMANTE", dto.getChiamante(),
                java.sql.Types.VARCHAR);
        
        params.addValue("STATO_XML", dto.getStato_xml(),
                java.sql.Types.INTEGER);

        params.addValue("DATA_RICEZIONE", dto.getData_ricezione(),
                java.sql.Types.TIMESTAMP);
        
        
        params.addValue("DATA_RISPOSTA", dto.getData_risposta(),
                java.sql.Types.TIMESTAMP);

        params.addValue("DATA_INVIO_A_PROMEMORIA", dto.getData_invio_a_promemoria(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("DATA_RISPOSTA_A_PROMEMORIA", dto.getData_risposta_a_promemoria(),
                java.sql.Types.TIMESTAMP);
        

        params.addValue("DATA_INS", dto.getData_ins(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("DATA_MOD", dto.getData_mod(),
                java.sql.Types.TIMESTAMP);

        params.addValue("ID_MESSAGGIO_ORIG", dto.getId_messaggio_orig(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("CF_ASSISTITO", dto.getCf_assistito(),
                java.sql.Types.VARCHAR);
        
        params.addValue("CF_UTENTE", dto.getCf_utente(),
                java.sql.Types.VARCHAR);

        params.addValue("RUOLO_UTENTE", dto.getRuolo_utente(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("NRE", dto.getNre(),
                java.sql.Types.VARCHAR);
        
        params.addValue("COD_ESITO_RISPOSTA_PROMEMORIA", dto.getCod_esito_risposta_promemoria(),
                java.sql.Types.VARCHAR);

        params.addValue("TIPO_PRESCRIZIONE", dto.getTipo_prescrizione(),
                java.sql.Types.VARCHAR);
      
  
        
        params.addValue("REGIONE_PRESCRIZIONE", dto.getRegione_prescrizione(),
                java.sql.Types.VARCHAR);

        params.addValue("INFO_AGGIUNTIVE_ERRORE", dto.getInfo_aggiuntive_errore(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("LISTA_CODICI_SERVIZIO", dto.getLista_codici_servizio(),
                java.sql.Types.VARCHAR);
                
        
        
        params.addValue("STATO_DELEGA", dto.getStato_delega(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_INVIO_SERVIZIO", dto.getData_invio_servizio(),
                java.sql.Types.VARCHAR);
        
        params.addValue("DATA_RISPOSTA_SERVIZIO", dto.getData_risposta_servizio(),
                java.sql.Types.TIMESTAMP);

        params.addValue("COD_ESITO_RISPOSTA_SERVIZIO", dto.getCod_esito_risposta_servizio(),
                java.sql.Types.VARCHAR);
        
        
        
        
        
      
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[MessaggiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("MessaggiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[MessaggiLowDaoImpl::insert] END");
        }

        dto.setId_xml(newKey);
        return dto.createPk();

    }
    
    
    
    public MessaggiLowDto insertLog(MessaggiLowDto dto)

    {
        Long newKey = new Long(incrementer.nextLongValue());
        
        
    


        final String sql = "INSERT INTO "
                + getTableName()
                
                + " (ID_XML ,WSO2_ID   ,SERVIZIO_XML   ,UUID   ,CHIAMANTE  ,  STATO_XML    , DATA_RICEZIONE    , DATA_RISPOSTA    , DATA_INVIO_A_PROMEMORIA"
                 + " , DATA_RISPOSTA_A_PROMEMORIA"
                 + " , DATA_INS    , DATA_MOD      ,ID_MESSAGGIO_ORIG   ,CF_ASSISTITO   ,CF_UTENTE   ,RUOLO_UTENTE   ,NRE   ,COD_ESITO_RISPOSTA_PROMEMORIA "
                + " ,TIPO_PRESCRIZIONE   ,REGIONE_PRESCRIZIONE   ,INFO_AGGIUNTIVE_ERRORE   ,LISTA_CODICI_SERVIZIO   ,STATO_DELEGA       , DATA_INVIO_SERVIZIO "
                 + " , DATA_RISPOSTA_SERVIZIO       ,COD_ESITO_RISPOSTA_SERVIZIO, IP_RICHIEDENTE, CODICE_SERVIZIO) "
                 + " VALUES (:ID_XML ,:WSO2_ID   ,:SERVIZIO_XML   ,:UUID   ,:CHIAMANTE  ,:STATO_XML    ,:DATA_RICEZIONE    ,:DATA_RISPOSTA    ,:DATA_INVIO_A_PROMEMORIA "
				 + " ,:DATA_RISPOSTA_A_PROMEMORIA"
				 + " ,:DATA_INS    ,:DATA_MOD      ,:ID_MESSAGGIO_ORIG   ,:CF_ASSISTITO   ,:CF_UTENTE   ,:RUOLO_UTENTE   ,:NRE   ,:COD_ESITO_RISPOSTA_PROMEMORIA "
				+ " ,:TIPO_PRESCRIZIONE   ,:REGIONE_PRESCRIZIONE   ,:INFO_AGGIUNTIVE_ERRORE   ,:LISTA_CODICI_SERVIZIO   ,:STATO_DELEGA       ,:DATA_INVIO_SERVIZIO "
				 + " ,:DATA_RISPOSTA_SERVIZIO   ,:COD_ESITO_RISPOSTA_SERVIZIO, :IP_RICHIEDENTE, :CODICE_SERVIZIO) ";
               
               

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("ID_XML", newKey, java.sql.Types.BIGINT);

   
        params.addValue("WSO2_ID", dto.getWso2_id(),
                java.sql.Types.VARCHAR);

        params.addValue("SERVIZIO_XML", dto.getServizio_xml(),
                java.sql.Types.VARCHAR);
        
        params.addValue("UUID", dto.getUuid(),
                java.sql.Types.VARCHAR);

        params.addValue("CHIAMANTE", dto.getChiamante(),
                java.sql.Types.VARCHAR);
        
        params.addValue("STATO_XML", dto.getStato_xml(),
                java.sql.Types.INTEGER);

        params.addValue("DATA_RICEZIONE", dto.getData_ricezione(),
                java.sql.Types.TIMESTAMP);
        
        
        params.addValue("DATA_RISPOSTA", dto.getData_risposta(),
                java.sql.Types.TIMESTAMP);

        params.addValue("DATA_INVIO_A_PROMEMORIA", dto.getData_invio_a_promemoria(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("DATA_RISPOSTA_A_PROMEMORIA", dto.getData_risposta_a_promemoria(),
                java.sql.Types.TIMESTAMP);
        

        params.addValue("DATA_INS", dto.getData_ins(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("DATA_MOD", dto.getData_mod(),
                java.sql.Types.TIMESTAMP);

        params.addValue("ID_MESSAGGIO_ORIG", dto.getId_messaggio_orig(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("CF_ASSISTITO", dto.getCf_assistito(),
                java.sql.Types.VARCHAR);
        
        params.addValue("CF_UTENTE", dto.getCf_utente(),
                java.sql.Types.VARCHAR);

        params.addValue("RUOLO_UTENTE", dto.getRuolo_utente(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("NRE", dto.getNre(),
                java.sql.Types.VARCHAR);
        
        params.addValue("COD_ESITO_RISPOSTA_PROMEMORIA", dto.getCod_esito_risposta_promemoria(),
                java.sql.Types.VARCHAR);

        params.addValue("TIPO_PRESCRIZIONE", dto.getTipo_prescrizione(),
                java.sql.Types.VARCHAR);
      
  
        
        params.addValue("REGIONE_PRESCRIZIONE", dto.getRegione_prescrizione(),
                java.sql.Types.VARCHAR);

        params.addValue("INFO_AGGIUNTIVE_ERRORE", dto.getInfo_aggiuntive_errore(),
                java.sql.Types.VARCHAR);
        
        
        params.addValue("LISTA_CODICI_SERVIZIO", dto.getLista_codici_servizio(),
                java.sql.Types.VARCHAR);
                
        
        
        params.addValue("STATO_DELEGA", dto.getStato_delega(),
                java.sql.Types.VARCHAR);

        params.addValue("DATA_INVIO_SERVIZIO", dto.getData_invio_servizio(),
                java.sql.Types.TIMESTAMP);
        
        params.addValue("DATA_RISPOSTA_SERVIZIO", dto.getData_risposta_servizio(),
                java.sql.Types.TIMESTAMP);

        params.addValue("COD_ESITO_RISPOSTA_SERVIZIO", dto.getCod_esito_risposta_servizio(),
                java.sql.Types.VARCHAR);

        params.addValue("IP_RICHIEDENTE", dto.getIp(),
                java.sql.Types.VARCHAR);

        params.addValue("CODICE_SERVIZIO", dto.getCodiceServizio(),
                java.sql.Types.VARCHAR);
        
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[MessaggiLowDaoImpl::insert] esecuzione query", ex);
            throw ex;
        } finally {
            stopWatch.dumpElapsed("MessaggiLowDaoImpl", "insert",
                    "esecuzione query", sql);
            log.debug("[MessaggiLowDaoImpl::insert] END");
        }

        dto.setId_xml(newKey);
        return dto;

    }
    
    
    public void update(MessaggiLowDto messaggio) throws MessaggiLowDaoException {
        final String sql = "UPDATE "
            	+ getTableName()
		+ " SET STATO_XML = :STATO_XML, DATA_MOD=:DATA_MOD,"
		+"  LISTA_CODICI_SERVIZIO=:LISTA_CODICI_SERVIZIO, STATO_DELEGA=:STATO_DELEGA,"
		+ " DATA_INVIO_SERVIZIO=:DATA_INVIO_SERVIZIO,DATA_RISPOSTA_SERVIZIO=:DATA_RISPOSTA_SERVIZIO,"
		+" COD_ESITO_RISPOSTA_SERVIZIO=:COD_ESITO_RISPOSTA_SERVIZIO, DATA_RISPOSTA =:DATA_RISPOSTA"
                + " WHERE  WSO2_ID = :WSO2_ID ";
        //TODO ServizioRichiedente_Id e ServizioRichiedente_Codice

        if (messaggio.getWso2_id() == null) {
            log.error("[MessaggiLowDaoImpl::update] chiave primaria non impostata");

            return;
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        
//        stato_xml 	2 (che significa Registrato in inoltro per Deleghe)
//        data_mod 	timestamp di aggiornamento del record in tabella
//        data_invio_servizio 	timestamp in cui si effettua la chiamata al sistema Deleghe
//        ServizioRichiedente_Id	In attesa di specifiche Exprivia
//        ServizioRichiedente_Codice	CC.GetDeleganti In attesa di specifiche Exprivia
//        CodiceServizio (lista) 	lista dei codici dei servizi ricevuti in input (se presenti) per cui si ricerca la delega
//        StatoDelega 	stato della delega ricevuto in input (se presente)


        params.addValue("WSO2_ID",messaggio.getWso2_id(), java.sql.Types.VARCHAR);
      
        params.addValue("STATO_XML",messaggio.getStato_xml(), java.sql.Types.INTEGER);
        params.addValue("DATA_MOD",messaggio.getData_mod(), java.sql.Types.TIMESTAMP);
        params.addValue("LISTA_CODICI_SERVIZIO",messaggio.getLista_codici_servizio(), java.sql.Types.VARCHAR);
        params.addValue("STATO_DELEGA",messaggio.getStato_delega(), java.sql.Types.VARCHAR);
        params.addValue("DATA_INVIO_SERVIZIO",messaggio.getData_invio_servizio(), java.sql.Types.TIMESTAMP);
        params.addValue("DATA_RISPOSTA_SERVIZIO",messaggio.getData_risposta_servizio(), java.sql.Types.TIMESTAMP);
        params.addValue("COD_ESITO_RISPOSTA_SERVIZIO",messaggio.getCod_esito_risposta_servizio(), java.sql.Types.VARCHAR);
        params.addValue("DATA_RISPOSTA",messaggio.getData_risposta(), java.sql.Types.TIMESTAMP);
        
        
      
        StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
        try {
            stopWatch.start();
            jdbcTemplate.update(sql.toString(), params);
        } catch (RuntimeException ex) {
            log.error("[MessaggiCCELowDaoImpl::update] esecuzione query", ex);
            throw new MessaggiLowDaoException("Query failed", ex);
        } finally {
            stopWatch.dumpElapsed("MessaggiCCELowDaoImpl", "update",
                    "esecuzione query", sql);
            log.debug("[MessaggiCCELowDaoImpl::update] END");
        }
    }
 

  
   

    public MessaggiLowDto mapRow(ResultSet rs, int row) throws SQLException {
    	MessaggiLowDto dto = new MessaggiLowDto();
        dto = mapRow_internal(dto, rs, row);
        return dto;
    }
    
    

  
    public MessaggiLowDto mapRow_internal(MessaggiLowDto objectToFill,
            ResultSet rs, int row) throws SQLException {
    	MessaggiLowDto dto = objectToFill;

    	MessaggiLowDaoRowMapper MessaggiLowDaoRowMapper = new MessaggiLowDaoRowMapper(
                null, MessaggiLowDto.class);

        return MessaggiLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
    }
    
    
    protected MessaggiLowDaoRowMapper findLogRowMapper = new MessaggiLowDaoRowMapper(
            null, MessaggiLowDto.class);

    /**
     * Method 'getTableName'
     * 
     * @return String
     * @generated
     */
    public String getTableName() {
        return "DMACC_L_MESSAGGI";
    }

    


    // / flexible row mapper.
    public class MessaggiLowDaoRowMapper implements
            org.springframework.jdbc.core.RowMapper {

        private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
        private boolean mapAllColumns = true;
        private Class dtoClass;

        /**
         * @param columnsToRead
         *            elenco delle colonne da includere nel mapping (per query
         *            incomplete, esempio distinct, custom select...)
         */
        public MessaggiLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
            if (columnsToRead != null) {
                mapAllColumns = false;
                for (int i = 0; i < columnsToRead.length; i++)
                    columnsToReadMap.put(columnsToRead[i], columnsToRead[i]);
            }
            this.dtoClass = dtoClass;
        }

        /**
         * Method 'mapRow'
         * 
         * @param rs
         * @param row
         * @throws SQLException
         * @return InvioNotificaConsensiLowDto
         * @generated
         */
        public Object mapRow(ResultSet rs, int row) throws SQLException {

            Object dto = null;
            try {
                dto = dtoClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException("Impossibile istanziare la classe "
                        + dto.getClass().getCanonicalName() + " ,"
                        + e.getCause());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Impossibile accedere alla classe "
                        + dto.getClass().getCanonicalName() + " ,"
                        + e.getCause());
            }

            if (dto instanceof MessaggiLowDto) {
                return mapRow_internal((MessaggiLowDto) dto, rs, row);
            }

            return dto;
        }
        
    
        public MessaggiLowDto mapRow_internal(MessaggiLowDto objectToFill,
                ResultSet rs, int row) throws SQLException

        {
        	MessaggiLowDto dto = objectToFill;
        	
        	
        	
        	 if (mapAllColumns || columnsToReadMap.get("ID_XML") != null)
                 dto.setId_xml((Long) rs.getObject("ID_XML"));
             

             if (mapAllColumns
                     || columnsToReadMap.get("WSO2_ID") != null)
                 dto.setWso2_id(rs
                         .getString("WSO2_ID"));

             if (mapAllColumns
                     || columnsToReadMap.get("SERVIZIO_XML") != null)
                 dto.setServizio_xml(rs.getString("SERVIZIO_XML"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("UUID") != null)
                 dto.setUuid(rs
                         .getString("UUID"));

             if (mapAllColumns
                     || columnsToReadMap.get("CHIAMANTE") != null)
                 dto.setChiamante(rs.getString("CHIAMANTE"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("STATO_XML") != null)
                 dto.setStato_xml(rs.getInt("STATO_XML"));

        	
        
             if (mapAllColumns || columnsToReadMap.get("DATA_RICEZIONE") != null)
                 dto.setData_ricezione(rs.getTimestamp("DATA_RICEZIONE"));
             

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_RISPOSTA") != null)
                 dto.setData_risposta(rs
                         .getTimestamp("DATA_RISPOSTA"));

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_INVIO_A_PROMEMORIA") != null)
                 dto.setData_invio_a_promemoria(rs.getTimestamp("DATA_INVIO_A_PROMEMORIA"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("DATA_RISPOSTA_A_PROMEMORIA") != null)
                 dto.setData_risposta_a_promemoria(rs
                         .getTimestamp("DATA_RISPOSTA_A_PROMEMORIA"));

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_INS") != null)
                 dto.setData_ins(rs.getTimestamp("DATA_INS"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("DATA_MOD") != null)
                 dto.setData_mod(rs
                         .getTimestamp("DATA_MOD"));

             

             if (mapAllColumns
                     || columnsToReadMap.get("ID_MESSAGGIO_ORIG") != null)
                 dto.setId_messaggio_orig(rs
                         .getString("ID_MESSAGGIO_ORIG"));

             if (mapAllColumns
                     || columnsToReadMap.get("CF_ASSISTITO") != null)
                 dto.setCf_assistito(rs.getString("CF_ASSISTITO"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("CF_UTENTE") != null)
                 dto.setCf_utente(rs
                         .getString("CF_UTENTE"));

             if (mapAllColumns
                     || columnsToReadMap.get("RUOLO_UTENTE") != null)
                 dto.setRuolo_utente(rs.getString("RUOLO_UTENTE"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("NRE") != null)
                 dto.setNre(rs
                         .getString("NRE"));

         
             
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("COD_ESITO_RISPOSTA_PROMEMORIA") != null)
                 dto.setCod_esito_risposta_promemoria(rs.getString("COD_ESITO_RISPOSTA_PROMEMORIA"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("TIPO_PRESCRIZIONE") != null)
                 dto.setTipo_prescrizione(rs
                         .getString("TIPO_PRESCRIZIONE"));

             if (mapAllColumns
                     || columnsToReadMap.get("REGIONE_PRESCRIZIONE") != null)
                 dto.setRegione_prescrizione(rs.getString("REGIONE_PRESCRIZIONE"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("INFO_AGGIUNTIVE_ERRORE") != null)
                 dto.setInfo_aggiuntive_errore(rs
                         .getString("INFO_AGGIUNTIVE_ERRORE"));

             
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("LISTA_CODICI_SERVIZIO") != null)
                 dto.setLista_codici_servizio(rs.getString("LISTA_CODICI_SERVIZIO"));
             
             
             if (mapAllColumns
                     || columnsToReadMap.get("STATO_DELEGA") != null)
                 dto.setStato_delega(rs
                         .getString("STATO_DELEGA"));
             
             
             
             

             if (mapAllColumns
                     || columnsToReadMap.get("DATA_INVIO_SERVIZIO") != null)
                 dto.setData_invio_servizio(rs.getTimestamp("DATA_INVIO_SERVIZIO"));           
             
                         
             if (mapAllColumns
                     || columnsToReadMap.get("DATA_RISPOSTA_SERVIZIO") != null)
                 dto.setData_risposta_servizio(rs
                         .getTimestamp("DATA_RISPOSTA_SERVIZIO"));
             
             if (mapAllColumns
                     || columnsToReadMap.get("COD_ESITO_RISPOSTA_SERVIZIO") != null)
                 dto.setCod_esito_risposta_servizio(rs
                         .getString("COD_ESITO_RISPOSTA_SERVIZIO"));
         
            
          
           
           


            return dto;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
