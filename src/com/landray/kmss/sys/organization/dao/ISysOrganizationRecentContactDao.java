package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 最近联系人数据访问接口
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public interface ISysOrganizationRecentContactDao extends IBaseDao {

	public void clearOldContacts(String personId) throws Exception;

	public void delContacts(String personId, String[] contactIds)
			throws Exception;

}
