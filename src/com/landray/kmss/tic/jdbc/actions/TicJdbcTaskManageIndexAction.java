/**
 * 
 */
package com.landray.kmss.tic.jdbc.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskManage;
import com.landray.kmss.tic.jdbc.service.ITicJdbcTaskManageService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * @author qiujh
 * @version 1.0 2013-12-31
 */
public class TicJdbcTaskManageIndexAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected ITicJdbcTaskManageService ticJdbcTaskManageService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (ticJdbcTaskManageService == null) {
			ticJdbcTaskManageService = (ITicJdbcTaskManageService) getBean("ticJdbcTaskManageService");
		}
		return ticJdbcTaskManageService;
	}
	
	@Override
    public ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticJdbcTaskManage.docCategory.fdId like :categoryId ");
			hqlInfo.setParameter("categoryId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				TicJdbcTaskManage.class);
	}

}
