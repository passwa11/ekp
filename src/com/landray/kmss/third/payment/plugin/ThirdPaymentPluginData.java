package com.landray.kmss.third.payment.plugin;

import com.landray.kmss.third.payment.service.IThirdPaymentProvider;

public class ThirdPaymentPluginData implements Comparable {

	private String key;

	private String name;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public IThirdPaymentProvider getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(IThirdPaymentProvider paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

	private IThirdPaymentProvider paymentProvider;

	private int order;

	@Override
    public int compareTo(Object o) {
		ThirdPaymentPluginData data = (ThirdPaymentPluginData) o;
		if (this.order > data.order) {
			return 1;
		} else if (this.order == data.order) {
			return 0;
		} else {
			return -1;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSupportedClient() {
		return supportedClient;
	}

	public void setSupportedClient(String supportedClient) {
		this.supportedClient = supportedClient;
	}

	private String supportedClient;

}
