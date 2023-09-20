package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdDingCallbackLogService extends IExtendDataService {
	
	public String saveOrUpdateCallbackAgain(String fdId);
	
	public void saveOrUpdateDingCallBack();

	public void clear(int days) throws Exception;
}
