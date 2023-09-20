package com.landray.kmss.third.weixin.notify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxArticle;
import com.landray.kmss.third.weixin.model.api.WxMessage;
import com.landray.kmss.third.weixin.spi.model.WxOmsRelationModel;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class WxNotifyProvider extends BaseSysNotifyProviderExtend
		implements
			WxConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxNotifyProvider.class);

	private WxApiService wxApiService = null;

	private void init() {
		wxApiService = WxUtils.getWxApiService();
	}

	private IWxOmsRelationService wxOmsRelationService = null;

	public void setWxOmsRelationService(
			IWxOmsRelationService wxOmsRelationService) {
		this.wxOmsRelationService = wxOmsRelationService;
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context)
			throws Exception {
		try{
			long atime = System.currentTimeMillis();
			if(!"true".equals(WeixinConfig.newInstance().getWxEnabled())){
				logger.debug("微信企业号未开启集成，不推送消息...");
				return;
			}
			boolean notifyflag = false;
			String appType = context.getFdAppType();
			if (StringUtil.isNotNull(appType) ) {
				if (appType.contains("all") || appType.contains("weixin")) {
					notifyflag = true;
					logger.debug("已经设置参数fdAppType=" + appType + ",故强制推送消息...");
				}
			}else{
				if (!"true".equals(WeixinConfig.newInstance().getWxTodoEnabled())) {
					return;
				}

				if (todo.getFdType() == 2) {
					if (!"true".equals(
							WeixinConfig.newInstance().getWxTodoType2Enabled())) {
						// 待阅关闭,默认关闭，开启需要勾上
						return;
					}
				}
				
				notifyflag = true;
			}

			if(!notifyflag){
				logger.debug("微信企业号不进行消息推送，消息参数fdAppType=" + appType + ",待办Id="+todo.getFdId()+",待办主题："+todo.getFdSubject());
				return;
			}
			
			NotifyContextImp ctx = (NotifyContextImp) context;
			List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
			if (notifyTargetList == null || notifyTargetList.isEmpty()) {
				logger.debug("通知人员为空不执行消息发送，通知标题为："+todo.getFdSubject()+"("+todo.getFdId()+")");
				return;
			} else {
				init();
				if (notifyTargetList.size() > 0) {
					WxMessage message = createWxMessage(todo);
					StringBuffer usersloginName = new StringBuffer();
					List<String> ekpIds = new ArrayList<String>();
					if (notifyTargetList != null && notifyTargetList.size() > 0) {
						Iterator<?> it = ctx.getNotifyPersons().iterator();
						while (it.hasNext()) {
							SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
							ekpIds.add(sysOrgPerson.getFdId());
						}
					}
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdEkpId", ekpIds));
					List<WxOmsRelationModel> omsModels = wxOmsRelationService
							.findList(hqlInfo);
					if (omsModels == null || omsModels.size() == 0) {
						logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+ekpIds+")，请先维护中间映射表数据");
					}
					if (omsModels != null && omsModels.size() > 0) {
						for (int i = 0; i < omsModels.size(); i++) {
							usersloginName
									.append(omsModels.get(i).getFdAppPkId() + "|");
							if ((i + 1) % 500 == 0) {
								if (StringUtils
										.isNotEmpty(usersloginName.toString())) {
									message.setToUser(usersloginName.substring(0,
											usersloginName.length() - 1));
									logger.debug("发送微信消息的人员列表:" + usersloginName);
									try {
										wxApiService.messageSend(message);
									} catch (Exception e) {
										logger.error(e.getMessage());
									}
								}
								usersloginName.setLength(0);
							}
						}
					}
					if (StringUtils.isNotEmpty(usersloginName.toString())) {
						message.setToUser(usersloginName.substring(0,
								usersloginName.length() - 1));
						logger.debug("发送微信消息的人员列表:" + usersloginName);
						try {
							wxApiService.messageSend(message);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}
			}
			logger.debug("发送微信企业号消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("", e);
		}
	}

	private WxMessage createWxMessage(SysNotifyTodo todo) throws Exception {
		return createWxNewsMessage(todo);
	}

	private WxMessage createWxNewsMessage(SysNotifyTodo todo)
			throws Exception {

		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}

		WxMessage message = new WxMessage();
		message.setAgentId(WeixinConfig.newInstance().getWxAgentid());
		message.setMsgType(WxConstant.CUSTOM_MSG_NEWS);
		String domainName = WeixinConfig.newInstance().getWxDomain();
		String viewUrl = null;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		}else{
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);		
			if(viewUrl.indexOf("?")==-1){
				viewUrl = viewUrl + "?oauth=" + OAUTH_EKP_FLAG;
			}else{
				viewUrl = viewUrl + "&oauth=" + OAUTH_EKP_FLAG;
			}
		}
		WxArticle article = new WxArticle();
