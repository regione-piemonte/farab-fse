/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.PazienteLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.PazienteLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.PazienteLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/*PROTECTED REGION ID(R1071147527) ENABLED START*/
// aggiungere qui eventuali import custom. 
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class PazienteLowDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PazienteLowDto>, PazienteLowDao {
	private static final String FLAG_NOTIFICA_MMG = "FLAG_NOTIFICA_MMG";
	private static final String COGNOME_MMG = "COGNOME_MMG";
	private static final String ID_AURA_MMG = "ID_AURA_MMG";
	private static final String CODICE_FISCALE_MMG = "CODICE_FISCALE_MMG";
	private static final String FLAG_EMAIL_ACCESSO = "FLAG_EMAIL_ACCESSO";
	private static final String NUMERO_TELEFONO = "NUMERO_TELEFONO";
	private static final String INDIRIZZO_EMAIL = "INDIRIZZO_EMAIL";
	private static final String ID_STATO_NASCITA = "ID_STATO_NASCITA";
	private static final String ID_COMUNE_NASCITA = "ID_COMUNE_NASCITA";
	private static final String DATA_AGGIORNAMENTO = "DATA_AGGIORNAMENTO";
	private static final String DATA_INSERIMENTO = "DATA_INSERIMENTO";
	private static final String DATA_DECESSO = "DATA_DECESSO";
	private static final String SESSO = "SESSO";
	private static final String CODICE_FISCALE = "CODICE_FISCALE";
	private static final String DATA_NASCITA = "DATA_NASCITA";
	private static final String NOME = "NOME";
	private static final String COGNOME = "COGNOME";
	private static final String ID_AURA = "ID_AURA";
	private static final String ID_PAZIENTE = "ID_PAZIENTE";
	private static final String NOME_MMG = "NOME_MMG";
	private static final String DATA_ANNULLAMENTO = "DATA_ANNULLAMENTO";
	private static final String FLAG_REGISTRY_INCARICO = "FLAG_REGISTRY_INCARICO";
	private static final String ID_ASR = "ID_ASR";
	private static final String ID_PAZIENTE_RICONDOTTO = "ID_PAZIENTE_RICONDOTTO";
	private static final String DATA_RICONDUZIONE = "DATA_RICONDUZIONE";
	private static final String CODICE_FISCALE_UTENTE_ORIG = "CODICE_FISCALE_UTENTE_ORIG";

		
	protected String[] findIdByCfFields = { "ID_PAZIENTE", "CODICE_FISCALE" };
	protected PazienteLowDaoRowMapper findByPrimaryKeyRowMapper = new PazienteLowDaoRowMapper(null,
			PazienteLowDto.class);

	protected PazienteLowDaoRowMapper findIdByCf = new PazienteLowDaoRowMapper(findIdByCfFields, PazienteLowDto.class);

	protected PazienteLowDaoRowMapper findByNomeRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);

	protected PazienteLowDaoRowMapper findByCFRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);

	protected PazienteLowDaoRowMapper byIdAuraRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);

	protected PazienteLowDaoRowMapper ByIdAuraAllRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);


	protected PazienteLowDaoRowMapper byParamsRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);


	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
	/**
	 * @generated
	 */
	protected NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @generated
	 */
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PazienteLowPk
	 * @generated
	 */

	public PazienteLowPk insert(PazienteLowDto dto)

	{
		Long newKey = new Long(incrementer.nextLongValue());

		final String sql = "INSERT INTO " + getTableName()
				+ " ( 	ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,"
				+ "DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,FLAG_EMAIL_ACCESSO,"
				+ "CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG, ID_ASR,"
				+ " ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ) "
				+ "VALUES (  :ID_PAZIENTE , :ID_AURA , :COGNOME , :NOME , :DATA_NASCITA , :CODICE_FISCALE , :SESSO , :DATA_DECESSO ,"
				+ " :DATA_INSERIMENTO , :DATA_AGGIORNAMENTO , :ID_COMUNE_NASCITA , :ID_STATO_NASCITA , :INDIRIZZO_EMAIL ,"
				+ " :NUMERO_TELEFONO , :FLAG_EMAIL_ACCESSO , :CODICE_FISCALE_MMG , :ID_AURA_MMG , :COGNOME_MMG , :NOME_MMG , "
				+ ":DATA_ANNULLAMENTO , :FLAG_NOTIFICA_MMG, :ID_ASR, :ID_PAZIENTE_RICONDOTTO, :DATA_RICONDUZIONE )";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue(ID_PAZIENTE, newKey, java.sql.Types.BIGINT);

		params.addValue(ID_AURA, dto.getIdAura(), java.sql.Types.BIGINT);

		params.addValue(ID_PAZIENTE_RICONDOTTO, dto.getIdPazienteRicondotto(), java.sql.Types.BIGINT);

		params.addValue(DATA_RICONDUZIONE, dto.getDataRiconduzione(), java.sql.Types.TIMESTAMP);

		params.addValue(COGNOME, dto.getCognome(), java.sql.Types.VARCHAR);

		params.addValue(NOME, dto.getNome(), java.sql.Types.VARCHAR);

		params.addValue(DATA_NASCITA, dto.getDataNascita(), java.sql.Types.DATE);

		params.addValue(CODICE_FISCALE, dto.getCodiceFiscale(), java.sql.Types.VARCHAR);

		params.addValue(SESSO, dto.getSesso(), java.sql.Types.VARCHAR);

		params.addValue(DATA_DECESSO, dto.getDataDecesso(), java.sql.Types.DATE);

		params.addValue(DATA_INSERIMENTO, dto.getDataInserimento(), java.sql.Types.TIMESTAMP);

		params.addValue(DATA_AGGIORNAMENTO, dto.getDataAggiornamento(), java.sql.Types.TIMESTAMP);

		params.addValue(ID_COMUNE_NASCITA, dto.getIdComuneNascita(), java.sql.Types.BIGINT);

		params.addValue(ID_STATO_NASCITA, dto.getIdStatoNascita(), java.sql.Types.BIGINT);

		params.addValue(INDIRIZZO_EMAIL, dto.getIndirizzoEmail(), java.sql.Types.VARCHAR);

		params.addValue(NUMERO_TELEFONO, dto.getNumeroTelefono(), java.sql.Types.VARCHAR);

		params.addValue(FLAG_EMAIL_ACCESSO, dto.getFlagEmailAccesso(), java.sql.Types.VARCHAR);

		params.addValue(CODICE_FISCALE_MMG, dto.getCodiceFiscaleMmg(), java.sql.Types.VARCHAR);

		params.addValue(ID_AURA_MMG, dto.getIdAuraMmg(), java.sql.Types.BIGINT);

		params.addValue(COGNOME_MMG, dto.getCognomeMmg(), java.sql.Types.VARCHAR);

		params.addValue(NOME_MMG, dto.getNomeMmg(), java.sql.Types.VARCHAR);

		params.addValue(DATA_ANNULLAMENTO, dto.getDataAnnullamento(), java.sql.Types.TIMESTAMP);

		params.addValue(FLAG_NOTIFICA_MMG, dto.getFlagNotificaMmg(), java.sql.Types.VARCHAR);

		params.addValue(ID_ASR, dto.getIdAsr(), java.sql.Types.BIGINT);

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			jdbcTemplate.update(sql.toString(), params);
		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::insert] esecuzione query", ex);
			throw ex;
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "insert", "esecuzione query", sql);
			log.debug("[PazienteLowDaoImpl::insert] END");
		}

		dto.setIdPaziente(newKey);
		return dto.createPk();

	}

	/**
	 * Custom updater in the DMACC_T_PAZIENTE table.
	 * 
	 * @generated
	 */
	public void customUpdaterUpdatePaziente(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto filter,
			it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto value) throws PazienteLowDaoException {
		/* PROTECTED REGION ID(R2060400173) ENABLED START */
		// ***scrivere la custom query
		final String sql = "UPDATE " + getTableName() + " SET ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("parametro", value);
		/* PROTECTED REGION END */
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			jdbcTemplate.update(sql.toString(), params);
		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::updateColumnsUpdatePaziente] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "updateColumnsUpdatePaziente", "esecuzione query", sql);
			log.debug("[PazienteLowDaoImpl::updateColumnsUpdatePaziente] END");
		}
	}

	/**
	 * Updates a single row in the DMACC_T_PAZIENTE table.
	 * 
	 * @generated
	 */
	public void update(PazienteLowDto dto) throws PazienteLowDaoException {
		final String sql = "UPDATE " + getTableName()
				+ " SET ID_AURA = :ID_AURA ,COGNOME = :COGNOME ,NOME = :NOME ,DATA_NASCITA = :DATA_NASCITA ,CODICE_FISCALE = :CODICE_FISCALE ,"
				+ "SESSO = :SESSO ,DATA_DECESSO = :DATA_DECESSO ,DATA_INSERIMENTO = :DATA_INSERIMENTO ,DATA_AGGIORNAMENTO = :DATA_AGGIORNAMENTO ,"
				+ "ID_COMUNE_NASCITA = :ID_COMUNE_NASCITA ,ID_STATO_NASCITA = :ID_STATO_NASCITA ,INDIRIZZO_EMAIL = :INDIRIZZO_EMAIL ,"
				+ "NUMERO_TELEFONO = :NUMERO_TELEFONO ,FLAG_EMAIL_ACCESSO = :FLAG_EMAIL_ACCESSO ,CODICE_FISCALE_MMG = :CODICE_FISCALE_MMG ,"
				+ "ID_AURA_MMG = :ID_AURA_MMG ,COGNOME_MMG = :COGNOME_MMG ,NOME_MMG = :NOME_MMG ,DATA_ANNULLAMENTO = :DATA_ANNULLAMENTO ,"
				+ "FLAG_NOTIFICA_MMG = :FLAG_NOTIFICA_MMG , ID_ASR= :ID_ASR, ID_PAZIENTE_RICONDOTTO= :ID_PAZIENTE_RICONDOTTO, "
				+ "DATA_RICONDUZIONE= :DATA_RICONDUZIONE  WHERE ID_PAZIENTE = :ID_PAZIENTE ";

		if (dto.getIdPaziente() == null) {
			log.error("[PazienteLowDaoImpl::update] chiave primaria non impostata");
			throw new PazienteLowDaoException("Chiave primaria non impostata");
		}

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue(ID_PAZIENTE, dto.getIdPaziente(), java.sql.Types.BIGINT);

		params.addValue(ID_AURA, dto.getIdAura(), java.sql.Types.BIGINT);

		params.addValue(ID_PAZIENTE_RICONDOTTO, dto.getIdPazienteRicondotto(), java.sql.Types.BIGINT);

		params.addValue(DATA_RICONDUZIONE, dto.getDataRiconduzione(), java.sql.Types.TIMESTAMP);

		params.addValue(COGNOME, dto.getCognome(), java.sql.Types.VARCHAR);

		params.addValue(NOME, dto.getNome(), java.sql.Types.VARCHAR);

		params.addValue(DATA_NASCITA, dto.getDataNascita(), java.sql.Types.DATE);

		params.addValue(CODICE_FISCALE, dto.getCodiceFiscale(), java.sql.Types.VARCHAR);

		params.addValue(SESSO, dto.getSesso(), java.sql.Types.VARCHAR);

		params.addValue(DATA_DECESSO, dto.getDataDecesso(), java.sql.Types.DATE);

		params.addValue(DATA_INSERIMENTO, dto.getDataInserimento(), java.sql.Types.TIMESTAMP);

		params.addValue(DATA_AGGIORNAMENTO, dto.getDataAggiornamento(), java.sql.Types.TIMESTAMP);

		params.addValue(ID_COMUNE_NASCITA, dto.getIdComuneNascita(), java.sql.Types.BIGINT);

		params.addValue(ID_STATO_NASCITA, dto.getIdStatoNascita(), java.sql.Types.BIGINT);

		params.addValue(INDIRIZZO_EMAIL, dto.getIndirizzoEmail(), java.sql.Types.VARCHAR);

		params.addValue(NUMERO_TELEFONO, dto.getNumeroTelefono(), java.sql.Types.VARCHAR);

		params.addValue(FLAG_EMAIL_ACCESSO, dto.getFlagEmailAccesso(), java.sql.Types.VARCHAR);

		params.addValue(CODICE_FISCALE_MMG, dto.getCodiceFiscaleMmg(), java.sql.Types.VARCHAR);

		params.addValue(ID_AURA_MMG, dto.getIdAuraMmg(), java.sql.Types.BIGINT);

		params.addValue(COGNOME_MMG, dto.getCognomeMmg(), java.sql.Types.VARCHAR);

		params.addValue(NOME_MMG, dto.getNomeMmg(), java.sql.Types.VARCHAR);

		params.addValue(DATA_ANNULLAMENTO, dto.getDataAnnullamento(), java.sql.Types.TIMESTAMP);

		params.addValue(FLAG_NOTIFICA_MMG, dto.getFlagNotificaMmg(), java.sql.Types.VARCHAR);

		params.addValue(ID_ASR, dto.getIdAsr(), java.sql.Types.BIGINT);

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			jdbcTemplate.update(sql.toString(), params);
		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::update] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "update", "esecuzione query", sql);
			log.debug("[PazienteLowDaoImpl::update] END");
		}
	}

	/**
	 * Deletes a single row in the DMACC_T_PAZIENTE table.
	 * 
	 * @generated
	 */

	public void delete(PazienteLowPk pk) throws PazienteLowDaoException {
		final String sql = "DELETE FROM " + getTableName() + " WHERE ID_PAZIENTE = :ID_PAZIENTE ";

		if (pk == null) {
			log.error("[PazienteLowDaoImpl::delete] chiave primaria non impostata");
			throw new PazienteLowDaoException("Chiave primaria non impostata");
		}

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue(ID_PAZIENTE, pk.getIdPaziente(), java.sql.Types.BIGINT);

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			jdbcTemplate.update(sql.toString(), params);
		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::delete] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "delete", "esecuzione query", sql);
			log.debug("[PazienteLowDaoImpl::delete] END");
		}
	}

	/**
	 * Returns all rows from the DMACC_T_PAZIENTE table that match the primary key
	 * criteria
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public PazienteLowDto findByPrimaryKey(PazienteLowPk pk) throws PazienteLowDaoException {

		final StringBuilder sql = new StringBuilder(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,"
						+ "ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,"
						+ "COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO , ID_ASR , ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE FROM  "
						+ getTableName() + " WHERE ID_PAZIENTE = :ID_PAZIENTE ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue(ID_PAZIENTE, pk.getIdPaziente(), java.sql.Types.BIGINT);

		List<PazienteLowDto> list = null;

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), params, findByPrimaryKeyRowMapper);
		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByPrimaryKey] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByPrimaryKey", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByPrimaryKey] END");
		}

		return list.size() == 0 ? null : list.get(0);

	}



	/**
	 * Implementazione del finder findByCF
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findFindByCF(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,"
						+ "FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_ASR, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		/* PROTECTED REGION ID(R668256829) ENABLED START */
		// la clausola from e'customizzabile poiche' il finder ha l'attributo
		// customFrom==true
		sql.append(" FROM DMACC_T_PAZIENTE");
		/* PROTECTED REGION END */
		sql.append(" WHERE ");
		/* PROTECTED REGION ID(R-748636011) ENABLED START */
		// personalizzare la query SQL relativa al finder

		// personalizzare l'elenco dei parametri da passare al jdbctemplate
		// (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		sql.append("LOWER(CODICE_FISCALE) = LOWER(:cf)");
		// che non sia morto
		
		if (input.getDataDecesso() == null ) {
			sql.append(" AND DATA_DECESSO IS NULL ");
		} else {
			sql.append(" AND (DATA_DECESSO >= :DATA_DECESSO OR DATA_DECESSO IS NULL) ");
		}
	
	
		// che sia maggiorenne
		// controllo rimosso sulla base della jira 3039
