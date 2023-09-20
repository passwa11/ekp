package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixCateService;
import com.landray.kmss.util.HQLHelper;

/**
 * 矩阵分类
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixCateAction extends ExtendAction {

	private ISysOrgMatrixCateService sysOrgMatrixCateService;

	@Override
	protected ISysOrgMatrixCateService getServiceImp(HttpServletRequest request) {
		if (sysOrgMatrixCateService == null) {
			sysOrgMatrixCateService = (ISysOrgMatrixCateService) getBean("sysOrgMatrixCateService");
		}
		return sysOrgMatrixCateService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOrgMatrixCate.class);
	}

}
