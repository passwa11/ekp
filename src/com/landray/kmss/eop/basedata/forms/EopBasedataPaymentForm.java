package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;

/**
  * 付款单
  */
public class EopBasedataPaymentForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelId;

    private String fdModelName;

    private String fdSubject;

    private String fdModelNumber;

    private String fdPaymentMoney;

    private String fdPaymentTime;

    private String fdStatus;

    private String fdRemark;

    private AutoArrayList fdDetail_Form = new AutoArrayList(EopBasedataPaymentDetailForm.class);

    private String fdDetail_Flag;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdModelId = null;
        fdModelName = null;
        fdSubject = null;
        fdModelNumber = null;
        fdPaymentMoney = null;
        fdPaymentTime = null;
        fdStatus = null;
        fdRemark = null;
        fdDetail_Form = new AutoArrayList(EopBasedataPaymentDetailForm.class);
        fdDetail_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataPayment> getModelClass() {
        return EopBasedataPayment.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdPaymentTime", new FormConvertor_Common("fdPaymentTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 模块ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 模块ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 模块名称
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块名称
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 对应单据
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 对应单据
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 单据编号
     */
    public String getFdModelNumber() {
        return this.fdModelNumber;
    }

    /**
     * 单据编号
     */
    public void setFdModelNumber(String fdModelNumber) {
        this.fdModelNumber = fdModelNumber;
    }

    /**
     * 实际支付金额
     */
    public String getFdPaymentMoney() {
        return this.fdPaymentMoney;
    }

    /**
     * 实际支付金额
     */
    public void setFdPaymentMoney(String fdPaymentMoney) {
        this.fdPaymentMoney = fdPaymentMoney;
    }

    /**
     * 付款时间
     */
    public String getFdPaymentTime() {
        return this.fdPaymentTime;
    }

    /**
     * 付款时间
     */
    public void setFdPaymentTime(String fdPaymentTime) {
        this.fdPaymentTime = fdPaymentTime;
    }

    /**
     * 付款状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 付款状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 描述
     */
    public String getFdRemark() {
        return this.fdRemark;
    }

    /**
     * 描述
     */
    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    /**
     * 付款单明细
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 付款单明细
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 付款单明细
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 付款单明细
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }
}
