package com.landray.kmss.sys.portal.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;

import java.util.List;

/**
 * 自定义页面业务对象接口
 *
 * @author
 * @version 1.0 2013-09-23
 */
public interface ISysPortalMainService extends IBaseService {
    public SysPortalMainPage getPortalInfoById(String portalId) throws Exception;

    public SysPortalMainPage getPortalPageById(String pageId) throws Exception;

    /**
     * 获取默认的门户页面
     *
     * @param lang       语言
     * @param authAreaId 场所ID
     * @return
     * @throws Exception
     */
    public SysPortalMainPage getDefaultPortalPage(String lang, String authAreaId) throws Exception;

    /**
     * 获取默认的匿名门户页面
     *
     * @param lang       语言
     * @param authAreaId 场所ID
     * @return
     * @throws Exception
     */
    public SysPortalMainPage getDefaultAnonymousPortalPage(String lang, String authAreaId) throws Exception;

    /**
     * 有过滤权限
     *
     * @param fdId
     * @return
     * @throws Exception
     */
    public List getPortalPageList(String fdId) throws Exception;


    /************ 匿名门户 Start ****************************************************************/
    /**
     * 获取默认的匿名门户页面
     *
     * @param lang 语言
     * @return
     * @throws Exception
     * @author 吴进 by 20191120
     */
    public SysPortalMainPage getAnonymousPortalPage(String lang) throws Exception;

    /**
     * 获取指定的匿名门户页面
     *
     * @param lang 语言
     * @return
     * @throws Exception
     * @author 吴进 by 20191120
     */
    public SysPortalMainPage getAnonymousPortalPageById(String portalId) throws Exception;

    /**
     * 根据匿名门户中某个页面获取，portalPageId是门户与页面中间表的fdId
     *
     * @param pageId
     * @return
     * @throws Exception
     * @author 吴进 by 20191120
     */
    public SysPortalMainPage getAnonymousPortalPageByPageId(String pageId) throws Exception;

    /**
     * 获取页面，无过滤权限
     *
     * @param fdId
     * @return
     * @throws Exception
     * @author 吴进 by 20191120
     */
    public List getAnonymousPortalPageList(String fdId) throws Exception;

    /************ 匿名门户 End ****************************************************************/
}
