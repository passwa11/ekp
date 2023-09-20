package com.landray.kmss.sys.restservice.client.hystrix.command;

import com.landray.kmss.sys.restservice.client.hystrix.CommandAction;
import com.landray.kmss.sys.restservice.client.hystrix.conf.CommandConfiguration;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;

/**
 * 指令工厂，根据配置和行为生成可执行的指令
 *
 * @author 苏运彬
 * @date 2021/7/15
 */
public class CommandFactory {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommandFactory.class);

    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 创建可执行指令
     * @param commandConfiguration
     * @param commandAction
     * @param <T>
     * @return
     */
    public <T>HystrixCommand<T> createCommand(CommandConfiguration commandConfiguration, CommandAction<T> commandAction) {
        if(logger.isInfoEnabled()){
            logger.info("Create GenericCommand");
        }
        commandConfiguration = CommandConfiguration.getOrDefaultConfiguration(commandConfiguration);
        HystrixCommand hystrixCommand = new GenericCommand<T>(commandConfiguration,commandAction);
        return hystrixCommand;
    }

    /**
     * 创建可执行指令
     * @param commandConfiguration
     * @param commandAction
     * @param <T>
     * @return
     */
    public <T>HystrixCommand<T> createCommand(CommandAction<T> commandAction) {
        if(logger.isInfoEnabled()){
            logger.info("Create GenericCommand");
        }
        CommandConfiguration commandConfiguration = CommandConfiguration.getOrDefaultConfiguration(null);
        HystrixCommand hystrixCommand = new GenericCommand<T>(commandConfiguration,commandAction);
        return hystrixCommand;
    }
}
