package com.landray.kmss.sys.news.rest.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.sys.news.actions.SysNewsMainAction;
import com.landray.kmss.sys.news.forms.SysNewsMainForm;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 新闻管理后端接口
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@Controller
@RequestMapping(value = "/data/sys-news/sysNewsMain", method = RequestMethod.POST)
public class SysNewsMainController extends BaseController {

	private final SysNewsMainAction action = getBeansForType(SysNewsMainAction.class);

	/**
	 * 进入新建页面
	 */
	@ResponseBody
	@RequestMapping("add")
	public RestResponse<SysNewsMainForm> add(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);

		SysNewsMainForm form = new SysNewsMainForm();
		form.setFdTemplateId((String) vo.get("fdTemplateId"));
		action.add(getDefMapping(), form, wrapper, response);
		SysNewsMainForm newForm = (SysNewsMainForm) wrapper.getAttribute(getFormName(form));
		if (newForm != null) {
			form = newForm;
		}
		return result(wrapper, form);
	}

	/**
	 * 进入编辑页面
	 */
	@ResponseBody
	@RequestMapping("edit")
	public RestResponse<SysNewsMainForm> edit(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);

		SysNewsMainForm form = new SysNewsMainForm();
		action.edit(getDefMapping(), form, wrapper, response);
		form = (SysNewsMainForm) wrapper.getAttribute(getFormName(form));
		return result(wrapper, form);
	}

	/**
	 * 新建暂存/提交
	 */
	@ResponseBody
	@RequestMapping("save")
	public RestResponse<String> save(@RequestBody SysNewsMainForm vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControllerHelper.VOConvertPostHandler(vo);
		action.save(getDefMapping(), vo, request, response);
		return result(request);
	}

	/**
	 * 编辑暂存/提交
	 */
	@ResponseBody
	@RequestMapping("update")
	public RestResponse<String> update(@RequestBody SysNewsMainForm vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ControllerHelper.VOConvertPostHandler(vo);
		action.update(getDefMapping(), vo, request, response);
		return result(request);
	}

	/**
	 * 查看
	 */
	@ResponseBody
	@RequestMapping("view")
	public RestResponse<SysNewsMainForm> view(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		wrapper.putParameter("method", "view");	//没有method不触发阅读机制的记录

		SysNewsMainForm form = new SysNewsMainForm();
		action.view(getDefMapping(), form, wrapper, response);
		form = (SysNewsMainForm) wrapper.getAttribute(getFormName(form));
		return result(wrapper, form);
	}

	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequestMapping("deleteall")
	public RestResponse<String> deleteall(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		action.deleteall(getDefMapping(), null, wrapper, response);
		return result(wrapper);
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("delete")
	public RestResponse<String> delete(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		wrapper.setMethod("GET");
		action.delete(getDefMapping(), null, wrapper, response);
		return result(wrapper);
	}

	/**
	 * 置顶/取消置顶
	 */
	@ResponseBody
	@RequestMapping("setTop")
	public RestResponse<String> setTop(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		action.setTop(getDefMapping(), null, wrapper, response);
		return result(wrapper);
	}

	/**
	 * 发布/取消发布
	 */
	@ResponseBody
	@RequestMapping("setPublish")
	public RestResponse<String> setPublish(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		action.setPublish(getDefMapping(), null, wrapper, response);
		return result(wrapper);
	}
}
