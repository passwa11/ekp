package com.landray.kmss.third.ding.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingOmsInitService;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 组织初始化 Action
 * 
 * @author
 * @version 1.0 2017-06-14
 */
public class ThirdDingOmsInitAction extends ExtendAction {
	protected IThirdDingOmsInitService thirdDingOmsInitService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingOmsInitService == null) {
			thirdDingOmsInitService = (IThirdDingOmsInitService) getBean(
					"thirdDingOmsInitService");
		}
		return thirdDingOmsInitService;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		hqlInfo.setWhereBlock(
				"fdIsOrg='" + request.getParameter("fdIsOrg") + "'");
		hqlInfo.setOrderBy("fdPath,fdHandleStatus");
	}

	private static Object lock = new Object();

	public ActionForward omsInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-omsInit", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			synchronized (lock) {
				String fdIsOrg = request.getParameter("fdIsOrg");
				//未匹配数据清除
				String sql = "delete from ThirdDingOmsInit";
				if ("1".equals(fdIsOrg)) {
					sql += " where fdIsOrg = 1";
				} else {
					sql += " where fdIsOrg = 0";
				}
				getServiceImp(request).getBaseDao().getHibernateSession().createQuery(sql).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession().flush();
				getServiceImp(request).getBaseDao().getHibernateSession().clear();
				//数据匹配
				if ("1".equals(fdIsOrg)) {
					if (UserOperHelper.allowLogOper("updateDept",
							getServiceImp(request).getModelName())) {
						UserOperHelper.setEventType(ResourceUtil
								.getString(
										"third-ding:table.thirdDingOmsInit"));
						UserOperHelper.setModelNameAndModelDesc(null,
								ResourceUtil.getString(
										"third-ding:module.third.ding"));
					}
					((IThirdDingOmsInitService) getServiceImp(request))
							.updateDept(json);
				} else {
					if (UserOperHelper.allowLogOper("updatePerson",
							getServiceImp(request).getModelName())) {
						UserOperHelper.setEventType(ResourceUtil
								.getString(
										"third-ding:thirdDingOmsInit.person.init"));
						UserOperHelper.setModelNameAndModelDesc(null,
								ResourceUtil.getString(
										"third-ding:module.third.ding"));
					}
					((IThirdDingOmsInitService) getServiceImp(request))
							.updatePerson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-omsInit", false, getClass());
		return null;
	}

	public ActionForward dingDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-dingDel", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String parentId = request.getParameter("parentId");
			((IThirdDingOmsInitService) getServiceImp(request)).updateDing(fdId,
					parentId, type);
			json.put("status", 1);
			json.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-dingDel", false, getClass());
		return null;
	}

	public ActionForward ekpUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-ekpUpdate", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String fdEKPId = request.getParameter("fdEKPId");
			boolean flag = ((IThirdDingOmsInitService) getServiceImp(request)).updateEKP(fdId,
					fdEKPId, type);
			if(flag){
				json.put("status", 1);
				json.put("msg", "成功");
			}else{
				json.put("status", 0);
				json.put("msg", "请不要重复映射");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-ekpUpdate", false, getClass());
		return null;
	}

	private DingApiService dingApiService = null;

	private IThirdDingWorkService thirdDingWorkService;

	public ActionForward appList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-appList", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		JSONArray ja = new JSONArray();
		JSONObject jn = null;
		try {
			List<ThirdDingWork> list = getThirdDingWorkService()
					.findList(new HQLInfo());
			for (ThirdDingWork work : list) {
				String name = work.getFdName();
				String agentId = work.getFdAgentid();
				jn = new JSONObject();
				jn.put("name", name);
				jn.put("appId", agentId);
				ja.add(jn);
			}
			json.put("data", ja);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		if (UserOperHelper.allowLogOper("appList", "*")) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-appList", false, getClass());
		return null;
	}

	public ActionForward appListOld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-appList", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		JSONArray ja = new JSONArray();
		JSONObject jn = null;
		try {
			dingApiService = DingUtils.getDingApiService();
			JSONObject appJson = dingApiService.getApps();
			System.out.println(appJson.toString());
			if (appJson != null && appJson.containsKey("errcode")
					&& appJson.getInt("errcode") == 0) {
				JSONArray aja = appJson.getJSONArray("appList");
				for (int i = 0; i < aja.size(); i++) {
					if (1 == aja.getJSONObject(i).getInt("appStatus")
							&& aja.getJSONObject(i).getBoolean("isSelf")) {
						jn = new JSONObject();
						jn.put("name", aja.getJSONObject(i).getString("name"));
						jn.put("appId",
								aja.getJSONObject(i).getString("agentId"));
						ja.add(jn);
					}
				}
			}
			String aid = DingConfig.newInstance().getDingAgentid();
			if (ja.size() == 0 && StringUtil.isNotNull(aid)) {
				jn = new JSONObject();
				jn.put("name", aid);
				jn.put("appId", aid);
				ja.add(jn);
			}
			json.put("data", ja);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		if (UserOperHelper.allowLogOper("appList", "*")) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-appList", false, getClass());
		return null;
	}

	public IThirdDingWorkService getThirdDingWorkService() {
		if (thirdDingWorkService == null) {
			thirdDingWorkService = (IThirdDingWorkService) SpringBeanUtil
					.getBean("thirdDingWorkService");
		}
		return thirdDingWorkService;
	}

	public void setThirdDingWorkService(
			IThirdDingWorkService thirdDingWorkService) {
		this.thirdDingWorkService = thirdDingWorkService;
	}
}
