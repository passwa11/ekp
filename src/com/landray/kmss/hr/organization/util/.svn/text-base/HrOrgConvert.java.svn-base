package com.landray.kmss.hr.organization.util;

import org.apache.commons.beanutils.BeanUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.profile.service.ISysOrgImportService;
import com.landray.kmss.util.SpringBeanUtil;

public class HrOrgConvert {

	private static IHrOrganizationElementService hrOrganizationElementService;

	public static IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	private static ISysOrgElementService sysOrgElementService;

	public static ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private static ISysOrgImportService sysOrgImportService;

	public static ISysOrgImportService getSysOrgImportService() {
		if (sysOrgImportService == null) {
			sysOrgImportService = (ISysOrgImportService) SpringBeanUtil.getBean("sysOrgImportService");
		}
		return sysOrgImportService;
	}

	/**
	 * <p>读取后台配置，设置对应的对象到model中</p>
	 * @param model
	 * @param sysProperty EKP组织架构字段name
	 * @param hrProperty  人事组织架构字段name
	 * @param fdId        需要设置的组织架构id
	 * @throws Exception
	 * @author sunj
	 */
	public static IBaseModel setPropertyById(Object model, String sysProperty, String hrProperty, String fdId)
			throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		IBaseModel baseModel = null;
		if ("true".equals(syncSetting.getHrToEkpEnable())) {
			baseModel = getHrOrganizationElementService()
					.findById(fdId);
			if (null != baseModel) {
				BeanUtils.setProperty(model, hrProperty, baseModel);
			}
		} else {
			baseModel = getSysOrgElementService().findByPrimaryKey(fdId);
			if (null != baseModel) {
				BeanUtils.setProperty(model, sysProperty, baseModel);
			}
		}
		return baseModel;
	}

	public static IBaseModel setPropertyByName(Object model, String sysProperty, String hrProperty, String fdName)
			throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		IBaseModel baseModel = null;
		if ("true".equals(syncSetting.getHrToEkpEnable())) {
			baseModel = getHrOrganizationElementService()
					.findOrgByName(fdName);
			if (null != baseModel) {
				BeanUtils.setProperty(model, hrProperty, baseModel);
			}
		} else {
			baseModel = getSysOrgImportService().getSysOrgElementByName(fdName, SysOrgConstant.ORG_TYPE_ORG,
					SysOrgConstant.ORG_TYPE_DEPT);
			if (null != baseModel) {
				BeanUtils.setProperty(model, sysProperty, baseModel);
			}
		}
		return baseModel;
	}

}
