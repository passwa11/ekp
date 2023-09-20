package com.landray.kmss.sys.restservice.client.ribbon.executor;

import com.landray.kmss.sys.restservice.client.ribbon.IAction;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;

import rx.Observable;

/**
 * 负载均衡操作执行器，根据应用对应的{@link ILoadBalancer}以及配置初始化{@link LoadBalancerCommand}执行命令
 *
 * @author 苏运彬
 * @date 2021/7/13
 */
public class LoadBalancerExecutor {
    /**
     * 执行方法之一，返回执行结果值
     * @param loadBalancer
     * @param iAction
     * @param <T>
     * @return
     */
    public static <T>T executeWithLoadBalancer(ILoadBalancer loadBalancer, IAction<T> iAction) {
        return executeWithLoadBalancer(buildLoadBalancerCommand(loadBalancer),iAction);
    }

    /**
     * 执行方法之一，返回Observable对象，可以对Observable操作
     * @param loadBalancer
     * @param iAction
     * @param <T>
     * @return
     */
    public static <T>Observable<T> observeWithLoadBalancer(ILoadBalancer loadBalancer, IAction<T> iAction) {
        return observeWithLoadBalancer(buildLoadBalancerCommand(loadBalancer),iAction);
    }

    /**
     * 执行方法之一，返回执行结果值
     * @param loadBalancerCommand
     * @param iAction
     * @param <T>
     * @return
     */
    public static <T>T executeWithLoadBalancer(LoadBalancerCommand loadBalancerCommand, IAction<T> iAction) {
        return observeWithLoadBalancer(loadBalancerCommand,iAction).toBlocking().first();
    }

    /**
     * 执行方法之一，返回Observable对象，可以对Observable操作
     * @param loadBalancerCommand
     * @param iAction
     * @param <T>
     * @return
     */
    public static <T>Observable<T> observeWithLoadBalancer(LoadBalancerCommand loadBalancerCommand, IAction<T> iAction) {
        return loadBalancerCommand.submit(new ServerOperation<T>() {
            @Override
            public Observable<T> call(Server server) {
                try {
                    return Observable.just((T) iAction.execute(server));
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }

    /**
     * 执行方法之一，返回执行结果值
     * @param loadBalancer
     * @param serverOperation
     * @param <T>
     * @return
     */
    public static <T>T executeWithLoadBalancer(ILoadBalancer loadBalancer, ServerOperation<T> serverOperation) {
        return observeWithLoadBalancer(buildLoadBalancerCommand(loadBalancer),serverOperation).toBlocking().first();
    }

    /**
     * 执行方法之一，返回Observable对象，可以对Observable操作
     * @param loadBalancer
     * @param serverOperation
     * @param <T>
     * @return
     */
    public static <T>Observable<T> observeWithLoadBalancer(ILoadBalancer loadBalancer, ServerOperation<T> serverOperation){
        return observeWithLoadBalancer(buildLoadBalancerCommand(loadBalancer),serverOperation);
    }

    /**
     * 执行方法之一，返回执行结果值
     * @param loadBalancerCommand
     * @param serverOperation
     * @param <T>
     * @return
     */
    public static <T>T executeWithLoadBalancer(LoadBalancerCommand loadBalancerCommand, ServerOperation<T> serverOperation) {
        return observeWithLoadBalancer(loadBalancerCommand,serverOperation).toBlocking().first();
    }

    /**
     * 执行方法之一，返回Observable对象，可以对Observable操作
     * @param loadBalancerCommand
     * @param serverOperation
     * @param <T>
     * @return
     */
    public static <T>Observable<T> observeWithLoadBalancer(LoadBalancerCommand loadBalancerCommand, ServerOperation<T> serverOperation) {
        return loadBalancerCommand.submit(serverOperation);
    }

    /**
     * 创建执行命令
     * @param loadBalancer
     * @return
     */
    public static LoadBalancerCommand buildLoadBalancerCommand(ILoadBalancer loadBalancer){
        IClientConfig loadBalancerConfig = ((BaseLoadBalancer)loadBalancer).getClientConfig();
        LoadBalancerCommand balancerCommand = LoadBalancerCommand.builder()
                .withLoadBalancer(loadBalancer)
                .withClientConfig(loadBalancerConfig)
                .build();
        return balancerCommand;
    }
}
