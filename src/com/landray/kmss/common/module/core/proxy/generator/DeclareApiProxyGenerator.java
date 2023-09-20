package com.landray.kmss.common.module.core.proxy.generator;

import com.landray.kmss.common.module.core.cache.ICachedProxyGenerator;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.proxy.invoker.MethodInvoker;
import com.landray.kmss.common.module.core.register.annotation.declare.ApiImplement;
import com.landray.kmss.common.module.core.register.annotation.declare.Declare;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 根据类注释@Declare生成代理类
 *
 * @author 严明镜
 * @version 1.0 2021年03月08日
 */
public class DeclareApiProxyGenerator<T> implements ICachedProxyGenerator<T> {

	protected static final Logger logger = LoggerFactory.getLogger(DeclareApiProxyGenerator.class);

	private final Class<T> apiInterface;

	/**
	 * 指定某个访问一个service的方法，用于通过serviceName获取代理的场景(如：机制定义接口，业务模块实现，机制模块调用时使用业务模块的serviceName)
	 */
	private final String assignedServiceName;

	public DeclareApiProxyGenerator(Class<T> apiInterface, String assignedServiceName) {
		Assert.isTrue(apiInterface != null, "代理的Api不能为空");
		Assert.isTrue(StringUtil.isNotNull(assignedServiceName), "代理的Service不能为空");
		this.apiInterface = apiInterface;
		this.assignedServiceName = assignedServiceName;
	}

	@Override
	public String getKey() {
		return "API" + "_" + apiInterface.getName() + "_" + assignedServiceName;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T createProxy() {
		return (T) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class<?>[]{apiInterface}, new ServiceInvocationHandler(apiInterface, assignedServiceName));
	}

	@Override
	public boolean valid() {
		if (apiInterface == null || !apiInterface.isInterface()) {
			throw new RuntimeException("Api代理类接口必须是interface类");
		}
		return checkDeclareAnnotation();
	}

	/**
	 * 检查@Declare注解
	 */
	private boolean checkDeclareAnnotation() {
		Declare declare = AnnotationUtils.getAnnotation(apiInterface, Declare.class);
		if (declare == null) {
			logger.error(apiInterface.getSimpleName() + "类需要有@Declare注解。");
			return false;
		}
		return true;
	}

	/**
	 * Service代理的方法调用
	 */
	private static class ServiceInvocationHandler implements InvocationHandler {

		private final Class<?> apiInterface;

		/**
		 * 指定的serviceName
		 *
		 * @see DeclareApiProxyGenerator#assignedServiceName
		 */
		private final String assignedServiceName;

		public ServiceInvocationHandler(Class<?> apiInterface, String assignedServiceName) {
			this.apiInterface = apiInterface;
			this.assignedServiceName = assignedServiceName;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			IDynamicProxy serviceProxy = ModuleCenter.getServiceProxy(assignedServiceName);
			if (serviceProxy == null) {
				return null;
			}
			serviceProxy.setPrepareCallBack(this::analyseApiImplements);
			return serviceProxy.invoke(method.getName(), args);
		}

		private void analyseApiImplements(MethodInvoker methodInvoker) {
			ApiImplement apiImplement = AnnotationUtils.getAnnotation(methodInvoker.getMethod(), ApiImplement.class);
			if (apiImplement == null) {
				throw new RuntimeException(methodInvoker.getInstanceClass().getName() + "." + methodInvoker.getMethod() + "调用错误，该方法需要有@ApiImplement注解。");
			}
			if (!apiInterface.getName().equals(apiImplement.value())) {
				throw new RuntimeException(methodInvoker.getInstanceClass().getName() + "." + methodInvoker.getMethod() + "调用错误，该方法是(" + apiImplement.value() +
						")的实现，但Api调用方为(" + apiInterface.getName() + ")");
			}
		}
	}

}
