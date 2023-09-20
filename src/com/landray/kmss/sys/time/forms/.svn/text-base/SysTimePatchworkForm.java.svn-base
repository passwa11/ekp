package com.landray.kmss.sys.time.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimePatchworkForm extends ExtendForm {
	/*
	 * 名称
	 */
	private String fdName = null;
	private String sysTimeCommonId = null;

	/*
	 * 所属区域组
	 */
	private String sysTimeAreaId = null;
	/*
	 * 类型 1.通用 2.自定义
	 */
	protected java.lang.String timeType;

	/*
	 * 开始时间
	 */
	private String fdStartTime = null;

	/*
	 * 结束时间
	 */
	private String fdEndTime = null;

	/*
	 * 创建人ID
	 */
	private String docCreatorId = null;

	/*
	 * 创建人姓名
	 */
	private String docCreatorName = null;
	/*
	 * 补班时间展示颜色
	 */
	private String fdPatchWorkColor = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	public java.lang.String getTimeType() {
		return timeType;
	}

	public void setTimeType(java.lang.String timeType) {
		this.timeType = timeType;
	}

	/*
	 * 班次列表
	 */
	private List sysTimePatchworkTimeFormList = new AutoArrayList(
			SysTimePatchworkTimeForm.class);

	/**
	 * @return 返回 补班时间展示颜色
	 */
	public String getFdPatchWorkColor() {
		return fdPatchWorkColor;
	}

	public void setFdPatchWorkColor(String fdPatchWorkColor) {
		this.fdPatchWorkColor = fdPatchWorkColor;
	}

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}
	/**
	 * 总工时 半天还是一天。用于统计
	 */
	protected Float fdTotalDay;
	/*
	 * 班次列表
	 */
	private SysTimeCommonTimeForm sysTimeCommonTimeForm = new SysTimeCommonTimeForm();
	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
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
	 * @return 返回 开始时间
	 */
	public String getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            要设置的 开始时间
	 */
	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * @return 返回 结束时间
	 */
	public String getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            要设置的 结束时间
	 */
	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public SysTimeCommonTimeForm getSysTimeCommonTimeForm() {
		return sysTimeCommonTimeForm;
	}

	public void
			setSysTimeCommonTimeForm(
					SysTimeCommonTimeForm sysTimeCommonTimeForm) {
		this.sysTimeCommonTimeForm = sysTimeCommonTimeForm;
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

	public List getSysTimePatchworkTimeFormList() {
		return sysTimePatchworkTimeFormList;
	}

	public void setSysTimePatchworkTimeFormList(
			List sysTimePatchworkTimeFormList) {
		this.sysTimePatchworkTimeFormList = sysTimePatchworkTimeFormList;
	}

	public String getSysTimeCommonId() {
		return sysTimeCommonId;
	}

	public void setSysTimeCommonId(String sysTimeCommonId) {
		this.sysTimeCommonId = sysTimeCommonId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		sysTimeAreaId = null;
		fdStartTime = null;
		fdEndTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		fdPatchWorkColor = null;
		sysTimePatchworkTimeFormList.clear();
		fdTotalDay =1F;
		fdRestStartType =1;
		fdRestEndType =1;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysTimePatchwork.class;
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
			toModelPropertyMap.put("sysTimePatchworkTimeFormList",
					new FormConvertor_FormListToModelList(
							"sysTimePatchworkTimeList", "sysTimePatchworkTime"));
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
