/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.interfacews.msg.farab;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.farmab.interfacews.IF.FarabService;
import it.csi.dma.farmab.interfacews.base.BaseService;
import it.csi.dma.farmab.util.Constants;

public class FarabServiceImpl extends BaseService implements FarabService {
	private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);	

	@Autowired
	CertificaDeviceConOtpServiceImpl certificaDeviceConOtpServiceImpl;
	
	@Autowired
	CertificaDeviceServiceImpl certificaDeviceServiceImpl;
	
	@Autowired
	FarmaciaOccasionaleServiceImpl farmaciaOccasionaleServiceImpl;
	


	@Override
	public CertificaDeviceResponse certificaDevice(CertificaDevice certificaDeviceRequest) {
		log.info("FarabServiceImpl::certificaDevice");
		return certificaDeviceServiceImpl.certificaDevice(certificaDeviceRequest, getIpAddress());
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public CertificaDeviceConOtpResponse certificaDeviceConOtp(CertificaDeviceConOtp certificaDeviceConOtpRequest) {
		log.info("FarabServiceImpl::certificaDeviceConOtp");
		return certificaDeviceConOtpServiceImpl.certificaDeviceConOtp(certificaDeviceConOtpRequest, getIpAddress());
	}

	@Override
	public GetFarmaciaOccasionaleResponse getFarmaciaOccasionale(GetFarmaciaOccasionale getFarmaciaOccasionaleRequest) {
		log.info("FarabServiceImpl::getFarmaciaOccasionale");
		return farmaciaOccasionaleServiceImpl.getFarmaciaOccasionale(getFarmaciaOccasionaleRequest);
	}

	@Override
	public AutorizzaFarmaciaOccasionaleResponse autorizzaFarmaciaOccasionale(
			AutorizzaFarmaciaOccasionale autorizzaFarmaciaOccasionaleRequest) {
		log.info("FarabServiceImpl::autorizzaFarmaciaOccasionale");
		return farmaciaOccasionaleServiceImpl.autorizzaFarmaciaOccasionale(autorizzaFarmaciaOccasionaleRequest, getIpAddress());
	}

}
