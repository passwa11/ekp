package com.landray.kmss.sys.organization.dao.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgDeptPersonRelationDao;

public class SysOrgDeptPersonRelationDaoImp extends BaseDaoImp implements
		ISysOrgDeptPersonRelationDao {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgDeptPersonRelationDaoImp.class);

	@Override
	public void delRelation(String personId) throws Exception {
		if (StringUtil.isNull(personId)) {
			logger.warn("personId不能为空");
			return;
		}
		super.getSession()
				.createQuery(
						"delete " + this.getModelName()
								+ " where fdPersonId=:personId")
				.setString(
						"personId", personId)
				.executeUpdate();
	}

}
