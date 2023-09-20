package com.landray.kmss.sys.filestore.queue.service;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.sun.istack.internal.NotNull;

public interface ConvertQueueCallbackExtension {
    void handle(@NotNull SysFileConvertQueue queue);
}
