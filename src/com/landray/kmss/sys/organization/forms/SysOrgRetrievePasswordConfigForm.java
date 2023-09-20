package com.landray.kmss.sys.organization.forms;

import com.landray.kmss.web.action.ActionForm;

public class SysOrgRetrievePasswordConfigForm extends ActionForm {
	public SysOrgRetrievePasswordConfigForm() throws Exception {
		super();
	}
	private String isEnable = "false";
	
	/*
	 * 重新发送的时间间隔
	 */
	private int reSentIntervalTime = 60;
	
	/*
	 * 验证码有效时间
	 */
	private int codeEffectiveTime = 15;
	
	/*
	 * 一天最多发送次数
	 */
	private int maxTimesOneDay = 5;



	public int getReSentIntervalTime() {
		return reSentIntervalTime;
	}

	public void setReSentIntervalTime(int reSentIntervalTime) {
		this.reSentIntervalTime = reSentIntervalTime;
	}

	public int getCodeEffectiveTime() {
		return codeEffectiveTime;
	}

	public void setCodeEffectiveTime(int codeEffectiveTime) {
		this.codeEffectiveTime = codeEffectiveTime;
	}

	public int getMaxTimesOneDay() {
		return maxTimesOneDay;
	}

	public void setMaxTimesOneDay(int maxTimesOneDay) {
		this.maxTimesOneDay = maxTimesOneDay;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsEnable() {
		return isEnable;
	}
	
	public void setMobileNoUpdateCheckEnable(String mobileNoUpdateCheckEnable) {
		this.mobileNoUpdateCheckEnable = mobileNoUpdateCheckEnable;
	}

	public String getMobileNoUpdateCheckEnable() {
		return mobileNoUpdateCheckEnable;
	}
	private String mobileNoUpdateCheckEnable = "false";

	
}
