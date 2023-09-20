package com.landray.kmss.sys.praise.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.sys.praise.actions.SysPraiseMainAction;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: SysPraiseMainController
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-16 15:57
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-praise/sysPraiseMain", method = RequestMethod.POST)
public class SysPraiseMainController extends BaseController {

    private final Log log = LogFactory
            .getLog(SysPraiseMainController.class);

    private final SysPraiseMainAction action = new SysPraiseMainAction();

    /**
     * 点赞/取消点赞
     */
    @ResponseBody
    @RequestMapping("executePraise")
    public RestResponse<String> executePraise(@RequestBody Map<String, Object> vo,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper
                .buildRequestParameterWrapper(request, vo);
        action.executePraise(new ActionMapping(new ActionConfig()), null, wrapper,
                response);
        return result(wrapper);
    }

    /**
     * 获取点赞人列表
     */
    @ResponseBody
    @RequestMapping("getPraisedPersons")
    public RestResponse<?> getPraisedPersons(@RequestBody QueryRequest query,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 统一转parameter
        HttpRequestParameterWrapper requestWrapper = ControllerHelper
                .buildRequestParameterWrapper(request, query);
        HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
        // 调用实际业务Action
        action.getPraisedPersons(new ActionMapping(new ActionConfig()), null,
                requestWrapper, response);
        return RestResponse.ok(ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
    }
}
