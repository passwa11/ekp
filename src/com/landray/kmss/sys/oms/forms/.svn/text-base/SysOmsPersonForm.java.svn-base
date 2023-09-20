package com.landray.kmss.sys.oms.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员
  */
public class SysOmsPersonForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsAvailable;

    private String fdShortName;

    private String fdNo;

    private String fdKeyword;

    private String fdImportinfo;

    private String fdIsBusiness;

    private String fdMemo;

    private String fdParent;

    private String fdRecordStatus;

    private String fdCreateTime;

    private String fdAlterTime;

    private String fdLdapDn;

    private String fdDynamicMap;

    private String fdCustomMap;

    private String fdCreator;

    private String fdViewRange;

    private String fdOrgEmail;

    private String fdOrder;

    private String fdMobileNo;

    private String fdScard;

    private String fdEmail;

    private String fdLoginName;

    private String fdPassword;

    private String fdAttendanceCardNumber;

    private String fdWorkPhone;

    private String fdPosts;

    private String fdLang;

    private String fdRtx;

    private String fdWechat;

    private String fdSex;

    private String fdShortno;

    private String fdNickName;

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
        fdParent = null;
        fdRecordStatus = null;
        fdCreateTime = null;
        fdAlterTime = null;
        fdLdapDn = null;
        fdDynamicMap = null;
        fdCustomMap = null;
        fdCreator = null;
        fdViewRange = null;
        fdOrgEmail = null;
        fdOrder = null;
        fdMobileNo = null;
        fdScard = null;
        fdEmail = null;
        fdLoginName = null;
        fdPassword = null;
        fdAttendanceCardNumber = null;
        fdWorkPhone = null;
        fdPosts = null;
        fdLang = null;
        fdRtx = null;
        fdWechat = null;
        fdSex = null;
        fdShortno = null;
        fdNickName = null;
        fdHandleStatus = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsPerson> getModelClass() {
        return SysOmsPerson.class;
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
     * 上级部门
     */
    public String getFdParent() {
        return this.fdParent;
    }

    /**
     * 上级部门
     */
    public void setFdParent(String fdParent) {
        this.fdParent = fdParent;
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
     * 查看范围
     */
    public String getFdViewRange() {
        return this.fdViewRange;
    }

    /**
     * 查看范围
     */
    public void setFdViewRange(String fdViewRange) {
        this.fdViewRange = fdViewRange;
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
     * 动态密码卡
     */
    public String getFdScard() {
        return this.fdScard;
    }

    /**
     * 动态密码卡
     */
    public void setFdScard(String fdScard) {
        this.fdScard = fdScard;
    }

    /**
     * 邮箱
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 邮箱
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 登录名
     */
    public String getFdLoginName() {
        return this.fdLoginName;
    }

    /**
     * 登录名
     */
    public void setFdLoginName(String fdLoginName) {
        this.fdLoginName = fdLoginName;
    }

    /**
     * 密码
     */
    public String getFdPassword() {
        return this.fdPassword;
    }

    /**
     * 密码
     */
    public void setFdPassword(String fdPassword) {
        this.fdPassword = fdPassword;
    }

    /**
     * 考勤卡号
     */
    public String getFdAttendanceCardNumber() {
        return this.fdAttendanceCardNumber;
    }

    /**
     * 考勤卡号
     */
    public void setFdAttendanceCardNumber(String fdAttendanceCardNumber) {
        this.fdAttendanceCardNumber = fdAttendanceCardNumber;
    }

    /**
     * 办公电话
     */
    public String getFdWorkPhone() {
        return this.fdWorkPhone;
    }

    /**
     * 办公电话
     */
    public void setFdWorkPhone(String fdWorkPhone) {
        this.fdWorkPhone = fdWorkPhone;
    }

    /**
     * 所属岗位
     */
    public String getFdPosts() {
        return this.fdPosts;
    }

    /**
     * 所属岗位
     */
    public void setFdPosts(String fdPosts) {
        this.fdPosts = fdPosts;
    }

    /**
     * 默认语言
     */
    public String getFdLang() {
        return this.fdLang;
    }

    /**
     * 默认语言
     */
    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
    }

    /**
     * RTX账号
     */
    public String getFdRtx() {
        return this.fdRtx;
    }

    /**
     * RTX账号
     */
    public void setFdRtx(String fdRtx) {
        this.fdRtx = fdRtx;
    }

    /**
     * 微信号
     */
    public String getFdWechat() {
        return this.fdWechat;
    }

    /**
     * 微信号
     */
    public void setFdWechat(String fdWechat) {
        this.fdWechat = fdWechat;
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
     * 短号
     */
    public String getFdShortno() {
        return this.fdShortno;
    }

    /**
     * 短号
     */
    public void setFdShortno(String fdShortno) {
        this.fdShortno = fdShortno;
    }

    /**
     * 昵称
     */
    public String getFdNickName() {
        return this.fdNickName;
    }

    /**
     * 昵称
     */
    public void setFdNickName(String fdNickName) {
        this.fdNickName = fdNickName;
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
