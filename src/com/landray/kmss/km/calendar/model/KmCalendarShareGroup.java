package com.landray.kmss.km.calendar.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarShareGroupForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 日程共享组设置 model
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarShareGroup extends BaseModel implements ISysNotifyModel {

	/*
	 * 名称
	 */
	protected String fdName;

	/*
	 * 备注
	 */
	protected String fdDescription;

	/*
	 * 排序号
	 */
	protected Long fdOrder;

	/*
	 * 创建人
	 */
	protected SysOrgPerson docCreator;

	/*
	 * 创建时间
	 */
	protected Date docCreateTime;

	/*
	 * 群级成员IDS(用分号分隔)
	 */
	protected String fdGroupMemberIds;

	/*
	 * 群级成员名称(用分号分隔)
	 */
	protected String fdGroupMemberNames;

	/*
	 * 当前日程共享组共享成员列表
	 */
	private List<SysOrgElement> shareGroupMembers;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public Long getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Long fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdGroupMemberIds() {
		return fdGroupMemberIds;
	}

	public void setFdGroupMemberIds(String fdGroupMemberIds) {
		this.fdGroupMemberIds = fdGroupMemberIds;
	}

	public String getFdGroupMemberNames() {
		return fdGroupMemberNames;
	}

	public void setFdGroupMemberNames(String fdGroupMemberNames) {
		this.fdGroupMemberNames = fdGroupMemberNames;
	}

	public List<SysOrgElement> getShareGroupMembers() {
		return shareGroupMembers;
	}

	public void setShareGroupMembers(List<SysOrgElement> shareGroupMembers) {
		this.shareGroupMembers = shareGroupMembers;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("shareGroupMembers",
					new ModelConvertor_ModelListToString(
							"fdGroupMemberIds:fdGroupMemberNames",
							"fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarShareGroupForm.class;
	}

}
