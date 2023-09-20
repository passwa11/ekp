package com.landray.kmss.sys.filestore.scheduler.third.foxit.dto;

/**
 * 回调的信息
 */
public class FoxitObserverParameter {
    private String productName; // 厂商名称
    private String fileId; // 任务ID
    private Integer retCode; // 结果码
    private String message; // 信息
    private String downloadUrl; // 下载文件地址


    public FoxitObserverParameter() {
    }

    public String getFileId() {
        return fileId;
    }

    public FoxitObserverParameter setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public FoxitObserverParameter setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FoxitObserverParameter setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public FoxitObserverParameter setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    @Override
    public String toString() {
        return "ConvertObserverParameter{" +
                "productName='" + productName + '\'' +
                ", retCode='" + retCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
