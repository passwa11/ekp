package com.landray.kmss.third.weixin.work.sso;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class WxWorkPcScanLogin extends AbstractThirdLogin {

	@Override
	public String key() {
		return "wxwork";
	}

	@Override
	public String name() {
		return ResourceUtil.getString("third.weixin.work.pcscan", "third-weixin-work");
	}

	@Override
	public boolean cfgEnable() throws Exception {
		return new Boolean(WeixinWorkConfig.newInstance().getWxEnabled());
	}

	@Override
	public boolean loginEnable() throws Exception {
		return new Boolean(WeixinWorkConfig.newInstance().getWxPcScanLoginEnabled());
	}

	@Override
	public boolean setLoginEnable(boolean enable) throws Exception {
		WeixinWorkConfig weixinConfig = WeixinWorkConfig.newInstance();
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
	public String loginUrl() throws Exception {
		String url = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?";
		String prefix_url = WeixinWorkConfig.newInstance().getWxApiUrl();
		if (StringUtil.isNotNull(prefix_url)) {
			// 私有化部署
			prefix_url = prefix_url.replace("/cgi-bin", "");
			if (prefix_url.endsWith("/")) {
				prefix_url = prefix_url.substring(0, prefix_url.length() - 1);
			}
			url = prefix_url + "/wwopen/sso/qrConnect?";
		}
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		String viewUrl = "/third/wxwork/pcScanLogin.do?method=service";
		if (StringUtils.isNotEmpty(domainName)) {
			viewUrl = domainName + viewUrl;
		} else {
			viewUrl = StringUtil.formatUrl(viewUrl);
		}
		String redirect = URLEncoder.encode(viewUrl,"UTF-8");
		String agentId = WeixinWorkConfig.newInstance().getWxSSOAgentId();
		if(StringUtil.isNull(agentId)) {
            agentId = WeixinWorkConfig.newInstance().getWxAgentid();
        }
		url = url + "appid=" + WeixinWorkConfig.newInstance().getWxCorpid() + "&agentid="
				+ agentId + "&redirect_uri=" + redirect + "&state=state";
		return url;
	}

}
