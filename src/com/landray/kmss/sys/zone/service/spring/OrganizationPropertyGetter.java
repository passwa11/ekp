package com.landray.kmss.sys.zone.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.ftsearch.config.IFullTextPropertyGetter;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.SpringBeanUtil;

public class OrganizationPropertyGetter implements IFullTextPropertyGetter {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(OrganizationPropertyGetter.class);
	
	private ISysZonePersonInfoService sysZonePersonInfoService;

	public ISysZonePersonInfoService getSysZonePersonInfoService() {
		if (sysZonePersonInfoService == null) 
    	{
			sysZonePersonInfoService = (ISysZonePersonInfoService) SpringBeanUtil.getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}

	/**
	 * 解决获取员工机构信息问题
	 */
	@Override
	public String getPropertyToString(String proerty, Object model) {
		if(model instanceof SysZonePersonInfo)
		{
			SysZonePersonInfo person = (SysZonePersonInfo)model;
			try {
				String org = getSysZonePersonInfoService().getoOrganization(person.getPerson());
				return org;
			} catch (Exception e) {
				LOGGER.error("获取机构信息错误！", e);
			}
		}
		return null;
	}

}
