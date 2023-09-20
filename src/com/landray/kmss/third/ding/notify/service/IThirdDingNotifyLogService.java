package com.landray.kmss.third.ding.notify.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdDingNotifyLogService extends IExtendDataService {

	public void clear(int days) throws Exception;
}
