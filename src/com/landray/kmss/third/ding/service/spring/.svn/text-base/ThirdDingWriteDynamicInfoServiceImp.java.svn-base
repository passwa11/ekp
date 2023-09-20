package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.dto.CorMode;
import com.landray.kmss.third.ding.messageType.DingOfficeBody;
import com.landray.kmss.third.ding.messageType.DingOfficeHead;
import com.landray.kmss.third.ding.messageType.DingOfficeMessage;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDynamicinfo;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDynamicinfoService;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.service.IThirdDingWriteDynamicInfoService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdDingWriteDynamicInfoServiceImp
		implements
			IThirdDingWriteDynamicInfoService,
			DingConstant {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThirdDingWriteDynamicInfoServiceImp.class);

	private IThirdDingDynamicinfoService thirdDingDynamicinfoService;

	public void setThirdDingDynamicinfoService(
			IThirdDingDynamicinfoService thirdDingDynamicinfoService) {
		this.thirdDingDynamicinfoService = thirdDingDynamicinfoService;
	}

	public IThirdDingDynamicinfoService getThirdDingDynamicinfoService() {
		return thirdDingDynamicinfoService;
	}

	private ThreadPoolTaskExecutor dingTaskExecutor;

	public void setDingTaskExecutor(ThreadPoolTaskExecutor dingTaskExecutor) {
		this.dingTaskExecutor = dingTaskExecutor;
	}

	@Override
	public void writeDynamicInfo(ThirdDingDynamicinfo dynamicinfo) {
		try {
			executeDynamicinfo(dynamicinfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("error", e);
		}
	}

	public void executeDynamicinfo(ThirdDingDynamicinfo dynamicinfo) {
		CorMode corMode = new CorMode();
		corMode.setCorpid(DingUtil.getCorpId());
		String secret = DingConfig.newInstance().getDingCorpSecret();
		if(StringUtil.isNotNull(secret)){
			corMode.setCorpsecret(DingConfig.newInstance().getDingCorpSecret());
		}else{
			corMode.setCorpsecret(DingConfig.newInstance().getAppSecret());
		}
		dingTaskExecutor
				.execute(new DingNotifyPostDataRunner(dynamicinfo, corMode));
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		return sysOrgCoreService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public class DingNotifyPostDataRunner implements Runnable {

		private ThirdDingDynamicinfo dynamicinfo;

		public DingNotifyPostDataRunner(ThirdDingDynamicinfo dynamicinfo,
				CorMode corMode) {
			this.dynamicinfo = dynamicinfo;
		}

		@Override
		public void run() {
			try {
				DingOfficeMessage dingOfficeMessage = createDingMsg(
						dynamicinfo);
				boolean isToAll = dingOfficeMessage.isToAll();
				String ekpUserId = null;
				if (dingOfficeMessage.getOa().containsKey("ekpUserId")) {
					ekpUserId = (String) dingOfficeMessage.getOa()
							.get("ekpUserId");
				}
				if (isToAll) {
					sendNotify(null, null, true, dingOfficeMessage, ekpUserId);
				} else {
					List<String> dingIdList = dingOfficeMessage
							.getUserid_list();
					if (dingIdList == null || dingIdList.isEmpty()) {
						log.error("接收人为空，不进行发送。标题："
								+ ((DingOfficeBody) dingOfficeMessage.getOa()
										.get("body")).getTitle());
					}
					StringBuffer userIds = new StringBuffer();
					int batch = 0;
					for (int i = 0; i < dingIdList.size(); i++) {
						userIds.append(dingIdList.get(i) + ",");
						if ((i + 1) % 100 == 0) {
							if (userIds.length() == 0) {
								continue;
							}
							String touser = userIds.toString().substring(0,
									userIds.toString().length() - 1);
							dingOfficeMessage.setTouser(touser);
							sendNotify(touser, null, false,
									dingOfficeMessage, ekpUserId);
							userIds.setLength(0);
							batch++;
							if (batch % 40 == 0) {
								log.debug("等待1分钟");
								Thread.sleep(60000L);
							}
						}
					}
					if (StringUtils.isNotEmpty(userIds.toString())) {
						String touser = userIds.toString().substring(0,
								userIds.toString().length() - 1);
						dingOfficeMessage.setTouser(touser);
						sendNotify(touser, null, false,
								dingOfficeMessage, ekpUserId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
		
		private void sendNotify(String userid, String deptid, boolean flag,
				DingOfficeMessage dingOfficeMessage, String ekpUserId)
				throws Exception {
			try{
				long time = System.currentTimeMillis();
				DingApiService dingService = DingUtils.getDingApiService();
				Map<String,String> content = new HashMap<String, String>();
				Map<String,Object> map = dingOfficeMessage.getOa();
				DingOfficeHead head = (DingOfficeHead) map.get("head");
				content.put("color", head.getBgcolor());
				DingOfficeBody body = (DingOfficeBody) map.get("body");
				content.put("content", body.getContent());
				content.put("title", body.getTitle());
				content.put("pc_message_url", (String) map.get("pc_message_url"));
				content.put("message_url", (String) map.get("message_url"));
				log.debug("发送数据参数，基本信息:"+ content+",用户信息："+userid+",部门信息"+deptid+",是否全员发送："+flag);
				Long agentid = null;
				if (StringUtil.isNotNull(dingOfficeMessage.getAgentid())) {
					agentid = Long.parseLong(dingOfficeMessage.getAgentid());
					if (!checkThirdDingWorkByAgentId(agentid + "")) {
						agentid = null;
					}
				}
				if (agentid == null) {
					if (StringUtil
							.isNotNull(DingConfig.newInstance()
									.getDingAgentid())) {
						agentid = Long.parseLong(
								DingConfig.newInstance().getDingAgentid());
					} else {
						agentid = 1L;
					}
				}
				String result = dingService.messageSend(content, userid, deptid,
						flag, agentid, ekpUserId);
				log.debug("消息发送返回消息："+result);
				log.debug("发送钉钉消息耗时："+(System.currentTimeMillis()-time)+"毫秒");
			}catch(Exception e){
				e.printStackTrace();
				log.debug("", e);
			}
		}

		private IThirdDingWorkService thirdDingWorkService;

		public IThirdDingWorkService getThirdDingWorkService() {
			if (thirdDingWorkService == null) {
				return (IThirdDingWorkService) SpringBeanUtil
						.getBean("thirdDingWorkService");
			}
			return thirdDingWorkService;
		}

		private boolean checkThirdDingWorkByAgentId(String agentId) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdAgentid = :fdAgentid");
			hqlInfo.setParameter("fdAgentid", agentId);
			try {
				List<ThirdDingWork> list = getThirdDingWorkService()
						.findList(hqlInfo);
				if (list != null && list.size() > 0) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
			return false;
		}

		private DingOfficeMessage createDingMsg(
				ThirdDingDynamicinfo dynamicInfo) throws Exception {
			DingOfficeMessage dingOfficeMessage = new DingOfficeMessage();
			String agentId = dynamicInfo.getAgentid();
			String ekpUserId = null;
			if(StringUtil.isNotNull(agentId)){
				dingOfficeMessage.setAgentid(agentId);
			}else{
				dingOfficeMessage
					.setAgentid(DingConfig.newInstance().getDingAgentid());
			}
			dingOfficeMessage.setMsgtype("oa");
			// dingOfficeMessage.setTouser("@all");
			String sendTargetType = dynamicInfo.getFdSendTargetType();
			if ("all".equals(sendTargetType)) {
				dingOfficeMessage.setToAll(true);
				log.debug("发送给全部用户," + dynamicInfo.getDocTitle());
			} else if ("reader".equals(sendTargetType)) {
				dingOfficeMessage.setToAll(false);
				List readers = dynamicInfo.getReaders();
				log.debug("可阅读者数目为：" + readers.size() + ","
						+ dynamicInfo.getDocTitle());
				List<String> ekpIds = sysOrgCoreService
						.expandToPersonIds(readers);
				for (int i = 0; i < ekpIds.size(); i++) {
					ekpUserId = ekpIds.get(i);
					List listTemp = ((IOmsRelationService) SpringBeanUtil
							.getBean("omsRelationService")).findList(
									"fdEkpId='" + ekpUserId + "'", null);
					if (listTemp != null && listTemp.size() > 0) {
						break;
					}
				}
				Set<String> dingIds = DingUtil.getDingIdSet(ekpIds);
				List<String> dingIdList = new ArrayList<String>();
				dingIdList.addAll(dingIds);
				dingOfficeMessage.setUserid_list(dingIdList);
				log.debug("根据文档可阅读者获取到dingid列表为" + dingIdList.toString() + ","
						+ dynamicInfo.getDocTitle());
			} else if ("specified".equals(sendTargetType)) {
				dingOfficeMessage.setToAll(false);
				String fdSpecifiedIds = dynamicInfo.getFdSpecifiedIds();
				String[] fdSpecifiedIdsArray = fdSpecifiedIds.split(";");
				List<String> ekpIds = sysOrgCoreService.expandToPersonIds(
						new ArrayList<>(Arrays.asList(fdSpecifiedIdsArray)));
				if (ekpIds == null || ekpIds.isEmpty()) {
					dingOfficeMessage.setToAll(true);
					log.warn("可阅读者为空，全员发送。" + dynamicInfo.getDocTitle());
				} else {
					Set<String> dingIds = DingUtil.getDingIdSet(ekpIds);

					for (int i = 0; i < ekpIds.size(); i++) {
						ekpUserId = ekpIds.get(i);
						List listTemp = ((IOmsRelationService) SpringBeanUtil
								.getBean("omsRelationService")).findList(
										"fdEkpId='" + ekpUserId + "'", null);
						if (listTemp != null && listTemp.size() > 0) {
							break;
						}
					}
					log.debug("ekpUserId:" + ekpUserId);
					List<String> dingIdList = new ArrayList<String>();
					dingIdList.addAll(dingIds);
					dingOfficeMessage.setUserid_list(dingIdList);
					log.debug("根据指定对象获取到dingid列表为" + dingIdList.toString() + ","
							+ dynamicInfo.getDocTitle());
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			// 设置钉钉消息头颜色
			DingOfficeHead dingOfficeHead = new DingOfficeHead();
			String titleColor = DingConfig.newInstance().getDingTitleColor();
			if (StringUtil.isNull(titleColor)) {
				titleColor = "FF9A89B9";
			}
			dingOfficeHead.setBgcolor(titleColor);

			DingOfficeBody dingOfficeBody = new DingOfficeBody();
			dingOfficeBody.setTitle(dynamicInfo.getDocTitle());
			String bodyContent = "创建日期:  " + DateUtil.convertDateToString(
					dynamicInfo.getDocCreateTime(), DateUtil.PATTERN_DATE);
			dingOfficeBody.setContent(bodyContent);

			if (StringUtil.isNotNull(dynamicInfo.getDocUrl())) {
				String url = dynamicInfo.getDocUrl();
				if (url.indexOf("?") > -1) {
					url += "&oauth=ekp";
				} else {
					url += "?oauth=ekp";
				}
				map.put("message_url", url);
				String domainName = DingConfig.newInstance().getDingDomain();
				if(StringUtil.isNull(domainName)) {
                    domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
                }
				if(domainName.endsWith("/")) {
                    domainName = domainName.substring(0, domainName.length()-1);
                }
				String pcViewUrl = domainName+"/third/ding/pc/pcopen.jsp?fdTodoId=&appId=" + agentId
						+ "&oauth=ekp&url="+SecureUtil.BASE64Encoder(url);
		    	map.put("pc_message_url", pcViewUrl);
			}
			map.put("head", dingOfficeHead);
			map.put("body", dingOfficeBody);
			map.put("ekpUserId", ekpUserId);
			dingOfficeMessage.setOa(map);
			return dingOfficeMessage;
		}

	}

	private ISysOrgCoreService sysOrgCoreService;

}
