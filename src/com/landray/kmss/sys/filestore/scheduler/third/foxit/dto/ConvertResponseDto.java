package com.landray.kmss.sys.filestore.scheduler.third.foxit.dto;

import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseBase;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
/*
 *请求转换对象
 */
public class ConvertResponseDto extends ConvertResponseBase {
    private SysFileConvertQueue deliveryTaskQueue;
    private String status; // 状态
    private byte[] bytes; // 字节流
    private String fdFilePath; // 文件所在系统的相对路径

    public ConvertResponseDto() {
    }

    public ConvertResponseDto(SysFileConvertQueue deliveryTaskQueue, String targetFilePath) {
        this.deliveryTaskQueue = deliveryTaskQueue;
    }

    public SysFileConvertQueue getDeliveryTaskQueue() {
        return deliveryTaskQueue;
    }

    public ConvertResponseDto setDeliveryTaskQueue(SysFileConvertQueue deliveryTaskQueue) {
        this.deliveryTaskQueue = deliveryTaskQueue;
        return this;
    }


    public String getStatus() {
        return status;
    }

    public ConvertResponseDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public ConvertResponseDto setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public String getFdFilePath() {
        return fdFilePath;
    }

    public ConvertResponseDto setFdFilePath(String fdFilePath) {
        this.fdFilePath = fdFilePath;
        return this;
    }

    @Override
    public String toString() {
        return "ConvertResponseDto{" +
                "deliveryTaskQueue=" + deliveryTaskQueue +
                ", status='" + status + '\'' +
                ", fdFilePath='" + fdFilePath + '\'' +
                '}';
    }
}
