package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinAccount;

/**
  * 企业微信账号
  */
public class ThirdWeixinAccountForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdAccountId;

    private String fdAccountType;

    private String fdAccountName;

    private String docCreateTime;

    private String docAlterTime;

    private String fdEkpId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdAccountId = null;
        fdAccountType = null;
        fdAccountName = null;
        docCreateTime = null;
        docAlterTime = null;
        fdEkpId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinAccount> getModelClass() {
        return ThirdWeixinAccount.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 账号ID
     */
    public String getFdAccountId() {
        return this.fdAccountId;
    }

    /**
     * 账号ID
     */
    public void setFdAccountId(String fdAccountId) {
        this.fdAccountId = fdAccountId;
    }

    /**
     * 账号类型
     */
    public String getFdAccountType() {
        return this.fdAccountType;
    }

    /**
     * 账号类型
     */
    public void setFdAccountType(String fdAccountType) {
        this.fdAccountType = fdAccountType;
    }

    /**
     * 账号名称
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 账号名称
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
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
     * EKP人员ID
     */
    public String getFdEkpId() {
        return this.fdEkpId;
    }

    /**
     * EKP人员ID
     */
    public void setFdEkpId(String fdEkpId) {
        this.fdEkpId = fdEkpId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
