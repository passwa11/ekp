package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 工作时间设置弹框
 * 
 * @author
 * @version 1.0 2018-06-12
 */
public class SysAttendCategoryTimesheetForm extends ExtendForm {

	/**
	 * 星期，以;分隔
	 */
	private String fdWeek;

	/**
	 * @return 星期名列表
	 */
	public String getFdWeekNames() {
		String fdWeekNames = "";
		if (StringUtil.isNotNull(fdWeek)) {
			String[] weekArr = fdWeek.split("[,;]");
			if (weekArr != null) {
				List<String> weekList = ArrayUtil.convertArrayToList(weekArr);
				Collections.sort(weekList, new Comparator<String>() {
					@Override
					public int compare(String s1, String s2) {
						try {
							if (StringUtil.isNotNull(s1)
									&& StringUtil.isNotNull(s2)) {
								return Integer.valueOf(s1)
										.compareTo(Integer.valueOf(s2));
							} else {
								return 0;
							}
						} catch (Exception e) {
							return 0;
						}
					}
				});
				for (String week : weekList) {
					if (StringUtil.isNotNull(week)) {
						try {
							String weekName = EnumerationTypeUtil
									.getColumnEnumsLabel(
											"sysAttendCategory_fdWeek", week);
							fdWeekNames += (StringUtil.isNull(fdWeekNames) ? ""
									: "、") + weekName;
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		return fdWeekNames;
	}

	/**
	 * 一班制：1，两班制：2
	 */
	private String fdWork;

	/**
	 * 班次
	 */
	private AutoArrayList fdWorkTime = new AutoArrayList(
			SysAttendCategoryWorktimeForm.class);

	/**
	 * @return 班次时间显示
	 */
	public String getFdWorkTimeText() {
		String fdWorkTimeText = "";
		if (fdWorkTime != null && !fdWorkTime.isEmpty()) {
			for (Object obj : fdWorkTime) {
				SysAttendCategoryWorktimeForm wtForm = (SysAttendCategoryWorktimeForm) obj;
				if (wtForm != null
						&& !"false".equals(wtForm.getFdIsAvailable())) {
					String startTime = wtForm.getFdStartTime();
					String endTime = wtForm.getFdEndTime();
					if (StringUtil.isNotNull(startTime)
							&& StringUtil.isNotNull(endTime)) {
						String start = DateUtil.convertDateToString(
								DateUtil.convertStringToDate(startTime,
										DateUtil.TYPE_DATETIME, null),
								DateUtil.TYPE_TIME, null);
						String end = DateUtil.convertDateToString(
								DateUtil.convertStringToDate(endTime,
										DateUtil.TYPE_DATETIME, null),
								DateUtil.TYPE_TIME, null);
						fdWorkTimeText += (StringUtil.isNull(fdWorkTimeText)
								? ""
								: ";&nbsp;") + start + "&nbsp;-&nbsp;"
								+ end;
					}
				}
			}
		}
		return fdWorkTimeText;
	}

	/**
	 * 最早打卡1
	 */
	private String fdStartTime1;

	/**
	 * 最早打卡2
	 */
	private String fdStartTime2;

	/**
	 * 最晚打卡1
	 */
	private String fdEndTime1;

	/**
	 * 最晚打卡2
	 */
	private String fdEndTime2;

	/**
	 * 当天：1，次日：2
	 */
	private String fdEndDay;

	/**
	 * 午休开始时间
	 */
	private String fdRestStartTime;

	/**
	 * 午休结束时间
	 */
	private String fdRestEndTime;

	/**
	 * 总工时
	 */
	private String fdTotalTime;
	/**
	 * 统计时 按照多少天统计
	 */
	private Float fdTotalDay;

	private String fdCategoryId;

	public String getFdWeek() {
		return fdWeek;
	}

	public void setFdWeek(String fdWeek) {
		this.fdWeek = fdWeek;
	}

	public String getFdWork() {
		return fdWork;
	}

	public void setFdWork(String fdWork) {
		this.fdWork = fdWork;
	}

	public AutoArrayList getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(AutoArrayList fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	public String getFdStartTime1() {
		return fdStartTime1;
	}

	public void setFdStartTime1(String fdStartTime1) {
		this.fdStartTime1 = fdStartTime1;
	}

	public String getFdStartTime2() {
		return fdStartTime2;
	}

	public void setFdStartTime2(String fdStartTime2) {
		this.fdStartTime2 = fdStartTime2;
	}

	public String getFdEndTime1() {
		return fdEndTime1;
	}

	public void setFdEndTime1(String fdEndTime1) {
		this.fdEndTime1 = fdEndTime1;
	}

	public String getFdEndTime2() {
		return fdEndTime2;
	}

	public void setFdEndTime2(String fdEndTime2) {
		this.fdEndTime2 = fdEndTime2;
	}

	public String getFdEndTime() {
		return getFdEndTime2();
	}

	public void setFdEndTime(String fdEndTime) {
		setFdEndTime2(fdEndTime);
	}

	public String getFdEndDay() {
		return fdEndDay;
	}

	public void setFdEndDay(String fdEndDay) {
		this.fdEndDay = fdEndDay;
	}

	public String getFdRestStartTime() {
		return fdRestStartTime;
	}

	public void setFdRestStartTime(String fdRestStartTime) {
		this.fdRestStartTime = fdRestStartTime;
	}

	public String getFdRestEndTime() {
		return fdRestEndTime;
	}

	public void setFdRestEndTime(String fdRestEndTime) {
		this.fdRestEndTime = fdRestEndTime;
	}

	public String getFdTotalTime() {
		if (StringUtil.isNotNull(fdTotalTime)) {
			try {
				DecimalFormat df = new DecimalFormat("#.#");
				return df.format(Float.parseFloat(fdTotalTime));
			} catch (Exception e) {
				return fdTotalTime;
			}
		}
		return fdTotalTime;
	}

	public void setFdTotalTime(String fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdWeek = null;
		fdWork = null;
		fdWorkTime = new AutoArrayList(SysAttendCategoryWorktimeForm.class);
		fdStartTime1 = null;
		fdStartTime2 = null;
		fdEndTime1 = null;
		fdEndTime2 = null;
		fdEndDay = null;
		fdRestStartTime = null;
		fdRestEndTime = null;
		fdTotalTime = null;
		fdCategoryId = null;
		fdTotalDay = null;
		fdRestEndType =1;
		fdRestStartType =1;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdWorkTime",
					new FormConvertor_FormListToModelList("fdWorkTime",
							"fdTimeSheet"));
			toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel(
					"fdCategory", SysAttendCategory.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysAttendCategoryTimesheet.class;
	}

	public Float getFdTotalDay() {
		return fdTotalDay;
	}

	public void setFdTotalDay(Float fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}

	/**
	 * 午休开始时间类型
	 * 1,当日
	 * 2,次日
	 */
	private Integer fdRestStartType;

	public Integer getFdRestStartType() {
		//兼容历史没设置过的，默认是当日
		if(fdRestStartType ==null){
			fdRestStartType =1;
		}
		return fdRestStartType;
	}
	public void setFdRestStartType(Integer fdRestStartType) {
		this.fdRestStartType = fdRestStartType;
	}
	/**
	 * 午休结束时间类型
	 * 1,当日
	 * 2,次日
	 */
	private Integer fdRestEndType;

	public Integer getFdRestEndType() {
		//兼容历史没设置过的，默认是当日
		if(fdRestEndType ==null){
			fdRestEndType =1;
		}
		return fdRestEndType;
	}
	public void setFdRestEndType(Integer fdRestEndType) {
		this.fdRestEndType = fdRestEndType;
	}

}
