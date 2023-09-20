package com.landray.kmss.sys.webservice2.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;

/**
 * WebService 用户帐号管理业务对象接口
 * 
 * @author Jeff
 */
public interface ISysWebserviceUserService extends IBaseService {

	/**
	 * 根据用户名或者登录ID查找WebService用户
	 * 
	 * @param userName
	 *            用户名
	 * @param loginId
	 *            登录ID
	 * @return WebService用户实例
	 * @throws Exception
	 */
	public SysWebserviceUser findUser(String userName, String loginId)
			throws Exception;
}
