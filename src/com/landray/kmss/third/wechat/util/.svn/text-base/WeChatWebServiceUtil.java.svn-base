package com.landray.kmss.third.wechat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyConfigService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.provider.ISysNotifyProvider;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.wechat.forms.WeChatNotifyForm;
import com.landray.kmss.util.ResourceUtil;

/**
 * @author:kezm
 * @date :2014-7-1上午10:06:59
 */
public class WeChatWebServiceUtil {
	private static HashMap providerMap = new HashMap();
	private static final String EXTENSION_POINT = "com.landray.kmss.sys.notify";
	private static final String ITEM_NAME = "notifyType";

	private static final String PARAM_KEY = "key";

	private static final String PARAM_BEAN = "bean";
	
	public static String  doProvider(WeChatNotifyForm weChatNotifyForm,NotifyContext notifyContext,String fromType) throws Exception{
		Map<String,Boolean> sendTypeMap = new HashMap<String,Boolean>();
		List<String> personInforList = weChatNotifyForm.getPersonList();
		//存放发送失败的人员
		List<String> resultList=weChatNotifyForm.getResultList();
		List<SysOrgPerson> personList = new ArrayList<SysOrgPerson>();
		Map<String,String> resultMap = new HashMap<String,String>();
	
		if (resultList != null && resultList.size() > 0) {
			for (String result : resultList) {
				String[] resultArray = result.split(";");
				resultMap.put(resultArray[0], resultArray[1]);
			}
		}
		
		// 获取是否开启了发送邮件和发送短信
		sendTypeMap = getSendTyps(weChatNotifyForm);
		if (sendTypeMap != null && sendTypeMap.size() > 0) {
			boolean kmssEmail = sendTypeMap.get("kmssEmail");
			boolean kmssMobile  = sendTypeMap.get("kmssMobile");
			boolean lweTodo = sendTypeMap.get("lweTodo");
			boolean lweEmail = sendTypeMap.get("lweEmail");
			boolean lweMobile = sendTypeMap.get("lweMobile");
			
			if (personInforList != null && personInforList.size() > 0) {
				
				//摘取出需要接受email,短消息的用户
				for (int i = 0; i < personInforList.size(); i++) {
					SysOrgPerson sysOrgPerson = new SysOrgPerson();
					JSONObject res = JSONObject.fromObject(personInforList.get(i));
					String userId = res.getString("userId");
					
					if ("1".equals(fromType)) {
						// fromType =1 表示需要接收email和短消息的人员是发送微信客户端失败的用户,
						if ("error".equals(resultMap.get(userId))) {
							sysOrgPerson.setFdId(userId);
							sysOrgPerson.setFdName(res.getString("userName"));
							sysOrgPerson.setFdEmail(res.getString("email"));
							sysOrgPerson.setFdMobileNo(res.getString("mobileNo"));
							personList.add(sysOrgPerson);
						}
					} else if ("2".equals(fromType)) {
						// fromType=2 表示需要接收email和短消息的人员是该流程中的所有的用户(ekp发送数据到lwe的过程中lwe接收失败)
						sysOrgPerson.setFdId(userId);
						sysOrgPerson.setFdName(res.getString("userName"));
						sysOrgPerson.setFdEmail(res.getString("email"));
						sysOrgPerson.setFdMobileNo(res.getString("mobileNo"));
						personList.add(sysOrgPerson);
					}
				}

				if (personList != null && personList.size() > 0) {
					notifyContext.setNotifyTarget(personList);
					notifyContext.setLink(weChatNotifyForm.getLink());
					notifyContext.setLinkSubject(weChatNotifyForm
							.getLinkSubject());
					notifyContext.setSubject(weChatNotifyForm.getSubject());
					notifyContext.setContent(weChatNotifyForm.getContent());
					notifyContext.setFdBundle(weChatNotifyForm.getFdBundle());
					notifyContext.setFdReplaceText(weChatNotifyForm
							.getReplaceText());

					Object rtnVal = getProviderMap().get("email");
					if (kmssEmail && lweTodo && lweEmail) {
						notifyContext.setNotifyType("email");
						//notifyContext.setMailFormat(weChatNotifyForm.getMailFormat());
						//notifyContext.setMailFrom(weChatNotifyFormgetFromPersonEmail());

						if (rtnVal != null) {
							ISysNotifyProvider provider = (ISysNotifyProvider) rtnVal;
							provider.send(notifyContext);
						}
					}

					if (kmssMobile && lweTodo && lweMobile) {
						notifyContext.setNotifyType("mobile");
						Object rtnVal2 = getProviderMap().get("mobile");
						if (rtnVal2 != null) {
							ISysNotifyProvider provider = (ISysNotifyProvider) rtnVal2;
							provider.send(notifyContext);
						}
					}
				}
			}
		}

		return null;
	}
	
	
	
	/**
	 * 获取是否启用了发送邮件和发送短信选项
	 * 
	 * @param weChatNotifyForm
	 * @return
	 */
	public static Map<String,Boolean> getSendTyps(WeChatNotifyForm weChatNotifyForm) {
		List<String> sendTypeList = new ArrayList<String>();
		Map<String,Boolean> sendTypeMap = new HashMap<String,Boolean>();
		String isEmailEnabled = ResourceUtil
				.getKmssConfigString("kmss.notify.type.email.enabled");

		if ("true".equals(isEmailEnabled)) {
			sendTypeMap.put("kmssEmail", true);
		} else {
			sendTypeMap.put("kmssEmail", false);
		}

		String isLweEmailEnabled = ResourceUtil.getKmssConfigString("lwe.notify.type.emil.enabled");
		if ("true".equals(isLweEmailEnabled)) {
			sendTypeMap.put("lweEmail", true);
		}else{
			sendTypeMap.put("lweEmail", false);
		}

		String isMobileEnabled = ResourceUtil.getKmssConfigString("kmss.notify.type.mobile.enabled");
		if ("true".equals(isMobileEnabled)) {
			sendTypeMap.put("kmssMobile", true);
		} else {
			sendTypeMap.put("kmssMobile", false);
		}
		
		String isLweMobileEnabled = ResourceUtil.getKmssConfigString("lwe.notify.type.mobile.enabled");
		if ("true".equals(isLweMobileEnabled)) {
			sendTypeMap.put("lweMobile", true);
		}else {
			sendTypeMap.put("lweMobile", false);
		}
		
		//微信是否启用
		String isLweTodoEnabled = ResourceUtil.getKmssConfigString("lwe.notify.type.todo.enabled");
		if ("true".equals(isLweTodoEnabled)) {
			sendTypeMap.put("lweTodo", true);
		}else {
			sendTypeMap.put("lweTodo", false);
		}
		
		
		return sendTypeMap;
	}
	
	
	public static HashMap getProviderMap() {
		if (providerMap == null || providerMap.isEmpty()) {
			// 获得所有通知机制的扩展
			IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT,
					"*", ITEM_NAME);
			if (extensions == null || extensions.length == 0) {
				return providerMap;
			}
			for (int i = 0; i < extensions.length; i++) {
				//获取配置中心的配置值，过滤已开启的扩展点
				ISysNotifyConfigService configService = Plugin.getParamValue(extensions[i],"service");
				if(configService !=null){
					boolean enable = configService.getNotifyType();
					if(!enable) {
                        continue;
                    }
				}
				// 获得通知方式的key与对应service
				String key = (String) Plugin.getParamValue(extensions[i],
						PARAM_KEY);
				providerMap.put(key, Plugin.getParamValue(extensions[i],
						PARAM_BEAN));

			}
		}
		return providerMap;
	}
	
	
}
