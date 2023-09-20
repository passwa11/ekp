package com.landray.kmss.hr.organization.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.util.StringUtil;

/**
 * <P>第一次加载时从人事档案同步员工数据到人事组织架构</P>
 * @author sunj
 * @version 1.0 2019年12月5日
 */
public class InitHrOrgPersonServiceImpl implements InitializingBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(InitHrOrgPersonServiceImpl.class);

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("InitHrOrgPersonServiceImpl begin");

		HrOrganizationSyncSetting setting = new HrOrganizationSyncSetting();
		String hrOrgPersonInit = setting.getHrOrgPersonInit();
		if (StringUtil.isNull(hrOrgPersonInit)) {
			hrOrganizationElementService.initStaffPerson();

			setting.setHrOrgPersonInit("true");
			//setting.setEkpToHrEnable("true");
			setting.save();
		}

		logger.info("InitHrOrgPersonServiceImpl end");
	}



}
