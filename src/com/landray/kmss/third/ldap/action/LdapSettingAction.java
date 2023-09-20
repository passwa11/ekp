package com.landray.kmss.third.ldap.action;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.kmss.third.ldap.LdapUtil;
import com.landray.kmss.third.ldap.apache.ApacheLdapService;
import com.landray.kmss.third.ldap.authentication.LdapAuthenticationProcessingFilter;
import com.landray.kmss.third.ldap.base.BaseLdapService;
import com.landray.kmss.third.ldap.cluster.LdapMessage;
import com.landray.kmss.third.ldap.cluster.LdapMessageType;
import com.landray.kmss.third.ldap.form.LdapSettingForm;
import com.landray.kmss.third.ldap.oms.in.LdapOmsConfig;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class LdapSettingAction extends BaseAction {

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			LdapSettingForm settingForm = new LdapSettingForm();
			settingForm.setMap(LdapUtil.loadLdapConfig());
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			if (StringUtil.isNotNull(password)) {
				settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desEncrypt(password).replace("\r", ""));
			}
			request.setAttribute("ldapSettingForm", settingForm);
			
			if (UserOperHelper.allowLogOper("Action_Go_Edit", LdapConfig.class.getName())) { 
				UserOperHelper.setModelNameAndModelDesc(LdapConfig.class.getName(), ResourceUtil.getString("third-ldap:ldap.system.setting")); 
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}
	
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			LdapSettingForm settingForm = new LdapSettingForm();
			Map map = LdapUtil.loadLdapConfig();
			map.putAll(ResourceUtil.getKmssConfig("kmss.oms.in.ldap"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.integrate.ldap"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.authentication.ldap"));
			settingForm.setMap(map);
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desEncrypt(password).replace("\r", ""));
			request.setAttribute("ldapSettingForm", settingForm);
			
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			
			LdapSettingForm settingForm = (LdapSettingForm) form;
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desDecrypt(password));

			Map<String, String> dataMap = LdapUtil.saveLdapConfig(settingForm
					.getMap());
			
			LdapAuthenticationProcessingFilter ldapProcessingFilter = (LdapAuthenticationProcessingFilter)SpringBeanUtil.getBean("ldapProcessingFilter");
			ldapProcessingFilter.update(dataMap);
			
			MessageCenter.getInstance().sendToOther(
					new LdapMessage(
							LdapMessageType.LDAP_MESSAGE_DETAIL_CONFIG_UPDATE,
							dataMap));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			KmssMessage message = new KmssMessage("admin.config.success");
			messages.addMsg(message);
			KmssReturnPage.getInstance(request).addButton("button.back",
					"setting.do?method=edit", false).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("success");
		}
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			LdapSettingForm settingForm = (LdapSettingForm) form;
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desDecrypt(password));
			Map<String, String> dataMap = LdapUtil.saveLdapConfig(settingForm
					.getMap());
			
			LdapAuthenticationProcessingFilter ldapProcessingFilter = (LdapAuthenticationProcessingFilter) SpringBeanUtil
					.getBean("ldapProcessingFilter");
			ldapProcessingFilter.update(dataMap);

			MessageCenter.getInstance().sendToOther(
					new LdapMessage(
							LdapMessageType.LDAP_MESSAGE_DETAIL_CONFIG_UPDATE,
							dataMap));
			request.setAttribute("dataMap", dataMap);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("show");
		}
	}

	/**
	 * 删除LDAP同步时间戳
	 */
	public ActionForward deleteTimeStamp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		LdapOmsConfig ldapOmsConfig = new LdapOmsConfig();
		ldapOmsConfig.setLastUpdateTime(null);
		ldapOmsConfig.setLastDeleteTime(null);
		ldapOmsConfig.setLastSynchroOutTime(null);
		ldapOmsConfig.save();

		// 日志记录
		if (UserOperHelper.allowLogOper("sysAppConfigUpdate", "*")) {
			UserOperHelper.setEventType(ResourceUtil.getString("third-ldap:ldap.button.delTimeStamp"));
			UserOperHelper.setModelNameAndModelDesc(this.getClass().getName(),
					ResourceUtil.getString("third-ldap:ldap.system.setting"));
		}

		KmssMessage message = new KmssMessage("admin.config.success");
		messages.addMsg(message);

		KmssReturnPage.getInstance(request).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");

	}

	/**
	 * 更新LDAP同步时间戳
	 */
	public ActionForward updateTimeStamp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String time = request.getParameter("time");
		if (StringUtil.isNull(time)) {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("更新失败，必须在URL中设置time属性");
			return null;
		}
		LdapOmsConfig ldapOmsConfig = new LdapOmsConfig();
		ldapOmsConfig.getDataMap().put("lastUpdateTime", time);
		// ldapOmsConfig.setLastDeleteTime(null);
		ldapOmsConfig.save();

		// 日志记录
		if (UserOperHelper.allowLogOper("sysAppConfigUpdate", "*")) {
			UserOperHelper.setEventType(ResourceUtil.getString("third-ldap:ldap.button.updateTimeStamp"));
			UserOperHelper.setModelNameAndModelDesc(this.getClass().getName(),
					ResourceUtil.getString("third-ldap:ldap.system.setting"));
		}

		KmssMessage message = new KmssMessage("admin.config.success");
		messages.addMsg(message);

		KmssReturnPage.getInstance(request).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");

	}

	public ActionForward toValidatePass(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			LdapSettingForm settingForm = (LdapSettingForm) form;
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desDecrypt(password));
			Map<String, String> dataMap = LdapUtil.saveLdapConfig(settingForm
					.getMap());


			LdapAuthenticationProcessingFilter ldapProcessingFilter = (LdapAuthenticationProcessingFilter) SpringBeanUtil
					.getBean("ldapProcessingFilter");
			ldapProcessingFilter.update(dataMap);

			MessageCenter.getInstance().sendToOther(
					new LdapMessage(
							LdapMessageType.LDAP_MESSAGE_DETAIL_CONFIG_UPDATE,
							dataMap));
			// request.setAttribute("dataMap", dataMap);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("toValidatePass");
		}
	}

	public void testValidatePass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		username = URLDecoder.decode(username);
		password = URLDecoder.decode(password);

		String result = "true";
		String msg = null;
		try {
			BaseLdapService service = null;
			Map<String, String> config = new LdapDetailConfig().getDataMap();
			String url = config.get("kmss.ldap.config.url");
			if (url.startsWith("ldaps")) {
				service = new ApacheLdapService(true);
			} else {
				service = new LdapService();
			}
			boolean validateResult = service.validateUser(username, password);
			if (!validateResult) {
				result = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
			result = "false";
		}
		String out = "&result=" + result;
		if (StringUtil.isNotNull(msg)) {
			out += "&msg=" + msg;
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().print(out);

	}

	public ActionForward doMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			LdapSettingForm settingForm = (LdapSettingForm) form;
			String password = (String) settingForm
					.getValue("kmss.ldap.config.password");
			settingForm.setValue("kmss.ldap.config.password",
					LdapUtil.desDecrypt(password));
			Map<String, String> dataMap = LdapUtil.saveLdapConfig(settingForm
					.getMap());

			LdapAuthenticationProcessingFilter ldapProcessingFilter = (LdapAuthenticationProcessingFilter) SpringBeanUtil
					.getBean("ldapProcessingFilter");
			ldapProcessingFilter.update(dataMap);

			MessageCenter.getInstance().sendToOther(
					new LdapMessage(
							LdapMessageType.LDAP_MESSAGE_DETAIL_CONFIG_UPDATE,
							dataMap));
			BaseLdapService service = getService();
			String mappingResult = service.doMapping();
			request.setAttribute("mappingResult", mappingResult);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("mappingResult");
		}
	}

	private BaseLdapService getService() throws Exception {
		Map<String, String> config = new LdapDetailConfig().getDataMap();
		String url = config.get("kmss.ldap.config.url");
		if (StringUtil.isNull(url)) {
			return new LdapService();
		}
		if (url.startsWith("ldaps")) {
			return new ApacheLdapService();
		}
		return new LdapService();
	}

}
