package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrgRoleLineDao;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线数据访问接口实现
 */
public class SysOrgRoleLineDaoImp extends BaseTreeDaoImp implements
		ISysOrgRoleLineDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysOrgRoleLine roleLine = (SysOrgRoleLine) modelObj;
		Date current = new Date();
		roleLine.setFdCreateTime(current);
		roleLine.setFdAlterTime(current);
		return super.add(modelObj);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysOrgRoleLine roleLine = (SysOrgRoleLine) modelObj;
		roleLine.setFdAlterTime(new Date());
		super.update(modelObj);
	}
}
