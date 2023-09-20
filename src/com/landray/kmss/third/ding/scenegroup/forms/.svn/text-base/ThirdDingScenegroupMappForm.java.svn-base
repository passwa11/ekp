package com.landray.kmss.third.ding.scenegroup.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 场景群管理
  */
public class ThirdDingScenegroupMappForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdSceneGroupId;

    private String fdChatId;

    private String docAlterTime;

    private String fdStatus;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private String docCreatorId;

    private String docCreatorName;

	private String fdModuleId;

	private String fdModuleName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        docCreateTime = null;
        fdSceneGroupId = null;
        fdChatId = null;
        docAlterTime = null;
        fdStatus = null;
        fdModelName = null;
        fdModelId = null;
        fdKey = null;
        docCreatorId = null;
        docCreatorName = null;
		fdModuleId = null;
		fdModuleName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingScenegroupMapp> getModelClass() {
        return ThirdDingScenegroupMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdModuleId", new FormConvertor_IDToModel(
					"fdModule", ThirdDingScenegroupModule.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 群名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 群名称
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
     * 群ID
     */
    public String getFdSceneGroupId() {
        return this.fdSceneGroupId;
    }

    /**
     * 群ID
     */
    public void setFdSceneGroupId(String fdSceneGroupId) {
        this.fdSceneGroupId = fdSceneGroupId;
    }

    /**
     * 群会话ID
     */
    public String getFdChatId() {
        return this.fdChatId;
    }

    /**
     * 群会话ID
     */
    public void setFdChatId(String fdChatId) {
        this.fdChatId = fdChatId;
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
     * 群状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 群状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * model名称
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * model名称
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * model id
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * model id
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
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

    /**
     * 所属模板
     */
	public String getFdModuleId() {
		return this.fdModuleId;
    }

    /**
     * 所属模板
     */
	public void setFdModuleId(String fdModuleId) {
		this.fdModuleId = fdModuleId;
    }

    /**
     * 所属模板
     */
	public String getFdModuleName() {
		return this.fdModuleName;
    }

    /**
     * 所属模板
     */
	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
