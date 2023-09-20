package com.landray.kmss.sys.time.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountForm;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-12
 */
public class SysTimeLeaveAmount extends BaseModel implements ISysAuthAreaModel {

	/**
	 * 人员
	 */
	private SysOrgPerson fdPerson;

	/**
	 * 年份
	 */
	private Integer fdYear;

	/**
	 * 额度明细
	 */
	private List<SysTimeLeaveAmountItem> fdAmountItems;

	private Date docCreateTime;

	/**
	 * 所属用户 与fdPerson一致
	 */
	private SysOrgPerson docCreator;

	/**
	 * 创建者 (替换docCreator)
	 */
	private SysOrgPerson fdOperator;

	private Date docAlterTime;

	private SysOrgPerson docAlteror;

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	public Integer getFdYear() {
		return fdYear;
	}

	public void setFdYear(Integer fdYear) {
		this.fdYear = fdYear;
	}

	public List<SysTimeLeaveAmountItem> getFdAmountItems() {
		return fdAmountItems;
	}

	public void setFdAmountItems(List<SysTimeLeaveAmountItem> fdAmountItems) {
		this.fdAmountItems = fdAmountItems;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
	}

	public SysOrgPerson getFdOperator() {
		return fdOperator;
	}

	public void setFdOperator(SysOrgPerson fdOperator) {
		this.fdOperator = fdOperator;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("fdAmountItems",
					new ModelConvertor_ModelListToFormList("fdAmountItems"));
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
			toFormPropertyMap.put("fdOperator.fdId", "fdOperatorId");
		}
		return toFormPropertyMap;
	}

	@Override
    public Class getFormClass() {
		return SysTimeLeaveAmountForm.class;
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
