package com.landray.kmss.third.weixin.sso;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 微信扫码登陆拓展bean
 */
public class WxPcScanLogin extends AbstractThirdLogin {

	@Override
	public String key() {
		return "weixin";
	}

	@Override
	public String name() {
		return ResourceUtil.getString("third.wx.pcscan", "third-weixin");
	}

	@Override
	public boolean cfgEnable() throws Exception {
		return new Boolean(WeixinConfig.newInstance().getWxEnabled());
	}

	@Override
	public boolean loginEnable() throws Exception {
		return new Boolean(WeixinConfig.newInstance().getWxPcScanLoginEnabled());
	}

	@Override
	public boolean setLoginEnable(boolean enable) throws Exception {
		WeixinConfig weixinConfig = WeixinConfig.newInstance();
		weixinConfig.setWxPcScanLoginEnabled(String.valueOf(enable));
		weixinConfig.save();
		return true;
	}

	@Override
	public String iconUrl() throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		return request.getContextPath() + "/third/weixin/resource/images/scanCode-weixin.png";
	}

	@Override
	public String loginUrl() throws Exception {
		String url = "https://qy.weixin.qq.com/cgi-bin/loginpage?";
		String corpId = WeixinConfig.newInstance().getWxCorpid();
		String domainName = WeixinConfig.newInstance().getWxDomain();
		String viewUrl = "/third/wx/pcScanLogin.do?method=service";
		if (StringUtils.isNotEmpty(domainName)) {
			viewUrl = domainName + viewUrl;
		} else {
			viewUrl = StringUtil.formatUrl(viewUrl);
		}
		String redirect = URLEncoder.encode(viewUrl, "UTF-8");
		url = url + "corp_id=" + corpId + "&redirect_uri=" + redirect + "&usertype=member";
		return url;
	}

}
