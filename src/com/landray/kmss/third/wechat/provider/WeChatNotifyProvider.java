package com.landray.kmss.third.wechat.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.common.util.StringUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.lic.SystemParameter;
import com.landray.kmss.sys.notify.dao.ISysNotifyShortMessageSendDao;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProvider;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.notify.util.SysNotifyLangUtil;
import com.landray.kmss.sys.organization.interfaces.PersonCommunicationInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.wechat.dto.WeChatNotifyDto;
import com.landray.kmss.third.wechat.forms.WeChatNotifyForm;
import com.landray.kmss.third.wechat.model.WechatMainConfig;
import com.landray.kmss.third.wechat.util.LicenseCheckUtil;
import com.landray.kmss.third.wechat.util.WeChatWebServiceUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * @author:kezm
 * @date :2014-6-16下午05:27:32
 */
public class WeChatNotifyProvider extends BaseSysNotifyProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeChatNotifyProvider.class);

	private ISysNotifyShortMessageSendDao sysNotifyShortMessageSendDao;

	private static MultiThreadedHttpConnectionManager connectionManager;

	private static MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	public void setSysNotifyShortMessageSendDao(
			ISysNotifyShortMessageSendDao sysNotifyShortMessageSendDao) {
		this.sysNotifyShortMessageSendDao = sysNotifyShortMessageSendDao;
	}

	@Override
	public PersonCommunicationInfo getNotifyPersonCommunicationInfo(
			NotifyContext ctx) throws Exception {
		NotifyContextImp context = (NotifyContextImp) ctx;
		PersonCommunicationInfo communicationInfo = null;
		if (context.getPersonCommunicationInfo() == null) {
			List notifyPersons = getNotifyPersons(ctx);
			if (notifyPersons != null) {
				communicationInfo = orgService
						.getPersonCommunicationInfo(notifyPersons);
			}
			context.setPersonCommunicationInfo(communicationInfo);
		}
		return context.getPersonCommunicationInfo();

	}

	@Override
    public void send(NotifyContext context) {
		PersonCommunicationInfo communicationInfo = null;
		try {
			communicationInfo = getNotifyPersonCommunicationInfo(context);
		} catch (Exception e) {
			logger.error(
					"WeChatNotifyProvider.send,获取用户信息出错:" + e.getMessage());
		}
		if (communicationInfo == null) {
			logger.error("WeChatNotifyProvider.send,获取用户信息为NULL");
			return;
		}

		List<String> defaultLangs = communicationInfo.getDefaultLangs();
		NotifyContextImp ctx = (NotifyContextImp) context;
		// 确定发送者
		SysOrgElement user = findSender(ctx);

		String link = context.getLink();
		List notifyTargetList = ctx.getNotifyTarget();


		Map<String, List<String>> userIdMap = new HashMap<String, List<String>>();
		// List<String> userIdList = new ArrayList<String>();
		if (notifyTargetList != null && notifyTargetList.size() > 0) {
			Iterator<?> it = ctx.getNotifyPersons().iterator();
			while (it.hasNext()) {
				SysOrgPerson sysOrgPerson = (SysOrgPerson) it.next();
				String lang = sysOrgPerson.getFdDefaultLang();
				if (StringUtil.isNull(lang)) {
					lang = SysNotifyLangUtil.getDefaultLang();
				}
				List<String> list = userIdMap.get(lang);
				if (list == null) {
					list = new ArrayList<String>();
					userIdMap.put(lang, list);
				}
				Map<String, String> userInforMap = new HashMap<String, String>();
				userInforMap.put("userId", sysOrgPerson.getFdId());
				userInforMap.put("userName", sysOrgPerson.getFdName());
				userInforMap.put("mobileNo", sysOrgPerson.getFdMobileNo());
				userInforMap.put("email", sysOrgPerson.getFdEmail());
				JSONObject userJson = JSONObject.fromObject(userInforMap);
				list.add(userJson.toString());
			}
		}

		for (String lang : userIdMap.keySet()) {
			WeChatNotifyDto weChatNotifyDto = new WeChatNotifyDto();
			NotifyContextImp contextImp = (NotifyContextImp) context;
			String subject = SysNotifyLangUtil.replaceLangValue(
					contextImp.getNotifyReplace(), lang, context.getSubject());
			String linkSubject = SysNotifyLangUtil.replaceLangValue(
					contextImp.getNotifyReplace(), lang,
					context.getLinkSubject());
			String content = SysNotifyLangUtil.replaceLangValue(
					contextImp.getNotifyReplace(), lang, context.getContent());
			weChatNotifyDto.setLink(link);
			weChatNotifyDto.setLinkSubject(linkSubject);
			weChatNotifyDto.setDefaultLangs(defaultLangs);
			weChatNotifyDto.setFdBundle(context.getFdBundle());
			weChatNotifyDto.setReplaceText(context.getFdReplaceText());
			weChatNotifyDto.setContent(content);

			if (user != null) {
				// 设置发送者
				weChatNotifyDto.setFromPersonId(user.getFdId());
				weChatNotifyDto.setFromPersonName(user.getFdName(lang));

				String fromEmail = "";
				if (StringUtils.isEmpty(fromEmail)) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) user;
					fromEmail = sysOrgPerson.getFdEmail();
				}
				weChatNotifyDto.setFromPersonEmail(fromEmail);
			}

			weChatNotifyDto.setPersonList(userIdMap.get(lang));
			weChatNotifyDto.setSubject(subject);

			String sysKey = null;
			if (sysKey == LicenseCheckUtil.checkLicense()) {
				// 兼容老系统
				sysKey = SystemParameter.get("license-to") + ","
						+ SystemParameter.get("license-title") + ","
						+ SystemParameter.get("license-type") + ","
						+ SystemParameter.get("license-expire");
			} else {
				sysKey = LicenseCheckUtil.checkLicense();
			}
			weChatNotifyDto.setLicense(sysKey);
			JSONObject jsonObject = JSONObject.fromObject(weChatNotifyDto);
			sendToWechat(jsonObject);
		}
	}

	private void sendToWechat(JSONObject jsonObject) {
		String wecharUrl = "";
		try {
			WechatMainConfig config = new WechatMainConfig();
			wecharUrl = config.getLwechat_wyUrl();
		} catch (Exception e1) {
			logger.error("WeChatNotifyProvider.send,获取配置信息发生异常,异常信息:"
					+ e1.getMessage());
			e1.printStackTrace();
		}
		if (StringUtil.isNotNull(wecharUrl)) {
			String[] sp = wecharUrl.split(";");
			wecharUrl = sp[0];
		}
		String url = wecharUrl + "/app/systemsso/lwenotify";
		HttpClient httpClient = new HttpClient(getConnectionManager());
		// 设置超时
		httpClient.setConnectionTimeout(15 * 1000);
		httpClient.setTimeout(15 * 1000);

		PostMethod post = new PostMethod(url);
		RequestEntity entity;

		try {
			entity = new StringRequestEntity(jsonObject.toString(), null,
					"utf-8");
			post.setRequestEntity(entity);
			String resString = "";

			int result = httpClient.executeMethod(post);
			boolean resultFlag = true;
			if (result == 200) {
				// 接受lwe反馈的信息
				resString = post.getResponseBodyAsString();
				if ("1".equals(resString)) {
					if (logger.isDebugEnabled()) {
						logger.debug("WeChatNotifyProvider.send,消息推送成功");
					}
				} else {
					resultFlag = false;
				}
			} else {
				resString = post.getResponseBodyAsString();
				if (logger.isDebugEnabled()) {
					logger.debug("WeChatNotifyProvider.send,消息推送发生异常,异常信息:"
							+ resString);
				}
				resultFlag = false;
			}

			if (!resultFlag) {
				// 发送失败后，进行email和短消息的发送
				WeChatNotifyForm weChatNotifyForm = transToForm(jsonObject
						.toString());
				NotifyContext notifyContext = new LweNotifyContext();
				String fromType = "2";
				WeChatWebServiceUtil.doProvider(weChatNotifyForm,
						notifyContext, fromType);
			}
		} catch (Exception e) {
			logger.error("WeChatNotifyProvider.send,消息推送发生异常,异常信息:"
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	private SysOrgElement findSender(NotifyContext context) {
		if (context instanceof NotifyContextImp) {
			try {
				NotifyContextImp imp = (NotifyContextImp) context;
				if (StringUtil.isNotNull(imp.getModelId())
						&& StringUtil.isNotNull(imp.getModelName())) {
					IBaseModel mainModel = sysNotifyShortMessageSendDao
							.findByPrimaryKey(imp.getModelId(), imp
									.getModelName(), true);
					if (mainModel != null
							&& PropertyUtils
									.isReadable(mainModel, "docCreator")
							&& PropertyUtils.isReadable(mainModel, "docStatus")
							&& "30".equals((String) PropertyUtils.getProperty(
									mainModel, "docStatus"))) {
						return (SysOrgElement) PropertyUtils.getProperty(
								mainModel, "docCreator");
					} else if (UserUtil.getUser() != null
							&& !UserUtil.getKMSSUser().isAnonymous()) {
						return UserUtil.getUser();
					}
				}
			} catch (Exception e) {
				logger.error(
						"WeChatNotifyProvider.findSender,消息推送查找接收者发生异常,异常信息:"
								+ e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getSimpleMailContent(String content, String linkSubject,
			String link) {
		if (link == null) {
            return content;
        }
		String maildns = ResourceUtil.getKmssConfigString("kmss.appsvr.dns");
		StringBuffer mailContent = new StringBuffer();

		if (content != null) {
			mailContent.append(content).append("\r\n\r\n");
		}

		if (StringUtil.isNotNull(linkSubject) && StringUtil.isNotNull(link)) {
			mailContent.append(linkSubject).append("\r\n");
		}
		if (StringUtil.isNull(maildns)) {
			mailContent.append(StringUtil.formatUrl(link));
		} else {
			String[] dns = maildns.split(";");
			for (int i = 0; i < dns.length; i++) {
				String urlPrefix = ResourceUtil
						.getKmssConfigString("kmss.appsvr." + dns[i].trim()
								+ ".urlPrefix");
				if (urlPrefix.endsWith("/")) {
                    urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 2);
                }
				if (!urlPrefix.startsWith("http")) {
                    urlPrefix = "http://" + urlPrefix;
                }
				String title = ResourceUtil.getKmssConfigString("kmss.appsvr."
						+ dns[i].trim() + ".title");
				String url = StringUtil.formatUrl(link, urlPrefix);
				mailContent.append(title).append("  ").append(url).append(
						"\r\n");
			}
		}
		return mailContent.toString();
	}

	/**
	 * 将返回的结果数据转换成form形式
	 * 
	 * @param result
	 * @return
	 */
	private WeChatNotifyForm transToForm(String result) {

		JSONObject res = JSONObject.fromObject(result);
		String license = res.getString("license");
		String link = res.getString("link");
		String linkSubject = res.getString("linkSubject");
		String subject = res.getString("subject");
		List<String> userIdList = res.getJSONArray("personList");
		String fromPersonId = res.getString("fromPersonId");
		String fromPersonName = res.getString("fromPersonName");
		String fromPersonEmail = res.getString("fromPersonEmail");
		String mailContent = res.getString("content");
		List<String> defaultLangs = res.getJSONArray("defaultLangs");
		String fdBundle = res.getString("fdBundle");
		String replaceText = res.getString("replaceText");

		List<String> userInforList = new ArrayList<String>();

		for (int j = 0; j < userIdList.size(); j++) {
			JSONObject userStr = JSONObject.fromObject(userIdList.get(j));
			userInforList.add(userStr.toString());
		}

		WeChatNotifyForm weChatNotifyForm = new WeChatNotifyForm();
		weChatNotifyForm.setLicense(license);
		weChatNotifyForm.setLink(link);
		weChatNotifyForm.setLinkSubject(linkSubject);
		weChatNotifyForm.setSubject(subject);
		weChatNotifyForm.setFdBundle(fdBundle);
		weChatNotifyForm.setReplaceText(replaceText);
		weChatNotifyForm.setContent(mailContent);
		weChatNotifyForm.setFromPersonId(fromPersonId);
		weChatNotifyForm.setFromPersonName(fromPersonName);
		weChatNotifyForm.setPersonList(userInforList);

		weChatNotifyForm.setFromPersonEmail(fromPersonEmail);
		weChatNotifyForm.setDefaultLangs(defaultLangs);
		return weChatNotifyForm;
	}

	// 由于NotifyContextImp的构造方法未暴露出来,故采用继承办法来实现构造
	private class LweNotifyContext extends NotifyContextImp {

	}

	@SuppressWarnings("unchecked")
	@Override
	protected WechatNotifyContext getExtendContext(NotifyContext context) {
		WechatNotifyContext wechatNotifyContext = context
				.getExtendContext(WechatNotifyContext.class);
		return wechatNotifyContext;
	}

}
