package com.landray.kmss.sys.remind.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.remind.model.SysRemindMainTask;

/**
 * 提醒任务
 * 
 * @author panyh
 * @date Jun 28, 2020
 */
public interface ISysRemindMainTaskService extends IBaseService {

	/**
	 * 执行任务
	 * 
	 * @param taskId
	 * @throws Exception
	 */
	public abstract void runTask(String taskId) throws Exception;

	/**
	 * 根据业务主文档获取提醒任务
	 * 
	 * @param fdModelId
	 * @param fdModelName
	 * @param fdKey
	 * @return
	 * @throws Exception
	 */
	public abstract List<SysRemindMainTask> getByModel(String fdModelId, String fdModelName, String fdKey)
			throws Exception;

}
