package com.landray.kmss.sys.filestore.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;

public class SysFileStoreDispenserDataSource extends
		SysFileStoreCustomDataSource {

	public SysFileStoreDispenserDataSource() throws Exception {
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
		hqlInfo.setSelectBlock("fdDispenser");
		try {
			List<String> fdDispensers = sysFileConvertConfigService
					.findValue(hqlInfo);
			for (String item : fdDispensers) {
				if (!dataMap.containsKey(item)) {
					dataMap.put(item, item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
