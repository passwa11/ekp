package com.landray.kmss.sys.restservice.server.dao;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;

/**
 * RestService信息表数据访问接口
 * 
 * @author  
 */
public interface ISysRestserviceServerMainDao extends IBaseDao {

	/**
	 * 查找所有注册的服务信息
	 */
	public List<SysRestserviceServerMain> findServiceList();
}
