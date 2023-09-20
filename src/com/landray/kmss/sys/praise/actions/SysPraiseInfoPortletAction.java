package com.landray.kmss.sys.praise.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysPraiseInfoPortletAction extends ExtendAction {

	protected ISysPraiseInfoService sysPraiseInfoService;

	@Override
	protected ISysPraiseInfoService getServiceImp(HttpServletRequest request) {
		if (sysPraiseInfoService == null) {
			sysPraiseInfoService = (ISysPraiseInfoService) getBean("sysPraiseInfoService");
		}
		return sysPraiseInfoService;
	}

	public ActionForward getRankPortlet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getScorePortlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String rowsize = request.getParameter("rowsize");// 显示排行榜条数
			String ranktype = request.getParameter("ranktype");
			String deptId = request.getParameter("deptId");
			request.setAttribute("ranktype", ranktype);
			request.setAttribute("rowsize", rowsize);
			request.setAttribute("deptId", deptId);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getScorePortlet", false, getClass());
		if (messages.hasError()) {
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}
	
}
