package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalCpageRelationForm;

public class SysMportalCpageRelation extends BaseModel{
	/**
	 * 名称
	 */
	protected String fdName;
	/**
	 * 图标
	 */
	protected String fdIcon;
	/**
	 * 素材库url
	 */
	protected String fdImg;

	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	/**
	 * 类别
	 */
	protected String fdType;
	/**
	 * 复合门户ID
	 */
	protected String fdMainCompositeId;
	/**
	 * 父节点
	 */
	protected SysMportalCpageRelation fdParent;
	/**
	 * 复合门户页
	 */
	protected SysMportalCpage sysMportalCpage;
	/**
	 * 复合门户
	 */
	protected SysMportalComposite sysMportalComposite;
	
	//子页面
	protected List<SysMportalCpageRelation> childPageRelations = new ArrayList<SysMportalCpageRelation>();

	public List<SysMportalCpageRelation> getChildPageRelations() {
		return childPageRelations;
	}

	public void setChildPageRelations(List<SysMportalCpageRelation> childPageRelations) {
		this.childPageRelations = childPageRelations;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
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

	public SysMportalCpageRelation getFdParent() {
		return fdParent;
	}

	public void setFdParent(SysMportalCpageRelation fdParent) {
		this.fdParent = fdParent;
	}

	public String getFdMainCompositeId() {
		return fdMainCompositeId;
	}

	public void setFdMainCompositeId(String fdMainCompositeId) {
		this.fdMainCompositeId = fdMainCompositeId;
	}

	public SysMportalCpage getSysMportalCpage() {
		return sysMportalCpage;
	}

	public void setSysMportalCpage(SysMportalCpage sysMportalCpage) {
		this.sysMportalCpage = sysMportalCpage;
	}
	
	public SysMportalComposite getSysMportalComposite() {
		return sysMportalComposite;
	}

	public void setSysMportalComposite(SysMportalComposite sysMportalComposite) {
		this.sysMportalComposite = sysMportalComposite;
	}



	private static ModelToFormPropertyMap toFormPropertyMap;
	
	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysMportalCpage.fdId", "sysMportalCpageId");
			toFormPropertyMap.put("sysMportalCpage.fdName", "sysMportalCpageName");
			toFormPropertyMap.put("sysMportalCpage.fdType", "sysMportalCpageType");
			toFormPropertyMap.put("sysMportalComposite.fdId", "sysMportalCompositeId");
			toFormPropertyMap.put("sysMportalComposite.fdName", "sysMportalCompositeName");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");			
			toFormPropertyMap.put("childPageRelations",
						new ModelConvertor_ModelListToFormList("childPageRelations"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysMportalCpageRelationForm.class;
	}
	
}
