package com.landray.kmss.sys.time.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeAreaForm extends ExtendForm implements ISysAuthAreaForm {
	/*
	 * 名称
	 */
	private String fdName = null;

	/*
	 * 创建人Id
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

	/*
	 * 区域组成员ID
	 */
	private String areaMemberIds = null;

	/*
	 * 区域组成员姓名
	 */
	private String areaMemberNames = null;

	/*
	 * 时间维护人ID
	 */
	private String areaAdminIds = null;

	/*
	 * 时间维护人姓名
	 */
	private String areaAdminNames = null;

	/**
	 * 是否按月批量排班
	 */
	private Boolean fdIsBatchSchedule;

	/**
	 * 批量排班数据
	 */
	private String orgElementData;

	/**
	 * 导入文件
	 */
	private FormFile file = null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/** 班次设置列表 */
	private List<SysTimeWork> sysTimeWorkList = new ArrayList<SysTimeWork>();

	/** 休假设置列表 */
	private List<SysTimeVacation> sysTimeVacationList = new ArrayList<SysTimeVacation>();

	/** 补班设置列表 */
	private List<SysTimePatchwork> sysTimePatchworkList = new ArrayList<SysTimePatchwork>();

	public List<SysTimeWork> getSysTimeWorkList() {
		return sysTimeWorkList;
	}

	public void setSysTimeWorkList(List<SysTimeWork> sysTimeWorkList) {
		this.sysTimeWorkList = sysTimeWorkList;
	}

	public List<SysTimeVacation> getSysTimeVacationList() {
		return sysTimeVacationList;
	}

	public void
			setSysTimeVacationList(List<SysTimeVacation> sysTimeVacationList) {
		this.sysTimeVacationList = sysTimeVacationList;
	}

	public List<SysTimePatchwork> getSysTimePatchworkList() {
		return sysTimePatchworkList;
	}

	public void
			setSysTimePatchworkList(
					List<SysTimePatchwork> sysTimePatchworkList) {
		this.sysTimePatchworkList = sysTimePatchworkList;
	}
	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
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

	public String getAreaAdminIds() {
		return areaAdminIds;
	}

	public void setAreaAdminIds(String areaAdminIds) {
		this.areaAdminIds = areaAdminIds;
	}

	public String getAreaAdminNames() {
		return areaAdminNames;
	}

	public void setAreaAdminNames(String areaAdminNames) {
		this.areaAdminNames = areaAdminNames;
	}

	public String getAreaMemberIds() {
		return areaMemberIds;
	}

	public void setAreaMemberIds(String areaMemberIds) {
		this.areaMemberIds = areaMemberIds;
	}

	public String getAreaMemberNames() {
		return areaMemberNames;
	}

	public void setAreaMemberNames(String areaMemberNames) {
		this.areaMemberNames = areaMemberNames;
	}

	public Boolean getFdIsBatchSchedule() {
		if (fdIsBatchSchedule == null) {
			fdIsBatchSchedule = Boolean.FALSE;
		}
		return fdIsBatchSchedule;
	}

	public void setFdIsBatchSchedule(Boolean fdIsBatchSchedule) {
		this.fdIsBatchSchedule = fdIsBatchSchedule;
	}

	public String getOrgElementData() {
		return orgElementData;
	}

	public void setOrgElementData(String orgElementData) {
		this.orgElementData = orgElementData;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		areaMemberIds = null;
		areaMemberNames = null;
		areaAdminIds = null;
		areaAdminNames = null;
		fdHolidayId = null;
		fdHolidayName = null;
		fdIsBatchSchedule = null;
		authAreaId = null;
		authAreaName = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return SysTimeArea.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdHolidayId", new FormConvertor_IDToModel(
					"fdHoliday", SysTimeHoliday.class));
			toModelPropertyMap.put("areaMemberIds",
					new FormConvertor_IDsToModelList("areaMembers",
							SysOrgElement.class));
			toModelPropertyMap.put("areaAdminIds",
					new FormConvertor_IDsToModelList("areaAdmins",
							SysOrgElement.class));
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
		}
		return toModelPropertyMap;
	}
	
	private String fdHolidayId = null;
	private String fdHolidayName = null;

	public String getFdHolidayId() {
		return fdHolidayId;
	}

	public void setFdHolidayId(String fdHolidayId) {
		this.fdHolidayId = fdHolidayId;
	}

	public String getFdHolidayName() {
		return fdHolidayName;
	}

	public void setFdHolidayName(String fdHolidayName) {
		this.fdHolidayName = fdHolidayName;
	}

	// 所属场所ID
	protected String authAreaId = null;

	@Override
	public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
	public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	@Override
	public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
	public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}
}
