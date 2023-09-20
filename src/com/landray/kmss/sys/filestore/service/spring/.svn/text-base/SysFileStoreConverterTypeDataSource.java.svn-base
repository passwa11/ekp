package com.landray.kmss.sys.filestore.service.spring;

import java.util.Map;

import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.util.ResourceUtil;

public class SysFileStoreConverterTypeDataSource extends SysFileStoreCustomDataSource {

	public SysFileStoreConverterTypeDataSource() throws Exception {
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
		dataMap.put("aspose", ResourceUtil.getString("sysFileConvertConfig.fdConverterType.aspose","sys-filestore")); // Aspose转换服务
		dataMap.put("yozo", ResourceUtil.getString("sysFileConvertConfig.fdConverterType.yozo","sys-filestore")); // 永中转换服务
		if (getWpsCloudEnable()) {
			dataMap.put("wps",
					ResourceUtil.getString(
							"sysFileConvertConfig.fdConverterType.wpsCloud",
							"sys-filestore")); // wps
		}
	}

	private Boolean getWpsCloudEnable() {
		Boolean flag = false;
		try {
			flag = SysAttWpsCloudUtil.isEnable();
		} catch (Exception e) {

		}
		return flag;
	}

}
