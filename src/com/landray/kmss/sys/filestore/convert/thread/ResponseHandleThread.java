package com.landray.kmss.sys.filestore.convert.thread;

import com.landray.kmss.sys.filestore.convert.constant.ThreadTime;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseBase;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理结果线程
 */
public class ResponseHandleThread<T extends ConvertResponseBase> extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandleThread.class);
    // 处理结果线程是否开启
    private volatile Boolean handleRunning = false;

    static class Singleton {
        public static ResponseHandleThread instance = new ResponseHandleThread();
    }
    public static ResponseHandleThread getInstance() {
        return Singleton.instance;
    }
    private  ResponseHandleThread() {
    }


    @Override
    public void run() {
        while(handleRunning) {
            try{

                if(logger.isDebugEnabled()) {
                    logger.debug("处理请求结果线程【{}】, 队列数量:{}", this.getName(), ConvertResponseQueue.getInstance().size());
                }

                T convertResponse = (T) ConvertResponseQueue.getInstance().poll();
                if(convertResponse != null) {
                    // 处理响应
                    convertResponse.getConvertResponseHandle().doResponse(convertResponse);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(ThreadTime.ONE_THOUSAND_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Boolean getHandleRunning() {
        return handleRunning;
    }

    public void setHandleRunning(Boolean handleRunning) {
        this.handleRunning = handleRunning;
    }
}