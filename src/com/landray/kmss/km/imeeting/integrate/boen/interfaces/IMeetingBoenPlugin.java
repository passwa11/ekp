package com.landray.kmss.km.imeeting.integrate.boen.interfaces;

public class IMeetingBoenPlugin {

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IMeetingBoenProvider getProvider() {
		return provider;
	}

	public void setProvider(IMeetingBoenProvider provider) {
		this.provider = provider;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	private String name;
	private IMeetingBoenProvider provider;
	private Integer order;

}
