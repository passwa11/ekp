package com.landray.kmss.third.weixin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.oms.ISynchroOrg2WxCheck;
import com.landray.kmss.third.weixin.oms.WxOmsConfig;
import com.landray.kmss.third.weixin.util.WxHttpClientUtil;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SynchroOrg2WxCheckAction extends ExtendAction {

	protected ISynchroOrg2WxCheck synchroOrg2WxCheck;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (synchroOrg2WxCheck == null) {
            synchroOrg2WxCheck = (ISynchroOrg2WxCheck) getBean(
                    "synchroOrg2WxCheck");
        }
		return synchroOrg2WxCheck;
	}

	public ActionForward checkData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			List<String> hiers = ((ISynchroOrg2WxCheck) getServiceImp(request))
					.getCheckHierarchy();
			// 人员信息
			List<String> errorPerson = ((ISynchroOrg2WxCheck) getServiceImp(
					request)).checkPersonInfo(hiers);
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (String ep : errorPerson) {
				jo = new JSONObject();
				jo.put("info", ep);
				ja.add(jo);
			}
			json.put("errorPerson", ja.toString());

			// 部门信息
			List<String> errorDept = ((ISynchroOrg2WxCheck) getServiceImp(
					request)).checkDeptInfo(hiers);
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
		// 记录日志信息
		if (UserOperHelper.allowLogOper("checkData", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString("third-weixin:third.wx.config.setting"));
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
			WxOmsConfig wxOmsConfig = new WxOmsConfig();
			wxOmsConfig.setLastUpdateTime("");
			wxOmsConfig.save();
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		// 记录日志信息
		if (UserOperHelper.allowLogOper("cleanTime", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString("third-weixin:third.wx.config.setting"));
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}
	
	protected IThirdWeixinWorkService thirdWeixinWorkService;

	protected IBaseService getThirdWeixinWorkServiceImp(HttpServletRequest request) {
		if (thirdWeixinWorkService == null) {
			thirdWeixinWorkService = (IThirdWeixinWorkService) getBean(
					"thirdWeixinWorkService");
		}
		return thirdWeixinWorkService;
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
			String type = request.getParameter("type");
			WxApiService wxApiService = null;
			WxworkApiService wxworkApiService = null;
			String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?agentid=";
			if(StringUtil.isNotNull(agentId)){
				url += agentId;
				if("1".equals(type)){
					wxApiService = WxUtils.getWxApiService();
					url += "&access_token=" + wxApiService.getAccessToken();
					WxHttpClientUtil.httpPost(url, null, null, null);
				}else{
					wxworkApiService = WxworkUtils.getWxworkApiService();
					List list = getThirdWeixinWorkServiceImp(request).findList("fdAgentid='"+agentId+"'", null);
					if(list==null||list.size()==0){
						json.put("status", 0);
						json.put("msg", "请先在EKP企业微信中配置应用");
					}
					url += "&access_token="
							+ wxworkApiService.getAccessTokenByAgentid(agentId);
					WxworkHttpClientUtil.httpPost(url, null, null, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		// 记录日志信息
		if (UserOperHelper.allowLogOper("agentIdCheck", "*")) {
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-agentIdCheck", false, getClass());
		return null;
	}
	
	public ActionForward urlCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String url = request.getParameter("source");
			String type = request.getParameter("type");
			if(StringUtil.isNotNull(url)&&(url.startsWith("http")||url.startsWith("https"))){
				if("wx".equals(type)){
					WxApiService wxApiService = WxUtils.getWxApiService();
					url = wxApiService.oauth2buildAuthorizationUrl(url, null);
				}else{
					WxworkApiService wxworkApiService = WxworkUtils
							.getWxworkApiService();
					url = wxworkApiService.oauth2buildAuthorizationUrl(url,
							null);
				}
				json.put("status", 1);
				json.put("msg", url);
			}else{
				json.put("status", 0);
				json.put("msg", "url为空或者不是以http开头");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		// 记录日志信息
		if (UserOperHelper.allowLogOper("urlCode", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString("third-weixin:third.wx.interation"));
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}
}
