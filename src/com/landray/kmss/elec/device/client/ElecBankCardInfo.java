package com.landray.kmss.elec.device.client;

import java.io.Serializable;

public class ElecBankCardInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 银行
	private String bank;

	// 银行支行
	private String bankBranch;

	// 银行卡号
	private String bankCardNo;

	// 地区
	private String area;

	// 联行号
	private String unionBankNo;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUnionBankNo() {
		return unionBankNo;
	}

	public void setUnionBankNo(String unionBankNo) {
		this.unionBankNo = unionBankNo;
	}

}
