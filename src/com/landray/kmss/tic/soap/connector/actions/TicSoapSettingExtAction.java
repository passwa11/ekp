package com.landray.kmss.tic.soap.connector.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingExtService;


/**
 * WEBSERVICE服务配置扩展 Action
 * 
 * @author 
 * @version 1.0 2012-08-06
 */
public class TicSoapSettingExtAction extends ExtendAction {
	protected ITicSoapSettingExtService TicSoapSettingExtService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(TicSoapSettingExtService == null) {
			TicSoapSettingExtService = (ITicSoapSettingExtService)getBean("ticSoapSettingExtService");
		}
		return TicSoapSettingExtService;
	}
}

