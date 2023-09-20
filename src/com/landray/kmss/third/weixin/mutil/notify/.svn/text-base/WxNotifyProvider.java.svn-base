package com.landray.kmss.third.weixin.mutil.notify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.ThirdWeixinWorkMutil;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.api.WxArticle;
import com.landray.kmss.third.weixin.mutil.model.api.WxMessage;
import com.landray.kmss.third.weixin.mutil.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;

public class WxNotifyProvider extends BaseSysNotifyProviderExtend
		implements
        WxmutilConstant {

	private static final Logger logger = LoggerFactory
			.getLogger(WxNotifyProvider.class);

	private Map<String, WxmutilApiService> wxmutilApiServiceList = null;

	private void init() {
		wxmutilApiServiceList = WxmutilUtils.getWxmutilApiServiceList();
	}

	private IWxworkOmsRelationService mutilWxworkOmsRelationService = null;

	public void setMutilWxworkOmsRelationService(IWxworkOmsRelationService mutilWxworkOmsRelationService) {
		this.mutilWxworkOmsRelationService = mutilWxworkOmsRelationService;
	}

	private IThirdWeixinWorkService thirdMutilWeixinWorkService;

	public void setThirdMutilWeixinWorkService(IThirdWeixinWorkService thirdMutilWeixinWorkService) {
		this.thirdMutilWeixinWorkService = thirdMutilWeixinWorkService;
	}

	@Override
    public void add(SysNotifyTodo todo, NotifyContext context)
			throws Exception {
		long atime = System.currentTimeMillis();
		init();
		if (wxmutilApiServiceList.isEmpty()
				|| wxmutilApiServiceList.size() <= 0) {
			logger.debug("没有配置企业微信集成，不推送消息...");
			return;
		}
		String appType = context.getFdAppType();
		if (appType != null && (appType.contains("all")
				|| appType.contains("wxwork"))) {
			logger.debug("已经设置参数fdAppType="+appType+",故强制推送消息...");
		}
		
		NotifyContextImp ctx = (NotifyContextImp) context;
		List notifyTargetList = ((NotifyContextImp) context).getNotifyPersons();
		if (notifyTargetList == null || notifyTargetList.isEmpty()) {
			logger.debug("no person to send");
			return;
		} else {
			if (notifyTargetList.size() > 0) {
				StringBuffer usersloginName = null;
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
				List<WxworkOmsRelationMutilModel> omsModels = mutilWxworkOmsRelationService
						.findList(hqlInfo);
				if (omsModels == null || omsModels.size() == 0) {
					logger.debug("no person to send");
				}
				//兼容多企业微信
				Map<String, List<WxworkOmsRelationMutilModel>> omsMap = new HashMap<String, List<WxworkOmsRelationMutilModel>>();
				for (WxworkOmsRelationMutilModel model : omsModels) {
					List<WxworkOmsRelationMutilModel> tempList = omsMap.get(model.getFdWxKey());
					if (ArrayUtil.isEmpty(tempList)) {
						tempList = new ArrayList<WxworkOmsRelationMutilModel>();
						tempList.add(model);
						omsMap.put(model.getFdWxKey(), tempList);
					} else {
						tempList.add(model);
					}
				}

				//通过fdwxkey获取wxCpService发送待办待阅
				for (Map.Entry<String, List<WxworkOmsRelationMutilModel>> entry : omsMap.entrySet()) {
					usersloginName = new StringBuffer();
					String key = entry.getKey();
					WxMessage message = createWxMessage(key, todo);
					logger.debug("推送应用ID：" + message.getAgentId());
					if (!"true".equals(WeixinMutilConfig.newInstance(key).getWxTodoEnabled())) {
						//待办关闭
						continue;
					}

					if (todo.getFdType() == 2) {
						if (!"true".equals(WeixinMutilConfig.newInstance(key).getWxTodoType2Enabled())) {
							// 待阅关闭,默认关闭，开启需要勾上
							continue;
						}
					}
					List<WxworkOmsRelationMutilModel> omsModelList = entry.getValue();
					for (int i = 0; i < omsModelList.size(); i++) {
						usersloginName.append(omsModelList.get(i).getFdAppPkId() + "|");
						if ((i + 1) % 1000 == 0) {
							if (StringUtils.isNotEmpty(usersloginName.toString())) {
								message.setToUser(usersloginName.substring(0, usersloginName.length() - 1));
								try {
									wxmutilApiServiceList.get(key)
											.messageSend(message);
								} catch (Exception e) {
									logger.error(e.getMessage());
								}
							}
							usersloginName.setLength(0);
						}
					}
					if (StringUtils.isNotEmpty(usersloginName.toString())) {
						message.setToUser(usersloginName.substring(0, usersloginName.length() - 1));
						try {
							wxmutilApiServiceList.get(key).messageSend(message);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}
			}
		}
		logger.debug("发送企业微信消息总耗时："+(System.currentTimeMillis()-atime)+"毫秒");
	}

	private WxMessage createWxMessage(String key, SysNotifyTodo todo)
			throws Exception {
		String MsgType = WeixinMutilConfig.newInstance(key).getWxNotifyType();
		if (todo.getFdType() == 2) {
			//待阅
			MsgType = WeixinMutilConfig.newInstance(key).getWxToReadNotifyType();
		}
		if (StringUtil.isNull(MsgType)) {
			MsgType = WxmutilConstant.CUSTOM_MSG_NEWS;
		}
		if (WxmutilConstant.CUSTOM_MSG_NEWS.equals(MsgType)) {
			return createWxNewsMessage(key, todo);
		} else {
			return createWxTextMessage(key, todo);
		}
	}

	private WxMessage createWxNewsMessage(String key, SysNotifyTodo todo)
			throws Exception {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		String wxagentId = getNotifyPushAgentId(key, todo);
		WxMessage message = new WxMessage();
		message.setAgentId(wxagentId);
		message.setMsgType(WxmutilConstant.CUSTOM_MSG_NEWS);
		String domainName = WeixinMutilConfig.newInstance(key).getWxDomain();
		if(StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if(domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length()-1);
        }
		String viewUrl = null;
		String purl = "/third/weixin/mutil/sso/pc_message.jsp?oauth=" + OAUTH_EKP_FLAG;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG + "&wxkey=" + key;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
			purl = purl + "&fdTodoId=" + todo.getFdId() + "&wxkey=" + key;
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
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(StringUtil.formatUrl(todo.getFdLink(), domainName));
		}
		WxArticle article = new WxArticle();
		viewUrl = wxmutilApiServiceList.get(key)
				.oauth2buildAuthorizationUrl(domainName + purl, null);
		article.setUrl(viewUrl);

		StringBuffer sb = new StringBuffer();
		if (todo.getDocCreator() != null) {
			sb.append("${fdCreatorName}:      " + todo.getDocCreator().getFdName(fdLang));
			sb.append("\r\n");
		}

		sb.append("${docCreateTime}:  "
				+ DateUtil.convertDateToString(todo.getFdCreateTime(),
						"yyyy-MM-dd"));
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

		String description = sb.toString()
				.replace("${fdCreatorName}",
						ResourceUtil.getStringValue("sysNotifyCategory.fdCreatorName", "sys-notify", locale))
				.replace("${docCreateTime}",
						ResourceUtil.getStringValue("sysNotifyCategory.docCreateTime", "sys-notify", locale))
				.replace("${module}",
						ResourceUtil.getStringValue("sysNotifyCategory.type.module", "sys-notify", locale));
		article.setDescription(description);
		article.setTitle(todo.getFdSubject());
		message.getArticles().add(article);

		if (logger.isDebugEnabled()) {
			logger.debug("wxMessage::" + JSON.toJSONString(message));
		}
		return message;
	}

	private WxMessage createWxTextMessage(String key, SysNotifyTodo todo)
			throws Exception {
		String fdLang = todo.getFdLang();
		Locale locale = SysLangUtil.getLocaleByShortName(fdLang);
		if (locale == null) {
			locale = ResourceUtil.getLocaleByUser();
		}
		WxMessage message = new WxMessage();
		String wxagentId = getNotifyPushAgentId(key, todo);
		message.setAgentId(wxagentId);
		message.setMsgType(WxmutilConstant.CUSTOM_MSG_TEXT);
		String domainName = WeixinMutilConfig.newInstance(key).getWxDomain();
		if(StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if(domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length()-1);
        }
		String viewUrl = null;
		String purl = "/third/weixin/mutil/sso/pc_message.jsp?oauth=" + OAUTH_EKP_FLAG;
		if(StringUtil.isNotNull(todo.getFdId())){
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=" + OAUTH_EKP_FLAG + "&wxkey=" + key;
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
			purl = purl + "&fdTodoId=" + todo.getFdId() + "&wxkey=" + key;
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
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(StringUtil.formatUrl(todo.getFdLink(), domainName));
		}
		viewUrl = wxmutilApiServiceList.get(key)
				.oauth2buildAuthorizationUrl(domainName + purl, null);
//		viewUrl = wxCpService.oauth2buildAuthorizationUrl(viewUrl, null);
		StringBuffer content = new StringBuffer();
		content.append(todo.getFdSubject()).append("\r\n");
		if (todo.getDocCreator() != null) {
			content.append("${fdCreatorName}：").append(todo.getDocCreator().getFdName(fdLang));
			content.append("\n");
		}
		content.append("${docCreateTime}:  "
				+ DateUtil.convertDateToString(todo.getFdCreateTime(),
						"yyyy-MM-dd"));
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

	/**
	 * <p>获取待阅推送的应用id</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String getNotifyPushAgentId(String key, SysNotifyTodo todo) throws Exception {
		String wxAgentId = WeixinMutilConfig.newInstance(key).getWxAgentid();
		if (todo.getFdType() == 2) {
			//待阅
			wxAgentId = WeixinMutilConfig.newInstance(key).getWxToReadAgentid();
			String wxToReadPre = WeixinMutilConfig.newInstance(key).getWxToReadPre();
			if(StringUtil.isNull(wxToReadPre) || !"true".equals(wxToReadPre)){
				return wxAgentId;
			}
			String workAgentId = getWxWorkByModel(todo);
			if(StringUtil.isNotNull(workAgentId)){
				return workAgentId;
			}
		}
		return wxAgentId;
	}

	private String getWxWorkByModel(SysNotifyTodo todo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<ThirdWeixinWorkMutil> wxWorkList = thirdMutilWeixinWorkService.findList(hqlInfo);
		if (null == wxWorkList || wxWorkList.size() <= 0) {
			return null;
		}
		for (ThirdWeixinWorkMutil weixinWork : wxWorkList) {
			if (StringUtil.isNotNull(weixinWork.getFdUrlPrefix())) {
				String[] fdUrlPrefix = weixinWork.getFdUrlPrefix().split(";");
				for (int i = 0; i < fdUrlPrefix.length; i++) {
					if (todo.getFdLink().indexOf(fdUrlPrefix[i]) > -1) {
						return weixinWork.getFdAgentid();
					}
				}
			}
		}
		return null;
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
