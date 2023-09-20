package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.service.IThirdDingOmsPostService;

 
/**
 * 岗位 Action
 * 
 * @author chenl
 * @version 1.0 2018-02-06
 */
public class ThirdDingOmsPostAction extends ExtendAction {
	protected IThirdDingOmsPostService thirdDingOmsPostService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(thirdDingOmsPostService == null){
			thirdDingOmsPostService = (IThirdDingOmsPostService)getBean("thirdDingOmsPostService");
		}
		return thirdDingOmsPostService;
	}
}

