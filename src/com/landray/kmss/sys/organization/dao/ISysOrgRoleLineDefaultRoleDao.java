package com.landray.kmss.sys.organization.dao;

import java.util.Collection;
import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.organization.forms.SysOrgRoleLineDefaultRoleForm;

/**
 * 创建日期 2008-十一月-27
 * 
 * @author 陈亮 角色是否默认数据访问接口
 */
public interface ISysOrgRoleLineDefaultRoleDao extends IBaseDao {
	public Collection<SysOrgRoleLineDefaultRoleForm> loadDefaultRoleForm(
			String confId) throws Exception;
	
	/**
	 * 查询所有机构ID
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List loadElementIds(String confId) throws Exception;
}
