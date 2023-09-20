package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 选择发票
  */
public class FsscExpenseTempForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdMainId;

    private AutoArrayList fdInvoiceListTemp_Form = new AutoArrayList(FsscExpenseTempDetailForm.class);

    private String fdInvoiceListTemp_Flag = "0";

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdMainId = null;
        fdInvoiceListTemp_Form = new AutoArrayList(FsscExpenseTempDetailForm.class);
        fdInvoiceListTemp_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseTemp> getModelClass() {
        return FsscExpenseTemp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdInvoiceListTemp_Form", new FormConvertor_FormListToModelList("fdInvoiceListTemp", "docMain", "fdInvoiceListTemp_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 报销ID
     */
    
    public String getFdMainId() {
		return fdMainId;
	}
    
    /**
     * 报销ID
     */

	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	/**
     * 选择发票信息明细
     */
    public AutoArrayList getFdInvoiceListTemp_Form() {
        return this.fdInvoiceListTemp_Form;
    }

    /**
     * 选择发票信息明细
     */
    public void setFdInvoiceListTemp_Form(AutoArrayList fdInvoiceListTemp_Form) {
        this.fdInvoiceListTemp_Form = fdInvoiceListTemp_Form;
    }

    /**
     * 选择发票信息明细
     */
    public String getFdInvoiceListTemp_Flag() {
        return this.fdInvoiceListTemp_Flag;
    }

    /**
     * 选择发票信息明细
     */
    public void setFdInvoiceListTemp_Flag(String fdInvoiceListTemp_Flag) {
        this.fdInvoiceListTemp_Flag = fdInvoiceListTemp_Flag;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
