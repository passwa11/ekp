package com.landray.kmss.sys.organization.service;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.forms.SysOrgRoleConfForm;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线配置业务对象接口
 */
public interface ISysOrgRoleConfService extends IBaseService {
	public SysOrgRoleConfForm loadRepeatRoleForm(String id) throws Exception;
	
	/**
	 * 检测无效的机构
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List loadInvalidElement(String id) throws Exception;

	public void updateRepeatRoleForm(SysOrgRoleConfForm form) throws Exception;

	/**
	 * 置为无效
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void updateInvalidated(String id, RequestContext requestContext) throws Exception;

	/**
	 * 批量置为无效
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception;

	/**
	 * 复制角色线
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public void updateCopy(String fdId) throws Exception;
	
}
