package com.landray.kmss.sys.organization.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;

/**
 * 外部组织类型扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public interface ISysOrgElementExternalService extends IBaseService {

	/**
	 * 删除属性
	 * 
	 * @param propId
	 * @throws Exception
	 */
	void deleteProp(String propId) throws Exception;
	
    void addExternal(IBaseModel modelObj) throws Exception;

	/**
	 * 置为无效
	 * 
	 * @param id
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String id, RequestContext requestContext) throws Exception;

	/**
	 * 批量置为无效
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception;

	/**
	 * 修复扩展属性
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void repair(String[] ids, RequestContext requestContext) throws Exception;

}
