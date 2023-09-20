package com.landray.kmss.sys.evaluation.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationShare;

/**
  * 分享
  */
public class SysEvaluationShareForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdShareTime;

    private String docSubject;

    private String fdShareReason;

    private String fdShareMode;

    private String goalPersonids;

    private String fdGroupId;

    private String fdTopic;

    private String fdModelId;

    private String fdModelName;

    private String fdShareType;

    private String docCreatorId;

    private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdShareTime = null;
        docSubject = null;
        fdShareReason = null;
        fdShareMode = null;
        goalPersonids = null;
        fdGroupId = null;
        fdTopic = null;
        fdModelId = null;
        fdModelName = null;
        fdShareType = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysEvaluationShare> getModelClass() {
        return SysEvaluationShare.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdShareTime", new FormConvertor_Common("fdShareTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
    }

    /**
     * 分享时间
     */
    public String getFdShareTime() {
        return this.fdShareTime;
    }

    /**
     * 分享时间
     */
    public void setFdShareTime(String fdShareTime) {
        this.fdShareTime = fdShareTime;
    }

    /**
     * 分享文本
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 分享文本
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 分享理由
     */
    public String getFdShareReason() {
        return this.fdShareReason;
    }

    /**
     * 分享理由
     */
    public void setFdShareReason(String fdShareReason) {
        this.fdShareReason = fdShareReason;
    }

    /**
     * 分享模式
     */
    public String getFdShareMode() {
        return this.fdShareMode;
    }

    /**
     * 分享模式
     */
    public void setFdShareMode(String fdShareMode) {
        this.fdShareMode = fdShareMode;
    }

    /**
     * 推荐对象
     */
    public String getGoalPersonids() {
        return this.goalPersonids;
    }

    /**
     * 推荐对象
     */
    public void setGoalPersonids(String goalPersonids) {
        this.goalPersonids = goalPersonids;
    }

    /**
     * 圈子id
     */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 圈子id
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    /**
     * 话题标题
     */
    public String getFdTopic() {
        return this.fdTopic;
    }

    /**
     * 话题标题
     */
    public void setFdTopic(String fdTopic) {
        this.fdTopic = fdTopic;
    }

    /**
     * 文档ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 文档ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 模块名称
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块名称
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 分享类型
     */
    public String getFdShareType() {
        return this.fdShareType;
    }

    /**
     * 分享类型
     */
    public void setFdShareType(String fdShareType) {
        this.fdShareType = fdShareType;
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
