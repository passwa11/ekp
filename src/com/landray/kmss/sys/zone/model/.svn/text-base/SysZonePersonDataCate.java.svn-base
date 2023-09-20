package com.landray.kmss.sys.zone.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.zone.forms.SysZonePersonDataCateForm;

/**
 * 个人资料目录设置
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataCate extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 状态
	 */
	protected String docStatus = "1";

	/**
	 * @return 状态
	 */
	public String getDocStatus() {
		return docStatus;
	}

	/**
	 * @param docStatus
	 *            状态
	 */
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	/**
	 * 个人资料目录模版列表
	 */
	protected List<SysZonePerDataTempl> fdDataCateTempls;

	/**
	 * @return 个人资料目录模版列表
	 */
	public List<SysZonePerDataTempl> getFdDataCateTempls() {
		return fdDataCateTempls;
	}

	/**
	 * @param fdDataCateTempls
	 *            个人资料目录模版列表
	 */
	public void setFdDataCateTempls(List<SysZonePerDataTempl> fdDataCateTempls) {
		this.fdDataCateTempls = fdDataCateTempls;
	}

	// 机制开始

	// 机制结束

	@Override
    public Class getFormClass() {
		return SysZonePersonDataCateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap
					.put("fdDataCateTempls",
							new ModelConvertor_ModelListToFormList(
									"fdDataCateTemplForms")
									.setIndexProperty("fdOrder"));
		}
		return toFormPropertyMap;
	}
}
