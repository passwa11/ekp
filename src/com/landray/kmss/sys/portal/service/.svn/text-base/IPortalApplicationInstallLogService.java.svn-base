package com.landray.kmss.sys.portal.service;

import com.landray.kmss.common.actions.RequestContext;

import java.util.Map;

/**
 * @description: 门户安装日志记录
 * @author: wangjf
 * @time: 2021/6/22 11:45 上午
 * @version: 1.0
 */

public interface IPortalApplicationInstallLogService {


    /**
     * 安装前置
     *
     * @param requestContext
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/22 11:48 上午
     */
    String preInstall(RequestContext requestContext) throws Exception;

    /**
     * 安装方法
     *
     * @param requestContext
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/22 11:50 上午
     */
    Map<String, String> saveInstall(RequestContext requestContext) throws Exception;

    /**
     * 安装中
     *
     * @param filePath
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/22 11:50 上午
     */
    Map<String, String> installIng(String filePath, RequestContext requestContext);

    /**
     * 安装后
     *
     * @param requestContext
     * @param result
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/22 11:51 上午
     */
    boolean postInstall(RequestContext requestContext, Map<String, String> result) throws Exception;

}
