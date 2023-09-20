package com.landray.kmss.third.pda.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

public interface IPdaHomePageConfigService extends IBaseService {
	public String getOwerPage(RequestContext requestContext) throws Exception;

	public String[] getAllPages(RequestContext requestContext) throws Exception;
	
	public void updateStatus(String[] ids) throws Exception;
}
