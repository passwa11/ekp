package com.landray.kmss.elec.device.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
*@author yucf
*@date  2019年10月18日
*@                               物理设备信息
*/

public class ElecChannelPhysicalDeviceInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//设备ID
	private String deviceId;
	
	//设备名称
	private String deviceName;
	
	//设备标识码
	private String deviceNo;
	
	//设备MAC
	private String deviceMac;
	
	//设备权限人员信息
	private List<ElecChannelUserInfo> mgrUserInfos;
	
	
	public boolean addMgrUserInfo(ElecChannelUserInfo userInfo){
		
		if(CollectionUtils.isEmpty(this.mgrUserInfos)){
			this.mgrUserInfos = new ArrayList<>();
		}
		
		return this.mgrUserInfos.add(userInfo);
	}

	public String getDeviceId() {
		return deviceId;
	}

	public ElecChannelPhysicalDeviceInfo setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public ElecChannelPhysicalDeviceInfo setDeviceName(String deviceName) {
		this.deviceName = deviceName;
		return this;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public ElecChannelPhysicalDeviceInfo setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
		return this;
	}

	public List<ElecChannelUserInfo> getMgrUserInfos() {
		return mgrUserInfos;
	}

	public ElecChannelPhysicalDeviceInfo setMgrUserInfos(List<ElecChannelUserInfo> mgrUserInfos) {
		this.mgrUserInfos = mgrUserInfos;
		return this;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public ElecChannelPhysicalDeviceInfo setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
		return this;
	}
}
