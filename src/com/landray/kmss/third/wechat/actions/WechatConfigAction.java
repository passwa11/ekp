package com.landray.kmss.third.wechat.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.wechat.service.IWechatConfigService;

/**
 * 新 类0 Action
 * 
 * @author
 * @version 1.0 2014-05-08
 */
public class WechatConfigAction extends ExtendAction {
	protected IWechatConfigService wechatConfigService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (wechatConfigService == null) {
            wechatConfigService = (IWechatConfigService) getBean("wechatConfigService");
        }
		return wechatConfigService;
	}
}
