package com.landray.kmss.sys.mportal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 移动公共门户页面业务对象接口
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public interface ISysMportalPageService extends IBaseService {

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

	/**
	 * 移动门户头配配置
	 */
	public void headerSetting(HttpServletRequest request) throws Exception;

	/**
	 * 获取头部类型
	 * 1:个人头像类型(默认)
	 * 2:搜索框类型
	 */
	public String getHeaderType() throws Exception;

	/**
	 * 更新、保存头部类型
	 */
	public void saveOrUpdateHeaderType(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取页面缓存所需数据
	 */
	public void initPageMessage(HttpServletRequest request, JSONObject jsonObject) throws Exception;

	/**
	 * 根据logo列表查询引用该logo列表的门户
	 * 
	 * @param logoList
	 * @return
	 * @throws Exception
	 */
	com.alibaba.fastjson.JSONArray getMportalInfoByLogo(List<String> logoList) throws Exception;

}
