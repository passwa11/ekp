package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain;

/**
 * 转换结果
 */
public class WPSCloudConvertResult {

    /**
     * 结果
     */
    private Boolean result;

    /**
     * 下载ID
     */
    private String downloadId;

    /**
     * WPS返回结果信息
     */
    private String wpsResult;

    public WPSCloudConvertResult() {
    }

    public WPSCloudConvertResult(Boolean result, String downloadId, String wpsResult) {
        this.result = result;
        this.downloadId = downloadId;
        this.wpsResult = wpsResult;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getWpsResult() {
        return wpsResult;
    }

    public void setWpsResult(String wpsResult) {
        this.wpsResult = wpsResult;
    }
}
