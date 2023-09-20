package com.landray.kmss.sys.filestore.scheduler.third.foxit.dto;

import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestBase;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 请求转换对象
 */
public class ConvertRequestDto extends ConvertRequestBase {
    public ConvertRequestDto(String serverName) {
        super(serverName);
    }
    // 福昕下载EKP系统文件地址
    private String url;

    // 任务ID， 对于福昕的接口来说是fileId
    private String fileId;

   // 源文件后缀名
    private String srcFormat;

    // 要转换的文件格式
    private String newFormat;

    // 回调的地址
    private String callbackUrl;

    // 是否同步：默认false, true 同步(实时返回信息)， false 异步(通过回调地址返回信息)
    private Boolean isSync = false;

    // 下载已经转换文件地址
    private String downloadConvertUrl;

    private SysFileConvertQueue deliveryTaskQueue;
    private String targetFilePath;

    public String getSrcFormat() {
        return srcFormat;
    }

    public String getUrl() {
        return url;
    }

    public ConvertRequestDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public ConvertRequestDto setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public ConvertRequestDto setSrcFormat(String srcFormat) {
        this.srcFormat = srcFormat;
        return this;
    }

    public String getNewFormat() {
        return newFormat;
    }

    public ConvertRequestDto setNewFormat(String newFormat) {
        this.newFormat = newFormat;
        return this;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public ConvertRequestDto setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public Boolean getSync() {
        return isSync;
    }

    public ConvertRequestDto setSync(Boolean sync) {
        isSync = sync;
        return this;
    }

    public SysFileConvertQueue getDeliveryTaskQueue() {
        return deliveryTaskQueue;
    }

    public ConvertRequestDto setDeliveryTaskQueue(SysFileConvertQueue deliveryTaskQueue) {
        this.deliveryTaskQueue = deliveryTaskQueue;
        return this;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public ConvertRequestDto setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
        return this;
    }

    public String getDownloadConvertUrl() {
        return downloadConvertUrl;
    }

    public ConvertRequestDto setDownloadConvertUrl(String downloadConvertUrl) {
        this.downloadConvertUrl = downloadConvertUrl;
        return this;
    }

    @Override
    public String toString() {
        return "ConvertRequestDto{" +
                "url='" + url + '\'' +
                ", fileId='" + fileId + '\'' +
                ", srcFormat='" + srcFormat + '\'' +
                ", newFormat='" + newFormat + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", isSync=" + isSync +
                ", targetFilePath='" + targetFilePath + '\'' +
                '}';
    }
}

