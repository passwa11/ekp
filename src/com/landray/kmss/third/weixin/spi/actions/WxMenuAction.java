package com.landray.kmss.third.weixin.spi.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxMenu;
import com.landray.kmss.third.weixin.model.api.WxMenu.WxMenuButton;
import com.landray.kmss.third.weixin.spi.forms.WxMenuForm;
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

public class WxMenuAction extends ExtendAction {

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
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		if (!ModelUtil.isModelMerge(WxMenuModel.class.getName(), fdModelId)) {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			rtnForm = (IExtendForm) newForm;
			rtnForm.setFdId(fdModelId);
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		} else {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(
					fdModelId, null, true);
			if (model != null) {
				// 记录日志信息
				UserOperHelper.logFind(model);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				String fdMenuJson = ((WxMenuModel) model).getFdMenuJson();
				JSONObject jso = JSONObject.fromObject(fdMenuJson);
				JSONArray jsa = jso.getJSONArray("button");
				for (int i = 0; i < jsa.size(); i++) {
					JSONObject m = jsa.getJSONObject(i);
					request.setAttribute("fdName" + (i + 1), m
							.getString("name"));
					StringBuffer sb = new StringBuffer();
					JSONArray jas = m.getJSONArray("sub_button");
					for (int j = 0; j < jas.size(); j++) {
						JSONObject sub = jas.getJSONObject(j);
						sb.append("<option value='" + sub.getString("url")
								+ "'>" + sub.getString("name") + "</option>");
					}
					request.setAttribute("tmp_M" + (i + 1) + "Sel", sb
							.toString());

				}
			}
			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}
	}

	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (!ModelUtil.isModelMerge(WxMenuModel.class.getName(), fdModelId)) {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"/third/wx/menu/wxMenu.do?method=edit", false)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (!ModelUtil.isModelMerge(WxMenuModel.class.getName(), fdModelId)) {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}
			init();
			wxApiService.menuDelete(WeixinConfig.newInstance().getWxAgentid());
			
			WxMenuForm wxForm = (WxMenuForm) form;
			String fdMenuJson = wxForm.getFdMenuJson();
			WxMenu wxMenu = new WxMenu();

			JSONObject jso = JSONObject.fromObject(fdMenuJson);
			JSONArray jsa = jso.getJSONArray("button");
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject m = jsa.getJSONObject(i);
				WxMenuButton button = new WxMenuButton();
				button.setName(m.getString("name"));
				wxMenu.getButtons().add(button);

				JSONArray jas = m.getJSONArray("sub_button");
				for (int j = 0; j < jas.size(); j++) {
					JSONObject sub = jas.getJSONObject(j);

					WxMenuButton subb = new WxMenuButton();
					subb.setType(WxConstant.BUTTON_VIEW);
					subb.setName(sub.getString("name"));
					String url = sub.getString("url");
					if(url.indexOf("?")!=-1){
						url += "&oauth="+WxConstant.OAUTH_EKP_FLAG; 
					}else{
						url += "?oauth="+WxConstant.OAUTH_EKP_FLAG; 
					}
					
					String domainName = WeixinConfig.newInstance()
							.getWxDomain();
					if (StringUtils.isNotEmpty(domainName)) {
						url = domainName + url;
					} else {
						url = StringUtil.formatUrl(url);
					}
					
					subb.setUrl(url);
					button.getSubButtons().add(subb);
				}

			}

			wxApiService.menuCreate(WeixinConfig.newInstance().getWxAgentid(),
					wxMenu);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"/third/wx/menu/wxMenu.do?method=edit", false)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

}
