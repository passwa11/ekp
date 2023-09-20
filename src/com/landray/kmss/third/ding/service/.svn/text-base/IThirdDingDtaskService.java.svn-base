package com.landray.kmss.third.ding.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.third.ding.model.ThirdDingDtask;

public interface IThirdDingDtaskService extends IExtendDataService {

	/**
	 * @param fdInstance
	 * @return
	 * @throws Exception
	 */
	public abstract List<ThirdDingDtask> findByFdInstance(ThirdDingDinstance fdInstance) throws Exception;

	/**
	 * @param dtask
	 * @param todo
	 * @return 发送待办通知
	 * @return
	 * @throws Exception
	 */
	public String addTask(ThirdDingDtask dtask, SysNotifyTodo todo, Long agentId) throws Exception;

	/**
	 * @param fdId
	 * @throws Exception
	 *             前端发送待办
	 */
	public void updateSendTask(String fdId) throws Exception;

	public ThirdDingDtask findByTaskId(String instanceId, String taskId)
			throws Exception;

	public ThirdDingDtask findByNotifyId(String notifyId, String dingUserId)
			throws Exception;

}
