package com.landray.kmss.sys.filestore.circuitbreak;

import static com.landray.kmss.sys.filestore.circuitbreak.CircuitBreakConstant.*;

import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 熔断计数器
 */
public class Counter {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);

    private final Map<String, CounterElement> counters = new ConcurrentHashMap<>();

    private  Counter() {
    }
    public static Counter getInstance() {
        return Singleton.instance;
    }

    static class Singleton {
        private static Counter instance = new Counter();
    }
    /**
     * 删除
     * @param serverName
     */
    public Counter remove(String serverName) {
        counters.remove(serverName);
        return this;
    }

    /**
     * 添加
     * @param serverName
     */
    public Counter add(String serverName) {
        if(counters.containsKey(serverName)) {
            return this;
        }
        CounterElement counterElement = new CounterElement();
        counters.put(serverName, counterElement);
        return this;
    }

    /**
     * 是否开启熔断
     */
    public Boolean isOpenCircuitBreak(String serverName) {
        CounterElement counterElement =  counters.get(serverName);
        if(counterElement == null) {
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("【{}】请求的总次数：{},异常次数：{},熔断状态：{}", serverName,
                    counterElement.requestCount.get(), counterElement.errorCount.get(), counterElement.atomicStatus.get());
        }

        if (counterElement.atomicStatus.get().equals(Status.OPEN)) {
            if(logger.isDebugEnabled()) {
                logger.warn("【{}】转换业务开启熔断机制,熔断开关是:OPEN", serverName);
            }

            long current = System.currentTimeMillis();
            // 默认：30分钟进行一次半开状态，尝试请求。如果请求成功，则关闭，否则继续开启熔断
            if ((current - counterElement.latestPeriod.get())
                    >= CIRCUIT_BREAK_HALF_OPEN_TIME) {
                logger.warn("【{}】转换业务已经开启了熔断机制,所以{}分钟将开关变为HALF_OPEN,尝试请求。"
                        , serverName, CIRCUIT_BREAK_HALF_OPEN_TIME);

                counterElement.atomicStatus.set(Status.HALF_OPEN);
                counterElement.latestPeriod.set(current);
            }

            //如果是半开状态，则发起请求一次。请求成功，状态是CLOSE,否则是OPEN
            if (counterElement.atomicStatus.get().equals(Status.HALF_OPEN)) {
                logger.warn("【{}】在HALF_OPEN的开关下尝试请求", serverName);
               return false;
            }

            return true;
        }

        return false;
    }

    /**
     * 累计请求统计
     */
    public void incrementRequestCount(String serverName) {
        CounterElement counterElement =  counters.get(serverName);
        if(counterElement == null) {
            return;
        }
        counterElement.requestCount.incrementAndGet();
    }

    /**
     * 异常统计：
     * 1.异常次数加1
     * 2.超过10分钟如果异常请求超过了80%,则开启熔断
     * 3.半开状态请求一次，出问题了，则继续开启熔断
     * @param serverName
     * @return
     */
    public Boolean handleError(String serverName, Exception e) {

        // 网络异常实现熔断机制
        if (!isNetworkError(e)) {
          //  resetCounter(serverName, current, Status.CLOSED);
            return false;
        }
        CounterElement counterElement =  counters.get(serverName);
        if(counterElement == null) {
            return false;
        }
        long current = System.currentTimeMillis();

        if(logger.isDebugEnabled()) {
            logger.debug("【{}】网络异常,时间间隔：{}", serverName,
                    ( current - counterElement.latestPeriod.get()));
        }

        // 异常数统计
        counterElement.errorCount.incrementAndGet();

        // 默认：超过10分钟如果异常请求超过了50%,则开启熔断
        if((current - counterElement.latestPeriod.get()) >= CIRCUIT_BREAK_OPEN_TIME) {
            logger.warn("【{}】网络异常超过了{}分钟，进行熔断决策", serverName, CIRCUIT_BREAK_OPEN_TIME);

            counterElement.latestPeriod.set(current);
            int totalRequest = counterElement.requestCount.getAndSet(0);
            int totalErrorRequest = counterElement.errorCount.getAndSet(0);
            int errorPercentage = (int) ((double) totalErrorRequest / totalRequest * 100);
            logger.warn("【{}】网络异常超过了{}分钟，异常率为：{}" ,
                    serverName, CIRCUIT_BREAK_OPEN_TIME, errorPercentage);

            if (errorPercentage > CIRCUIT_BREAK_ERROR_PERCENTAGE) {
                logger.warn("异常超过了{}百分比,开启熔断.", CIRCUIT_BREAK_ERROR_PERCENTAGE);
                resetCounter(serverName, current, Status.OPEN);
                return true;
            }
        }


        // 默认：半开状态请求一次，出问题了，则继续开启熔断
        if(counterElement.atomicStatus.get().equals(Status.HALF_OPEN)) {
            logger.warn("【{}】状态为HALF_OPEN,尝试请求,还是网络问题.可能需要继续熔断.", serverName);
            resetCounter(serverName, current, Status.OPEN);
        }

        return false;
    }

    /**
     * 重置统计器
     * @param serverName
     */
    public void recover(String serverName) {
        CounterElement counterElement =  counters.get(serverName);
        if(counterElement == null) {
            return;
        }
      //  if(counterElement.atomicStatus.get().equals(Status.HALF_OPEN)) {
            resetCounter(serverName, System.currentTimeMillis(), Status.CLOSED);
      //  }
    }
    /**
     * 更新计数器
     * @param time  时间
     * @param status 状态
     */
    public void resetCounter(String serverName, Long time, Status status) {
        CounterElement counterElement =  counters.get(serverName);
        counterElement.requestCount.getAndSet(0);
        counterElement.errorCount.getAndSet(0);
        counterElement.latestPeriod.set(time);
        counterElement.atomicStatus.set(status);
    }

    /**
     * 是否是网络异常
     * @param e
     * @return
     */
    public Boolean isNetworkError(Exception e) {

       return e.getCause() instanceof SocketTimeoutException
               || e.getCause() instanceof ConnectException
               || e.getCause() instanceof UnknownHostException
               || e.getCause() instanceof ConnectTimeoutException;

    }

    /**
     * 统计器元素
     */
    class CounterElement {
        public final AtomicInteger requestCount = new AtomicInteger(0); // 请求次数
        public final AtomicInteger errorCount = new AtomicInteger(0); // 错误次数
        public final AtomicLong latestPeriod = new AtomicLong(System.currentTimeMillis()); // 时间记录
        public final AtomicReference<Status> atomicStatus = new AtomicReference<Status>(Status.CLOSED); // 状态
    }

}
