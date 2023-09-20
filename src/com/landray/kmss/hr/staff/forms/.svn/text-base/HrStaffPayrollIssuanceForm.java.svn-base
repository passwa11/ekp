package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;


public class HrStaffPayrollIssuanceForm extends ExtendForm implements
		IAttachmentForm {
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
	private String fdBasicWage;
	// 岗位工资
	private String fdPositionSalary;
	// 交通补贴
	private String fdTransAllowance;
	// 公积金
	private String fdHousingFund;
	// 社保医保
	private String fdSocialInsuBasicMed;
	// 备注
	private String fdRemark;
	// 创建者
	private String fdCreatorId;
	private String fdCreatorName;
	// 创建时间
	private String fdCreateTime;
	// 上传的文件
	private FormFile file;
	
	private String fdPassword;
	
	public String getFdMessageName() {
		return fdMessageName;
	}

	public void setFdMessageName(String messageName) {
		this.fdMessageName = messageName;
	}

	public String getFdResultMseeage() {
		return fdResultMseeage;
	}

	public void setFdResultMseeage(String resultMseeage) {
		this.fdResultMseeage = resultMseeage;
	}


	public String getFdResultDetailMseeage() {
		return fdResultDetailMseeage;
	}

	public void setFdResultDetailMseeage(String fdResultDetailMseeage) {
		this.fdResultDetailMseeage = fdResultDetailMseeage;
	}

	/**
	 * @return fdBasicWage
	 */
	public String getFdBasicWage() {
		return fdBasicWage;
	}

	/**
	 * @param fdBasicWage
	 *            要设置的 fdBasicWage
	 */
	public void setFdBasicWage(String fdBasicWage) {
		this.fdBasicWage = fdBasicWage;
	}

	/**
	 * @return fdPositionSalary
	 */
	public String getFdPositionSalary() {
		return fdPositionSalary;
	}

	/**
	 * @param fdPositionSalary
	 *            要设置的 fdPositionSalary
	 */
	public void setFdPositionSalary(String fdPositionSalary) {
		this.fdPositionSalary = fdPositionSalary;
	}

	/**
	 * @return fdTransAllowance
	 */
	public String getFdTransAllowance() {
		return fdTransAllowance;
	}

	/**
	 * @param fdTransAllowance
	 *            要设置的 fdTransAllowance
	 */
	public void setFdTransAllowance(String fdTransAllowance) {
		this.fdTransAllowance = fdTransAllowance;
	}

	/**
	 * @return fdHousingFund
	 */
	public String getFdHousingFund() {
		return fdHousingFund;
	}

	/**
	 * @param fdHousingFund
	 *            要设置的 fdHousingFund
	 */
	public void setFdHousingFund(String fdHousingFund) {
		this.fdHousingFund = fdHousingFund;
	}

	/**
	 * @return fdSocialInsuBasicMed
	 */
	public String getFdSocialInsuBasicMed() {
		return fdSocialInsuBasicMed;
	}

	/**
	 * @param fdSocialInsuBasicMed
	 *            要设置的 fdSocialInsuBasicMed
	 */
	public void setFdSocialInsuBasicMed(String fdSocialInsuBasicMed) {
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

	public void setAttachmentForms(AutoHashMap attachmentForms) {
		this.attachmentForms = attachmentForms;
	}

	/*
	 * 提醒方式
	 */
	private String fdNotifyType = null;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	
	public String getFdCreatorId() {
		return fdCreatorId;
	}

	
	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	
	public String getFdCreatorName() {
		return fdCreatorName;
	}

	
	public void setFdCreatorName(String fdCreatorName) {
		this.fdCreatorName = fdCreatorName;
	}

	
	public String getFdCreateTime() {
		return fdCreateTime;
	}

	
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	@Override
	public Class getModelClass() {
		return HrStaffPayrollIssuance.class;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdMessageName = null;
		this.fdResultMseeage = null;
		this.fdResultDetailMseeage = null;
		this.fdBasicWage = null;
		this.fdPositionSalary = null;
		this.fdTransAllowance = null;
		this.fdHousingFund = null;
		this.fdSocialInsuBasicMed = null;
		this.fdRemark = null;
		this.fdNotifyType = null;
		this.fdCreateTime = null;
		this.fdCreatorId = null;
		this.fdCreatorName = null;
		this.fdPassword = null;		
		this.file = null;
		this.fdSendEmail = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCreatorId", new FormConvertor_IDToModel(
					"fdCreator", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	/*
	 * 附件机制
	 */
	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	public String getFdPassword() {
		return fdPassword;
	}

	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}

	public String getFdSendEmail() {
		return fdSendEmail;
	}

	public void setFdSendEmail(String fdSendEmail) {
		this.fdSendEmail = fdSendEmail;
	}
	
}
