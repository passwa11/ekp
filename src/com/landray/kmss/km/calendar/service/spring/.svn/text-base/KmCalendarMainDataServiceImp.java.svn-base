package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/km-calendar/main", method = RequestMethod.POST)
@RestApi(docUrl = "/km/calendar/km_calendar_webservice/kmCalendarRestHelp.jsp", name = "kmCalendarMainDataService", resourceKey = "km-calendar:kmCalendarMain.job.sync")
public class KmCalendarMainDataServiceImp {
	
	
	private IKmCalendarMainService kmCalendarMainService;
	
	public void setKmCalendarMainService(IKmCalendarMainService kmCalendarMainService) {
		this.kmCalendarMainService = kmCalendarMainService;
	}

	@ResponseBody
	@RequestMapping("/get")
	public JSONObject getCalendar(@RequestBody JSONObject paramData) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			Date beginTime = null;
			if (paramData != null && paramData.containsKey("beginTime")) {
				beginTime = DateUtil.convertStringToDate(paramData.getString("beginTime"));
			}
			// 只获取当天的有效会议
			HQLInfo info = new HQLInfo();
			StringBuffer sb = new StringBuffer();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (beginTime == null || cal.getTime().after(beginTime)) {
				// 如果是当天第一次同步，则同步所有当天的日程
				sb.append(
						"((kmCalendarMain.docStartTime<:beginTime and kmCalendarMain.docFinishTime>:beginTime) or (kmCalendarMain.docStartTime>:beginTime and kmCalendarMain.docStartTime<:endTime))");
			} else {
				info.setParameter("publishTime", beginTime);
				sb.append(
						"kmCalendarMain.docAlterTime>:publishTime and ((kmCalendarMain.docStartTime<:beginTime and kmCalendarMain.docFinishTime>:beginTime) or (kmCalendarMain.docStartTime>:beginTime and kmCalendarMain.docStartTime<:endTime))");
			}
			info.setWhereBlock(sb.toString());
			info.setParameter("beginTime", cal.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 5);
			info.setParameter("endTime", cal.getTime());
			List<?> retVal = kmCalendarMainService.findList(info);
			JSONArray array = new JSONArray();
			for (int i = 0; i < retVal.size(); i++) {
				KmCalendarMain kmCalendarMain = (KmCalendarMain) retVal.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("workItemId", kmCalendarMain.getFdId());
				jsonObject.put("workTitle", kmCalendarMain.getDocSubject());
				jsonObject.put("bgTime",
						kmCalendarMain.getDocStartTime() != null ? kmCalendarMain.getDocStartTime().getTime() : null);
				jsonObject.put("endTime",
						kmCalendarMain.getDocFinishTime() != null ? kmCalendarMain.getDocFinishTime().getTime() : null);
				jsonObject.put("jobStatus", "UPDATE");
				jsonObject.put("detailUrl", StringUtil.formatUrl(ModelUtil.getModelUrl(kmCalendarMain),true));
				List<String> fdLoginName = new ArrayList<String>();
				fdLoginName.add(kmCalendarMain.getDocOwner().getFdLoginName());
				jsonObject.put("loginNames", fdLoginName);
				array.add(jsonObject);
			}
			result.put("datas", array);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

}
