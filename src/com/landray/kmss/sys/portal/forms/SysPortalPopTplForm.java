package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalPopTpl;
import com.landray.kmss.sys.portal.model.SysPortalPopTplCategory;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalPopTplForm extends ExtendAuthForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docSubject;

    private String fdIsAvailable;

    private String docContent;

    private String docCreatorId;

    private String docCreatorName;

    private String fdCategoryId;

    private String fdCategoryName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        docSubject = null;
        fdIsAvailable = null;
        docContent = null;
        docCreatorId = null;
        docCreatorName = null;
        fdCategoryId = null;
        fdCategoryName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysPortalPopTpl> getModelClass() {
        return SysPortalPopTpl.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel("fdCategory", SysPortalPopTplCategory.class));
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
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
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
     * 文档内容
     */
    public String getDocContent() {
        return this.docContent;
    }

    /**
     * 文档内容
     */
    public void setDocContent(String docContent) {
        this.docContent = docContent;
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
     * 分类
     */
    public String getFdCategoryId() {
        return this.fdCategoryId;
    }

    /**
     * 分类
     */
    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    /**
     * 分类
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 分类
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
