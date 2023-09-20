package com.landray.kmss.third.im.kk.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;



/**
 * 推送消息队列表
 * 
 * @author 
 * @version 1.0 2017-09-11
 */
public class KkImNotify  extends BaseModel {

	/**
	 * 类别
	 */
	private Integer fdType;
	
	/**
	 * @return 类别
	 */
	public Integer getFdType() {
		return this.fdType;
	}
	
	/**
	 * @param fdType 类别
	 */
	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}
	
	/**
	 * 消息ID
	 */
	private String fdNotifyId;
	
	/**
	 * @return 消息ID
	 */
	public String getFdNotifyId() {
		return this.fdNotifyId;
	}
	
	/**
	 * @param fdNotifyId 消息ID
	 */
	public void setFdNotifyId(String fdNotifyId) {
		this.fdNotifyId = fdNotifyId;
	}
	
	/**
	 * 通知人ID
	 */
	private String fdUserId;
	
	/**
	 * @return 通知人ID
	 */
	public String getFdUserId() {
		return this.fdUserId;
	}
	
	/**
	 * @param fdUserId 通知人ID
	 */
	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}
	
	/**
	 * 通知人name
	 */
	private String fdUserName;
	
	/**
	 * @return 通知人name
	 */
	public String getFdUserName() {
		return this.fdUserName;
	}
	
	/**
	 * @param fdUserName 通知人name
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}
	
	/**
	 * 标题
	 */
	private String fdSubject;
	
	/**
	 * @return 标题
	 */
	public String getFdSubject() {
		return this.fdSubject;
	}
	
	/**
	 * @param fdSubject 标题
	 */
	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}
	
	/**
	 * 消息发送地址
	 */
	private String fdSendUrl;
	
	/**
	 * @return 消息发送地址
	 */
	public String getFdSendUrl() {
		return this.fdSendUrl;
	}
	
	/**
	 * @param fdSendUrl 消息发送地址
	 */
	public void setFdSendUrl(String fdSendUrl) {
		this.fdSendUrl = fdSendUrl;
	}
	
	/**
	 * 发送消息包
	 */
	private String fdNotifyData;
	
	/**
	 * @return 发送消息包
	 */
	public String getFdNotifyData() {
		return this.fdNotifyData;
	}
	
	/**
	 * @param fdNotifyData 发送消息包
	 */
	public void setFdNotifyData(String fdNotifyData) {
		this.fdNotifyData = fdNotifyData;
	}
	
	/**
	 * 返回消息
	 */
	private String fdRtnMsg;
	
	/**
	 * @return 返回消息
	 */
	public String getFdRtnMsg() {
		return this.fdRtnMsg;
	}
	
	/**
	 * @param fdRtnMsg 返回消息
	 */
	public void setFdRtnMsg(String fdRtnMsg) {
		this.fdRtnMsg = fdRtnMsg;
	}
	
	/**
	 * 状态
	 */
	private Integer fdStatus;
	
	/**
	 * @return 状态
	 */
	public Integer getFdStatus() {
		return this.fdStatus;
	}
	
	/**
	 * @param fdStatus 状态
	 */
	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}
	
	/**
	 * 发送时间
	 */
	private Date fdSendTime;
	
	/**
	 * @return 发送时间
	 */
	public Date getFdSendTime() {
		return this.fdSendTime;
	}
	
	/**
	 * @param fdSendTime 发送时间
	 */
	public void setFdSendTime(Date fdSendTime) {
		this.fdSendTime = fdSendTime;
	}
	
	/**
	 * 首次发送时间
	 */
	private Date fdFirstTime;
	
	/**
	 * @return 首次发送时间
	 */
	public Date getFdFirstTime() {
		return this.fdFirstTime;
	}
	
	/**
	 * @param fdFirstTime 首次发送时间
	 */
	public void setFdFirstTime(Date fdFirstTime) {
		this.fdFirstTime = fdFirstTime;
	}
	

	//机制开始
	//机制结束

	/*	public Class<KkImNotifyForm> getFormClass() {
			return KkImNotifyForm.class;
		}*/

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
