package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 签到记录日志
 * @author wj
 * @date 2021-10-19
 */
public interface ISysAttendSignLogService extends IExtendDataService {
    /**
     * 定期将签到记录转为历史表数据
     * @param context
     * @throws Exception
     */
    public void syncSignLogToHis(SysQuartzJobContext context) throws Exception;
}
