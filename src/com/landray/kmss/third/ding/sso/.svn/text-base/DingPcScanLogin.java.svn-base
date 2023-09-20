package com.landray.kmss.third.ding.sso;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class DingPcScanLogin extends AbstractThirdLogin {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DingPcScanLogin.class);
	@Override
	public String key() {
		return "ding";
	}

	@Override
	public String name() {
		return ResourceUtil.getString("third.ding.pcscan", "third-ding");
	}

	@Override
	public boolean cfgEnable() throws Exception {
		return new Boolean(DingConfig.newInstance().getDingEnabled());
	}

	@Override
	public boolean loginEnable() throws Exception {
		return new Boolean(DingConfig.newInstance().getDingPcScanLoginEnabled());
	}

	@Override
	public boolean setLoginEnable(boolean enable) throws Exception {
		DingConfig config = DingConfig.newInstance();
		config.setDingPcScanLoginEnabled(String.valueOf(enable));
		config.save();
		return true;
	}

	@Override
	public String iconUrl() throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		return request.getContextPath() + "/third/ding/resource/images/scanCode-ding.png";
	}
	
	@Override
	public boolean isDefaultLang() throws Exception {
		return false;
	}
	
	@Override
	public String loginUrl() throws Exception {
		String url = DingConstant.DING_PREFIX + "/connect/qrconnect?appid="
				+ DingConfig.newInstance().getDingPcScanappId()
				+ "&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=";
		String domainName = DingConfig.newInstance().getDingDomain();
    	String viewUrl = "/third/ding/pcScanLogin.do?method=service";
		if (StringUtils.isNotEmpty(domainName)) {
			viewUrl = domainName + viewUrl;
		} else {
			viewUrl = StringUtil.formatUrl(viewUrl);
		}
		url += URLEncoder.encode(viewUrl, "UTF-8");
		log.debug("钉钉接口：" + url);
		return url;
	}
	
	@Override
    public String loginUrl(HttpServletRequest request) throws Exception {
		String url = DingConstant.DING_PREFIX + "/connect/qrconnect?appid="
				+ DingConfig.newInstance().getDingPcScanappId()
				+ "&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=";
		String domainName = DingConfig.newInstance().getDingDomain();
		String viewUrl = "/third/ding/pcScanLogin.do?method=service";
		if (StringUtils.isNotEmpty(domainName)) {
			viewUrl = domainName + viewUrl;
		} else { 
			viewUrl = StringUtil.formatUrl(viewUrl); 
		}
		String j_lang = request.getParameter("j_lang");
		if(StringUtil.isNotNull(j_lang)) {
            viewUrl += "&j_lang="+request.getParameter("j_lang");
        }
		url += URLEncoder.encode(viewUrl, "UTF-8");
		log.debug("钉钉接口：" + url);
		return url;
	} 
	
	@Override
	public String qrCodeUrl() throws Exception {
		return "/third/ding/import/thirdDingLoginQrCode.jsp";
	}
}
