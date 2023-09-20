package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalCpageCardForm;

public class SysMportalCpageCard extends BaseModel{
	
	protected String fdName;
	protected Integer fdOrder;
	// 上边间隙
	protected Boolean fdMargin = Boolean.TRUE;
	protected SysMportalCpage sysMportalCpage;
	protected SysMportalCard sysMportalCard;
	
	public Boolean getFdMargin() {
		return fdMargin;
	}

	public void setFdMargin(Boolean fdMargin) {
		this.fdMargin = fdMargin;
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

	
	public SysMportalCpage getSysMportalCpage() {
		return sysMportalCpage;
	}

	public void setSysMportalCpage(SysMportalCpage sysMportalCpage) {
		this.sysMportalCpage = sysMportalCpage;
	}

	public SysMportalCard getSysMportalCard() {
		return sysMportalCard;
	}

	public void setSysMportalCard(SysMportalCard sysMportalCard) {
		this.sysMportalCard = sysMportalCard;
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;
	
	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysMportalCpage.fdId", "sysMportalCpageId");
			toFormPropertyMap.put("sysMportalCpage.fdName", "sysMportalCpageName");
			toFormPropertyMap.put("sysMportalCard.fdId", "sysMportalCardId");
			toFormPropertyMap.put("sysMportalCard.fdName", "sysMportalCardName");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysMportalCpageCardForm.class;
	}
	
}
