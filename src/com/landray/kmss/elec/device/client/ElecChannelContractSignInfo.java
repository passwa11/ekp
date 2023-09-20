package com.landray.kmss.elec.device.client;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
*@author yucf
*@date  2019年7月23日
*@Description            渠道合同 签暑信息
*/

public class ElecChannelContractSignInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//服务唯一自定义标识(交易流水号)
	private String customTag;
	
	//基本信息
	private BasicInfo basicInfo;
	
	//文件信息
	private List<ContentInfo> contentInfos;
	
	//参与者信息
	private List<ParticipantInfo> participantInfos;
	
	
	//调用方自定义要求三方WEB平台在流程结束后需要跳转的指定URL地址
	private String returnUrl;
	
	//异步通知地址
	private String notifyUrl;

	/**
	 * 附加信息，可为空
	 * @return
	 */
	private Map<String,Object> fdAdditionalInfo;
	
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	public Map<String,Object> getFdAdditionalInfo() {
		if(fdAdditionalInfo==null){
			fdAdditionalInfo = new HashMap<String,Object>();
		}
		return fdAdditionalInfo;
	}
	public void setFdAdditionalInfo(Map<String,Object> fdAdditionalInfo) {
		this.fdAdditionalInfo = fdAdditionalInfo;
	}
	public void put(String key, Object value){
		if(fdAdditionalInfo==null){
			fdAdditionalInfo = new HashMap<String,Object>();
		}
		fdAdditionalInfo.put(key, value);
	}
	public Object get(String key){
		if(fdAdditionalInfo==null){
			return null;
		}
		return fdAdditionalInfo.get(key);
	}

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}
	
