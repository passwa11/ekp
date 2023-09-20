package com.landray.kmss.third.feishu.sso;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.util.ResourceUtil;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FeishuPcScanLogin extends AbstractThirdLogin {

	private static final Logger log = LoggerFactory.getLogger(FeishuPcScanLogin.class);
	@Override
	public String key() {
		return "feishu";
	}

	@Override
	public String name() {
		return ResourceUtil.getString("third.feishu.pcscan", "third-feishu");
	}

	@Override
	public boolean cfgEnable() throws Exception {
		return new Boolean(ThirdFeishuConfig.newInstance().getFeishuEnabled());
	}

	@Override
	public boolean loginEnable() throws Exception {
		String feishuEnabled = ThirdFeishuConfig.newInstance().getFeishuEnabled();
		String pcScanLoginEnabled = ThirdFeishuConfig.newInstance().getPcScanLoginEnabled();
		if("true".equals(feishuEnabled) && "true".equals(pcScanLoginEnabled)){
			return true;
		}
		return false;
	}

	@Override
	public boolean setLoginEnable(boolean enable) throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		config.setPcScanLoginEnabled(String.valueOf(enable));
		config.save();
		return true;
	}

	@Override
	public String iconUrl() throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		return request.getContextPath() + "/third/feishu/resource/images/scanCode-feishu.png";
	}
	
	@Override
	public boolean isDefaultLang() throws Exception {
		return false;
	}
	
	@Override
	public String loginUrl() throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String url = ThirdFeishuConstant.API_URL + "/authen/v1/index?";
		url += "app_id=" + config.getFeishuAppid();

		try {
			String ekpPath = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			ekpPath = ekpPath + "/third/feishu/ssoRedirect.jsp?to="
					+ new String(Base64.encode(ekpPath.getBytes()),
					"UTF-8");
			url += "&redirect_uri="
					+ URLEncoder.encode(ekpPath,
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		log.info("飞书 sso redirect url = " + url);
		return url;
	}
	

	@Override
	public String qrCodeUrl() throws Exception {
		return "/third/feishu/import/thirdFeishuLoginQrCode.jsp";
	}
}
