package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import java.util.List;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingDinstanceForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;

/**
  * 钉钉待办实例
  */
public class ThirdDingDinstance extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdInstanceId;

    private String fdDingUserId;

    private String fdEkpInstanceId;

    private String fdUrl;

    private Date docCreateTime;

    private ThirdDingDtemplate fdTemplate;

    private SysOrgElement fdCreator;

    private List<ThirdDingInstanceDetail> fdDetail;

    @Override
    public Class<ThirdDingDinstanceForm> getFormClass() {
        return ThirdDingDinstanceForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
            toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
            toFormPropertyMap.put("fdCreator.fdName", "fdCreatorName");
            toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
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
     * 实例Id
     */
    public String getFdInstanceId() {
        return this.fdInstanceId;
    }

    /**
     * 实例Id
     */
    public void setFdInstanceId(String fdInstanceId) {
        this.fdInstanceId = fdInstanceId;
    }

    /**
     * 钉钉接收人
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉接收人
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

    /**
     * 文档Id
     */
    public String getFdEkpInstanceId() {
        return this.fdEkpInstanceId;
    }

    /**
     * 文档Id
     */
    public void setFdEkpInstanceId(String fdEkpInstanceId) {
        this.fdEkpInstanceId = fdEkpInstanceId;
    }

    /**
     * 文档地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 文档地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
     * 所属模板
     */
    public ThirdDingDtemplate getFdTemplate() {
        return this.fdTemplate;
    }

    /**
     * 所属模板
     */
    public void setFdTemplate(ThirdDingDtemplate fdTemplate) {
        this.fdTemplate = fdTemplate;
    }

    /**
     * 发起人
     */
    public SysOrgElement getFdCreator() {
        return this.fdCreator;
    }

    /**
     * 发起人
     */
    public void setFdCreator(SysOrgElement fdCreator) {
        this.fdCreator = fdCreator;
    }

    /**
     * 钉钉实例明细表
     */
    public List<ThirdDingInstanceDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 钉钉实例明细表
     */
    public void setFdDetail(List<ThirdDingInstanceDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }
}
