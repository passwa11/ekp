package com.landray.kmss.sys.restservice.server.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerLogService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RestService日志表 Action
 * 
 * @author  
 */
public class SysRestserviceServerLogAction extends ExtendAction {
	protected ISysRestserviceServerLogService sysRestserviceServerLogService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysRestserviceServerLogService == null) {
			sysRestserviceServerLogService = (ISysRestserviceServerLogService) getBean("sysRestserviceServerLogService");
		}
		return sysRestserviceServerLogService;
	}

	/**
	 * 打开超时预警列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward timeout(ActionMapping mapping, ActionForm form,
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
			int rowsize = 0;
			if (StringUtil.isNotNull(SysConfigParameters.getFdRowSize())) {
				rowsize = Integer.parseInt(SysConfigParameters.getFdRowSize());
			}
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}

			ISysRestserviceServerLogService service = (ISysRestserviceServerLogService) getServiceImp(request);
			Page page = service.findTimeoutPage(getFindPageOrderBy(request,
					orderby), pageno, rowsize);
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

		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				SysRestserviceServerLog.class);
	}
}
