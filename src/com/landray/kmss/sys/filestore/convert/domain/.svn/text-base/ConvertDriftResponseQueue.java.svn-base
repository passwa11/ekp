package com.landray.kmss.sys.filestore.convert.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换成功先放入此队列，等待回调之后处理
 * 非集群下可以使用
 */
@Deprecated
public class ConvertDriftResponseQueue<T> {
    private static volatile ConvertDriftResponseQueue instance= null;

    public static ConvertDriftResponseQueue getInstance() {
        if(instance == null) {
            synchronized(ConvertDriftResponseQueue.class) {
                if(instance == null) {
                    instance = new ConvertDriftResponseQueue();
                }
            }
        }

        return instance;
    }

    private Map<String, T> successResponse = new ConcurrentHashMap<>();

    public void put(String taskId, T response) {
        successResponse.put(taskId, response);
    }

    public T get(String taskId) {
        return successResponse.remove(taskId);
    }

    public Map<String, T> getSuccessResponse(){
        return successResponse;
    }
}
