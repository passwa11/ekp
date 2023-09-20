package com.landray.kmss.third.ding.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingCategoryLog;

/**
  * 审批分类同步
  */
public class ThirdDingCategoryLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSynType;

    private String fdSynTime;

    private String fdSynStatus;

    private String fdContent;

    private String fdCorpId;

    private String fdInput;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSynType = null;
        fdSynTime = null;
        fdSynStatus = null;
        fdContent = null;
        fdCorpId = null;
        fdInput = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCategoryLog> getModelClass() {
        return ThirdDingCategoryLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdSynTime", new FormConvertor_Common("fdSynTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
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
    public String getFdSynTime() {
        return this.fdSynTime;
    }

    /**
     * 同步时间
     */
    public void setFdSynTime(String fdSynTime) {
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
