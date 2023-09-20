package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉自定义待办模版
  */
public class ThirdDingTodoTemplateForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIscustom;

    private String fdDetail;

    private String fdIsdefault;

    private String fdTemplateId;

    private String docCreateTime;

    private String docAlterTime;

    private String fdModelName;

    private String fdTemplateName;

    private String fdTemplateClass;

    private String fdModelNameText;

    private String docCreatorId;

    private String docCreatorName;

	private String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdIscustom = null;
        fdDetail = null;
        fdIsdefault = null;
		fdType = null;
        fdTemplateId = null;
        docCreateTime = null;
        docAlterTime = null;
        fdModelName = null;
        fdTemplateName = null;
        fdTemplateClass = null;
        fdModelNameText = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingTodoTemplate> getModelClass() {
        return ThirdDingTodoTemplate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");

			// 创建者
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
        }
        return toModelPropertyMap;
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
}
