package com.landray.kmss.sys.webservice2.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.sunbor.web.tag.Page;

/**
 * WebService 日志表业务对象接口
 * 
 * @author Jeff
 */
public interface ISysWebserviceLogService extends IBaseService {
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
