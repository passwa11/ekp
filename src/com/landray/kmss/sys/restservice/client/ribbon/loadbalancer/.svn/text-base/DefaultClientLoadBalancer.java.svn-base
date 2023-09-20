package com.landray.kmss.sys.restservice.client.ribbon.loadbalancer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.sys.restservice.client.ribbon.IAction;
import com.landray.kmss.sys.restservice.client.ribbon.IEkpLoadbalancer;
import com.landray.kmss.sys.restservice.client.ribbon.executor.LoadBalancerExecutor;
import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;

/**
 * 客户端负载均衡器，适配ekp一个客户端对应多个应用的场景，维护一个loadbalancer列表<br/>
 *
 * @author 苏运彬
 * @date 2021/7/15
 */
public class DefaultClientLoadBalancer implements IClientLoadBalancer {
    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultClientLoadBalancer.class);

    private final Map<String, ILoadBalancer> appNameLoadBalancerMap = new HashMap<>();
    private final IClientConfig loadBalancerConfig;

    public DefaultClientLoadBalancer(IClientConfig loadBalancerConfig){
        this.loadBalancerConfig = loadBalancerConfig;
    }

    public IClientConfig getLoadBalancerConfig() {
        return loadBalancerConfig;
    }

    /**
     *  根据AppName创建对应的loadBalancer，loadBalancer的配置是相同的<br/>
     *
     * @param appName
     * @param loadBalancerConfig
     * @return
     * @throws ClientException
     */
    @Override
    public synchronized ILoadBalancer getLoadBalancerByAppName(String appName) throws ClientException {
        if(logger.isInfoEnabled()){
            logger.info("get loadbalancer by appName："+appName);
        }

        ILoadBalancer loadBalancer = appNameLoadBalancerMap.get(appName);
        if(loadBalancer == null) {
            loadBalancer = ClientFactory.registerNamedLoadBalancerFromclientConfig(appName, loadBalancerConfig);
            ((IEkpLoadbalancer) loadBalancer).updateListOfServersAfterCreate(appName);
            appNameLoadBalancerMap.put(appName, loadBalancer);
        }

        return loadBalancer;
    }

    /**
     * 负载均衡（执行）
     * @param appName
     * @param iAction
     * @param <T>
     * @return
     */
    @Override
    public <T> T executeWithLoadBalancer(String appName, IAction<T> iAction){
        try{
            if(logger.isInfoEnabled()){
                logger.info("execute action with loadBalancer for [clientName:"+ loadBalancerConfig.getClientName() +"][AppName: "+appName+"]");
            }
            ILoadBalancer loadBalancer = this.getLoadBalancerByAppName(appName);
            return LoadBalancerExecutor.executeWithLoadBalancer(loadBalancer,iAction);
        }catch (ClientException e){
            if(logger.isErrorEnabled()){
                logger.error("Build loadBalancer fail for [clientName:"+ loadBalancerConfig.getClientName() +"][AppName: "+appName+"] and ExecuteDefault for [AppName: "+appName+"]",e);
            }
            return iAction.executeDefault(appName);
        }catch (Exception e){
            if(logger.isErrorEnabled()){
                logger.error("ExecuteWithLoadBalancer is fail for [clientName:"+ loadBalancerConfig.getClientName() +"][AppName: "+appName+"] and ExecuteDefault for [AppName: "+appName+"]",e);
            }
            return iAction.executeDefault(appName);
        }
    }

}
