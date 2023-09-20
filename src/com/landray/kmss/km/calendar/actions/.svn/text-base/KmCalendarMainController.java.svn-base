package com.landray.kmss.km.calendar.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;

import net.sf.json.JSONArray;

/**
 * 门户数据源数据接口
 * 
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value = "/data/km-calendar/kmCalendarMain")
public class KmCalendarMainController {

	private IKmCalendarMainService kmCalendarMainService;

	public void setKmCalendarMainService(
			IKmCalendarMainService kmCalendarMainService) {
		this.kmCalendarMainService = kmCalendarMainService;
	}

	@RequestMapping(value = "/getEventsByRange", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> getEventsByRange(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONArray datas = null;
		try {
			datas = kmCalendarMainService.getEventsByRange(requestCtx);
		} catch (Exception e) {
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(datas);
	}

	@RequestMapping(value = "/data", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> data(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONArray datas = null;
		try {
			datas = kmCalendarMainService.data(requestCtx);
		} catch (Exception e) {
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(datas);
	}

}
