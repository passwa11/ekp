package com.landray.kmss.hr.staff.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffRatifyLogForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.DateUtil;

public class HrStaffRatifyLog extends BaseModel {

	@Override
	public Class getFormClass() {
		return HrStaffRatifyLogForm.class;
	}

	// 人员
	private SysOrgElement fdOrgPerson;
	// 异动前部门
	private SysOrgElement fdRatifyOldDept;
	// 异动后部门
	private List<SysOrgPost> fdRatifyOldPosts;

	private String fdRatifyType;


	private SysOrgElement fdRatifyDept;

	private List<SysOrgPost> fdRatifyPosts;

	private Date fdRatifyDate;

	private String fdRatifyProcess;

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdOrgPerson.fdId", "fdOrgPersonId");
			toFormPropertyMap.put("fdOrgPerson.fdName",
					"fdOrgPersonNames");
			toFormPropertyMap.put("fdRatifyOldDept.fdId", "fdRatifyOldDeptId");
			toFormPropertyMap.put("fdRatifyOldDept.fdName",
					"fdRatifyOldDeptName");
			toFormPropertyMap.put("fdRatifyOldPosts",
					new ModelConvertor_ModelListToString(
							"fdRatifyOldPostIds:fdRatifyOldPostNames",
							"fdId:fdName"));

			toFormPropertyMap.put("fdRatifyDept.fdId", "fdRatifyDeptId");
			toFormPropertyMap.put("fdRatifyDept.fdName", "fdRatifyDeptName");
			toFormPropertyMap.put("fdRatifyPosts",
					new ModelConvertor_ModelListToString(
							"fdRatifyPostIds:fdRatifyPostNames",
							"fdId:fdName"));
			toFormPropertyMap.put("fdRatifyDate",
					new ModelConvertor_Common("fdRatifyDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

	public String getFdRatifyType() {
		return fdRatifyType;
	}

	public void setFdRatifyType(String fdRatifyType) {
		this.fdRatifyType = fdRatifyType;
	}

	public SysOrgElement getFdRatifyDept() {
		return fdRatifyDept;
	}

	public void setFdRatifyDept(SysOrgElement fdRatifyDept) {
		this.fdRatifyDept = fdRatifyDept;
	}

	public List<SysOrgPost> getFdRatifyPosts() {
		return fdRatifyPosts;
	}

	public void setFdRatifyPosts(List<SysOrgPost> fdRatifyPosts) {
		this.fdRatifyPosts = fdRatifyPosts;
	}

	public Date getFdRatifyDate() {
		return fdRatifyDate;
	}

	public void setFdRatifyDate(Date fdRatifyDate) {
		this.fdRatifyDate = fdRatifyDate;
	}

	public String getFdRatifyProcess() {
		return fdRatifyProcess;
	}

	public void setFdRatifyProcess(String fdRatifyProcess) {
		this.fdRatifyProcess = fdRatifyProcess;
	}

	public SysOrgElement getFdOrgPerson() {
		return fdOrgPerson;
	}

	public void setFdOrgPerson(SysOrgElement fdOrgPerson) {
		this.fdOrgPerson = fdOrgPerson;
	}

	public SysOrgElement getFdRatifyOldDept() {
		return fdRatifyOldDept;
	}

	public void setFdRatifyOldDept(SysOrgElement fdRatifyOldDept) {
		this.fdRatifyOldDept = fdRatifyOldDept;
	}

	public List<SysOrgPost> getFdRatifyOldPosts() {
		return fdRatifyOldPosts;
	}

	public void setFdRatifyOldPosts(List<SysOrgPost> fdRatifyOldPosts) {
		this.fdRatifyOldPosts = fdRatifyOldPosts;
	}


}
