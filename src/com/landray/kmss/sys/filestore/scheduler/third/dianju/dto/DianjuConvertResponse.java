package com.landray.kmss.sys.filestore.scheduler.third.dianju.dto;

import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseBase;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

import java.io.InputStream;

public class DianjuConvertResponse extends ConvertResponseBase {
    // 队列
    private SysFileConvertQueue deliveryTaskQueue;
    // 转换回调函数
    private ConvertResponseHandle convertResponseHandle;
    // 转换结果
    private ConvertRequestResultDTO requestResultDTO;

    private InputStream inputStream;

    public ConvertRequestResultDTO getRequestResultDTO() {
        return requestResultDTO;
    }

    public DianjuConvertResponse setRequestResultDTO(ConvertRequestResultDTO requestResultDTO) {
        this.requestResultDTO = requestResultDTO;
        return this;
    }

    public SysFileConvertQueue getDeliveryTaskQueue() {
        return deliveryTaskQueue;
    }

    public DianjuConvertResponse setDeliveryTaskQueue(SysFileConvertQueue deliveryTaskQueue) {
        this.deliveryTaskQueue = deliveryTaskQueue;
        return this;
    }


    @Override
    public ConvertResponseHandle getConvertResponseHandle() {
        return convertResponseHandle;
    }

    @Override
    public DianjuConvertResponse setConvertResponseHandle(ConvertResponseHandle convertResponseHandle) {
        this.convertResponseHandle = convertResponseHandle;
        return this;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
