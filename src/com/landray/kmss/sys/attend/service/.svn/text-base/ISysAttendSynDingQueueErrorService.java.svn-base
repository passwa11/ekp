package com.landray.kmss.sys.attend.service;

import java.util.Date;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 钉钉考勤同步失败接口
 * 
 */
public interface ISysAttendSynDingQueueErrorService extends IBaseService {

	public String add(Date fdStartTime, Date fdEndTime, String userIds,
			String fdErrorMsg) throws Exception;

	/**
	 * 定时任务运行出错队列消息
	 * 
	 * @param jobContext
	 */
	public void updateRunErrorQueue(SysQuartzJobContext jobContext)
			throws Exception;

	/**
	 * 清理错误队列消息
	 * 
	 * @param jobContext
	 */
	public void clearNotifyQueueError(SysQuartzJobContext jobContext);
}
