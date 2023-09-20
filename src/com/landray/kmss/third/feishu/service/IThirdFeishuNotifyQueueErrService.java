package com.landray.kmss.third.feishu.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdFeishuNotifyQueueErrService extends IExtendDataService {

	public void updateResend(String[] ids) throws Exception;

	public void updateRunErrorQueue(SysQuartzJobContext jobContext);

	public void clearNotifyQueueError(SysQuartzJobContext jobContext);

}
