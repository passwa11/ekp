package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 *  pc端切换静态资源来源
 * @link{com.landray.kmss.sys.ui.compressor.PcCompressService#channelCompressRecource()} 中调用
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-16
 */
public class PcResourceChannelRunner implements Callable<String> {

    private final static Logger logger = LoggerFactory.getLogger(PcResourceChannelRunner.class);

    private final PcCompressTaskInfo taskInfo;
    //是否使用压缩资源
    private final boolean isUseCompress;

    public PcResourceChannelRunner(PcCompressTaskInfo taskInfo, boolean isUseCompress){
        super();
        this.isUseCompress = isUseCompress;
        this.taskInfo = taskInfo;
    }

    public PcResourceChannelRunner(PcCompressTaskInfo taskInfo){
        this(taskInfo, false);
    }

    @Override
    public String call() throws Exception {
        try {
            PcResourceChannelTaskFactory.getTask(taskInfo).isUseCompress(isUseCompress).run();
            return taskInfo.getName();
        } catch (RuntimeException e) {
            logger.error("切换静态资源线程执行过程出错，任务name:{}", taskInfo.getName(), e);
            throw e;
        }
    }
}
