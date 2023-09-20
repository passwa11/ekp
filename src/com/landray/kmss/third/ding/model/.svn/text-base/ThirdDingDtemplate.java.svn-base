package com.landray.kmss.third.ding.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingDtemplateForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉待办模板
  */
public class ThirdDingDtemplate extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdProcessCode;

    private Long fdAgentId;

    private Boolean fdType;

    private Boolean fdFlow;

    private Boolean fdDisableFormEdit;

    private String fdDesc;

    private Date docCreateTime;

    private List<ThirdDingTemplateDetail> fdDetail;

    @Override
    public Class<ThirdDingDtemplateForm> getFormClass() {
        return ThirdDingDtemplateForm.class;
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
    public Long getFdAgentId() {
        return this.fdAgentId;
    }

    /**
     * 应用Id
     */
    public void setFdAgentId(Long fdAgentId) {
        this.fdAgentId = fdAgentId;
    }

    /**
     * 通用模板
     */
    public Boolean getFdType() {
        return this.fdType;
    }

    /**
     * 通用模板
     */
    public void setFdType(Boolean fdType) {
        this.fdType = fdType;
    }

    /**
     * 非流程模板
     */
    public Boolean getFdFlow() {
        return this.fdFlow;
    }

    /**
     * 非流程模板
     */
    public void setFdFlow(Boolean fdFlow) {
        this.fdFlow = fdFlow;
    }

    /**
     * 不可编辑表单
     */
    public Boolean getFdDisableFormEdit() {
        return this.fdDisableFormEdit;
    }

    /**
     * 不可编辑表单
     */
    public void setFdDisableFormEdit(Boolean fdDisableFormEdit) {
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
     * 表单字段
     */
    public List<ThirdDingTemplateDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 表单字段
     */
    public void setFdDetail(List<ThirdDingTemplateDetail> fdDetail) {
        this.fdDetail = fdDetail;
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
	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
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
