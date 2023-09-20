package com.landray.kmss.third.ding.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IThirdDingGroupService extends IExtendDataService {

	/**
	 * <p>检查当前用户是否在群中，如果不在，则添加</p>
	 * @author 孙佳
	 */
	public String checkGroupByUserId(String groupID, String userId) throws Exception;

	/**
	 * <p>添加用户到群里</p>
	 * @author 孙佳
	 */
	public void addUser2Group(String groupID, String userId) throws Exception;

	/**
	 * <p>发送群消息</p>
	 * @author 孙佳
	 */
	public void sendGroupMessage(String groupID, String fdId, String modelName) throws Exception;

	/**
	 * <p>修改群名称</p>
	 * @param groupId
	 * @author 孙佳
	 */
	public void updateGroupName(String groupId, String fdId, String modelName) throws Exception;

	/**
	 * <p>创建群聊</p>
	 * @param userIds
	 * @param fdId
	 * @param modelName
	 * @author 孙佳
	 */
	public String addGroup(String userIds,String ownerId, String fdId, String modelName) throws Exception;

	/**
	 * <p>删除群聊</p>
	 * @param groupID
	 * @throws Exception
	 * @author 孙佳
	 */
	public void deleteByGroupId(String groupID) throws Exception;
	
	/**
	 * 发送群消息
	 * @param groupID
	 * @param fdId
	 * @param modelName
	 * @param message
	 * @throws Exception
	 */
	public void sendGroupMessage(String groupID, String fdId, String modelName,JSONObject message) throws Exception;
	
	/**
	 * 获取群会话信息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public String findGroup(String groupId) throws Exception;

	/**
	 * 删除群会话
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroup(String groupId) throws Exception;
	
	/**
	 * 创建群聊
	 * @param userIds
	 * @param fdId
	 * @param modelName
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String addGroup(String userIds,String ownerId,String fdId, String modelName,JSONObject message) throws Exception;
}
