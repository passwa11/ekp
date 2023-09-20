package com.landray.kmss.km.imeeting.integrate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.integrate.interfaces.IMeetingVideoPlugin;
import com.landray.kmss.km.imeeting.integrate.interfaces.IMeetingVideoProvider;
import com.landray.kmss.util.StringUtil;

public class IMeetingIntegratePluginUtil {

	private static final String ID = "com.landray.kmss.km.imeeting.integrate";

	private static List<IMeetingVideoPlugin> videoExtensionList = null;

	@SuppressWarnings("unchecked")
	public static List<IMeetingVideoPlugin> getVedioExtensionList() {
		if (videoExtensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "video");
			videoExtensionList = new ArrayList<IMeetingVideoPlugin>();
			for (IExtension extension : extensions) {
				IMeetingVideoPlugin plugin = new IMeetingVideoPlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((IMeetingVideoProvider) Plugin
						.getParamValue(extension, "provider"));
				String order = Plugin.getParamValueString(extension, "order");
				if (StringUtil.isNotNull(order)) {
					plugin.setOrder(Integer.parseInt(order));
				}
				videoExtensionList.add(plugin);
			}
		}
		return videoExtensionList;
	}

}
