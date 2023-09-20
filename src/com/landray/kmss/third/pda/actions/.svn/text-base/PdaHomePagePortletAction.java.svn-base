package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.pda.service.IPdaHomePagePortletService;

public class PdaHomePagePortletAction extends ExtendAction {

	private IPdaHomePagePortletService pdaHomePagePortletService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (this.pdaHomePagePortletService == null) {
            this.pdaHomePagePortletService = (IPdaHomePagePortletService) getBean("pdaHomePagePortletService");
        }
		return pdaHomePagePortletService;
	}

}
