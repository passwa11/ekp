package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月10日
*@Description            交易类型
*/

public enum ElecChannelTxCodeEnum {
	
	//个人开户
	OPEN_PERSONAL_ACCOUNT("OPEN_PERSONAL_ACCOUNT"),
	
	//企业开户
	OPEN_ENTERPRISE_ACCOUNT("OPEN_ENTERPRISE_ACCOUNT"),
	
	//上传合同签署
	UPLOAD_CONTRACT("UPLOAD_CONTRACT"),
	
	//合同查询
	QUERY_CONTRACT("QUERY_CONTRACT"),
	
	//单个合同下载
	DOWNLOAD_SINGLE_CONTRACT("DOWNLOAD_SINGLE_CONTRACT"),
	
	//合同批量下载
	DOWNLOAD_MULTIPLE_CONTRACT("DOWNLOAD_MULTIPLE_CONTRACT"),
	
	//认证
	AUTHENTICATE("AUTHENTICATE"), //如登录身份认证
	
	//授权
	AUTHORIZE("AUTHORIZE"),
	
	//用印申请
	APPLY_USE_SEAL("APPLY_USE_SEAL"),
	
	//用户查询
	QUERY_USER("QUERY_USER"),
	
	//印章查询
	QUERY_SEAL("QUERY_SEAL"),
	
	//用印记录
	QUERY_USE_SEAL("QUERY_USE_SEAL"),
	
	//解锁
	UNLOCK("UNLOCK"),
	
	//查询用户联系方式
	QUERY_USER_CONTACT_INFO("QUERY_USER_CONTACT_INFO"),
	
	//系统数据同步
	//添加组织
	ADD_ORG("ADD_ORG"),
	
	//添加用户
	ADD_USER("ADD_USER"),
	
	//添加印章
	ADD_SEAL("ADD_SEAL"),
	
	//添加设备
	ADD_DEVICE("ADD_DEVICE"),
	
	//设备放章
	PUT_SEAL("PUT_SEAL"),
	
	//设备取章
	TAKE_SEAL("TAKE_SEAL"),
	
	//设备查询
	QUERY_DEVICE("QUERY_DEVICE"),
	
	//设备状态
	QUERY_DEVICE_STATUS("QUERY_DEVICE_STATUS"),
	
	//更新设备
	UPDATE_DEVICE("UPDATE_DEVICE"),
	
	//更新印章
	UPDATE_SEAL("UPDATE_SEAL"),
	
	//企业认证状态
	QUERY_ENTERPRISE_AUTH_STATUS("QUERY_ENTERPRISE_AUTH_STATUS"),
	
	//个人认证状态
	QUERY_PERSONAL_AUTH_STATUS("QUERY_PERSONAL_AUTH_STATUS"),
	
	//帐号绑定状态
	QUERY_ACCOUNT_BIND_STATUS("QUERY_ACCOUNT_BIND_STATUS"),
	
	 //正反一对
	
	//绑定的第三方帐号
	QUERY_BIND_ACCOUNT("QUERY_BIND_ACCOUNT"),
	
	//第三方帐号关联的持有方帐号
	QUERY_CLIENTID_BIND_ACCOUNT("QUERY_CLIENTID_BIND_ACCOUNT"),
	
	//单点链接
	SSO_LINK("SSO_LINK"),
	
	//绑定
	BIND("BIND"),
	
	//解绑
	UNBIND("UNBIND"),
	
	//关键字定位
	KEYWORD_LOCATION("KEYWORD_LOCATION"),
	
	//合同预览
	PREVIEW_CONTRACT("PREVIEW_CONTRACT"),
	
	//自动签署合同
	AUTO_SIGN_CONTRACT("AUTO_SIGN_CONTRACT"),
	
	//代理签署合同
	AGENT_SIGN_CONTRACT("AGENT_SIGN_CONTRACT"),
	
	//合同详情
	QUERY_CONTRACT_DETAIL("QUERY_CONTRACT_DETAIL"),
	
	//提醒
	REMIND("REMIND"),
	
	//撤销
	REVOKE("REVOKE"),
	
	//附页下载
	DOWNLOAD_APPENDIX("DOWNLOAD_APPENDIX"),
	
	//添加指纹
	ADD_FINGER("ADD_FINGER"),
	
	//结束用印
	END_TASK("END_TASK"),
	
	//查询组织
	QUERY_ORG("QUERY_ORG"),
	
	//通用交易类型（与子交易类型配合使用,仅适用于交易类型是某个集成厂所特有的)
	QUERY("QUERY_"),   //查询
	ADD("ADD_"),       //添加
	UPDATE("UPDATE_"), //更新
	DELETE("DELETE_"),  //删除
	
//	查询字典类型("QUERY_DICT_TYPE"),
//	添加字典类型("ADD_DICT_TYPE"),
//	查询字典值("QUERY_DICT_VALUE"),
//	添加字典值("ADD_DICT_VALUE")
	
	;
	
	private String value;
	
	ElecChannelTxCodeEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
