package com.landray.kmss.common.module.core.proxy;

import com.landray.kmss.common.module.core.proxy.callback.IProxyCallBack;

/**
 * 解耦调用类中的方法
 *
 * @author 严明镜
 * @version 1.0 2021年02月19日
 */
public interface IDynamicProxy {

	/**
	 * 用于调用所包装类的方法，根据方法名及传入的参数类型进行匹配，若参数为null，则忽略该参数类型的匹配
	 *
	 * @param methodName 方法名
	 * @param parameters 参数
	 * @return 方法调用后的返回结果
	 */
	Object invoke(String methodName, Object... parameters);

	/**
	 * 在MethodInvoker执行prepare后，执行invoke前调用该callBack
	 *
	 * @param callBack 回调方法
	 */
	void setPrepareCallBack(IProxyCallBack callBack);
}
