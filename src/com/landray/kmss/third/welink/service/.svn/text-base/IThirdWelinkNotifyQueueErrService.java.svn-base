package com.landray.kmss.third.welink.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdWelinkNotifyQueueErrService extends IExtendDataService {

	public void updateRunErrorQueue(SysQuartzJobContext jobContext);

	public void clearNotifyQueueError(SysQuartzJobContext jobContext);

	public void updateResend(String[] ids) throws Exception;
}
