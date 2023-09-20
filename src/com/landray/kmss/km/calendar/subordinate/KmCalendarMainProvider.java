package com.landray.kmss.km.calendar.subordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.util.CalendarQueryContext;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * 下属日程提供者
 * 
 * @author 潘永辉 2019年3月30日
 *
 */
public class KmCalendarMainProvider extends AbstractSubordinateProvider {
	protected IKmCalendarMainService kmCalendarMainService;

	protected IKmCalendarMainService getKmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 创建者
		items.add(new PropertyItem("docCreator", ""));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo)
			throws Exception {
		String orgId = request.getParameter("orgId");
		String whereblock = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmCalendarMain.docOwner.fdId =:owner");
		hqlInfo.setParameter("owner", orgId);
		String subject = request.getParameter("subject");
		if (StringUtil.isNotNull(subject)) {
			whereblock = StringUtil.linkString(whereblock, " and ", "kmCalendarMain.docSubject like :subject");
			hqlInfo.setParameter("subject", "%" + subject + "%");
		}
		hqlInfo.setWhereBlock(whereblock);
	}

	@Override
	public Object findData(RequestContext request) throws Exception {
		JSONArray datas = new JSONArray();
		String orgId = request.getParameter("orgId");
		String startTime = request.getParameter("fdStart"); // 开始时间
		String endTime = request.getParameter("fdEnd"); // 结束时间
		String labelIds = request.getParameter("labelIds");
		String subject = request.getParameter("subject");
		String calType = null;
		Date docStartTime = new Date();
		Date docFinishTime = new Date();
		if (StringUtil.isNotNull(startTime)) {
			docStartTime = DateUtil.convertStringToDate(startTime, DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
		}
		if (StringUtil.isNotNull(endTime)) {
			docFinishTime = DateUtil.convertStringToDate(endTime, DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
		}

		CalendarQueryContext context = new CalendarQueryContext();
		context.setRangeStart(docStartTime);
		context.setRangeEnd(docFinishTime);
		context.setCalType(calType);
		context.setIncludeRecurrence(false);
		context.setPersonsIds(orgId);
		context.setLabelIds(labelIds);
		// 忽略权限
		context.setIfAuth(false);
		// 非重复日程
		List<KmCalendarMain> kmCalendars = getKmCalendarMainService().getRangeCalendars(context);

		context.setCalType(null);
		// 重复日程
		List<KmCalendarMain> recurrenceCalendars = getKmCalendarMainService().getRecurrenceCalendars(context);
		kmCalendars.addAll(recurrenceCalendars);
		// 记录日志
		UserOperHelper.logFindAll(kmCalendars, getKmCalendarMainService().getModelName());
		UserOperHelper.setOperSuccess(true);
		// 过滤数据AI
		if (StringUtil.isNotNull(subject)) {
			List<KmCalendarMain> allCalendar = new ArrayList<KmCalendarMain>();
			for (KmCalendarMain main : kmCalendars) {
				if (StringUtil.isNotNull(main.getDocSubject()) && main.getDocSubject().indexOf(subject) > -1) {
					allCalendar.add(main);
				}
			}
			kmCalendars = allCalendar;
		}

		Collections.sort(kmCalendars, new CalendarComparator());
		int index = kmCalendars.size();
		for (KmCalendarMain kmCalendarMain : kmCalendars) {
			JSONObject data = genCalendarData(kmCalendarMain, request);
			if (StringUtil.isNull(kmCalendarMain.getFdRecurrenceStr()) || "NO".equals(kmCalendarMain.getFdRecurrenceStr())) {
				data.put("isRecurrence", false);
			} else {
				data.put("isRecurrence", true);
			}
			data.put("priority", index--);
			datas.add(data);
		}
		return datas;
	}

	/**
	 * 日程对象转为JSON
	 */
	private JSONObject genCalendarData(KmCalendarMain kmCalendarMain,
			RequestContext request) throws Exception {
		JSONObject data = new JSONObject();
		data.put("id", kmCalendarMain.getFdId());
		data.put("title", kmCalendarMain.getDocSubject());
		String type = DateUtil.TYPE_DATETIME;
		Boolean isAlldayevent = kmCalendarMain.getFdIsAlldayevent();
		if (isAlldayevent == null || isAlldayevent) {
			type = DateUtil.TYPE_DATE;
		}
		String statDate = DateUtil.convertDateToString(
				kmCalendarMain.getDocStartTime(), type, null);
		data.put("start", statDate);
		if (kmCalendarMain.getDocFinishTime() != null) {
			String endDate = DateUtil.convertDateToString(
					kmCalendarMain.getDocFinishTime(), type, null);
			data.put("end", endDate);
		}
		data.put("allDay", kmCalendarMain.getFdIsAlldayevent());
		KmCalendarLabel kmCalendarLabel = kmCalendarMain.getDocLabel();
		if (kmCalendarLabel != null) {
			data.put("labelId", kmCalendarMain.getDocLabel().getFdId());
			data.put("labelName", kmCalendarMain.getDocLabel().getFdName());
			data.put("color", kmCalendarMain.getDocLabel().getFdColor());
		}
		if (kmCalendarMain.getDocContent() != null) {
			data.put("content", kmCalendarMain.getDocContent());
		}
		data.put("type", kmCalendarMain.getFdType());
		// 头像
		if (kmCalendarMain.getDocOwner() != null) {
			String img = PersonInfoServiceGetter
					.getPersonHeadimageUrl(kmCalendarMain.getDocOwner().getFdId());
			if (!PersonInfoServiceGetter.isFullPath(img)) {
				img = request.getContextPath() + img;
			}
			data.put("img", img);
			data.put("owner", kmCalendarMain.getDocOwner().getFdName());
			if (kmCalendarMain.getDocCreator() != kmCalendarMain.getDocOwner()) {
				data.put("ownerId", kmCalendarMain.getDocOwner().getFdId());
			} else {
				data.put("ownerId", "");
			}
		}
		return data;
	}
	
	private class CalendarComparator implements Comparator<KmCalendarMain> {
		@Override
		public int compare(KmCalendarMain o1, KmCalendarMain o2) {
			int result = 0;
			if (o1.getDocStartTime().getTime() > o2.getDocStartTime().getTime()) {
				result = 1;
			}else if(o1.getDocStartTime().getTime() < o2.getDocStartTime().getTime()){
				result = -1;
			}else{
				if(o1.getDocCreateTime().getTime() > o2.getDocCreateTime().getTime()){
					result  = 1;
				} else if (o1.getDocCreateTime().getTime() < o2
						.getDocCreateTime().getTime()) {
					result = -1;
				}
			}
			return result;
		}
	}

}
