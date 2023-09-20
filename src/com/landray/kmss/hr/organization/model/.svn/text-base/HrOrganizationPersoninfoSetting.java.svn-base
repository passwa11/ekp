package com.landray.kmss.hr.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * <P>
 * 人事组织架构开关配置
 * </P>
 * 
 * @author 苏琦
 */
public class HrOrganizationPersoninfoSetting extends BaseAppConfig {

	protected String ispostRelationdept; // 岗位名称关联部门名称
	protected String isUniqueGroupName; // 保持组织架构和群组名唯一

	public HrOrganizationPersoninfoSetting() throws Exception {
		super();
		ispostRelationdept = super.getValue("ispostRelationdept");
		if (StringUtil.isNull(ispostRelationdept)) {
			ispostRelationdept = "false";
		}
		super.setValue("ispostRelationdept", ispostRelationdept);

		isUniqueGroupName = super.getValue("isUniqueGroupName");
		if (StringUtil.isNull(isUniqueGroupName)) {
			isUniqueGroupName = "false";
		}
		super.setValue("isUniqueGroupName", isUniqueGroupName);

	}

	public String getIspostRelationdept() {
		return ispostRelationdept;
	}

	public void setIspostRelationdept(String ispostRelationdept) {
		this.ispostRelationdept = ispostRelationdept;
	}

	public String getIsUniqueGroupName() {
		return isUniqueGroupName;
	}

	public void setIsUniqueGroupName(String isUniqueGroupName) {
		this.isUniqueGroupName = isUniqueGroupName;
	}


	@Override
	public String getJSPUrl() {
		return "/hr/organization/hr_organization_personinfo_setting/index.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil
				.getString(
						"hr-organization:hr.organization.personinfo.setting");
	}

}

