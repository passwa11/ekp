package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 转换回调使用的接口
 */
public interface IWPSCenterCallbackResult {

    /**
     * 回调处理方法
     * @param taskId 任务ID
     * @param status  状态
     */
    void doCallbackResult(String taskId, String status);

    /**
     * 如果转换已经分配，但是长时间没有回调，则将转换标记为失败
     * 而失败中，如果转换次数少于3次，则会重新分配转换
     */
    void cleanCallbackCache(SysQuartzJobContext context);
}
