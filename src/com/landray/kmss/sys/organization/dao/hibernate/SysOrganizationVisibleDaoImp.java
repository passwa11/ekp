package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrganizationVisibleDao;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;

/**
 * 组织可见性数据访问接口实现
 * 
 * @author
 * @version 1.0 2015-06-16
 */
public class SysOrganizationVisibleDaoImp extends BaseDaoImp implements
		ISysOrganizationVisibleDao {
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysOrganizationVisible visible = (SysOrganizationVisible)modelObj;
		Date current = new Date();
		visible.setDocCreateTime(current);
		visible.setDocAlterTime(current);
		return super.add(modelObj);
	}
	
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysOrganizationVisible visible = (SysOrganizationVisible)modelObj;
		visible.setDocAlterTime(new Date());
		super.update(modelObj);
	}
	
	

}
