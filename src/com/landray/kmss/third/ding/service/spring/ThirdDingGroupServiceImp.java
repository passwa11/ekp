package com.landray.kmss.third.ding.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.ThirdDingGroup;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingGroupService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ThirdDingGroupServiceImp extends ExtendDataServiceImp implements IThirdDingGroupService {

	protected final Logger logger = LoggerFactory.getLogger(ThirdDingGroupServiceImp.class);

	@Override
	public String checkGroupByUserId(String groupID, String userId) throws Exception {
		return findGroupUser(groupID, userId);
	}

	@Override
	public void addUser2Group(String groupID, String userId) throws Exception {
		this.updateGroup(groupID,null,null,userId,null);
	}

	/**
	 * <p>
	 * 查询用户是否在群中
	 * </p>
	 * 
	 * @author 孙佳
	 * @throws Exception
	 */
	private String findGroupUser(String groupID, String userId) throws Exception {
		String dingUserId = getDingUserId(userId);
		String url = DingConstant.DING_PREFIX + "/chat/get?access_token=" + getAccessToken() + "&chatid=" + groupID
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject response = DingHttpClientUtil.httpGet(url, null, JSONObject.class);
		if ("0".equals(response.get("errcode").toString())) {
			JSONObject chatInfo = (JSONObject) response.get("chat_info");
			List<String> userList = (List<String>) chatInfo.get("useridlist");
			if (null == userList) {
				return null;
			}
			if (!userList.contains(dingUserId)) {
				updateGroup(groupID, null, null, dingUserId, null);
			}
		} else {
			logger.info("查询群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
			System.out.println("查询群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
		}
		return groupID;
	}

	/**
	 * <p>
	 * 修改群信息
	 * </p>
	 * 
	 * @param name           群名称
	 * @param owner          群主
	 * @param add_useridlist 添加成员
	 * @param del_useridlist 删除成员
	 * @throws Exception
	 * @author 孙佳
	 */
	private void updateGroup(String chatid, String name, String owner, String add_useridlist, String del_useridlist)
			throws Exception {
		JSONObject object = new JSONObject();
		if (StringUtil.isNotNull(chatid)) {
			object.put("chatid", chatid);
			if (StringUtil.isNotNull(name)) {
				object.put("name", name);
			}
			if (StringUtil.isNotNull(owner)) {
				object.put("owner", owner);
			}
			if (StringUtil.isNotNull(add_useridlist)) {
				object.put("add_useridlist", add_useridlist.split(","));
			}
			if (StringUtil.isNotNull(del_useridlist)) {
				object.put("del_useridlist", del_useridlist.split(","));
			}
			String url = DingConstant.DING_PREFIX + "/chat/update?access_token=" + getAccessToken()
					+ DingUtil.getDingAppKeyByEKPUserId("&", null);
			JSONObject response = DingHttpClientUtil.httpPost(url, object, null, JSONObject.class);
			if ("0".equals(response.get("errcode").toString())) {
                logger.debug("进群成功："+response);
			} else {
				logger.info("添加群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
			}
		}
	}

	private String getAccessToken() throws Exception {
		DingApiService dingApiService = DingUtils.getDingApiService();
		return dingApiService.getAccessToken();
	}

	private String getDingUserId(String userId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("omsRelationModel.fdEkpId = :fdEkpId");
		hqlInfo.setParameter("fdEkpId", userId);
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		String fdAppPkId = (String) omsRelationService.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdAppPkId)) {
			return fdAppPkId;
		} else {
			throw new Exception("用户不存在钉钉中！");
		}
	}

	@Override
	public void sendGroupMessage(String groupID, String fdId, String modelName) throws Exception {
		JSONObject object = new JSONObject();
		JSONObject oa = new JSONObject();
		JSONObject head = new JSONObject();
		JSONObject body = new JSONObject();
		JSONArray form = new JSONArray();
		JSONObject fromContent = new JSONObject();
		// 获取文档标题
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);
		String messageKey = ResourceUtil.getString(sysDictModel.getMessageKey()); // 模块名称
		String messageUrl = ModelUtil.getModelUrl(baseModel) + "&oauth=ekp"; // 查看url
		// 获取创建人、创建时间
		String descUser = getModelDocCreatorProperyValue(baseModel, "docCreator", null);
		String docCreateTime = getModelDocCreateTimeProperyValue(baseModel, "docCreateTime", Locale.CHINESE);

		// head
		head.put("bgcolor", "FFBBBBBB");
		head.put("text", messageKey);

		// 消息body部分
		body.put("title", docValue);
		// form
		/*
		 * fromContent = new JSONObject(); fromContent.put("key", "创建时间：");
		 * fromContent.put("value", docCreateTime); form.add(fromContent); fromContent =
		 * new JSONObject(); fromContent.put("key", "创建人："); fromContent.put("value",
		 * descUser); form.add(fromContent);
		 * 
		 * body.put("form", form);
		 */
		body.put("author", descUser);

		String sendUrl = DingUtil.getDingPcUrl(messageUrl);
		logger.debug("pc端 url:" + sendUrl);
		oa.put("message_url", StringUtil.formatUrl(messageUrl));
		oa.put("pc_message_url", StringUtil.formatUrl(sendUrl));
		oa.put("head", head);
		oa.put("body", body);

		object.put("chatid", groupID);
		object.put("msgtype", "oa");
		object.put("oa", oa);
		String url = DingConstant.DING_PREFIX + "/chat/send?access_token=" + getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);

		logger.debug("【钉聊发送消息】"+ JSON.toJSONString(object));
		JSONObject response = DingHttpClientUtil.httpPost(url, object, null, JSONObject.class);
		if (!"0".equals(response.get("errcode").toString())) {
			logger.error("发送群消息失败------" + response.get("errcode") + ":" + response.get("errmsg"));
		}
	}

	@Override
	public void updateGroupName(String groupId, String fdId, String modelName) throws Exception {
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);
		updateGroup(groupId, docValue, null, null, null);
	}

	private String getModelDocCreateTimeProperyValue(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel, property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			rtnStr = PropertyUtils.getProperty(baseModel, property).toString();
			if (StringUtil.isNotNull(rtnStr)) {
				rtnStr = rtnStr.substring(0, 16);
			}
		}
		return rtnStr;
	}

	private String getModelDocCreatorProperyValue(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel, property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) PropertyUtils.getProperty(baseModel, property);
			if (sysOrgPerson != null) {
				rtnStr = sysOrgPerson.getFdName();
			}
		}
		return rtnStr;
	}

	@Override
	public String addGroup(String userIds,String ownerId, String fdId, String modelName) throws Exception {
		String chatid = addGroup(userIds,ownerId, fdId, modelName, null);
		return chatid;
	}

	@Override
	public void deleteByGroupId(String groupID) throws Exception {
		String hql = "DELETE FROM ThirdDingGroup where fdGroupId = :fdGroupId";
		this.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdGroupId", groupID).executeUpdate();
		this.getBaseDao().getHibernateSession().clear();
	}

	@Override
	public void sendGroupMessage(String groupID, String fdId, String modelName, JSONObject message) throws Exception {
		JSONObject object = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONObject action_card = new JSONObject();
		JSONArray btn_list = new JSONArray();
		JSONObject btn_body = new JSONObject();

		// 获取文档标题
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String messageUrl = ModelUtil.getModelUrl(baseModel) + "&oauth=ekp"; // 查看url
		if (message == null) {
			logger.error("发送群消息失败------message为null");
			return;
		}
		String msgType = message.containsKey("msgType") ? message.getString("msgType") : "";
		String btnTitle = message.containsKey("btnTitle") ? message.getString("btnTitle") : "";
		String title = message.containsKey("title") ? message.getString("title") : "";
		String markdown = message.containsKey("markdown") ? message.getString("markdown") : "";
		if ("action_card".equals(msgType)) {
			// 消息body部分
			btn_body.put("title", btnTitle);
			btn_body.put("action_url", StringUtil.formatUrl(messageUrl));

			action_card.put("title", title);
			action_card.put("markdown", markdown);
			action_card.put("btn_orientation", "1");

			btn_list.add(btn_body);
			action_card.put("btn_json_list", btn_list);

			object.put("chatid", groupID);
			msg.put("msgtype", msgType);
			msg.put("action_card", action_card);
			object.put("msg", msg);
		}
		String url = DingConstant.DING_PREFIX + "/chat/send?access_token=" + getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject response = DingHttpClientUtil.httpPost(url, object, null, JSONObject.class);
		if (!"0".equals(response.get("errcode").toString())) {
			logger.error("发送群消息失败------" + response.get("errcode") + ":" + response.get("errmsg"));
		}
	}

	@Override
	public String findGroup(String groupId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/chat/get?access_token=" + getAccessToken() + "&chatid=" + groupId;
		JSONObject response = DingHttpClientUtil.httpGet(url, null, JSONObject.class);
		if ("0".equals(response.get("errcode").toString())) {
			return response.getString("chat_info");
		} else {
			logger.info("查询群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
			System.out.println("查询群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
		}
		return null;
	}

	@Override
	public void deleteGroup(String groupId) throws Exception {
		String chatInfo = this.findGroup(groupId);
		if (StringUtil.isNotNull(chatInfo)) {
			JSONObject chatInfoObj = JSONObject.fromObject(chatInfo);
			List<String> userList = (List<String>) chatInfoObj.get("useridlist");
			if (null == userList) {
				return;
			}
			String del_useridlist = "";
			Iterator<String> userIter = userList.iterator();
			while (userIter.hasNext()) {
				String userId = userIter.next();
				del_useridlist += userId + ",";
			}
			if (StringUtil.isNotNull(del_useridlist)) {
				del_useridlist = del_useridlist.substring(0,del_useridlist.length() - 1);
			}
			updateGroup(groupId, null, null, null, del_useridlist);
		}
		this.deleteByGroupId(groupId);
	}

	@Override
    public String addGroup(String userIds, String ownerId, String fdId, String modelName, JSONObject message) throws Exception {
		JSONObject object = new JSONObject();
		String chatid = null;
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);
		Boolean sendMsg = true;
		if (message != null) {
			if (message.containsKey("name")) {
				docValue = (String) message.get("name");
			}
			if (message.containsKey("sendMsg")) {
				sendMsg = message.getBoolean("sendMsg");
			}
		}
		// 群聊名称限制20个字符
		if (docValue.length() > 20) {
			docValue = docValue.substring(0, 15) + "...";
		}
		object.put("name", docValue);
		object.put("owner", StringUtil.isNotNull(ownerId)?ownerId:getDingUserId(UserUtil.getUser().getFdId()));
		object.put("useridlist", userIds.split(","));
		object.put("showHistoryType", 1);// 新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录），0代表否，1代表是，不传默认为否
		String url = DingConstant.DING_PREFIX + "/chat/create?access_token=" + getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject response = DingHttpClientUtil.httpPost(url, object, null, JSONObject.class);
		if ("0".equals(response.get("errcode").toString())) {
			chatid = response.get("chatid").toString();
			ThirdDingGroup modelObj = new ThirdDingGroup();
			modelObj.setFdGroupId(chatid);
			modelObj.setFdModelId(fdId);
			modelObj.setFdModelName(modelName);
			if (UserOperHelper.allowLogOper("addGroup", getModelName())) {
				UserOperContentHelper.putAdd(modelObj, "fdGroupId", "fdModelId", "fdModelName");
			}
			this.add(modelObj);
			if (sendMsg) {
				// 发送消息
				sendGroupMessage(chatid, fdId, modelName);
			}
		} else {
			logger.error("创建群聊失败------" + response.get("errcode") + ":" + response.get("errmsg"));
			System.out.println(response.get("errcode") + ":" + response.get("errmsg"));
		}
		return chatid;
	}
}
