package com.landray.kmss.sys.attachment.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.model.SysAttComputingTimePlugin;
import com.landray.kmss.sys.attachment.service.ISysAttComputingTimeService;
import com.landray.kmss.util.StringUtil;

public class SysAttComputingTimeUtil {
	
	private static final String ID = "com.landray.kmss.sys.attachment.computing.time";

	private static List<SysAttComputingTimePlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<SysAttComputingTimePlugin> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "time");
			extensionList = new ArrayList<SysAttComputingTimePlugin>();
			for (IExtension extension : extensions) {
				SysAttComputingTimePlugin plugin = new SysAttComputingTimePlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((ISysAttComputingTimeService) Plugin
						.getParamValue(extension, "provider"));
				String timeInterval = Plugin.getParamValueString(extension,
						"timeInterval");
				if (StringUtil.isNotNull(timeInterval)) {
					plugin.setTimeInterval(Integer.parseInt(timeInterval));
				}
				extensionList.add(plugin);
			}
		}
		return extensionList;
	}

}
