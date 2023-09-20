package com.landray.kmss.eop.basedata.model;

import java.util.List;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.eop.basedata.forms.EopBasedataPaymentForm;

/**
  * 付款单
  */
public class EopBasedataPayment extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdModelId;

    private String fdModelName;

    private String fdSubject;

    private String fdModelNumber;

    private Double fdPaymentMoney;

    private Date fdPaymentTime;

    private String fdStatus;

    private String fdRemark;

    private List<EopBasedataPaymentDetail> fdDetail;

    @Override
    public Class<EopBasedataPaymentForm> getFormClass() {
        return EopBasedataPaymentForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdPaymentTime", new ModelConvertor_Common("fdPaymentTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Double getFdPaymentMoney() {
        return this.fdPaymentMoney;
    }

    /**
     * 实际支付金额
     */
    public void setFdPaymentMoney(Double fdPaymentMoney) {
        this.fdPaymentMoney = fdPaymentMoney;
    }

    /**
     * 付款时间
     */
    public Date getFdPaymentTime() {
        return this.fdPaymentTime;
    }

    /**
     * 付款时间
     */
    public void setFdPaymentTime(Date fdPaymentTime) {
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
    public List<EopBasedataPaymentDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 付款单明细
     */
    public void setFdDetail(List<EopBasedataPaymentDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }
}
