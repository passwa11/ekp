package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPayrollIssuanceForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class HrStaffPayrollIssuance extends BaseModel implements
		IAttachment,ISysNotifyModel, InterceptFieldEnabled {
	/**
	 * 推送邮件账号
	 */
	private String fdSendEmail;
	// 推送消息名称
	private String fdMessageName;
	// 发放信息
	private String fdResultMseeage;
	// 发送信息详情
	private String fdResultDetailMseeage;
	// 基本工资
	private Double fdBasicWage;
	// 岗位工资
	private Double fdPositionSalary;
	// 交通补贴
	private Double fdTransAllowance;
	// 公积金
	private Double fdHousingFund;
	// 社保医保
	private Double fdSocialInsuBasicMed;
	// 备注
	private String fdRemark;
	// 创建者
	private SysOrgPerson fdCreator;
	// 创建时间
	private Date fdCreateTime;
	
	public String getFdMessageName() {
		return fdMessageName;
	}

	public void setFdMessageName(String fdMessageName) {
		this.fdMessageName = fdMessageName;
	}

	public String getFdResultMseeage() {
		return fdResultMseeage;
	}

	public void setFdResultMseeage(String fdResultMseeage) {
		this.fdResultMseeage = fdResultMseeage;
	}
		
	
	
	public String getFdResultDetailMseeage() {
		return (String) readLazyField("fdResultDetailMseeage",
				fdResultDetailMseeage);
	}

	public void setFdResultDetailMseeage(String fdResultDetailMseeage) {
		this.fdResultDetailMseeage = (String) writeLazyField(
				"fdResultDetailMseeage", this.fdResultDetailMseeage,
				fdResultDetailMseeage);
	}

	/**
	 * @return fdBasicWage
	 */
	public Double getFdBasicWage() {
		return fdBasicWage;
	}

	/**
	 * @param fdBasicWage
	 *            要设置的 fdBasicWage
	 */
	public void setFdBasicWage(Double fdBasicWage) {
		this.fdBasicWage = fdBasicWage;
	}

	/**
	 * @return fdPositionSalary
	 */
	public Double getFdPositionSalary() {
		return fdPositionSalary;
	}

	/**
	 * @param fdPositionSalary
	 *            要设置的 fdPositionSalary
	 */
	public void setFdPositionSalary(Double fdPositionSalary) {
		this.fdPositionSalary = fdPositionSalary;
	}

	/**
	 * @return fdTransAllowance
	 */
	public Double getFdTransAllowance() {
		return fdTransAllowance;
	}

	/**
	 * @param fdTransAllowance
	 *            要设置的 fdTransAllowance
	 */
	public void setFdTransAllowance(Double fdTransAllowance) {
		this.fdTransAllowance = fdTransAllowance;
	}

	/**
	 * @return fdHousingFund
	 */
	public Double getFdHousingFund() {
		return fdHousingFund;
	}

	/**
	 * @param fdHousingFund
	 *            要设置的 fdHousingFund
	 */
	public void setFdHousingFund(Double fdHousingFund) {
		this.fdHousingFund = fdHousingFund;
	}

	/**
	 * @return fdSocialInsuBasicMed
	 */
	public Double getFdSocialInsuBasicMed() {
		return fdSocialInsuBasicMed;
	}

	/**
	 * @param fdSocialInsuBasicMed
	 *            要设置的 fdSocialInsuBasicMed
	 */
	public void setFdSocialInsuBasicMed(Double fdSocialInsuBasicMed) {
		this.fdSocialInsuBasicMed = fdSocialInsuBasicMed;
	}

	/**
	 * @return fdRemark
	 */
	public String getFdRemark() {
		return fdRemark;
	}

	/**
	 * @param fdRemark
	 *            要设置的 fdRemark
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	public SysOrgPerson getFdCreator() {
		return fdCreator;
	}

	public void setFdCreator(SysOrgPerson fdCreator) {
		this.fdCreator = fdCreator;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}


	/*
	 * 提醒方式
	 */
	protected String fdNotifyType;
	
	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
			toFormPropertyMap.put("fdCreator.fdName", "fdCreatorName");
		}
		return toFormPropertyMap;
	}
	
	@Override
	public Class getFormClass() {
		return HrStaffPayrollIssuanceForm.class;
	}
	// ===== 附件机制（开始） =====
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	public String getFdSendEmail() {
		return fdSendEmail;
	}

	public void setFdSendEmail(String fdSendEmail) {
		this.fdSendEmail = fdSendEmail;
	}
	
}
