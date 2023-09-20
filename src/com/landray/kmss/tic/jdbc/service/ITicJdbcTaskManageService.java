package com.landray.kmss.tic.jdbc.service;

import com.landray.kmss.common.service.IBaseService;

/**
 * 任务管理业务对象接口
 * 
 * @author 
 * @version 1.0 2013-07-24
 */
public interface ITicJdbcTaskManageService extends IBaseService {
	public void updateChgEnabled(String[] ids,boolean isEnable) throws Exception;
	
}
