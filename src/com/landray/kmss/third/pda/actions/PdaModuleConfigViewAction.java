package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.pda.service.IPdaModuleConfigViewService;


/**
 * 展示页面配置信息 Action
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigViewAction extends ExtendAction {
	protected IPdaModuleConfigViewService pdaModuleConfigViewService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(pdaModuleConfigViewService == null) {
            pdaModuleConfigViewService = (IPdaModuleConfigViewService)getBean("pdaModuleConfigViewService");
        }
		return pdaModuleConfigViewService;
	}
}

