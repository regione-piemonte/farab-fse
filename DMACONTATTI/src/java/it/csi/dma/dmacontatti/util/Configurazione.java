/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.util;

import java.util.List;

import it.csi.dma.dmacontatti.business.dao.ConfigurazioneDao;
import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.exceptions.ConfigurazioneLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;



/**
 * Servizio di retrieve dei valori di configurazione
 * 
 * @author Alberto Lagna
 * @author Emanuele Scaglia
 * @version $Id: Configurazione.java 23518 2021-07-26 09:35:11Z 1118 $
 */
public class Configurazione {
	
	ConfigurazioneDao configurazioneDao;
	ConfigurazioneLowDao configurazioneLowDao;

	private static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");

	public Configurazione (){
		/*this.props.setProperty("PAZIENTE_QUERY_LIMIT", "20");
		this.props.setProperty("DURATA_MAX_TRANSAZIONE", "14400");
		*/
	}
	
	/**
	 * Restituisce la property corrispondente come stringa, se esiste. Se no
	 * null
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {

		String retVal = null;

		try {
			retVal = configurazioneDao.findByCodice(key).getValore();
		} catch (ConfigurazioneLowDaoException e) {
			log.warn("key:" + key + " non trovata");
		}

		return retVal;
	}

	public String getNoCache(String key) {
		
		String retVal = null;
		
		try {
			retVal = configurazioneLowDao.findByCodice(key).get(0).getValore();
		} catch (ConfigurazioneLowDaoException e) {
			log.warn("key:" + key + " non trovata");
		}
		
		return retVal;
	}

	public int getInt(String key, int defaultValue) {
		String val = get(key);
		int r = defaultValue;

		if (val != null) {
			try {
				r = Integer.parseInt(val);
			} catch (NumberFormatException nfe) {
				log.error("Valore della proprietÃ  non numerico: '" + val + "'");
			}
		}
		return r;
	}
	
	/**
	 * Reastituisce la property come numero
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return getInt(key, -1);
	}

	
	public List<ConfigurazioneLowDto> getAll() throws ConfigurazioneLowDaoException{
		List<ConfigurazioneLowDto> retVal = null;
		try {
			retVal = configurazioneDao.findAll();
		} 
		catch (Exception e) {
			throw new ConfigurazioneLowDaoException(e.getMessage(), e.getCause());
		}

		return retVal;	
	}

	public ConfigurazioneDao getConfigurazioneDao() {
		return configurazioneDao;
	}

	public void setConfigurazioneDao(ConfigurazioneDao configurazioneDao) {
		this.configurazioneDao = configurazioneDao;
	}

	public ConfigurazioneLowDao getConfigurazioneLowDao() {
		return configurazioneLowDao;
	}

	public void setConfigurazioneLowDao(ConfigurazioneLowDao configurazioneLowDao) {
		this.configurazioneLowDao = configurazioneLowDao;
	}
}
