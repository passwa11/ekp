package com.landray.kmss.sys.filestore.circuitbreak;

public class CircuitBreakConstant {

    /**
     * 30分钟进行一次半开状态，尝试请求 30 * 60 * 1000
     */
    public static final int CIRCUIT_BREAK_HALF_OPEN_TIME = 30 * 60 * 1000;

    /**
     * 超过10分钟如果异常请求超过了80%,则开启熔断 10 * 60 * 1000;
     */
    public static final int CIRCUIT_BREAK_OPEN_TIME = 10 * 60 * 1000;

    /**
     * 异常率达到80%
     */
    public static final int CIRCUIT_BREAK_ERROR_PERCENTAGE = 80;

    private CircuitBreakConstant() {

    }
}
