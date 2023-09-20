package com.landray.kmss.elec.device.client;

import java.util.Set;

/**
*@author yucf
*@date  2019年6月27日
*@Description                       合同签暑信息
* 此类作废，换 ElecChannelContractSignInfo
*/
@Deprecated
public class ElecContractSignInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//平台是否签署(0：抄送；1：签署；2：暂不签署)
	private int isSign = 2;
	
	//合同类型
	private String contractType;
	
	//合同名称
	private String contractName;
	
	//项目编号
	private String projectNo;
	
	//签署用户ID
	private Set<String> signUserIdSet;
	
	//合同路径
	private String contractPath;

	public int getIsSign() {
		return isSign;
	}

	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public Set<String> getSignUserIdSet() {
		return signUserIdSet;
	}

	public void setSignUserIdSet(Set<String> signUserIdSet) {
		this.signUserIdSet = signUserIdSet;
	}

	public String getContractPath() {
		return contractPath;
	}

	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}
}
