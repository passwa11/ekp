package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffContractTypeForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateModel;
import com.landray.kmss.sys.print.model.SysPrintTemplate;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.AutoHashMap;

public class HrStaffContractType extends SysSimpleCategoryAuthTmpModel
		implements ISysPrintTemplateModel, IAttachment {

	private static ModelToFormPropertyMap toFormPropertyMap;

	/**
	 * 合同类型名称
	 */
	protected String fdName;
	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	@Override
    public String getFdName() {
		return fdName;
	}

	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Override
    public Integer getFdOrder() {
		return fdOrder;
	}

	@Override
    public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
	public Class getFormClass() {
		return HrStaffContractTypeForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	/**
	 * 打印机制
	 */
	private SysPrintTemplate sysPrintTemplate = new SysPrintTemplate();

	@Override
	public SysPrintTemplate getSysPrintTemplate() {
		return sysPrintTemplate;
	}

	@Override
	public void setSysPrintTemplate(SysPrintTemplate sysPrintTemplate) {
		this.sysPrintTemplate = sysPrintTemplate;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

}
