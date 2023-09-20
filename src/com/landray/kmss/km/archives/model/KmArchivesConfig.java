package com.landray.kmss.km.archives.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.sso.client.oracle.StringUtil;

/**
  * 档案默认参数
  */
public class KmArchivesConfig extends BaseAppConfig {

	public KmArchivesConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getJSPUrl() {
		return "/km/archives/km_archives_config/kmArchivesConfig_edit.jsp";
	}

    /**
     * 档案即将到期提醒
     */
	public String getFdSoonExpireDate() {
		return getValue("fdSoonExpireDate");
    }

    /**
     * 档案即将到期提醒
     */
	public void setFdSoonExpireDate(String fdSoonExpireDate) {
		setValue("fdSoonExpireDate", fdSoonExpireDate);
    }

    /**
     * 档案归还提前提醒
     */
	public String getFdEarlyReturnDate() {
		return getValue("fdEarlyReturnDate");
    }

    /**
     * 档案归还提前提醒
     */
	public void setFdEarlyReturnDate(String fdEarlyReturnDate) {
		setValue("fdEarlyReturnDate", fdEarlyReturnDate);
    }

    /**
     * 最大续借天数
     */
	public String getFdMaxRenewDate() {
		return getValue("fdMaxRenewDate");
    }

    /**
     * 最大续借天数
     */
	public void setFdMaxRenewDate(String fdMaxRenewDate) {
		setValue("fdEarlyReturnDate", fdMaxRenewDate);
    }

    /**
     * 默认授权范围
     */
    public String getFdDefaultRange() {
		String fdDefaultRange = getValue("fdDefaultRange");
		if (StringUtil.isNotNull(fdDefaultRange)) {
			return fdDefaultRange;
		}
		return "";
    }

    /**
     * 默认授权范围
     */
    public void setFdDefaultRange(String fdDefaultRange) {
		setValue("fdDefaultRange", fdDefaultRange);
    }

    /**
     * 默认提醒方式
     */
    public String getFdDefaultRemind() {
		return getValue("fdDefaultRemind");
    }

    /**
     * 默认提醒方式
     */
    public void setFdDefaultRemind(String fdDefaultRemind) {
		setValue("fdDefaultRemind", fdDefaultRemind);
    }

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-archives:table.kmArchivesConfig");
	}

}
