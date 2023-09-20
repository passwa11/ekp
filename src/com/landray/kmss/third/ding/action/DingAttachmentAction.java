package com.landray.kmss.third.ding.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.oms.DingOmsConfig;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class DingAttachmentAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingAttachmentAction.class);

	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getServiceImp(
			HttpServletRequest request) {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
		return sysAttMainService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public ActionForward cleanTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			DingOmsConfig config = new DingOmsConfig();
			String oldLastUpdateTime = config.getLastUpdateTime();
			config.setLastUpdateTime("");
			if (UserOperHelper.allowLogOper("cleanTime", "*")) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("third-ding:module.third.ding"));
				UserOperContentHelper.putUpdate("")
						.putSimple("lastUpdateTime", oldLastUpdateTime, "");
			}
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}
	
	public ActionForward cleanLdingTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			DingConfig config = DingConfig.newInstance();
			String ldingSynTime = config.getLdingSynTime();
			config.setLdingSynTime(null);
			if (UserOperHelper.allowLogOper("cleanLdingTime", "*")) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("third-ding:module.third.ding"));
				UserOperContentHelper.putUpdate("")
				.putSimple("ldingSynTime", ldingSynTime, "");
			}
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}
	
	public ActionForward agentIdCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-agentIdCheck", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String agentId = request.getParameter("agentId");
			DingApiService dingApiService = DingUtils.getDingApiService();
			if(StringUtil.isNotNull(agentId)){
				JSONObject jo = dingApiService.appVisible(agentId);
				System.out.println(jo.toString());
				if(jo.getInt("errcode")!=0){
					json.put("status", 0);
					json.put("msg", jo.getString("errmsg"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		if (UserOperHelper.allowLogOper("agentIdCheck", "*")) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-agentIdCheck", false, getClass());
		return null;
	}

	/**
	 * 根据节点是否包括生态组织
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkExternal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkExternal", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 0);
		json.put("msg", "");
		try {
			String dingOrgId = request.getParameter("dingOrgId");
			if (StringUtil.isNotNull(dingOrgId)) {
				String[] ids = dingOrgId.split(";");
				List<SysOrgElement> list = getSysOrgCoreService()
						.findByPrimaryKeys(ids);
				if (!list.isEmpty()) {
					for (SysOrgElement org : list) {
						if (Boolean.TRUE.equals(org.getFdIsExternal())) {
							json.put("status", 1);
							json.put("msg", "成功");
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		if (UserOperHelper.allowLogOper("checkExternal", "*")) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-checkExternal", false, getClass());
		return null;
	}
}
