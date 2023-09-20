package com.landray.kmss.third.ding.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingDtemplateXformForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉流程模板
  */
public class ThirdDingDtemplateXform extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Boolean fdIsAvailable;

    private String fdProcessCode;

    private String fdAgentId;

    private String fdType;

    private String fdFlow;

    private String fdDisableFormEdit;

    private String fdDesc;

    private String fdCorpId;

    private String fdLang;

    private Date docCreateTime;

    private String fdTemplateId;

    private String fdDirid;

    private String fdOriginDirid;

    private String fdIcon;

    private String fdPcUrl;

    private String fdMobileUrl;

    private String fdProcessConfig;

	private String fdAllReaders;

	private String fdErrMsg;

    private List<ThirdDingTemplateXDetail> fdDetail;

    @Override
    public Class<ThirdDingDtemplateXformForm> getFormClass() {
        return ThirdDingDtemplateXformForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

	public String getFdErrMsg() {
		return fdErrMsg;
	}

	public void setFdErrMsg(String fdErrMsg) {
		this.fdErrMsg = fdErrMsg;
	}

	public String getFdAllReaders() {
		return fdAllReaders;
	}

	public void setFdAllReaders(String fdAllReaders) {
		this.fdAllReaders = fdAllReaders;
	}

	@Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public List<ThirdDingTemplateXDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 流程模板明细
     */
    public void setFdDetail(List<ThirdDingTemplateXDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }
}
