package com.landray.kmss.sys.zone.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysZonePrivateConfig  extends BaseAppConfig {

	public SysZonePrivateConfig() throws Exception {
		super();
		//联系方式
		setDefault("isContactPrivate", "0");
		//岗位部门信息
		setDefault("isDepInfoPrivate", "0");
		//汇报关系 
		setDefault("isRelationshipPrivate", "0");
		//部门同事
		setDefault("isWorkmatePrivate", "0");
		// 二维码
		setDefault("isQrCodePrivate", "0");
	}
	
	private void setDefault(String name, String value) {
		String thisValue = super.getValue(name);
		if(StringUtil.isNull(thisValue)) {
			super.setValue(name, value);
		}
	}
	
	@Override
	public String getJSPUrl() {
		return "/sys/zone/sys_zone_private_config/sysZonePrivateConfig_edit.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-zone:sysZonePrivate.privacy.setting");
	}
	
	public boolean hideQrCode() {
		return "1".equals(getValue("isQrCodePrivate"));
	}
}
