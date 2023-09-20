package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgPersonAddressTypeForm;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮 个人地址本分类
 */
public class SysOrgPersonAddressType extends BaseModel {

	/*
	 * 名称
	 */
	protected String fdName;

	/*
	 * 创建者
	 */
	protected SysOrgElement docCreator;

	/*
	 * 创建时间
	 */
	protected Date docCreateTime;
	/*
	 * 排序号
	 */
	private Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysOrgPersonAddressType() {
		super();
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

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * @return 返回 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 分类成员
	 */
	protected List sysOrgPersonTypeList = new ArrayList();

	public List getSysOrgPersonTypeList() {
		return sysOrgPersonTypeList;
	}

	public void setSysOrgPersonTypeList(List sysOrgPersonTypeList) {
		this.sysOrgPersonTypeList = sysOrgPersonTypeList;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap
					.put("sysOrgPersonTypeList",
							new ModelConvertor_ModelListToString(
									"fdTypeMemberIds:fdTypeMemberNames",
									"fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
    public Class getFormClass() {
		return SysOrgPersonAddressTypeForm.class;
	}
}
