package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.ui.compressor.config.PcCompressConfigPlugin;
import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  pc端静态资源服务类</br>
 *  压缩备份：{@link #synchCompressAll}</br>
 *  切换资源：{@link #channelCompressRecource}</br>
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-16
 */
public class PcCompressService {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    /**
     * 遍历扩展点压缩资源以及备份资源
     * @throws InterruptedException
     */
    public static synchronized void synchCompressAll() throws InterruptedException {
        //获取包装后的扩展点配置信息
        List<PcCompressTaskInfo> taskInfos = PcCompressConfigPlugin.getTaskInfos();
        List<PcCompressRunner> tasks = new ArrayList<>(taskInfos.size());
        //分发线程执行
        for(PcCompressTaskInfo taskInfo:taskInfos){
            PcCompressRunner runner = new PcCompressRunner(taskInfo);
            tasks.add(runner);
        }
        EXECUTOR.invokeAll(tasks);
    }

    /**
     * 遍历扩展点切换为压缩资源或者非压缩资源
     * @param isUseCompress 是否使用压缩后的文件资源
     * @throws InterruptedException
     */
    public static synchronized void channelCompressRecource(boolean isUseCompress) throws InterruptedException{
        //获取包装后的扩展点配置信息
        List<PcCompressTaskInfo> taskInfos = PcCompressConfigPlugin.getTaskInfos();
        List<PcResourceChannelRunner> tasks = new ArrayList<>(taskInfos.size());
        //分发线程执行
        for(PcCompressTaskInfo taskInfo:taskInfos){
            PcResourceChannelRunner runner = new PcResourceChannelRunner(taskInfo, isUseCompress);
            tasks.add(runner);
        }
        EXECUTOR.invokeAll(tasks);
    }

}
