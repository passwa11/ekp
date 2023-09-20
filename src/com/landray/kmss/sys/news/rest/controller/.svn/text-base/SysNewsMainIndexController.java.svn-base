package com.landray.kmss.sys.news.rest.controller;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.common.rest.controller.ColumnDatasController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.sys.news.actions.SysNewsMainIndexAction;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.sunbor.web.tag.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新闻管理后端接口
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@Controller
@RequestMapping(value = "/data/sys-news/sysNewsMainIndex", method = RequestMethod.POST)
public class SysNewsMainIndexController extends ColumnDatasController {

	private final Log log = LogFactory.getLog(SysNewsMainIndexController.class);

	private final SysNewsMainIndexAction action = new SysNewsMainIndexAction();

	/**
	 * 列表数据
	 */
	@ResponseBody
	@RequestMapping("listChildren")
	public RestResponse<?> listChildren(@RequestBody QueryRequest query, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 统一转parameter
		HttpRequestParameterWrapper requestWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);

		//防止后面空指针异常
		if (requestWrapper.getParameter("q.j_path") == null) {
			throw new IllegalArgumentException("no parameter conditions['q.j_path']");
		}

		//调用实际业务Action
		action.listChildren(new ActionMapping(new ActionConfig()), null, requestWrapper, response);
		Page queryPage = (Page) requestWrapper.getAttribute("queryPage");
		RestResponse<Object> checkActionResult = result(requestWrapper);
		if(!checkActionResult.isSuccess()) {
			return checkActionResult;
		}
        PageVO pageVO = convert(requestWrapper, queryPage, SysNewsMain.class, "list");
        return result(requestWrapper, pageVO);
	}

}
