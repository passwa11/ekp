package com.landray.kmss.sys.filestore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysFileConvertClearConfig extends BaseAppConfig {

	private static final long serialVersionUID = -895352177504775735L;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertClearConfig.class);

	/**
	 * 调用处可以直接调用此方法（统一处理异常），也可以使用new PasswordSecurityConfig()（异常需要自行处理）
	 */
	public static SysFileConvertClearConfig newInstance() {
		SysFileConvertClearConfig config = null;
		try {
			config = new SysFileConvertClearConfig();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return config;
	}

	/**
	 * 是否开启清理历史转换文件
	 */
	public String getKmssConvertFileClearEnabled() {
		String kmssConvertFileClearEnabled = getValue("kmssConvertFileClearEnabled");
		if (StringUtil.isNull(kmssConvertFileClearEnabled)) {
			kmssConvertFileClearEnabled = "false";
		}
		return kmssConvertFileClearEnabled;
	}
	
	/**
	 * 清理多少个月前的历史转换文件
	 */
	public String getKmssConvertFileClearMonth() {
		String kmssConvertFileClearMonth = getValue("kmssConvertFileClearMonth");
		if (StringUtil.isNull(kmssConvertFileClearMonth)) {
			kmssConvertFileClearMonth = "36";
		}
		return kmssConvertFileClearMonth;
	}
	
	/**
	 * 每次清理文件的个数
	 */
	public String getKmssConvertFileClearBatchNum() {
		String kmssConvertFileClearBatchNum = getValue("kmssConvertFileClearBatchNum");
		if (StringUtil.isNull(kmssConvertFileClearBatchNum)) {
			kmssConvertFileClearBatchNum = "500";
		}
		return kmssConvertFileClearBatchNum;
	}

	public SysFileConvertClearConfig() throws Exception {
		super();
		String kmssConvertFileClearMonth = getValue("kmssConvertFileClearMonth");
		if (StringUtil.isNull(kmssConvertFileClearMonth)) {
			kmssConvertFileClearMonth = "36";
		}
		super.setValue("kmssConvertFileClearMonth", kmssConvertFileClearMonth);
		
		String kmssConvertFileClearBatchNum = getValue("kmssConvertFileClearBatchNum");
		if (StringUtil.isNull(kmssConvertFileClearBatchNum)) {
			kmssConvertFileClearBatchNum = "500";
		}
		super.setValue("kmssConvertFileClearBatchNum", kmssConvertFileClearBatchNum);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getJSPUrl() {
		// TODO Auto-generated method stub
		return "/sys/filestore/sys_filestore_convertclear_config.jsp";
	}
	
	@Override
    public String getModelDesc() {
		return ResourceUtil.getString("sys-filestore:sysFilestore.tree.convertClear.displayConfig");
	}

}
