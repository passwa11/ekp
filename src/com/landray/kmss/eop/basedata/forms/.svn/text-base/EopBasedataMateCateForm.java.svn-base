package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 物料类别
  */
public class EopBasedataMateCateForm extends ExtendAuthTmpForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdOrder;

    private String fdStatus;

    private String fdDesc;

    private String docCreateTime;

    private String fdCode;

    private String docCreatorId;

    private String docCreatorName;

    private String fdParentId;

    private String fdParentName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdOrder = null;
        fdStatus = null;
        fdDesc = null;
        docCreateTime = null;
        fdCode = null;
        docCreatorId = null;
        docCreatorName = null;
        fdParentId = null;
        fdParentName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataMateCate> getModelClass() {
        return EopBasedataMateCate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataMateCate.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 类别名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 类别名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 类别描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 类别描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 最近更新时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 最近更新时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 类别编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 类别编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 父类别名称
     */
    public String getFdParentId() {
        return this.fdParentId;
    }

    /**
     * 父类别名称
     */
    public void setFdParentId(String fdParentId) {
        this.fdParentId = fdParentId;
    }

    /**
     * 父类别名称
     */
    public String getFdParentName() {
        return this.fdParentName;
    }

    /**
     * 父类别名称
     */
    public void setFdParentName(String fdParentName) {
        this.fdParentName = fdParentName;
    }
}
