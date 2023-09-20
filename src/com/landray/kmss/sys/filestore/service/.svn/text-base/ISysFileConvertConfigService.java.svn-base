package com.landray.kmss.sys.filestore.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.filestore.forms.SysFileConvertGlobalConfigForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

public interface ISysFileConvertConfigService extends IBaseService {

	public final static String GLOBAL_CONFIG = "SysFileConvertGlobalConfig";

	public String findAppConfigValue(String whereBlock, String hintValue) throws Exception;

	public void save(String enabled, String useHTML) throws Exception;

	public boolean isAttConvertEnable();

	public boolean isOldConvertSuccessUseHTML();

	public void changeConfigStatus(String[] ids, String changeType) throws Exception;

	public ISysAppConfigService getSysAppConfigService();

	public void enableDefaultConvertConfig();

	public SysFileConvertConfig getQueueConfig(SysFileConvertQueue taskQueue);

	public void deleteConfigs(String[] ids) throws Exception;

	public List<SysFileConvertConfig> getSupportHighFidelityConfigs();

	public void saveGlobalInfos(SysFileConvertGlobalConfigForm globalConfigForm);

	public SysFileConvertGlobalConfigForm getGlobalConfigForm();

	public List<SysFileConvertConfig> getConfigsByTypeAndKey(String convertType, String[] convertKeys);

	/**
	 * 清空缓存
	 */
	public void clearCache();
}
