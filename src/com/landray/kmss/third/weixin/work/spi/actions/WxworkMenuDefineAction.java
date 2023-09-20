package com.landray.kmss.third.weixin.work.spi.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWork;
import com.landray.kmss.third.weixin.work.model.api.WxMenu;
import com.landray.kmss.third.weixin.work.model.api.WxMenu.WxMenuButton;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkMenuModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkMenuService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxworkMenuDefineAction extends ExtendAction {

	protected IWxworkMenuService wxworkMenuService;

	private String fdModelId = "wx1234567890";

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (wxworkMenuService == null) {
            wxworkMenuService = (IWxworkMenuService) getBean("wxworkMenuService");
        }
		return wxworkMenuService;
	}

	private WxworkApiService wxworkApiService = null;

	private void init() {
		wxworkApiService = WxworkUtils.getWxworkApiService();
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
			if(StringUtil.isNotNull(name)){
				byte[] buff=name.getBytes();
			    json.put("len", buff.length);
			}else{
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
		// 记录日志信息
		if (UserOperHelper.allowLogOper("checkLen",
				getServiceImp(request).getModelName())) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
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
				UserOperHelper.logFind(model);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				String fdMenuJson = ((WxworkMenuModel) model).getFdMenuJson();
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
//		List<WxCpAgent> agents = wxCpService.agentList();
		IThirdWeixinWorkService thirdWeixinWorkService = (IThirdWeixinWorkService) SpringBeanUtil.getBean("thirdWeixinWorkService");
		List<ThirdWeixinWork> works = thirdWeixinWorkService.findList(null, "fdAgentid");
		List<Map<String, String>> agentList = new ArrayList<Map<String, String>>();
		String url = WxworkUtils.getWxworkApiUrl() +"/agent/get?";
		String agentSimple = null;
		JSONObject jo = null;
		String fdAppId = request.getParameter("fdAppId");
		String menuAgentId = null;
		for (ThirdWeixinWork work : works) {
//			agentSimple = wxCpService.get(url,
//					"access_token=" + wxCpService.getAccessToken() + "&agentid="
//							+ a.getAgentid());
//			if (StringUtil.isNotNull(agentSimple)) {
//				jo = JSONObject.fromObject(agentSimple);
//				if (jo.containsKey("errcode")
//						&& jo.getString("errcode").equals("0")) {
//					if (jo.containsKey("type") && jo.getInt("type") == 2) {
//						continue;
//					}
//				}
//			}
			if(StringUtil.isNotNull(fdAppId)&&work.getFdId().equals(fdAppId)){
				request.setAttribute("checkedAppId", work.getFdAgentid());
				menuAgentId = work.getFdAgentid();
			}
			Map<String, String> agentMap = new HashMap<String, String>();
			agentMap.put("id", work.getFdAgentid());
			agentMap.put("name", work.getFdName());
			agentList.add(agentMap);
		}
		request.setAttribute("agents", agentList);
		boolean rmerror = false;
		List allApps = getServiceImp(request).findList(null, null);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allApps.size(); i++) {
			WxworkMenuModel mm = (WxworkMenuModel) allApps.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append(mm.getFdAgentId());
			if(mm.getFdAgentId().equals(menuAgentId)){
				rmerror = true;
			}
		}
		request.setAttribute("allAgentIds", sb.toString());
		if(rmerror){
			throw new KmssRuntimeException(new KmssMessage(
				"third-weixin-work:third.wx.menu.btn.wxwork.menu.create"));
		}
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

			WxworkMenuModel mm = (WxworkMenuModel) getServiceImp(request)
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

			WxworkMenuModel mm = null;
			String fdId = request.getParameter("fdId");
			if (!ModelUtil.isModelMerge(WxworkMenuModel.class.getName(),
					fdId)) {
				throw new NoRecordException();
			} else {
				mm = (WxworkMenuModel) getServiceImp(request)
						.findByPrimaryKey(fdId);
			}
			init();
			wxworkApiService.menuDelete(mm.getFdAgentId());

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
						button.setType(WxworkConstant.BUTTON_VIEW);
						String url = m.getString("url");
						url = getUrl(url);
						url = wxworkApiService.oauth2buildAuthorizationUrl(url,
								null);
						button.setUrl(url);
					}
				} else {
					JSONArray jas = m.getJSONArray("sub_button");
					for (int j = 0; j < jas.size(); j++) {
						JSONObject sub = jas.getJSONObject(j);

						WxMenuButton subb = new WxMenuButton();
						subb.setType(WxworkConstant.BUTTON_VIEW);
						subb.setName(sub.getString("name"));
						String url = sub.getString("url");
						url = getUrl(url);
						url = wxworkApiService.oauth2buildAuthorizationUrl(url,
								null);
						subb.setUrl(url);
						button.getSubButtons().add(subb);
					}
				}
			}
			wxworkApiService.menuCreate(mm.getFdAgentId(), wxMenu);

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
				url += "&oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
			} else {
				url += "?oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
			}
		}
		return url;
	}
}
