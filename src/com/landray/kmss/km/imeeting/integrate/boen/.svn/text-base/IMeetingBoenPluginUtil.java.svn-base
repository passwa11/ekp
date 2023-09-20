package com.landray.kmss.km.imeeting.integrate.boen;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.integrate.boen.interfaces.IMeetingBoenPlugin;
import com.landray.kmss.km.imeeting.integrate.boen.interfaces.IMeetingBoenProvider;
import com.landray.kmss.util.StringUtil;

public class IMeetingBoenPluginUtil {

	private static final String ID = "com.landray.kmss.km.imeeting.integrate.boen";

	private static List<IMeetingBoenPlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<IMeetingBoenPlugin> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "boen");
			extensionList = new ArrayList<IMeetingBoenPlugin>();
			for (IExtension extension : extensions) {
				IMeetingBoenPlugin plugin = new IMeetingBoenPlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((IMeetingBoenProvider) Plugin.getParamValue(extension, "provider"));
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
