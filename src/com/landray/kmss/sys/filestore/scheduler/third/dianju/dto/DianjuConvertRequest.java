package com.landray.kmss.sys.filestore.scheduler.third.dianju.dto;

import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestBase;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

import java.util.Map;

public class DianjuConvertRequest extends ConvertRequestBase {
    // 厂商名称
    private String productName;
    // 任务ID
    private String taskId;
    // 队列
    private SysFileConvertQueue deliveryTaskQueue;

    // 下载请求ID
    private String serialNumber;

    // 请求参数
    Map<String, Object> convertRequestParameter;
    // 请求头
    Map<String, String> convertRequestHeader;

    private String downloadUrl; // 下载转换后的文件

    public DianjuConvertRequest(String serverName) {
        super(serverName);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DianjuConvertRequest setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Map<String, Object> getConvertRequestParameter() {
        return convertRequestParameter;
    }

    public DianjuConvertRequest setConvertRequestParameter(Map<String, Object> convertRequestParameter) {
        this.convertRequestParameter = convertRequestParameter;
        return this;
    }

    public Map<String, String> getConvertRequestHeader() {
        return convertRequestHeader;
    }

    public DianjuConvertRequest setConvertRequestHeader(Map<String, String> convertRequestHeader) {
        this.convertRequestHeader = convertRequestHeader;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public DianjuConvertRequest setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public DianjuConvertRequest setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public SysFileConvertQueue getDeliveryTaskQueue() {
        return deliveryTaskQueue;
    }

    public DianjuConvertRequest setDeliveryTaskQueue(SysFileConvertQueue deliveryTaskQueue) {
        this.deliveryTaskQueue = deliveryTaskQueue;
        return this;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public DianjuConvertRequest setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }
}
