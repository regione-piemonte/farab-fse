/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public final class FarmabUtils {
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	public final static String TDATE="yyyy-MM-dd HH:mm:ss";

	public static boolean isValidDate(String data) {
		boolean ret=false;
		log.info("FarmabUtils::isValidDate");

		if (data!=null && data.length()==19) {
			try {
				SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(TDATE, Locale.ITALIAN);
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date myDate=simpleDateFormat.parse(data);
				log.debug("in input="+data+" Convertita in"+myDate);
				String doppiaConversione=simpleDateFormat.format(myDate);
				if(data.equalsIgnoreCase(doppiaConversione))
					ret= true;
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ret;
	}

	public static boolean isDataGreaterThan(String dataMin, String dataGreater) {
		log.info("FarmabUtils::isDataGreaterThan");
		boolean flag = true;
		if (dataGreater != null && dataMin != null) {
			try {
					SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(TDATE, Locale.ITALIAN);
					simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date dataMinDate=simpleDateFormat.parse(dataMin);
					Date dataGreaterDate=simpleDateFormat.parse(dataGreater);
					if(dataGreaterDate.getTime()<dataMinDate.getTime()) {
						flag=false;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		return flag;
	}



	public static boolean isGreaterThanToday(String data) {
		log.info("FarmabUtils::isGreaterThanToday");
		boolean flag = false;
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.now();
		Date oggi= Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		try {
			SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(TDATE, Locale.ITALIAN);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date dataMinDate=simpleDateFormat.parse(data);
			if(oggi.getTime()<=dataMinDate.getTime()) {
				flag=true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean isNotValidCf(String cf) {
		log.debug("FarmabUtils::isNotValidCf");
		boolean flag = true;
		if (StringUtils.isNotEmpty(cf) && (cf.trim().length()==11 || cf.trim().length()==16)) {
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
		Boolean test1= FarmabUtils.isValidDate("2021-11-30 19:10:10");
		//Boolean test1= FarmabUtils.isGreaterThanToday("2021-11-10 23:59:59");
		//Boolean test1= FarmabUtils.isGreaterThanToday("2021-11-14 00:00:00");
		System.out.print(test1);
	}

}
