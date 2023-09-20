package com.landray.kmss.km.imeeting.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议室信息业务对象接口
 */
public interface IKmImeetingResService extends IBaseService {

	/**
	 * 返回会议室占用列表
	 */
	Page listUse(RequestContext requestContext) throws Exception;
	
	public JSONArray placeList() throws Exception;
	
	public JSONArray getCateById(String cateId) throws Exception;
	
	public JSONArray getResById(String placeId,JSONArray cateJson) throws Exception;
	
	public Page findKmImeetingRes(RequestContext request)
			throws Exception;

	public void addSyncToBoen() throws Exception;

	public List<String> findConflictResInMain(HttpServletRequest request, Date start, Date end) throws Exception;

	public List<String> findConflictResInBook(HttpServletRequest request, Date start, Date end) throws Exception;

	public List<String> findConflictViceResInMain(HttpServletRequest request, Date start, Date end) throws Exception;

	public JSONObject isConflictRes(HttpServletRequest request, String resIds) throws Exception;

	public List findOccupiedResInMain(HttpServletRequest request, String resId, Date start, Date end) throws Exception;

	public JSONObject isConflictRes(RequestContext request, String resIds) throws Exception;

	public List findOccupiedResInBook(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception;

}
