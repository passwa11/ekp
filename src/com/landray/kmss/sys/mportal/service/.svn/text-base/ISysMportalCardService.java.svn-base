package com.landray.kmss.sys.mportal.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 文档类业务对象接口
 * 
 * @author 
 * @version 1.0 2015-09-14
 */
public interface ISysMportalCardService extends IBaseService {
	
	/**
	 * 部件选择页面获取推送的部件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONArray getPushPortlets(RequestContext request) throws Exception;
	
	/**
	 * 批量禁用
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateInvalidatedAll(String[] ids, RequestContext requestContext)
			throws Exception;
	
	/**
	 * 批量启用
	 * 
	 * @param ids
	 * @param requestContext
	 * @throws Exception
	 */
	public void updateValidatedAll(String[] ids, RequestContext requestContext)
			throws Exception;

	/**
	 * 根据卡片id获取卡片
	 * 
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCardById(RequestContext requestContext)
			throws Exception;
}
