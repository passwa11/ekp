package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPersonAddressType;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮
 */
public class SysOrgPersonAddressTypeForm extends ExtendForm {
	/*
	 * 名称
	 */
	private String fdName = null;
	/*
	 * 成员
	 */
	private String fdTypeMemberIds = null;
	private String fdTypeMemberNames = null;
	/*
	 * 创建者
	 */
	private String docCreatorId = null;
	private String docCreatorName = null;
	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 排序号
	 */
	private String fdOrder;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
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

	public String getFdTypeMemberIds() {
		return fdTypeMemberIds;
	}

	public void setFdTypeMemberIds(String fdTypeMemberIds) {
		this.fdTypeMemberIds = fdTypeMemberIds;
	}

	public String getFdTypeMemberNames() {
		return fdTypeMemberNames;
	}

	public void setFdTypeMemberNames(String fdTypeMemberNames) {
		this.fdTypeMemberNames = fdTypeMemberNames;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		fdTypeMemberIds = null;
		fdTypeMemberNames = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("fdTypeMemberIds",
					new FormConvertor_IDsToModelList("sysOrgPersonTypeList",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysOrgPersonAddressType.class;
	}

}
