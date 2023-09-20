package com.landray.kmss.third.ding.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingDtaskXform;

public interface IThirdDingDtaskXformService extends IExtendDataService {

    public abstract List<ThirdDingDtaskXform> findByFdInstance(ThirdDingDinstanceXform fdInstance) throws Exception;

	/**
	 * @param dtask
	 * @param todo
	 * @return 发送待办通知
	 * @return
	 * @throws Exception
	 */
	public String addTask(ThirdDingDtaskXform dtask, SysNotifyTodo todo,
			Long agentId) throws Exception;
}
