package com.landray.kmss.sys.webservice2.dao.hibernate;

import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceMainDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;

/**
 * WebService信息表数据访问接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceMainDaoImp extends BaseDaoImp implements
		ISysWebserviceMainDao {

	/**
	 * 查找所有注册的服务信息
	 */
	@Override
    public List<SysWebserviceMain> findServiceList() {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		List<SysWebserviceMain> serviceList = HibernateWrapper.find(getHibernateTemplate(),
				"from SysWebserviceMain");
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());

		return serviceList;
	}

}
