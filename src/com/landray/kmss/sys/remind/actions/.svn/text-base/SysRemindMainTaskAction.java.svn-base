package com.landray.kmss.sys.remind.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提醒任务
 * 
 * @author panyh
 * @date Jun 28, 2020
 */
public class SysRemindMainTaskAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysRemindMainTaskService sysRemindMainTaskService;

	@Override
	protected ISysRemindMainTaskService getServiceImp(HttpServletRequest request) {
		if (sysRemindMainTaskService == null) {
			sysRemindMainTaskService = (ISysRemindMainTaskService) getBean("sysRemindMainTaskService");
		}
		return sysRemindMainTaskService;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String remindId = request.getParameter("remindId");
		if (StringUtil.isNotNull(remindId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"sysRemindMainTask.fdRemindId = :remindId"));
			hqlInfo.setParameter("remindId", remindId);
		}
	}

	/**
	 * 执行任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void runTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			getServiceImp(request).runTask(fdId);
			json.put("status", true);
		} catch (Exception e) {
			logger.error("执行任务失败：", e);
			json.put("status", false);
			json.put("message", e.getMessage());
		}
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
