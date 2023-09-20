package com.landray.kmss.hr.staff.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 薪酬福利明细
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareDetaliedForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	// 相关流程
	private String fdRelatedProcess;
	// 调整日期
	private String fdAdjustDate;
	// 调整前薪酬
	private String fdBeforeEmolument;
	// 调整金额
	private String fdAdjustAmount;
	// 调整后薪酬
	private String fdAfterEmolument;
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
	public Class getModelClass() {
		return HrStaffEmolumentWelfareDetalied.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdRelatedProcess = null;
		this.fdAdjustDate = null;
		this.fdBeforeEmolument = null;
		this.fdAdjustAmount = null;
		this.fdAfterEmolument = null;
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

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public String getFdAdjustDate() {
		return fdAdjustDate;
	}

	public void setFdAdjustDate(String fdAdjustDate) {
		this.fdAdjustDate = fdAdjustDate;
	}

	public String getFdBeforeEmolument() {
		return fdBeforeEmolument;
	}

	public void setFdBeforeEmolument(String fdBeforeEmolument) {
		this.fdBeforeEmolument = fdBeforeEmolument;
	}

	public String getFdAdjustAmount() {
		return fdAdjustAmount;
	}

	public void setFdAdjustAmount(String fdAdjustAmount) {
		this.fdAdjustAmount = fdAdjustAmount;
	}

	public String getFdAfterEmolument() {
		return fdAfterEmolument;
	}

	public void setFdAfterEmolument(String fdAfterEmolument) {
		this.fdAfterEmolument = fdAfterEmolument;
	}

}
