package com.landray.kmss.third.im.kk.model;

import com.landray.kmss.sys.profile.interfaces.AbstractThirdLogin;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkUserService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class KKPcScanLogin extends AbstractThirdLogin {

	private IKkImConfigService kkImConfigService;

	private IKkUserService kkUserService;

	private IKkImConfigService getKkImConfigService() {
		if (kkImConfigService == null) {
			kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	private IKkUserService getKkUserService() {
		if (kkUserService == null) {
			kkUserService = (IKkUserService) SpringBeanUtil.getBean("kkUserService");
		}
		return kkUserService;
	}

	@Override
	public boolean isDefault() throws Exception {
		return true;
	}

	@Override
	public String key() {
		return "kk";
	}

	@Override
	public String name() {
		String kkName = getKkImConfigService().getValuebyKey(KeyConstants.KK_NAME);
		if (StringUtil.isNotNull(kkName)) {
			return kkName;
		}
		return "KK";
	}

	@Override
	public void setName(String name) throws Exception {
		String kkName = getKkImConfigService().getValuebyKey(KeyConstants.KK_NAME);
		if (StringUtil.isNull(kkName)) {
			KkImConfig config = new KkImConfig();
			config.setFdKey(KeyConstants.KK_NAME);
			config.setFdValue(name);
			getKkImConfigService().add(config);
		} else {
			getKkImConfigService().updateValueBykey(KeyConstants.KK_NAME, name);
		}
	}

	@Override
	public boolean cfgEnable() throws Exception {
		String status = getKkImConfigService().getValuebyKey(KeyConstants.KK_CONFIG_SATUS);
		if (StringUtil.isNull(status)) {
			return false;
		}
		return new Boolean(status);
	}

	@Override
	public String iconUrl() throws Exception {
		return null;
	}

	@Override
	public String loginUrl() throws Exception {
		// JSONObject json = getKkUserService().getSign();
		// StringBuffer url = new StringBuffer();
		// url.append(json.getString("outerDomain"));
		// url.append(json.getString("outerDomain").lastIndexOf("/") ==
		// json.getString("outerDomain").length() - 1 ? ""
		// : "/");
		// url.append(KkQrcodeConstants.KK_QRCODE_INDEX);
		// url.append("?sign=").append(json.getString("sign"));
		// url.append("&lang=").append(URLEncoder.encode(ResourceUtil.getLocaleStringByUser()));
		// url.append("&redirectUrl=").append(json.getString("redirectUrl"));
		// return url.toString();
		return ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/third/im/kk/kk_sso/pcScanLogin.jsp";
	}
	
	@Override
	public String qrCodeUrl() throws Exception {
		return "/third/im/kk/kk_sso/import/thirdKKLoginQrCode.jsp";
	}

}
