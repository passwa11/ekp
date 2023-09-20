package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 主体
  */
public class EopBasedataContbodyForm extends ExtendAuthForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdOrder;

    private String fdIsAvailable;

    private String docCreateTime;

    private String fdIden;

    private String fdKey;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdOrder = null;
        fdIsAvailable = null;
        docCreateTime = null;
        fdIden = null;
        fdKey = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<com.landray.kmss.eop.basedata.model.EopBasedataContbody> getModelClass() {
        return com.landray.kmss.eop.basedata.model.EopBasedataContbody.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
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
     * 统一信用代码
     */
    public String getFdIden() {
        return this.fdIden;
    }

    /**
     * 统一信用代码
     */
    public void setFdIden(String fdIden) {
        this.fdIden = fdIden;
    }

    /**
     * 关键字
     */
    public String getFdKey() {
        return this.fdKey;
    }

    /**
     * 关键字
     */
    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }
}
