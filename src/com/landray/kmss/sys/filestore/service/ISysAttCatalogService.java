package com.landray.kmss.sys.filestore.service;

import com.landray.kmss.common.service.IBaseService;

/**
 * 目录配置业务对象接口
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public interface ISysAttCatalogService extends IBaseService {

	/**
	 * 设置目录为当前目录，并执行update语句设置其他为非当前
	 * @param id
	 * @throws Exception
	 */
	public abstract void updateCurrent(String id) throws Exception;
	
	
}
