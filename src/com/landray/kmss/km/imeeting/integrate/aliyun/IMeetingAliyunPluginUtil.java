package com.landray.kmss.km.imeeting.integrate.aliyun;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.integrate.aliyun.interfaces.IMeetingAliyunPlugin;
import com.landray.kmss.km.imeeting.integrate.aliyun.interfaces.IMeetingAliyunProvider;
import com.landray.kmss.util.StringUtil;

public class IMeetingAliyunPluginUtil {
	
	private static final String ID = "com.landray.kmss.km.imeeting.integrate.alimeeting";

	private static List<IMeetingAliyunPlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<IMeetingAliyunPlugin> getExtensionList() {
		if (extensionList == null || extensionList.isEmpty()) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "alimeeting");
			extensionList = new ArrayList<IMeetingAliyunPlugin>();
			for (IExtension extension : extensions) {
				IMeetingAliyunPlugin plugin = new IMeetingAliyunPlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((IMeetingAliyunProvider) Plugin.getParamValue(extension, "provider"));
				String order = Plugin.getParamValueString(extension, "order");
				if (StringUtil.isNotNull(order)) {
					plugin.setOrder(Integer.parseInt(order));
				}
				extensionList.add(plugin);
			}
		}
		return extensionList;
	}

}
