package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.ResourceUtil;

public class SysOrgRoleServiceImp extends SysOrgElementServiceImp implements
		ISysOrgRoleService {

	@Override
	public void updateInvalidated(String id, RequestContext requestContext)
			throws Exception {
		SysOrgElement sysOrgElement = (SysOrgElement) this.findByPrimaryKey(id);
		String s_fdEnabled = requestContext.getParameter("fdEnabled");
		boolean fdEnabled = "true".equals(s_fdEnabled) ? true : false;
		// 记录日志
		if (UserOperHelper.allowLogOper("Service_Update",
				"com.landray.kmss.sys.organization.model.SysOrgRole")) {
			UserOperHelper.setEventType(fdEnabled ? ResourceUtil.getString("sys-organization:sysOrgPerson.fdDoubleValidation.enable")
					: ResourceUtil.getString("sys-organization:sysOrgPerson.fdDoubleValidation.disable"));
			UserOperContentHelper.putUpdate(sysOrgElement);
		}
		if (sysOrgElement != null) {
			sysOrgElement.setFdIsAvailable(fdEnabled);
		}
		super.update(sysOrgElement);
	}
}
