package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;

public abstract class SysPraiseInfoDetailBaseForm extends ExtendForm {

	/**
	 * 点赞数
	 */
	private String fdPraiseNum = "0";

	/**
	 * 被赞数
	 */
	private String fdPraisedNum = "0";

	/**
	 * 踩数
	 */
	private String fdOpposeNum = "0";

	/**
	 * 被踩数
	 */
	private String fdOpposedNum = "0";

	/**
	 * 打赏财富值
	 */
	private String fdRichPay = "0";

	/**
	 * 收到的财富值
	 */
	private String fdRichGet = "0";

	/**
	 * 打赏次数
	 */
	private String fdPayNum = "0";

	/**
	 * 被赏次数
	 */
	private String fdReceiveNum = "0";

	public String getFdPayNum() {
		return fdPayNum;
	}

	public void setFdPayNum(String fdPayNum) {
		this.fdPayNum = fdPayNum;
	}

	public String getFdReceiveNum() {
		return fdReceiveNum;
	}

	public void setFdReceiveNum(String fdReceiveNum) {
		this.fdReceiveNum = fdReceiveNum;
	}

	public String getFdPraiseNum() {
		return fdPraiseNum;
	}

	public void setFdPraiseNum(String fdPraiseNum) {
		this.fdPraiseNum = fdPraiseNum;
	}

	public String getFdPraisedNum() {
		return fdPraisedNum;
	}

	public void setFdPraisedNum(String fdPraisedNum) {
		this.fdPraisedNum = fdPraisedNum;
	}

	public String getFdOpposeNum() {
		return fdOpposeNum;
	}

	public void setFdOpposeNum(String fdOpposeNum) {
		this.fdOpposeNum = fdOpposeNum;
	}

	public String getFdOpposedNum() {
		return fdOpposedNum;
	}

	public void setFdOpposedNum(String fdOpposedNum) {
		this.fdOpposedNum = fdOpposedNum;
	}

	public String getFdRichPay() {
		return fdRichPay;
	}

	public void setFdRichPay(String fdRichPay) {
		this.fdRichPay = fdRichPay;
	}

	public String getFdRichGet() {
		return fdRichGet;
	}

	public void setFdRichGet(String fdRichGet) {
		this.fdRichGet = fdRichGet;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPraiseNum = "0";
		fdPraisedNum = "0";
		fdOpposeNum = "0";
		fdOpposedNum = "0";
		fdRichPay = "0";
		fdRichGet = "0";
		fdPayNum = "0";
		fdReceiveNum = "0";
		super.reset(mapping, request);
	}

}
