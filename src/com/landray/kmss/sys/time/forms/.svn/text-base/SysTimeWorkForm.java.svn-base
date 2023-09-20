package com.landray.kmss.sys.time.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeWorkForm extends ExtendForm {
	/*
	 * 所属区域组
	 */
	private String sysTimeAreaId = null;
	private String sysTimeCommonId = null;
	private String fdName = null;
	/*
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String timeType;

	/*
	 * 从星期几开始
	 */
	private String fdWeekStartTime = null;

	/*
	 * 到星期几结束
	 */
	private String fdWeekEndTime = null;

	/*
	 * 工作的有效开始时间
	 */
	private String fdStartTime = null;

	/*
	 * 工作的有效结束时间
	 */
	private String fdEndTime = null;

	/*
	 * 创建ID
	 */
	private String docCreatorId = null;

	/*
	 * 创建人姓名
	 */
	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/**
	 * 总工时 半天还是一天。用于统计
	 */
	protected Float fdTotalDay;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 班次列表
	 */
	private SysTimeCommonTimeForm sysTimeCommonTimeForm = new SysTimeCommonTimeForm();
	/*
	 * 工作时间展示颜色
	 */
	private String fdTimeWorkColor = null;

	public java.lang.String getTimeType() {
		return timeType;
	}

	public void setTimeType(java.lang.String timeType) {
		this.timeType = timeType;
	}

	/*
	 * 班次列表
	 */
	private List sysTimeWorkTimeFormList = new AutoArrayList(
			SysTimeWorkTimeForm.class);

	/**
	 * @return 工作时间展示颜色
	 */
	public String getFdTimeWorkColor() {
		return fdTimeWorkColor;
	}

	public void setFdTimeWorkColor(String fdTimeWorkColor) {
		this.fdTimeWorkColor = fdTimeWorkColor;
	}

	/**
	 * @return 返回 所属区域组
	 */
	public String getSysTimeAreaId() {
		return sysTimeAreaId;
	}

	/**
	 * @param fdAreaId
	 *            要设置的 所属区域组
	 */
	public void setSysTimeAreaId(String fdAreaId) {
		this.sysTimeAreaId = fdAreaId;
	}

	/**
	 * @return 返回 从星期几开始
	 */
	public String getFdWeekStartTime() {
		return fdWeekStartTime;
	}

	/**
	 * @param fdWeekStartTime
	 *            要设置的 从星期几开始
	 */
	public void setFdWeekStartTime(String fdWeekStartTime) {
		this.fdWeekStartTime = fdWeekStartTime;
	}

	public List getSysTimeWorkTimeFormList() {
		return sysTimeWorkTimeFormList;
	}

	public void setSysTimeWorkTimeFormList(List sysTimeWorkTimeFormList) {
		this.sysTimeWorkTimeFormList = sysTimeWorkTimeFormList;
	}

	/**
	 * @return 返回 到星期几结束
	 */
	public String getFdWeekEndTime() {
		return fdWeekEndTime;
	}

	/**
	 * @param fdWeekEndTime
	 *            要设置的 到星期几结束
	 */
	public void setFdWeekEndTime(String fdWeekEndTime) {
		this.fdWeekEndTime = fdWeekEndTime;
	}

	/**
	 * @return 返回 工作的有效开始时间
	 */
	public String getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            要设置的 工作的有效开始时间
	 */
	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * @return 返回 工作的有效结束时间
	 */
	public String getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            要设置的 工作的有效结束时间
	 */
	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	/**
	 * @return 返回 创建人
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public SysTimeCommonTimeForm getSysTimeCommonTimeForm() {
		return sysTimeCommonTimeForm;
	}

	public void
			setSysTimeCommonTimeForm(
					SysTimeCommonTimeForm sysTimeCommonTimeForm) {
		this.sysTimeCommonTimeForm = sysTimeCommonTimeForm;
	}

	public String getSysTimeCommonId() {
		return sysTimeCommonId;
	}

	public void setSysTimeCommonId(String sysTimeCommonId) {
		this.sysTimeCommonId = sysTimeCommonId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		sysTimeAreaId = null;
		sysTimeCommonId = null;
		fdWeekStartTime = null;
		fdWeekEndTime = null;
		fdStartTime = null;
		fdEndTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdTimeWorkColor = null;
		sysTimeWorkTimeFormList.clear();
		docCreateTime = null;
		sysTimeCommonTimeForm = new SysTimeCommonTimeForm();
		fdTotalDay =1F;
		fdRestStartType =1;
		fdRestEndType =1;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysTimeWork.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysTimeAreaId",
					new FormConvertor_IDToModel("sysTimeArea",
							SysTimeArea.class));
			toModelPropertyMap.put("sysTimeCommonId",
					new FormConvertor_IDToModel("sysTimeCommonTime",
							SysTimeCommonTime.class));
			toModelPropertyMap.put("sysTimeWorkTimeFormList",
					new FormConvertor_FormListToModelList(
							"sysTimeWorkTimeList", "sysTimeWorkTime"));
		}
		return toModelPropertyMap;
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
	/***
	 * 午休结束时间的标识，
	 * 2次日，1当日
	 */
	private Integer fdRestEndType;

	public Integer getFdRestEndType() {
		if(fdRestEndType ==null){
			fdRestEndType =1;
		}
		return fdRestEndType;
	}

	public void setFdRestEndType(Integer fdRestEndType) {
		this.fdRestEndType = fdRestEndType;
	}
}
