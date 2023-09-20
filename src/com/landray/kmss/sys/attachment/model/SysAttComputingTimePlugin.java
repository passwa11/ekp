package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.attachment.service.ISysAttComputingTimeService;

public class SysAttComputingTimePlugin {
	private String key;
	private String name;
	private ISysAttComputingTimeService provider;
	private Integer timeInterval;

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

	public ISysAttComputingTimeService getProvider() {
		return provider;
	}

	public void setProvider(ISysAttComputingTimeService provider) {
		this.provider = provider;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}
	
}
