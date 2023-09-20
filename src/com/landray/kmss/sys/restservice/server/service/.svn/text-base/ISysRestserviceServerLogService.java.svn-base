package com.landray.kmss.sys.restservice.server.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.sunbor.web.tag.Page;

/**
 * RestService 日志表业务对象接口
 * 
 * @author  
 */
public interface ISysRestserviceServerLogService extends IBaseService {
	/**
	 * 查找注册的服务信息
	 */
	public SysRestserviceServerLog findServiceLog(String fdId) throws Exception;

	/**
	 * 检测客户端的访问频率
	 */
	public int countAccessFrequency(String serviceBean, String userName)
			throws Exception;

	/**
	 * 查询超时预警分页
	 * 
	 * @param orderBy
	 * @param pageno
	 * @param rowsize
	 * @return
	 * @throws Exception
	 */
	public Page findTimeoutPage(String orderBy, int pageno, int rowsize)
			throws Exception;
}
