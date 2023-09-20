package com.landray.kmss.third.ekp.java.notify.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdEkpJavaNotifyQueErrService extends IExtendDataService {

	public void updateResend(String[] ids) throws Exception;

	public void clearNotifyQueueError(SysQuartzJobContext jobContext);

	public void updateRunErrorQueue(SysQuartzJobContext jobContext);

	public void synchroError(SysQuartzJobContext context);

	public void clear(String personIds) throws Exception;

	public void resend(String personIds) throws Exception;
}
