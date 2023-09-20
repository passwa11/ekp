package com.landray.kmss.sys.organization.service.spring;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.sys.authentication.user.KmssUserInitedEvent;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.sys.organization.service.InitSysOrgMatrixService;

public class InitSysOrgMatrixServiceImpl implements InitSysOrgMatrixService, ApplicationListener<KmssUserInitedEvent> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(InitSysOrgMatrixServiceImpl.class);
	
	private ISysOrgMatrixService sysOrgMatrixService;
	
	public ISysOrgMatrixService getSysOrgMatrixService() {
		return sysOrgMatrixService;
	}

	public void setSysOrgMatrixService(ISysOrgMatrixService sysOrgMatrixService) {
		this.sysOrgMatrixService = sysOrgMatrixService;
	}

	@Override
	public void onApplicationEvent(KmssUserInitedEvent arg0) {
		logger.info("InitSysOrgMatrixServiceImpl begin");
		try {
			sysOrgMatrixService.saveInitMatrixData();
		} catch (Exception ex) {
			logger.info("InitSysOrgMatrixServiceImpl afterPropertiesSet error", ex);
		}
		logger.info("InitSysOrgMatrixServiceImpl end");
	}
	
}
