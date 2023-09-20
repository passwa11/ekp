package com.landray.kmss.third.ekp.java.notify.queue.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 待办推送失败接口
 * 
 * @author linxiuxian
 *
 */
public interface IThirdEkpNotifyQueueErrorService extends IBaseService {

	public String add(Object todoContext, int opt, String errorMsg,
			String notifyId)
			throws Exception;

	/**
	 * 定时任务运行出错队列消息
	 * 
	 * @param jobContext
	 */
	public void updateRunErrorQueue(SysQuartzJobContext jobContext);

	/**
	 * 清理错误队列消息
	 * 
	 * @param jobContext
	 */
	public void clearNotifyQueueError(SysQuartzJobContext jobContext);
}
