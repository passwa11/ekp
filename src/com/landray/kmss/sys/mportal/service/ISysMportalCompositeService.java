package com.landray.kmss.sys.mportal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;

import net.sf.json.JSONObject;

public interface ISysMportalCompositeService extends IExtendDataService {

    public abstract List<SysMportalComposite> findByFdPages(SysMportalCpage fdPages) throws Exception;
    
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
	 * 获取页面json数据
	 * @param fdId
	 * @return
	 */
	String getPagesJsonById(String fdId);

	List<Map<String, Object>> getListPagesById(String fdId);

	String getStringCompositeByPageId(String cpageId);

	List<SysMportalComposite> getListCompositeByPageId(String cpageId) throws Exception;

	void initCompositeMessage(HttpServletRequest request, JSONObject jsonObject, Boolean isPreview) throws Exception;

	List<Map<String, Object>> getListPagesById(String fdId, Boolean enabled);

	/**
	 * 根据logo列表查询引用该logo列表的门户
	 * 
	 * @param logoList
	 * @return
	 * @throws Exception
	 */
	JSONArray getMportalInfoByLogo(List<String> logoList) throws Exception;

}
