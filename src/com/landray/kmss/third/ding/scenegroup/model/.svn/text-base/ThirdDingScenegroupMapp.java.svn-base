package com.landray.kmss.third.ding.scenegroup.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.scenegroup.forms.ThirdDingScenegroupMappForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 场景群管理
  */
public class ThirdDingScenegroupMapp extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Date docCreateTime;

    private String fdSceneGroupId;

    private String fdChatId;

    private Date docAlterTime;

    private String fdStatus;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private SysOrgPerson docCreator;

	private ThirdDingScenegroupModule fdModule;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdDingScenegroupMappForm> getFormClass() {
        return ThirdDingScenegroupMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdModule.fdName", "fdModuleName");
			toFormPropertyMap.put("fdModule.fdId", "fdModuleId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
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
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 所属模板
     */
	public ThirdDingScenegroupModule getFdModule() {
		return this.fdModule;
    }

    /**
     * 所属模板
     */
	public void setFdModule(ThirdDingScenegroupModule fdModule) {
		this.fdModule = fdModule;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
