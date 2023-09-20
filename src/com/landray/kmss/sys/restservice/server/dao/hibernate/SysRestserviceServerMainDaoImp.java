package com.landray.kmss.sys.restservice.server.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerMainDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;

/**
 * RestService信息表数据访问接口实现
 * 
 * @author  
 */
public class SysRestserviceServerMainDaoImp extends BaseDaoImp implements
		ISysRestserviceServerMainDao {

	/**
	 * 查找所有注册的服务信息
	 */
	@Override
	public List<SysRestserviceServerMain> findServiceList() {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		List<SysRestserviceServerMain> serviceList = (List<SysRestserviceServerMain>) getHibernateTemplate().find(
				"from SysRestserviceServerMain");
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());

		return serviceList;
	}

}
