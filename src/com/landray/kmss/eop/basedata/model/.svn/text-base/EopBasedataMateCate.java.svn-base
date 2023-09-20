package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataMateCateForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

/**
  * 物料类别
  */
public class EopBasedataMateCate extends ExtendAuthTmpModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdOrder;

    private Integer fdStatus;

    private String fdDesc;

    private String fdCode;

    private EopBasedataMateCate fdParent;

    @Override
    public Class<EopBasedataMateCateForm> getFormClass() {
        return EopBasedataMateCateForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 类别名称
     */
    @Override
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
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
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
     * 父类别名称
     */
    public EopBasedataMateCate getFdParent() {
        return fdParent;
    }

    /**
     * 父类别名称
     */
    public void setFdParent(EopBasedataMateCate parent) {
    	EopBasedataMateCate oldParent = getHbmParent();
        if (!ObjectUtil.equals(oldParent, parent)) {
            ModelUtil.checkTreeCycle(this, parent, "fdParent");
            setHbmParent(parent);
        }
    }

    /**
     * 父类别名称
     */
    public EopBasedataMateCate getHbmParent() {
        return fdParent;
    }

    /**
     * 父类别名称
     */
    public void setHbmParent(EopBasedataMateCate parent) {
        this.fdParent = parent;
    }
}
