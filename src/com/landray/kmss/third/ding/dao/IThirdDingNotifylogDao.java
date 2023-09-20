package com.landray.kmss.third.ding.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IThirdDingNotifylogDao extends IBaseDao {

	public void clean(SysQuartzJobContext context, String logDays);
}
