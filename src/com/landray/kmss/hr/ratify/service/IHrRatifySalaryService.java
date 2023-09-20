package com.landray.kmss.hr.ratify.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IHrRatifySalaryService extends IHrRatifyMainService {
    /**
     * 设置员工薪资定时任务
     * @param context
     */
    public void setSalaryJob(SysQuartzJobContext context);
}
