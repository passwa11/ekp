package com.landray.kmss.third.weixin.api;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.model.api.WxAgent;
import com.landray.kmss.third.weixin.model.api.WxDepart;
import com.landray.kmss.third.weixin.model.api.WxMenu;
import com.landray.kmss.third.weixin.model.api.WxMessage;
import com.landray.kmss.third.weixin.model.api.WxUser;

/**
 * 微信企业号API的Service
 * 
 * @author 唐有炜
 *
 */
public interface WxApiService {
	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	String getAccessToken() throws Exception;

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

	/**
	 * 获取应用信息
	 * 
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	String agentGet(String agentId) throws Exception;

	/**
	 * 获取应用列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<WxAgent> agentList() throws Exception;

	// ===========================
	// SSO
	// ===========================
	/**
	 * 获取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	JSONObject getLoginInfo(String authCode) throws Exception;

	/**
	 * 获取授权用户信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	JSONObject oauth2getUserInfo(String code)
			throws Exception;

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

	// =================================
	// 组织架构同步
	// =================================
	List<WxUser> userList(Long departId, Boolean fetchChild,
			Integer status) throws Exception;

	/**
	 * 获取用户信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	String userGet(String userid) throws Exception;

	/**
	 * 新增用户
	 * 
	 * @param wxuser
	 * @return
	 * @throws Exception
	 */
	JSONObject userCreate(WxUser wxuser) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	JSONObject userDelete(String userid) throws Exception;

	/**
	 * 更新用户
	 * 
	 * @param wxuser
	 * @return
	 * @throws Exception
	 */
	JSONObject userUpdate(WxUser wxuser) throws Exception;

	/**
	 * 获取部门
	 * 
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet() throws Exception;

	/**
	 * 获取子部门
	 * 
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	List<WxDepart> departGet(String departId) throws Exception;

	/**
	 * 更新部门
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	JSONObject departUpdate(WxDepart dept) throws Exception;

	/**
	 * 删除部门
	 * @param departId
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
}
