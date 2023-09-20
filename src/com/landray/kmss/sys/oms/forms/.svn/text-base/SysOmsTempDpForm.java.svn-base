package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 组织架构部门人员关系临时表
  */
public class SysOmsTempDpForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdOrder;

    private String fdTrxId;

    private String fdDeptId;

    private String fdPersonId;

    private String fdAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdOrder = null;
        fdTrxId = null;
        fdDeptId = null;
        fdPersonId = null;
        fdAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempDp> getModelClass() {
        return SysOmsTempDp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
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
     * 事务号
     */
    public String getFdTrxId() {
        return this.fdTrxId;
    }

    /**
     * 事务号
     */
    public void setFdTrxId(String fdTrxId) {
        this.fdTrxId = fdTrxId;
    }

    /**
     * 部门ID
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门ID
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 人员ID
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 人员ID
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 最后更新时间
     */
    public String getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 最后更新时间
     */
    public void setFdAlterTime(String fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }
}
