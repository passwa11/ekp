package com.landray.kmss.common.rest.controller;

import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: RTF上传图片、video
 * @Author: admin
 * @Date: 2020-12-23 15:52
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-common/RTFUpload")
public class RTFUploaderController extends BaseController {

    /**
     * RTF上传图片、video
     */
    @ResponseBody
    @RequestMapping("simpleUploader")
    public RestResponse<?> simpleUploader(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
        request.getRequestDispatcher("/resource/fckeditor/editor/filemanager/upload/simpleuploader").forward(request, respWrapper);
        return result(request,ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
    }
    
}