//		viewUrl = wxCpService.oauth2buildAuthorizationUrl(viewUrl, null);
		article.setUrl(viewUrl);

		StringBuffer sb = new StringBuffer();
		if (todo.getDocCreator() != null) {
			sb.append("${fdCreatorName}:      " + todo.getDocCreator().getFdName(fdLang));
			sb.append("\r\n");
		}

		sb.append("${docCreateTime}:  " + DateUtil
				.convertDateToString(todo.getFdCreateTime(), "yyyy-MM-dd"));
		sb.append("\r\n");
		SysDictModel sysDict = SysDataDict.getInstance()
				.getModel(todo.getFdModelName());
		String modelName = "";
		if (sysDict != null) {
			modelName = ResourceUtil.getStringValue(sysDict.getMessageKey(), null, locale);
		}
		if (StringUtils.isNotBlank(modelName)) {
			sb.append("${module}:  " + modelName);
		}
		String description = sb.toString().replace("${fdCreatorName}",
				ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale))
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
		article.setDescription(description);
		String title = todo.getFdSubject();
		switch (todo.getFdLevel()) {
		case 1:
			title = ResourceUtil.getString("enums.notify.level.1", "third-weixin") + title;
			break;
		case 2:
			title = ResourceUtil.getString("enums.notify.level.2", "third-weixin") + title;
			break;
		default:
			title = ResourceUtil.getString("enums.notify.level.3", "third-weixin") + title;
			break;
		}
		article.setTitle(title);
		message.getArticles().add(article);

		if (logger.isDebugEnabled()) {
			logger.debug("wxMessage::" + JSON.toJSONString(message));
		}
		return message;
	}

	private WxMessage createWxTextMessage(SysNotifyTodo todo)
			throws Exception {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}

		WxMessage message = new WxMessage();
		message.setAgentId(WeixinConfig.newInstance().getWxAgentid());
		message.setMsgType(WxConstant.CUSTOM_MSG_TEXT);
		String domainName = WeixinConfig.newInstance().getWxDomain();
		String viewUrl = null;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		}else{
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);		
			if(viewUrl.indexOf("?")==-1){
				viewUrl = viewUrl + "?oauth=" + OAUTH_EKP_FLAG;
			}else{
				viewUrl = viewUrl + "&oauth=" + OAUTH_EKP_FLAG;
			}
		}
		viewUrl = wxApiService.oauth2buildAuthorizationUrl(viewUrl, null);
		StringBuffer content = new StringBuffer();
		String title = todo.getFdSubject();
		Locale local = new Locale(todo.getFdLang().split("-")[0],todo.getFdLang().split("-")[1]);
		switch (todo.getFdLevel()) {
		case 1:
			title = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.1", "sys-notify", local)+"】" + title;
			break;
		case 2:
			title = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.2", "sys-notify", local)+"】" + title;
			break;
		default:
			title = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.3", "sys-notify", local)+"】" + title;
			break;
		}
		content.append(title).append("\r\n");
		if (todo.getDocCreator() != null) {
			content.append("${fdCreatorName}：").append(todo.getDocCreator().getFdName(fdLang));
			content.append("\n");
		}
		content.append("${docCreateTime}:  " + DateUtil
				.convertDateToString(todo.getFdCreateTime(), "yyyy-MM-dd"));
		content.append("\n");
		SysDictModel sysDict = SysDataDict.getInstance()
				.getModel(todo.getFdModelName());
		String modelName = "";
		if (sysDict != null) {
			modelName = ResourceUtil.getStringValue(sysDict.getMessageKey(), null, locale);
		}
		if (StringUtils.isNotBlank(modelName)) {
			content.append("${module}:  " + modelName);
			content.append("\r\n");
		}
		content.append("<a href='" + viewUrl + "'>"
				+ ResourceUtil.getStringValue("third.wx.notify.viewall", "third-weixin", locale) + "</a>");

		String c = content.toString()
				.replace("${fdCreatorName}",
						ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale))
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
		message.setContent(c);
		if (logger.isDebugEnabled()) {
			logger.debug("wxMessage::" + JSON.toJSONString(message));
		}
		return message;
	}

	@Override
    public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void remove(SysNotifyTodo todo) throws Exception {
	}

	@Override
    public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
	}

	@Override
    public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
	}

	@Override
    public void setTodoDone(SysNotifyTodo todo) throws Exception {
	}

}
