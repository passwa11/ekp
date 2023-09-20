package com.landray.kmss.sys.organization.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SysOrgElementHideRangeForm extends ExtendForm {
	
	/**
	 * 所属组织
	 */
	private String fdElementId;
	private String fdElementName;

	/**
	 * 是否开启组织成员查看组织范围
	 */
	private String fdIsOpenLimit;
	
	/**
	 * 查看类型
	 */
	private String fdViewType;

	/**
	 * 组织下成员查看范围
	 */
	private String fdOtherIds;
	private String fdOtherNames;

	public String getFdElementId() {
		return fdElementId;
	}

	public void setFdElementId(String fdElementId) {
		this.fdElementId = fdElementId;
	}

	public String getFdElementName() {
		return fdElementName;
	}

	public void setFdElementName(String fdElementName) {
		this.fdElementName = fdElementName;
	}

	public String getFdIsOpenLimit() {
		return fdIsOpenLimit;
	}

	public void setFdIsOpenLimit(String fdIsOpenLimit) {
		this.fdIsOpenLimit = fdIsOpenLimit;
	}

	public String getFdViewType() {
		return fdViewType;
	}

	public void setFdViewType(String fdViewType) {
		this.fdViewType = fdViewType;
	}

	public String getFdOtherIds() {
		return fdOtherIds;
	}

	public void setFdOtherIds(String fdOtherIds) {
		this.fdOtherIds = fdOtherIds;
	}

	public String getFdOtherNames() {
		return fdOtherNames;
	}

	public void setFdOtherNames(String fdOtherNames) {
		this.fdOtherNames = fdOtherNames;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdElementId = null;
		fdElementName = null;
		fdIsOpenLimit = null;
		fdViewType = null;
		fdOtherIds = null;
		fdOtherNames = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdElementId", new FormConvertor_IDToModel("fdElement", SysOrgElement.class));
			toModelPropertyMap.put("fdOtherIds", new FormConvertor_IDsToModelList("fdOthers", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysOrgElementHideRange.class;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null){
			return false;
		}
		SysOrgElementHideRangeForm form = (SysOrgElementHideRangeForm) o;
		boolean flag = true;
		if(StringUtil.isNotNull(form.getFdElementId())){
			if(!form.getFdElementId().equals(this.getFdElementId())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdElementId())){
				flag = false;
			}
		}
		if(StringUtil.isNotNull(form.getFdElementName())){
			if(!form.getFdElementName().equals(this.getFdElementName())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdElementName())){
				flag = false;
			}
		}
		if(StringUtil.isNotNull(form.getFdIsOpenLimit())){
			if(!form.getFdIsOpenLimit().equals(this.getFdIsOpenLimit())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdIsOpenLimit())){
				flag = false;
			}
		}
		if(StringUtil.isNotNull(form.getFdViewType())){
			if(!form.getFdViewType().equals(this.getFdViewType())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdViewType())){
				flag = false;
			}
		}
		if(StringUtil.isNotNull(form.getFdOtherIds())){
			if(!form.getFdOtherIds().equals(this.getFdOtherIds())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdOtherIds())){
				flag = false;
			}
		}
		if(StringUtil.isNotNull(form.getFdOtherNames())){
			if(!form.getFdOtherNames().equals(this.getFdOtherNames())){
				flag = false;
			}
		}else{
			if(StringUtil.isNotNull(this.getFdOtherNames())){
				flag = false;
			}
		}
		return flag;
	}
}
