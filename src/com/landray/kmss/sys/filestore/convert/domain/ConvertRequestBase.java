package com.landray.kmss.sys.filestore.convert.domain;

import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;

/**
 * 请求对象
 */
public class ConvertRequestBase {
    private ConvertRequestHandle convertRequestHandle;

    private String requestType; // 1：请求转换  2：请求获取转换信息 3：下载文件
    // 过期时间
    private Long expireTime;

    private String serverName; // 厂商名称
    public ConvertRequestBase(String serverName) {
        this.serverName = serverName;
    }

    public ConvertRequestHandle getConvertRequestHandle() {
        return convertRequestHandle;
    }


    public ConvertRequestBase setConvertRequestHandle(ConvertRequestHandle convertRequestHandle) {
        this.convertRequestHandle = convertRequestHandle;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public ConvertRequestBase setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public ConvertRequestBase setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
