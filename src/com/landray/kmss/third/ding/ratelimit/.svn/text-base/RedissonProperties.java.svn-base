package com.landray.kmss.third.ding.ratelimit;

import com.landray.kmss.util.ResourceUtil;

public class RedissonProperties {
	/**
	 * 连接超时时长
	 */
	private int timeout = 3000;
	/**
	 * ip
	 */
	private String address;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 连接库
	 */
	private int database = 0;
	/**
	 * 连接池大小
	 */
	private int connectionPoolSize = 1000;
	/**
	 * 最小连接数
	 */
	private int connectionMinimumIdleSize = 10;

	/**
	 * 主服务器连接数
	 */
	private int masterConnectionPoolSize = 250;

	/**
	 * 主服务器名称
	 */
	private String masterName;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getAddress() {
		return ResourceUtil.getKmssConfigString("cache.redis.host");
		//return "127.0.0.1:6379";
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return ResourceUtil
		 .getKmssConfigString("cache.redis.password");
		//return null;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public int getConnectionMinimumIdleSize() {
		return connectionMinimumIdleSize;
	}

	public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
		this.connectionMinimumIdleSize = connectionMinimumIdleSize;
	}

	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}

	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
}

