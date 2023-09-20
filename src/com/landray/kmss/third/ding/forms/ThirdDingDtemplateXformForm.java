package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉流程模板
  */
public class ThirdDingDtemplateXformForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsAvailable;

    private String fdProcessCode;

    private String fdAgentId;

    private String fdType;

    private String fdFlow;

    private String fdDisableFormEdit;

    private String fdDesc;

    private String fdCorpId;

    private String fdLang;

    private String docCreateTime;

    private String fdTemplateId;

    private String fdDirid;

    private String fdOriginDirid;

    private String fdIcon;

    private String fdPcUrl;

    private String fdMobileUrl;

    private String fdProcessConfig;

	private String fdErrMsg;

    private AutoArrayList fdDetail_Form = new AutoArrayList(ThirdDingTemplateXDetailForm.class);

    private String fdDetail_Flag = "0";

	private String fdAllReaders;

	public String getFdAllReaders() {
		return fdAllReaders;
	}

	public void setFdAllReaders(String fdAllReaders) {
		this.fdAllReaders = fdAllReaders;
	}

	public String getFdErrMsg() {
		return fdErrMsg;
	}

	public void setFdErrMsg(String fdErrMsg) {
		this.fdErrMsg = fdErrMsg;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdIsAvailable = null;
        fdProcessCode = null;
        fdAgentId = null;
        fdType = null;
        fdFlow = null;
        fdDisableFormEdit = null;
        fdDesc = null;
        fdCorpId = null;
        fdLang = null;
        docCreateTime = null;
        fdTemplateId = null;
        fdDirid = null;
        fdOriginDirid = null;
        fdIcon = null;
        fdPcUrl = null;
        fdMobileUrl = null;
        fdProcessConfig = null;
        fdDetail_Form = new AutoArrayList(ThirdDingTemplateXDetailForm.class);
        fdDetail_Flag = null;
		fdAllReaders = null;
		fdErrMsg = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingDtemplateXform> getModelClass() {
        return ThirdDingDtemplateXform.class;
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
     * 模板code
     */
    public String getFdProcessCode() {
        return this.fdProcessCode;
    }

    /**
     * 模板code
     */
    public void setFdProcessCode(String fdProcessCode) {
        this.fdProcessCode = fdProcessCode;
    }

    /**
     * 应用ID
     */
    public String getFdAgentId() {
        return this.fdAgentId;
    }

    /**
     * 应用ID
     */
    public void setFdAgentId(String fdAgentId) {
        this.fdAgentId = fdAgentId;
    }

    /**
     * 类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 类型
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
     * CorpId
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * CorpId
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    /**
     * 语言
     */
    public String getFdLang() {
        return this.fdLang;
    }

    /**
     * 语言
     */
    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
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
     * 流程模板ID
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 流程模板ID
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 分组Id
     */
    public String getFdDirid() {
        return this.fdDirid;
    }

    /**
     * 分组Id
     */
    public void setFdDirid(String fdDirid) {
        this.fdDirid = fdDirid;
    }

    /**
     * 原分组id
     */
    public String getFdOriginDirid() {
        return this.fdOriginDirid;
    }

    /**
     * 原分组id
     */
    public void setFdOriginDirid(String fdOriginDirid) {
        this.fdOriginDirid = fdOriginDirid;
    }

    /**
     * 图标
     */
    public String getFdIcon() {
        return this.fdIcon;
    }

    /**
     * 图标
     */
    public void setFdIcon(String fdIcon) {
        this.fdIcon = fdIcon;
    }

    /**
     * pc地址
     */
    public String getFdPcUrl() {
        return this.fdPcUrl;
    }

    /**
     * pc地址
     */
    public void setFdPcUrl(String fdPcUrl) {
        this.fdPcUrl = fdPcUrl;
    }

    /**
     * 移动地址
     */
    public String getFdMobileUrl() {
        return this.fdMobileUrl;
    }

    /**
     * 移动地址
     */
    public void setFdMobileUrl(String fdMobileUrl) {
        this.fdMobileUrl = fdMobileUrl;
    }

    /**
     * 流程配置
     */
    public String getFdProcessConfig() {
        return this.fdProcessConfig;
    }

    /**
     * 流程配置
     */
    public void setFdProcessConfig(String fdProcessConfig) {
        this.fdProcessConfig = fdProcessConfig;
    }

    /**
     * 流程模板明细
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 流程模板明细
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 流程模板明细
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 流程模板明细
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }
}
