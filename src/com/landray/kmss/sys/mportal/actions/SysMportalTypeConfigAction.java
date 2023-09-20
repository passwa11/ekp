package com.landray.kmss.sys.mportal.actions;

import java.util.Comparator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mportal.model.SysMportalLogoInfo;
import com.landray.kmss.sys.mportal.model.SysMportalTypeConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * sys配置 Action
 * 
 * @author
 */
public class SysMportalTypeConfigAction extends SysAppConfigAction {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysAppConfigService sysAppConfigService;

	@Override
	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
            sysAppConfigService = (ISysAppConfigService) getBean(
                    "sysAppConfigService");
        }
		return sysAppConfigService;
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();

		String modelName = request.getParameter("modelName");
		if (StringUtil.isNull(modelName)) {
            throw new NoRecordException();
        }
		SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
		BaseAppConfig appConfig = (BaseAppConfig) Class.forName(modelName).newInstance();
		appConfigForm.setMap(appConfig.getDataMap());
		// 记录日志
		if (UserOperHelper.allowLogOper("Base_UrlParam", SysMportalTypeConfig.class.getName())) { 
			UserOperHelper.setModelNameAndModelDesc(SysMportalTypeConfig.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("select");
		}

	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String modelName = request.getParameter("modelName");
			String autoclose = request.getParameter("autoclose");
			if (StringUtil.isNull(modelName)) {
                throw new NoRecordException();
            }
			if (StringUtil.isNotNull(autoclose) && "false".equals(autoclose)) {
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			}
			SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
			BaseAppConfig appConfig = (BaseAppConfig) Class.forName(modelName).newInstance();
			appConfig.getDataMap().putAll(appConfigForm.getMap());
			appConfig.save();

			// getSysAppConfigService().add(modelName, appConfigForm.getMap());
			// 发送update事件
			WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
					.publishEvent(new Event_Common(modelName));
			
			// 记录日志
			if (UserOperHelper.allowLogOper("Service_Update", SysMportalTypeConfig.class.getName())) { 
				UserOperHelper.setEventType(ResourceUtil.getString("sysMportal.msg.mportalTypeConfiguration", "sys-mportal"));
				UserOperHelper.setModelNameAndModelDesc(SysMportalTypeConfig.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
			}
			
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

}

class ModuleComparator implements Comparator<Map<String, String>> {

	public static final Comparator<Map<String, String>> INSTANCE = new ModuleComparator();

	@Override
	public int compare(Map<String, String> s1, Map<String, String> s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		if (s1.containsKey("name") && s2.containsKey("name")) {
			return ChinesePinyinComparator.compare(s1.get("name"), s2.get("name"));
		}
		if (s1.containsKey("value") && s2.containsKey("value")) {
			return ChinesePinyinComparator.compare(s1.get("value"), s2.get("value"));
		}
		return 0;
	}
}
