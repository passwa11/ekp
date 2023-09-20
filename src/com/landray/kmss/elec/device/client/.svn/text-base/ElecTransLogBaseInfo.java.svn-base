package com.landray.kmss.elec.device.client;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
*@author yucf
*@date  2019年7月25日
*@Description               第三方交易日志基础表
*/

public class ElecTransLogBaseInfo extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	//平台流水号
	private String fdPlatSn;
	
	//第三方平台交易号
	private String fdTransNo;
	
	//交易标识
	private String fdTxCode;
	
	//请求参数
	private String fdRequestMsg;
	
	//响应报文
	private String fdResponseMsg;
	
	//请求时间
	private Date fdRequestDate = new Date();
	
	//响应时间
	private Date fdResponseDate;
	
	//响应状态码
	private String fdResponseCode;
	
	//响应描述
	private String fdResponseCodeDesc;
	
	//便于业务上查询数据，而追加关键数据项
	private String fdKeyword1;
	
	private String fdKeyword2;
	
	private String fdKeyword3;

	public String getFdPlatSn() {
		return fdPlatSn;
	}

	public void setFdPlatSn(String fdPlatSn) {
		this.fdPlatSn = fdPlatSn;
	}

	public String getFdTransNo() {
		return fdTransNo;
	}

	public void setFdTransNo(String fdTransNo) {
		this.fdTransNo = fdTransNo;
	}

	public String getFdTxCode() {
		return fdTxCode;
	}

	public void setFdTxCode(String fdTxCode) {
		this.fdTxCode = fdTxCode;
	}

	public String getFdRequestMsg() {
		return (String) readLazyField("fdRequestMsg", fdRequestMsg);
	}

	public void setFdRequestMsg(String fdRequestMsg) {
		this.fdRequestMsg = (String) writeLazyField("fdRequestMsg",
				this.fdRequestMsg, fdRequestMsg);
	}


	public String getFdResponseMsg() {
		return (String) readLazyField("fdResponseMsg", fdResponseMsg);
	}

	public void setFdResponseMsg(String fdResponseMsg) {		
		this.fdResponseMsg = (String) writeLazyField("fdResponseMsg",
				this.fdResponseMsg, fdResponseMsg);
	}


	public Date getFdRequestDate() {
		return fdRequestDate;
	}


	public void setFdRequestDate(Date fdRequestDate) {
		this.fdRequestDate = fdRequestDate;
	}


	public Date getFdResponseDate() {
		return fdResponseDate;
	}


	public void setFdResponseDate(Date fdResponseDate) {
		this.fdResponseDate = fdResponseDate;
	}


	public String getFdResponseCode() {
		return fdResponseCode;
	}


	public void setFdResponseCode(String fdResponseCode) {
		this.fdResponseCode = fdResponseCode;
	}


	public String getFdResponseCodeDesc() {
		return fdResponseCodeDesc;
	}

	public void setFdResponseCodeDesc(String fdResponseCodeDesc) {
		this.fdResponseCodeDesc = fdResponseCodeDesc;
	}
	
	public String getFdKeyword1() {
		return fdKeyword1;
	}

	public void setFdKeyword1(String fdKeyword1) {
		this.fdKeyword1 = fdKeyword1;
	}

	public String getFdKeyword2() {
		return fdKeyword2;
	}

	public void setFdKeyword2(String fdKeyword2) {
		this.fdKeyword2 = fdKeyword2;
	}


	public String getFdKeyword3() {
		return fdKeyword3;
	}

	public void setFdKeyword3(String fdKeyword3) {
		this.fdKeyword3 = fdKeyword3;
	}

	@Override
	public Class getFormClass() {
		return null;
	}
}
