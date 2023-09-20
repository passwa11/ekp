package com.landray.kmss.sys.filestore.scheduler.third.dianju.dto;

/**
 * 回调的信息
 */
public class ConvertObserverParameter {
    private String productName; // 厂商名称
    private String taskId; // 任务ID
    private String retCode; // 结果码
    private String message; // 信息


    public ConvertObserverParameter() {
    }

    public String getTaskId() {
        return taskId;
    }

    public ConvertObserverParameter setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getRetCode() {
        return retCode;
    }

    public ConvertObserverParameter setRetCode(String retCode) {
        this.retCode = retCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ConvertObserverParameter setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ConvertObserverParameter setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public String toString() {
        return "ConvertObserverParameter{" +
                "productName='" + productName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", retCode='" + retCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
