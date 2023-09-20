package com.landray.kmss.sys.time.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeCommonTimeForm extends ExtendForm {
	/**
	 * 班次管理ID
	 */
	private String fdWorkId = null;

	/** 名称 */
	protected java.lang.String fdName;

	/**
	 * 简称
	 */
	protected java.lang.String simpleName;
	protected java.lang.String fdOrder;
	/**
	 * 状态
	 */
	protected java.lang.String status;
	protected java.lang.String isSchedule;
	/**
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String type;
	/**
	 * 总工时
	 */
	protected java.lang.String fdWorkHour;
	/**
	 * 总工时
	 */
	protected Float fdTotalDay;


	/** 班次展示颜色 */
	protected String fdWorkTimeColor;

	/**
	 * 午休开始时间
	 */
	private String fdRestStartTime = null;

	/**
	 * 午休结束时间
	 */
	private String fdRestEndTime = null;

	/**
	 * 班次设置
	 */
	protected List sysTimeWorkList = new AutoArrayList(
			SysTimeWorkForm.class);
	protected List sysTimePatchList = new AutoArrayList(
			SysTimePatchworkForm.class);
	protected List sysTimeWorkDetails = new AutoArrayList(
			SysTimeWorkDetailForm.class);

	public Float getFdTotalDay() {
		if(fdTotalDay ==null){
			return 1F;
		}
		return fdTotalDay;
	}

	public void setFdTotalDay(Float fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}

	/**
	 * @return 返回 班次管理ID
	 */
	public String getFdWorkId() {
		return fdWorkId;
	}

	/**
	 * @param fdWorkId
	 *            要设置的 班次管理ID
	 */
	public void setFdWorkId(String fdWorkId) {
		this.fdWorkId = fdWorkId;
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

	public java.lang.String getFdName() {
		return fdName;
	}

	public void setFdName(java.lang.String fdName) {
		this.fdName = fdName;
	}

	public java.lang.String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(java.lang.String simpleName) {
		this.simpleName = simpleName;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public java.lang.String getIsSchedule() {
		return isSchedule;
	}

	public void setIsSchedule(java.lang.String isSchedule) {
		this.isSchedule = isSchedule;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getFdWorkHour() {
		return fdWorkHour;
	}

	public void setFdWorkHour(java.lang.String fdWorkHour) {
		this.fdWorkHour = fdWorkHour;
	}

	public String getFdWorkTimeColor() {
		return fdWorkTimeColor;
	}

	public void setFdWorkTimeColor(String fdWorkTimeColor) {
		this.fdWorkTimeColor = fdWorkTimeColor;
	}

	public java.lang.String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(java.lang.String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdWorkId = null;
		fdRestStartTime = null;
		fdRestEndTime = null;
		authAreaId = null;
		authAreaName = null;
		fdTotalDay =1F;
		fdRestEndType =1;
		fdRestStartType =1;
		sysTimeWorkDetails.clear();
		super.reset(mapping, request);
	}

	public List getSysTimeWorkList() {
		return sysTimeWorkList;
	}

	public void setSysTimeWorkList(List sysTimeWorkList) {
		this.sysTimeWorkList = sysTimeWorkList;
	}

	public List getSysTimeWorkDetails() {
		return sysTimeWorkDetails;
	}

	public void setSysTimeWorkDetails(List sysTimeWorkDetails) {
		this.sysTimeWorkDetails = sysTimeWorkDetails;
	}

	@Override
    public Class getModelClass() {
		return SysTimeCommonTime.class;
	}

	public List getSysTimePatchList() {
		return sysTimePatchList;
	}

	public void setSysTimePatchList(List sysTimePatchList) {
		this.sysTimePatchList = sysTimePatchList;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysTimeWorkDetails",
					new FormConvertor_FormListToModelList(
							"sysTimeWorkDetails", "sysTimeCommonTime"));
			toModelPropertyMap.put("fdRestStartTime",
					new FormConvertor_Common("fdRestStartTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
			toModelPropertyMap.put("fdRestEndTime",
					new FormConvertor_Common("fdRestEndTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
		}
		return toModelPropertyMap;
	}

	// 所属场所ID
	protected String authAreaId = null;

	public String getAuthAreaId() {
		return authAreaId;
	}

	public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	public String getAuthAreaName() {
		return authAreaName;
	}

	public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
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
