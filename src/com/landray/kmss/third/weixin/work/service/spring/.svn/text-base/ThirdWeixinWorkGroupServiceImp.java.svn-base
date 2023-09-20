package com.landray.kmss.third.weixin.work.service.spring;

import java.util.*;

import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.work.api.WxworkApiServiceImpl;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkGroupService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class ThirdWeixinWorkGroupServiceImp extends ExtendDataServiceImp implements IThirdWeixinWorkGroupService {

	protected final Logger logger = LoggerFactory
			.getLogger(ThirdWeixinWorkGroupServiceImp.class);

	@Override
	public String checkGroupByUserId(String groupID, String userId)
			throws Exception {
		return findGroupUser(groupID, userId);
	}

	/**
	 * <p>
	 * 查询用户是否在群中
	 * </p>
	 * 
	 * @author 陈火旺
	 * @throws Exception
	 */
	private String findGroupUser(String groupID, String userId)
			throws Exception {
		String wxUserId = getWxWorkUserId(userId);
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/appchat/get?access_token="
				+ getAccessToken() + "&chatid=" + groupID;
		JSONObject response = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if ("0".equals(response.get("errcode").toString())) {
			JSONObject chatInfo = (JSONObject) response.get("chat_info");
			List<String> userList = (List<String>) chatInfo.get("userlist");
			if (null == userList) {
				return null;
			}
			if (!userList.contains(wxUserId)) {
				updateGroup(groupID, null, null, wxUserId, null);
			}
		} else {
			logger.info("查询群聊失败------" + response.get("errcode") + ":"
					+ response.get("errmsg"));
			System.out.println("查询群聊失败------" + response.get("errcode") + ":"
					+ response.get("errmsg"));
		}
		return groupID;
	}

	/**
	 * <p>
	 * 修改群信息
	 * </p>
	 * 
	 * @param name
	 *            群名称
	 * @param owner
	 *            群主
	 * @param add_useridlist
	 *            添加成员
	 * @param del_useridlist
	 *            删除成员
	 * @throws Exception
	 * @author 陈火旺
	 */
	private void updateGroup(String chatid, String name, String owner,
			String add_useridlist, String del_useridlist) throws Exception {
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
				object.put("add_user_list", add_useridlist.split(","));
			}
			if (StringUtil.isNotNull(del_useridlist)) {
				object.put("del_user_list", del_useridlist.split(","));
			}
			String url = WxworkUtils.getWxworkApiUrl()
					+ "/appchat/update?access_token="
					+ getAccessToken();
			JSONObject response = WxworkHttpClientUtil.httpPost(url, object,
					null,
					JSONObject.class);
			if ("0".equals(response.get("errcode").toString())) {

			} else {
				logger.info("添加群聊失败------" + response.get("errcode") + ":"
						+ response.get("errmsg"));
			}
		}
	}

	private String getAccessToken() throws Exception {
		WxworkApiService wxApiService = WxworkUtils.getWxworkApiService();
		return wxApiService.getTodoAccessToken();
	}

	private String getWxWorkUserId(String userId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdEkpId = :fdEkpId");
		hqlInfo.setParameter("fdEkpId", userId);
		hqlInfo.setSelectBlock("fdAppPkId");
		IWxworkOmsRelationService omsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
				.getBean("wxworkOmsRelationService");
		String fdAppPkId = (String) omsRelationService
				.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdAppPkId)) {
			return fdAppPkId;
		} else {
			throw new Exception("用户 "+userId+" 不存在企业微信中！");
		}
	}

	@Override
	public void sendGroupMessage(String groupID, String fdId, String modelName)
			throws Exception {
		JSONObject object = new JSONObject();
		JSONObject textcard = new JSONObject();
		// 获取文档标题
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil
				.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName)
				.getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName,
				null, null);
		String messageKey = ResourceUtil
				.getString(sysDictModel.getMessageKey()); // 模块名称
		String messageUrl = ModelUtil.getModelUrl(baseModel) + "&oauth=ekp"; // 查看url
		// 获取创建人、创建时间
		// String descUser = getModelDocCreatorProperyValue(baseModel,
		// "docCreator", null);
		// String docCreateTime = getModelDocCreateTimeProperyValue(baseModel,
		// "docCreateTime",
		// Locale.CHINESE);

		textcard.put("description", messageKey);
		// 消息body部分
		textcard.put("title", docValue);
		textcard.put("url", StringUtil.formatUrl(messageUrl));
		textcard.put("btntxt", "详情");

		object.put("chatid", groupID);
		object.put("msgtype", WxworkConstant.CUSTOM_MSG_TEXTCARD);
		object.put("textcard", textcard);
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/appchat/send?access_token="
				+ getAccessToken();
		JSONObject response = WxworkHttpClientUtil.httpPost(url, object, null,
				JSONObject.class);
		if (!"0".equals(response.get("errcode").toString())) {
			logger.error("发送群消息失败------" + response.get("errcode") + ":"
					+ response.get("errmsg"));
		}
	}

	@Override
	public void updateGroupName(String groupId, String fdId, String modelName)
			throws Exception {
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil
				.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName)
				.getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName,
				null, null);
		updateGroup(groupId, docValue, null, null, null);
	}

	private String getModelDocCreateTimeProperyValue(IBaseModel baseModel,
			String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel,
				property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			rtnStr = PropertyUtils.getProperty(baseModel, property).toString();
			if (StringUtil.isNotNull(rtnStr)) {
				rtnStr = rtnStr.substring(0, 16);
			}
		}
		return rtnStr;
	}

	private String getModelDocCreatorProperyValue(IBaseModel baseModel,
			String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = ModelUtil.getModelPropertyString(baseModel,
				property, "", local);
		if (StringUtil.isNotNull(tempString)) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) PropertyUtils
					.getProperty(baseModel, property);
			if (sysOrgPerson != null) {
				rtnStr = sysOrgPerson.getFdName();
			}
		}
		return rtnStr;
	}

	@Override
	public String addGroup(String userIds, String fdId, String modelName)
			throws Exception {
		JSONObject object = new JSONObject();
		String chatid = null;
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(modelName);
		IBaseService baseService = (IBaseService) SpringBeanUtil
				.getBean(sysDictModel.getServiceBean());
		IBaseModel baseModel = baseService.findByPrimaryKey(fdId);
		String fdName = SysDataDict.getInstance().getModel(modelName)
				.getDisplayProperty();
		String docValue = ModelUtil.getModelPropertyString(baseModel, fdName,
				null, null);
		// 群聊名称限制20个字符
//		if (docValue.length() > 20) {
//			docValue = docValue.substring(0, 15) + "...";
//		}

		Set<String> userSet = new HashSet<>(Arrays.asList(userIds.split(",")));
		String owner = getWxWorkUserId(UserUtil.getUser().getFdId());
		userSet.add(owner);
		object.put("name", docValue);
		object.put("owner", owner);
		object.put("userlist", JSONArray.fromObject(userSet));
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/appchat/create?access_token="
				+ getAccessToken();
		JSONObject response = WxworkHttpClientUtil.httpPost(url, object, null,
				JSONObject.class);
		if ("0".equals(response.get("errcode").toString())) {
			chatid = response.get("chatid").toString();
			ThirdWeixinWorkGroup modelObj = new ThirdWeixinWorkGroup();
			modelObj.setFdGroupId(chatid);
			modelObj.setFdModelId(fdId);
			modelObj.setFdModelName(modelName);
			if (UserOperHelper.allowLogOper("addGroup", getModelName())) {
				UserOperContentHelper.putAdd(modelObj, "fdGroupId", "fdModelId",
						"fdModelName");
			}
			this.add(modelObj);
			// 发送消息
			sendGroupMessage(chatid, fdId, modelName);
		} else {
			logger.error("创建群聊失败，发送报文："+object.toString() + "响应信息："+response.toString());
			throw new Exception("创建群聊失败，发送报文："+object.toString() + "响应信息："+response.toString());
		}
		return chatid;
	}

	@Override
	public void deleteByGroupId(String groupID) throws Exception {
		String hql = "DELETE FROM thirdWeixinWorkGroup where fdGroupId = :fdGroupId";
		this.getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdGroupId", groupID).executeUpdate();
		this.getBaseDao().getHibernateSession().clear();
	}
}
