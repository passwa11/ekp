package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 *  pc端静态资源压缩线程类
 * @link{com.landray.kmss.sys.ui.compressor.PcCompressService#synchCompressAll()} 中调用
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-16
 */
public class PcCompressRunner implements Callable<String> {

    private final static Logger logger = LoggerFactory.getLogger(PcCompressRunner.class);

    private final PcCompressTaskInfo taskInfo;

    public PcCompressRunner(PcCompressTaskInfo taskInfo){
        super();
        this.taskInfo = taskInfo;
    }

    @Override
    public String call() throws Exception {
        try {
            PcCompressTaskFactory.getTask(taskInfo).run();
            return taskInfo.getName();
        } catch (RuntimeException e) {
            logger.error("压缩线程执行过程出错，任务name:{}", taskInfo.getName(), e);
            throw e;
        }
    }
}
