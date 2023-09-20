package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.ding.forms.ThirdDingCategoryXformForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 分组关系表
  */
public class ThirdDingCategoryXform extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdDirid;

    private String fdChildren;

    private String fdCorpid;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdTemplateId;

    private String fdRoomId;

    private Boolean fdIsAvailable;

    @Override
    public Class<ThirdDingCategoryXformForm> getFormClass() {
        return ThirdDingCategoryXformForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 分组名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 分组名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 子分组
     */
    public String getFdChildren() {
        return this.fdChildren;
    }

    /**
     * 子分组
     */
    public void setFdChildren(String fdChildren) {
        this.fdChildren = fdChildren;
    }

    /**
     * CorpId
     */
    public String getFdCorpid() {
        return this.fdCorpid;
    }

    /**
     * CorpId
     */
    public void setFdCorpid(String fdCorpid) {
        this.fdCorpid = fdCorpid;
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
     * ekp流程模板Id
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * ekp流程模板Id
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 场所id
     */
    public String getFdRoomId() {
        return this.fdRoomId;
    }

    /**
     * 场所id
     */
    public void setFdRoomId(String fdRoomId) {
        this.fdRoomId = fdRoomId;
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
}
