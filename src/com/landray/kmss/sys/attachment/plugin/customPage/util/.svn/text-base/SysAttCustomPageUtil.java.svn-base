package com.landray.kmss.sys.attachment.plugin.customPage.util;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.plugin.customPage.model.SysAttCustomPagePlugin;
import com.landray.kmss.sys.attachment.plugin.customPage.service.ISysAttCustomPageService;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SysAttCustomPageUtil {
	
	private static final String customPage = "com.landray.kmss.sys.attachment.custom.page";

	private static List<SysAttCustomPagePlugin> extensionList = null;

	@SuppressWarnings("unchecked")
	public static List<SysAttCustomPagePlugin> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(customPage, "*", "customPage");
			extensionList = new ArrayList<SysAttCustomPagePlugin>();
			for (IExtension extension : extensions) {
				SysAttCustomPagePlugin plugin = new SysAttCustomPagePlugin();
				plugin.setKey(Plugin.getParamValueString(extension, "key"));
				plugin.setName(Plugin.getParamValueString(extension, "name"));
				plugin.setProvider((ISysAttCustomPageService) Plugin.getParamValue(extension, "provider"));
				extensionList.add(plugin);
			}
		}
		return extensionList;
	}

	/**
	 * 获取某个模块自定义的附件页面
	 * @param request
	 * @return
	 */
	public static String getCustomPage(HttpServletRequest request) throws Exception {
		String customPageUrl="";
		List<SysAttCustomPagePlugin> plugins = getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttCustomPagePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)) {
					ISysAttCustomPageService provider = plugin.getProvider();
					customPageUrl = provider.getCustomPageUrl(request);
					if(StringUtil.isNotNull(customPageUrl)){
						//找到满足条件的自定义页面url
						break;
					}
				}
			}
		}
		return customPageUrl;
	}
}
