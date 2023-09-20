package com.landray.kmss.tic.jdbc.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;


/**
 * 数据集管理 Action
 * 
 * @author 
 * @version 1.0 2014-04-15
 */
public class TicJdbcDataSetIndexAction extends TicExtendAction {
	protected ITicJdbcDataSetService ticJdbcDataSetService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(ticJdbcDataSetService == null) {
			ticJdbcDataSetService = (ITicJdbcDataSetService)getBean("ticJdbcDataSetService");
		}
		return ticJdbcDataSetService;
	}
	
	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");		
		String hql = hqlInfo.getWhereBlock();
		if(!StringUtil.isNull(categoryId)){
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcDataSet.fdCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
		}
		if (!StringUtil.isNull(fdAppType)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcDataSet.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if (StringUtil.isNotNull(fdEnviromentId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcDataSet.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicJdbcDataSet.class);
	}
}

