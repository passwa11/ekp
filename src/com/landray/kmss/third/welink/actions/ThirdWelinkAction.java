package com.landray.kmss.third.welink.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.welink.interfaces.ApiException;
import com.landray.kmss.third.welink.oms.WelinkOmsConfig;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWelinkAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkAction.class);


    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		return null;
    }

	public ActionForward delOmsTimestamp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delOmsTimestamp", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			WelinkOmsConfig config = new WelinkOmsConfig();
			config.setLastSynchroTime(null);
			config.save();

		} catch (Exception e) {
			logger.error("删除同步时间戳失败", e);
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-delOmsTimestamp", false, getClass());
		return null;
	}

	public ActionForward getSyncStatus(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getUserSyncStatus", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String orgType = request.getParameter("orgType");
		try {
			IThirdWelinkService thirdWelinkService = (IThirdWelinkService) SpringBeanUtil
					.getBean("thirdWelinkService");
			if (StringUtil.isNull(orgType) || "person".equals(orgType)) {
			IThirdWelinkPersonMappingService thirdWelinkPersonMappingService = (IThirdWelinkPersonMappingService) SpringBeanUtil
					.getBean("thirdWelinkPersonMappingService");
				List list = thirdWelinkPersonMappingService.findValue(
						"thirdWelinkPersonMapping.fdEkpPerson.fdId", null,
						null);
				try {
					JSONArray personSyncResult = thirdWelinkService
							.getUserSyncStatus(list);
					request.setAttribute("personSyncResult",
							"人员同步结果：<br>" + personSyncResult.toString());
				} catch (ApiException e) {
					request.setAttribute("personSyncResult",
							"<span style='color:red'>人员同步出错</span><br>"
									+ e.getMessage());
				}
			}

			if (StringUtil.isNull(orgType) || "dept".equals(orgType)) {
				IThirdWelinkDeptMappingService thirdWelinkDeptMappingService = (IThirdWelinkDeptMappingService) SpringBeanUtil
						.getBean("thirdWelinkDeptMappingService");
				List list = thirdWelinkDeptMappingService.findValue(
						"thirdWelinkDeptMapping.fdEkpDept.fdId", null,
						null);
				JSONArray deptSyncResult = thirdWelinkService
						.getDeptSyncStatus(list);
				request.setAttribute("deptSyncResult",
						"部门同步结果：<br>" + deptSyncResult.toString());
			}
			return getActionForward("syncStatus", mapping, form, request,
					response);
		} catch (Exception e) {
			logger.error("获取同步结果失败," + e.getMessage(), e);
			messages.addError(e);
			return getActionForward("failure", mapping, form, request,
					response);
		}
	}

}
