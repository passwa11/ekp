package com.landray.kmss.third.ding.notify.queue.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import net.sf.json.JSONArray;

/**
 * 待办推送失败接口
 * 
 * @author linxiuxian
 *
 */
public interface IThirdDingNotifyQueueErrorService extends IExtendDataService {

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

	public void updateResend(String[] ids) throws Exception;

	public String updateResendByCleaningTool(JSONArray data);
}
