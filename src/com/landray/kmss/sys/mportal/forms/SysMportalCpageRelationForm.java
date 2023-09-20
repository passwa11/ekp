package com.landray.kmss.sys.mportal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.model.SysMportalCpageRelation;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

public class SysMportalCpageRelationForm extends ExtendForm {

	private String fdName;

	private String fdOrder;

	private String fdType;
	
	private String fdIcon;
	private String fdImg;

	private String fdParentId;
	
	private String fdParentName;
	
	private String fdMainCompositeId;

	private String sysMportalCpageId;

	private String sysMportalCpageName;
	
	private String sysMportalCpageType;
	
	private String sysMportalCompositeId;

	private String sysMportalCompositeName;
	
	private List childPageRelations = new AutoArrayList(SysMportalCpageRelationForm.class);

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}	

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}



	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentName() {
		return fdParentName;
	}

	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}	

	public String getFdMainCompositeId() {
		return fdMainCompositeId;
	}

	public void setFdMainCompositeId(String fdMainCompositeId) {
		this.fdMainCompositeId = fdMainCompositeId;
	}

	public String getSysMportalCpageId() {
		return sysMportalCpageId;
	}

	public void setSysMportalCpageId(String sysMportalCpageId) {
		this.sysMportalCpageId = sysMportalCpageId;
	}

	public String getSysMportalCpageName() {
		return sysMportalCpageName;
	}

	public void setSysMportalCpageName(String sysMportalCpageName) {
		this.sysMportalCpageName = sysMportalCpageName;
	}

	public String getSysMportalCpageType() {
		return sysMportalCpageType;
	}

	public void setSysMportalCpageType(String sysMportalCpageType) {
		this.sysMportalCpageType = sysMportalCpageType;
	}

	public String getSysMportalCompositeId() {
		return sysMportalCompositeId;
	}

	public void setSysMportalCompositeId(String sysMportalCompositeId) {
		this.sysMportalCompositeId = sysMportalCompositeId;
	}

	public String getSysMportalCompositeName() {
		return sysMportalCompositeName;
	}

	public void setSysMportalCompositeName(String sysMportalCompositeName) {
		this.sysMportalCompositeName = sysMportalCompositeName;
	}
	

	public List getChildPageRelations() {
		return childPageRelations;
	}

	public void setChildPageRelations(List childPageRelations) {
		this.childPageRelations = childPageRelations;
	}

	@Override
	public Class getModelClass() {
		return SysMportalCpageRelation.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdImg = null;
		fdParentId = null;
		fdParentName = null;
		fdMainCompositeId = null;
		sysMportalCpageId = null;
		sysMportalCpageName = null;
		sysMportalCpageType = null;
		sysMportalCompositeId = null;
		sysMportalCompositeName = null;
		childPageRelations.clear();
		super.reset(mapping, request);
	}

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysMportalCpageId",
					new FormConvertor_IDToModel("sysMportalCpage",
							SysMportalCpage.class));

			toModelPropertyMap.put("sysMportalCompositeId",
					new FormConvertor_IDToModel("sysMportalComposite",
							SysMportalComposite.class));
			
			toModelPropertyMap.put("fdParentId",
					new FormConvertor_IDToModel("sysMportalCpageRelation",
							SysMportalCpageRelation.class));
		}
		return toModelPropertyMap;
	}
}
