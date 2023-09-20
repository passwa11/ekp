package com.landray.kmss.sys.restservice.server.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;

/**
 * RestService 管理接口
 * 
 * @author  
 */
public interface ISysRestserviceServerMainService extends IBaseService {

	/**
	 * 启动服务
	 * 
	 * @param model
	 *            RestService注册信息持久化对象
	 * @throws Exception
	 */
	public void startService(SysRestserviceServerMain model) throws Exception;

	/**
	 * 启动服务
	 * 
	 * @param fdId
	 *            RestService注册信息中的fdId主键标识
	 * @throws Exception
	 */
	public void startService(String fdId) throws Exception;

	/**
	 * 批量启动多个服务
	 * 
	 * @param fdIds
	 * @throws Exception
	 */
	public void startService(String[] fdIds) throws Exception;

	/**
	 * 停止服务
	 * 
	 * @param model
	 *            RestService注册信息持久化对象
	 */
	public void stopService(SysRestserviceServerMain model) throws Exception;

	/**
	 * 停止服务
	 * 
	 * @param fdId
	 *            RestService注册信息中的fdId主键标识
	 * @throws Exception
	 */
	public void stopService(String fdId) throws Exception;

	/**
	 * 批量停止多个服务
	 * 
	 * @param fdIds
	 * @throws Exception
	 */
	public void stopService(String[] fdIds) throws Exception;

	/**
	 * 根据服务标识查找服务
	 * 
	 * @param serviceBean
	 * @return
	 * @throws Exception
	 */
	public SysRestserviceServerMain findByServiceBean(String serviceBean) throws Exception;

	/**
	 * 根据URI查找服务
	 * @param uriPrefix
	 * @return
	 * @throws Exception 
	 */
	public SysRestserviceServerMain findByURI(String uriPrefix) throws Exception;
}
