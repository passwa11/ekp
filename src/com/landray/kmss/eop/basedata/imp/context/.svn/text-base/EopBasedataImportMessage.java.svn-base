package com.landray.kmss.eop.basedata.imp.context;

import java.util.ArrayList;
import java.util.List;

public class EopBasedataImportMessage {
	/**
	 * 常量：失败信息。
	 */
	public final static int MESSAGE_FAIL = 0;

	/**
	 * 常量：警告信息。
	 */
	public final static int MESSAGE_WARN = 1;

	/**
	 * 常量：成功信息。
	 */
	public final static int MESSAGE_SUCCESS = 2;

	/**
	 * 返回信息
	 */
	protected String message = null;

	/**
	 * 更多信息
	 */
	protected List<String> moreMessages = new ArrayList<String>();

	/**
	 * 消息类型 见本类的MESSAGE_常量
	 */
	protected int messageType;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMoreMessages() {
		return moreMessages;
	}

	public void setMoreMessages(List<String> moreMessages) {
		this.moreMessages = moreMessages;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * 将信息作为失败信息添加。
	 * 
	 * @param message
	 *            信息
	 */
	public void addFailMsg(String message) {
		messageType = MESSAGE_FAIL;
		this.message = message;
	}

	/**
	 * 将信息作为警告信息添加。
	 * 
	 * @param message
	 *            信息
	 */
	public void addWarnMsg(String message) {
		messageType = MESSAGE_WARN;
		this.message = message;
	}

	/**
	 * 将信息作为成功信息添加。
	 * 
	 * @param message
	 *            信息
	 */
	public void addSuccessMsg(String message) {
		messageType = MESSAGE_SUCCESS;
		this.message = message;
	}

	/**
	 * 添加更多信息。
	 * 
	 * @param message
	 *            信息
	 */
	public void addMoreMsg(String message) {
		moreMessages.add(message);
	}
}
