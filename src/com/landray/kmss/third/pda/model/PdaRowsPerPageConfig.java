package com.landray.kmss.third.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * pda列表页面每页显示行数配置
 * 
 */

public class PdaRowsPerPageConfig extends BaseAppConfig {

	public PdaRowsPerPageConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/third/pda/pda_rows_per_page_config/pdaRowsPerPageConfig_edit.jsp";
	}

	/**
	 * 每页显示行数
	 */
	public String getFdRowsNumber() {
		return getValue("fdRowsNumber");
	}

	public void setFdRowsNumber(String fdRowsNumber) {
		setValue("fdRowsNumber", fdRowsNumber);
	}
	
	public boolean getFdAttDownload() {
		return "true".equalsIgnoreCase(getValue("fdAttDownLoadEnabled"));
	}

	public void setFdAttDownload(boolean fdAttDownLoadEnabled) {
		setValue("fdAttDownLoadEnabled", String.valueOf(fdAttDownLoadEnabled));
	}
	
	/**
	 * 异构系统解锁链接
	 */
	public String[] getFdExtendsUrl() {
		String valStr = getValue("fdExtendsUrl");
		List<String> valList = new ArrayList<String>();
		int i = 1;
		while (StringUtil.isNotNull(valStr)) {
			valList.add(valStr);
			valStr = getValue("fdExtendsUrl_" + i);
			i++;
		}
		if (valList.isEmpty()) {
            return null;
        }
		return valList.toArray(new String[valList.size()]);
	}

	public void setFdExtendsUrl(String[] fdExtendsUrl) {
		if (fdExtendsUrl != null) {
			int m = 0;
			for (int i = 0; i < fdExtendsUrl.length; i++) {
				if (StringUtil.isNotNull(fdExtendsUrl[i])
						&& StringUtil.isNotNull(fdExtendsUrl[i].trim())) {
					setValue("fdExtendsUrl" + (m == 0 ? "" : ("_" + m)),
							fdExtendsUrl[i]);
					m++;
				}
			}
		} else {
			String valStr = getValue("fdExtendsUrl");
			int j = 0;
			while (StringUtil.isNotNull(valStr)) {
				setValue("fdExtendsUrl" + (j == 0 ? "" : ("_" + j)), null);
				j++;
				valStr = getValue("fdExtendsUrl_" + j);
			}
		}
	}
	
	/**
	 * 消息接收时段 样例：08:00-22:00
	 */
	public String getMsgRevTimeInterval() {
		 return getValue("msgRevTimeInterval");
	}

	public void setMsgRevTimeInterval(String msgRevTimeInterval) {
		setValue("msgRevTimeInterval", msgRevTimeInterval);
	}
	
	/**
	 * andriod消息推送对象
	 * 在线用户:broadcastAndriod=Y,所有用户:broadcastAndriod=A,指定用户:broadcastAndriod=其他任何值
	 */
	public String getBroadcastAndriod() {
		 return getValue("broadcastAndriod");
	}

	public void setBroadcastAndriod(String broadcastAndriod) {
		setValue("broadcastAndriod", broadcastAndriod);
	}
	
	/**
	 * 安卓消息推送apiKey
	 * 缺省值为：1234567890
	 */
	public String getApiKeyAndriod() {
		 return getValue("apiKeyAndriod");
	}

	public void setApiKeyAndriod(String apiKeyAndriod) {
		setValue("apiKeyAndriod", apiKeyAndriod);
	}
	
	/**
	 * 安卓消息推送URI
	 * http://www.dokdocorea.com, geo:37.24,131.86, tel:111-222-3333
	 */
	public String getUriAndriod() {
		 return getValue("uriAndriod");
	}

	public void setUriAndriod(String uriAndriod) {
		setValue("uriAndriod", uriAndriod);
	}
	
	/**
	 * 安卓消息推送服务URL
	 * 样例：http://192.168.5.108:8080/Androidpn/notification.do?action=send
	 */
	public String getPushMsgServerUrlAndriod() {
		 return getValue("pushMsgServerUrlAndriod");
	}

	public void setPushMsgServerUrlAndriod(String pushMsgServerUrlAndriod) {
		setValue("pushMsgServerUrlAndriod", pushMsgServerUrlAndriod);
	}
	
	/**
	 * 安卓消息推送xmppHost地址
	 * 样例：192.168.5.108
	 */
	public String getPushMsgServerIpAndriod() {
		 return getValue("pushMsgServerIpAndriod");
	}

	public void setPushMsgServerIpAndriod(String pushMsgServerIpAndriod) {
		setValue("pushMsgServerIpAndriod", pushMsgServerIpAndriod);
	}
	
	/**
	 * 安卓消息推送服务xmppPort端口
	 * 样例：5222
	 */
	public String getPushMsgServerPortAndriod() {
		 return getValue("pushMsgServerPortAndriod");
	}

	public void setPushMsgServerPortAndriod(String pushMsgServerPortAndriod) {
		setValue("pushMsgServerPortAndriod", pushMsgServerPortAndriod);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("third-pda:pdaGeneralConfig.rowsPerPageConfig");
	}
	
}
