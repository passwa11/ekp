package com.landray.kmss.third.pda.actions;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.pda.model.PdaHomePageConfig;
import com.landray.kmss.third.pda.service.IPdaHomePageConfigService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class PdaHomePageConfigAction extends ExtendAction {

	private IPdaHomePageConfigService pdaHomePageConfigService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (this.pdaHomePageConfigService == null) {
            this.pdaHomePageConfigService = (IPdaHomePageConfigService) getBean("pdaHomePageConfigService");
        }
		return pdaHomePageConfigService;
	}
	
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String modelName = getServiceImp(request).getModelName();
			if (UserOperHelper.allowLogOper("updateStatus", modelName)) {
				for (String id : ids) {
					PdaHomePageConfig config = (PdaHomePageConfig) getServiceImp(
							request).findByPrimaryKey(id);
					UserOperContentHelper
							.putUpdate(id, config.getFdName(), modelName)
							.putSimple("fdIsDefault", config.getFdIsDefault(),
									"1");
				}
				String notInHql = HQLUtil.buildLogicIN(
						"pdaHomePageConfig.fdId not", Arrays.asList(ids));
				List<PdaHomePageConfig> configList = getServiceImp(request)
						.findList(notInHql, "fdOrder");
				for (PdaHomePageConfig config2 : configList) {
					UserOperContentHelper.putUpdate(config2.getFdId(),
							config2.getFdName(), modelName)
							.putSimple("fdIsDefault", config2.getFdIsDefault(),
									"0");
				}
			}
			if (ids != null) {
                ((IPdaHomePageConfigService)getServiceImp(request)).updateStatus(ids);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
