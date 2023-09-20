package com.landray.kmss.sys.filestore.convert.domain;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 请求转换队列
 */
public class ConvertRequestQueue<T> {


    private static volatile ConvertRequestQueue instance = null;

    public static ConvertRequestQueue getInstance() {
        if(instance == null) {
            synchronized (ConvertRequestQueue.class) {
                if(instance == null) {
                    instance = new ConvertRequestQueue();
                }
            }
        }

        return instance;
    }

    // 请求队列
    private ConcurrentLinkedDeque<T> requestQueue = new ConcurrentLinkedDeque<>();

    public void offer(T request) {
        requestQueue.offer(request);
    }

    public <T extends ConvertRequestBase> T poll(){
        return (T) requestQueue.poll();
    }

    public Integer size(){
        return requestQueue.size();
    }
}
