package com.landray.kmss.sys.oms.notify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2006-12-26
 * 
 * 组织机构同步发布设置
 * 
 * @author 吴兵
 */
public class OrgSynchroNotifyTemplateEmptyAction extends ExtendAction {
	protected IBaseService orgSynchroNotifyTemplateEmptyService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (orgSynchroNotifyTemplateEmptyService == null) {
            orgSynchroNotifyTemplateEmptyService = (IBaseService) getBean("orgSynchroNotifyTemplateEmptyService");
        }
		return orgSynchroNotifyTemplateEmptyService;
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
			// 日志记录
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
					"com.landray.kmss.sys.oms.notify.model.OrgSynchroNotifyTemplateEmpty")) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("sys-oms-notify:table.orgSynchroNotifyTemplateEmpty"));
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
