package com.landray.kmss.tic.rest.connector.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.rest.connector.model.TicRestSetting;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettingService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

public class TicRestSettingIndexAction extends ExtendAction {
	protected ITicRestSettingService TicRestSettingService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicRestSettingService == null) {
			TicRestSettingService = (ITicRestSettingService) getBean("ticRestSettingService");
		}
		return TicRestSettingService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");

		String hql=hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(fdAppType)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestSetting.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if (StringUtil.isNotNull(fdEnviromentId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestSetting.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicRestSetting.class);
		
	}
	
}
