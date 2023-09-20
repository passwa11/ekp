package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.util.UserUtil;

/**
 * 组织可见性 Action
 * 
 * @author
 * @version 1.0 2015-06-16
 */
public class SysOrganizationVisibleAction extends ExtendAction {
	protected ISysOrganizationVisibleService sysOrganizationVisibleService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrganizationVisibleService == null) {
            sysOrganizationVisibleService = (ISysOrganizationVisibleService) getBean("sysOrganizationVisibleService");
        }
		return sysOrganizationVisibleService;
	}

	public ActionForm test(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		((ISysOrganizationVisibleService) getServiceImp(request))
				.getPersonVisibleOrgIds(UserUtil.getUser());

		return null;
	}
}
