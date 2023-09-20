package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;

/**
  * 组织架构岗位临时表
  */
public class SysOmsTempPostForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdOrder;

    private String fdCreateTime;

    private String fdParentid;

    private String fdStatus;

    private String fdPostId;

    private String fdAlterTime;

    private String fdIsAvailable;

    private String fdTrxId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdOrder = null;
        fdCreateTime = null;
        fdParentid = null;
        fdStatus = null;
        fdPostId = null;
        fdAlterTime = null;
        fdIsAvailable = null;
        fdTrxId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempPost> getModelClass() {
        return SysOmsTempPost.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCreateTime", new FormConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdAlterTime", new FormConvertor_Common("fdAlterTime").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
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
     * 创建时间
     */
    public String getFdCreateTime() {
        return this.fdCreateTime;
    }

    /**
     * 创建时间
     */
    public void setFdCreateTime(String fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    /**
     * 岗位所属部门
     */
    public String getFdParentid() {
        return this.fdParentid;
    }

    /**
     * 岗位所属部门
     */
    public void setFdParentid(String fdParentid) {
        this.fdParentid = fdParentid;
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
     * 源数据id
     */
    public String getFdPostId() {
        return this.fdPostId;
    }

    /**
     * 源数据id
     */
    public void setFdPostId(String fdPostId) {
        this.fdPostId = fdPostId;
    }

    /**
     * 源数据修改时间
     */
    public String getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 源数据修改时间
     */
    public void setFdAlterTime(String fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
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
}
