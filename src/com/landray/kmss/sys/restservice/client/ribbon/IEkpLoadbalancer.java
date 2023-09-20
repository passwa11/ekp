package com.landray.kmss.sys.restservice.client.ribbon;

/**
 * 负载均衡接口，不同的客户端实现负载均衡器时，需要实现该接口，在创建loadBalancer后要根据appName进行serverList的更新
 *
 * @author 苏运彬
 * @date 2021/7/15
 */
public interface IEkpLoadbalancer {
    /**
     * loadbalancer创建后，再次更新服务列表（带appName)
     * @param appName
     */
    void updateListOfServersAfterCreate(String appName);
}