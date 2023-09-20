package com.landray.kmss.sys.zone.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.zone.forms.SysZonePerDataTemplForm;

/**
 * 个人资料目录模版设置
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePerDataTempl extends BaseModel {

	/**
	 * 目录名
	 */
	protected String fdName;

	/**
	 * @return 目录名
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            目录名
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 内容
	 */
	protected String docContent;

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 所属个人资料目录
	 */
	protected SysZonePersonDataCate fdPersonDataCate;

	/**
	 * 
	 * @return 所属个人资料目录
	 */
	public SysZonePersonDataCate getFdPersonDataCate() {
		return fdPersonDataCate;
	}

	/**
	 * 所属个人资料目录
	 * 
	 * @param fdPersonDataCate
	 */
	public void setFdPersonDataCate(SysZonePersonDataCate fdPersonDataCate) {
		this.fdPersonDataCate = fdPersonDataCate;
	}

	// 机制开始

	// 机制结束

	@Override
    public Class getFormClass() {
		return SysZonePerDataTemplForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPersonDataCate.fdId",
					"fdPersonDataCateId");
			toFormPropertyMap.put("fdPersonDataCate.fdName",
					"fdPersonDataCateName");
		}
		return toFormPropertyMap;
	}
}
