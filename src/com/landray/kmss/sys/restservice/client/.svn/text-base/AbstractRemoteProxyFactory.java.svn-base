package com.landray.kmss.sys.restservice.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.landray.kmss.util.KeyLockFactory;
import com.landray.kmss.util.KeyLockFactory.KeyLock;

/**
 * 
 * 可缓存的远程客户端代理工厂
 * @author 陈进科
 * 2019-03-12
 */
public abstract class AbstractRemoteProxyFactory<C> implements IProxyFactory<C> {
    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    private Map<Class<?>, Object> proxyCache = new HashMap<>();
    private KeyLockFactory locker = new KeyLockFactory();
    
    @Override
    @SuppressWarnings("unchecked")
    public final <T> T create(Class<T> descriptorInterface, C client) {
        if(proxyCache.containsKey(descriptorInterface)){
            return (T)proxyCache.get(descriptorInterface);
        }
        if(!valid(descriptorInterface)){
            return null;
        }
        String name = descriptorInterface.getName();
        KeyLock keyLock = locker.getKeyLock(name).lock();
        try{
            return doCreate(descriptorInterface,client);
        }finally{
            keyLock.unlock();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final <T> T create(String clazz, C client) {
        try {
            Class<T> forName = (Class<T>)com.landray.kmss.util.ClassUtils.forName(clazz, ClassUtils.getDefaultClassLoader());
            return create(forName,client);
        } catch (ClassNotFoundException|LinkageError e) {
            logger.error(e.toString());
            return null;
        }
    }
    
    /**
     * 校验描述接口是否完备
     * @param descriptorInterface
     * @return
     */
    protected abstract <T> boolean valid(Class<T> descriptorInterface);
   
    /**
     * 创建动态代理
     * @param descriptorInterface
     * @param cloudClient
     * @return
     */
    protected abstract <T> T doCreate(Class<T> descriptorInterface, C cloudClient);
}
