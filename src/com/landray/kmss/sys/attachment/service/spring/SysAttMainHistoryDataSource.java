package com.landray.kmss.sys.attachment.service.spring;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin;
import com.landray.kmss.sys.attachment.plugin.HistoryVersionPluginData;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

/**
 * @todo 可配置附件历史版本的模块
 * @author  叶正平
 * @date 2020-9-11
 */
public class SysAttMainHistoryDataSource implements ICustomizeDataSourceWithRequest {

	@Override
	public void setRequest(ServletRequest request) {

	}

	@Override
	public String getDefaultValue() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Map<String, String> getOptions() {
		// TODO 自动生成的方法存根
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, List<HistoryVersionPluginData>> moduleMap = HistoryVersionPlugin
				.getModuleMap();
		for (String key : moduleMap.keySet()) {
			String name = ResourceUtil.getString(key);
			if (StringUtil.isNull(name)) {
				name = key;
			}
			map.put(key, name);
		}
		return map;
	}

}
