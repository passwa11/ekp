package com.landray.kmss.km.imeeting.synchro;

import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroProvider;

public class ImeetingSynchroPluginData implements Comparable {

	private String key;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	private IMeetingSynchroProvider provider;

	private int order;

	@Override
	public int compareTo(Object o) {
		ImeetingSynchroPluginData data = (ImeetingSynchroPluginData) o;
		if (this.order > data.order) {
            return 1;
        } else if (this.order == data.order) {
            return 0;
        } else {
            return -1;
        }
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBindPageUrl(String bindPageUrl) {
		this.bindPageUrl = bindPageUrl;
	}

	public String getBindPageUrl() {
		return bindPageUrl;
	}

	public IMeetingSynchroProvider getProvider() {
		return provider;
	}

	public void setProvider(IMeetingSynchroProvider provider) {
		this.provider = provider;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String bindPageUrl;

}
