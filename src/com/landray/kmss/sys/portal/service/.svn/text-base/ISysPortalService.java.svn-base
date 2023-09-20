package com.landray.kmss.sys.portal.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.portal.forms.SysPortalPackageForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 门户导入导出
 * @author: wangjf
 * @time: 2021/6/14 5:00 下午
 * @version: 1.0
 */

public interface ISysPortalService {

    /**
     * 门户导出
     *
     * @param portalMainId
     * @param request
     * @param response
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/19 2:48 下午
     */
    void getExport(String portalMainId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 门户导入
     *
     * @param filePath
     * @param requestContext
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/7/2 1:36 下午
     */
    String saveImport(String filePath, RequestContext requestContext) throws Exception;

    /**
     * 上传门户压缩包
     *
     * @param requestContext
     * @param sysPortalPackageForm
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/20 6:26 下午
     */
    String saveUpload(RequestContext requestContext, SysPortalPackageForm sysPortalPackageForm) throws Exception;
}
