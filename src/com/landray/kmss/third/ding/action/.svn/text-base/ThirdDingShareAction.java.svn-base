package com.landray.kmss.third.ding.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Msg;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttPicUtils;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ObjectUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;

import net.sf.json.JSONObject;

public class ThirdDingShareAction extends ExtendAction {

	private static final Log logger = LogFactory.getLog(ThirdDingShareAction.class);

	protected ISysOrgElementService sysOrgElementService;

	protected String _ekpUserId; // F4用

	protected ISysOrgElementService
			getSysOrgElementService(HttpServletRequest request) {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	protected ISysOrgPostService sysOrgPostService;

	protected ISysOrgPostService
			getSysOrgPostServiceService(HttpServletRequest request) {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) getBean(
					"sysOrgPostService");
		}
		return sysOrgPostService;
	}

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			return (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		}
		return omsRelationService;
	}
	protected ISysAttMainCoreInnerService attMainCoreInnerService;

	protected ISysAttMainCoreInnerService
			getSysAttMainCoreInnerService(HttpServletRequest request) {
		if (attMainCoreInnerService == null) {
			attMainCoreInnerService = (ISysAttMainCoreInnerService) getBean(
					"sysAttMainTarget");
		}
		return attMainCoreInnerService;
	}

	public ActionForward share(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		try {
			Remark remark = new Remark(Options.github());
			String ekpUserId = UserUtil.getUser().getFdId();
			logger.debug("=======ekpUserId=========" + ekpUserId);
			IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
			String sender = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("=======sender=========" + sender);

			String cid = request.getParameter("cid");
			logger.debug("=======cid=========" + cid);
			String title = request.getParameter("title");
			String content = request.getParameter("fdContent");
			logger.debug("=====content:" + content);
			String fdContentPro = request.getParameter("fdContentPro");
			logger.debug("=====fdContentPro:" + fdContentPro);

			String fdModelName = request.getParameter("fdModelName");
			logger.debug("=====fdModelName:" + fdModelName);

			String fdModelId = request.getParameter("fdModelId");
			logger.debug("=====fdModelId:" + fdModelId);


			String domainName = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
			if (domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0,
                        domainName.length() - 1);
            }

			if (StringUtil.isNull(content)) {
				content = addContent(fdModelName, fdModelId, fdContentPro);
				logger.debug("=====content 22:" + content);
				if (StringUtil.isNotNull(content)
						&& content.contains("/resource/fckeditor/editor")) {
					content = content.replaceAll("/resource/fckeditor/editor",
							domainName + "/resource/fckeditor/editor");

				}
				// 移动端表情
				if (StringUtil.isNotNull(content)
						&& content.contains("/sys/mobile/js/lib/ckeditor")) {
					content = content.replaceAll("/sys/mobile/js/lib/ckeditor",
							domainName + "/sys/mobile/js/lib/ckeditor");

				}

				if (content.length() >= 1000) {
					logger.warn("分享的内容超过1000个字符，将取用前900个字符！");
					content = content.substring(0, 901) + " ...";
				}

				logger.debug("=====content 23:" + content);
				content = remark.convertFragment(content);
				logger.debug("=====content 33:" + content);
			}

			String reqUrl = request.getParameter("reqUrl");
			// reqUrl =
			// "http://chenhw.myekp.com/km/forum/km_forum/kmForumPost.do?method=view&fdForumId=172276a6c3654c35818e67e4c9481e52&fdTopicId=17227813e2dee150da6a2b94388b38f7";
			logger.debug("=====reqUrl:" + reqUrl);
			reqUrl = SecureUtil.BASE64Decoder(reqUrl);
			logger.debug("=====reqUrl22222:" + reqUrl);
			if (StringUtil.isNotNull(reqUrl)
					&& reqUrl.trim().indexOf("http") != 0) {
				if (reqUrl.trim().indexOf("/") == 0) {
					reqUrl = domainName + reqUrl.trim();
				} else {
					reqUrl = domainName + "/" + reqUrl.trim();
				}
			}
			logger.debug("=====reqUrl3333333:" + reqUrl);
			reqUrl = SecureUtil.BASE64Encoder(reqUrl);

			reqUrl = domainName + "/third/ding/pc/url_out.jsp?pg=" + reqUrl;
			logger.debug("=====reqUrl4444444:" + reqUrl);
			String fdSubject = request.getParameter("fdSubject");
			// fdSubject = SecureUtil.BASE64Decoder(fdSubject);
			logger.debug("=====fdSubject:" + fdSubject);
			if (StringUtil.isNotNull(fdSubject) && fdSubject.length() > 63) {

				fdSubject = fdSubject.substring(0, 60) + "...";
				logger.debug("标题长度超过(包括)64,处理后:" + fdSubject);
			}
			String dingUrl = DingConstant.DING_PREFIX
					+ "/message/send_to_conversation"
					+ DingUtil.getDingAppKeyByEKPUserId("?",
							ekpUserId);
			logger.debug("钉钉接口：" + dingUrl);
			ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
			OapiMessageSendToConversationRequest req = new OapiMessageSendToConversationRequest();
			req.setSender(sender);
			req.setCid(cid);
			OapiMessageSendToConversationRequest.Msg msg = new OapiMessageSendToConversationRequest.Msg();

			OapiMessageSendToConversationRequest.ActionCard actionCard = new OapiMessageSendToConversationRequest.ActionCard();
			actionCard.setTitle(fdSubject);
			// content 加上表头
			content = "# " + "**<font color=#000000 size=4>" + fdSubject
					+ "</font>** "
					+ " \n  ---------------------------------------  \n "
					+ content;

			logger.debug("=====content666:" + content);
			actionCard.setMarkdown(content);

			actionCard.setSingleTitle("查看详情");
			actionCard.setSingleUrl(reqUrl);
			msg.setActionCard(actionCard);
			msg.setMsgtype("action_card");
			// Oa oa = new Oa();
			// Head head = new Head();
			// head.setText(fdSubject);
			// head.setBgcolor("FFBBBBBB");
			// oa.setHead(head);
			// msg.setOa(oa);

			req.setMsg(msg);
			// req.setOa(oa);
			req.setTopHttpMethod("POST");
			OapiMessageSendToConversationResponse res = client.execute(req,
					DingUtils.getDingApiService().getAccessToken());
			response.setCharacterEncoding("UTF-8");

			result.put("cid", cid);
			if (res.getErrcode() == 0) {
				logger.debug(
						"分享成功：" + fdSubject + " res.getBody():"
								+ res.getBody());
				result.put("error", "0");

			} else {
				result.put("error", "1");
				result.put("msg", res.getBody());
				logger.error("分享失败，详细：" + res.getBody());
			}
		} catch (Exception e) {
			result.put("error", "1");
			logger.error("", e);
		}

		response.getWriter().write(result.toString());
		return null;
	}

	/*
	 * 获取推送内容
	 */
	private String addContent(String fdModelName, String fdModelId,
			String fdContentPro) {
		try {
			if (StringUtil.isNull(fdContentPro)) {
                return null;
            }
			SysDictModel model = SysDataDict.getInstance()
					.getModel(fdModelName);
			Map<String, SysDictCommonProperty> map = model
					.getPropertyMap();
			String beanName = model.getServiceBean();
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean(beanName);
			Object object = obj.findByPrimaryKey(fdModelId);
			Class clazz = object.getClass();
			if (fdContentPro.contains(".")) {
				String[] keyArray = fdContentPro.split("\\.");
				if (!fdContentPro.contains("[")) {
					if (keyArray != null && keyArray.length > 0) {
						String _key;
						for (int i = 1; i < keyArray.length; i++) {
							clazz = object.getClass();
							_key = "get"
									+ keyArray[i].substring(0, 1).toUpperCase()
									+ keyArray[i].substring(1);
							logger.debug("======_key=======:" + _key);

							Method method = clazz.getMethod(_key.trim());
							object = method.invoke(object);
						}
						logger.debug("======object=======:" + object);
						return object == null ? null : object.toString();

					}
				} else {
					// forumPosts[fdFloor=1].docContent
					String _key;
					String proerty;
					String keyEntity;
					String proertyKey;
					String proertyVal;
					Object rs = null;
					for (int i = 0; i < keyArray.length; i++) {
						String keyTemp = keyArray[i];
						if (keyTemp.contains("[")) {
							keyEntity = keyTemp.substring(0,
									keyTemp.indexOf("["));
							logger.debug(
									"======keyEntity=======:" + keyEntity);
							proerty = keyTemp.substring(
									keyTemp.indexOf("[") + 1,
									keyTemp.indexOf("]")); // fdFloor=1
							if (StringUtil.isNotNull(proerty)
									&& proerty.contains("=")) {
								proertyKey = proerty.split("=")[0];
								proertyVal = proerty.split("=")[1];
								logger.debug("======proertyKey=======:"
										+ proertyKey);
								logger.debug("======proertyVal=======:"
										+ proertyVal);
							} else {
								return null;
							}
							_key = "get"
									+ keyEntity.substring(0, 1).toUpperCase()
									+ keyEntity.substring(1);
							logger.debug("======_key=======:" + _key);
							Method method = clazz.getMethod(_key.trim());
							List objectList = (List) method.invoke(object);
							Object o;
							Class clazz2;
							Method method2;
							String pro_key = "get"
									+ proertyKey.substring(0, 1).toUpperCase()
									+ proertyKey.substring(1);
							logger.debug("======_key=======:" + _key);
							for (int j = 0; j < objectList.size(); j++) {
								o = objectList.get(j);
								clazz2 = o.getClass();
								method2 = clazz2.getMethod(pro_key);
								rs = method2.invoke(o);
								logger.debug("======rs2=======:" + rs);
								if (rs == null) {
									return null;
								}
								if (proertyVal.equals(rs.toString())) {
									object = o;
									break;
								}
							}
						} else {
							_key = "get"
									+ keyTemp.substring(0, 1).toUpperCase()
									+ keyTemp.substring(1);
							logger.debug("======keyTemp=======:" + _key);
							clazz = object.getClass();
							Method method = clazz.getMethod(_key.trim());
							rs = method.invoke(object);
						}

					}
					logger.debug("======object=======:" + rs);
					return rs == null ? null : rs.toString();

				}
			} else {
				String key = "get" + fdContentPro.substring(0, 1).toUpperCase()
						+ fdContentPro.substring(1);
				logger.debug("========key:" + key);
				Method method = clazz.getMethod(key.trim());
				Object rs = method.invoke(object);
				return rs == null ? null : rs.toString();
			}

		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 分享到工作通知里
	 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		try {

			String fdModelName = request.getParameter("fdModelName");
			logger.debug("fdModelName:" + fdModelName);
			if (StringUtil.isNull(fdModelName)) {
				logger.error("fdModelName不能为空");
				result.put("error", "fdModelName不能为空");
				response.getWriter().write(result.toString());
				return null;
			}
			String docSubject = request.getParameter("docSubject");
			logger.debug("docSubject:" + docSubject);

			// 内容 contentMap
			String contentMap = request.getParameter("contentMap");
			logger.debug("contentMap:" + contentMap);
			if (StringUtil.isNull(contentMap)) {
				contentMap = "docCreateTime";
			}

			String fdModelId = request.getParameter("fdModelId");
			logger.debug("fdModelId:" + fdModelId);
			if (StringUtil.isNull(fdModelId)) {
				logger.error("fdModelId不能为空");
				result.put("error", "fdModelId不能为空");
				response.getWriter().write(result.toString());
				return null;
			}

			SysDictModel sysDict = SysDataDict.getInstance().getModel(fdModelName);

			String reqUrl = request.getParameter("reqUrl");
			if (StringUtil.isNull(reqUrl) || "null".equalsIgnoreCase(reqUrl)) {
				logger.debug("分享的url为空,将取数字字典的view地址");
				String url = sysDict.getUrl();
				logger.debug("dictModel url:" + url);
				if (StringUtil.isNotNull(url) && url.contains("${fdId}")) {
					url = url.replace("${fdId}", fdModelId);
				}
				reqUrl = url;
			}
			String dingDomain = DingUtil.getDingDomin();
			if (StringUtil.isNotNull(reqUrl)
					&& reqUrl.trim().indexOf("http") != 0) {
				if (reqUrl.startsWith("/")) {
					reqUrl = dingDomain + reqUrl.trim();
				} else {
					reqUrl = dingDomain + "/" + reqUrl.trim();
				}
			}
			logger.debug("reqUrl:" + reqUrl);
			String pc_reqUrl = DingUtil.getDingPcUrl(reqUrl);
			logger.debug("pc_reqUrl:" + reqUrl);

			String sendAll = request.getParameter("sendAll");
			logger.debug("用户："+UserUtil.getKMSSUser().getUserName()+",是否全员："+sendAll);
			String targets = request.getParameter("targets");
			logger.debug("targets:" + targets);
			String agentId = request.getParameter("agentId");
			logger.debug("agentId:" + agentId);

			List<String> dingDeptid = null;
			List<String> dingUserid = null;
			List<String> deptList = new ArrayList();
			List userList = new ArrayList();
			boolean to_all_user = false;
			if (StringUtil.isNotNull(sendAll) && "all".equals(sendAll)) {
				String dingTopDeptIds = DingUtil.getDingTopDeptIds();
				if(StringUtil.isNull(dingTopDeptIds)){
					to_all_user = true;
				}else{
					dingDeptid = new ArrayList<>();
					dingDeptid.addAll(ArrayUtil.asList(dingTopDeptIds.split(";")));
				}
			}else{
				if (StringUtil.isNull(targets)) {
					throw new RuntimeException("发送对象为空！");
				} else {
					String[] org_arr = targets.split(";");
					for (int i = 0; i < org_arr.length; i++) {
						logger.debug("fdId:" + org_arr[i]);
						SysOrgElement org = (SysOrgElement) getSysOrgElementService(request).findByPrimaryKey(org_arr[i],null,true);
						if (org == null) {
							logger.error("根据fdId：" + org_arr[i] + " 找不到对应的组织架构信息！");
							continue;
						}
						Integer type = org.getFdOrgType();
						logger.debug("type:" + type + "   name:" + org.getFdName());
						if (type == 8) {
							logger.debug("人员信息");
							userList.add(org_arr[i]);
						} else if (type == 2 || type == 1) {
							logger.debug("部门信息");
							deptList.add(org_arr[i]);
						} else if (type == 4) {
							logger.debug("岗位信息");
							SysOrgPost post = (SysOrgPost) getSysOrgPostServiceService(request).findByPrimaryKey(org_arr[i],null,true);
							if (post == null) {
								logger.debug("岗位为空");
								continue;
							} else {
								List<SysOrgPerson> persons = post.getFdPersons();
								if (persons == null || persons.isEmpty()) {
									logger.debug("该岗位上没有人员");
									continue;
								}
								for (SysOrgPerson per : persons) {
									userList.add(per.getFdId());
								}
							}
						}

					}
				}
			}
			Map<String, String> send_content = new HashMap<String, String>();
			// 主题
			String serviceBean = sysDict.getServiceBean();
			logger.debug("serviceBean:" + serviceBean);

			IBaseService baseService = (IBaseService) SpringBeanUtil
					.getBean(serviceBean);
			Object baseModel = baseService.findByPrimaryKey(fdModelId);
			String title = ObjectUtil.getValue2(baseModel, docSubject);
			logger.debug("title:" + title);

			// 内容
			String content = "";
			String[] contentArray = contentMap.split(";");
			Map<String, SysDictCommonProperty> map = sysDict.getPropertyMap();
			for (int i = 0; i < contentArray.length; i++) {
				try {
					String key = contentArray[i];
					logger.debug("key" + key);
					if (StringUtil.isNull(key)) {
                        continue;
                    }

					Locale locale = ResourceUtil.getLocaleByUser();

					String name = "";
					String messageKey = "";
					String dict_key = key;
					if (key.contains(".")) {
						// 复合
						String pre = key.substring(0, key.indexOf("."));
						logger.debug("pre:" + pre);
						messageKey = map.get(pre).getMessageKey();
						dict_key = pre;
					} else {
						messageKey = map.get(key).getMessageKey();
					}
					name = ResourceUtil.getStringValue(messageKey, null,
							locale);
					logger.debug("messageKey:" + messageKey);
					logger.debug("name:" + name);
					content += name + ": ";
					String value = ObjectUtil.getValue2(baseModel, key);
					logger.debug("value:" + value);
					if (StringUtil.isNotNull(value)
							&& "DateTime".equals(map.get(dict_key).getType())) {
						value = DateUtil.convertDateToString(
								DateUtil.convertStringToDate(value),
								"yyyy/MM/dd");
					}
					
					content += value + "\n";
				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
				}
			}

			send_content.put("title", title);
			send_content.put("content", content);
			send_content.put("message_url", reqUrl);
			send_content.put("color", "FF9A89B9");
			send_content.put("pc_message_url", pc_reqUrl);

			if(dingDeptid == null){
				//全员推送
				dingUserid = findDingIdsByEkpList(userList);
				dingDeptid = findDingIdsByEkpList(deptList);
			}
			Long ding_agentId = null;
			if (StringUtil.isNotNull(agentId)) {
				ding_agentId = Long.valueOf(agentId);
			} else {
				ding_agentId = Long.valueOf(DingConfig.newInstance()
						.getDingAgentid());
			}
			logger.debug("--------------分享到工作通知的参数   start---------------");
			logger.debug("send_content:" + send_content);
			logger.debug("ding_userid:" + dingUserid);
			logger.debug("ding_deptid:" + dingDeptid);
			logger.debug("to_all_user:" + to_all_user);
			logger.debug("ding_agentId:" + ding_agentId);
			logger.debug("--------------分享到工作通知的参数   end---------------");
			if (!(to_all_user || (dingDeptid!=null&&!dingDeptid.isEmpty())
					|| (dingUserid!=null&&!dingUserid.isEmpty()))) {
				logger.error("接收分享的人或者部门为空，可能是因为对照表找不到分享对象的信息！");
				result.put("error", "接收分享的人或者部门为空，可能是因为对照表找不到分享对象的信息！");
				response.getWriter().write(result.toString());
				return null;
			}

			//分批推送
			String errorMsg = batchSendMsg(send_content,
					dingUserid, dingDeptid, to_all_user, ding_agentId,
					_ekpUserId);
			if(StringUtil.isNotNull(errorMsg)){
				result.put("error", errorMsg);
			}else{
				result.put("error", "0");
			}
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("error", "分享过程中发生异常：" + e.getMessage());
			response.getWriter().write(result.toString());
		}
		return null;

	}

	/**
	 * 批量推送工作通知
	 * @return
	 */
	private String batchSendMsg(Map<String, String> sendContent, List<String> dingUserid, List<String> dingDeptid, boolean toAllUser, Long dingAgentId, String ekpUserId) throws Exception {

		if(toAllUser){
			return checkResult(DingUtils.dingApiService.messageSend(sendContent, null, null, true, dingAgentId, ekpUserId));
		}else{
			int batchNum = 0;
			int size = 100;
			int userNum =0;
			int deptNum =0;
			List<String> userList = new ArrayList<>();//批量推送用户的数据
			List<String> deptList = new ArrayList<>(); //批量推送部门的数据
			if(dingUserid != null && dingUserid.size()>0){
				size = 100;
				userNum = dingUserid.size() % size == 0 ? dingUserid.size() / size : dingUserid.size() / size + 1;
				batchNum = userNum>batchNum?userNum:batchNum;
				for (int i = 0; i < dingUserid.size(); i += size) {
					int toIndex = Math.min(i + size, dingUserid.size());
					userList.add(String.join(",", dingUserid.subList(i, toIndex)));
				}
			}
			if(dingDeptid != null && dingDeptid.size()>0){
				size = 20;
				deptNum = dingDeptid.size() % size == 0 ? dingDeptid.size() / size : dingDeptid.size() / size + 1;
				batchNum = deptNum>batchNum?deptNum:batchNum;
				for (int i = 0; i < dingDeptid.size(); i += size) {
					int toIndex = Math.min(i + size, dingDeptid.size());
					deptList.add(String.join(",", dingDeptid.subList(i, toIndex)));
				}
			}
			if(batchNum == 1){
				//一次推送
				String userids =(userList!=null&&userList.size()>0)?userList.get(0):null;
				String deptIds =(deptList!=null&&deptList.size()>0)?deptList.get(0):null;
				return checkResult(DingUtils.dingApiService.messageSend(sendContent,userids, deptIds, false, dingAgentId,ekpUserId));
			}else if(batchNum > 1){
				//分批推送
				JSONObject result = new JSONObject();
				String rs="";
				for (int i = 0; i < batchNum; i++) {
					String userids =(userList.size()>i)?userList.get(i):null;
					String deptIds =(deptList.size()>i)?userList.get(i):null;
					rs+=checkResult(DingUtils.dingApiService.messageSend(sendContent,userids, deptIds, false, dingAgentId,ekpUserId));
				}
				return rs;
			}
		}
        return null;
	}

	/**
	 * 结果检测，返回错误信息
	 */
	private String checkResult(String result) throws Exception{
		if(StringUtil.isNull(result)){
			throw new RuntimeException("返回结果为空");
		}
		logger.info("发送钉钉工作通知结果为："+result);
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.containsKey("errcode")&& resultJson.getInt("errcode") == 0) {
			return "";
		} else {
			return result;
		}
	}

	/**
	 * 将ekpid转换成钉钉id
	 */
	public List<String> findDingIdsByEkpList(List<String> list) throws Exception {
		if (list == null || list.isEmpty()) {
			return null;
		}
		//查询匹配的数据
		String sql = "SELECT fd_app_pk_id FROM oms_relation_model " +
				" WHERE " + HQLUtil.buildLogicIN("fd_ekp_id", list);
		List<String> dingUserIds = getOmsRelationService().getBaseDao().getHibernateSession().createNativeQuery(sql).list();
		if(dingUserIds!=null&&!dingUserIds.isEmpty()){
			return dingUserIds;
		}else {
			return null;
		}
	}

	// 备份---有图片的方案
	public ActionForward sendMsg2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		try {
			String fdModelName = request.getParameter("fdModelName");
			logger.debug("fdModelName:" + fdModelName);
			if (StringUtil.isNull(fdModelName)) {
				logger.error("fdModelName不能为空");
				result.put("error", "fdModelName不能为空");
				response.getWriter().write(result.toString());
				return null;
			}
			String docSubject = request.getParameter("docSubject");
			logger.debug("docSubject:" + docSubject);
			String fdModelId = request.getParameter("fdModelId");
			logger.debug("fdModelId:" + fdModelId);
			if (StringUtil.isNull(fdModelId)) {
				logger.error("fdModelId不能为空");
				result.put("error", "fdModelId不能为空");
				response.getWriter().write(result.toString());
				return null;
			}

			SysDictModel sysDict = SysDataDict.getInstance()
					.getModel(fdModelName);

			String reqUrl = request.getParameter("reqUrl");
			if (StringUtil.isNull(reqUrl) || "null".equalsIgnoreCase(reqUrl)) {
				logger.warn("分享的url为空,将取数字字典的view地址");
				String url = sysDict.getUrl();
				logger.debug("dictModel url:" + url);
				if (StringUtil.isNotNull(url) && url.contains("${fdId}")) {
					url = url.replace("${fdId}", fdModelId);
				}
				reqUrl = url;
			}
			String dingDomain = null;
			dingDomain = DingConfig.newInstance().getDingDomain();
			if (StringUtil.isNull(dingDomain)) {
				dingDomain = ResourceUtil
						.getKmssConfigString("kmss.urlPrefix");
			}
			if (dingDomain.trim().endsWith("/")) {
				dingDomain = dingDomain.trim().substring(0,
						dingDomain.length() - 1);
			}
			if (StringUtil.isNotNull(reqUrl)
					&& reqUrl.trim().indexOf("http") != 0) {

				if (reqUrl.startsWith("/")) {
					reqUrl = dingDomain + reqUrl.trim();
				} else {
					reqUrl = dingDomain + "/" + reqUrl.trim();
				}

			}
			reqUrl = DingUtil.getDingPcUrl(reqUrl);
			logger.debug("final reqUrl:" + reqUrl);
			String sendAll = request.getParameter("sendAll");
			logger.debug("sendAll:" + sendAll);
			String targets = request.getParameter("targets");
			logger.debug("targets:" + targets);
			String agentId = request.getParameter("agentId");
			logger.debug("agentId:" + agentId);
			String fdKey = request.getParameter("fdKey");
			logger.debug("fdKey:" + fdKey);

			String pic_url = request.getParameter("pic_url");
			logger.debug("pic_url:" + pic_url);

			if (StringUtil.isNull(pic_url)) {
				List list = getSysAttMainCoreInnerService(request)
						.findByModelKey(fdModelName, fdModelId, fdKey);
				if (list.isEmpty()) {
					logger.debug("取默认图片");
					pic_url = dingDomain
							+ "/third/ding/resource/images/msg.png";
				} else {
					SysAttMain att = (SysAttMain) list.get(0);
					pic_url = dingDomain
							+ "/resource/third/ding/attachment.do?method=download&fdId="
							+ att.getFdId()
							+ "&fileName=" + att.getFdFileName();

					String timestampStr = null;
					String generateStr = null;
					timestampStr = String
							.valueOf(
									DbUtils.getDbTimeMillis() - 9L * 60 * 1000);
					generateStr = SysAttPicUtils
							.generate(timestampStr + att.getFdId());

					String viewPicHref = dingDomain
							+ "/resource/pic/attachment.do?method=view";
					viewPicHref += "&t=" + timestampStr;
					viewPicHref += "&k=" + generateStr;
					viewPicHref += "&fdId=" + att.getFdId();
					logger.debug("viewPicHref:" + viewPicHref);

					pic_url = viewPicHref;
				}

			}

			// 主题
			String serviceBean = sysDict.getServiceBean();
			logger.debug("serviceBean:" + serviceBean);

			IBaseService baseService = (IBaseService) SpringBeanUtil
					.getBean(serviceBean);
			Object baseModel = baseService.findByPrimaryKey(fdModelId);
			String content = ObjectUtil.getValue2(baseModel, docSubject);
			logger.debug("content:" + content);

			List deptList = new ArrayList();
			List userList = new ArrayList();
			if (StringUtil.isNull(targets)) {
				logger.error("发送对象为空！");
				// return null;
			} else {
				String[] org_arr = targets.split(";");
				for (int i = 0; i < org_arr.length; i++) {
					logger.debug("fdId:" + org_arr[i]);
					SysOrgElement org = (SysOrgElement) getSysOrgElementService(
							request).findByPrimaryKey(org_arr[i]);
					if (org == null) {
						logger.error("根据fdId：" + org_arr[i] + " 找不到对应的组织架构信息！");
						continue;
					}
					Integer type = org.getFdOrgType();
					logger.debug("type:" + type + "   name:" + org.getFdName());
					if (type == 8) {
						logger.debug("人员信息");
						userList.add(org_arr[i]);
					} else if (type == 2 || type == 1) {
						logger.debug("部门信息");
						deptList.add(org_arr[i]);
					} else if (type == 4) {
						logger.debug("岗位信息");
						SysOrgPost post = (SysOrgPost) getSysOrgPostServiceService(
								request).findByPrimaryKey(org_arr[i]);
						if (post == null) {
							logger.debug("岗位为空");
							continue;
						} else {
							List<SysOrgPerson> persons = post.getFdPersons();
							if (persons == null || persons.isEmpty()) {
								logger.debug("该岗位上没有人员");
								continue;
							}
							for (SysOrgPerson per : persons) {
								userList.add(per.getFdId());
							}
						}
					}

				}
			}

			Msg msg = new Msg();
			msg.setActionCard(
					new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
			msg.getActionCard().setTitle(content);
			StringBuilder sd = new StringBuilder();
			sd.append(content);
			sd.append(" \n ");
			sd.append(
					"![](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595331390499&di=6515041ce84481df61fc968b2c9cba86&imgtype=0&src=http%3A%2F%2Fbbsimg.feidee.com%2Fdata%2Fattachment%2Fforum%2Fmonth_1301%2F13010811457abe97de71de9d87.jpg)");

			// msg.getActionCard().setMarkdown(sd.toString());
			String markdown = "![](" + pic_url + ")  \n  " + content;
			logger.debug("markdown:" + markdown);
			msg.getActionCard()
					.setMarkdown(markdown);

			msg.getActionCard().setSingleTitle("查看详情");
			msg.getActionCard().setSingleUrl(reqUrl);
			msg.setMsgtype("action_card");



			boolean to_all_user = false;
			if (StringUtil.isNotNull(sendAll) && "true".equals(sendAll)) {
				to_all_user = true;
			}
			String rs = DingUtils.dingApiService.sendWorkNotify(msg, userList,
					deptList,
					to_all_user, agentId);

			logger.error("rs:" + rs);
			System.out.println("rs:" + rs);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		result.put("error", "0");
		response.getWriter().write(result.toString());
		return null;

	}
}
