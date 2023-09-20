package com.landray.kmss.third.payment.plugin;

import com.landray.kmss.framework.plugin.core.config.IConfigurationElement;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.third.payment.service.IThirdPaymentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThirdPaymentPlugin {

	private static final String ID = "com.landray.kmss.third.payment";

	private static List<ThirdPaymentPluginData> extensionList = null;

	public static List<ThirdPaymentPluginData> getExtensionList() {
		if (extensionList == null) {
			IExtension[] extensions = Plugin.getExtensions(ID, "*", "pay");
			extensionList = new ArrayList<ThirdPaymentPluginData>();
			IConfigurationElement element = null;
			for (IExtension extension : extensions) {
				ThirdPaymentPluginData data = new ThirdPaymentPluginData();
				data.setKey(Plugin.getParamValueString(extension, "key"));
				data.setPaymentProvider((IThirdPaymentProvider) Plugin.getParamValue(
						extension, "provider"));
				data.setOrder(Integer.parseInt(Plugin.getParamValueString(
						extension, "order")));
				data.setName(Plugin.getParamValueString(extension, "name"));
				data.setSupportedClient(Plugin.getParamValueString(extension, "supportedClient"));
				extensionList.add(data);
			}
		}
		return extensionList;
	}

	public static ThirdPaymentPluginData getPluginData(String key) {
		if (extensionList == null) {
			getExtensionList();
		}
		for (ThirdPaymentPluginData data : extensionList) {
			if (key.equals(data.getKey())) {
				return data;
			}
		}
		return null;
	}

	private static Map<String,Map<String, String>> enabledPaymentWays = new ConcurrentHashMap<>();

	public static Map<String, String> getEnabledPaymentWays(String clientType) throws Exception{
		if(!enabledPaymentWays.containsKey(clientType)){
			Map<String, String> payWays = new HashMap<>();
			List<ThirdPaymentPluginData> list = getExtensionList();
			for(ThirdPaymentPluginData data:list){
				String supportedClient = data.getSupportedClient();
				if(supportedClient.contains(clientType+";")){
					payWays.put(data.getKey(),data.getName());
				}
			}
			enabledPaymentWays.put(clientType,payWays);
		}
		return enabledPaymentWays.get(clientType);
	}
}
