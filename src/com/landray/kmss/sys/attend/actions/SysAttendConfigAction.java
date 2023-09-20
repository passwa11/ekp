package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * @author linxiuxian
 *
 */
public class SysAttendConfigAction extends ExtendAction {
	private ISysAttendConfigService sysAttendConfigService;

	@Override
	protected ISysAttendConfigService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) getBean(
					"sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysAttendConfig.class.getName());
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		Page page = getServiceImp(request).findPage(hqlInfo);
		SysAttendConfig model = new SysAttendConfig();
		if (page.getList() != null && !page.getList().isEmpty()) {
			model = (SysAttendConfig) page.getList().get(0);
		}
		if (model.getFdSameDeviceLimit() == null) {
			model.setFdSameDeviceLimit(true);
		}
		if (model.getFdTrip() == null) {
			model.setFdTrip(true);
		}
		if (model.getFdShouldDayCfg() == null) {
			model.setFdShouldDayCfg(true);
		}
		UserOperHelper.logFind(model);// 添加日志信息
		rtnForm = getServiceImp(request).convertModelToForm(
				(IExtendForm) form, model, new RequestContext(request));

		// 判断kk,钉钉集成
		boolean isEnableKKConfig = AttendUtil.isEnableKKConfig();
		// boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
		request.setAttribute("isEnableKKConfig", isEnableKKConfig);
		// request.setAttribute("isEnableDingConfig", isEnableDingConfig);

		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			// 发送事件通知
			WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
					.publishEvent(new Event_Common(
							"extendApp"));
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}
