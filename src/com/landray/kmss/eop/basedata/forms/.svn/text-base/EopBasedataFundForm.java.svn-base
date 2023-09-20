package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.eop.basedata.model.EopBasedataFund;

/**
  * 款项
  */
public class EopBasedataFundForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdOrder;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCode;

    private String fdAccountingName;

    private String fdAccountingCode;

    private String fdType;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdOrder = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCode = null;
        fdAccountingName = null;
        fdAccountingCode = null;
        fdType = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataFund> getModelClass() {
        return EopBasedataFund.class;
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
     * 款项名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 款项名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
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
     * 款项编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 款项编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 财务科目名称
     */
    public String getFdAccountingName() {
        return this.fdAccountingName;
    }

    /**
     * 财务科目名称
     */
    public void setFdAccountingName(String fdAccountingName) {
        this.fdAccountingName = fdAccountingName;
    }

    /**
     * 财务科目编码
     */
    public String getFdAccountingCode() {
        return this.fdAccountingCode;
    }

    /**
     * 财务科目编码
     */
    public void setFdAccountingCode(String fdAccountingCode) {
        this.fdAccountingCode = fdAccountingCode;
    }

    /**
     * 款项类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 款项类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
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
}
