package com.landray.kmss.sys.attachment.service;

import com.landray.kmss.sys.attachment.model.SysAttMain;

import java.util.Map;

/**
 * 本接口用于规约第三方模块系统如 wps中台，福昕，点聚等共性的附件相关操作
 */
public interface ISysAttThirdService {
    /**
     * 生成附件下载链接。<br/>
     * 链接生成规则：具体模块实现并生成链接，访问链接时，应该由生成链接的模块进行请求的接收处理。
     * <br/>
     * @param sysAttMain 附件信息
     * @param additionInfo 附加信息
     * @return 由对应模块响应的附件下载链接
     */
    String generateDownloadUrl(SysAttMain sysAttMain, Map<String, String> additionInfo);
}
