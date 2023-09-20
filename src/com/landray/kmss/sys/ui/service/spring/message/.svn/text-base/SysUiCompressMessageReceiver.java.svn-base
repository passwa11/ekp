package com.landray.kmss.sys.ui.service.spring.message;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.ui.compressor.PcCompressService;
import com.landray.kmss.sys.ui.service.spring.CompressExecutorRunner;
import com.landray.kmss.sys.ui.util.PcJsOptimizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PC端静态资源压缩切换集群消息接收器
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-23
 */
public class SysUiCompressMessageReceiver implements IMessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(SysUiCompressMessageReceiver.class);
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    protected IMessageQueue messageQueue = new UniqueMessageQueue();

    @Override
    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void receiveMessage(IMessage message) throws Exception {
        if(!(message instanceof SysUiCompressMessage)){
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("接受到JS压缩合并的集群消息");
        }
        try{
            SysUiCompressMessage sysUiCompressMessage = (SysUiCompressMessage) message;
            CompressExecutorRunner runner;
            //模板js合并压缩加载开关
            if(sysUiCompressMessage.isOpenCompress()){
                runner = new CompressExecutorRunner(true);
            }else{
                runner = new CompressExecutorRunner(false);
            }
            EXECUTOR.invokeAll(Arrays.asList(runner));
        }catch (Exception e){
            logger.error("执行集群消息任务-压缩合并js任务时出错，{}", e);
        }
    }
}
