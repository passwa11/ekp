package com.landray.kmss.third.weixin.work.api;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.model.ThirdWeixinContact;
import com.landray.kmss.third.weixin.work.model.api.*;

/**
 * 企业微信API的Service
 * 
 * @author 唐有炜
 *
 */
public interface WxworkApiService {

	void expireAllToken() throws Exception;

	void expireJsapiTicket(String corpId);

	void expireAllJsapiTicket();
	/**
	 * 获取access_token,组织架构、免登陆、应用菜单发布使用
	 * 
	 * @return
	 * @throws Exception
	 */
	String getAccessToken() throws Exception;

	/**
	 * 获取待办accessToken，待办、机器人节点使用
	 * 
	 * @return
	 */
	String getTodoAccessToken() throws Exception;

	/**
	 * 获取待阅accessToken
	 * 
	 * @return
	 */
	String getToReadAccessToken() throws Exception;

	/**
	 * 获取access_token,
	 * 不强制刷新access_token,AccessToken有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。
	 * 
	 * @param agentId
	 *            应用ID，组织架构agentId传0
	 * @return
	 * @throws Exception
	 */
	String getAccessTokenByAgentid(String agentId) throws Exception;
	
	/**
	 * 获取access_token,
	 * 不强制刷新access_token,AccessToken有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。
	 * 
	 * @param secret
	 *            企业微信基础应用secret,非自建和第三方应用
	 * @param agentId 应用标识，用于区分其他应用的token
	 * @return
	 * @throws Exception
	 */
	String getAccessBasicsToken(String secret,String agentId) throws Exception;

	/**
	 * 获取授权链接
	 * 
	 * @param redirectUri
	 * @param state
	 * @return
	 */
	String oauth2buildAuthorizationUrl(String redirectUri, String state);

	// ====================================================
	// JsapiSignature
	// ====================================================
	/**
	 * JsapiTicket
	 * 
	 * @return
	 * @throws Exception
	 */
	String getJsapiTicket(String corpId) throws Exception;

	/**
	 * JsapiSignature
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createJsapiSignature(String url, String corpId)
			throws Exception;
	
	/**
	 * JsapiSignature
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createJsapiSignature(String url,String agentid,String corpId)
			throws Exception;

	/**
	 * 应用agentConfig需要
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createAgentConfigJsapiSignature(String url,String agentid, String corpId)
			throws Exception;

	// ==================================================================
	// 组织架构
	// ==================================================================
	/**
	 * 获取人员列表
	 * 
	 * @param departId
	 *            部门ID
	 * @param fetchChild
	 *            是否获取子级
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	List<WxUser> userList(Long departId, Boolean fetchChild, Integer status)
			throws Exception;

	/**
	 * 人员删除
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	JSONObject userDelete(String userid) throws Exception;

	/**
	 * 人员修改
	 * 
	 * @param wxuser
	 * @return
	 * @throws Exception
	 */
	JSONObject userUpdate(WxUser wxuser) throws Exception;

	/**
	 * 获取用户信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	String userGet(String userid) throws Exception;

	/**
	 * 创建用户
	 * 
	 * @param wxuser
	 * @return
	 * @throws Exception
	 */
	JSONObject userCreate(WxUser wxuser) throws Exception;

	/**
	 * 获取部门列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet() throws Exception;

	/**
	 * 获取子部门列表
	 * 
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet(String departId) throws Exception;

	/**
	 * 部门更新
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	JSONObject departUpdate(WxDepart dept) throws Exception;

	/**
	 * 部门删除
	 * 
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	JSONObject departDelete(Long departId) throws Exception;

	/**
	 * 新增部门
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	JSONObject departCreate(WxDepart dept) throws Exception;

	// ==================================================
	// 待办、待阅、机器人节点
	// ==================================================
	JSONObject messageSend(WxMessage msg) throws Exception;

	// ==================================================
	// SSO
	// ==================================================
	JSONObject oauth2getUserInfo(String agentId, String code) throws Exception;

	// =================================================
	// 应用菜单
	// ==================================================
	/**
	 * 删除菜单
	 * 
	 * @param agentId
	 * @throws Exception
	 */
	void menuDelete(String agentId) throws Exception;

	/**
	 * 创建菜单
	 * 
	 * @param agentId
	 * @param menu
	 * @throws Exception
	 */
	void menuCreate(String agentId, WxMenu menu) throws Exception;

	// ====================================================
	String agentGet(String agentId) throws Exception;

	public JSONObject messageSendTaskcard(WxMessage msg, String notifyId)
			throws Exception;

	public JSONObject updateTaskcard(String agentId, String userid,
			String todoId, String corpId)
			throws Exception;

