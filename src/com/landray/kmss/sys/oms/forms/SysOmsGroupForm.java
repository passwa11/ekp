package com.landray.kmss.sys.oms.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsGroup;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 群组
  */
public class SysOmsGroupForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsAvailable;

    private String fdShortName;

    private String fdNo;

    private String fdKeyword;

    private String fdImportinfo;

    private String fdIsBusiness;

    private String fdMemo;

    private String fdRecordStatus;

    private String fdCreateTime;

    private String fdAlterTime;

    private String fdLdapDn;

    private String fdDynamicMap;

    private String fdCustomMap;

    private String fdCreator;

    private String fdMembers;

    private String fdOrgEmail;

    private String fdOrder;

    private String fdHandleStatus;


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdIsAvailable = null;
        fdShortName = null;
        fdNo = null;
        fdKeyword = null;
        fdImportinfo = null;
        fdIsBusiness = null;
        fdMemo = null;
        fdRecordStatus = null;
        fdCreateTime = null;
        fdAlterTime = null;
        fdLdapDn = null;
        fdDynamicMap = null;
        fdCustomMap = null;
        fdCreator = null;
        fdMembers = null;
        fdOrgEmail = null;
        fdOrder = null;
        fdHandleStatus = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsGroup> getModelClass() {
        return SysOmsGroup.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCreateTime", new FormConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdAlterTime", new FormConvertor_Common("fdAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
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
     * 简称
     */
    public String getFdShortName() {
        return this.fdShortName;
    }

    /**
     * 简称
     */
    public void setFdShortName(String fdShortName) {
        this.fdShortName = fdShortName;
    }

    /**
     * 编号
     */
    public String getFdNo() {
        return this.fdNo;
    }

    /**
     * 编号
     */
    public void setFdNo(String fdNo) {
        this.fdNo = fdNo;
    }

    /**
     * 关键字
     */
    public String getFdKeyword() {
        return this.fdKeyword;
    }

    /**
     * 关键字
     */
    public void setFdKeyword(String fdKeyword) {
        this.fdKeyword = fdKeyword;
    }

    /**
     * 映射关系
     */
    public String getFdImportinfo() {
        return this.fdImportinfo;
    }

    /**
     * 映射关系
     */
    public void setFdImportinfo(String fdImportinfo) {
        this.fdImportinfo = fdImportinfo;
    }

    /**
     * 是否业务相关
     */
    public String getFdIsBusiness() {
        return this.fdIsBusiness;
    }

    /**
     * 是否业务相关
     */
    public void setFdIsBusiness(String fdIsBusiness) {
        this.fdIsBusiness = fdIsBusiness;
    }

    /**
     * 备注
     */
    public String getFdMemo() {
        return this.fdMemo;
    }

    /**
     * 备注
     */
    public void setFdMemo(String fdMemo) {
        this.fdMemo = fdMemo;
    }

    /**
     * 更新状态
     */
    public String getFdRecordStatus() {
        return this.fdRecordStatus;
    }

    /**
     * 更新状态
     */
    public void setFdRecordStatus(String fdRecordStatus) {
        this.fdRecordStatus = fdRecordStatus;
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
     * 更新时间
     */
    public String getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 更新时间
     */
    public void setFdAlterTime(String fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }

    /**
     * LDAP DN
     */
    public String getFdLdapDn() {
        return this.fdLdapDn;
    }

    /**
     * LDAP DN
     */
    public void setFdLdapDn(String fdLdapDn) {
        this.fdLdapDn = fdLdapDn;
    }

    /**
     * 动态参数
     */
    public String getFdDynamicMap() {
        return this.fdDynamicMap;
    }

    /**
     * 动态参数
     */
    public void setFdDynamicMap(String fdDynamicMap) {
        this.fdDynamicMap = fdDynamicMap;
    }

    /**
     * 自定义参数
     */
    public String getFdCustomMap() {
        return this.fdCustomMap;
    }

    /**
     * 自定义参数
     */
    public void setFdCustomMap(String fdCustomMap) {
        this.fdCustomMap = fdCustomMap;
    }

    /**
     * 创建者
     */
    public String getFdCreator() {
        return this.fdCreator;
    }

    /**
     * 创建者
     */
    public void setFdCreator(String fdCreator) {
        this.fdCreator = fdCreator;
    }

    /**
     * 群组成员
     */
    public String getFdMembers() {
        return this.fdMembers;
    }

    /**
     * 群组成员
     */
    public void setFdMembers(String fdMembers) {
        this.fdMembers = fdMembers;
    }

    /**
     * 组织邮箱
     */
    public String getFdOrgEmail() {
        return this.fdOrgEmail;
    }

    /**
     * 组织邮箱
     */
    public void setFdOrgEmail(String fdOrgEmail) {
        this.fdOrgEmail = fdOrgEmail;
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
     * 处理状态
     */
    public String getFdHandleStatus() {
        return this.fdHandleStatus;
    }

    /**
     * 处理状态
     */
    public void setFdHandleStatus(String fdHandleStatus) {
        this.fdHandleStatus = fdHandleStatus;
    }

}
