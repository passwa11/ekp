package com.landray.kmss.util;

import com.landray.kmss.sys.config.dict.SysDataDict;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EKP专用的类工具
 */
public class ClassUtils {

	private static final KeyLockFactory keyLockFactory = new KeyLockFactory();
	/**
	 * 普通类对象的缓存，在EKP一般多用于反射com.landray.kmss空间下的类，比如Form，Model，BaseAppConfig，Service等
	 * 考虑到运行过程中的扩容，这里初始化的容量设置大一些，可以减少扩容次数
	 */
	private final static Map<String, Class<?>> commonClassCache =
			new ConcurrentHashMap<>(1024);

	/**
	 * 针对一些曾经请求过，但是无法加载的类，进行标记，防止重复犯错
	 * 无法找到的类一般不多见，初始化容量128，如果这个容器内容过大，表示业务可能在错误的调用
	 */
	private static final Map<String, Boolean> unreachableClassCache =
			new ConcurrentHashMap<>(256);

	/**
	 * 针对EKP特有的短类名设计的缓存，存储内容大致是 KmReviewMain->com.landray.kmss.km.review.model.KmReviewMain
	 * 这些信息是从数据字典里读取到的，所以只适用于加载ModelClass
	 * 它存在初始化的过程，所以有个
	 */
	private volatile static Map<String, String> modelNames = null;


	/**
	 * 通过类名找到对应的class对象，过期方法，建议替换为{@link #forNameNullable(String)}
	 * @param className 可以是类全名，如果是短类名，则必须在EKP的数据字典里有对应的全路径
	 * @return class对象
	 * @throws ClassNotFoundException 类找不到的时候抛出
	 */
	@Deprecated
	public static Class<?> forName(String className) throws ClassNotFoundException {
		return forName(className, org.springframework.util.ClassUtils.getDefaultClassLoader());
	}

	/**
	 * 通过类名找到对应的class对象，过期方法，建议替换为{@link #forNameNullable(String)}
	 * @param className 可以是类全名，如果是短类名，则必须在EKP的数据字典里有对应的全路径
	 * @param classLoader 类加载器，可空，一般是当前线程的类加载器
	 * @return class对象
	 * @throws ClassNotFoundException 类找不到的时候抛出
	 */
	@Deprecated
	public static Class<?> forName(String className, ClassLoader classLoader)
			throws ClassNotFoundException, LinkageError {
		return forNameInner(className,classLoader,false);
	}

	/**
	 * 通过类名找到对应的class对象
	 * @param className 可以是类全名，如果是短类名，则必须在EKP的数据字典里有对应的全路径
	 * @return 如果存在返回对象，否则返回null
	 */
	public static Class<?> forNameNullable(String className){
		try {
			return forNameInner(className,org.springframework.util.ClassUtils.getDefaultClassLoader(),true);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 找类对象的具体实现逻辑
	 * @param className 可以是类全名，如果是短类名，则必须在EKP的数据字典里有对应的全路径
	 * @param classLoader 类加载器，可空，一般是当前线程的类加载器
	 * @param ignoreException 是否忽略过程中的异常
	 * @return 存在即返回class对象，如果不存在，当ignoreException=true表示忽略异常，如果没找到就返回null，否则抛异常。
	 * @throws ClassNotFoundException 当ignoreException=false且class对象不存在的时候
	 * @throws LinkageError 当ignoreException=false且class对象不存在的时候
	 */
	private static Class<?> forNameInner(String className, ClassLoader classLoader,boolean ignoreException)
			throws ClassNotFoundException, LinkageError {
		/*
		 * 优化点
		 * #1 优先处理不规范的入参（短类名），尽可能避免真实的 Class.forName
		 * #2 添加了ignoreException选项，防止构造异常信息（JDK构造异常是很耗时的）
		 * #3 记录加载失败的参数，快速返回（避免无谓的Class.forName）
		 */
		String clazzFullName = className;
		// #1 转换className,这样可以有效避免底层forName触发的ClassNotFoundException，
		// 异常的处理是抵消且不必要的
		if (!className.contains(".")) {
			clazzFullName = buildModelName().get(className);
			//如果没有找到长类名，结束
			if (StringUtils.isBlank(clazzFullName)) {
				if(!ignoreException){
					throw new ClassNotFoundException("ClassNotFound: "+clazzFullName);
				}else{
					return null;
				}
			}
		}
		//#2 如果曾经加载失败过，则结束
		if(unreachableClassCache.containsKey(clazzFullName)){
			if(!ignoreException){
				throw new ClassNotFoundException("Class has marked as not found: "+clazzFullName);
			}else{
				return null;
			}
		}
		//#3 优先缓存，再forName
		//有.的类名可以认定为全路径
		Class<?> classFromCache = commonClassCache.get(clazzFullName);
		if(classFromCache!=null){
			//程序大多数在此处返回
			return classFromCache;
		}else{
			KeyLockFactory.KeyLock keyLock =
					keyLockFactory.getKeyLock(clazzFullName);
			//等待，对一个classFullName来说，理论上会出现1-2次
			keyLock.lock();
			try{
				try{
					//获取锁,再第二次确认
					classFromCache = commonClassCache.get(clazzFullName);
					if(classFromCache!=null){
						return classFromCache;
					}
					//spring的接口不会返回null
					Class<?> aClass1 =
							org.springframework.util.ClassUtils.forName(clazzFullName, classLoader);
					commonClassCache.put(clazzFullName,aClass1);
					return aClass1;
				}catch (ClassNotFoundException| LinkageError t){
					//如果底层抛出了声明异常，则标记，方便下次跳过
					unreachableClassCache.put(clazzFullName,Boolean.TRUE);
					if(!ignoreException){
						throw new ClassNotFoundException("ClassNotFound : "+clazzFullName);
					}else{
						return null;
					}
				}
			}finally{
				keyLock.unlock();
			}
		}
	}

	/**
	 * 清空当前工具类已经缓存的类名与Class实例的对应关系
	 */
	public static void clear(){
		commonClassCache.clear();
		unreachableClassCache.clear();
	}

	/**
	 * 构造短类名映射
	 * @return
	 */
	private static Map<String, String> buildModelName() {
		if (modelNames == null) {
			synchronized(ClassUtils.class){
				if(modelNames==null){
					modelNames = new ConcurrentHashMap<String, String>(512);
					List<String> list = SysDataDict.getInstance().getModelInfoList();
					if (CollectionUtils.isNotEmpty(list)) {
						for (String modelName : list) {
							int i = modelName.lastIndexOf('.');
							String simpleName = modelName;
							if (i > -1) {
								simpleName = modelName.substring(i + 1);
							}
							modelNames.put(simpleName, modelName);
						}
					}
				}
			}
		}
		return modelNames;
	}
}