	public JSONObject messageSend(String agentId, JSONObject paramObj,String corpId)
			throws Exception;

	public JSONObject updateTaskcard(String agentId, JSONObject msgObj, String corpId)
			throws Exception;

	public JSONObject convert2Openid(String userId)
			throws Exception;

	public String getUserOpenId(String userId)
			throws Exception;

	public String getContactAccessToken() throws Exception;

	JSONObject listContact(String userid) throws Exception;

	JSONObject getContact(String external_userid) throws Exception;

	JSONObject getContact(String external_userid, String cursor) throws Exception;

	void listContactBatch(JSONArray userids, String cursor, JSONArray contactList_all) throws Exception;

	JSONObject listCorpTag(String group_id, String tag_id) throws Exception;

	Map<String,ThirdWeixinContact> listContactBatch(List<String> userIdList) throws Exception;

	public JSONObject listCorpTag(JSONArray groupIds) throws Exception;

	public JSONObject convert2Userid(String openId)
			throws Exception;

	public String getUserId(String openId) throws Exception;

	/*
	 *创建预约直播
	 */
	JSONObject createLiving(WxLiving living) throws Exception;

	/*
	 *修改预约直播
	 */
	JSONObject modifyLiving(WxLiving living) throws Exception;

	/*
	 *取消预约直播
	 */
	JSONObject cancelLiving(String livingid) throws Exception;

	/*
	 *删除直播回放
	 */
	JSONObject deleteReplayDataLiving(String livingid) throws Exception;

	/*
	 *获取微信观看直播凭证
	 */
	JSONObject getLivingCode(String livingid,String openid) throws Exception;


	/*
	 *获取成员直播ID列表
	 */
	JSONObject getUserAllLivingid(String userid) throws Exception;


	/*
	 *获取直播详情
	 */
	JSONObject getLivingInfo(String livingid) throws Exception;


	/*
	 *获取直播观看明细
	 */
	JSONObject getWatchStat(String livingid) throws Exception;


	/**
	 * 获取机器人信息
	 * @param robot_id 机器人ID
	 * @return
	 * 	  {
	 *         "robot_id": "wbxxxxxxxxxxxxxxxxxxxxxxxx",
	 *         "name": "机器人A",
	 *         "creator_userid": "zhangsan"
	 *     }
	 * @throws Exception
	 */
	JSONObject getRobotInfo(String robot_id) throws Exception;

	/**
	 * 获取客户详情
	 * @param external_userid 客户ID
	 * @return
	 * {
	 *         "external_userid":"woAJ2GCAAAXtWyujaWJHDDGi0mACHAAA",
	 *         "name":"李四",
	 *         "position":"Manager",
	 *         "avatar":"http://p.qlogo.cn/bizmail/IcsdgagqefergqerhewSdage/0",
	 *         "corp_name":"腾讯",
	 *         "corp_full_name":"腾讯科技有限公司",
	 *         "type":2,
	 *         "gender":1,
	 *         "unionid":"ozynqsulJFCZ2z1aYeS8h-nuasdAAA"
	 * }
	 * @throws Exception
	 */
	JSONObject getExternalContact(String external_userid) throws Exception;

	/**
	 * 获取群信息
	 * @param roomid
	 * @return
	 * {
	 *     "roomname": "蓦然回首",
	 *     "creator": "ZhangWenChao",
	 *     "room_create_time": 1592361604,
	 *     "notice": "",
	 *     "members": [
	 *         {
	 *             "memberid": "ZhangWenChao",
	 *             "jointime": 1592361605
	 *         },
	 *         {
	 *             "memberid": "xujinsheng",
	 *             "jointime": 1592377076
	 *         }
	 *     ],
	 *     "errcode": 0,
	 *     "errmsg": "ok"
	 * }
	 * @throws Exception
	 */
	JSONObject getGroupChat(String roomid) throws Exception;

	/**
	 *
	 * @param userid
	 * @return
	 * {
	 *     "errcode": 0,
	 *     "errmsg": "ok",
	 *     "userid": "zhangsan",
	 *     "name": "张三",
	 *     "department": [1, 2],
	 *     "order": [1, 2],
	 *     "position": "后台工程师",
	 *     "mobile": "13800000000",
	 *     "gender": "1",
	 *     "email": "zhangsan@gzdev.com",
	 *     "is_leader_in_dept": [1, 0],
	 *     "direct_leader":["lisi","wangwu"],
	 *     "avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
	 *     "thumb_avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/100",
	 *     "telephone": "020-123456",
	 *     "alias": "jackzhang",
	 *     "address": "广州市海珠区新港中路",
	 *     "open_userid": "xxxxxx",
	 *     "main_department": 1
	 * }
	 * @throws Exception
	 */
	JSONObject getUserInfo(String userid) throws Exception;


