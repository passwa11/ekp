package com.landray.kmss.sys.filestore.convert.service;

/**
 * 转换响应接口
 */
public interface ConvertResponseHandle<T> {

    /**
     * 处理响应
     * @param convertResponse
     * @throws Exception
     */
    void doResponse(T convertResponse) throws  Exception;
}
