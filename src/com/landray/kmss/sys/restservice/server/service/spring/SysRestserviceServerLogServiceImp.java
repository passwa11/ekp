package com.landray.kmss.sys.restservice.server.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerLogDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerLogService;
import com.sunbor.web.tag.Page;

/**
 * RestService日志表业务接口实现
 * 
 * @author  
 */
public class SysRestserviceServerLogServiceImp extends BaseServiceImp implements
		ISysRestserviceServerLogService {

	/**
	 * 查找注册的服务信息
	 */
	@Override
    public SysRestserviceServerLog findServiceLog(String fdId) throws Exception {
		ISysRestserviceServerLogDao dao = (ISysRestserviceServerLogDao) getBaseDao();

		return dao.findServiceLog(fdId);
	}

	/**
	 * 检测客户端的访问频率
	 */
	@Override
    public int countAccessFrequency(String serviceBean, String userName)
			throws Exception {
		ISysRestserviceServerLogDao dao = (ISysRestserviceServerLogDao) getBaseDao();
		return dao.countAccessFrequency(serviceBean, userName);
	}

	/**
	 * 查询超时预警分页
	 */
	@Override
    public Page findTimeoutPage(String orderBy, int pageno, int rowsize)
			throws Exception {
		ISysRestserviceServerLogDao dao = (ISysRestserviceServerLogDao) getBaseDao();
		return dao.findTimeoutPage(orderBy, pageno, rowsize);
	}

}
