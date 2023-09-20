package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.ding.forms.ThirdDingCategoryLogForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 审批分类同步
  */
public class ThirdDingCategoryLog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSynType;

    private Date fdSynTime;

    private String fdSynStatus;

    private String fdContent;

    private String fdCorpId;

    private String fdInput;

    @Override
    public Class<ThirdDingCategoryLogForm> getFormClass() {
        return ThirdDingCategoryLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdSynTime", new ModelConvertor_Common("fdSynTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 同步类型
     */
    public String getFdSynType() {
        return this.fdSynType;
    }

    /**
     * 同步类型
     */
    public void setFdSynType(String fdSynType) {
        this.fdSynType = fdSynType;
    }

    /**
     * 同步时间
     */
    public Date getFdSynTime() {
        return this.fdSynTime;
    }

    /**
     * 同步时间
     */
    public void setFdSynTime(Date fdSynTime) {
        this.fdSynTime = fdSynTime;
    }

    /**
     * 同步结果
     */
    public String getFdSynStatus() {
        return this.fdSynStatus;
    }

    /**
     * 同步结果
     */
    public void setFdSynStatus(String fdSynStatus) {
        this.fdSynStatus = fdSynStatus;
    }

    /**
     * 同步范围
     */
    public String getFdContent() {
        return this.fdContent;
    }

    /**
     * 同步范围
     */
    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
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
     * 参数
     */
    public String getFdInput() {
        return this.fdInput;
    }

    /**
     * 参数
     */
    public void setFdInput(String fdInput) {
        this.fdInput = fdInput;
    }
}
