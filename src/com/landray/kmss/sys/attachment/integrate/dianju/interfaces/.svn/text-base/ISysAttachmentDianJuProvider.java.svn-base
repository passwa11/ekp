package com.landray.kmss.sys.attachment.integrate.dianju.interfaces;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;

/**
 * 点聚集成接口
 */
public interface ISysAttachmentDianJuProvider extends IBaseService {
    /**
     * 获取点聚轻阅读预览地址
     * @param sysAttMain
     * @param waterMarkText
     * @return
     */
    String getPreviewUrl(SysAttMain sysAttMain, String waterMarkText);

    /**
     * 校验下载token
     * @param token
     * @return
     */
    Boolean checkDownloadToken(String token);
}
