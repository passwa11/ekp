package com.landray.kmss.elec.device.client;

import java.io.Serializable;

/**
*@author yucf
*@date  2019年7月10日
*@Description
*/

public class ElecAdditionalInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//客户端IP
	private String clientIP;
	
	//浏览器userAgent
	private String userAgent;
	
	//操作人
	private String operator;
	
	//而追加关键数据项
	private String keyword1;
	
	private String keyword2;
	
	private String keyword3;
	
	/**
	 * {@link ElecChannelTxCodeEnum}
	 * 通用交易类型（有些交易类型可能是某些渠道特有，为了避免频繁改动 ElecChannelTxCodeEnum, 
	 * 所以需要通用交易类型+子交易类型来构造完整的交易标识，集成模块最终交易码格式为：txCode_subTxCode
	 */
	private ElecChannelTxCodeEnum txCode;
	
	//子交易码，不单独使用，须结合txCode
	private String subTxCode;
	
	public ElecAdditionalInfo() {
		super();
	}

	public ElecAdditionalInfo(ElecChannelTxCodeEnum txCode) {
		super();
		this.txCode = txCode;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getOperator() {
		return operator;
	}

	public ElecAdditionalInfo setOperator(String operator) {
		this.operator = operator;
		return this;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public ElecAdditionalInfo setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
		return this;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public ElecAdditionalInfo setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
		return this;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public ElecAdditionalInfo setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
		return this;
	}

	public ElecChannelTxCodeEnum getTxCode() {
		return txCode;
	}

	public ElecAdditionalInfo setTxCode(ElecChannelTxCodeEnum txCode) {
		this.txCode = txCode;
		return this;
	}

	public String getSubTxCode() {
		return subTxCode;
	}

	public ElecAdditionalInfo setSubTxCode(String subTxCode) {
		this.subTxCode = subTxCode;
		return this;
	}
}
