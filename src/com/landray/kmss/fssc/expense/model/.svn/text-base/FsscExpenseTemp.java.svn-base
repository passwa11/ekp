package com.landray.kmss.fssc.expense.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTempForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;

/**
  * 选择发票
  */
public class FsscExpenseTemp extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMainId;

    private List<FsscExpenseTempDetail> fdInvoiceListTemp;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<FsscExpenseTempForm> getFormClass() {
        return FsscExpenseTempForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdInvoiceListTemp", new ModelConvertor_ModelListToFormList("fdInvoiceListTemp_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public List<FsscExpenseTempDetail> getFdInvoiceListTemp() {
        return this.fdInvoiceListTemp;
    }

    /**
     * 选择发票信息明细
     */
    public void setFdInvoiceListTemp(List<FsscExpenseTempDetail> fdInvoiceListTemp) {
        this.fdInvoiceListTemp = fdInvoiceListTemp;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
