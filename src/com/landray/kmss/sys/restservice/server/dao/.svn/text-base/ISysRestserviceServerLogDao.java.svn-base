package com.landray.kmss.sys.restservice.server.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.sunbor.web.tag.Page;

/**
 * RestService日志表数据访问接口
 * 
 * @author  
 */
public interface ISysRestserviceServerLogDao extends IBaseDao {

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
	 * 查询超时预警的分页
	 */
	public Page findTimeoutPage(String orderBy, int pageno, int rowsize)
			throws Exception;
}
