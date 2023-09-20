package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalPopMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class SysPortalPopMain extends ExtendAuthModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Boolean fdIsAvailable;

    private String docContent;

    private String fdLink;

    private Date fdStartTime;

    private Date fdEndTime;

    private Integer fdDuration;
    
    private String fdCustomCategory;

    private SysPortalPopCategory fdCategory;

    private List<SysOrgElement> fdNotifiers = new ArrayList<SysOrgElement>();
    
    // 弹窗模式：
    // 0：仅一次；1：每天一次；2：每周一次；3：每月一次
    private String fdMode;

    @Override
    public Class<SysPortalPopMainForm> getFormClass() {
        return SysPortalPopMainForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common("fdStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdEndTime", new ModelConvertor_Common("fdEndTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdCategory.fdName", "fdCategoryName");
            toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("fdNotifiers", new ModelConvertor_ModelListToString("fdNotifierIds:fdNotifierNames", "fdId:fdName"));
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
     * 详情连接
     */
    public String getFdLink() {
        return this.fdLink;
    }

    /**
     * 详情连接
     */
    public void setFdLink(String fdLink) {
        this.fdLink = fdLink;
    }

    /**
     * 开始时间
     */
    public Date getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 开始时间
     */
    public void setFdStartTime(Date fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 结束时间
     */
    public Date getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 结束时间
     */
    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 持续时间
     */
    public Integer getFdDuration() {
        return this.fdDuration;
    }

    /**
     * 持续时间
     */
    public void setFdDuration(Integer fdDuration) {
        this.fdDuration = fdDuration;
    }

    /**
     * 分类
     */
    public SysPortalPopCategory getFdCategory() {
        return this.fdCategory;
    }

    /**
     * 分类
     */
    public void setFdCategory(SysPortalPopCategory fdCategory) {
        this.fdCategory = fdCategory;
    }
    
    /**
     * 自定义分类
     */
    public String getFdCustomCategory() {
        return this.fdCustomCategory;
    }

    /**
     * 自定义分类
     */
    public void setFdCustomCategory(String fdCustomCategory) {
        this.fdCustomCategory = fdCustomCategory;
    }

    /**
     * 通知人
     */
    public List<SysOrgElement> getFdNotifiers() {
        return this.fdNotifiers;
    }

    /**
     * 通知人
     */
    public void setFdNotifiers(List<SysOrgElement> fdNotifiers) {
        this.fdNotifiers = fdNotifiers;
    }
    
    /**
     * 弹窗模式
     */
    public String getFdMode() {
    	return this.fdMode;
    }
    
    /**
     * 弹窗模式
     */
    public void setFdMode(String fdMode) {
    	this.fdMode = fdMode;
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