//		sql.append(" AND (NOW() - DATA_NASCITA) > '6575 days'");
		/* PROTECTED REGION END */

		/* PROTECTED REGION ID(R-1607993611) ENABLED START */
		// ***aggiungere tutte le condizioni

		paramMap.addValue("cf", input.getCodiceFiscale());

		if (input.getDataDecesso() != null ) {
			paramMap.addValue("DATA_DECESSO", input.getDataDecesso());
		} 

		
		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, findByCFRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findFindByCF] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findFindByCF", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findFindByCF] END");
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findFindByCF_INI(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,"
						+ "FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_ASR, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		/* PROTECTED REGION ID(R668256829) ENABLED START */
		// la clausola from e'customizzabile poiche' il finder ha l'attributo
		// customFrom==true
		sql.append(" FROM DMACC_T_PAZIENTE");
		/* PROTECTED REGION END */
		sql.append(" WHERE ");
		/* PROTECTED REGION ID(R-748636011) ENABLED START */
		// personalizzare la query SQL relativa al finder

		// personalizzare l'elenco dei parametri da passare al jdbctemplate
		// (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		sql.append("LOWER(CODICE_FISCALE) = LOWER(:cf)");
		// che non sia morto
		if (input.getDataDecesso() == null ) {
			sql.append(" AND DATA_DECESSO IS NULL ");
		} else {
			sql.append(" AND (DATA_DECESSO >= :DATA_DECESSO OR DATA_DECESSO IS NULL) ");
		}
		
		/* PROTECTED REGION END */

		/* PROTECTED REGION ID(R-1607993611) ENABLED START */
		// ***aggiungere tutte le condizioni

		paramMap.addValue("cf", input.getCodiceFiscale());

		if (input.getDataDecesso() != null ) {
			paramMap.addValue("DATA_DECESSO", input.getDataDecesso());
		} 

			
		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, findByCFRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findFindByCF] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findFindByCF", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findFindByCF] END");
		}
		return list;
	}
	// cerca Id Paziente collegato al cf che non sia inattivo
	@SuppressWarnings("unchecked")
	public PazienteLowDto findIdByCf(String cf) throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append("SELECT ID_PAZIENTE, CODICE_FISCALE ");
		sql.append(" FROM DMACC_T_PAZIENTE");
		sql.append(" WHERE ");
		sql.append("LOWER(CODICE_FISCALE) = LOWER(:cf)");
		sql.append(" AND DATA_RICONDUZIONE IS NULL");
		sql.append(" AND ID_PAZIENTE_RICONDOTTO IS NULL");
		sql.append(" AND DATA_DECESSO IS NULL");
		// controllo rimosso sulla base della jira 3039
