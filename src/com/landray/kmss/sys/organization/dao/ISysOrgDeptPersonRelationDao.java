package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.dao.IBaseDao;


public interface ISysOrgDeptPersonRelationDao extends IBaseDao {

	public void delRelation(String personId) throws Exception;

}
