package com.landray.kmss.third.weixin.work.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdWeixinNotifyLogService extends IExtendDataService {

	public void clear(int days) throws Exception;
}