//	public BasicInfo buildBasicInfo() {
//		return this.new BasicInfo();
//	}
	

	public ElecChannelContractSignInfo setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
		return this;
	}

	public List<ContentInfo> getContentInfos() {
		return contentInfos;
	}

	public ElecChannelContractSignInfo setContentInfos(List<ContentInfo> contentInfos) {
		this.contentInfos = contentInfos;
		return this;
	}
	
	public ElecChannelContractSignInfo addContentInfo(ContentInfo contentInfo) {
		
		if(this.contentInfos == null) {
			this.contentInfos = new ArrayList<>();
		}
		
		this.contentInfos.add(contentInfo);
		return this;
	}

	public List<ParticipantInfo> getParticipantInfos() {
		return participantInfos;
	}

	public ElecChannelContractSignInfo setParticipantInfos(List<ParticipantInfo> participantInfos) {
		this.participantInfos = participantInfos;
		return this;
	}
	
	public ElecChannelContractSignInfo addParticipantInfo(ParticipantInfo participantInfo) {
		
		if(this.participantInfos == null) {
			this.participantInfos = new ArrayList<>();
		}
		
		this.participantInfos.add(participantInfo);
		return this;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public ElecChannelContractSignInfo setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
		return this;
	}

	public ElecChannelContractSignInfo() {
		super();
	}

	@Override
	public String toString() {
		return "ElecChannelContractSignInfo [customTag=" + customTag
				+ ", basicInfo=" + basicInfo + ", contentInfos=" + contentInfos
				+ ", participantInfos=" + participantInfos + ", returnUrl="
				+ returnUrl + "]";
	}



	public class BasicInfo {
		
		//合同编号
		private String contractNo;
		
		//合同名称或标题
		private String contractName;
		
		//备注
		private String remark;
		
		//合同类型
		private ElecContractTypeEnum contractType;
		
		//创建合同时间
		private Date createTime;
		
		//截止时间
		private Date expiryDate;
		
		//自定义元数据信息(作为扩展字段)
		private JSONObject metadata;

		public String getContractNo() {
			return contractNo;
		}

		public BasicInfo setContractNo(String contractNo) {
			this.contractNo = contractNo;
			return this;
		}

		public String getContractName() {
			return contractName;
		}

		public BasicInfo setContractName(String contractName) {
			this.contractName = contractName;
			return this;
		}

		public String getRemark() {
			return remark;
		}

		public BasicInfo setRemark(String remark) {
			this.remark = remark;
			return this;
		}

		public ElecContractTypeEnum getContractType() {
			return contractType;
		}

		public BasicInfo setContractType(ElecContractTypeEnum contractType) {
			this.contractType = contractType;
			return this;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public BasicInfo setCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public Date getExpiryDate() {
			return expiryDate;
		}

		public BasicInfo setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
			return this;
		}

		public JSONObject getMetadata() {
			return metadata;
		}

		public BasicInfo setMetadata(JSONObject metadata) {
			this.metadata = metadata;
			return this;
		}

		public BasicInfo() {
			super();
		}

		@Override
		public String toString() {
			return "BasicInfo [contractNo=" + contractNo + ", contractName="
					+ contractName + ", remark=" + remark + ", contractType="
					+ contractType + ", createTime=" + createTime
					+ ", metadata=" + metadata + "]";
		}
	}
	
	public class ContentInfo {
		
		private String fileId;
		
		//签暑顺序
		private int sequence;
		
		//文件地址(外网访问地址)
		private String fileUrl;
		
		//文件存储路径
		private String filePath;
		
		//文件base64串
		private String fileBase64;
		
		//文件名
		private String fileName;
		
		//是否附件
		private boolean isAttached = false;

		/**
		 * 存储位置(ekp附件存储位置标志，参考com.landray.kmss.sys.filestore.model.SysAttFileBase#fdAttLocation)
		 */
		private String attLocation;
		
		//自定义元数据信息(作为扩展字段)
		private JSONObject metadata;

		public String getFileId() {
			return fileId;
		}

		public ContentInfo setFileId(String fileId) {
			this.fileId = fileId;
			return this;
		}

		public int getSequence() {
			return sequence;
		}

		public ContentInfo setSequence(int sequence) {
			this.sequence = sequence;
			return this;
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public ContentInfo setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
			return this;
		}

		public String getFilePath() {
			return filePath;
		}

		public ContentInfo setFilePath(String filePath) {
			this.filePath = filePath;
			return this;
		}

		public String getFileBase64() {
			return fileBase64;
		}

		public ContentInfo setFileBase64(String fileBase64) {
			this.fileBase64 = fileBase64;
			return this;
		}

		public String getFileName() {
			return fileName;
		}

		public ContentInfo setFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public boolean getIsAttached() {
			return isAttached;
		}

		public ContentInfo setIsAttached(boolean isAttached) {
			this.isAttached = isAttached;
			return this;
		}

		public String getAttLocation() {
			return attLocation;
		}

		public ContentInfo setAttLocation(String attLocation) {
			this.attLocation = attLocation;
			return this;
		}

		public JSONObject getMetadata() {
			return metadata;
		}

		public ContentInfo setMetadata(JSONObject metadata) {
			this.metadata = metadata;
			return this;
		}

		public ContentInfo() {
			super();
		}

		@Override
		public String toString() {
			return "ContentInfo [fileId=" + fileId + ", sequence=" + sequence + ", fileUrl=" + fileUrl + ", filePath="
					+ filePath + ", fileBase64=" + fileBase64 + ", fileName=" + fileName + ", isAttached=" + isAttached
					+ ", attLocation=" + attLocation + ", metadata=" + metadata + "]";
		}
	}
	
	public class ParticipantInfo {
		
		//用户唯一标识
		private String userId;
			
		//签署人名称
		private String userName;
		
		//签署人证件类型编码
		private ElecIdentityTypeEnum identType;
		
		//签署人证件号码
		private String identNo;
		
		private String email;
		
		private String phone;
		
		//企业id
		private String enterpriseId;
		//企业名称
		private String enterpriseName;
		//企业证件类型
		private ElecIdentityTypeEnum enterpriseIdentType;
		//企业证件号码
		private String enterpriseIdentNO;
		
		//个人/企业
		private ElecParticipantTypeEnum participantType;
		
		//发送方/参与方
		private ElecParticipantTargetEnum participantTarget;
		
		//签暑人/审核员/抄送
		private ElecParticipantRoleEnum participantRole;
		
		//确认签署时可使用的认证类型
		private List<ElecSignAuthTypeEnum> signAuthTypeEnum;
		
		//内部/外部用户
		private ElecParticipantClassifyEnum participantClassify;
		
		//签署模式
		private ElecSignModeEnum signMode;
		
		//分配的签暑顺序
		private int assignedSequence;
		
		//签署信息
		private List<ElecSignInfo> signInfos;

		//自定义元数据信息(作为扩展字段)
		private JSONObject metadata;
		
		public class ElecSignInfo {

			private ElecChannelContractKeywordPosition signPositon;
			
			private ElecSignSealInfo signSeal;

			public ElecChannelContractKeywordPosition getSignPositon() {
				return signPositon;
			}

			public ElecSignInfo setSignPositon(ElecChannelContractKeywordPosition signPositon) {
				this.signPositon = signPositon;
				return this;
			}

			public ElecSignSealInfo getSignSeal() {
				return signSeal;
			}

			public ElecSignInfo setSignSeal(ElecSignSealInfo signSeal) {
				this.signSeal = signSeal;
				return this;
			}

			@Override
			public String toString() {
				return "ElecSignInfo [signPositon=" + signPositon
						+ ", signSeal=" + signSeal + "]";
			}
			
			/**
			 * 
			 * 签署印章的信息
			 *
			 */
			public class ElecSignSealInfo {

				private String sealNo;
				
				private String sealName;
				
				private Number width;
				
				private Number height;
				
				//base64图片
				private String imgBase64;
				
				private ElecChannelSignTypeEnum signType;

				public String getSealNo() {
					return sealNo;
				}

				public ElecSignSealInfo setSealNo(String sealNo) {
					this.sealNo = sealNo;
					return this;
				}

				public String getSealName() {
					return sealName;
				}

				public ElecSignSealInfo setSealName(String sealName) {
					this.sealName = sealName;
					return this;
				}

				public Number getWidth() {
					return width;
				}

				public ElecSignSealInfo setWidth(Number width) {
					this.width = width;
					return this;
				}

				public Number getHeight() {
					return height;
				}

				public ElecSignSealInfo setHeight(Number height) {
					this.height = height;
					return this;
				}

				public ElecChannelSignTypeEnum getSignType() {
					return signType;
				}

				public ElecSignSealInfo setSignType(ElecChannelSignTypeEnum signType) {
					this.signType = signType;
					return this;
				}
				
				

				public String getImgBase64() {
					return imgBase64;
				}

				public ElecSignSealInfo setImgBase64(String imgBase64) {
					this.imgBase64 = imgBase64;
					return this;
				}

				@Override
				public String toString() {
					return "ElecSignSealInfo [sealNo=" + sealNo + ", sealName="
							+ sealName + ", width=" + width + ", height=" + height
							+ ", signType=" + signType + "]";
				}
			}
		}
		
		
		public List<ElecSignInfo> getSignInfos() {
			return signInfos;
		}

		public ParticipantInfo setSignInfos(List<ElecSignInfo> signInfos) {
			this.signInfos = signInfos;
			return this;
		}

		public String getUserId() {
			return userId;
		}

		public ParticipantInfo setUserId(String userId) {
			this.userId = userId;
			return this;
		}

		public String getUserName() {
			return userName;
		}

		public ParticipantInfo setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public ElecIdentityTypeEnum getIdentType() {
			return identType;
		}

		public ParticipantInfo setIdentType(ElecIdentityTypeEnum identType) {
			this.identType = identType;
			return this;
		}

		public String getIdentNo() {
			return identNo;
		}

		public ParticipantInfo setIdentNo(String identNo) {
			this.identNo = identNo;
			return this;
		}

		public String getEmail() {
			return email;
		}

		public ParticipantInfo setEmail(String email) {
			this.email = email;
			return this;
		}

		public String getPhone() {
			return phone;
		}

		public ParticipantInfo setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		
		public String getEnterpriseId() {
			return enterpriseId;
		}

		public ParticipantInfo setEnterpriseId(String enterpriseId) {
			this.enterpriseId = enterpriseId;
			return this;
		}

		public String getEnterpriseName() {
			return enterpriseName;
		}

		public ParticipantInfo setEnterpriseName(String enterpriseName) {
			this.enterpriseName = enterpriseName;
			return this;
		}
		
		public ElecIdentityTypeEnum getEnterpriseIdentType() {
			return enterpriseIdentType;
		}

		public ParticipantInfo setEnterpriseIdentType(ElecIdentityTypeEnum enterpriseIdentType) {
			this.enterpriseIdentType = enterpriseIdentType;
			return this;
		}

		public String getEnterpriseIdentNO() {
			return enterpriseIdentNO;
		}

		public ParticipantInfo setEnterpriseIdentNO(String enterpriseIdentNO) {
			this.enterpriseIdentNO = enterpriseIdentNO;
			return this;
		}

		public ElecParticipantTypeEnum getParticipantType() {
			return participantType;
		}

		public ParticipantInfo setParticipantType(ElecParticipantTypeEnum participantType) {
			this.participantType = participantType;
			return this;
		}

		public ElecParticipantTargetEnum getParticipantTarget() {
			return participantTarget;
		}

		public ParticipantInfo setParticipantTarget(ElecParticipantTargetEnum participantTarget) {
			this.participantTarget = participantTarget;
			return this;
		}

		public ElecParticipantRoleEnum getParticipantRole() {
			return participantRole;
		}

		public ParticipantInfo setParticipantRole(ElecParticipantRoleEnum participantRole) {
			this.participantRole = participantRole;
			return this;
		}

		public List<ElecSignAuthTypeEnum> getSignAuthTypeEnum() {
			return signAuthTypeEnum;
		}

		public ParticipantInfo setSignAuthTypeEnum(List<ElecSignAuthTypeEnum> signAuthTypeEnum) {
			this.signAuthTypeEnum = signAuthTypeEnum;
			return this;
		}

		public int getAssignedSequence() {
			return assignedSequence;
		}

		public ParticipantInfo setAssignedSequence(int assignedSequence) {
			this.assignedSequence = assignedSequence;
			return this;
		}

		public JSONObject getMetadata() {
			return metadata;
		}

		public ParticipantInfo setMetadata(JSONObject metadata) {
			this.metadata = metadata;
			return this;
		}

		public ElecParticipantClassifyEnum getParticipantClassify() {
			return participantClassify;
		}

		public ParticipantInfo setParticipantClassify(
				ElecParticipantClassifyEnum participantClassify) {
			this.participantClassify = participantClassify;
			return this;
		}

		public ParticipantInfo() {
			super();
		}

		public ElecSignModeEnum getSignMode() {
			return signMode;
		}

		public void setSignMode(ElecSignModeEnum signMode) {
			this.signMode = signMode;
		}

		@Override
		public String toString() {
			return "ParticipantInfo [userId=" + userId + ", userName=" + userName + ", identType=" + identType
					+ ", identNo=" + identNo + ", email=" + email + ", phone=" + phone + ", enterpriseId="
					+ enterpriseId + ", enterpriseName=" + enterpriseName + ", enterpriseIdentType="
					+ enterpriseIdentType + ", enterpriseIdentNO=" + enterpriseIdentNO + ", participantType="
					+ participantType + ", participantTarget=" + participantTarget + ", participantRole="
					+ participantRole + ", signAuthTypeEnum=" + signAuthTypeEnum + ", participantClassify="
					+ participantClassify + ", signMode=" + signMode + ", assignedSequence=" + assignedSequence
					+ ", signInfos=" + signInfos + ", metadata=" + metadata + "]";
		}
	}
}
