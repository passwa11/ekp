package com.landray.kmss.sys.zone.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.sys.zone.util.SysZoneTemplate;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

public class SysZonePageTemplateAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (null == sysAppConfigService) {
			sysAppConfigService = (ISysAppConfigService) getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute(
					"pcTemplates",
					SysZoneTemplate.getTemplatesByType(SysZoneConfigUtil.TYPE_PC_KEY));
			request.setAttribute(
					"mobileTemplates",
					SysZoneTemplate.getTemplatesByType(SysZoneConfigUtil.TYPE_MOBILE_KEY));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward updateTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String pcJspPath = request.getParameter("pcJspPath");
			String mobileJspPath = request.getParameter("mobileJspPath");
			Map<String, String> map = new HashMap<String, String>();
			map.put(SysZoneConfigUtil.TYPE_PC_KEY, pcJspPath);
			map.put(SysZoneConfigUtil.TYPE_MOBILE_KEY, mobileJspPath);
			getSysAppConfigService().add(
					SysZonePageTemplateAction.class.getName(), map);
			SysZoneTemplate.setPageTemplateJsp(
					SysZoneConfigUtil.TYPE_PC_KEY, pcJspPath);
			SysZoneTemplate.setPageTemplateJsp(
					SysZoneConfigUtil.TYPE_MOBILE_KEY, mobileJspPath);
			JSONObject json = new JSONObject();
			json.accumulate("success", true);
			response.getWriter().append(json.toString());
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

}
