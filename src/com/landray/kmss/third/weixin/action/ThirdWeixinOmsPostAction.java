package com.landray.kmss.third.weixin.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.weixin.service.IThirdWeixinOmsPostService;

/**
 * 岗位同步 Action
 * 
 * @author chenl
 * @version 1.0 2018-03-27
 */
public class ThirdWeixinOmsPostAction extends ExtendAction {
	protected IThirdWeixinOmsPostService thirdWeixinOmsPostService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdWeixinOmsPostService == null) {
			thirdWeixinOmsPostService = (IThirdWeixinOmsPostService) getBean(
					"thirdWeixinOmsPostService");
		}
		return thirdWeixinOmsPostService;
	}
}
