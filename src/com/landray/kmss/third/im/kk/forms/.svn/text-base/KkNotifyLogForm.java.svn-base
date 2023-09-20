package com.landray.kmss.third.im.kk.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.im.kk.model.KkNotifyLog;


/**
 * kk待办集成日志 Form
 * 
 * @author 
 * @version 1.0 2012-04-13
 */
public class KkNotifyLogForm extends ExtendForm {

	/**
	 * 标题
	 */
	protected String fdSubject = null;
	
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
	protected String kkNotifyData = null;
	
	/**
	 * @return 发送消息包
	 */
	public String getKkNotifyData() {
		return kkNotifyData;
	}
	
	/**
	 * @param kkNotifyData 发送消息包
	 */
	public void setKkNotifyData(String kkNotifyData) {
		this.kkNotifyData = kkNotifyData;
	}
	
	/**
	 * 发送时间
	 */
	protected String sendTime = null;
	
	/**
	 * @return 发送时间
	 */
	public String getSendTime() {
		return sendTime;
	}
	
	/**
	 * @param sendTime 发送时间
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
	 * 返回时间
	 */
	protected String rtnTime = null;
	
	/**
	 * @return 返回时间
	 */
	public String getRtnTime() {
		return rtnTime;
	}
	
	/**
	 * @param rtnTime 返回时间
	 */
	public void setRtnTime(String rtnTime) {
		this.rtnTime = rtnTime;
	}
	
	/**
	 * 返回信息
	 */
	protected String kkRtnMsg = null;
	
	/**
	 * @return 返回信息
	 */
	public String getKkRtnMsg() {
		return kkRtnMsg;
	}
	
	/**
	 * @param kkRtnMsg 返回信息
	 */
	public void setKkRtnMsg(String kkRtnMsg) {
		this.kkRtnMsg = kkRtnMsg;
	}
	
	/**
	 * 其他信息
	 */
	protected String fdParams = null;
	
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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdSubject = null;
		kkNotifyData = null;
		sendTime = null;
		rtnTime = null;
		kkRtnMsg = null;
		fdParams = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KkNotifyLog.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
