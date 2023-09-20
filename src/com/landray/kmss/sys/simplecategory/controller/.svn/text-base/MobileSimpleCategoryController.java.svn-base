package com.landray.kmss.sys.simplecategory.controller;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.sys.simplecategory.mobile.MobileSimpleCategoryAction;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: admin
 * @Date: 2021-1-25 17:26
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-category/mobileSimpleCategory")
public class MobileSimpleCategoryController extends BaseController {

    private MobileSimpleCategoryAction action = new MobileSimpleCategoryAction();

    /**
     * 获取有权限的分类ID
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("authData")
    public RestResponse<?> authData(@RequestBody Map query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
        action.authData(emptyMapping, null, requestWrapper, response);
        return result(request, ControllerHelper.standardizeResult(requestWrapper.getAttribute("lui-source")));
    }

    /**
     * 获取有权限的分类ID
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("pathList")
    public RestResponse<?> pathList(@RequestBody Map query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
        action.pathList(emptyMapping, null, requestWrapper, response);
        return result(request, ControllerHelper.standardizeResult(requestWrapper.getAttribute("lui-source")));
    }

    /**
     * 移动端分类选择框
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("cateList")
    public RestResponse<?> cateList(@RequestBody Map query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
        action.cateList(emptyMapping, null, requestWrapper, response);
        return result(request, ControllerHelper.standardizeResult(requestWrapper.getAttribute("lui-source")));
    }

    /**
     * 搜索
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("searchList")
    public RestResponse<?> searchList(@RequestBody Map query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
        action.searchList(emptyMapping, null, requestWrapper, response);
        return result(request, ControllerHelper.standardizeResult(requestWrapper.getAttribute("lui-source")));
    }
}
