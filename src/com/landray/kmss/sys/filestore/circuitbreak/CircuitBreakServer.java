package com.landray.kmss.sys.filestore.circuitbreak;

import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestBase;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

import static com.landray.kmss.sys.filestore.convert.constant.HandleStatus.*;

/**
 * 实现熔断请求
 * @param <T>
 */
public class CircuitBreakServer<T extends ConvertRequestBase> {

  private static final Logger logger = LoggerFactory.getLogger(CircuitBreakServer.class);
  private static ThreadPoolExecutor threadPoolExecutor = null;
  // 核心线程数量
  private static final int THREAD_CORE_POOL_SIZE = 6;
  // 最大线程数量
  private static final int THREAD_MAX_MUN_POOL_SIZE = 10;
  // 非核心线程保留时间
  private static final long THREAD_KEEP_ALIVE_TIME = 10;
  // 线程队列大小
  private static final int THREAD_QUEUE_SIZE = 20;

    /**
     * 初始化线程池
     * @throws Exception
     */
    static {
        int corePoolSize = THREAD_CORE_POOL_SIZE;
        int maximumPoolSize = THREAD_MAX_MUN_POOL_SIZE;
        long keepAliveTime = THREAD_KEEP_ALIVE_TIME;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(THREAD_QUEUE_SIZE);
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new RejectPolicy();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
    }

    static class Singleton {
        public static CircuitBreakServer instance = new CircuitBreakServer();
    }

    public static CircuitBreakServer getInstance() {
        return Singleton.instance;
    }


    public void agentRequest(ConvertRequestHandle convertRequestHandle,
                              T convertRequest) {
        String serverName = convertRequest.getServerName();
        Counter counter = Counter.getInstance().add(serverName);

        if (counter.isOpenCircuitBreak(serverName)) {
            if(logger.isDebugEnabled()) {
                logger.debug("开启了熔断，不执行请求......");
            }

            try {
                // 在熔断的情况下，将已经分配的队列改变为失败
                convertRequestHandle.convertFailure(convertRequest);
            } catch (Exception e) {
                logger.error("熔断下的队列从已分配变为失败出现问题:{}", e);
            }

            return;
        }

        // 熔断开关为CLOSE或HALF_OPEN,则可以请求
        handleRequest(counter,serverName, convertRequestHandle, convertRequest);
    }

    public void handleRequest(Counter counter, String serverName,
                               ConvertRequestHandle convertRequestHandle,
                              T convertRequest) {
        // 统计请求次数
        counter.incrementRequestCount(serverName);

        //隔离机制
        Future<Boolean> delegate = executeRequest(convertRequestHandle,  convertRequest);

        // 熔断处理
        try {
            delegate.get();
            // 重置统计器
            counter.recover(serverName);
        } catch (Exception e) {
            counter.handleError(serverName, e);
        }
    }

    /**
     * 线程池执行转换
     * @return
     */
    public Future<Boolean> executeRequest(ConvertRequestHandle convertRequestHandle, T convertRequest) {
        return  threadPoolExecutor.submit(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                Boolean isSuccessed = false;
                try {
                    if(convertRequest != null) {

                        if(REQUEST_TYPE_CONVERT.equals(convertRequest.getRequestType())) {
                            // 请求转换
                            convertRequest.getConvertRequestHandle().doConvert(convertRequest);
                        } else if(REQUEST_TYPE_CONVERT_INFO.equals(convertRequest.getRequestType())) {
                            // 请求获取转换信息
                            convertRequest.getConvertRequestHandle().getConvertInfo(convertRequest);
                        }else if(REQUEST_TYPE_DOWNLOAD.equals(convertRequest.getRequestType())) {
                            // 请求下载
                            convertRequest.getConvertRequestHandle().doConvertDownload(convertRequest);
                        }

                    }

                    isSuccessed = true;
                } catch (SocketTimeoutException e) {
                    isSuccessed = false;
                    throw e;
                } catch (ConnectException e) {
                    isSuccessed = false;
                    throw e;
                }catch (Exception e) {
                    isSuccessed = false;
                    throw e;
                }

                return isSuccessed;
            }

        });
    }


}
