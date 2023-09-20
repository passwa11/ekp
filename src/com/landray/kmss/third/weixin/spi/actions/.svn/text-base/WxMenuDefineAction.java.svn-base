package com.landray.kmss.third.weixin.spi.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.api.WxAgent;
import com.landray.kmss.third.weixin.model.api.WxMenu;
import com.landray.kmss.third.weixin.model.api.WxMenu.WxMenuButton;
import com.landray.kmss.third.weixin.spi.model.WxMenuModel;
import com.landray.kmss.third.weixin.spi.service.IWxMenuService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxMenuDefineAction extends ExtendAction {

	protected IWxMenuService wxMenuService;

	private String fdModelId = "wx1234567890";

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (wxMenuService == null) {
            wxMenuService = (IWxMenuService) getBean("wxMenuService");
        }
		return wxMenuService;
	}

	private WxApiService wxApiService = null;

	private void init() {
		wxApiService = WxUtils.getWxApiService();
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		hqlInfo.setWhereBlock("fdId!='" + fdModelId + "'");
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	public ActionForward checkLen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkLen", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String name = request.getParameter("name");
			if (StringUtil.isNotNull(name)) {
				byte[] buff = name.getBytes();
				json.put("len", buff.length);
			} else {
				json.put("len", 0);
				json.put("status", 0);
				json.put("msg", "名称为空，无法获取字节长度");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("len", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		// 记录日志信息
		if (UserOperHelper.allowLogOper("checkLen",
				getServiceImp(request).getModelName())) {
			UserOperHelper.logMessage(json.toString());
		}
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-checkLen", false, getClass());
		return null;
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (model != null) {
				UserOperHelper.logFind(model);// 记录日志信息
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				String fdMenuJson = ((WxMenuModel) model).getFdMenuJson();
				JSONObject jso = JSONObject.fromObject(fdMenuJson);

				JSONArray jsa = jso.getJSONArray("button");
				for (int i = 0; i < jsa.size(); i++) {
					JSONObject m = jsa.getJSONObject(i);
					request.setAttribute("subm" + (i + 1) + "Name",
							m.getString("name"));
					if (m.get("type") != null) {
						if ("view".equals(m.getString("type"))) {
							request.setAttribute("subm" + (i + 1) + "Type",
									m.getString("type"));
							request.setAttribute("subm" + (i + 1) + "Link",
									m.getString("url"));
						}
					} else {
						JSONArray jas = m.getJSONArray("sub_button");
						List fdLinks = new ArrayList();
						for (int j = 0; j < jas.size(); j++) {
							JSONObject sub = jas.getJSONObject(j);
							Map<String, String> subm = new HashMap<String, String>();
							subm.put("name", sub.getString("name"));
							subm.put("url", sub.getString("url"));
							subm.put("type", sub.getString("type"));
							fdLinks.add(subm);
						}
						request.setAttribute("fdLinks" + (i + 1), fdLinks);
					}
				}

				request.setAttribute("jso", jso);
			}

			initAllAgent(request);

			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);

		}

	}

	private void initAllAgent(HttpServletRequest request) throws Exception {
		init();
		List<WxAgent> agents = wxApiService.agentList();
		List<Map<String, String>> agentList = new ArrayList<Map<String, String>>();

		String agentSimple = null;
		JSONObject jo = null;
		for (WxAgent a : agents) {
			agentSimple = wxApiService.agentGet(a.getAgentid());

			if (StringUtil.isNotNull(agentSimple)) {
				jo = JSONObject.fromObject(agentSimple);
				if (jo.containsKey("errcode")
						&& "0".equals(jo.getString("errcode"))) {
					if (jo.containsKey("type") && jo.getInt("type") == 2) {
						continue;
					}
				}
			}
			Map<String, String> agentMap = new HashMap<String, String>();
			agentMap.put("id", a.getAgentid());
			agentMap.put("name", a.getName());
			agentList.add(agentMap);
		}
		request.setAttribute("agents", agentList);

		List allApps = getServiceImp(request).findList(null, null);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allApps.size(); i++) {
			WxMenuModel mm = (WxMenuModel) allApps.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append(mm.getFdAgentId());
		}
		request.setAttribute("allAgentIds", sb.toString());

	}

	@Override
    protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		initAllAgent(request);
		return form;
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));

			WxMenuModel mm = (WxMenuModel) getServiceImp(request)
					.findByPrimaryKey(request.getParameter("fdId"));
			mm.setFdPublished("");
			getServiceImp(request).update(mm);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			if ("POST".equals(request.getMethod())) {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}

			WxMenuModel mm = null;
			String fdId = request.getParameter("fdId");
			if (!ModelUtil.isModelMerge(WxMenuModel.class.getName(), fdId)) {
				throw new NoRecordException();
			} else {
				mm = (WxMenuModel) getServiceImp(request)
						.findByPrimaryKey(fdId);
			}
			init();
			wxApiService.menuDelete(mm.getFdAgentId());

			String fdMenuJson = mm.getFdMenuJson();
			WxMenu wxMenu = new WxMenu();

			JSONObject jso = JSONObject.fromObject(fdMenuJson);
			JSONArray jsa = jso.getJSONArray("button");
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject m = jsa.getJSONObject(i);
				WxMenuButton button = new WxMenuButton();
				button.setName(m.getString("name"));
				wxMenu.getButtons().add(button);
				if (m.get("type") != null) {
					if ("view".equals(m.getString("type"))) {
						button.setType(WxConstant.BUTTON_VIEW);
						String url = m.getString("url");
						url = getUrl(url);
						url = wxApiService.oauth2buildAuthorizationUrl(url,
								null);
						button.setUrl(url);
					}
				} else {
					JSONArray jas = m.getJSONArray("sub_button");
					for (int j = 0; j < jas.size(); j++) {
						JSONObject sub = jas.getJSONObject(j);

						WxMenuButton subb = new WxMenuButton();
						subb.setType(WxConstant.BUTTON_VIEW);
						subb.setName(sub.getString("name"));
						String url = sub.getString("url");
						url = getUrl(url);
						url = wxApiService.oauth2buildAuthorizationUrl(url,
								null);
						subb.setUrl(url);
						button.getSubButtons().add(subb);
					}
				}
			}

			wxApiService.menuCreate(mm.getFdAgentId(), wxMenu);

			if (mm != null) {
				mm.setFdPublished("1");
				getServiceImp(request).update(mm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	private String getUrl(String url) {
		if (url.indexOf("http") == -1) {
			// 没有http开头认为是外部URL
			url = "http://" + url;
		} else {
			if (url.indexOf("?") != -1) {
				url += "&oauth=" + WxConstant.OAUTH_EKP_FLAG;
			} else {
				url += "?oauth=" + WxConstant.OAUTH_EKP_FLAG;
			}
		}
		return url;
	}

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
		JSONObject jo = null;
		try {
			init();
			String agentSimple = null;
			List<WxAgent> agents = wxApiService.agentList();
			// 记录日志信息
			if (UserOperHelper.allowLogOper("appList",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logFindAll(agents, null);
			}
			for (WxAgent a : agents) {
				if (!"0".equals(a.getAgentid())) {
					agentSimple = wxApiService.agentGet(a.getAgentid());

					if (StringUtil.isNotNull(agentSimple)) {
						jo = JSONObject.fromObject(agentSimple);
						if (jo.containsKey("errcode")
								&& 0 == jo.getInt("errcode")
								&& jo.containsKey("close")
										&& 0 == jo.getInt("close")) {
							jn = new JSONObject();
							jn.put("name", a.getName());
							jn.put("appId", a.getAgentid());
							ja.add(jn);
						}
					}
				}
			}
			json.put("data", ja);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-appList", false, getClass());
		return null;
	}
}
