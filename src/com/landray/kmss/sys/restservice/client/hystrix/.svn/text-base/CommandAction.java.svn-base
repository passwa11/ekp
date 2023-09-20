package com.landray.kmss.sys.restservice.client.hystrix;

/**
 * 指令操作内容（行为）
 *
 * @author 苏运彬
 * @date 2021/7/15
 * @param <T>
 */
public interface CommandAction<T> {
    /**
     * 执行内容
     * @return
     * @throws Exception
     */
    T execute() throws Exception;

    /**
     * 回退处理，即调用超时，执行异常等之后调用的友好响应（兜底方法）<br/>
     * 可以不实现，则使用默认的fallBack
     * @return
     */
    default T fallBack(){return null;};

    /**
     * 是否配置回退处理，搭配fallBack一起使用<br/>
     * 若是需要自定义fallback方法，该方法需要返回true，否则默认为false，表示使用默认回退处理
     * @return
     */
    default boolean hasFallback(){return false;};
}
