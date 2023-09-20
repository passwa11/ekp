package com.landray.kmss.sys.filestore.location.model;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;

public class SysFileLocation {

	/**
	 * 存储类型名称
	 */
	private String title;

	/**
	 * 存储位置<br>
	 * 本地存储为server，其他存储各自扩展
	 */
	private String key;

	/**
	 * 直连实现
	 */
	private ISysFileLocationDirectService directService;

	/**
	 * 流代理
	 */
	private ISysFileLocationProxyService proxyService;

	/**
	 * 排序号
	 */
	private Integer order;

	/**
	 * 配置文件路径
	 */
	private String configJspUrl;

	/**
	 * 前端相关脚本路径
	 */
	private String jsUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ISysFileLocationDirectService getDirectService() {
		return directService;
	}

	public void setDirectService(ISysFileLocationDirectService directService) {
		this.directService = directService;
	}

	public ISysFileLocationProxyService getProxyService() {
		return proxyService;
	}

	public void setProxyService(ISysFileLocationProxyService proxyService) {
		this.proxyService = proxyService;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getConfigJspUrl() {
		return configJspUrl;
	}

	public void setConfigJspUrl(String configJspUrl) {
		this.configJspUrl = configJspUrl;
	}

	public String getJsUrl() {
		return jsUrl;
	}

	public void setJsUrl(String jsUrl) {
		this.jsUrl = jsUrl;
	}

}
