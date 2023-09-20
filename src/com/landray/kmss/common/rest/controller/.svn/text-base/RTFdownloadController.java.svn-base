package com.landray.kmss.common.rest.controller;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: RTF下载图片
 * @Author: admin
 * @Date: 2021-1-21 11:06
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-common/RTFdownload")
public class RTFdownloadController extends BaseController{

    /**
     * RTF下载图片
     */
    @RequestMapping("download")
    public void download(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
        request.getRequestDispatcher("/resource/fckeditor/editor/filemanager/download").forward(reqWrapper, response);
    }

}
