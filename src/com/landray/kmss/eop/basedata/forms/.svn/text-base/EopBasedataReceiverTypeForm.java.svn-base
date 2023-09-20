package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataReceiverType;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 收款类型
  */
public class EopBasedataReceiverTypeForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdCode;

    private String fdType;  // 类别(应收/实收)

    private String fdIsAvailable;

    private String docCreatorId;

    private String docCreatorName;

    private String fdAccountsId;

    private String fdAccountsName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        docCreateTime = null;
        fdCode = null;
        fdType = null; // 类别(应收/实收)
        fdIsAvailable = null;
        docCreatorId = null;
        docCreatorName = null;
        fdAccountsId = null;
        fdAccountsName = null;
        fdCompanyListIds = null;
        fdCompanyListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataReceiverType> getModelClass() {
        return EopBasedataReceiverType.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdAccountsId", new FormConvertor_IDToModel("fdAccounts", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 类别(应收/实收)
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 类别(应收/实收)
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 对应会计科目
     */
    public String getFdAccountsId() {
        return this.fdAccountsId;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccountsId(String fdAccountsId) {
        this.fdAccountsId = fdAccountsId;
    }

    /**
     * 对应会计科目
     */
    public String getFdAccountsName() {
        return this.fdAccountsName;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccountsName(String fdAccountsName) {
        this.fdAccountsName = fdAccountsName;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyListIds() {
        return this.fdCompanyListIds;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyListIds(String fdCompanyListIds) {
        this.fdCompanyListIds = fdCompanyListIds;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyListNames() {
        return this.fdCompanyListNames;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyListNames(String fdCompanyListNames) {
        this.fdCompanyListNames = fdCompanyListNames;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
