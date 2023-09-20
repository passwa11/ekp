package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-21
 */
public interface ISysAttendMainLogJobService {

	public void execute(SysQuartzJobContext jobContext) throws Exception;

}
