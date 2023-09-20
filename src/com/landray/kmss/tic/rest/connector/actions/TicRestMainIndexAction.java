package com.landray.kmss.tic.rest.connector.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

public class TicRestMainIndexAction extends TicExtendAction {
	protected ITicRestMainService TicRestMainService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicRestMainService == null) {
			TicRestMainService = (ITicRestMainService) getBean("ticRestMainService");
		}
		return TicRestMainService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");		
		String hql=hqlInfo.getWhereBlock();
		// hql=StringUtil.linkString(hql, " and ", "ticRestMain.docIsNewVersion
		// = :docIsNewVersion");
		// hqlInfo.setParameter("docIsNewVersion", true);
		if(!StringUtil.isNull(categoryId)){
			hql=StringUtil.linkString(hql, " and ", "ticRestMain.docCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
		}
		if (!StringUtil.isNull(fdAppType)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestMain.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if (StringUtil.isNotNull(fdEnviromentId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestMain.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicRestMain.class);
		
	}
	
}
