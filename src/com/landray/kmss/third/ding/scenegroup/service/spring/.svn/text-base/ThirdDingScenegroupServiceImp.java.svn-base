package com.landray.kmss.third.ding.scenegroup.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberGetResponse;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.mobile.ding.interfaces.IDingScenegroupService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupMappService;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupModuleService;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupService;
import com.landray.kmss.third.ding.scenegroup.util.DingScenegroupApiUtil;
import com.landray.kmss.util.UserUtil;

public class ThirdDingScenegroupServiceImp extends ExtendDataServiceImp
		implements IThirdDingScenegroupService, IDingScenegroupService {


	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingScenegroupServiceImp.class);

	private IThirdDingScenegroupModuleService thirdDingScenegroupModuleService;

	private IThirdDingScenegroupMappService thirdDingScenegroupMappService;


	@Override
    public boolean addScenegroup(String groupModuleKey, String title,
                                 String ownerPersonId, List<String> personIds, String modelName,
                                 String modelId, String fdKey) throws Exception {
		List<String> ownerPersonIdList = new ArrayList<String>();
		ownerPersonIdList.add(ownerPersonId);
		return addScenegroup(groupModuleKey, title,
				DingScenegroupApiUtil.buildUserIds(ownerPersonIdList),
				DingScenegroupApiUtil.buildUserIds(personIds), modelName,
				modelId, fdKey);
	}

	@Override
    public boolean addScenegroup(String groupModuleKey, String title,
                                 String ownerUserId, String userIds, String modelName,
                                 String modelId, String fdKey)
			throws Exception {
		ThirdDingScenegroupModule thirdDingScenegroupModule = thirdDingScenegroupModuleService
				.findByKey(groupModuleKey);
		if (thirdDingScenegroupModule == null) {
			throw new Exception("找不到对应的群组模板，" + groupModuleKey);
		}
		ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
				.findByModel(modelName, modelId, fdKey);
		if (mapp != null) {
			throw new Exception("已经存在这样的群组，modelName:" + modelName + "，modelId:"
					+ modelId + "，fdKey:" + fdKey);
		}
		try {
			OapiImChatScenegroupCreateResponse rsp = DingScenegroupApiUtil
					.createScenegroup(thirdDingScenegroupModule.getFdModuleId(),
							title, ownerUserId, userIds);
			if (rsp.getErrcode() == 0) {
				String openConversationId = rsp.getResult()
						.getOpenConversationId();
				String chatId = rsp.getResult().getChatId();
				ThirdDingScenegroupMapp mapping = new ThirdDingScenegroupMapp();
				mapping.setDocCreator(UserUtil.getUser());
				mapping.setFdChatId(chatId);
				mapping.setFdKey(fdKey);
				mapping.setFdModelName(modelName);
				mapping.setFdModelId(modelId);
				mapping.setFdName(title);
				mapping.setFdSceneGroupId(openConversationId);
				mapping.setFdStatus("1");
				mapping.setFdModule(thirdDingScenegroupModule);

				thirdDingScenegroupMappService.add(mapping);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return false;
	}

	@Override
    public boolean addScenegroupMember(List<String> personIds, String modelName,
                                       String modelId, String fdKey) throws Exception {
		return addScenegroupMember(
				DingScenegroupApiUtil.buildUserIds(personIds), modelName,
				modelId, fdKey);
	}

	@Override
    public boolean addScenegroupMember(String userIds, String modelName,
                                       String modelId, String fdKey)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			return DingScenegroupApiUtil.scenegroupAddMember(
					mapp.getFdSceneGroupId(),
					userIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public boolean deleteScenegroupMember(List<String> personIds,
                                          String modelName,
                                          String modelId, String fdKey) throws Exception {
		return deleteScenegroupMember(
				DingScenegroupApiUtil.buildUserIds(personIds), modelName,
				modelId, fdKey);
	}

	@Override
    public boolean deleteScenegroupMember(String userIds, String modelName,
                                          String modelId, String fdKey)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			return DingScenegroupApiUtil.scenegroupDeleteMember(
					mapp.getFdSceneGroupId(),
					userIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public void sendGroupTextMsg(String modelName,
                                 String modelId, String fdKey, String content)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			DingScenegroupApiUtil.sendGroupTextMsg(mapp, content);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public void sendGroupLinkMsg(String modelName,
                                 String modelId, String fdKey, String title, String text,
                                 String messageUrl)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			DingScenegroupApiUtil.sendGroupLinkMsg(mapp, messageUrl, title,
					text);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public void sendGroupActioncardMsg(String modelName,
                                       String modelId, String fdKey, String title, String text,
                                       JSONArray btnsArray, String btnOrientation)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			DingScenegroupApiUtil.sendGroupActionCardMsg(mapp, title, text,
					btnsArray, btnOrientation);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public void sendGroupActionCardMsg(String modelName,
                                       String modelId, String fdKey, String title, String text,
                                       String singleTitle,
                                       String singleURL)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			DingScenegroupApiUtil.sendGroupActionCardMsg(mapp, title, text,
					singleTitle, singleURL);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
    public void sendGroupMarkdownMsg(String modelName,
                                     String modelId, String fdKey, String title, String text)
			throws Exception {
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			DingScenegroupApiUtil.sendGroupMarkdownMsg(mapp, title, text);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
    public List<String> getScenegroupMembers(String modelName,
                                             String modelId, String fdKey) throws Exception {
		List<String> memberUserIds = new ArrayList<>();
		try {
			ThirdDingScenegroupMapp mapp = thirdDingScenegroupMappService
					.findByModel(modelName, modelId, fdKey);
			if (mapp == null) {
				throw new Exception(
						"找不到对应的群组，modelName:" + modelName + "，modelId:"
								+ modelId + "，fdKey:" + fdKey);
			}
			OapiImChatScenegroupMemberGetResponse rsp = DingScenegroupApiUtil.getScenegroupMembers(mapp.getFdSceneGroupId());
			if(logger.isDebugEnabled()) {
				logger.debug(rsp.getBody());
			}
			if (rsp.getErrcode() == 0) {
				memberUserIds = rsp.getResult().getMemberUserIds();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return memberUserIds;
	}
	
	/**
	 * 删除所有成员（删除群）
	 * @param modelName
	 * @param modelId
	 * @param fdKey
	 * @return
	 * @throws Exception
	 */
	@Override
    public boolean deleteScenegroup(String modelName,
                                    String modelId, String fdKey) throws Exception {
		List<String> dingIds = getScenegroupMembers(modelName,modelId,fdKey);
		if(dingIds!=null && !dingIds.isEmpty()) {
			String userIds = "";
			for (String dingId : dingIds) {
				userIds += dingId + ",";
			}
			if (userIds.length() > 0) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			return this.deleteScenegroupMember(userIds, modelName, modelId, fdKey);
		}
		return false;
	}

	public IThirdDingScenegroupModuleService
			getThirdDingScenegroupModuleService() {
		return thirdDingScenegroupModuleService;
	}

	public void setThirdDingScenegroupModuleService(
			IThirdDingScenegroupModuleService thirdDingScenegroupModuleService) {
		this.thirdDingScenegroupModuleService = thirdDingScenegroupModuleService;
	}

	public IThirdDingScenegroupMappService getThirdDingScenegroupMappService() {
		return thirdDingScenegroupMappService;
	}

	public void setThirdDingScenegroupMappService(
			IThirdDingScenegroupMappService thirdDingScenegroupMappService) {
		this.thirdDingScenegroupMappService = thirdDingScenegroupMappService;
	}
}
