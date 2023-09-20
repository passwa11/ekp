package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年8月5日
*@Description               渠道用户信息
*/

public class ElecChannelUserInfo implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//一般是无规则性的编号（如主键）
	private String userId;
	
	//用户编号 (自定的业务编号)
	private String userNo;
	
	//登录名
	private String userName;
	
	//真实姓名
	private String realName;
	
	//证件类型
	private ElecIdentityTypeEnum identType;
	
	//证件号码
	private String identNo; 
	
	//用户类型(1：个人；2：企业)
	private String userType;
	
	//邮箱
	private String email;
	
	//手机号
	private String phone;
	
	//用户状态
	private String userState;
	
	private String remark;
	
	//部门信息
	private ElecChannelOrgInfo deptInfo;
	
	public ElecChannelUserInfo(){
	}
	
	public ElecChannelUserInfo(String userId) {
		super();
		this.userId = userId;
	}

	public ElecChannelUserInfo(String userId, String userName, String realName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
	}

	public String getUserId() {
		return userId;
	}

	public ElecChannelUserInfo setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public ElecChannelUserInfo setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getRealName() {
		return realName;
	}

	public ElecChannelUserInfo setRealName(String realName) {
		this.realName = realName;
		return this;
	}

	public ElecIdentityTypeEnum getIdentType() {
		return identType;
	}

	public void setIdentType(ElecIdentityTypeEnum identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public ElecChannelUserInfo setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ElecChannelOrgInfo getDeptInfo() {
		return deptInfo;
	}

	public void setDeptInfo(ElecChannelOrgInfo deptInfo) {
		this.deptInfo = deptInfo;
	}

	public String getUserNo() {
		return userNo;
	}

	public ElecChannelUserInfo setUserNo(String userNo) {
		this.userNo = userNo;
		return this;
	}
}
