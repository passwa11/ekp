package com.landray.kmss.sys.restservice.client;

/**
 * 
 * 为Api接口生成代理对象的工厂
 * @author 陈进科
 * 2019-03-15
 * @param <C> 提供远程访问的客户端
 */
public interface IProxyFactory<C> {

    /**
     * 根据一个描述接口，使用client完成远程段勇
     * @param descriptorInterface
     * @param client
     * @return 返回描述接口的代理对象，使得程序调用更优雅
     */
    public <T> T create(Class<T> descriptorInterface, C client);
    
    /**
     * 根据一个描述接口，使用client完成远程段勇
     * @param descriptorInterface
     * @param client
     * @return 返回描述接口的代理对象，使得程序调用更优雅
     */
    public <T> T create(String descriptorInterface, C client);
}
