package com.landray.kmss.sys.simplecategory.controller;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysSimpleCategoryController
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-19 10:06
 * @Version: 1.0
 */
@Controller
@RequestMapping("/data/sys-simplecategory/sysSimpleCategoryTree")
public class SysSimpleCategoryController {

    /**
     * 关联图表-图表树列表
     */
    @ResponseBody
    @RequestMapping("getDataList")
    public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request,vo);
        IXMLDataBean sysSimpleCategoryTreeService = (IXMLDataBean)SpringBeanUtil.getBean("sysSimpleCategoryTreeService");
        List<Map<String, String>> list = sysSimpleCategoryTreeService.getDataList(
                new RequestContext(reqWrapper));
        return RestResponse.ok(ControllerHelper.standardizeResult(list));
    }



}
