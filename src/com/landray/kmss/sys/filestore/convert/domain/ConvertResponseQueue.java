package com.landray.kmss.sys.filestore.convert.domain;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 响应队列，用于结果处理
 */
public class ConvertResponseQueue<T> {

    private static volatile ConvertResponseQueue instance;
    public static ConvertResponseQueue getInstance(){
        if(instance == null) {
            synchronized (ConvertResponseQueue.class) {
                if(instance == null) {
                    instance = new ConvertResponseQueue();
                }
            }
        }

        return instance;
    }

    private ConcurrentLinkedDeque<T> responseQueue = new ConcurrentLinkedDeque<>();

    public void offer(T response) {
        responseQueue.offer(response);
    }

    public T poll() {
        return responseQueue.poll();
    }
    public Integer size(){
        return responseQueue.size();
    }
}