	/**
	 * 获取应用共享信息
	 *
	 * @param business_type 填0则为企业互联/局校互联，填1则表示上下游企业
	 * @param agentid       上级/上游企业应用agentid
	 * @param corpid        下级/下游企业corpid，若指定该参数则表示拉取该下级/下游企业的应用共享信息
	 * @return
	 * @throws Exception
	 */
	JSONArray listAppShareInfo(Integer business_type, String agentid, String corpid) throws Exception;

	/**
	 * 获取下游组织token
	 *
	 * @param corpAndAgentId 下游企业corpId+"#"+下游企业agentId
	 * @param business_type  填0则为企业互联/局校互联，填1则表示上下游企业
	 * @return
	 * @throws Exception
	 */
	String getCorpTokenString(String corpAndAgentId, Integer business_type)
			throws Exception;

	/**
	 * 获取下游组织token
	 *
	 * @param corpAndAgentId 下游企业corpId+"#"+下游企业agentId
	 * @param forceRefresh   是否更新token
	 * @param business_type  填0则为企业互联/局校互联，填1则表示上下游企业
	 * @return
	 * @throws Exception
	 */
	String getCorpTokenString(String corpAndAgentId, boolean forceRefresh, Integer business_type)
			throws Exception;

	JSONObject sendCorpMessage(String agentId, JSONObject paramObj)
			throws Exception;

	/**
	 * 获取应用共享信息（上下游信息）
	 * @return
	 * @throws Exception
	 */
	Map<String, CorpGroupAppShareInfo> getAppShareInfoMap() throws Exception;

	/**
	 * 重置上下游信息
	 * @throws Exception
	 */
	void resetAppShareInfoMap() throws Exception;

	void resetAppShareInfoMap(String agentIdStr) throws Exception;

	/**
	 * 获取上下游列表
	 *
	 * @param agentid 上级企业共享应用ID
	 * @return
	 * @throws Exception
	 */
	JSONArray getCorpChainList(String agentid) throws Exception;

	/**
	 * 获取所有下游列表
	 *
	 * @throws Exception
	 */
	JSONArray getCorpChainList() throws Exception;

	/**
	 * 获取上下游通讯录分组
	 *
	 * @param agentid  上级企业共享应用ID
	 * @param chain_id 下游企业ID
	 * @return
	 * @throws Exception
	 */
	JSONArray getCorpChainGroup(String agentid, String chain_id) throws Exception;

	/**
	 * 获取企业上下游通讯录分组下的企业详情列表
	 *
	 * @param agentid     上级企业共享应用ID
	 * @param chain_id    下游企业ID
	 * @param groupid     下游企业分组ID
	 * @param fetch_child 递归获取指定分组及其子分组的所有企业。1表示递归获取该分组下所有企业，0表示指定分组下所有企业，默认为0
	 * @return
	 * @throws Exception
	 */
	JSONArray getChainCorpinfoList(String agentid, String chain_id, String groupid, Integer fetch_child) throws Exception;

	/**
	 * 兼容下游组织
	 * @param corpAndAgentId 下游组织的corpId+"#"+agentId
	 * @param business_type  填0则为企业互联/局校互联，填1则表示上下游企业
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet(String corpAndAgentId, Integer business_type) throws Exception;

	/**
	 * 兼容下游组织
	 * @param departId 上级部门ID
	 * @param corpAndAgentId 下游组织的corpId+"#"+agentId
	 * @param business_type 填0则为企业互联/局校互联，填1则表示上下游企业
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet(String departId, String corpAndAgentId, Integer business_type) throws Exception;

	String userGet(String userid, String corpAndAgentId, Integer business_type) throws Exception;

	List<WxUser> userList(Long departId, Boolean fetchChild,
								 Integer status, String corpAndAgentId, Integer business_type) throws Exception;

	/**
	 * 获取下游应用详情
	 * @param corpId 下游应用的corpid
	 * @param agentId 下游应用的agentid
	 * @return
	 * @throws Exception
	 */
	JSONObject getAgent(String corpId, String agentId)
			throws Exception;

	JSONArray getAgentAllowUsers(String corpId, String agentId)
			throws Exception;

	String getCorpTokenStringByCorpId(String corpId, Integer business_type)
			throws Exception;

	JSONObject convert2Userid(String openId, String corpId, String agentId) throws Exception;

	JSONObject messageSend(WxMessage msg, String corpId) throws Exception;

	String getUserid(String mobile) throws Exception;
}
