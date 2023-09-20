package com.landray.kmss.third.payment.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.third.payment.model.ThirdPaymentMerchant;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 商户管理
  */
public class ThirdPaymentMerchantForm extends ExtendAuthForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docAlterTime;

    private String fdMerchId;

    private String fdMerchType;

    private String fdMerchName;

    private String fdCorpName;

    private String fdMerchStatus;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        docAlterTime = null;
        fdMerchId = null;
        fdMerchType = null;
        fdMerchName = null;
        fdCorpName = null;
        fdMerchStatus = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdPayWay = "wxworkpay";
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdPaymentMerchant> getModelClass() {
        return ThirdPaymentMerchant.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
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
    public String getFdMerchStatus() {
        return this.fdMerchStatus;
    }

    /**
     * 状态
     */
    public void setFdMerchStatus(String fdMerchStatus) {
        this.fdMerchStatus = fdMerchStatus;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "1";
    }

    private String fdPayWay;
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
}
