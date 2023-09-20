package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.util.ImeetingCalendarUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;
import com.oreilly.servlet.ParameterNotFoundException;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 门户数据源数据接口
 * 
 * @author ASUS
 *
 */
@Controller
@RequestMapping(value = "/data/km-imeeting/kmImeetingCalendar")
public class KmImeetingCalendarController {

	private IKmImeetingResService kmImeetingResService;

	public void setKmImeetingResService(
			IKmImeetingResService kmImeetingResService) {
		this.kmImeetingResService = kmImeetingResService;
	}

	private IKmImeetingMainService kmImeetingMainService;

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private IKmImeetingBookService kmImeetingBookService;

	public void setKmImeetingBookService(
			IKmImeetingBookService kmImeetingBookService) {
		this.kmImeetingBookService = kmImeetingBookService;
	}

	@RequestMapping(value = "/mycalendar", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> mycalendar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONArray datas = null;
		try {
			// 会议安排
			List<KmImeetingMain> mains = kmImeetingMainService
					.findKmIMeetingMain(requestCtx, true);
			// 会议预约
			List<KmImeetingBook> meetingBooks = kmImeetingBookService
					.findKmImeetingBook(requestCtx, true);
			datas = ImeetingCalendarUtil.mycalendar(mains, meetingBooks,
					request);
		} catch (Exception e) {
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(datas);
	}

	@RequestMapping(value = "/rescalendar", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> rescalendar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONObject result = null;
		try {
			// 资源（会议室）
			Page resourcePage = kmImeetingResService
					.findKmImeetingRes(requestCtx);
			// 会议安排
			List<KmImeetingMain> mains = kmImeetingMainService
					.findKmIMeetingMain(requestCtx, false);
			// 会议预约
			List<KmImeetingBook> books = kmImeetingBookService
					.findKmImeetingBook(requestCtx, false);
			result = ImeetingCalendarUtil.rescalendar(resourcePage, mains,
					books, request);
		} catch (Exception e) {
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(result);
	}

	@RequestMapping(value = "/iframe", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> iframe(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String portletId = request.getParameter("portletId");
			if (StringUtil.isNull(portletId)) {
				throw new ParameterNotFoundException("未找到部件ID");
			}
			boolean isPda = PdaFlagUtil.checkClientIsPda(request);
			String url = null;
			// 会议资源日历
			if ("km.imeeting.calendar.portlet".equals(portletId)) {
				if (isPda) {
					url = "/km/imeeting/mobile/index_place_portlet.jsp";
				} else {
					url = "/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_portlet_alone.jsp";
				}
				// 我的会议日历
			} else if ("km.imeeting.mycalendar.portlet".equals(portletId)) {
				if (isPda) {
					url = "/km/imeeting/mobile/index_calendar_portlet.jsp";
				} else {
					url = "/km/imeeting/km_imeeting_calendar/index_content_mycalendar.jsp";
				}
			}
			result.put("src", url);
		} catch (Exception e) {
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(result);
	}
}
