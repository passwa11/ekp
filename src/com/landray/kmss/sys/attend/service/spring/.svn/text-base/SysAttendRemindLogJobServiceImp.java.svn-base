package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.sys.attend.model.SysAttendNotifyRemindLog;
import com.landray.kmss.sys.attend.service.ISysAttendNotifyRemindLogService;
import com.landray.kmss.sys.attend.service.ISysAttendRemindLogJobService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 清空上下班提醒日志
 * 
 * @author cuiwj
 * @version 1.0 2019-01-09
 */
public class SysAttendRemindLogJobServiceImp
		implements ISysAttendRemindLogJobService {

	private ISysAttendNotifyRemindLogService sysAttendNotifyRemindLogService;

	public void setSysAttendNotifyRemindLogService(
			ISysAttendNotifyRemindLogService sysAttendNotifyRemindLogService) {
		this.sysAttendNotifyRemindLogService = sysAttendNotifyRemindLogService;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		List<SysAttendNotifyRemindLog> list = sysAttendNotifyRemindLogService
				.findList("", "");
		if (!list.isEmpty()) {
			for (SysAttendNotifyRemindLog log : list) {
				sysAttendNotifyRemindLogService.delete(log);
			}
		}
	}

}
