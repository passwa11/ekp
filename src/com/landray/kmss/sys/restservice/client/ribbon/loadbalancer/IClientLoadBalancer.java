package com.landray.kmss.sys.restservice.client.ribbon.loadbalancer;

import com.landray.kmss.sys.restservice.client.ribbon.IAction;
import com.netflix.client.ClientException;
import com.netflix.loadbalancer.ILoadBalancer;

/**
 * 客户端负载均衡器，适配ekp一个客户端对应多个应用的场景的接口
 *
 * @author 苏运彬
 * @date 2021/7/28
 */
public interface IClientLoadBalancer {
    /**
     * 获取appName对应的LoadBalancer
     * @param appName
     * @return
     * @throws ClientException
     */
    ILoadBalancer getLoadBalancerByAppName(String appName) throws ClientException;

    /**
     * 负载均衡的执行
     * @param appName
     * @param iAction
     * @param <T>
     * @return
     */
    <T> T executeWithLoadBalancer(String appName, IAction<T> iAction);
}
