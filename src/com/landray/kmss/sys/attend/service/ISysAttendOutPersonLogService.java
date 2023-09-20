package com.landray.kmss.sys.attend.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendOutPersonLog;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-23
 */
public interface ISysAttendOutPersonLogService extends IBaseService {

	public List<SysAttendOutPersonLog> findOutPersonLogs(String phoneNum,
			Date availableTime) throws Exception;
}
