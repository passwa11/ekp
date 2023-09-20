package com.landray.kmss.sys.restservice.client.hystrix.command;

import com.landray.kmss.sys.restservice.client.hystrix.CommandAction;
import com.landray.kmss.sys.restservice.client.hystrix.conf.CommandConfiguration;
import com.landray.kmss.util.StringUtil;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;

/**
 * 通用指令，配置了执行内容和回退
 *
 * @author 苏运彬
 * @date 2021/7/15
 * @param <T>
 */
public class GenericCommand<T> extends HystrixCommand {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GenericCommand.class);
    private final CommandAction<T> commandAction;//行为
    private final CommandConfiguration commandConfiguration;//配置

    public GenericCommand(CommandConfiguration commandConfiguration, CommandAction commandAction) {
        super(buildSetter(commandConfiguration));
        this.commandAction = commandAction;
        this.commandConfiguration = commandConfiguration;
    }

    /**
     * 根据配置的内容更新指令实例对应的配置，没有配置的话还是使用的默认配置
     * @param commandConfiguration
     * @return
     */
    private static Setter buildSetter(CommandConfiguration commandConfiguration){
        if(logger.isInfoEnabled()){
            logger.info("build Setter for create GenericCommand");
        }
        if(commandConfiguration.getInstanceProperties() != null){
            ConfigurationManager.loadProperties(commandConfiguration.getInstanceProperties());
        }
        Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandConfiguration.getCommandGroupKey()));
        if(StringUtil.isNotNull(commandConfiguration.getCommandKey())){
            setter.andCommandKey(HystrixCommandKey.Factory.asKey(commandConfiguration.getCommandKey()));
        }
        if(StringUtil.isNotNull(commandConfiguration.getThreadPoolKey())){
            setter.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(commandConfiguration.getThreadPoolKey()));
        }
        return setter;
    }

    @Override
    protected T run() throws Exception {
        if(logger.isInfoEnabled()){
            logger.info("begin execute command："+getCommandKey().name());
        }

        T result = commandAction.execute();

        if(logger.isInfoEnabled()){
            logger.info("complete execute command："+getCommandKey().name()+"，result："+result);
        }
        return result;
    }

    @Override
    protected T getFallback() {
        if(logger.isInfoEnabled()){
            logger.info("fallback command："+getCommandKey().name());
        }

        if(commandAction.hasFallback()){//自定义fallBack
            if(logger.isInfoEnabled()){
                logger.info("execute custom fallback");
            }

            return commandAction.fallBack();
        }else{//默认fallBack
            if(logger.isInfoEnabled()){
                logger.info("execute default fallback");
            }

            return getDefaultFallBack();
        }
    }

    /**
     * 默认的fallBack
     * @return
     */
    protected T getDefaultFallBack(){
        Throwable throwable = this.getExecutionException();
        if (throwable == null){
            if(logger.isErrorEnabled()){
                logger.error("GenericCommand run fail，throw unknow exception");
            }
        }
        //非Hystrix造成的异常
        if (this.isFailedExecution()) {
            if(logger.isErrorEnabled()){
                logger.error("GenericCommand run fail，" + throwable.getMessage(),throwable);
            }
        }
        //Hystrix引起的异常
        if (throwable instanceof HystrixTimeoutException){
            if(logger.isErrorEnabled()){
                logger.error("GenericCommand run timeout，" + throwable.getMessage());
            }
        }else if (throwable instanceof HystrixBadRequestException){
            if(logger.isErrorEnabled()){
                logger.error("GenericCommand request fail，"+throwable.getMessage());
            }
        }else if (throwable instanceof HystrixRuntimeException){
            if(logger.isErrorEnabled()){
                logger.error("GenericCommand run fail，"+throwable.getMessage());
            }
        }else if(logger.isErrorEnabled()){
            logger.error("GenericCommand run fail，"+throwable.getMessage());
        }
        return null;
    }
}
