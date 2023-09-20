/**
 * 
 */
package com.landray.kmss.tic.soap.connector.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

/**
 * @author qiujh
 * @version 1.0 2013-12-30
 */
public class TicSoapMainIndexAction extends TicExtendAction {
	protected ITicSoapMainService TicSoapMainService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicSoapMainService == null) {
			TicSoapMainService = (ITicSoapMainService) getBean("ticSoapMainService");
		}
		return TicSoapMainService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");
		String hql=hqlInfo.getWhereBlock();
		// hql=StringUtil.linkString(hql, " and ", "ticSoapMain.docIsNewVersion
		// = :docIsNewVersion");
		// hqlInfo.setParameter("docIsNewVersion", true);
		if(!StringUtil.isNull(categoryId)){
			hql = StringUtil.linkString(hql, " and ",
					"ticSoapMain.fdCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
		}
		if(!StringUtil.isNull(fdAppType)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapMain.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if(StringUtil.isNotNull(fdEnviromentId)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapMain.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicSoapMain.class);
		
	}
	
}
