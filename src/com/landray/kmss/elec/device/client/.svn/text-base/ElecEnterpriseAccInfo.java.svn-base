package com.landray.kmss.elec.device.client;

import java.util.List;

/**
*@author yucf
*@date  2019年7月10日
*@Description                企业开户信息
*/

public class ElecEnterpriseAccInfo implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//企业名称
	private String enterpriseName;
	
	//证件类型编码
	private String identType;
	
	//证件号码
	private String identNo;
	
	//邮箱与手机号不能同时为空
	//邮箱
	private String email;
	
	//企业电子签章的负责人或者经办人的手机号；
	private String phone;
	
	//企业联系电话
	private String landlinePhone;
	
	//企业联系人信息
	private ElecEnterpriseTransactor transactor;
	
	// 法人
	private ElecEnterpriseTransactor legalPerson;

	// 经办人
	private ElecEnterpriseTransactor agent;

	// 统一社会信用代码
	private String unifiedSocialCode;

	// 认证类型
	private String authType;

	// 对公银行信息
	private ElecBankCardInfo bankCardInfo;

	// 额外照片/附件
	private List<ElecIdCardImage> extraImages;

	// 自定义标识
	private String customTag;

	// 自定义跳转URL
	private String returnUrl;

	// 自定义可接受数据类型
	private String acceptDataType;


	//用户ID（申请成功时返回的）
	private String userId;
	
	
	public ElecEnterpriseAccInfo() {
		super();
	}

	public ElecEnterpriseTransactor getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(ElecEnterpriseTransactor legalPerson) {
		this.legalPerson = legalPerson;
	}

	public ElecEnterpriseTransactor getAgent() {
		return agent;
	}

	public void setAgent(ElecEnterpriseTransactor agent) {
		this.agent = agent;
	}

	public String getUnifiedSocialCode() {
		return unifiedSocialCode;
	}

	public void setUnifiedSocialCode(String unifiedSocialCode) {
		this.unifiedSocialCode = unifiedSocialCode;
	}
	
	public List<ElecIdCardImage> getExtraImages() {
		return extraImages;
	}

	public void setExtraImages(List<ElecIdCardImage> extraImages) {
		this.extraImages = extraImages;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public ElecBankCardInfo getBankCardInfo() {
		return bankCardInfo;
	}

	public void setBankCardInfo(ElecBankCardInfo bankCardInfo) {
		this.bankCardInfo = bankCardInfo;
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAcceptDataType() {
		return acceptDataType;
	}

	public void setAcceptDataType(String acceptDataType) {
		this.acceptDataType = acceptDataType;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}


	public String getIdentType() {
		return identType;
	}

	
	public void setIdentType(String identType) {
		this.identType = identType;
	}

	
	public String getIdentNo() {
		return identNo;
	}

	
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
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


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLandlinePhone() {
		return landlinePhone;
	}

	public void setLandlinePhone(String landlinePhone) {
		this.landlinePhone = landlinePhone;
	}

	public ElecEnterpriseTransactor getTransactor() {
		return transactor;
	}


	public void setTransactor(ElecEnterpriseTransactor transactor) {
		this.transactor = transactor;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	//经办人信息
	public class ElecEnterpriseTransactor {
		
		//经办人名称
		private String realName;
		
		//证件类型编码
		private String identType;
		
		//证件号码
		private String identNo;
		
		// 手机号
		private String phone;

		// 附件/图片
		private List<ElecIdCardImage> trustInstrumentImage;


		public ElecEnterpriseTransactor() {
			super();
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public List<ElecIdCardImage> getTrustInstrumentImage() {
			return trustInstrumentImage;
		}

		public void
				setTrustInstrumentImage(List<ElecIdCardImage> trustInstrumentImage) {
			this.trustInstrumentImage = trustInstrumentImage;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getIdentType() {
			return identType;
		}

		public void setIdentType(String identType) {
			this.identType = identType;
		}

		public String getIdentNo() {
			return identNo;
		}

		public void setIdentNo(String identNo) {
			this.identNo = identNo;
		}	
	}
}
