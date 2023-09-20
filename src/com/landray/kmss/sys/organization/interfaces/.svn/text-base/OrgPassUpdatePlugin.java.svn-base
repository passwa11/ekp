package com.landray.kmss.sys.organization.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;

public class OrgPassUpdatePlugin {
	private static final String ID = "com.landray.kmss.sys.organization.person";
	private static final String NAME = "updatePass";

	private static List<OrgPassUpdatePluginData> extensionList = null;

	public static List<OrgPassUpdatePluginData> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*",NAME);
			extensionList = new ArrayList<OrgPassUpdatePluginData>();
			for (IExtension extension : extensions) {
				OrgPassUpdatePluginData data = new OrgPassUpdatePluginData();
				data.setKey(Plugin.getParamValueString(extension, "key"));
				data.setBean((ISysOrgPassUpdate) Plugin.getParamValue(
						extension, "beanName"));
				extensionList.add(data);
			}
		}
		return extensionList;
	}
	
	public static List<OrgPassUpdatePluginData> getEnabledExtensionList() {
		List<OrgPassUpdatePluginData> result = new ArrayList<OrgPassUpdatePluginData>();
		List<OrgPassUpdatePluginData> extensionList = getExtensionList();
		if(extensionList.isEmpty()){
			return result;
		}
		for(OrgPassUpdatePluginData data:extensionList){
			ISysOrgPassUpdate bean = data.getBean();
			try {
				if(bean.isPassUpdateEnable()){
					result.add(data);
				}
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
		return result;
	}

}
