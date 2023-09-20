package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.print.forms.SysPrintTemplateForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffContractTypeForm extends SysSimpleCategoryAuthTmpForm
		implements ISysPrintTemplateForm, IAttachmentForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	/**
	 * 合同类型名称
	 */
	protected String fdName = null;

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	@Override
    public String getFdName() {
		return fdName;
	}

	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Override
    public String getFdOrder() {
		return fdOrder;
	}

	@Override
    public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return HrStaffContractType.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	/**
	 * 打印机制
	 */
	private SysPrintTemplateForm sysPrintTemplateForm = new SysPrintTemplateForm();

	@Override
	public SysPrintTemplateForm getSysPrintTemplateForm() {
		return sysPrintTemplateForm;
	}

	@Override
	public void setSysPrintTemplateForm(SysPrintTemplateForm form) {
		this.sysPrintTemplateForm = form;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

}
