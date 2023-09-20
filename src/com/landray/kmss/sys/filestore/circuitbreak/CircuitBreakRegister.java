package com.landray.kmss.sys.filestore.circuitbreak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 厂商注册是否使用熔断
 */
public class CircuitBreakRegister {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakRegister.class);

    private static Map<String, Boolean> registers = new ConcurrentHashMap<>();

    static class Singleton {
        private static CircuitBreakRegister instance =  new CircuitBreakRegister();
    }

    public CircuitBreakRegister getInstance() {
        return Singleton.instance;
    }

    /**
     * 注册服务
     * @param serverName
     */
    public static void register(String serverName) {
        if(registers.containsKey(serverName)) {
            return;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("注册的厂商服务是:{}", serverName);
        }
        registers.put(serverName, true);
    }

    /**
     * 删除服务
     * @param serverName
     */
    public static void remove(String serverName) {
        if(logger.isDebugEnabled()) {
            logger.debug("删除厂商的服务计数器和服务:{}", serverName);
        }
        if(registers.containsKey(serverName)) {
            //删除计数器
            Counter.getInstance().remove(serverName);
            //删除注册服务
            registers.remove(serverName);
        }
    }

    /**
     * 是否使用熔断
     * @param serverName
     * @return
     */
    public static Boolean circuitBreak(String serverName) {
        return registers.containsKey(serverName);
    }

}
