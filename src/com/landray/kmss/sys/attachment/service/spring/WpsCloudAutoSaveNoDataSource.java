package com.landray.kmss.sys.attachment.service.spring;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

public class WpsCloudAutoSaveNoDataSource
		implements ICustomizeDataSourceWithRequest {

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
		List<SysCfgModuleInfo> moduleInfoList = SysConfigs.getInstance()
				.getModuleInfoList();
		if (!moduleInfoList.isEmpty()) {
			for (SysCfgModuleInfo sysCfgModuleInfo : moduleInfoList) {
				String urlPrefix = sysCfgModuleInfo.getUrlPrefix();
				String messageKey = sysCfgModuleInfo.getMessageKey();
				if (StringUtil.isNull(urlPrefix)
						&& StringUtil.isNull(messageKey)) {
					continue;
				}
				if (urlPrefix.endsWith("/")) {
					urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1);
				}
				String name = ResourceUtil.getString(messageKey);
				if (urlPrefix.contains("km/agreement")) {
					//合同模块有两个，区分一下名称
					name = ResourceUtil.getString(messageKey+".alias");
					if (StringUtil.isNull(name)) {
						name = ResourceUtil.getString(messageKey);
					}
				}
				if (StringUtil.isNull(name)) {
					continue;
				}
				map.put(urlPrefix, name);
			}
		}
		return map;
	}

}
