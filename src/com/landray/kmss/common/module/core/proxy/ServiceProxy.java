package com.landray.kmss.common.module.core.proxy;

import com.landray.kmss.common.module.core.proxy.callback.IProxyCallBack;
import com.landray.kmss.common.module.core.proxy.invoker.MethodInvoker;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 解耦调用Service中的方法
 *
 * @author 严明镜
 * @version 1.0 2021年02月19日
 */
public class ServiceProxy implements IDynamicProxy {
	protected static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

	private final Object service;

	private IProxyCallBack callBack;

	private final MethodInvoker invoker;

	public ServiceProxy(Object service) {
		this.service = service;
		this.invoker = new MethodInvoker(service);
		this.callBack = null;
	}

	/**
	 * method准备好后回调
	 */
	@Override
	public void setPrepareCallBack(IProxyCallBack callBack) {
		this.callBack = callBack;
	}

	/**
	 * 通过反射调用service对应方法，
	 *
	 * @param methodName 方法名
	 * @param parameters 参数
	 */
	@Override
	public Object invoke(String methodName, Object... parameters) {
		try {
			invoker.prepare(methodName, parameters);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("未找到" + service + "的" + methodName + "方法", e);
		}
		if (invoker.isPrepared() && callBack != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("调用callBack=" + callBack);
			}
			callBack.afterPrepare(invoker);
		}
		if (!invoker.isPrepared()) {
			if (logger.isDebugEnabled()) {
				logger.warn("MethodInvoker的prepared为false，停止执行" + service.getClass().getName() + "." + methodName+ "方法");
			}
			return null;
		}
		return invokeTransactional(methodName);
	}

	/**
	 * 以嵌套事务的方式执行
	 * [事务说明]：
	 * 当外层有事务时，不创建新的事务。
	 * 当外层没有事务时，开启事务，并在调用完毕时提交。
	 * （多层嵌套说明：若有多层嵌套，内层发生异常时若想要与外层保持事务的一致性，则内层应设置WarnLevel=ERROR，将异常抛到外层使其回滚事务）
	 *
	 * @param methodName 方法名
	 */
	private Object invokeTransactional(String methodName) {
		boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
		if (logger.isDebugEnabled()) {
			logger.debug("调用方法:" + methodName + " 当前是否已处于事务中:" + actualTransactionActive + " 是否只读:" + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
		}
		TransactionStatus status = null;
		Throwable t = null;
		if (!actualTransactionActive) {
			status = TransactionUtils.beginTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug("开启事务" + status);
			}
		}
		try {
			Object result = invoker.invoke();
			if (!actualTransactionActive) {
				if (logger.isDebugEnabled()) {
					logger.debug("提交事务" + status);
				}
				TransactionUtils.commit(status);
			}
			return result;
		} catch (IllegalAccessException e) {
			t = e;
			throw new RuntimeException("无法访问" + service + "的" + methodName + "方法", e);
		} catch (Exception e) {
			t = e;
			throw new RuntimeException("调用" + service + "的" + methodName + "方法出错", e);
		} finally {
			if (t != null && status != null) {
				try {
					if (logger.isDebugEnabled()) {
						logger.debug("回滚事务" + status);
					}
					TransactionUtils.rollback(status);
				} catch (Exception e) {
					logger.error("回滚事务时发生异常", e);
				}
			}
		}
	}
}
