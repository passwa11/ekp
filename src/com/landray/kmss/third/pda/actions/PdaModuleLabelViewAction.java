package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.pda.service.IPdaModuleLabelViewService;


/**
 * 展示页面标签信息 Action
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelViewAction extends ExtendAction {
	protected IPdaModuleLabelViewService pdaModuleLabelViewService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(pdaModuleLabelViewService == null) {
            pdaModuleLabelViewService = (IPdaModuleLabelViewService)getBean("pdaModuleLabelViewService");
        }
		return pdaModuleLabelViewService;
	}
}

