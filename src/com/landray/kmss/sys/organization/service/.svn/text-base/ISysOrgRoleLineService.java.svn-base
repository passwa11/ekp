package com.landray.kmss.sys.organization.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线业务对象接口
 */
public interface ISysOrgRoleLineService extends IBaseService {
	/**
	 * 快速新建下级，注意：返回的第一行信息为是否成功
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> quickAdd(RequestContext requestInfo)
			throws Exception;

	/**
	 * 删除角色以及所有的子
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> deleteChildren(RequestContext requestInfo)
			throws Exception;
	/**
	 * 删除角色以及所有的子
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> deleteAllChildren(RequestContext requestInfo)
			throws Exception;

	/**
	 * 移动角色
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> move(RequestContext requestInfo)
			throws Exception;
}
