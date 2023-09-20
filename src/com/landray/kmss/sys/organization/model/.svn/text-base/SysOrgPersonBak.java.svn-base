package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;

/**
 * 个人
 * 
 * @author 叶中奇
 */
public class SysOrgPersonBak extends SysOrgElementBak implements IBaseModel {
	public SysOrgPersonBak() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_PERSON));
	}

	public boolean isAnonymous() {
		if (fdLoginName == null) {
            return false;
        }
		return "anonymous".equals(fdLoginName);
	}

	/**
	 * 性别
	 */
	private String fdSex;

	public String getFdSex() {
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	/*
	 * 手机号码
	 */
	private String fdMobileNo;

	public String getFdMobileNo() {
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
	}

	/*
	 * 邮件地址
	 */
	private String fdEmail;

	public String getFdEmail() {
		return fdEmail;
	}

	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail;
	}

	/*
	 * 登录名
	 */
	private String fdLoginName;

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	/*
	 * 密码
	 */
	private String fdPassword;

	public String getFdPassword() {
		return fdPassword;
	}

	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}

	/*
	 * 密码，仅页面展现用，不保存到数据库
	 */
	private String fdNewPassword;

	public String getFdNewPassword() {
		return fdNewPassword;
	}

	public void setFdNewPassword(String fdNewPassword) {
		this.fdNewPassword = fdNewPassword;
	}

	/*
	 * 初使密码用于OMS
	 */
	private String fdInitPassword;

	public String getFdInitPassword() {
		return fdInitPassword;
	}

	public void setFdInitPassword(String fdInitPassword) {
		this.fdInitPassword = fdInitPassword;
	}

	/*
	 * RTX帐号
	 */
	private String fdRtxNo;

	public String getFdRtxNo() {
		return fdRtxNo;
	}

	public void setFdRtxNo(String fdRtxNo) {
		this.fdRtxNo = fdRtxNo;
	}

	/*
	 * 微信号
	 */
	private String fdWechatNo;

	public String getFdWechatNo() {
		return fdWechatNo;
	}

	public void setFdWechatNo(String fdWechatNo) {
		this.fdWechatNo = fdWechatNo;
	}

	/*
	 * 动态密码卡号
	 */
	private String fdCardNo;

	public String getFdCardNo() {
		return fdCardNo;
	}

	public void setFdCardNo(String fdCardNo) {
		this.fdCardNo = fdCardNo;
	}

	/*
	 * 考勤卡号
	 */
	protected String fdAttendanceCardNumber;

	public String getFdAttendanceCardNumber() {
		return fdAttendanceCardNumber;
	}

	public void setFdAttendanceCardNumber(String fdAttendanceCardNumber) {
		this.fdAttendanceCardNumber = fdAttendanceCardNumber;
	}

	/*
	 * 办公电话
	 */
	protected String fdWorkPhone;

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	/*
	 * 默认语言
	 */
	protected String fdDefaultLang;

	public String getFdDefaultLang() {
		return fdDefaultLang;
	}

	public void setFdDefaultLang(String fdDefaultLang) {
		this.fdDefaultLang = fdDefaultLang;
	}

	private List<SysOrgPersonAddressType> addressTypeList = new ArrayList<SysOrgPersonAddressType>();

	public List<SysOrgPersonAddressType> getAddressTypeList() {
		return addressTypeList;
	}

	public void setAddressTypeList(List<SysOrgPersonAddressType> addressTypeList) {
		this.addressTypeList = addressTypeList;
	}

	@Override
	public Class getFormClass() {
		return SysOrgPersonForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPosts",
					new ModelConvertor_ModelListToString(
							"fdPostIds:fdPostNames", "fdId:fdName"));
			// 黄郴去掉注释，由于在修改我的地址本时显示出错
			toFormPropertyMap.put("addressTypeList",
					new ModelConvertor_ModelListToFormList("addressTypeList"));
		}
		return toFormPropertyMap;
	}

	protected Date fdLastChangePwd;

	public Date getFdLastChangePwd() {
		return fdLastChangePwd;
	}

	public void setFdLastChangePwd(Date fdLastChangePwd) {
		this.fdLastChangePwd = fdLastChangePwd;
	}

	public Date getFdLockTime() {
		return fdLockTime;
	}

	public void setFdLockTime(Date fdLockTime) {
		this.fdLockTime = fdLockTime;
	}

	public String getFdStaffingLevelId() {
		return fdStaffingLevelId;
	}

	public void setFdStaffingLevelId(String fdStaffingLevelId) {
		this.fdStaffingLevelId = fdStaffingLevelId;
	}

	protected Date fdLockTime;

	private String fdStaffingLevelId;
	
	/*
	 * 短号
	 */
	protected String fdShortNo;

	public String getFdShortNo() {
		return fdShortNo;
	}

	public void setFdShortNo(String fdShortNo) {
		this.fdShortNo = fdShortNo;
	}

	/*
	 * 动态密码（双因子验证）：启用，禁用，网段策略
	 */
	protected String fdDoubleValidation;

	public String getFdDoubleValidation() {
		return fdDoubleValidation;
	}

	public void setFdDoubleValidation(String fdDoubleValidation) {
		if (fdDoubleValidation == null) {
            fdDoubleValidation = "disable";
        }
		this.fdDoubleValidation = fdDoubleValidation;
	}

	/*
	 * 用户类型：是否新用户(判断标准：当此数据为1(或空)时，为老用户；当值为0时，为新用户)
	 * 用户注册（或导入、同步）时，此值为0，当用户主动修改密码时，此值更新为1.（管理员重置密码不会改变此值）
	 */
	protected String fdUserType = "0";

	public String getFdUserType() {
		return fdUserType;
	}

	public void setFdUserType(String fdUserType) {
		this.fdUserType = fdUserType;
	}
}