//		sql.append(" AND (NOW() - DATA_NASCITA) > '6575 days'");
		paramMap.addValue("cf", cf);

		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		PazienteLowDto ret = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, findIdByCf);
			ret = list.get(0);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findFindByCF] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findFindByCF", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findFindByCF] END");
		}
		return ret;
	}

	/**
	 * Implementazione del finder byIdAura
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findByIdAura(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,"
						+ "FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_ASR ,  ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		sql.append(" FROM DMACC_T_PAZIENTE");
		sql.append(" WHERE ");
		/* PROTECTED REGION ID(R-1074809389) ENABLED START */
		// personalizzare la query SQL relativa al finder

		// personalizzare l'elenco dei parametri da passare al jdbctemplate
		// (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		sql.append("ID_AURA = :id_aura");
		// che sia maggiorenne
		// controllo rimosso sulla base della jira 3039
//		sql.append(" AND (NOW() - DATA_NASCITA) > '6575 days'");
		/* PROTECTED REGION END */

		/* PROTECTED REGION ID(R1165533559) ENABLED START */
		// ***aggiungere tutte le condizioni

		paramMap.addValue("id_aura", input.getIdAura());

		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, byIdAuraRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByIdAura] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByIdAura", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByIdAura] END");
		}
		return list;
	}

	/**
	 * Implementazione del finder ByIdAuraAll
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findByIdAuraAll(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,"
						+ "FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO,ID_ASR, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		sql.append(" FROM DMACC_T_PAZIENTE");
		sql.append(" WHERE ");
		/* PROTECTED REGION ID(R1538221852) ENABLED START */
		// personalizzare la query SQL relativa al finder

		// personalizzare l'elenco dei parametri da passare al jdbctemplate
		// (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		// sql.append("nome = :nome");
		// da revision 8745
		sql.append("ID_AURA = :id_aura");

		/* PROTECTED REGION END */

		/* PROTECTED REGION ID(R565123406) ENABLED START */
		// ***aggiungere tutte le condizioni

		// paramMap.addValue("nome", input);
		// da revision 8745
		paramMap.addValue("id_aura", input.getIdAura());

		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, ByIdAuraAllRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByIdAuraAll] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByIdAuraAll", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByIdAuraAll] END");
		}
		return list;
	}



	/**
	 * Implementazione del finder byParams
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findByParams(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,FLAG_EMAIL_ACCESSO,CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		sql.append(" FROM DMACC_T_PAZIENTE");
		sql.append(" WHERE ");
		/* PROTECTED REGION ID(R1326323307) ENABLED START */
		// personalizzare la query SQL relativa al finder
		// personalizzare l'elenco dei parametri da passare al jdbctemplate
		// (devono corrispondere in tipo e
		// numero ai parametri definiti nella queryString)
		// sql.append("nome = :nome");
		sql.append("FLAG_NOTIFICA_MMG = :flagNotificaMMG");

		/* PROTECTED REGION END */

		/* PROTECTED REGION ID(R-1708764193) ENABLED START */
		// ***aggiungere tutte le condizioni

		paramMap.addValue("flagNotificaMMG", input.getFlagNotificaMmg());

		/* PROTECTED REGION END */
		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, byParamsRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByParams] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByParams", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByParams] END");
		}
		return list;
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
	public PazienteLowDto mapRow(ResultSet rs, int row) throws SQLException {
		PazienteLowDto dto = new PazienteLowDto();
		dto = mapRow_internal(dto, rs, row);
		return dto;
	}

	/**
	 * Method 'mapRow_internal'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PazienteLowDto
	 * @generated
	 */
	public PazienteLowDto mapRow_internal(PazienteLowDto objectToFill, ResultSet rs, int row) throws SQLException {
		PazienteLowDto dto = objectToFill;

		PazienteLowDaoRowMapper pazienteLowDaoRowMapper = new PazienteLowDaoRowMapper(null, PazienteLowDto.class);

		return pazienteLowDaoRowMapper.mapRow_internal(objectToFill, rs, row);
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 * @generated
	 */
	public String getTableName() {
		return "DMACC_T_PAZIENTE";
	}

	// / flexible row mapper.
	public class PazienteLowDaoRowMapper implements org.springframework.jdbc.core.RowMapper {

		private java.util.HashMap<String, String> columnsToReadMap = new java.util.HashMap<String, String>();
		private boolean mapAllColumns = true;
		private Class dtoClass;

		/**
		 * @param columnsToRead elenco delle colonne da includere nel mapping (per query
		 *                      incomplete, esempio distinct, custom select...)
		 */
		public PazienteLowDaoRowMapper(String[] columnsToRead, Class dtoClass) {
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
		 * @return PazienteLowDto
		 * @throws SQLException
		 * @generated
		 */
		public Object mapRow(ResultSet rs, int row) throws SQLException {

			Object dto = null;
			try {
				dto = dtoClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Impossibile istanziare la classe " + dto.getClass().getCanonicalName() + " ," + e.getCause());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Impossibile accedere alla classe " + dto.getClass().getCanonicalName() + " ," + e.getCause());
			}

			if (dto instanceof PazienteLowDto) {
				return mapRow_internal((PazienteLowDto) dto, rs, row);
			}

			return dto;
		}

		public PazienteLowDto mapRow_internal(PazienteLowDto objectToFill, ResultSet rs, int row) throws SQLException {
			PazienteLowDto dto = objectToFill;

			if (mapAllColumns || columnsToReadMap.get(ID_PAZIENTE) != null)
				dto.setIdPaziente((Long) rs.getObject(ID_PAZIENTE));

			if (mapAllColumns || columnsToReadMap.get(ID_PAZIENTE_RICONDOTTO) != null)
				dto.setIdPazienteRicondotto((Long) rs.getObject(ID_PAZIENTE_RICONDOTTO));

			if (mapAllColumns || columnsToReadMap.get(DATA_RICONDUZIONE) != null)
				dto.setDataRiconduzione(rs.getTimestamp(DATA_RICONDUZIONE));

			if (mapAllColumns || columnsToReadMap.get(ID_AURA) != null)
				dto.setIdAura((Long) rs.getObject(ID_AURA));

			if (mapAllColumns || columnsToReadMap.get(COGNOME) != null)
				dto.setCognome(rs.getString(COGNOME));

			if (mapAllColumns || columnsToReadMap.get(NOME) != null)
				dto.setNome(rs.getString(NOME));

			if (mapAllColumns || columnsToReadMap.get(DATA_NASCITA) != null)
				dto.setDataNascita(rs.getDate(DATA_NASCITA));

			if (mapAllColumns || columnsToReadMap.get(CODICE_FISCALE) != null)
				dto.setCodiceFiscale(rs.getString(CODICE_FISCALE));

			if (mapAllColumns || columnsToReadMap.get(SESSO) != null)
				dto.setSesso(rs.getString(SESSO));

			if (mapAllColumns || columnsToReadMap.get(DATA_DECESSO) != null)
				dto.setDataDecesso(rs.getDate(DATA_DECESSO));

			if (mapAllColumns || columnsToReadMap.get(DATA_INSERIMENTO) != null)
				dto.setDataInserimento(rs.getTimestamp(DATA_INSERIMENTO));

			if (mapAllColumns || columnsToReadMap.get(DATA_AGGIORNAMENTO) != null)
				dto.setDataAggiornamento(rs.getTimestamp(DATA_AGGIORNAMENTO));

			if (mapAllColumns || columnsToReadMap.get(ID_COMUNE_NASCITA) != null)
				dto.setIdComuneNascita((Long) rs.getObject(ID_COMUNE_NASCITA));

			if (mapAllColumns || columnsToReadMap.get(ID_STATO_NASCITA) != null)
				dto.setIdStatoNascita((Long) rs.getObject(ID_STATO_NASCITA));

			if (mapAllColumns || columnsToReadMap.get(INDIRIZZO_EMAIL) != null)
				dto.setIndirizzoEmail(rs.getString(INDIRIZZO_EMAIL));

			if (mapAllColumns || columnsToReadMap.get(NUMERO_TELEFONO) != null)
				dto.setNumeroTelefono(rs.getString(NUMERO_TELEFONO));

			if (mapAllColumns || columnsToReadMap.get(FLAG_EMAIL_ACCESSO) != null)
				dto.setFlagEmailAccesso(rs.getString(FLAG_EMAIL_ACCESSO));

			if (mapAllColumns || columnsToReadMap.get(CODICE_FISCALE_MMG) != null)
				dto.setCodiceFiscaleMmg(rs.getString(CODICE_FISCALE_MMG));

			if (mapAllColumns || columnsToReadMap.get(ID_AURA_MMG) != null)
				dto.setIdAuraMmg((Long) rs.getObject(ID_AURA_MMG));

			if (mapAllColumns || columnsToReadMap.get(COGNOME_MMG) != null)
				dto.setCognomeMmg(rs.getString(COGNOME_MMG));

			if (mapAllColumns || columnsToReadMap.get(NOME_MMG) != null)
				dto.setNomeMmg(rs.getString(NOME_MMG));

			if (mapAllColumns || columnsToReadMap.get(DATA_ANNULLAMENTO) != null)
				dto.setDataAnnullamento(rs.getTimestamp(DATA_ANNULLAMENTO));

			if (mapAllColumns || columnsToReadMap.get(FLAG_NOTIFICA_MMG) != null)
				dto.setFlagNotificaMmg(rs.getString(FLAG_NOTIFICA_MMG));

			if (mapAllColumns || columnsToReadMap.get(FLAG_REGISTRY_INCARICO) != null)
				dto.setFlagRegistryIncaricoMmg(rs.getString(FLAG_REGISTRY_INCARICO));

			if (mapAllColumns || columnsToReadMap.get(ID_ASR) != null) {
				if (hasColumn(rs, ID_ASR)) {
					dto.setIdAsr((Long) rs.getObject(ID_ASR));
				}
			}
			return dto;
		}

	}

	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findByCFAll(it.csi.dma.dmacontatti.business.dao.dto.PazienteLowDto input)
			throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,FLAG_EMAIL_ACCESSO,"
						+ "CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE ");
		sql.append(" FROM DMACC_T_PAZIENTE");
		sql.append(" WHERE ");
		sql.append("CODICE_FISCALE = :codice_fiscale");

		paramMap.addValue("codice_fiscale", input.getCodiceFiscale());

		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, ByIdAuraAllRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByIdAuraAll] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByIdAuraAll", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByIdAuraAll] END");
		}
		return list;
	}

	private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PazienteLowDto> findByCodiceFiscale(String codiceFiscale) throws PazienteLowDaoException {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		sql.append(
				"SELECT ID_PAZIENTE,ID_AURA,COGNOME,NOME,DATA_NASCITA,CODICE_FISCALE,SESSO,DATA_DECESSO,DATA_INSERIMENTO,DATA_AGGIORNAMENTO,ID_COMUNE_NASCITA,ID_STATO_NASCITA,INDIRIZZO_EMAIL,NUMERO_TELEFONO,FLAG_EMAIL_ACCESSO,"
						+ "CODICE_FISCALE_MMG,ID_AURA_MMG,COGNOME_MMG,NOME_MMG,DATA_ANNULLAMENTO,FLAG_NOTIFICA_MMG,FLAG_REGISTRY_INCARICO, ID_PAZIENTE_RICONDOTTO, DATA_RICONDUZIONE FROM "
						+ getTableName() + " WHERE CODICE_FISCALE=:CODICE_FISCALE");

		paramMap.addValue("CODICE_FISCALE", codiceFiscale);

		List<PazienteLowDto> list = null;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			stopWatch.start();
			list = jdbcTemplate.query(sql.toString(), paramMap, ByIdAuraAllRowMapper);

		} catch (RuntimeException ex) {
			log.error("[PazienteLowDaoImpl::findByCodiceFiscale] esecuzione query", ex);
			throw new PazienteLowDaoException("Query failed", ex);
		} finally {
			stopWatch.dumpElapsed("PazienteLowDaoImpl", "findByCodiceFiscale", "esecuzione query", sql.toString());
			log.debug("[PazienteLowDaoImpl::findByCodiceFiscale] END");
		}
		return list;
	}
	

}
