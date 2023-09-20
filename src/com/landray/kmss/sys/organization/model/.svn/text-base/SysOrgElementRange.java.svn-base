package com.landray.kmss.sys.organization.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;

public class SysOrgElementRange extends BaseModel {
	/**
	 * 所属组织
	 */
	private SysOrgElement fdElement;
	
	/**
	 * 是否开启组织成员查看组织范围
	 */
	private Boolean fdIsOpenLimit;
	
	/**
	 * 查看类型
	 */
	private Integer fdViewType;
	
	/**
	 * 查看子类型
	 */
	private String fdViewSubType;
	
	/**
	 * 组织下成员查看范围
	 */
	private List<SysOrgElement> fdOthers;
	
	/**
	 * 钉钉邀请成员时地址
	 */
	private String fdInviteUrl;
	
	public SysOrgElement getFdElement() {
		return fdElement;
	}

	public void setFdElement(SysOrgElement fdElement) {
		this.fdElement = fdElement;
	}

	public Boolean getFdIsOpenLimit() {
		return fdIsOpenLimit;
	}

	public void setFdIsOpenLimit(Boolean fdIsOpenLimit) {
		this.fdIsOpenLimit = fdIsOpenLimit;
	}

	public Integer getFdViewType() {
		return fdViewType;
	}

	public void setFdViewType(Integer fdViewType) {
		this.fdViewType = fdViewType;
	}

	public String getFdViewSubType() {
		return fdViewSubType;
	}

	public void setFdViewSubType(String fdViewSubType) {
		this.fdViewSubType = fdViewSubType;
	}
	
	@Override
	public Class getFormClass() {
		return SysOrgElementRangeForm.class;
	}

	public List<SysOrgElement> getFdOthers() {
		return fdOthers;
	}

	public void setFdOthers(List<SysOrgElement> fdOthers) {
		this.fdOthers = fdOthers;
	}

	public String getFdInviteUrl() {
		return fdInviteUrl;
	}

	public void setFdInviteUrl(String fdInviteUrl) {
		this.fdInviteUrl = fdInviteUrl;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;
	
	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdElement.fdId", "fdElementId");
			toFormPropertyMap.put("fdElement.fdName", "fdElementName");
			toFormPropertyMap.put("fdOthers",
					new ModelConvertor_ModelListToString("fdOtherIds:fdOtherNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

}
