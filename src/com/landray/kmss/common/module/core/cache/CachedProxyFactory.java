package com.landray.kmss.common.module.core.cache;

import com.landray.kmss.common.module.util.ExceptionUtil;
import com.landray.kmss.util.KeyLockFactory;
import com.landray.kmss.util.KeyLockFactory.KeyLock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 在生成代理逻辑之前加一层缓存
 *
 * @author 严明镜
 * @version 1.0 2021年03月11日
 */
public class CachedProxyFactory {

	private final KeyLockFactory locker = new KeyLockFactory();

	/**
	 * 缓存key根据接口和警告级别一起判断
	 */
	private final Map<String, Object> proxyCache = new HashMap<>(64);

	/**
	 * 标记那些代理对象为null的key
	 */
	private final Set<String> nullProxyKeys = new HashSet<>(64);

	public <T> T createProxy(ICachedProxyGenerator<T> generator) {
		if (generator.getKey() == null) {
			//不缓存直接创建
			return generateProxy(generator);
		} else {
			return generateCachedProxy(generator);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T generateCachedProxy(ICachedProxyGenerator<T> generator) {
		String key = generator.getKey();
		if (!generator.valid()) {
			return null;
		}
		//从缓存中取
		//如果有，则从proxyCache中
		if (proxyCache.containsKey(key)) {
			return (T) proxyCache.get(key);
		}
		//如果key构造过一次，但是返回null，则后续直接返回null，不再进行锁操作
		if (nullProxyKeys.contains(key)) {
			return null;
		}
		KeyLock keyLock = locker.getKeyLock(key).lock();
		try {
			T proxy = generateProxy(generator);
			if (proxy != null) {
				//如果非空，缓存起来
				proxyCache.put(key, proxy);
			} else {
				//如果null，则放到nullProxySet标记
				nullProxyKeys.add(key);
			}
			//只有构造对象时异常才需要重试
			return proxy;
		} finally {
			keyLock.unlock();
		}
	}

	private <T> T generateProxy(ICachedProxyGenerator<T> generator) {
		try {
			return generator.createProxy();
		} catch (Exception e) {
			ExceptionUtil.printException("获取代理对象时发生异常", e);
			return null;
		}
	}
}
