package com.landray.kmss.sys.attachment.controller;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.common.rest.controller.ColumnDatasController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.sys.attachment.actions.SysAttDownloadLogAction;
import com.landray.kmss.sys.attachment.model.SysAttDownloadLog;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.sunbor.web.tag.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载日志后端接口
 *
 * @Author 严明镜
 * @create 2020/10/23 17:18
 */
@Controller
@RequestMapping(value = "/data/sys-attachment/sysAttDownloadLog", method = RequestMethod.POST)
public class SysAttDownloadLogController extends ColumnDatasController {

	private final SysAttDownloadLogAction action = new SysAttDownloadLogAction();

	/**
	 * 访问统计-打印记录
	 */
	@ResponseBody
	@RequestMapping("list")
	public RestResponse<?> list(@RequestBody QueryRequest query, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
		action.list(new ActionMapping(new ActionConfig()), null, requestWrapper, response);
		Page queryPage = (Page) request.getAttribute("queryPage");
		if(queryPage == null) {
			return RestResponse.error("请求失败");
		}
        PageVO pageVO = convert(requestWrapper, queryPage, SysAttDownloadLog.class, "list");
        return result(requestWrapper, pageVO);
	}

}
