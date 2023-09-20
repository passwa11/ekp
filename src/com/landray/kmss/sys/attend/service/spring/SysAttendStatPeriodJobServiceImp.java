package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.sys.attend.model.SysAttendStatPeriod;
import com.landray.kmss.sys.attend.service.ISysAttendStatPeriodJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatPeriodService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-03
 */
public class SysAttendStatPeriodJobServiceImp
		implements ISysAttendStatPeriodJobService {
	
	private ISysAttendStatPeriodService sysAttendStatPeriodService;

	public void setSysAttendStatPeriodService(
			ISysAttendStatPeriodService sysAttendStatPeriodService) {
		this.sysAttendStatPeriodService = sysAttendStatPeriodService;
	}

	@Override
    public void execute(SysQuartzJobContext jobContext) throws Exception {
		List<SysAttendStatPeriod> list = sysAttendStatPeriodService.findList("", "");
		if (!list.isEmpty()) {
			for (SysAttendStatPeriod statPeriod : list) {
				sysAttendStatPeriodService.delete(statPeriod);
			}
		}

	}

}
