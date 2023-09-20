package com.landray.kmss.common.module.core.proxy.generator;

import com.landray.kmss.common.module.core.cache.ICachedProxyGenerator;
import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.register.annotation.depend.ApiMethod;
import com.landray.kmss.common.module.core.register.annotation.depend.Dependency;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.util.SpringBeanUtil;
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
 * 根据类注释@Dependency及方法注释@ApiMethod，生成代理类
 *
 * @author 严明镜
 * @version 1.0 2021年03月08日
 */
public class ApiProxyGenerator<T> implements ICachedProxyGenerator<T> {

	protected static final Logger logger = LoggerFactory.getLogger(ApiProxyGenerator.class);

	private final Class<T> apiInterface;

	/**
	 * 指定service对象，优先级高于assignedServiceName及注解
	 */
	private final IBeanEnhance<?> assignedService;

	public ApiProxyGenerator(Class<T> apiInterface) {
		Assert.isTrue(apiInterface != null, "代理的Api不能为空");
		this.apiInterface = apiInterface;
		this.assignedService = null;
	}

	@Override
	public String getKey() {
		return "API" + "_" + apiInterface.getName() + (assignedService == null ? "" : "_" + assignedService.getBeanClass().getSimpleName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public T createProxy() {
		if (assignedService != null) {
			return (T) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(),
					new Class<?>[]{apiInterface}, new AssigndServiceInvocationHandler(assignedService));
		}
		return (T) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(),
				new Class<?>[]{apiInterface}, new ServiceInvocationHandler(getDependencyServiceName()));
	}

	/**
	 * 从@Dependency注解中获取ServiceName
	 */
	private String getDependencyServiceName() {
		String defaultServiceName = null;
		Dependency dependency = AnnotationUtils.getAnnotation(this.apiInterface, Dependency.class);
		if (dependency != null && StringUtil.isNotNull(dependency.value())) {
			defaultServiceName = dependency.value();
		}
		return defaultServiceName;
	}

	@Override
	public boolean valid() {
		if (apiInterface == null || !apiInterface.isInterface()) {
			logger.error("Api代理类接口必须是interface类");
			return false;
		}
		return checkDependencyAnnotation();
	}

	/**
	 * 检查@Dependency注解
	 */
	private boolean checkDependencyAnnotation() {
		Dependency dependency = AnnotationUtils.getAnnotation(apiInterface, Dependency.class);
		if (dependency == null || StringUtil.isNull(dependency.value())) {
			logger.error(apiInterface.getSimpleName() + "类需要有@Dependency注解，且value对应的ServiceBean不可为空。");
			return false;
		}
		//尝试获取ServiceBean在系统中是否存在
		try {
			Object serviceBean = SpringBeanUtil.getBean(dependency.value());
			if (serviceBean == null) {
				return false;
			}
		} catch (RuntimeException e) {
			if(logger.isDebugEnabled()){
				logger.debug("ServiceBean不存在：" + dependency.value());
			}
			return false;
		}
		return true;
	}

	protected static String getMethodName(Method method) {
		ApiMethod apiMethod = AnnotationUtils.getAnnotation(method, ApiMethod.class);
		if (apiMethod != null && StringUtil.isNotNull(apiMethod.value())) {
			return apiMethod.value();
		}
		return method.getName();
	}

	/**
	 * 指定Service的方法调用
	 */
	private static class AssigndServiceInvocationHandler implements InvocationHandler {

		private final IBeanEnhance<?> service;

		public AssigndServiceInvocationHandler(IBeanEnhance<?> service) {
			this.service = service;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = getMethodName(method);
			return service.invoke(methodName, args);
		}
	}

	/**
	 * Service代理的方法调用
	 */
	private static class ServiceInvocationHandler implements InvocationHandler {

		private final String defaultServiceName;

		public ServiceInvocationHandler(String defaultServiceName) {
			this.defaultServiceName = defaultServiceName;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = getMethodName(method);
			IDynamicProxy serviceProxy = ModuleCenter.getServiceProxy(getServiceName(method));
			if (serviceProxy == null) {
				return null;
			}
			return serviceProxy.invoke(methodName, args);
		}

		private String getServiceName(Method method) {
			ApiMethod apiMethod = AnnotationUtils.getAnnotation(method, ApiMethod.class);
			if (apiMethod != null && StringUtil.isNotNull(apiMethod.serviceBean())) {
				return apiMethod.serviceBean();
			}
			return defaultServiceName;
		}
	}

}
