package com.landray.kmss.third.payment.model;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.third.payment.forms.ThirdPaymentMerchantForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 商户管理
  */
public class ThirdPaymentMerchant extends ExtendAuthModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docAlterTime;

    private String fdMerchId;

    private String fdMerchType;

    private String fdMerchName;

    private String fdCorpName;

    private Integer fdMerchStatus;

    private String fdPayWay;

    private SysOrgPerson docAlteror;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdPaymentMerchantForm> getFormClass() {
        return ThirdPaymentMerchantForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("fdPayWay");
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 商户号
     */
    public String getFdMerchId() {
        return this.fdMerchId;
    }

    /**
     * 商户号
     */
    public void setFdMerchId(String fdMerchId) {
        this.fdMerchId = fdMerchId;
    }

    /**
     * 商户类型
     */
    public String getFdMerchType() {
        return this.fdMerchType;
    }

    /**
     * 商户类型
     */
    public void setFdMerchType(String fdMerchType) {
        this.fdMerchType = fdMerchType;
    }

    /**
     * 商户简称
     */
    public String getFdMerchName() {
        return this.fdMerchName;
    }

    /**
     * 商户简称
     */
    public void setFdMerchName(String fdMerchName) {
        this.fdMerchName = fdMerchName;
    }

    /**
     * 企业名称
     */
    public String getFdCorpName() {
        return this.fdCorpName;
    }

    /**
     * 企业名称
     */
    public void setFdCorpName(String fdCorpName) {
        this.fdCorpName = fdCorpName;
    }

    /**
     * 状态
     */
    public Integer getFdMerchStatus() {
        return this.fdMerchStatus;
    }

    /**
     * 状态
     */
    public void setFdMerchStatus(Integer fdMerchStatus) {
        this.fdMerchStatus = fdMerchStatus;
    }

    /**
     * 所属支付方式
     */
    public String getFdPayWay() {
        return this.fdPayWay;
    }

    /**
     * 所属支付方式
     */
    public void setFdPayWay(String fdPayWay) {
        this.fdPayWay = fdPayWay;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public String getDocStatus() {
        return "30";
    }

    @Override
    public String getDocSubject() {
        return getFdMerchId();
    }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("fdMerchId",fdMerchId);
        o.put("fdMerchName",fdMerchName);
        o.put("fdCorpName",fdCorpName);
        o.put("fdMerchStatus",fdMerchStatus);
        return o;
    }
}
