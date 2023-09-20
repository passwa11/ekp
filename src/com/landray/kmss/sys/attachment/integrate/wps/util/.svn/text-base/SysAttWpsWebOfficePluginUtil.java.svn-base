package com.landray.kmss.sys.attachment.integrate.wps.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.integrate.wps.SysAttWpsWebOfficePlugin;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsWebOfficeProvider;
import com.landray.kmss.util.StringUtil;

public class SysAttWpsWebOfficePluginUtil {
	
	private static final String ID = "com.landray.kmss.sys.attachment.integrate.wps";

	private static List<SysAttWpsWebOfficePlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<SysAttWpsWebOfficePlugin> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "wps");
			extensionList = new ArrayList<SysAttWpsWebOfficePlugin>();
			for (IExtension extension : extensions) {
				SysAttWpsWebOfficePlugin plugin = new SysAttWpsWebOfficePlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((ISysAttachmentWpsWebOfficeProvider) Plugin.getParamValue(extension, "provider"));
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
