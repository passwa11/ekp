package com.landray.kmss.elec.device.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@author yucf
*@date  2019年6月28日
*@Description              合同信息
*/

public class ElecContractInfo implements IElecChannelRequestMessage,IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//合同编号
	private String contractNo;
	
	//合同类型
	private ElecContractTypeEnum contractType;
	
	//合同名称
	private String contractName;
	
	//合同状态
	private ElecContractStateEnum contractState;
	
	//创建合同时间
	private Date createTime;
	
	//合同签署截止时间
	private Date expiredDate;
	
	//签暑信息
	private List<Signatory> signatories = new ArrayList<>();
	
	//备注信息
	private String remark;

	private String thirdContractNo;

	/**
	 * 附加信息，可为空
	 * @return
	 */
	private Map<String,Object> fdAdditionalInfo;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getThirdContractNo() {
		return thirdContractNo;
	}

	public void setThirdContractNo(String thirdContractNo) {
		this.thirdContractNo = thirdContractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public ElecContractTypeEnum getContractType() {
		return contractType;
	}

	public void setContractType(ElecContractTypeEnum contractType) {
		this.contractType = contractType;
	}

	public ElecContractStateEnum getContractState() {
		return contractState;
	}

	public void setContractState(ElecContractStateEnum contractState) {
		this.contractState = contractState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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

	public List<Signatory> getSignatories() {
		return signatories;
	}

	public void setSignatories(List<Signatory> signatories) {
		this.signatories = signatories;
	}

	public class Signatory {
		//唯一标识
		private String uniqueKey;
		
		//签署人名称
		private String userName;
		
		//签署人证件类型编码
		private ElecIdentityTypeEnum identType;
		
		//签署人证件号码
		private String identNo;
		
		//个人/企业
		private ElecParticipantTypeEnum participantType;
		
		//内部/外部用户
		private ElecParticipantClassifyEnum participantClassify;
		
		//签署状态
		private ElecSignmentStateEnum signmentState;
		
		//签署时间
		private Date signTime;
		
		//拒绝原因
		private String refuseReason;
		
		//签署链接
		private String signUrl;
		
		public Signatory(){
		}
		
		public String getUniqueKey() {
			return uniqueKey;
		}

		public void setUniqueKey(String uniqueKey) {
			this.uniqueKey = uniqueKey;
		}
		
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getIdentNo() {
			return identNo;
		}

		public void setIdentNo(String identNo) {
			this.identNo = identNo;
		}

		public Date getSignTime() {
			return signTime;
		}

		public void setSignTime(Date signTime) {
			this.signTime = signTime;
		}

		public String getRefuseReason() {
			return refuseReason;
		}

		public void setRefuseReason(String refuseReason) {
			this.refuseReason = refuseReason;
		}

		public ElecIdentityTypeEnum getIdentType() {
			return identType;
		}

		public void setIdentType(ElecIdentityTypeEnum identType) {
			this.identType = identType;
		}

		public ElecSignmentStateEnum getSignmentState() {
			return signmentState;
		}

		public void setSignmentState(ElecSignmentStateEnum signmentState) {
			this.signmentState = signmentState;
		}
		
		public ElecParticipantTypeEnum getParticipantType() {
			return participantType;
		}

		public void setParticipantType(ElecParticipantTypeEnum participantType) {
			this.participantType = participantType;
		}

		public ElecParticipantClassifyEnum getParticipantClassify() {
			return participantClassify;
		}

		public void setParticipantClassify(ElecParticipantClassifyEnum participantClassify) {
			this.participantClassify = participantClassify;
		}

		public String getSignUrl() {
			return signUrl;
		}

		public void setSignUrl(String signUrl) {
			this.signUrl = signUrl;
		}
	}


}
