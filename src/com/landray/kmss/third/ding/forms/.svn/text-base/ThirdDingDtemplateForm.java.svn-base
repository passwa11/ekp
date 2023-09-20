package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉待办模板
  */
public class ThirdDingDtemplateForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdProcessCode;

    private String fdAgentId;

    private String fdType;

    private String fdFlow;

    private String fdDisableFormEdit;

    private String fdDesc;

    private String docCreateTime;

    private AutoArrayList fdDetail_Form = new AutoArrayList(ThirdDingTemplateDetailForm.class);

    private String fdDetail_Flag = "0";

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdProcessCode = null;
        fdAgentId = null;
        fdType = null;
        fdFlow = null;
        fdDisableFormEdit = null;
        fdDesc = null;
        docCreateTime = null;
        fdDetail_Form = new AutoArrayList(ThirdDingTemplateDetailForm.class);
        fdDetail_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingDtemplate> getModelClass() {
        return ThirdDingDtemplate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
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
     * code
     */
    public String getFdProcessCode() {
        return this.fdProcessCode;
    }

    /**
     * code
     */
    public void setFdProcessCode(String fdProcessCode) {
        this.fdProcessCode = fdProcessCode;
    }

    /**
     * 应用Id
     */
    public String getFdAgentId() {
        return this.fdAgentId;
    }

    /**
     * 应用Id
     */
    public void setFdAgentId(String fdAgentId) {
        this.fdAgentId = fdAgentId;
    }

    /**
     * 通用模板
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 通用模板
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 非流程模板
     */
    public String getFdFlow() {
        return this.fdFlow;
    }

    /**
     * 非流程模板
     */
    public void setFdFlow(String fdFlow) {
        this.fdFlow = fdFlow;
    }

    /**
     * 不可编辑表单
     */
    public String getFdDisableFormEdit() {
        return this.fdDisableFormEdit;
    }

    /**
     * 不可编辑表单
     */
    public void setFdDisableFormEdit(String fdDisableFormEdit) {
        this.fdDisableFormEdit = fdDisableFormEdit;
    }

    /**
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
     * 表单字段
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 表单字段
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 表单字段
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 表单字段
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }
    
    private String fdCorpId = null;

	public String getFdCorpId() {
		return fdCorpId;
	}

	public void setFdCorpId(String fdCorpId) {
		this.fdCorpId = fdCorpId;
	}
	
	/*
	 * 是否有效
	 */
	private String fdIsAvailable = "true";

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	public String getFdLang() {
		return fdLang;
	}

	public void setFdLang(String fdLang) {
		this.fdLang = fdLang;
	}

	private String fdLang;
}
