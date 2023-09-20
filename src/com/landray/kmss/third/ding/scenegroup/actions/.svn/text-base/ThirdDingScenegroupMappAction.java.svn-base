package com.landray.kmss.third.ding.scenegroup.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.ding.interfaces.DingScenegroupUtil;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingScenegroupMappForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupMappService;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupModuleService;
import com.landray.kmss.third.ding.scenegroup.util.DingScenegroupApiUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingScenegroupMappAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingScenegroupMappAction.class);

    private IThirdDingScenegroupMappService thirdDingScenegroupMappService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingScenegroupMappService == null) {
            thirdDingScenegroupMappService = (IThirdDingScenegroupMappService) getBean("thirdDingScenegroupMappService");
        }
        return thirdDingScenegroupMappService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingScenegroupMapp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo,
				request,
				com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingScenegroupMappForm thirdDingScenegroupMappForm = (ThirdDingScenegroupMappForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingScenegroupMappService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingScenegroupMappForm;
    }

	public ActionForward getCreateScenegroupInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			String fdKey = request.getParameter("fdKey");
			String moduleKey = request.getParameter("moduleKey");
			String ownerPersonId = request.getParameter("ownerPersonId");
			String personIds = request.getParameter("personIds");

			ThirdDingScenegroupMapp mapp = ((IThirdDingScenegroupMappService) getServiceImp(
					request)).findByModel(modelName, fdId, fdKey);
			if (mapp != null) {
				json.put("result", false);
				json.put("errorMsg", "已经存在重复的群");
				out(request, response, json);
				return null;
			}

			IThirdDingScenegroupModuleService thirdDingScenegroupModuleService = (IThirdDingScenegroupModuleService) SpringBeanUtil
					.getBean("thirdDingScenegroupModuleService");
			ThirdDingScenegroupModule module = thirdDingScenegroupModuleService
					.findByKey(moduleKey);
			if (module == null) {
				json.put("result", false);
				json.put("errorMsg", "找不到对应的群模板，" + moduleKey);
				out(request, response, json);
				return null;
			}

			List<String> ownerPersonIdList = new ArrayList<String>();
			ownerPersonIdList.add(ownerPersonId);
			String ownerDingId = DingScenegroupApiUtil
					.buildUserIds(ownerPersonIdList);
			if (StringUtil.isNull(ownerDingId)) {
				json.put("result", false);
				json.put("errorMsg", "找不到群主的dingId，" + ownerPersonId);
				out(request, response, json);
				return null;
			}

			String[] personIdArray = personIds.split(";");
			List<String> personIdList = new ArrayList<String>();
			for (String personId : personIdArray) {
				personIdList.add(personId);
			}
			String dingIds = DingScenegroupApiUtil.buildUserIds(personIdList);
			String[] dingIdArray = dingIds.split(",");
			JSONArray pickedUsers = new JSONArray();
			for (String dingId : dingIdArray) {
				pickedUsers.add(dingId);
			}
			json.put("result", true);
			JSONObject data = new JSONObject();
			data.put("corpId", DingUtil.getCorpId());
			data.put("appId", DingUtil.getAgentIdByCorpId(null));
			data.put("pickedUsers", pickedUsers);
			json.put("data", data);
			logger.debug("钉聊默认带出的参与人员：" + pickedUsers);
			out(request, response, json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("errorMsg", e.getMessage());
			out(request, response, json);
		}
		return null;
	}

	private void out(HttpServletRequest request, HttpServletResponse response,
			JSONObject json) throws IOException {
		response.setCharacterEncoding("UTF-8");
		if (UserOperHelper.allowLogOper("getCreateScenegroupInfo",
				getServiceImp(request).getModelName())) {
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}

	public ActionForward createScenegroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			String fdKey = request.getParameter("fdKey");
			String moduleKey = request.getParameter("moduleKey");
			String ownerPersonId = request.getParameter("ownerPersonId");
			String users = request.getParameter("users");
			String title = request.getParameter("title");
			String personIds = request.getParameter("personIds");

			if (StringUtil.isNotNull(users)) {
				users = users.replaceAll(";", ",");
			}

			ThirdDingScenegroupMapp mapp = ((IThirdDingScenegroupMappService) getServiceImp(
					request)).findByModel(modelName, fdId, fdKey);
			if (mapp != null) {
				json.put("result", false);
				json.put("errorMsg", "已经存在重复的群");
				out(request, response, json);
				return null;
			}

			IThirdDingScenegroupModuleService thirdDingScenegroupModuleService = (IThirdDingScenegroupModuleService) SpringBeanUtil
					.getBean("thirdDingScenegroupModuleService");
			ThirdDingScenegroupModule module = thirdDingScenegroupModuleService
					.findByKey(moduleKey);
			if (module == null) {
				json.put("result", false);
				json.put("errorMsg", "找不到对应的群模板，" + moduleKey);
				out(request, response, json);
				return null;
			}

			List<String> ownerPersonIdList = new ArrayList<String>();
			ownerPersonIdList.add(ownerPersonId);
			String ownerDingId = DingScenegroupApiUtil
					.buildUserIds(ownerPersonIdList);
			if (StringUtil.isNull(ownerDingId)) {
				json.put("result", false);
				json.put("errorMsg", "找不到群主的dingId，" + ownerPersonId);
				out(request, response, json);
				return null;
			}

			if (StringUtil.isNotNull(personIds)) {
				String[] personIdsArray = personIds.split(";");
				DingScenegroupUtil.createScenegroup(moduleKey, title,
						ownerPersonId,
						(List<String>) Arrays.asList(personIdsArray), modelName,
						fdId, fdKey);
			} else {
				DingScenegroupUtil.createScenegroup(moduleKey, title,
						ownerDingId,
					users, modelName, fdId, fdKey);
			}

			json.put("result", true);
			JSONObject data = new JSONObject();
			data.put("corpId", DingUtil.getCorpId());
			data.put("appId", DingUtil.getAgentIdByCorpId(null));
			json.put("data", data);
			out(request, response, json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("errorMsg", e.getMessage());
			out(request, response, json);
		}
		return null;
	}
}
