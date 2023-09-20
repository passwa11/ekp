package com.landray.kmss.common.module.core.proxy.callback;

import com.landray.kmss.common.module.core.proxy.invoker.MethodInvoker;

/**
 * @author 严明镜
 * @version 1.0 2021年03月29日
 */
@FunctionalInterface
public interface IProxyCallBack {

	/**
	 * 在MethodInvoker.prepare(...)执行成功后调用
	 */
	void afterPrepare(MethodInvoker invoker);

}
