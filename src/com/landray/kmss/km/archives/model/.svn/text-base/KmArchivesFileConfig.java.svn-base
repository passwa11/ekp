package com.landray.kmss.km.archives.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;

/**
 * 档案归档配置
 */
public class KmArchivesFileConfig extends BaseAppConfig {

	public KmArchivesFileConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getJSPUrl() {
		return "/km/archives/km_archives_config/kmArchivesFileConfig_edit.jsp";
	}

    /**
	 * 是否开启归档功能
	 */
	public String getFdStartFile() {
		return getValue("fdStartFile");
    }

    /**
	 * 是否开启归档功能
	 */
	public void setFdStartFile(String fdStartFile) {
		setValue("fdStartFile", fdStartFile);
    }

	/**
	 * 归档模块
	 */
	public String getFdFileModels() {
		return getValue("fdFileModels");
	}

	/**
	 * 归档模块
	 */
	public void setFdFileModels(String fdFileModels) {
		setValue("fdFileModels", fdFileModels);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-archives:table.kmArchivesFileConfig");
	}

}
