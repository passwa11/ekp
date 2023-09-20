package com.landray.kmss.sys.webservice2.service;

import org.apache.cxf.Bus;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;

/**
 * WebService 管理接口
 * 
 * @author Jeff
 */
public interface ISysWebserviceMainService extends IBaseService {

	/**
	 * 启动服务
	 * 
	 * @param model
	 *            WebService注册信息持久化对象
	 * @throws Exception
	 */
	public void startService(SysWebserviceMain model) throws Exception;

	/**
	 * 启动服务
	 * 
	 * @param fdId
	 *            WebService注册信息中的fdId主键标识
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
	 * 加载并启动所有配置在plugin.xml中的WebService
	 */
	public void startAllServices();

	/**
	 * 停止服务
	 * 
	 * @param model
	 *            WebService注册信息持久化对象
	 */
	public void stopService(SysWebserviceMain model) throws Exception;

	/**
	 * 停止服务
	 * 
	 * @param fdId
	 *            WebService注册信息中的fdId主键标识
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
	public SysWebserviceMain findByServiceBean(String serviceBean)
			throws Exception;

	/**
	 * 设置CXF BUS
	 * 
	 * @param bus
	 */
	public void setBus(Bus bus);

	/**
	 * 生成Java客户端源码并打包
	 * 
	 * @param fdId
	 *            主键
	 * @param urlPrefix
	 *            URL的上下文路径
	 * @return 压缩文件的全名
	 * @throws Exception
	 */
	public String genClient(String fdId, String urlPrefix) throws Exception;

	/**
	 * 获取WebService的映射路径
	 */
	public String getUrlPattern();
}
