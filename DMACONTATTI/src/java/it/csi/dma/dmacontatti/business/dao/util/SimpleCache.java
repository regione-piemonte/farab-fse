/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.util;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class SimpleCache {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	HashMap<Object, CacheElement> map = new HashMap<Object, CacheElement>();
	int ttl_minutes = 0;
	boolean updateAccessTime = false;

	public SimpleCache(int ttl, boolean updateAccessTime) {
		this.ttl_minutes = ttl;
		this.updateAccessTime = updateAccessTime;
	}

	//store del dato
	public void put(Object key, Object value) {
		CacheElement el = new CacheElement();
		el.setLastAccess(System.currentTimeMillis());
		el.setValue(value);
		map.put(key, el);
	}

	//accesso al dato
	public Object get(Object key) {
		CacheElement el = map.get(key);
		if (el != null) {
			if ((System.currentTimeMillis() - el.getLastAccess()) > (ttl_minutes * 60000)) {
				log.debug("elemento trovato ma scaduto... (impostato TTL pari a "
						+ ttl_minutes + " minuti");
				map.remove(key);
				return null;
			}

			else {
				log.debug("elemento trovato");
				if (updateAccessTime) {
					el.setLastAccess(System.currentTimeMillis());
				}
				map.put(key, el); // non servirebbe nemmeno, reference...
				Object val = el.getValue();
				return val;
			}
		} else {
			log.debug("elemento non trovato");
			return null;
		}

	}

	//per debug
	public void dumpCache() {
		Iterator<Object> it = map.keySet().iterator();
		while (it.hasNext()) {
			Object currK = it.next();
			CacheElement el = map.get(currK);
			log.debug("key:" + currK + ", val:" + el.getValue()
					+ ", lastAccess:" + el.getLastAccess());
		}
	}
}
