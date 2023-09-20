package com.landray.kmss.third.ding.notify.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;

public interface IThirdDingNotifyMessageService extends IExtendDataService {

	public String delete(String dingTaskId, String todoId) throws Exception;

	public ThirdDingNotifyMessage findByDingTaskId(String dingTaskId)
			throws Exception;
}
