package com.landray.kmss.km.imeeting.integrate.kk;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.integrate.kk.interfaces.IMeetingKKPlugin;
import com.landray.kmss.km.imeeting.integrate.kk.interfaces.IMeetingKKProvider;
import com.landray.kmss.util.StringUtil;

public class IMeetingKKPluginUtil {

	private static final String ID = "com.landray.kmss.km.imeeting.integrate.kk";

	private static List<IMeetingKKPlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<IMeetingKKPlugin> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "kk");
			extensionList = new ArrayList<IMeetingKKPlugin>();
			for (IExtension extension : extensions) {
				IMeetingKKPlugin plugin = new IMeetingKKPlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((IMeetingKKProvider) Plugin.getParamValue(extension, "provider"));
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
