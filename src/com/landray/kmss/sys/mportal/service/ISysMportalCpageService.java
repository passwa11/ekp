package com.landray.kmss.sys.mportal.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;

public interface ISysMportalCpageService extends IExtendDataService {

	/**
	 * 获取当前这个门户的卡片配置
	 * 
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
	 * 获取有效的门户配置js和css列表
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<String>> loadMporletJsAndCssList() throws Exception;
	
}
