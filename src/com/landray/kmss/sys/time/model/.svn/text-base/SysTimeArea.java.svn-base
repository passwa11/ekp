package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.forms.SysTimeAreaForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 区域组设置
 */
@SuppressWarnings("serial")
public class SysTimeArea extends BaseModel implements ISysAuthAreaModel {
	/** 名称 */
	protected java.lang.String fdName;

	/** 创建时间 */
	protected java.util.Date docCreateTime = new Date();

	/** 创建人 */
	protected SysOrgElement docCreator = null;

	/** 班次设置列表 */
	protected List<SysTimeWork> sysTimeWorkList = new ArrayList<SysTimeWork>();

	/** 休假设置列表 */
	protected List<SysTimeVacation> sysTimeVacationList = new ArrayList<SysTimeVacation>();

	/** 补班设置列表 */
	protected List<SysTimePatchwork> sysTimePatchworkList = new ArrayList<SysTimePatchwork>();

	/**
	 * 是否按月批量排班(TRUE = 按个人排班)
	 */
	protected Boolean fdIsBatchSchedule;

	/**
	 * 人员排班信息
	 */
	protected List<SysTimeOrgElementTime> orgElementTimeList = new ArrayList<>();

	public Boolean getFdIsBatchSchedule() {
		if (fdIsBatchSchedule == null) {
			fdIsBatchSchedule = Boolean.FALSE;
		}
		return fdIsBatchSchedule;
	}

	public void setFdIsBatchSchedule(Boolean fdIsBatchSchedule) {
		this.fdIsBatchSchedule = fdIsBatchSchedule;
	}

	public List<SysTimeOrgElementTime> getOrgElementTimeList() {
		return orgElementTimeList;
	}

	public void
			setOrgElementTimeList(
					List<SysTimeOrgElementTime> orgElementTimeList) {
		this.orgElementTimeList = orgElementTimeList;
	}

	public SysTimeArea() {
		super();
	}

	/**
	 * @return 返回 名称
	 */
	public java.lang.String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(java.lang.String fdName) {
		this.fdName = fdName;
	}

	/**
	 * @return 返回 创建时间
	 */
	public java.util.Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(java.util.Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param sysOrgElement
	 *            要设置的 创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/*
	 * 区域组成员
	 */
	protected List<SysOrgElement> areaMembers = new ArrayList<SysOrgElement>();

	/**
	 * @return 返回 区域组成员
	 */
	public List<SysOrgElement> getAreaMembers() {
		return areaMembers;
	}

	/**
	 * @param toKhrusers
	 *            要设置的 区域组成员
	 */
	public void setAreaMembers(List<SysOrgElement> areaMembers) {
		this.areaMembers = areaMembers;
	}

	/*
	 * 时间维护人
	 */
	protected List<SysOrgElement> areaAdmins = new ArrayList<SysOrgElement>();

	/**
	 * @return 返回 时间维护人
	 */
	public List<SysOrgElement> getAreaAdmins() {
		return areaAdmins;
	}

	/**
	 * @param toKhrusers
	 *            要设置的 时间维护人
	 */
	public void setAreaAdmins(List<SysOrgElement> areaAdmins) {
		this.areaAdmins = areaAdmins;
	}

	public List<SysTimePatchwork> getSysTimePatchworkList() {
		return sysTimePatchworkList;
	}

	public void setSysTimePatchworkList(
			List<SysTimePatchwork> sysTimePatchworkList) {
		this.sysTimePatchworkList = sysTimePatchworkList;
	}

	public List<SysTimeVacation> getSysTimeVacationList() {
		return sysTimeVacationList;
	}

	public void
			setSysTimeVacationList(List<SysTimeVacation> sysTimeVacationList) {
		this.sysTimeVacationList = sysTimeVacationList;
	}

	public List<SysTimeWork> getSysTimeWorkList() {
		return sysTimeWorkList;
	}

	public void setSysTimeWorkList(List<SysTimeWork> sysTimeWorkkList) {
		this.sysTimeWorkList = sysTimeWorkkList;
	}

	@Override
	public Class<?> getFormClass() {
		return SysTimeAreaForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdHoliday.fdId", "fdHolidayId");
			toFormPropertyMap.put("fdHoliday.fdName", "fdHolidayName");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("areaMembers",
					new ModelConvertor_ModelListToString(
							"areaMemberIds:areaMemberNames", "fdId:fdName"));
			toFormPropertyMap.put("areaAdmins",
					new ModelConvertor_ModelListToString(
							"areaAdminIds:areaAdminNames", "fdId:fdName"));
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
		}
		return toFormPropertyMap;
	}

	private SysTimeHoliday fdHoliday = null;

	public SysTimeHoliday getFdHoliday() {
		return fdHoliday;
	}

	public void setFdHoliday(SysTimeHoliday fdHoliday) {
		this.fdHoliday = fdHoliday;
	}

	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
	public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
	public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}
}
