package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;

/**
 * @version 1.0
 * @author
 */
public class SysOrgGroupCateAction extends ExtendAction implements
		SysOrgConstant {
	private ISysOrgGroupCateService sysOrgGroupCateService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgGroupCateService == null) {
            sysOrgGroupCateService = (ISysOrgGroupCateService) getBean("sysOrgGroupCateService");
        }
		return sysOrgGroupCateService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysOrgGroupCate.class);
	}
}
