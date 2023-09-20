package com.landray.kmss.sys.attachment.integrate.foxit;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;

/**
 * 提供给福昕的接口
 */
public interface ISysAttachmentFoxitProvider  extends IBaseService {

    /**
     * 获取预览地址:云阅读的页面访问路径后拼接docuri参数
     * @return
     */
    String previewUrl(SysAttMain sysAttMain);

    /**
     * 获取预览地址:云阅读的页面访问路径后拼接docParam参数
     * @return
     */
    String extendsPreviewUrl(SysAttMain sysAttMain);

    /**
     * 检查Token
     * @param token
     * @return
     */
    Boolean checkToken(String token);

    /**
     * 文件是否已经转换
     * @param sysAttMain
     * @return
     * @throws Exception
     */
    String isConverted(SysAttMain sysAttMain) throws Exception;
}
