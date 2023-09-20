package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalPopCategory;
import com.landray.kmss.sys.portal.model.SysPortalPopMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalPopMainForm extends ExtendAuthForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String docSubject;

    private String fdIsAvailable;

    private String docContent;

    private String fdLink;

    private String fdStartTime;

    private String fdEndTime;

    private String fdDuration;

    private String fdCustomCategory;

    private String docCreatorId;

    private String docCreatorName;

    private String fdCategoryId;

    private String fdCategoryName;

    private String fdNotifierIds;

    private String fdNotifierNames;
    
    private String fdMode;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        docSubject = null;
        fdIsAvailable = null;
        docContent = null;
        fdLink = null;
        fdStartTime = null;
        fdEndTime = null;
        fdDuration = null;
        fdCustomCategory = null;
        docCreatorId = null;
        docCreatorName = null;
        fdCategoryId = null;
        fdCategoryName = null;
        fdNotifierIds = null;
        fdNotifierNames = null;
        fdMode = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysPortalPopMain> getModelClass() {
        return SysPortalPopMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdStartTime", new FormConvertor_Common("fdStartTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdEndTime", new FormConvertor_Common("fdEndTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel("fdCategory", SysPortalPopCategory.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("fdNotifierIds", new FormConvertor_IDsToModelList("fdNotifiers", SysOrgElement.class));
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
    public String getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 开始时间
     */
    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 结束时间
     */
    public String getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 结束时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 持续时间
     */
    public String getFdDuration() {
        return this.fdDuration;
    }

    /**
     * 持续时间
     */
    public void setFdDuration(String fdDuration) {
        this.fdDuration = fdDuration;
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

    /**
     * 通知人
     */
    public String getFdNotifierIds() {
        return this.fdNotifierIds;
    }

    /**
     * 通知人
     */
    public void setFdNotifierIds(String fdNotifierIds) {
        this.fdNotifierIds = fdNotifierIds;
    }

    /**
     * 通知人
     */
    public String getFdNotifierNames() {
        return this.fdNotifierNames;
    }

    /**
     * 通知人
     */
    public void setFdNotifierNames(String fdNotifierNames) {
        this.fdNotifierNames = fdNotifierNames;
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
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}