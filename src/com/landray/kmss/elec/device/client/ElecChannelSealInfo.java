package com.landray.kmss.elec.device.client;

import java.util.ArrayList;
import java.util.List;

/**
*@author yucf
*@date  2019年8月5日
*@Description            印章信息(支持物理及电子印章)，含绑定关系
*/

public class ElecChannelSealInfo implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//印章基本信息
	private ElecChannelSealBaseInfo baseInfo; 
	
	//印章类型
	private ElecChannelSealType sealType;
	
	//印章形状
	private ElecChannelSealShape sealShape;
	
	//印章材质
	private ElecChannelSealMaterial sealMaterial;
	
	//部门/公司
	private List<ElecChannelOrgInfo> orgList;
	
	//绑定的设备
	private ElecChannelPhysicalDeviceInfo bindDeviceInfo;
	
	//印章权限人员信息
	private List<ElecChannelUserInfo> mgrUserInfos;

	
	public ElecChannelSealInfo(){
	}

	public ElecChannelSealInfo(ElecChannelSealBaseInfo baseInfo) {
		super();
		this.baseInfo = baseInfo;
	}

	public ElecChannelSealBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public ElecChannelSealInfo setBaseInfo(ElecChannelSealBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
		return this;
	}

	public ElecChannelSealType getSealType() {
		return sealType;
	}

	public ElecChannelSealInfo setSealType(ElecChannelSealType sealType) {
		this.sealType = sealType;
		return this;
	}

	public List<ElecChannelOrgInfo> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<ElecChannelOrgInfo> orgList) {
		this.orgList = orgList;
	}

	public ElecChannelPhysicalDeviceInfo getBindDeviceInfo() {
		return bindDeviceInfo;
	}

	public ElecChannelSealInfo setBindDeviceInfo(ElecChannelPhysicalDeviceInfo bindDeviceInfo) {
		this.bindDeviceInfo = bindDeviceInfo;
		return this;
	}

	public List<ElecChannelUserInfo> getMgrUserInfos() {
		return mgrUserInfos;
	}

	public ElecChannelSealInfo setMgrUserInfos(List<ElecChannelUserInfo> mgrUserInfos) {
		this.mgrUserInfos = mgrUserInfos;
		return this;
	}
	
	public List<ElecChannelUserInfo> addMgrUserInfo(ElecChannelUserInfo mgrUserInfo){
		
		if(this.mgrUserInfos == null){
			this.mgrUserInfos = new ArrayList<>();
		}
		
		this.mgrUserInfos.add(mgrUserInfo);
		
		return mgrUserInfos;
	}

	public ElecChannelSealShape getSealShape() {
		return sealShape;
	}

	public ElecChannelSealInfo setSealShape(ElecChannelSealShape sealShape) {
		this.sealShape = sealShape;
		return this;
	}

	public ElecChannelSealMaterial getSealMaterial() {
		return sealMaterial;
	}

	public ElecChannelSealInfo setSealMaterial(ElecChannelSealMaterial sealMaterial) {
		this.sealMaterial = sealMaterial;
		return this;
	}
	
	
}
