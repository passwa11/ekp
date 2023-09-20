package com.landray.kmss.hr.staff.model;

import com.landray.kmss.hr.staff.forms.HrStaffEmolumentWelfareDetaliedForm;

import java.util.Date;

/**
 * 薪酬福利明细
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareDetalied extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;

	// 相关流程
	private String fdRelatedProcess;
	// 调整日期
	private Date fdAdjustDate;
	// 调整前薪酬
	private Double fdBeforeEmolument;
	// 调整金额
	private Double fdAdjustAmount;
	// 调整后薪酬
	private Double fdAfterEmolument;
	//调薪来源
	private String fdSource;
	/**
	 * 异动生效时间
	 */
	private Date fdEffectTime;
	/**
	 * 调薪生效状态 【true、生效；false、未生效;】
	 */
	private Boolean fdIsEffective =Boolean.FALSE;
	/**
	 * 调薪生效状态 【true、生效；false、未生效;】
	 */
	public Boolean getFdIsEffective() {
		return fdIsEffective;
	}

	public void setFdIsEffective(Boolean fdIsEffective) {
		this.fdIsEffective = fdIsEffective;
	}
	@Override
	public Class getFormClass() {
		return HrStaffEmolumentWelfareDetaliedForm.class;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public Date getFdAdjustDate() {
		return fdAdjustDate;
	}

	public void setFdAdjustDate(Date fdAdjustDate) {
		this.fdAdjustDate = fdAdjustDate;
	}

	public Double getFdBeforeEmolument() {
		return fdBeforeEmolument;
	}

	public void setFdBeforeEmolument(Double fdBeforeEmolument) {
		this.fdBeforeEmolument = fdBeforeEmolument;
	}

	public Double getFdAdjustAmount() {
		return fdAdjustAmount;
	}

	public void setFdAdjustAmount(Double fdAdjustAmount) {
		this.fdAdjustAmount = fdAdjustAmount;
	}

	public Double getFdAfterEmolument() {
		return fdAfterEmolument;
	}

	public void setFdAfterEmolument(Double fdAfterEmolument) {
		this.fdAfterEmolument = fdAfterEmolument;
	}

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	public Date getFdEffectTime() {
		return fdEffectTime;
	}

	public void setFdEffectTime(Date fdEffectTime) {
		this.fdEffectTime = fdEffectTime;
	}

}
