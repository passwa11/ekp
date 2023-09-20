package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrgRoleConfDao;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线配置数据访问接口实现
 */
public class SysOrgRoleConfDaoImp extends BaseDaoImp implements
		ISysOrgRoleConfDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysOrgRoleConf conf = (SysOrgRoleConf) modelObj;
		Date current = new Date();
		conf.setFdCreateTime(current);
		conf.setFdAlterTime(current);
		return super.add(modelObj);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysOrgRoleConf conf = (SysOrgRoleConf) modelObj;
		conf.setFdAlterTime(new Date());
		super.update(modelObj);
	}

}
