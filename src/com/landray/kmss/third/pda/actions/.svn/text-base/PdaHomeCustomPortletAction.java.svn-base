package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.pda.service.IPdaHomeCustomPortletService;

public class PdaHomeCustomPortletAction extends ExtendAction {

	private IPdaHomeCustomPortletService pdaHomeCustomPortletService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (this.pdaHomeCustomPortletService == null) {
            this.pdaHomeCustomPortletService = (IPdaHomeCustomPortletService) getBean("pdaHomeCustomPortletService");
        }
		return pdaHomeCustomPortletService;
	}

}
