package com.landray.kmss.sys.restservice.client.ribbon;

import com.netflix.loadbalancer.Server;

/**
 * 负载均衡的操作
 *
 * @author 苏运彬
 * @date 2021/07/14
 * @param <T>
 */
public interface IAction<T> {
    /**
     * 执行操作
     * @param server
     * @return
     */
    T execute(Server server);

    /**
     * 执行默认操作，执行操作失败后的兜底方法，默认返回null
     * @param appName
     * @return
     */
    T executeDefault(String appName);
}
