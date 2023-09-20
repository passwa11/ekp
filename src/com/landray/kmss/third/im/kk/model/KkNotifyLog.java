package com.landray.kmss.third.im.kk.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.im.kk.forms.KkNotifyLogForm;

/**
 * kk待办集成日志
 * 
 * @author 
 * @version 1.0 2012-04-13
 */
public class KkNotifyLog extends BaseModel {

	/**
	 * 标题
	 */
	protected String fdSubject;
	
	/**
	 * @return 标题
	 */
	public String getFdSubject() {
		return fdSubject;
	}
	
	/**
	 * @param fdSubject 标题
	 */
	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}
	
	/**
	 * 发送消息包
	 */
	protected String kkNotifyData;
	
	/**
	 * @return 发送消息包
	 */
	public String getKkNotifyData() {
		return (String) readLazyField("kkNotifyData", kkNotifyData);
	}
	
	/**
	 * @param kkNotifyData 发送消息包
	 */
	public void setKkNotifyData(String kkNotifyData) {
		this.kkNotifyData = (String) writeLazyField("kkNotifyData",
				this.kkNotifyData, kkNotifyData);
	}
	
	/**
	 * 发送时间
	 */
	protected Date sendTime;
	
	/**
	 * @return 发送时间
	 */
	public Date getSendTime() {
		return sendTime;
	}
	
	/**
	 * @param sendTime 发送时间
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
	 * 返回时间
	 */
	protected Date rtnTime;
	
	/**
	 * @return 返回时间
	 */
	public Date getRtnTime() {
		return rtnTime;
	}
	
	/**
	 * @param rtnTime 返回时间
	 */
	public void setRtnTime(Date rtnTime) {
		this.rtnTime = rtnTime;
	}
	
	/**
	 * 返回信息
	 */
	protected String kkRtnMsg;
	
	/**
	 * @return 返回信息
	 */
	public String getKkRtnMsg() {
		return (String) readLazyField("kkRtnMsg", kkRtnMsg);
	}
	
	/**
	 * @param kkRtnMsg 返回信息
	 */
	public void setKkRtnMsg(String kkRtnMsg) {
		this.kkRtnMsg = (String) writeLazyField("kkRtnMsg",
				this.kkRtnMsg, kkRtnMsg);
	}
	
	/**
	 * 其他信息
	 */
	protected String fdParams;
	
	/**
	 * @return 其他信息
	 */
	public String getFdParams() {
		return fdParams;
	}
	
	/**
	 * @param fdParams 其他信息
	 */
	public void setFdParams(String fdParams) {
		this.fdParams = fdParams;
	}
	
	@Override
    public Class getFormClass() {
		return KkNotifyLogForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
