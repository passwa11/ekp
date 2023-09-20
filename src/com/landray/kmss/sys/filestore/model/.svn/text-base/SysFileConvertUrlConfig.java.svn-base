package com.landray.kmss.sys.filestore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysFileConvertUrlConfig extends BaseAppConfig {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertUrlConfig.class);
	
	public SysFileConvertUrlConfig() throws Exception {
		super();
	}
	
	public static SysFileConvertUrlConfig newInstance() {
		SysFileConvertUrlConfig config = null;
		try {
			config = new SysFileConvertUrlConfig();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return config;
	}
	
	/**
	 * 是否开启清理历史转换附件
	 */
	public String getKmssConvertFileClearEnabled() {
		String kmssConvertFileClearEnabled = getValue("kmssConvertFileClearEnabled");
		if (StringUtil.isNull(kmssConvertFileClearEnabled)) {
			kmssConvertFileClearEnabled = "false";
		}
		return kmssConvertFileClearEnabled;
	}
	
	public int getKmssConvertFileKeep() {
		String kmssConvertFileKeep = getValue("kmssConvertFileKeep");
		if (StringUtil.isNull(kmssConvertFileKeep)) {
			kmssConvertFileKeep = "36";
		}
		return Integer.parseInt(kmssConvertFileKeep);
	}
	
	public int getKmssConvertFileBatchNum() {
		String kmssConvertFileClearBatchNum = getValue("kmssConvertFileClearBatchNum");
		if (StringUtil.isNull(kmssConvertFileClearBatchNum)) {
			kmssConvertFileClearBatchNum = "2000";
		}
		return Integer.parseInt(kmssConvertFileClearBatchNum);
	}
	

	@Override
    public String getJSPUrl() {
		return "/sys/filestore/sys_filestore_converturl_config.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-filestore:sysFilestore.tree.convertUrl.displayConfig");
	}
	
	

}
