package com.landray.kmss.km.calendar.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDToModel;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDsToModelList;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 日程共享组设置 Form
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarShareGroupForm extends ExtendForm {

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
	 * 创建人ID
	 */
	private String docCreatorId = null;

	/*
	 * 创建人名称
	 */
	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	protected String docCreateTime;

	/*
	 * 群级成员IDS
	 */
	private String fdGroupMemberIds = null;
	/*
	 * 群级成员名称
	 */
	private String fdGroupMemberNames = null;

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

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
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

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdName = null;
		this.fdDescription = null;
		this.fdOrder = null;
		this.docCreatorId = null;
		this.docCreatorName = null;
		this.docCreateTime = null;
		this.fdGroupMemberIds = null;
		this.fdGroupMemberNames = null;
		this.shareGroupMembers = null;
		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarShareGroup.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new KmCalendar_FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdGroupMemberIds",
					new KmCalendar_FormConvertor_IDsToModelList(
							"shareGroupMembers",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

}
