package com.landray.kmss.sys.filestore.scheduler.third.dianju.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;

/**
 * 请求响应结果处理器接口
 */
public interface ConvertRequestResultExecutor {

    Boolean doResult(SysFileConvertQueue convertQueue, ConvertRequestResultDTO requestResultDTO) throws Exception;
}
