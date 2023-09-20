package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain;

/**
 * 转换Handler结果
 */
public class WPSCloudConvertHandlerResult {

    /**
     * 处理结果
     */
    private Boolean result;
    /**
     * 是否执行下一个
     */
    private Boolean doNext = true;

    /**
     * 上传文件后获取到的转换ID
     */
    private String convertId;

    /**
     * 转换之后获取到的下载ID
     */
    private String downloadId;

    public WPSCloudConvertHandlerResult(Boolean result, Boolean doNext) {
        this.result = result;
        this.doNext = doNext;
    }

    public WPSCloudConvertHandlerResult(String convertId, String downloadId) {
        this.convertId = convertId;
        this.downloadId = downloadId;
    }

    public WPSCloudConvertHandlerResult(Boolean result, Boolean doNext, String convertId, String downloadId) {
        this.result = result;
        this.doNext = doNext;
        this.convertId = convertId;
        this.downloadId = downloadId;
    }

    public WPSCloudConvertHandlerResult(Boolean result) {
        this.result = result;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getDoNext() {
        return doNext;
    }

    public void setDoNext(Boolean doNext) {
        this.doNext = doNext;
    }
}
