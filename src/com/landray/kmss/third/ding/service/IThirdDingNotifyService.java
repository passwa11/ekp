package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import net.sf.json.JSONArray;

public interface IThirdDingNotifyService extends IExtendDataService {
	public void synchroError(SysQuartzJobContext context);

	public void handle(boolean del) throws Exception;
	
	public void cleaningAllNotify() throws Exception ;

	public String updateCleaningNotify(String userId) throws Exception ;

	public String cleaningBytool(JSONArray data) throws Exception;
}
