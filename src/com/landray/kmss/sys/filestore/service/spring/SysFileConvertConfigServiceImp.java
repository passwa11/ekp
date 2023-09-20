package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.CacheLoader;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.cache.FileConvertConfigCache;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.filestore.forms.SysFileConvertGlobalConfigForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

@SuppressWarnings("unchecked")
public class SysFileConvertConfigServiceImp extends BaseServiceImp implements ISysFileConvertConfigService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertConfigServiceImp.class);

	private final KmssCache globalConfigCache;

	public SysFileConvertConfigServiceImp() {
		CacheConfig config = CacheConfig.get(GLOBAL_CONFIG, true);
		config.returnNullWhenLoading = false;
		config.setCacheLoader(new CacheLoader() {
			@Override
			public Object load(String key) throws Exception {
				// 查询不带权限数据
				return loadGlobalConfigForm();
			}
		});
		globalConfigCache = new KmssCache(SysFileConvertGlobalConfigForm.class, config);
	}

	private ISysAppConfigService sysAppConfigService;

	@Override
    public ISysAppConfigService getSysAppConfigService() {
		return sysAppConfigService;
	}

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	@Override
	public void save(String enabled, String useHTML) throws Exception {
		SysAppConfig enableConfig = new SysAppConfig();
		enableConfig.setFdKey("attconvert.enabled");
		enableConfig.setFdValue(enabled);
		List<String> fdIdList = sysAppConfigService.findValue("fdId", "fdKey='attconvert.enabled'", "");
		if (fdIdList == null || fdIdList.size() == 0) {
			sysAppConfigService.add(enableConfig);
		} else {
			enableConfig.setFdId(fdIdList.get(0));
			sysAppConfigService.update(enableConfig);
		}
		SysAppConfig useHTMLConfig = new SysAppConfig();
		useHTMLConfig.setFdKey("attconvert.oldsuccess.usehtmlview");
		useHTMLConfig.setFdValue(useHTML);
		List<String> fdIdsList = sysAppConfigService.findValue("fdId", "fdKey='attconvert.oldsuccess.usehtmlview'", "");
		if (fdIdsList == null || fdIdsList.size() == 0) {
			sysAppConfigService.add(useHTMLConfig);
		} else {
			useHTMLConfig.setFdId(fdIdsList.get(0));
			sysAppConfigService.update(useHTMLConfig);
		}
	}

	@Override
	public String findAppConfigValue(String whereBlock, String hintValue) throws Exception {
		String keyBlock = whereBlock;
		if(StringUtil.isNull(keyBlock)) {
			logger.warn("业务模块没有传入where条件.");
			return hintValue;
		}

		String[] keys = keyBlock.split("=");
		if(keys.length <= 0) {
			logger.warn("业务模块传入的where条件格式不对:", whereBlock);
			return hintValue;
		}

		String key = keys[1].replace("\'", "").trim();
		// 先查询缓存中是否有数据
		String value = FileConvertConfigCache.getInstance().get(key);
		if(logger.isDebugEnabled()) {
			logger.debug("从缓存中查询key:{}, value:{}", key, value);
		}

		if(StringUtil.isNotNull(value)) {
			return value;
		}

		//如果缓存没有，则查询数据库
		List<String> valueList = sysAppConfigService.findValue("fdValue", whereBlock, "");
		if (valueList == null || valueList.size() == 0 || StringUtil.isNull(valueList.get(0))) {
			FileConvertConfigCache.getInstance().put(key, hintValue);
			return hintValue;
		}

		FileConvertConfigCache.getInstance().put(key, valueList.get(0));
		return valueList.get(0);
	}

	@Override
	public boolean isAttConvertEnable() {
		String attConvertEnabledValue = "";
		try {
			attConvertEnabledValue = findAppConfigValue("fdKey ='attconvert.enabled'", "false");
		} catch (Exception e) {
			attConvertEnabledValue = "false";
		}
		return "true".equals(attConvertEnabledValue);
	}

	@Override
	public boolean isOldConvertSuccessUseHTML() {
		String attConvertUseHTMLValue = "";
		try {
			attConvertUseHTMLValue = findAppConfigValue("fdKey ='attconvert.oldsuccess.usehtmlview'", "false");
		} catch (Exception e) {
			attConvertUseHTMLValue = "false";
		}
		return "true".equals(attConvertUseHTMLValue);
	}

	@Override
	public void changeConfigStatus(String[] ids, String changeType) throws Exception {
		String[] statusIds = ids;
		String enabled = "true";
		if (changeType.contains("disable")) {
			enabled = "false";
		}
		if (changeType.contains("enable")) {
			enabled = "true";
		}
		if (changeType.contains("all")) {
			List<Object> fdIds = findValue("fdId", "", "");
			statusIds = new String[fdIds.size()];
			for (int i = 0; i < fdIds.size(); i++) {
				statusIds[i] = (String) fdIds.get(i);
			}
		}
		for (String id : statusIds) {
			SysFileConvertConfig config = (SysFileConvertConfig) findByPrimaryKey(id);
			config.setFdStatus("true".equals(enabled) ? "1" : "0");
			update(config);
		}
	}

	@Override
	public void enableDefaultConvertConfig() {
		TransactionStatus newTran = null;
		try {
			newTran = TransactionUtils.beginNewTransaction();
			String attConverttConfigSetDefault = "";
			try {
				attConverttConfigSetDefault = findAppConfigValue("fdKey ='attconvert.config.setdefault'", "false");
			} catch (Throwable throwable) {
				attConverttConfigSetDefault = "false";
			}
			ensureNewConfig();
			if ("false".equals(attConverttConfigSetDefault)) {
				logger.debug("启用默认");
				try {
					save("true", "true");
					List<SysFileConvertConfig> allConfig = findList("", "");
					if (allConfig != null && allConfig.size() > 0) {
						for (SysFileConvertConfig item : allConfig) {
							item.setFdStatus("1");
							if (item.getFdFileExtName().contains("doc")) {
								item.setFdHighFidelity("1");
							}
							update(item);
						}
					} else {
						importIntialConfig();
					}
					try {
						SysAppConfig defaultConfig = new SysAppConfig();
						defaultConfig.setFdKey("attconvert.config.setdefault");
						defaultConfig.setFdValue("true");
						List<String> fdIdList = sysAppConfigService.findValue("fdId",
								"fdKey='attconvert.config.setdefault'", "");
						if (fdIdList == null || fdIdList.size() == 0) {
							sysAppConfigService.add(defaultConfig);
						} else {
							defaultConfig.setFdId(fdIdList.get(0));
							sysAppConfigService.update(defaultConfig);
						}
					} catch (Exception e) {
						logger.info("设置已经默认配置出错", e);
					}
				} catch (Exception e) {
					logger.info("启用默认配置出错", e);
				}
			}
			TransactionUtils.commit(newTran);
		} catch (Throwable ex) {
			logger.error("defaultConvertConfig", ex);
			TransactionUtils.rollback(newTran);
		}
	}

	private void ensureNewConfig() throws Exception {
		List<String> rtfConfigs = findValue("fdId", "fdFileExtName = 'rtf'", "");
		if (rtfConfigs.size() == 0) {
			SysFileConvertConfig config = new SysFileConvertConfig();
			config.setFdId("14a0f3054a678ba190bbbc44c05a856e");
			config.setFdConverterKey("toHTML");
			config.setFdConverterType("aspose");
			config.setFdDispenser("remote");
			config.setFdFileExtName("rtf");
			config.setFdHighFidelity("0");
			config.setFdModelName("*");
			config.setFdStatus("1");
			add(config);
		}
	}

	private void importIntialConfig() throws Exception {
		SysFileConvertConfig config = new SysFileConvertConfig();
		config.setFdId("14a0f3054a678ba190bbbc44c05a857e");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("doc");
		config.setFdHighFidelity("1");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f325c2e410e17516b384018b0baf");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("docx");
		config.setFdHighFidelity("1");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f327240738acaacae8a42bc87ca9");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("wps");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f328804be30b8da30a9489ebdedd");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("xls");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f32bdcb5011639247564c0c80559");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("xlsx");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f32d41465854c30dad541c69b7b4");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("et");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f335364fba95f9671074c668220e");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("ppt");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f3379918c37f457065d4a1bb2c31");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("pptx");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f33b779619fb18744594f0bae8f9");
		config.setFdConverterKey("toHTML");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("pdf");
		config.setFdHighFidelity("1");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f34a5b7782a1ecc6076405fb73ed");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("flv");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f355303c1fea793550b498aa9036");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("f4v");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f358e12ee891c715a2644bdb339c");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("mp4");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f35d01e07373b4ec2654f159d946");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("ogg");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f360086d3faab1c86084675b4e1c");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("3gp");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f362e5a9b06e0ad62cb470292f4f");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("avi");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f36659f3d3e7a67d7f54e09ab04e");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("asx");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f36adf164b3e511f4fe4fa781a8c");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("asf");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f36d7ea51bc1a67cc00402d9307a");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("mpg");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f3700769f09d5e502664462bfe14");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("mov");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f3725fe8d0ef1daeff849a98f7ac");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("rm");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f37714475131242cec74449bb8dc");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("rmvb");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f3790715174b946ab7241ecb511c");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("wmv9");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f37bb31626b477594604794bd87e");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("wrf");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a5c0065cd73638df1956a4ed78fa89");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("wmv");
		config.setFdHighFidelity("0");
		config.setFdModelName("*.kms.*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("15e74c8f5aff179110ff647465ba1709");
		config.setFdConverterKey("videoToMp4");
		config.setFdConverterType("aspose");
		config.setFdDispenser("remote");
		config.setFdFileExtName("m4v");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f37dde56978cf9501fe480ab37d0");
		config.setFdConverterKey("image2thumbnail");
		config.setFdConverterType("");
		config.setFdDispenser("local");
		config.setFdFileExtName("jpg");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f3847ae3ffe662f7c9d4baa8ee19");
		config.setFdConverterKey("image2thumbnail");
		config.setFdConverterType("");
		config.setFdDispenser("local");
		config.setFdFileExtName("jpeg");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14a0f38a242f530c7cdc9a241ab8f1a3");
		config.setFdConverterKey("image2thumbnail");
		config.setFdConverterType("");
		config.setFdDispenser("local");
		config.setFdFileExtName("bmp");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14c10e0badc187e6971026f4278b58f6");
		config.setFdConverterKey("image2thumbnail");
		config.setFdConverterType("");
		config.setFdDispenser("local");
		config.setFdFileExtName("png");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14c10e0badc187e6971026f4278b58s6");
		config.setFdConverterKey("cadToImg");
		config.setFdConverterType("");
		config.setFdDispenser("remote");
		config.setFdFileExtName("dwg");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("14c10e0badc18se6971026f4278b58s6");
		config.setFdConverterKey("cadToImg");
		config.setFdConverterType("");
		config.setFdDispenser("remote");
		config.setFdFileExtName("dxf");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
		config = new SysFileConvertConfig();
		config.setFdId("15c10e0badc18se6971026f4278b58s6");
		config.setFdConverterKey("cadToImg");
		config.setFdConverterType("");
		config.setFdDispenser("remote");
		config.setFdFileExtName("dwf");
		config.setFdHighFidelity("0");
		config.setFdModelName("*");
		config.setFdStatus("1");
		super.add(config);
	}

	@Override
	public SysFileConvertConfig getQueueConfig(SysFileConvertQueue taskQueue) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdConverterKey = :converterKey and fdFileExtName = :fileExtName");
		hqlInfo.setParameter("converterKey", taskQueue.getFdConverterKey());
		hqlInfo.setParameter("fileExtName", taskQueue.getFdFileExtName());
		List<SysFileConvertConfig> fitConfigs = new ArrayList<SysFileConvertConfig>();
		Object obj = null;
		try {
			obj = findFirstOne(hqlInfo);
		} catch (Exception e) {
			//
		}
		return (SysFileConvertConfig) obj;
	}

	@Override
	public void deleteConfigs(String[] ids) throws Exception {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				delete((SysFileConvertConfig) findByPrimaryKey(id));
			}
		}
	}

	private void saveAppConfig(String globalKey, String globalValue) {
		SysAppConfig appConfig = null;
		try {
			appConfig = (SysAppConfig)sysAppConfigService.findFirstOne("fdKey='" + globalKey + "'", "");
			if (appConfig != null) {
				appConfig.setFdValue(globalValue);
				sysAppConfigService.update(appConfig);
			} else {
				appConfig = new SysAppConfig();
				appConfig.setFdKey(globalKey);
				appConfig.setFdValue(globalValue);
				sysAppConfigService.add(appConfig);
			}
			FileConvertConfigCache.getInstance().put(globalKey,globalValue);
		} catch (Exception e) {
			logger.info("保存附件转换配置全局参数出错", e);
		}
	}

	@Override
	public List<SysFileConvertConfig> getSupportHighFidelityConfigs() {
		List<SysFileConvertConfig> supportHighFidelities = new ArrayList<SysFileConvertConfig>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdFileExtName='doc' or fdFileExtName='docx' or fdFileExtName='wps' or fdFileExtName='ppt' or fdFileExtName='pptx' or fdFileExtName='pdf'");
		try {
			supportHighFidelities = findList(hqlInfo);
		} catch (Exception e) {
			//
		}
		return supportHighFidelities;
	}

	@Override
	public void saveGlobalInfos(SysFileConvertGlobalConfigForm globalConfigForm) {
		saveAppConfig("attconvert.enabled", globalConfigForm.getAttConvertEnable());
		saveAppConfig("attconvert.oldsuccess.usehtmlview", globalConfigForm.getAttConvertOldSuccessUseHTML());
		saveAppConfig("attconvert.limit.unsignedtask.getnum", globalConfigForm.getUnsignedTaskGetNum());
		saveAppConfig("attconvert.limit.thread.sleep", globalConfigForm.getDistributeThreadSleepTime());
		saveAppConfig("attconvert.long.task.size", globalConfigForm.getLongTaskSize());
		
		saveAppConfig("attconvert.converter.type.aspose", globalConfigForm.getConverter_aspose());
		saveAppConfig("attconvert.converter.type.skofd", globalConfigForm.getConverter_skofd());
		saveAppConfig("attconvert.converter.type.wps", globalConfigForm.getConverter_wps());
		saveAppConfig("attconvert.converter.type.yozo", globalConfigForm.getConverter_yozo());
		saveAppConfig("attconvert.converter.type.wpsCenter", globalConfigForm.getConverter_wps_center());
		saveAppConfig("attconvert.converter.type.dianju", globalConfigForm.getConverter_dianju());
		saveAppConfig("attconvert.converter.type.foxit", globalConfigForm.getConverter_foxit());
		saveHighFidelityInfo("doc", globalConfigForm.getAttConvertHighFidelityDoc());
		saveHighFidelityInfo("docx", globalConfigForm.getAttConvertHighFidelityDocx());
		saveHighFidelityInfo("ppt", globalConfigForm.getAttConvertHighFidelityPpt());
		saveHighFidelityInfo("pptx", globalConfigForm.getAttConvertHighFidelityPptx());
		saveHighFidelityInfo("pdf", globalConfigForm.getAttConvertHighFidelityPdf());
		saveHighFidelityInfo("wps", globalConfigForm.getAttConvertHighFidelityWps());
	}

	private void saveHighFidelityInfo(String fileExtName, String highFidelityValue) {
		List<SysFileConvertConfig> highFidelities = getSupportHighFidelityConfigs(fileExtName);
		if (highFidelities != null && highFidelities.size() > 0) {
			try {
				for (SysFileConvertConfig highFidelity : highFidelities) {
					highFidelity.setFdHighFidelity("true".equals(highFidelityValue) ? "1" : "0");
					update(highFidelity);
				}
			} catch (Exception e) {
				//
			}
		}
	}

	private List<SysFileConvertConfig> getSupportHighFidelityConfigs(String fileExtName) {
		List<SysFileConvertConfig> supportHighFidelities = new ArrayList<SysFileConvertConfig>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdFileExtName='" + fileExtName + "'");
		try {
			supportHighFidelities = findList(hqlInfo);
		} catch (Exception e) {
			//
		}
		return supportHighFidelities;
	}

	@Override
	public SysFileConvertGlobalConfigForm getGlobalConfigForm() {
		if(globalConfigCache.get(GLOBAL_CONFIG) != null) {
			return (SysFileConvertGlobalConfigForm) globalConfigCache.get(GLOBAL_CONFIG);
		}
		return loadGlobalConfigForm();
	}

	public SysFileConvertGlobalConfigForm loadGlobalConfigForm() {
		SysFileConvertGlobalConfigForm resultForm = new SysFileConvertGlobalConfigForm();
		resultForm.setAttConvertEnable(isAttConvertEnable() ? "true" : "false");
		resultForm.setAttConvertOldSuccessUseHTML(isOldConvertSuccessUseHTML() ? "true" : "false");
		try {
			String isConverterAspose = findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");
			String isConverterYOZO = findAppConfigValue("fdKey ='attconvert.converter.type.yozo'", "false");
			String isConverterWPS = findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
			String isConverterWPSCenter = findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
			String isConverterSKOFD = findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");
			String isConverterDianju = findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
			String isConverterFoxit = findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
			resultForm.setConverter_aspose("true".equals(isConverterAspose) ? "true" : "false");
			resultForm.setConverter_yozo("true".equals(isConverterYOZO) ? "true" : "false");
			resultForm.setConverter_wps("true".equals(isConverterWPS) ? "true" : "false");
			resultForm.setConverter_skofd("true".equals(isConverterSKOFD) ? "true" : "false");
			resultForm.setConverter_wps_center("true".equals(isConverterWPSCenter) ? "true" : "false");
			resultForm.setConverter_dianju("true".equals(isConverterDianju) ? "true" : "false");
			resultForm.setConverter_foxit("true".equals(isConverterFoxit) ? "true" : "false");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer hideNamesSB = new StringBuffer();
		String highFidelityValue = getHighFidelityValue("doc");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityDoc");
		}
		resultForm.setAttConvertHighFidelityDoc("true".equals(highFidelityValue) ? "true" : "false");
		highFidelityValue = getHighFidelityValue("docx");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityDocx");
		}
		resultForm.setAttConvertHighFidelityDocx("true".equals(highFidelityValue) ? "true" : "false");
		highFidelityValue = getHighFidelityValue("ppt");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityPpt");
		}
		resultForm.setAttConvertHighFidelityPpt("true".equals(highFidelityValue) ? "true" : "false");
		highFidelityValue = getHighFidelityValue("pptx");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityPptx");
		}
		resultForm.setAttConvertHighFidelityPptx("true".equals(highFidelityValue) ? "true" : "false");
		highFidelityValue = getHighFidelityValue("pdf");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityPdf");
		}
		resultForm.setAttConvertHighFidelityPdf("true".equals(highFidelityValue) ? "true" : "false");
		highFidelityValue = getHighFidelityValue("wps");
		if ("noConfig".equals(highFidelityValue)) {
			hideNamesSB.append(",").append("attConvertHighFidelityWps");
		}
		resultForm.setAttConvertHighFidelityWps("true".equals(highFidelityValue) ? "true" : "false");
		if (hideNamesSB.length() > 0) {
			hideNamesSB.deleteCharAt(0);
			resultForm.setHideNames(hideNamesSB.toString());
		}
		String unsignedTaskGetNumValue = "96";
		String threadSleep = "30";
		String longTaskSize = "30";
		try {
			unsignedTaskGetNumValue = findAppConfigValue("fdKey ='attconvert.limit.unsignedtask.getnum'", "96");
			threadSleep = findAppConfigValue("fdKey ='attconvert.limit.thread.sleep'", "30");
			longTaskSize = findAppConfigValue("fdKey ='attconvert.long.task.size'", "30");
			resultForm.setDistributeThreadSleepTime(threadSleep);
			resultForm.setUnsignedTaskGetNum(unsignedTaskGetNumValue);
			resultForm.setLongTaskSize(longTaskSize);
		} catch (Exception e) {
			unsignedTaskGetNumValue = "96";
			threadSleep = "30";
			longTaskSize = "30";
		}
		return resultForm;
	}

	private String getHighFidelityValue(String fileExtName) {
		String result = "";
		List<String> highFidelityValues = null;
		try {
			highFidelityValues = findValue("fdHighFidelity", "fdFileExtName='" + fileExtName + "'", "");
			if (highFidelityValues != null && highFidelityValues.size() > 0) {
				boolean breaked = false;
				for (String highFidelity : highFidelityValues) {
					if (StringUtil.isNull(highFidelity) || "0".equals(highFidelity)) {
						result = "false";
						breaked = true;
						break;
					}
				}
				if (!breaked) {
					result = "true";
				}
			} else {
				result = "noConfig";
			}
		} catch (Exception e) {
			//
		}
		return result;
	}
	@Override
    public List<SysFileConvertConfig> getConfigsByTypeAndKey(String convertType, String[] convertKeys) {
		List<SysFileConvertConfig> list = new ArrayList<SysFileConvertConfig>();
		try {
			StringBuffer whereBlock = new StringBuffer();
			HQLInfo hqlInfo = new HQLInfo();

			for (int i = 0; i < convertKeys.length; i++) {
				whereBlock.append("fdConverterKey=:convertKey" + i).append(" or ");
				hqlInfo.setParameter("convertKey" + i, convertKeys[i]);
			}

			String whereBlockAbridge = whereBlock.toString().substring(0, whereBlock.lastIndexOf("or"));
			String where = "fdConverterType =:convertType and (" + whereBlockAbridge + ")";
			hqlInfo.setParameter("convertType", convertType);
			hqlInfo.setWhereBlock(where);

			list = findList(hqlInfo);
		} catch (Exception e) {
			logger.error("error:" + e);
		}

		return list;
	}
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		FileConvertConfigCache.getInstance().clear();
		String fdId = super.add(modelObj);
		SysFileStoreUtil.updateConfigCache();
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FileConvertConfigCache.getInstance().clear();
		super.update(modelObj);
		SysFileStoreUtil.updateConfigCache();
	}

	@Override
	public void clearCache() {
		KmssCache cache = new KmssCache(SysFileConvertGlobalConfigForm.class);
		cache.clear();
		logger.debug("清空SysFileConvertGlobalConfig缓存成功...");
	}
}
