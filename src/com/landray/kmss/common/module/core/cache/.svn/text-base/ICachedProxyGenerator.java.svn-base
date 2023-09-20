package com.landray.kmss.common.module.core.cache;

/**
 * @author 严明镜
 * @version 1.0 2021年03月11日
 */
public interface ICachedProxyGenerator<T> {

	/**
	 * 用于缓存结果的key，
	 * 返回null表示不进行缓存
	 */
	String getKey();

	/**
	 * 创建代理前的校验
	 */
	boolean valid();

	/**
	 * 开始创建代理
	 */
	T createProxy();
}
