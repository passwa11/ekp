/**
 * 
 */
package com.landray.kmss.tic.soap.connector.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

/**
 * @author qiujh
 * @version 1.0 2013-12-31
 */
public class TicSoapSettingIndexAction extends ExtendAction {
	protected ITicSoapSettingService TicSoapSettingService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicSoapSettingService == null) {
			TicSoapSettingService = (ITicSoapSettingService) getBean("ticSoapSettingService");
		}
		return TicSoapSettingService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");
		String hql=hqlInfo.getWhereBlock();
		//hql=StringUtil.linkString(hql, " and ", "ticSoapSetting.docIsNewVersion = :docIsNewVersion");
		//hqlInfo.setParameter("docIsNewVersion", true);
		if(!StringUtil.isNull(categoryId)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapSetting.settCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
		}
		if(!StringUtil.isNull(fdAppType)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapSetting.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if(StringUtil.isNotNull(fdEnviromentId)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapSetting.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicSoapSetting.class);
	}
}
