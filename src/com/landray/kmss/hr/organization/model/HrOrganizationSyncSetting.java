package com.landray.kmss.hr.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * <P>人事组织架构同步规则</P>
 * @version 1.0 2019年12月5日
 */
public class HrOrganizationSyncSetting extends BaseAppConfig {

	protected String hrToEkpEnable; //人事组织架构同步到ekp
	protected String ekpToHrEnable;//ekp同步到hr

	//定时任务最后同步时间
	private String lastUpdateTime;
	private String ekpLastUpdateTime;

	//手动同步EKP组织架构新增数据-同步时间
	private String syncEkpLastUpdateTime;

	//同步人事档案历史数据到人事组织架构
	private String hrOrgPersonInit;

	public HrOrganizationSyncSetting() throws Exception {
		super();
		String hrToEkpEnable = super.getValue("hrToEkpEnable");
		if (StringUtil.isNull(hrToEkpEnable)) {
			hrToEkpEnable = "false";
		}
		super.setValue("hrToEkpEnable", hrToEkpEnable);

		String ekpToHrEnable = super.getValue("ekpToHrEnable");
		if (StringUtil.isNull(ekpToHrEnable)) {
			ekpToHrEnable = "false";
		}
		super.setValue("ekpToHrEnable", ekpToHrEnable);
	}

	public String getHrToEkpEnable() {
		return super.getValue("hrToEkpEnable");
	}

	public String getEkpToHrEnable() {
		return super.getValue("ekpToHrEnable");
	}

	public void setHrToEkpEnable(String hrToEkpEnable) {
		super.setValue("hrToEkpEnable", hrToEkpEnable);
	}

	public void setEkpToHrEnable(String ekpToHrEnable) {
		super.setValue("ekpToHrEnable", ekpToHrEnable);
	}

	public String getHrOrgPersonInit() {
		return super.getValue("hrOrgPersonInit");
	}

	public void setHrOrgPersonInit(String hrOrgPersonInit) {
		super.setValue("hrOrgPersonInit", hrOrgPersonInit);
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		setValue("lastUpdateTime", lastUpdateTime);
	}

	public String getLastUpdateTime() {
		return getValue("lastUpdateTime");
	}

	public String getEkpLastUpdateTime() {
		return getValue("ekpLastUpdateTime");
	}

	public void setEkpLastUpdateTime(String ekpLastUpdateTime) {
		setValue("ekpLastUpdateTime", ekpLastUpdateTime);
	}

	public String getSyncEkpLastUpdateTime() {
		return getValue("syncEkpLastUpdateTime");
	}

	public void setSyncEkpLastUpdateTime(String syncEkpLastUpdateTime) {
		setValue("syncEkpLastUpdateTime", syncEkpLastUpdateTime);
	}

	@Override
	public String getJSPUrl() {
		return "/hr/organization/hr_organization_sync_setting/index.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("hr-organization:hr.organization.sync.rule");
	}

}
