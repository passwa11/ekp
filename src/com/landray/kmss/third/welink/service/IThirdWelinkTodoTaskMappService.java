package com.landray.kmss.third.welink.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdWelinkTodoTaskMappService extends IExtendDataService {

	public void addMapp(String todoId, String title, String personId,
			String taskId, String welinkUserId) throws Exception;

	public void deleteByTaskId(String taskId) throws Exception;

}
