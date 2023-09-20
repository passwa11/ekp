package com.landray.kmss.sys.restservice.client.ribbon;

/**
 * 提供负载均衡上下文参数的setter/getter方法接口定义，在负载均衡上下文中使用
 *
 * @author 苏运彬
 * @date 2021/7/15
 */
public interface IEkpLoadBalancerContext<ClientType> {
    /**
     * 获取客户端
     * @return
     */
    ClientType getClient();

    /**
     * 设置客户端
     * @param client
     */
    void setClient(ClientType client);
}
