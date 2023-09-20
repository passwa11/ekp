package com.landray.kmss.third.weixin.mutil.sso;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import org.apache.commons.lang.StringUtils;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.util.StringUtil;

public class WxWorkPcScanLogin extends AbstractThirdLogin {

	@Override
	public String key() {
		Map<String, Map<String, String>> dataMap = WeixinMutilConfig.getWxConfigDataMap();
		StringBuffer sbf = new StringBuffer();
		for (Map.Entry<String, Map<String, String>> map : dataMap.entrySet()) {
			sbf.append(map.getKey()).append("|");
		}
		return sbf.toString();
	}

	@Override
	public String name(String key) {
		return WeixinMutilConfig.newInstance(key).getWxName();
	}

	@Override
    public boolean cfgEnable(String key) throws Exception {
		return new Boolean(WeixinMutilConfig.newInstance(key).getWxEnabled());
	}

	@Override
	public boolean loginEnable(String key) throws Exception {
		return new Boolean(WeixinMutilConfig.newInstance(key).getWxPcScanLoginEnabled());
	}

	@Override
	public boolean setLoginEnable(boolean enable, String key) throws Exception {
		WeixinMutilConfig weixinConfig = WeixinMutilConfig.newInstance(key);
		weixinConfig.setWxPcScanLoginEnabled(String.valueOf(enable));
		weixinConfig.save();
		return true;
	}

	@Override
	public String iconUrl() throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		return request.getContextPath() + "/third/weixin/resource/images/scanCode-wxwork.png";
	}

	@Override
	public String loginUrl(String key) throws Exception {
		WeixinMutilConfig config = WeixinMutilConfig.newInstance(key);
		String url = "https://open.mutil.weixin.qq.com/wwopen/sso/qrConnect?";
		String domainName = config.getWxDomain();
		String viewUrl = "/third/wxwork/pcScanLogin.do?method=service&fdwxkey=" + key;
		if (StringUtils.isNotEmpty(domainName)) {
			viewUrl = domainName + viewUrl;
		} else {
			viewUrl = StringUtil.formatUrl(viewUrl);
		}
		String redirect = URLEncoder.encode(viewUrl,"UTF-8");
		String agentId = config.getWxSSOAgentId();
		if(StringUtil.isNull(agentId)) {
            agentId = config.getWxAgentid();
        }
		url = url + "appid=" + config.getWxCorpid() + "&agentid="
				+ agentId + "&redirect_uri=" + redirect + "&state=state";
		return url;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public String loginUrl() throws Exception {
		return null;
	}

}
