package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.pda.service.IPdaTabViewLabelListService;

public class PdaTabViewLabelListAction extends ExtendAction {
	protected IPdaTabViewLabelListService pdaTabViewLabelListService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (pdaTabViewLabelListService == null) {
            pdaTabViewLabelListService = (IPdaTabViewLabelListService) getBean("pdaTabViewLabelListService");
        }
		return pdaTabViewLabelListService;
	}

}
