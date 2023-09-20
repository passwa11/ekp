package com.landray.kmss.third.pda.service;

import com.landray.kmss.common.service.IBaseService;

/**
 * 模块配置表业务对象接口
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public interface IPdaModuleConfigMainService extends IBaseService {
	public void updateStatus(String[] ids, String status) throws Exception;
}
