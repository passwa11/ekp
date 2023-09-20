package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdDingErrorService extends IExtendDataService {
	public void synchroError(SysQuartzJobContext context);
}
