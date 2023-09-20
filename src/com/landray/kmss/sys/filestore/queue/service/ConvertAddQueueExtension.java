package com.landray.kmss.sys.filestore.queue.service;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.dto.ConvertParameter;
import com.sun.istack.internal.NotNull;

import java.util.Map;

public interface ConvertAddQueueExtension {
    SysFileConvertQueue addQueue(@NotNull SysAttMain sysAttMain, @NotNull ConvertParameter convertParams, Map<String, Object> extParams) throws Exception;
}
