package com.landray.kmss.sys.ui.service.spring;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.ui.service.ISysUiCompressExecutor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 合并压缩线程类<br/>
 * 依靠入参flag区分，主要功能为以下两个：<br/>
 * 1、负责遍历执行扩展点对应的ISysUiCompressExecutor.execute()方法<br/>
 * 2、负责移除生成的静态资源文件夹（/resource/dynamic_combination）<br/>
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-10-18
 */
public class CompressExecutorRunner implements Callable<String> {
    private static final Logger logger = LoggerFactory.getLogger(CompressExecutorRunner.class);
    /**
     * 用于判断执行压缩合并，还是移除文件
     * true--执行压缩合并逻辑
     * false--执行移除压缩合并文件
     */
    private boolean flag;

    /**
     * @param flag 压缩合并 or 移除资源
     */
    public CompressExecutorRunner(boolean flag){
        this.flag = flag;
    }
    @Override
    public String call() throws Exception {
        if(flag){
            //开关打开，执行合并压缩扩展点
            if (logger.isDebugEnabled()) {
                logger.debug("根据扩展点生成/resource/dynamic_combination目录");
            }
            IExtension[] extensions = Plugin.getExtensions(
                    ISysUiCompressExecutor.EXTENSION_POINT_ID,
                    ISysUiCompressExecutor.class.getName(), "executor");
            for(IExtension extension : extensions){
                ISysUiCompressExecutor executor = null;
                try{
                    executor = Plugin.getParamValue(extension, "bean");
                    executor.execute();
                } catch (Exception e){
                    logger.error("扩展点ISysUiCompressExecutor实现类{}执行出错，{}",executor, e);
                }
            }
        }else{
            //开关关闭，删除压缩合并路径的文件
            if (logger.isDebugEnabled()) {
                logger.debug("移除/resource/dynamic_combination目录");
            }
            //  /resource/dynamic_combination目录下的文件必须删除。
            File location = new File(ConfigLocationsUtil.getWebContentPath());
            File dynamicCombinationDir = new File(location, ISysUiCompressExecutor.COM_COMPRESS_SOURCE_PATH);
            if(dynamicCombinationDir.exists()){
                try {
                    FileUtils.deleteDirectory(dynamicCombinationDir);
                } catch (IOException e) {
                    logger.error("移除/resource/dynamic_combination目录出错");
                }
            }
        }
        return null;
    }
}
