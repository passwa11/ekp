package com.landray.kmss.sys.filestore.convert.service;

import java.util.Collection;

/**
 * 转换成功后，但是没有回调，需要定时清缓存
 * 集群下使用redis, 非集群下可以使用
 * @param <T>
 */
@Deprecated
public interface DriftResponseHandle<T> {
    /**
     * 清缓存
     * @param responses
     * @throws Exception
     */
    void doCleanResponse(Collection<T> responses) throws Exception;
}
