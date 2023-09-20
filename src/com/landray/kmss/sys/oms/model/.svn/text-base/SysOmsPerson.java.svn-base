package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.oms.forms.SysOmsPersonForm;
import com.landray.kmss.util.DateUtil;

/**
 * 人员
 */
public class SysOmsPerson extends SysOmsElement
        implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdViewRange;

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

    private Boolean fdIsActivated;

    private Boolean fdCanLogin;

    private String fdLoginNameLower;

    @Override
    public Class<SysOmsPersonForm> getFormClass() {
        return SysOmsPersonForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdAlterTime", new ModelConvertor_Common("fdAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        // super.recalculateFields();
    }

    /**
     * 查看范围
     */
    public String getFdViewRange() {
        return (String) readLazyField("fdViewRange", this.fdViewRange);
    }

    /**
     * 查看范围
     */
    public void setFdViewRange(String fdViewRange) {
        this.fdViewRange = (String) writeLazyField("fdViewRange", this.fdViewRange, fdViewRange);
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
        return (String) readLazyField("fdPosts", this.fdPosts);
    }

    /**
     * 所属岗位
     */
    public void setFdPosts(String fdPosts) {
        this.fdPosts = (String) writeLazyField("fdPosts", this.fdPosts, fdPosts);
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

    @Override
    public Integer getOrgType() {
        return 8;
    }

    public Boolean getFdIsActivated() {
        return fdIsActivated;
    }

    public void setFdIsActivated(Boolean fdIsActivated) {
        this.fdIsActivated = fdIsActivated;
    }

    public Boolean getFdCanLogin() {
        return fdCanLogin;
    }

    public void setFdCanLogin(Boolean fdCanLogin) {
        this.fdCanLogin = fdCanLogin;
    }

    public String getFdLoginNameLower() {
        return fdLoginNameLower;
    }

    public void setFdLoginNameLower(String fdLoginNameLower) {
        this.fdLoginNameLower = fdLoginNameLower;
    }
}
