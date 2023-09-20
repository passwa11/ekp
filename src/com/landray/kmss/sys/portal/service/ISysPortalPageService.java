package com.landray.kmss.sys.portal.service;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;

/**
 * 主文档业务对象接口
 *
 * @author
 * @version 1.0 2013-07-18
 */
public interface ISysPortalPageService extends IBaseService {
    public String createFile(SysPortalPageDetail page) throws Exception;

    public boolean existPageFile(String pageId, String md5) throws Exception;

    public String pageJspPath(String pageId, String md5) throws Exception;

    /*************匿名门户Start @author 吴进 by 20191202 ***************************/
    public String createFileAnonym(SysPortalPageDetail page) throws Exception;

    public boolean existPageFileAnonym(String pageId, String md5) throws Exception;

    public String pageJspPathAnonym(String pageId, String md5) throws Exception;
    /*************匿名门户End @author 吴进 by 20191202 ***************************/

    /**
     * 保存 只调用super.add方法
     *
     * @param modelObj
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/8/11 3:09 下午
     */
    String saveAdd(IBaseModel modelObj) throws Exception;
}
