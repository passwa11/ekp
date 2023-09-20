package com.landray.kmss.third.weixin.work.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdWeixinNotifyQueErrService extends IExtendDataService {

	public void updateRunErrorQueue(SysQuartzJobContext jobContext);

	public void clearNotifyQueueError(SysQuartzJobContext jobContext);

	public void updateResend(String[] ids) throws Exception;

}
