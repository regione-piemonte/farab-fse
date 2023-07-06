/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.impl;

import it.csi.dma.dmacontatti.business.dao.ConfigurazioneDao;
import it.csi.dma.dmacontatti.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowDto;
import it.csi.dma.dmacontatti.business.dao.dto.ConfigurazioneLowPk;
import it.csi.dma.dmacontatti.business.dao.exceptions.ConfigurazioneLowDaoException;
import it.csi.dma.dmacontatti.business.dao.util.Constants;
import org.apache.log4j.Logger;

import java.util.List;

public class ConfigurazioneDaoImpl implements ConfigurazioneDao {

    private ConfigurazioneLowDao configurazioneLowDao;

    private final static Logger logger = Logger
            .getLogger(Constants.APPLICATION_CODE + ".dao");

    public ConfigurazioneDaoImpl() {
        super();
    }

    public ConfigurazioneLowPk insert(ConfigurazioneLowDto dto) {
        return configurazioneLowDao.insert(dto);
    }

    public void update(ConfigurazioneLowDto dto)
            throws ConfigurazioneLowDaoException {
        configurazioneLowDao.update(dto);

    }

    public List<ConfigurazioneLowDto> findAll()
            throws ConfigurazioneLowDaoException {
        return configurazioneLowDao.findAll();
    }

    public ConfigurazioneLowDto findByPrimaryKey(ConfigurazioneLowPk pk)
            throws ConfigurazioneLowDaoException {
        return configurazioneLowDao.findByPrimaryKey(pk);
    }

    public ConfigurazioneLowDto findByCodice(String input)
            throws ConfigurazioneLowDaoException {
        ConfigurazioneLowDto retVal = null;

        List<ConfigurazioneLowDto> listDto = configurazioneLowDao
                .findByCodice(input);

        if (listDto != null && listDto.size() > 0) {
            retVal = listDto.get(0);
        }

        return retVal;
    }
    
    
    public ConfigurazioneLowDto findByCodiceNoCache(String input)
            throws ConfigurazioneLowDaoException {
       

            List<ConfigurazioneLowDto> listDto = configurazioneLowDao
                    .findByCodice(input);      

        return  listDto.get(0);
    }

    public ConfigurazioneLowDao getConfigurazioneLowDao() {
        return configurazioneLowDao;
    }

    public void setConfigurazioneLowDao(
            ConfigurazioneLowDao configurazioneLowDao) {
        this.configurazioneLowDao = configurazioneLowDao;
    }

	@Override
	public void updateConf(String codice, String value) throws ConfigurazioneLowDaoException {
		ConfigurazioneLowDto dto = findByCodiceNoCache(codice);
		dto.setValore(value);
		update(dto);			
	}

}
