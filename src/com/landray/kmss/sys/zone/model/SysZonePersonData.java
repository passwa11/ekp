package com.landray.kmss.sys.zone.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.zone.forms.SysZonePersonDataForm;
import com.landray.kmss.util.StringUtil;

/**
 * 个人资料
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonData extends BaseModel {
	/**
	 * 目录名
	 */
	protected String fdName;

	/**
	 * @return 目录名
	 */
	public String getFdName() {
		if (StringUtil.isNull(this.fdName)) {
			this.fdName = this.fdPerson.getDocSubject();
		}
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
	 * 所属个人资料目录
	 */
	protected SysZonePersonDataCate fdDataCate;

	/**
	 * @return 所属个人资料目录
	 */
	public SysZonePersonDataCate getFdDataCate() {
		return fdDataCate;
	}

	/**
	 * @param fdDataCate
	 *            所属个人资料目录
	 */
	public void setFdDataCate(SysZonePersonDataCate fdDataCate) {
		this.fdDataCate = fdDataCate;
	}

	/**
	 * 所属员工
	 */
	protected SysZonePersonInfo fdPerson;

	/**
	 * 所属员工
	 * 
	 * @return
	 */
	public SysZonePersonInfo getFdPerson() {
		return fdPerson;
	}

	/**
	 * 所属员工
	 * 
	 * @param fdPerson
	 */
	public void setFdPerson(SysZonePersonInfo fdPerson) {
		this.fdPerson = fdPerson;
	}

	// 机制开始

	// 机制结束

	@Override
    public Class getFormClass() {
		return SysZonePersonDataForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdDataCate.fdId", "fdDataCateId");
			toFormPropertyMap.put("fdDataCate.fdName", "fdDataCateName");
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
		}
		return toFormPropertyMap;
	}
}
