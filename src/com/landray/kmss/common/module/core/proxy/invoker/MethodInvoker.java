package com.landray.kmss.common.module.core.proxy.invoker;

import com.landray.kmss.common.module.core.exception.ModuleProxyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过反射调用方法
 * <p>
 * ps:为什么不用Spring的org.springframework.util.MethodInvoker?
 * 1.其未对class代理类进行处理
 * 2.其使用权重方式匹配method，但我们不需要
 * 3.我们需要对错误进行更清楚的描述
 *
 * @author 严明镜
 * @version 1.0 2021年03月03日
 */
public class MethodInvoker {

	private static final Logger logger = LoggerFactory.getLogger(MethodInvoker.class);

	/**
	 * 执行方法的实体类，当调用static方法时应为null
	 */
	private final Object instance;

	/**
	 * 执行方法的实体类的原生类型
	 */
	private final Class<?> instanceClass;

	/**
	 * 调用的方法
	 */
	private Method method;

	/**
	 * 调用方法使用的参数
	 */
	private Object[] arguments;

	/**
	 * methodInvoker是否准备好被调用
	 */
	private boolean isPrepared;

	/**
	 * 静态类构造时的instance应传入其Class
	 */
	public MethodInvoker(Object instance) {
		//静态类传入的是Class
		if (instance instanceof Class) {
			this.instance = null;
			this.instanceClass = (Class<?>) instance;
		} else {
			this.instance = getTarget(instance);
			if (AopUtils.isAopProxy(instance)) {
				//代理类
				this.instanceClass = AopUtils.getTargetClass(instance);
			} else {
				this.instanceClass = instance.getClass();
			}
		}
	}

	public Object getInstance() {
		return instance;
	}

	public Class<?> getInstanceClass() {
		return instanceClass;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * 执行invoke前的准备工作
	 *
	 * @param methodName 方法名
	 * @param parameters 参数
	 */
	public void prepare(String methodName, Object[] parameters) throws NoSuchMethodException {
		Class<?>[] parameterTypes = getParameterTypes(methodName, parameters);
		if (logger.isDebugEnabled()) {
			logger.debug("调用类中方法:" + methodName + " 实体类类型:" + instanceClass + " 实体类:" + instance +
					" 参数:" + getArraysClassString(parameters) + " 参数类型:" + Arrays.toString(parameterTypes));
		}
		this.method = matchMethod(methodName, parameterTypes);
		this.arguments = parameters;
		this.isPrepared = true;
	}

	/**
	 * 调用方法的入口，调用前应先调用prepare(...)
	 */
	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		if (!isPrepared) {
			logger.warn("prepare(...)未被执行或执行不成功，跳过调用" + method.getName() + "方法");
			return null;
		}
		if (arguments == null) {
			return method.invoke(instance);
		} else {
			return method.invoke(instance, arguments);
		}
	}

	private Method matchMethod(String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		if (parameterTypes == null) {
			return instanceClass.getMethod(methodName);
		} else {
			return instanceClass.getMethod(methodName, parameterTypes);
		}
	}

	/**
	 * 数组转为其Class的字符串
	 */
	private String getArraysClassString(Object[] parameters) {
		if (parameters == null || parameters.length < 1) {
			return null;
		}
		StringBuilder sb = new StringBuilder("[");
		for (Object parameter : parameters) {
			if (parameter != null) {
				sb.append(parameter.getClass());
			} else {
				sb.append("null");
			}
			sb.append(",");
		}
		return sb.substring(0, sb.length() - 1) + "]";
	}

	/**
	 * 根据传入参数返回对应的classTypes
	 *
	 * @param methodName 方法名
	 * @param parameters 参数列表
	 * @return 参数类型列表
	 */
	private Class<?>[] getParameterTypes(String methodName, Object[] parameters) throws NoSuchMethodException {
		if (parameters == null) {
			return null;
		}
		Class<?>[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterTypes[i] = guessParameterType(methodName, parameters, i);
		}
		return parameterTypes;
	}

