package com.landray.kmss.third.ekp.java.notify.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdEkpJavaNotifyLogService extends IExtendDataService {

	public void clear(int days) throws Exception;
}
