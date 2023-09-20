package com.landray.kmss.sys.attachment.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.sys.attachment.actions.SysAttMainAction;
import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 附件/图片后端接口
 *
 * @Author 严明镜
 * @create 2020/10/27 9:00
 */
@Controller
@RequestMapping(value = "/data/sys-attachment/sysAttMain")
public class SysAttMainController extends BaseController {

	private final SysAttMainAction action = new SysAttMainAction();

	/**
	 * gettype=getuserkey	握手获取密钥(附件上传第一步)
	 * gettype=checkMd5		检查附件MD5
	 * gettype=submit		上传结果处理
	 */
	@ResponseBody
	@RequestMapping(value = "handleAttUpload/{gettype}", method = RequestMethod.POST)
	public RestResponse<?> handleAttUpload(@RequestBody Map<String, Object> vo, @PathVariable String gettype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		//前后端分离的Controller格式固定为json
		reqWrapper.putParameter("format", "json");
		reqWrapper.putParameter("gettype", gettype);

		action.handleAttUpload(emptyMapping, new SysAttMainForm(), reqWrapper, respWrapper);
		if("checkMd5".equals(gettype)) {
			return RestResponse.ok(ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
		} else {
			return RestResponse.ok(request.getAttribute("resultMap"));
		}
	}

	/**
	 * 重命名
	 */
	@ResponseBody
	@RequestMapping(value = "updateFileName", method = RequestMethod.POST)
	public RestResponse<?> updateFileName(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		action.updateFileName(emptyMapping, null, reqWrapper, respWrapper);
		return RestResponse.ok(ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
	}

	/**
	 * 校验附件权限
	 */
	@ResponseBody
	@RequestMapping(value = "checkEditName", method = RequestMethod.POST)
	public RestResponse<?> checkEditName(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		action.checkEditName(emptyMapping, null, reqWrapper, respWrapper);
		return RestResponse.ok(ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
	}

	/**
	 * 下载
	 */
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		action.download(emptyMapping, null, request, response);
	}

	/**
	 * 阅读
	 */
	@RequestMapping(value = "readDownload")
	public void readDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		action.readDownload(emptyMapping, null, request, response);
	}

}
