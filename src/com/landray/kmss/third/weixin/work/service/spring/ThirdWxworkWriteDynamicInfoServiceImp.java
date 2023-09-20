package com.landray.kmss.third.weixin.work.service.spring;

import java.util.*;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWxworkDynamicinfo;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxArticle;
import com.landray.kmss.third.weixin.work.model.api.WxMessage;
import com.landray.kmss.third.weixin.work.service.IThirdWxworkDynamicinfoService;
import com.landray.kmss.third.weixin.work.service.IThirdWxworkWriteDynamicInfoService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdWxworkWriteDynamicInfoServiceImp
		implements
			IThirdWxworkWriteDynamicInfoService,
		WxworkConstant {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThirdWxworkWriteDynamicInfoServiceImp.class);

	private IThirdWxworkDynamicinfoService thirdWxworkDynamicinfoService;

	public void setThirdWxworkDynamicinfoService(
			IThirdWxworkDynamicinfoService thirdWxworkDynamicinfoService) {
		this.thirdWxworkDynamicinfoService = thirdWxworkDynamicinfoService;
	}

	private ThreadPoolTaskExecutor wxTaskExecutor;

	public void setWxTaskExecutor(ThreadPoolTaskExecutor wxTaskExecutor) {
		this.wxTaskExecutor = wxTaskExecutor;
	}

	private WxworkApiService wxworkApiService = null;

	@Override
	public void writeDynamicInfo(ThirdWxworkDynamicinfo dynamicinfo) {
		try {
			wxworkApiService = WxworkUtils.getWxworkApiService();
			wxTaskExecutor.execute(new WxworkNotifyPostDataRunner(dynamicinfo));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("error", e);
		}
	}

	public class WxworkNotifyPostDataRunner implements Runnable {
		private ThirdWxworkDynamicinfo dynamicinfo;

		public WxworkNotifyPostDataRunner(ThirdWxworkDynamicinfo dynamicinfo) {
			this.dynamicinfo = dynamicinfo;
		}

		@Override
        public void run() {
			try {
				WxMessage message = createWxNewsMessage(dynamicinfo);

				String agentId = dynamicinfo.getAgentid();
				if(StringUtil.isNotNull(agentId)){
					message.setAgentId(agentId);
				}else{
					message.setAgentId(
							WeixinWorkConfig.newInstance().getWxAgentid());
				}

				boolean isToAll = message.isToAll();
				if (isToAll) {
					message.setToUser("@all");
					wxworkApiService.messageSend(message);
				} else {
					send2InnerUsers(message);
					send2CorpgroupUsers(message);
				}
				thirdWxworkDynamicinfoService.delete(dynamicinfo);
			} catch (Exception e) {
				log.error("", e);
			}
		}

		/**
		 * 发送给内部用户
		 * @param message
		 * @throws Exception
		 */
		private void send2InnerUsers(WxMessage message) throws Exception {
			List<String> userIdList = message
					.getUserid_list();
			if (userIdList == null || userIdList.isEmpty()) {
				log.error("接收人为空，不进行发送。标题："
						+ message.getArticles().get(0).getTitle());
				return;
			}
			sendMessage(message,userIdList,null);
		}

		/**
		 * 发送给下游用户
		 * @param message
		 * @throws Exception
		 */
		private void send2CorpgroupUsers(WxMessage message) throws Exception {
			Map<String,List<String>> corpgroupUserIdsMap = message.getCorpgroup_userIds_map();
			if(corpgroupUserIdsMap==null || corpgroupUserIdsMap.isEmpty()){
				return;
			}
			for(String corpId:corpgroupUserIdsMap.keySet()){
				List<String> userIdList = corpgroupUserIdsMap.get(corpId);
				sendMessage(message,userIdList,corpId);
			}
		}

		private void sendMessage(WxMessage message, List<String> userIdList, String corpId) throws Exception {
			StringBuffer userIds = new StringBuffer();
			for (int i = 0; i < userIdList.size(); i++) {
				userIds.append(userIdList.get(i) + "|");
				if ((i + 1) % 1000 == 0) {
					if (userIds.length() == 0) {
						continue;
					}
					String touser = userIds.toString().substring(0,
							userIds.toString().length() - 1);
					message.setToUser(touser);
					wxworkApiService.messageSend(message,corpId);
					userIds.setLength(0);
				}
			}
			if (StringUtils.isNotEmpty(userIds.toString())) {
				String touser = userIds.toString().substring(0,
						userIds.toString().length() - 1);
				message.setToUser(touser);
				wxworkApiService.messageSend(message,corpId);
			}
		}

		private WxMessage createWxNewsMessage(
				ThirdWxworkDynamicinfo dynamicinfo) throws Exception {
			WxMessage message = new WxMessage();
			String agentId = dynamicinfo.getAgentid();
			if(StringUtil.isNull(agentId)){
				message.setAgentId(WeixinWorkConfig.newInstance().getWxAgentid());
			}else{
				message.setAgentId(agentId);
			}
			String sendTargetType = dynamicinfo.getFdSendTargetType();
			if ("all".equals(sendTargetType)) {
				message.setToAll(true);
				log.debug("发送给全部用户");
			} else if ("reader".equals(sendTargetType)) {
				message.setToAll(false);
				List readers = dynamicinfo.getReaders();
				// Thread.sleep(200);
				log.debug("可阅读者数目为：" + readers.size());
				List<String> ekpIds = sysOrgCoreService
						.expandToPersonIds(readers);
				List<String> relationIds = getRelationIds(ekpIds);
				message.setUserid_list(relationIds);
				log.debug("根据文档可阅读者获取到dingid列表为：" + relationIds.toString());
				Map<String, List<String>> corpgroupUserIdsMap = WxworkUtils.getCorpgroupUserIdsMap(ekpIds);
				message.setCorpgroup_userIds_map(corpgroupUserIdsMap);
				if(corpgroupUserIdsMap!=null) {
					log.debug("根据文档可阅读者获取到的下游组织用户列表为：" + corpgroupUserIdsMap.toString());
				}
			} else if ("specified".equals(sendTargetType)) {
				message.setToAll(false);
				String fdSpecifiedIds = dynamicinfo.getFdSpecifiedIds();
				String[] fdSpecifiedIdsArray = fdSpecifiedIds.split(";");
				List<String> ekpIds = sysOrgCoreService.expandToPersonIds(
						new ArrayList<>(Arrays.asList(fdSpecifiedIdsArray)));
				List<String> relationIds = getRelationIds(ekpIds);
				message.setUserid_list(relationIds);
				log.debug("根据指定对象获取到的dingid列表为：" + relationIds.toString());
				Map<String, List<String>> corpgroupUserIdsMap = WxworkUtils.getCorpgroupUserIdsMap(ekpIds);
				message.setCorpgroup_userIds_map(corpgroupUserIdsMap);
				if(corpgroupUserIdsMap!=null) {
					log.debug("根据指定对象获取到的下游组织用户列表为：" + corpgroupUserIdsMap.toString());
				}
			}
			message.setMsgType(WxworkConstant.CUSTOM_MSG_NEWS);
			String url = dynamicinfo.getDocUrl();
			if (StringUtil.isNotNull(url)) {
				if (url.indexOf("?") > -1) {
					url += "&oauth=" + OAUTH_EKP_FLAG;
				} else {
					url += "?oauth=" + OAUTH_EKP_FLAG;
				}
			}
			String domainName = WeixinWorkConfig.newInstance().getWxDomain();
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			String purl = domainName + "/third/weixin/work/sso/pc_message.jsp?oauth="+OAUTH_EKP_FLAG;
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(url);
			WxArticle article = new WxArticle();
			article.setUrl(purl);
			article.setDescription(dynamicinfo.getDocDescription());
			article.setTitle(dynamicinfo.getDocTitle());
			if (StringUtil.isNotNull(dynamicinfo.getPicUrl())) {
                article.setPicUrl(dynamicinfo.getPicUrl());
            }
			message.getArticles().add(article);
			if (log.isDebugEnabled()) {
				log.debug("wxMessage::" + JSON.toJSONString(message));
			}
			return message;
		}

	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		return sysOrgCoreService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private IWxworkOmsRelationService wxworkOmsRelationService = null;

	public IWxworkOmsRelationService getWxworkOmsRelationService() {
		return wxworkOmsRelationService;
	}

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private List<String> getRelationIds(List<String> ekpIds) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdEkpId", ekpIds));
		List<String> relationIds = wxworkOmsRelationService
				.findValue(hqlInfo);
		if (relationIds == null || relationIds.size() == 0) {
			log.warn("通过EKP的fdId查找中间映射表发现找不到对应的微信人员(" + ekpIds
					+ ")，请先维护中间映射表数据");
		}
		return relationIds;
	}



}
