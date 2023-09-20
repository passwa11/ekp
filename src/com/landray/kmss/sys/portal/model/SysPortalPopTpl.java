package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.portal.forms.SysPortalPopTplForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class SysPortalPopTpl extends ExtendAuthModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Boolean fdIsAvailable;

    private String docContent;

    private SysPortalPopTplCategory fdCategory;

    @Override
    public Class<SysPortalPopTplForm> getFormClass() {
        return SysPortalPopTplForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdCategory.fdName", "fdCategoryName");
            toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
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
     * 标题
     */
    @Override
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
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 文档内容
     */
    public String getDocContent() {
        return (String) readLazyField("docContent", this.docContent);
    }

    /**
     * 文档内容
     */
    public void setDocContent(String docContent) {
        this.docContent = (String) writeLazyField("docContent", this.docContent, docContent);
    }

    /**
     * 分类
     */
    public SysPortalPopTplCategory getFdCategory() {
        return this.fdCategory;
    }

    /**
     * 分类
     */
    public void setFdCategory(SysPortalPopTplCategory fdCategory) {
        this.fdCategory = fdCategory;
    }

    @Override
    public String getDocStatus() {
        return "30";
    }

    AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
