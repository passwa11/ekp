package com.landray.kmss.sys.fans.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.landray.kmss.common.service.IBaseService;
import com.sunbor.web.tag.Page;

/**
 * 关注机制业务对象接口
 * 
 * @author 
 * @version 1.0 2015-02-13
 */
public interface ISysFansMainService extends IBaseService {
	
	/**
	 * 获得关注数/粉丝数
	 * @param userId
	 * @param type :0代表关注数；1代表粉丝数
	 * @return
	 * @throws Exception
	 */
	public Integer getFollowCount(String userId, Integer type, String fdModelName) throws Exception ;
	
	/**
	 * 校验是否已关注
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Boolean checkFollow(String userId,String fdModelName) throws Exception ;
	
	/**
	 * 关注或取消关注
	 * @param requestInfo
	 * @return 
	 * @throws Exception
	 */
	public String updateFollow(HttpServletRequest requestInfo) throws Exception;
	
	/**
	 * 用户关系
	 * @param orgId1
	 * @param orgId2
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public Integer getRelation(String orgId1, String orgId2) throws Exception;
	
	public Page findFollowPage(int pageno, int rowsize, String orderby, String id, String type, String fansModelName) throws Exception;
	
	public JSONArray getRelationByIds(String personIdsStr) throws Exception ;
	
	/**
	 * 获取关注、粉丝总数 
	 * @param id
	 * @param type 获取粉丝数：fans;获取关注数attention
	 * @return
	 * @throws Exception
	 */
	public int getFollowTotal(String id, String type) throws Exception;
	
	public String addFans(String userId,
			String fdModelName,Integer fdUserType) throws Exception ;
	
	public String addFansByIds(String orginId,String targetId,
			String fdModelName,Integer fdUserType) throws Exception ;
	
	public Boolean isFollowPerson(String originId, 
			String targetId, String fdModelName) throws Exception;
}
