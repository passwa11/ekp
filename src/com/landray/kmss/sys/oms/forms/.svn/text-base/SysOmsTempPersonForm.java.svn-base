package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;

/**
  * 组织架构人员临时表
  */
public class SysOmsTempPersonForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdParentid;

    private String fdCreateTime;

    private String fdOrder;

    private String fdMobileNo;

    private String fdEmail;

    private String fdSex;

    private String fdStatus;

    private String fdPersonId;

    private String fdAlterTime;

    private String fdIsAvailable;

    private String fdTrxId;

    private String fdExtra;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdParentid = null;
        fdCreateTime = null;
        fdOrder = null;
        fdMobileNo = null;
        fdEmail = null;
        fdSex = null;
        fdStatus = null;
        fdPersonId = null;
        fdAlterTime = null;
        fdIsAvailable = null;
        fdTrxId = null;
        fdExtra = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempPerson> getModelClass() {
        return SysOmsTempPerson.class;
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
     * 所属部门ID
     */
    public String getFdParentid() {
        return this.fdParentid;
    }

    /**
     * 所属部门ID
     */
    public void setFdParentid(String fdParentid) {
        this.fdParentid = fdParentid;
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
     * 手机号
     */
    public String getFdMobileNo() {
        return this.fdMobileNo;
    }

    /**
     * 手机号
     */
    public void setFdMobileNo(String fdMobileNo) {
        this.fdMobileNo = fdMobileNo;
    }

    /**
     * 邮件
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 邮件
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 性别
     */
    public String getFdSex() {
        return this.fdSex;
    }

    /**
     * 性别
     */
    public void setFdSex(String fdSex) {
        this.fdSex = fdSex;
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
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 源数据id
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
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

    /**
     * 扩展类型
     */
    public String getFdExtra() {
        return this.fdExtra;
    }

    /**
     * 扩展类型
     */
    public void setFdExtra(String fdExtra) {
        this.fdExtra = fdExtra;
    }
}
