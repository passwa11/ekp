package com.landray.kmss.sys.webservice2.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.sunbor.web.tag.Page;

/**
 * WebService日志表数据访问接口
 * 
 * @author Jeff
 */
public interface ISysWebserviceLogDao extends IBaseDao {

	/**
	 * 查找注册的服务信息
	 */
	public SysWebserviceLog findServiceLog(String fdId) throws Exception;

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
