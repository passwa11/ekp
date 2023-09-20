package com.landray.kmss.sys.filestore.queue.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.dto.QueryParameter;

import java.util.List;

/**
 * 队列服务接口
 */
public interface ConvertQueueService {

    /**
     * 获取未转换的数量
     * @return
     * @throws Exception
     */
    Integer getCount(QueryParameter queryParameter) throws Exception;

    /**
     * 获取未分配的信息，每次查询50条
     * @return
     * @throws Exception
     */
    List<SysFileConvertQueue> getUnassignedTasks(QueryParameter queryParameter) throws Exception;
}
