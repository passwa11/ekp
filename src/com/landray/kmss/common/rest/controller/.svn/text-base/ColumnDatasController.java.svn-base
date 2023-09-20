package com.landray.kmss.common.rest.controller;

import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.rest.convertor.PageVOConvertor;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ColumnDatasController
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-15 10:53
 * @Version: 1.0
 */
public class ColumnDatasController extends BaseController {
    private final Log logger = LogFactory.getLog(ColumnDatasController.class);

    /**
     * @Description
     * @param request
     * @param queryPage
     * @param clazz
     * @param method
     * @Return java.lang.Object
     * @Date 2020-12-15 10:59
     */
    public PageVO convert(HttpServletRequest request, Page queryPage, Class clazz, String method) {
        PageVOConvertor pageVOConvertor = new PageVOConvertor(request, queryPage, ModelUtil.getModelClassName(clazz.getName()), method);
        return pageVOConvertor.convert();
    }

    public RestResponse<?> convertResult(HttpServletRequest request, Page queryPage, Class clazz, String method) {
        return result(request, convert(request, queryPage, clazz, method));
    }

}

