package com.landray.kmss.sys.portal.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;

public interface ISysPortalMapTplService extends IExtendDataService {

	public abstract List<SysPortalMapTpl> findByFdNav(SysPortalMapTpl fdNav)
			throws Exception;

	/**
	 * 根据模板封装自定义数据
	 * 
	 * @param tpl
	 * @return
	 * @throws Exception
	 */
	public JSONArray getCustomDatasByTpl(SysPortalMapTpl tpl) throws Exception;
	
	
	/**
	 * 根据模板封装引用导航数据
	 * @param tpl
	 * @return
	 * @throws Exception
	 */
	public JSONArray getDatasByTpl(SysPortalMapTpl tpl) throws Exception;
}
