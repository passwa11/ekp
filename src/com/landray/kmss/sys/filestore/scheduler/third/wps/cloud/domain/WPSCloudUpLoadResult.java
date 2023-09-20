package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain;

/**
 * 上传文件到WPS服务结果
 */
public class WPSCloudUpLoadResult {
    /**
     * 结果情况
     */
    private Boolean result;
    /**
     * 转换的ID
     */
    private String convertId;

    /**
     * WPS返回的结果
     */
    private String wpsResult;

    public WPSCloudUpLoadResult() {
    }

    public WPSCloudUpLoadResult(Boolean result, String convertId, String wpsResult) {
        this.result = result;
        this.convertId = convertId;
        this.wpsResult = wpsResult;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getWpsResult() {
        return wpsResult;
    }

    public void setWpsResult(String wpsResult) {
        this.wpsResult = wpsResult;
    }
}