	/**
	 * 根据参数类型及长度匹配合适的方法
	 *
	 * @param methodName 方法名
	 * @param parameters 参数列表
	 * @param index      匹配前几个参数
	 * @return 匹配参数的方法
	 */
	private Class<?> guessParameterType(String methodName, Object[] parameters, int index) throws NoSuchMethodException {
		List<Method> methods = findMethodsByName(methodName, parameters);
		if (methods.size() <= 0) {
			throw new NoSuchMethodException(instance + "的" + methodName + "方法，对于参数 " + Arrays.toString(parameters) + " 未能匹配(对前" + (index + 1) + "个参数进行匹配)。");
		}
		for (Method method : methods) {
			Class<?>[] methodParameterTypes = method.getParameterTypes();
			if (isParameterTypesMatch(parameters, index, methodParameterTypes)) {
				return methodParameterTypes[index];
			}
		}
		throw new NoSuchMethodException("找不到匹配参数的方法:" + methodName + " (" + getArraysClassString(parameters) + ")");
	}

	/**
	 * 匹配方法名与参数个数相同的所有方法
	 *
	 * @param methodName 方法名
	 * @return 所有匹配方法名的方法
	 */
	private List<Method> findMethodsByName(String methodName, Object[] parameters) {
		List<Method> matchMethods = new ArrayList<>();
		Method[] methods = instanceClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				if (method.getParameterTypes().length != parameters.length) {
					continue;
				}
				matchMethods.add(method);
			}
		}
		return matchMethods;
	}

	/**
	 * 参数类型是否都匹配，传入参数为空的始终认为匹配
	 *
	 * @param parameters           参数列表
	 * @param index                下标
	 * @param methodParameterTypes 进行匹配的参数类型
	 * @return 匹配/不匹配
	 */
	private boolean isParameterTypesMatch(Object[] parameters, int index, Class<?>[] methodParameterTypes) {
		for (int i = 0; i <= index; i++) {
			if (parameters[i] == null) {
				continue;
			}
			Class<?> methodParameterType = methodParameterTypes[i];
			methodParameterType = handleBasicType(methodParameterType);
			//类型不同且非父子关系
			if (!methodParameterType.equals(parameters[i].getClass()) && !(methodParameterType.isAssignableFrom(parameters[i].getClass()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 基础类型在定义时可能为基础类型，
	 * 但在获取传入参数时必定为对象，
	 * 此处转换基础类型为对象用于类型匹配
	 *
	 * @param methodParameterType 方法中定义的类型
	 * @return 如果为基础类型，返回基础类型的对象。
	 */
	private Class<?> handleBasicType(Class<?> methodParameterType) {
		if (methodParameterType.equals(Integer.TYPE)) {
			methodParameterType = Integer.class;
		} else if (methodParameterType.equals(Long.TYPE)) {
			methodParameterType = Long.class;
		} else if (methodParameterType.equals(Float.TYPE)) {
			methodParameterType = Float.class;
		} else if (methodParameterType.equals(Double.TYPE)) {
			methodParameterType = Double.class;
		} else if (methodParameterType.equals(Boolean.TYPE)) {
			methodParameterType = Boolean.class;
		} else if (methodParameterType.equals(Short.TYPE)) {
			methodParameterType = Short.class;
		} else if (methodParameterType.equals(Character.TYPE)) {
			methodParameterType = Character.class;
		} else if (methodParameterType.equals(Byte.TYPE)) {
			methodParameterType = Byte.class;
		}
		return methodParameterType;
	}

	/**
	 * 获取代理的目标类
	 *
	 * @param proxy 代理类
	 * @return 目标类
	 */
	public static Object getTarget(Object proxy) {
		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;
		}
		try {
			if (AopUtils.isJdkDynamicProxy(proxy)) {
				return getJdkDynamicProxyTargetObject(proxy);
			} else {
				return getCglibProxyTargetObject(proxy);
			}
		} catch (Exception e) {
			logger.error("获取代理的目标类时发生异常", e);
			throw new ModuleProxyException("获取代理的目标类时发生异常");
		}
	}

	/**
	 * CGLIBProxy 返回目标类
	 */
	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field cglibCallBack = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		cglibCallBack.setAccessible(true);
		Object dynamicAdvisedInterceptor = cglibCallBack.get(proxy);
		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
	}

	/**
	 * JDKProxy 返回目标类
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
		Field field = invocationHandler.getClass().getDeclaredField("advised");
		field.setAccessible(true);
		return ((AdvisedSupport) field.get(invocationHandler)).getTargetSource().getTarget();
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public boolean isPrepared() {
		return isPrepared;
	}
}
