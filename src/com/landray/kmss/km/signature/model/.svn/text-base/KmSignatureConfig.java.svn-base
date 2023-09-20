package com.landray.kmss.km.signature.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 参数设置
 * 
 * @author 
 * @version 1.0 2012-07-03
 */
public class KmSignatureConfig extends BaseAppConfig {
	public KmSignatureConfig() throws Exception {
		super();
		if(StringUtil.isNull(getFdIsAutoSign())){
			setValue("fdIsAutoSign", "0");
		}
	}

	@Override
    public String getJSPUrl() {
		return "/km/signature/km_signature_config/kmSignatureConfig_edit.jsp";
	}
	
		
	// 是否开启自动免签
	public String getFdIsAutoSign() {
		return getValue("fdIsAutoSign");
	}

	public void setFdIsAutoSign(String fdIsAutoSign) {
		setValue("fdIsAutoSign", fdIsAutoSign);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-signature:table.kmSignatureConfig");
	}

}
