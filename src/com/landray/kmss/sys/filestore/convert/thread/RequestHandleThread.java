package com.landray.kmss.sys.filestore.convert.thread;

import com.landray.kmss.sys.filestore.convert.constant.ThreadTime;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestBase;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.circuitbreak.CircuitBreakRegister;
import com.landray.kmss.sys.filestore.circuitbreak.CircuitBreakServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.landray.kmss.sys.filestore.convert.constant.HandleStatus.*;

/**
 * 转换线程
 */
public  class RequestHandleThread<T extends ConvertRequestBase> extends  Thread {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandleThread.class);
    private volatile Boolean convertRunning = false;
    static class Singleton {
        private static RequestHandleThread instance = new RequestHandleThread();
    }

    public static RequestHandleThread getInstance() {
        return Singleton.instance;
    }
    private RequestHandleThread() {

    }


    @Override
    public void run() {
        while(convertRunning) {
            try {
                if(logger.isDebugEnabled()) {
                    logger.debug("处理转换请求线程【{}】, 处理请求的数量:{}", this.getName(),ConvertRequestQueue.getInstance().size());
                }

               T convertRequest = (T) ConvertRequestQueue.getInstance().poll();

                if(convertRequest != null) {
                    if(logger.isDebugEnabled()) {
                        logger.debug("处理转换请求线程【{}】",  this.getName());
                    }

                    String serverName = convertRequest.getServerName();
                    ConvertRequestHandle convertRequestHandle = convertRequest.getConvertRequestHandle();

                    // 走熔断
                    if(CircuitBreakRegister.circuitBreak(serverName)) {
                        CircuitBreakServer.getInstance().agentRequest(convertRequestHandle, convertRequest);
                        continue;
                    }

                    if(REQUEST_TYPE_CONVERT.equals(convertRequest.getRequestType())) {
                        // 请求转换
                        convertRequestHandle.doConvert(convertRequest);
                    } else if(REQUEST_TYPE_CONVERT_INFO.equals(convertRequest.getRequestType())) {
                        // 获取转换信息
                        convertRequestHandle.getConvertInfo(convertRequest);
                    }else if(REQUEST_TYPE_DOWNLOAD.equals(convertRequest.getRequestType())) {
                        // 请求下载
                        convertRequestHandle.doConvertDownload(convertRequest);
                    }

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

    public Boolean getConvertRunning() {
        return convertRunning;
    }

    public void setConvertRunning(Boolean convertRunning) {
        this.convertRunning = convertRunning;
    }


}