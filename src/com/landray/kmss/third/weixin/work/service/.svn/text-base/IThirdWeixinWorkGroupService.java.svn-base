package com.landray.kmss.third.weixin.work.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdWeixinWorkGroupService extends IExtendDataService {

	/**
	 * <p>
	 * 检查当前用户是否在群中，如果不在，则添加
	 * </p>
	 * 
	 * @author 陈火旺
	 */
	public String checkGroupByUserId(String groupID, String userId)
			throws Exception;

	/**
	 * <p>
	 * 发送群消息
	 * </p>
	 * 
	 * @author 陈火旺
	 */
	public void sendGroupMessage(String groupID, String fdId, String modelName)
			throws Exception;

	/**
	 * <p>
	 * 修改群名称
	 * </p>
	 * 
	 * @param groupId
	 * @author 陈火旺
	 */
	public void updateGroupName(String groupId, String fdId, String modelName)
			throws Exception;

	/**
	 * <p>
	 * 创建群聊
	 * </p>
	 * 
	 * @param userIds 群组成员ID
	 * @param fdId  主文档fdId
	 * @param modelName  主文档modelName
	 * @author 陈火旺
	 */
	public String addGroup(String userIds, String fdId, String modelName)
			throws Exception;

	/**
	 * <p>
	 * 删除群聊
	 * </p>
	 * 
	 * @param groupID
	 * @throws Exception
	 * @author 陈火旺
	 */
	public void deleteByGroupId(String groupID) throws Exception;
}
