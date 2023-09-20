package com.landray.kmss.sys.filestore.convert.service;

/**
 * 转换请求的接口
 */
public interface ConvertRequestHandle<T> {

    /**
     * 转换
     * @param convertRequest
     */
    void doConvert(T convertRequest) throws Exception;

    /**
     * 查询转换后的信息
     * @param convertRequest
     * @throws Exception
     */
    void getConvertInfo(T convertRequest) throws Exception;
    /**
     * 下载
     * @param convertRequest
     */
    void doConvertDownload(T convertRequest)  throws Exception;

    /**
     * 使用熔断时，在熔断的状态下，需要把队列的状态从已经分配改为失败
     * @param convertRequest
     * @throws Exception
     */
    void convertFailure(T convertRequest) throws Exception;
}
