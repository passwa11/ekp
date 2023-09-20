package com.landray.kmss.sys.filestore.queue.dto;

import java.util.Arrays;

/**
 * 请求参数
 */
public class RequestParameter {
    /**
     * 目标转换类型
     */
    private String[] convertKeys;

    /**
     * 调用第三方类型
     */
    private String convertType;

    public RequestParameter(String[] convertKeys, String convertType) {
        this.convertKeys = convertKeys;
        this.convertType = convertType;
    }

    public String[] getConvertKeys() {
        return convertKeys;
    }

    public void setConvertKeys(String[] convertKeys) {
        this.convertKeys = convertKeys;
    }

    public String getConvertType() {
        return convertType;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }

    @Override
    public String toString() {
        return "RequestParameter{" +
                "convertKeys=" + Arrays.toString(convertKeys) +
                ", convertType='" + convertType + '\'' +
                '}';
    }
}
