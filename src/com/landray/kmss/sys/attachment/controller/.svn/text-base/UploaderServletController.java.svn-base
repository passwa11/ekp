package com.landray.kmss.sys.attachment.controller;

import com.landray.kmss.sys.attachment.servlet.UploaderServlet;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 附件/图片上传后端接口
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@Controller
@RequestMapping(value = "/data/sys-attachment/uploaderServlet", method = RequestMethod.POST)
public class UploaderServletController {

	private final UploaderServletExtra servlet = new UploaderServletExtra();

	/**
	 * 附件/图片上传
	 */
	@ResponseBody
	@RequestMapping("{gettype}")
	public RestResponse<?> doPost(@PathVariable String gettype, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		//前后端分离的Controller格式固定为json
		reqWrapper.putParameter("format", "json");
		reqWrapper.putParameter("gettype", gettype);

		servlet.init();
		servlet.doPost(reqWrapper, respWrapper);

		//处理错误的返回值为标准格式
		Object data = ControllerHelper.standardizeResult(respWrapper.getWriteContent());
		if(data instanceof Map) {
			Map<String, Object> dataMap = (Map) data;
			Object status = dataMap.get("status");
			if(!"1".equals(status)) {
				Object msg = dataMap.get("msg");
				return RestResponse.error(String.valueOf(msg));
			}
		}
		return RestResponse.ok(data);
	}

	public static class UploaderServletExtra extends UploaderServlet {
		@Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			super.doPost(request, response);
		}
	}
}
