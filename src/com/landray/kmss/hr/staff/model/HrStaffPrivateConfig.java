package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
public class HrStaffPrivateConfig  extends BaseAppConfig {

	public HrStaffPrivateConfig() throws Exception {
		super();
	}
	
	@Override
	public String getJSPUrl() {
		return "/hr/staff/hr_staff_private_config/hrStaffPrivateConfig_edit.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("hr-staff:hr.staff.tree.privacy.settings");
	}
	
}