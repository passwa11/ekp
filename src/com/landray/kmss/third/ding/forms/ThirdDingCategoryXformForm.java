package com.landray.kmss.third.ding.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingCategoryXform;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 分组关系表
  */
public class ThirdDingCategoryXformForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdDirid;

    private String fdChildren;

    private String fdCorpid;

    private String docCreateTime;

    private String docAlterTime;

    private String fdTemplateId;

    private String fdRoomId;

    private String fdIsAvailable;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdDirid = null;
        fdChildren = null;
        fdCorpid = null;
        docCreateTime = null;
        docAlterTime = null;
        fdTemplateId = null;
        fdRoomId = null;
        fdIsAvailable = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCategoryXform> getModelClass() {
        return ThirdDingCategoryXform.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
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
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }
}
