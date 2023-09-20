package com.landray.kmss.common.module.core.proxy;

import com.landray.kmss.common.module.core.proxy.callback.IProxyCallBack;
import com.landray.kmss.common.module.core.proxy.invoker.MethodInvoker;
import com.landray.kmss.common.module.util.ExceptionUtil;

/**
 * 解耦调用静态类中的方法
 *
 * @author 严明镜
 * @version 1.0 2021年03月03日
 */
public class StaticProxy implements IDynamicProxy {

	private final MethodInvoker invoker;

	private IProxyCallBack callBack;

	public StaticProxy(Object clazz) {
		this.invoker = new MethodInvoker(clazz);
	}

	@Override
	public Object invoke(String methodName, Object... parameters) {
		try {
			invoker.prepare(methodName, parameters);
			if (callBack != null) {
				callBack.afterPrepare(invoker);
			}
			return invoker.invoke();
		} catch (NoSuchMethodException e) {
			ExceptionUtil.throwRuntimeException("未找到静态方法:" + methodName, e);
		} catch (IllegalAccessException e) {
			ExceptionUtil.throwRuntimeException("无法访问静态方法:" + methodName, e);
		} catch (Exception e) {
			ExceptionUtil.throwRuntimeException("调用静态方法出错:" + methodName, e);
		}
		return null;
	}

	@Override
	public void setPrepareCallBack(IProxyCallBack callBack) {
		this.callBack = callBack;
	}
}
