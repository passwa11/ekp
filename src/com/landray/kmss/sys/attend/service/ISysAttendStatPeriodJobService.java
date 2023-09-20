package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-03
 */
public interface ISysAttendStatPeriodJobService {

	/**
	 * 清空按日期区间的统计数据
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

}
