package com.landray.kmss.common.rest.controller;

import com.landray.kmss.common.actions.ImportAction;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 富文本导入word
 * @Author: admin
 * @Date: 2020-12-23 14:41
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-common/RTFImport")
public class ImportController extends BaseController {

    private ImportAction action = new ImportAction();
    
    /**
     * @Description RTF导入word文件
     * @param: form
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?> 
     * @throws 
     */
    @RequestMapping(value = "importWord")
    public RestResponse<?> importWord(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpResponseWriterWrapper responseWrapper = ControllerHelper.buildResponseWriterWrapper(response);
        action.importWord(emptyMapping, form, request, responseWrapper);
        return result(request,ControllerHelper.standardizeResult(responseWrapper.getWriteContent()));
    }

    /**
     * @Description 导入成功之后删除生成的HTML文件
     * @param: form
     * @param: request
     * @param: response
     * @throws
     * @return com.landray.kmss.web.RestResponse<?>
     */
    @RequestMapping(value = "deleteFile")
    public RestResponse<?> deleteFile(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpResponseWriterWrapper responseWrapper = ControllerHelper.buildResponseWriterWrapper(response);
        action.deleteFile(emptyMapping, form, request, responseWrapper);
        return result(request);
    }

}
