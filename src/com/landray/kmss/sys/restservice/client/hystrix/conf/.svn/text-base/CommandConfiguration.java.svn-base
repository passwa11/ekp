package com.landray.kmss.sys.restservice.client.hystrix.conf;

import com.landray.kmss.sys.restservice.client.hystrix.CommandConstants;
import com.landray.kmss.sys.restservice.client.hystrix.command.GenericCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 指令配置，用于{@link GenericCommand}初始化<br/>
 *
 * @author 苏运彬
 * @date 2021/7/15
 */
public class CommandConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(CommandConfiguration.class);
    /**
     * 分组key，必须
     */
    private final String commandGroupKey;
    /**
     * 指令key，默认为类的名称，非必须
     */
    private String commandKey;
    /**
     * 线程key，非必须
     */
    private String threadPoolKey;
    /**
     * 实例属性，用于覆盖默认配置，实现实例的局部配置，自定义properties，非必须
     */
    private Properties instanceProperties;

    /**
     * 默认配置
     */
    public CommandConfiguration() {
        this.commandGroupKey = CommandConstants.DEFAULT_GROUP_KEY;
    }

    public CommandConfiguration(String commandGroupKey){
        this.commandGroupKey = commandGroupKey;
    }

    public CommandConfiguration(String commandGroupKey, String commandKey, String threadPoolKey) {
        this.commandGroupKey = commandGroupKey;
        this.commandKey = commandKey;
        this.threadPoolKey = threadPoolKey;
    }

    public CommandConfiguration(String commandGroupKey, Properties instanceProperties) {
        this.commandGroupKey = commandGroupKey;
        this.instanceProperties = instanceProperties;
    }

    public CommandConfiguration(String commandGroupKey, String commandKey, String threadPoolKey, Properties instanceProperties) {
        this.commandGroupKey = commandGroupKey;
        this.commandKey = commandKey;
        this.threadPoolKey = threadPoolKey;
        this.instanceProperties = instanceProperties;
    }

    public String getCommandGroupKey() {
        return commandGroupKey;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public String getThreadPoolKey() {
        return threadPoolKey;
    }

    public Properties getInstanceProperties() {
        return instanceProperties;
    }

    /**
     * 获取一个默认配置
     * @return
     */
    public static CommandConfiguration getOrDefaultConfiguration(CommandConfiguration commandConfiguration){
        if(commandConfiguration == null){
            if(logger.isInfoEnabled()){
                logger.info("Current commandConfiguration is null，so get default commandConfiguration");
            }
            return new CommandConfiguration();
        }
        return commandConfiguration;
    }
}
