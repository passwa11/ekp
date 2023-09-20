package com.landray.kmss.sys.filestore.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.filestore.service.ISysFileConvertLogService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.util.StringUtil;

public class SysFileConvertLogAction extends ExtendAction {

	protected ISysFileConvertLogService sysFileConvertLogService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertLogService == null) {
			sysFileConvertLogService = (ISysFileConvertLogService) SpringBeanUtil.getBean("sysFileConvertLogService");
		}
		return sysFileConvertLogService;
	}

	public ActionForward delLogs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delLogs", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("selected");
			String delType = request.getParameter("delType");
			((ISysFileConvertLogService) getServiceImp(request)).deleteLogs(delType, request.getParameter("queueId"),
					ids);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-delLogs", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		String queueId = request.getParameter("queueId");
		if (StringUtil.isNotNull(queueId)) {
			where += "and sysFileConvertLog.fdQueueId = :queueId";
			hqlInfo.setParameter("queueId", queueId);
		}
		hqlInfo.setWhereBlock(where);
	}
}
