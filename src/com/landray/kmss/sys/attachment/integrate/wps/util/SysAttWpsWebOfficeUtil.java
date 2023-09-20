package com.landray.kmss.sys.attachment.integrate.wps.util;

import java.util.List;

import com.landray.kmss.sys.attachment.integrate.wps.SysAttWpsWebOfficePlugin;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsWebOfficeProvider;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.util.StringUtil;

public class SysAttWpsWebOfficeUtil {

	public static Boolean isEnable() throws Exception {
		Boolean flag = false;
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "wpsWebOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					flag = provider.isEnabled();
				}
			}
		}
		return flag
				&& SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWEBOFFICE.equals(SysAttConfigUtil.getOnlineToolType());
	}

	public static String getUrl() throws Exception {

		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "wpsWebOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					if (StringUtil.isNotNull(provider.getUrl())) {
						String Url = provider.getUrl();
						if (Url.endsWith("/")) {
							Url = Url.substring(0, Url.length() - 1);
						}
						return Url;
					}
				}
			}
		}

		return null;
	}

	public static String getAppid() throws Exception {
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "wpsWebOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					return provider.getAppid();
				}
			}
		}
		return null;
	}

	public static String getAppkey() throws Exception {
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "wpsWebOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					return provider.getAppkey();
				}
			}
		}
		return null;
	}
}
