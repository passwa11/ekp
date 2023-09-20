package com.landray.kmss.km.imeeting.integrate.aliyun.interfaces;

public class IMeetingAliyunPlugin {

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

	public IMeetingAliyunProvider getProvider() {
		return provider;
	}

	public void setProvider(IMeetingAliyunProvider provider) {
		this.provider = provider;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	private String name;
	private IMeetingAliyunProvider provider;
	private Integer order;

}
