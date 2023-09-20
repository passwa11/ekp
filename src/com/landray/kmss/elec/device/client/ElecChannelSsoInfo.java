package com.landray.kmss.elec.device.client;

import java.io.Serializable;

/**
*@author yucf
*@date  2020年1月3日
*@Description                   渠道单点登录/绑定信息
*/

public class ElecChannelSsoInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//序列号
	private String serialNo;
	
	//平台帐号信息
	private AccountInfo platAccInfo;
	
	//第三方帐号信息
	private AccountInfo thirdpartyAccInfo;
	
	//业务跳转参数
	private BussinessInfo bussinessInfo;
	

	public ElecChannelSsoInfo() {
		super();
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public AccountInfo getPlatAccInfo() {
		return platAccInfo;
	}


	public void setPlatAccInfo(AccountInfo platAccInfo) {
		this.platAccInfo = platAccInfo;
	}


	public AccountInfo getThirdpartyAccInfo() {
		return thirdpartyAccInfo;
	}


	public void setThirdpartyAccInfo(AccountInfo thirdpartyAccInfo) {
		this.thirdpartyAccInfo = thirdpartyAccInfo;
	}


	public BussinessInfo getBussinessInfo() {
		return bussinessInfo;
	}


	public void setBussinessInfo(BussinessInfo bussinessInfo) {
		this.bussinessInfo = bussinessInfo;
	}

	@Override
	public String toString() {
		return "ElecChannelSsoInfo [serialNo=" + serialNo + ", platAccInfo="
				+ platAccInfo + ", thirdpartyAccInfo=" + thirdpartyAccInfo
				+ ", bussinessInfo=" + bussinessInfo + "]";
	}


	public class BussinessInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String bussinessId;
		
		//业务类型
		private ElecChannelSsoTargeEnum targetType;
		
		//相应的业务操作后跳转地址或自定跳转页
		private String returnUrl;

		//异步通知地址
		private String notifyUrl;
		
		public BussinessInfo() {
			super();
		}

		public String getBussinessId() {
			return bussinessId;
		}

		public void setBussinessId(String bussinessId) {
			this.bussinessId = bussinessId;
		}

		public ElecChannelSsoTargeEnum getTargetType() {
			return targetType;
		}

		public void setTargetType(ElecChannelSsoTargeEnum targetType) {
			this.targetType = targetType;
		}

		public String getReturnUrl() {
			return returnUrl;
		}

		public void setReturnUrl(String returnUrl) {
			this.returnUrl = returnUrl;
		}

		public String getNotifyUrl() {
			return notifyUrl;
		}

		public void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
		}

		@Override
		public String toString() {
			return "BussinessInfo [bussinessId=" + bussinessId + ", targetType="
					+ targetType + ", returnUrl=" + returnUrl + ", notifyUrl="
					+ notifyUrl + "]";
		}
	}
	

	public class AccountInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;

		//帐号
		private String account;
		
		//帐号类型：个人， 企业
		private String type;
		
		//户主
		private String name;
		
		//企业名称
		private String enterpriseName;
		
		//角色
		
		//部门

		public AccountInfo() {
			super();
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEnterpriseName() {
			return enterpriseName;
		}

		public void setEnterpriseName(String enterpriseName) {
			this.enterpriseName = enterpriseName;
		}

		@Override
		public String toString() {
			return "AccountInfo [account=" + account + ", type=" + type
					+ ", name=" + name + ", enterpriseName=" + enterpriseName
					+ "]";
		}
	}
	
	

}
