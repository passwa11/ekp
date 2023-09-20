package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

public class SysFileStoreModelNamesDataSource extends SysFileStoreCustomDataSource {

	public SysFileStoreModelNamesDataSource() throws Exception {
		super();
	}

	@Override
	public String getDefaultValue() {
		return "";
	}

	@Override
	public Map<String, String> getOptions() {
		return dataMap;
	}

	@Override
	protected void initialDataMap() {
		List<Map<String, String>> moduleList = getModuleList();
		dataMap.put("*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.unlimited","sys-filestore")); // 不限
		dataMap.put("*.km.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.kmModule","sys-filestore")); // KM模块
		dataMap.put("*.kms.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.kmsModule","sys-filestore")); // KMS模块
		dataMap.put("*.sys.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.systemModule","sys-filestore")); // 系统模块
		for (Map<String, String> module : moduleList) {
			dataMap.put(module.get("value"), module.get("name"));
		}
		
	}

	private List<Map<String, String>> getModuleList() {
		List<Map<String, String>> moduleList = new ArrayList<Map<String, String>>();
		List<?> moduleInfoList = SysConfigs.getInstance().getModuleInfoList();
		for (int i = 0; i < moduleInfoList.size(); i++) {
			SysCfgModuleInfo sysCfgModuleInfo = (SysCfgModuleInfo) moduleInfoList.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", formatModelValue(sysCfgModuleInfo.getUrlPrefix()));
			String messageKey = sysCfgModuleInfo.getMessageKey();
			if (StringUtil.isNotNull(messageKey) && StringUtil.isNotNull(ResourceUtil.getString(messageKey))) {
				map.put("name", ResourceUtil.getString(messageKey));
				moduleList.add(map);
			}
		}
		Collections.sort(moduleList, new ModuleComparator());
		return moduleList;
	}

	private String formatModelValue(String urlPrefix) {
		StringBuffer resultSB = new StringBuffer("*");
		if (StringUtil.isNotNull(urlPrefix)) {
			resultSB.append(urlPrefix.replace("/", ".")).append("*");
		}
		return resultSB.toString();
	}

	class ModuleComparator implements Comparator<Map<String, String>> {

		@Override
        public int compare(Map<String, String> s1, Map<String, String> s2) {
			if (s1 == null || s2 == null) {
				return 0;
			}
			if (s1.containsKey("name") && s2.containsKey("name")) {
				return ChinesePinyinComparator.compare(s1.get("name"), s2.get("name"));
			}
			if (s1.containsKey("value") && s2.containsKey("value")) {
				return ChinesePinyinComparator.compare(s1.get("value"), s2.get("value"));
			}
			return 0;
		}
	}

}
