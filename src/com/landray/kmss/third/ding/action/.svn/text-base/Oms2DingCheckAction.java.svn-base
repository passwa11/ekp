package com.landray.kmss.third.ding.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.ding.oms.DingOmsConfig;
import com.landray.kmss.third.ding.oms.SynchroOrg2DingCheck;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Oms2DingCheckAction extends ExtendAction {

	protected SynchroOrg2DingCheck synchroOrg2DingCheck;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (synchroOrg2DingCheck == null) {
            synchroOrg2DingCheck = (SynchroOrg2DingCheck) getBean(
                    "synchroOrg2DingCheck");
        }
		return synchroOrg2DingCheck;
	}

	public ActionForward checkData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			List<String> hiers = ((SynchroOrg2DingCheck) getServiceImp(request))
					.getCheckHierarchy();
			// 人员信息
			Long start = System.currentTimeMillis();
			logger.warn("检查人员信息开始..."+start);
			List<String> errorPerson = ((SynchroOrg2DingCheck) getServiceImp(
					request)).checkPersonInfo(hiers);
			logger.warn("检查人员信息结束,耗时："+(System.currentTimeMillis()-start)/1000+" 秒");
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (String ep : errorPerson) {
				jo = new JSONObject();
				jo.put("info", ep);
				ja.add(jo);
			}
			json.put("errorPerson", ja.toString());

			start = System.currentTimeMillis();
			logger.warn("检查部门信息开始..."+start);
			// 部门信息
			List<String> errorDept = ((SynchroOrg2DingCheck) getServiceImp(
					request)).checkDeptInfo(hiers);
			logger.warn("检查部门信息结束,耗时："+(System.currentTimeMillis()-start)/1000+" 秒");
			ja = new JSONArray();
			for (String ed : errorDept) {
				jo = new JSONObject();
				jo.put("info", ed);
				ja.add(jo);
			}
			json.put("errorDept", ja.toString());

			json.put("suc", "1");
			json.put("msg", "");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("suc", "0");
			json.put("msg", "检查出错，请联系管理员!");
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (UserOperHelper.allowLogOper("checkData", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("third-ding:module.third.ding"));
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().println(json.toString());
		return null;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("check", mapping, form, request, response);
		}
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
}
