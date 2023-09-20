package com.landray.kmss.sys.filestore.service.spring;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

public abstract class SysFileStoreCustomDataSource implements
		ICustomizeDataSource {
	public SysFileStoreCustomDataSource() throws Exception {
		initialDataMap();
	}

	protected abstract void initialDataMap();

	protected static ISysFileConvertConfigService sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
			.getBean("sysFileConvertConfigService");

	protected Map<String, String> dataMap = new HashMap<String, String>();
}
