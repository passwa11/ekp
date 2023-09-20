package com.landray.kmss.sys.attachment.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 附件清除定时任务
 */
public interface ISysAttClearService {
    /**
     * 移走附件历史版本
     */
    void moveHisAttachments(SysQuartzJobContext context);

    /**
     * 删除超过保留期限的历史版本
     */
    void delHisAttachments();
}
