package com.landray.kmss.third.ding.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.forms.ThirdDingTodoTemplateForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉自定义待办模版
  */
public class ThirdDingTodoTemplate extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdIscustom;

    private String fdDetail;

    private String fdIsdefault;

    private String fdTemplateId;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdModelName;

    private String fdTemplateName;

    private String fdTemplateClass;

    private String fdModelNameText;

    private SysOrgPerson docCreator;

	private String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}


    public ThirdDingTodoTemplate() {
        super();
    }

    public ThirdDingTodoTemplate(String fdName, String fdIscustom,
                                 String fdIsdefault, String fdTemplateId, String fdModelName,
                                 String fdTemplateName, String fdTemplateClass,
                                 String fdModelNameText, String fdType) {
        super();
        this.fdName = fdName;
        this.fdIscustom = fdIscustom;
        this.fdIsdefault = fdIsdefault;
        this.fdTemplateId = fdTemplateId;
        this.fdModelName = fdModelName;
        this.fdTemplateName = fdTemplateName;
        this.fdTemplateClass = fdTemplateClass;
        this.fdModelNameText = fdModelNameText;
        this.fdType = fdType;
    }

	@Override
    public Class<ThirdDingTodoTemplateForm> getFormClass() {
        return ThirdDingTodoTemplateForm.class;
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
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 模版名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 模版名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 是否自定义
     */
    public String getFdIscustom() {
        return this.fdIscustom;
    }

    /**
     * 是否自定义
     */
    public void setFdIscustom(String fdIscustom) {
        this.fdIscustom = fdIscustom;
    }

    /**
     * 推送字段详细
     */
    public String getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 推送字段详细
     */
    public void setFdDetail(String fdDetail) {
        this.fdDetail = fdDetail;
    }

    /**
     * 是否为默认模版
     */
    public String getFdIsdefault() {
        return this.fdIsdefault;
    }

    /**
     * 是否为默认模版
     */
    public void setFdIsdefault(String fdIsdefault) {
        this.fdIsdefault = fdIsdefault;
    }

    /**
     * 表单模版Id
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 表单模版Id
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
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
     * 模块类名
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块类名
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 表单名称
     */
    public String getFdTemplateName() {
        return this.fdTemplateName;
    }

    /**
     * 表单名称
     */
    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    /**
     * 表单模版类名
     */
    public String getFdTemplateClass() {
        return this.fdTemplateClass;
    }

    /**
     * 表单模版类名
     */
    public void setFdTemplateClass(String fdTemplateClass) {
        this.fdTemplateClass = fdTemplateClass;
    }

    /**
     * 所属模块
     */
    public String getFdModelNameText() {
        return this.fdModelNameText;
    }

    /**
     * 所属模块
     */
    public void setFdModelNameText(String fdModelNameText) {
        this.fdModelNameText = fdModelNameText;
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
}
