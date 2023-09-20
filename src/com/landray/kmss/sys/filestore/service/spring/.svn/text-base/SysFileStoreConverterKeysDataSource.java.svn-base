package com.landray.kmss.sys.filestore.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;

public class SysFileStoreConverterKeysDataSource extends
		SysFileStoreCustomDataSource {

	public SysFileStoreConverterKeysDataSource() throws Exception {
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

	@SuppressWarnings("unchecked")
	@Override
	protected void initialDataMap() {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdConverterKey");
		try {
			List<String> fdConverterKeys = sysFileConvertConfigService
					.findValue(hqlInfo);
			for (String item : fdConverterKeys) {
				if (!dataMap.containsKey(item)) {
					dataMap.put(item, item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
