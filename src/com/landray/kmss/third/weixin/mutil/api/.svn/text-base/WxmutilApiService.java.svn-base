package com.landray.kmss.third.weixin.mutil.api;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.mutil.model.api.WxDepart;
import com.landray.kmss.third.weixin.mutil.model.api.WxMenu;
import com.landray.kmss.third.weixin.mutil.model.api.WxMessage;
import com.landray.kmss.third.weixin.mutil.model.api.WxUser;

/**
 * 企业微信API的Service
 * 
 * @author 唐有炜
 *
 */
public interface WxmutilApiService {

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
	String getJsapiTicket() throws Exception;

	/**
	 * JsapiSignature
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createJsapiSignature(String url)
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

	public JSONObject convert2Openid(String userId)
			throws Exception;

}
