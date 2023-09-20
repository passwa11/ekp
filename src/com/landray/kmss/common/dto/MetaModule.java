package com.landray.kmss.common.dto;

public interface MetaModule {
	/**
	 * 读-字段名
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 读-中文名
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * 读-MessageKey
	 * 
	 * @return
	 */
	String getMessageKey();

	/**
	 * 读-所在的应用名
	 * 
	 * @return
	 */
	String getAppName();
}
