package com.landray.kmss.common.module.core.util;

import com.landray.kmss.common.module.util.ExceptionUtil;
import com.landray.kmss.common.module.util.WarnLevel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 模块中心使用的class操作工具，区别在于发生异常时，根据参数是以RuntimeException的方式抛出或仅打印错误说明
 *
 * @author 严明镜
 * @version 1.0 2021年03月17日
 */
public class RuntimeClassUtil {

	/**
	 * 根据完整包名找到类
	 */
	public static Class<?> forName(String className) {
		return forName(className, WarnLevel.ERROR);
	}

	/**
	 * 根据完整包名找到类
	 */
	public static Class<?> forNameNoThrow(String className) {
		return forName(className, WarnLevel.WARN);
	}

	/**
	 * 根据完整包名找到类
	 */
	public static Class<?> forNameSilent(String className) {
		return forName(className, WarnLevel.DEBUG);
	}

	/**
	 * 根据完整包名找到类
	 */
	private static Class<?> forName(String className, WarnLevel level) {
		ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();
		try {
			return org.springframework.util.ClassUtils.forName(className, classLoader);
		} catch (ClassNotFoundException e) {
			ExceptionUtil.handleException("未找到类:" + className, e, level);
		}
		if (WarnLevel.ERROR.equals(level)) {
			//should never reach here
			throw new RuntimeException("未找到类:" + className);
		} else {
			return null;
		}
	}

	/**
	 * 创建类的实例，如果出现异常则抛出
	 */
	public static <T> T newInstance(Class<T> clz) {
		return newInstance(clz, WarnLevel.ERROR);
	}

	/**
	 * 创建类的实例，如果出现异常则不抛出
	 */
	public static <T> T newInstanceNoThorw(Class<T> clz) {
		return newInstance(clz, WarnLevel.WARN);
	}

	/**
	 * 创建类的实例，如果出现异常则忽略
	 */
	public static <T> T newInstanceSilent(Class<T> clz) {
		return newInstance(clz, WarnLevel.DEBUG);
	}

	/**
	 * 创建类的实例，如果出现异常则抛出
	 */
	private static <T> T newInstance(Class<T> clz, WarnLevel level) {
		try {
			if (clz != null) {
				return clz.newInstance();
			}
		} catch (IllegalAccessException e) {
			ExceptionUtil.handleException("无法访问类构造函数:" + clz, e, level);
		} catch (InstantiationException e) {
			ExceptionUtil.handleException("无法实例化类:" + clz, e, level);
		}
		if (WarnLevel.ERROR.equals(level)) {
			//should never reach here
			throw new RuntimeException("无法实例化类:" + clz);
		} else {
			return null;
		}
	}

	/**
	 * 获取静态字段值
	 *
	 * @param fullClassName 类名全称
	 * @param fieldName     字段名
	 * @param returnType    返回值类型
	 * @return 属性值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getStaticFiled(String fullClassName, String fieldName, Class<T> returnType) {
		Class<?> clz = forName(fullClassName);
		try {
			Field field = clz.getDeclaredField(fieldName);
			field.setAccessible(true);
			if (!field.getType().equals(returnType)) {
				throw new NoSuchFieldException("字段返回值 " + field.getType() + " 与期望的 " + returnType + " 不符");
			}
			if (!Modifier.isStatic(field.getModifiers())) {
				throw new NoSuchFieldException(fieldName + "非静态属性值");
			}
			return (T) field.get(clz);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			ExceptionUtil.throwRuntimeException("获取静态属性值失败，(" + fullClassName + " 类，返回值 " + returnType + "，字段名 " + fieldName + ")", e);
		}
		return null;
	}
}
