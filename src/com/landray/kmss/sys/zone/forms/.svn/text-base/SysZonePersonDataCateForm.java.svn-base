package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;
import com.landray.kmss.util.AutoArrayList;

/**
 * 个人资料目录设置 Form
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataCateForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

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
	 * 目录模版
	 */
	protected AutoArrayList fdDataCateTemplForms = new AutoArrayList(
			SysZonePerDataTemplForm.class);

	/**
	 * 目录模版
	 * 
	 * @return
	 */
	public AutoArrayList getFdDataCateTemplForms() {
		return fdDataCateTemplForms;
	}

	/**
	 * 目录模版
	 * 
	 * @param fdDataCateTemplForms
	 */
	public void setFdDataCateTemplForms(AutoArrayList fdDataCateTemplForms) {
		this.fdDataCateTemplForms = fdDataCateTemplForms;
	}

	// 机制开始

	// 机制结束
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docStatus = null;
		fdDataCateTemplForms = new AutoArrayList(SysZonePerDataTemplForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePersonDataCate.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdDataCateTemplForms",
					new FormConvertor_FormListToModelList(
							"fdDataCateTempls",
							"SysZonePerDataTempl"));
		}
		return toModelPropertyMap;
	}
}
