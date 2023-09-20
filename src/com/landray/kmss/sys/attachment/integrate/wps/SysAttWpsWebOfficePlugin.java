package com.landray.kmss.sys.attachment.integrate.wps;

import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsWebOfficeProvider;

public class SysAttWpsWebOfficePlugin {
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

	public ISysAttachmentWpsWebOfficeProvider getProvider() {
		return provider;
	}

	public void setProvider(ISysAttachmentWpsWebOfficeProvider provider) {
		this.provider = provider;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	private String name;
	private ISysAttachmentWpsWebOfficeProvider provider;
	private Integer order;
}
