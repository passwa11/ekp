package com.landray.kmss.sys.organization.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;

/**
 * 职级配置数据访问接口
 * 
 * @author 
 * @version 1.0 2015-07-23
 */
public interface ISysOrganizationStaffingLevelDao extends IBaseDao {
	
	/**
	 * 更新其他通用模板为非默认模板
	 * 
	 * @param template
	 */
	public void clearDefaultFlag(SysOrganizationStaffingLevel sysOrganizationStaffingLevel);

}
