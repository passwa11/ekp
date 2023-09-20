/**
 * 
 */
package com.landray.kmss.sys.zone.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.zone.service.ISysZoneNavigationService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * @author 傅游翔
 * 
 */
public class SysZoneIndexAction extends BaseAction {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SysZoneIndexAction.class);

	private ISysZoneNavigationService sysZoneNavigationService;

	protected ISysZoneNavigationService getSysZoneNavigationService() {
		if (sysZoneNavigationService == null) {
			sysZoneNavigationService = (ISysZoneNavigationService) getBean("sysZoneNavigationService");
		}
		return sysZoneNavigationService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	protected ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

//	private ISysZonePageService sysZonePageService;
//
//	private ISysZonePageService getSysZonePageService() {
//		if (sysZonePageService == null) {
//			sysZonePageService = (ISysZonePageService) getBean("sysZonePageService");
//		}
//		return sysZonePageService;
//	}

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String parameter) throws Exception {
		String keyName = request.getParameter(parameter);
		if (keyName == null) {
			/* return "home"; */
			return "zoneHome";
		}
		return getLookupMapName(request, keyName, mapping);
	}

	public ActionForward img(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	/* 跳到员工个人黄页 */
	public ActionForward zoneHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-home", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId =  request.getParameter("userid");
			if(StringUtil.isNull(fdId)) {
				fdId = UserUtil.getUser().getFdId();
			}
			if (UserUtil.getUser(fdId).getFdIsAvailable()) {
				request.getRequestDispatcher(
						"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=view&fdId="
								+ fdId)
						.forward(request, response);
			} else {
				return mapping.findForward("e403");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-home", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		}
		return null;
	}
}
