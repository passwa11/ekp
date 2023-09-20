package com.landray.kmss.sys.attachment.borrow.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 附件传阅
 */
public class SysAttBorrowForm extends ExtendAuthForm
		implements ISysLbpmMainForm, IAttachmentForm {

	private static final long serialVersionUID = 1L;

	private static FormToModelPropertyMap toModelPropertyMap;

	private String docCreateTime;

	private String fdBorrowEffectiveTime;

	private String fdBorrowExpireTime;

	private String fdDuration;

	private String fdReason;

	private String fdReadEnable;

	private String fdDownloadEnable;

	private String fdCopyEnable;

	private String fdPrintEnable;

	private String fdAttId;

	private String docSubject;

	private String docCreatorId;

	private String docCreatorName;

	private String fdBorrowerIds;

	private String fdBorrowerNames;

	private String fdStatus;

	private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

	private AutoHashMap attachmentForms =
			new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		fdBorrowEffectiveTime = null;
		fdDuration = null;
		fdReason = null;
		fdReadEnable = null;
		fdDownloadEnable = null;
		fdCopyEnable = null;
		fdPrintEnable = null;
		fdAttId = null;
		docSubject = null;
		docCreatorId = null;
		docCreatorName = null;
		fdBorrowerIds = null;
		fdBorrowerNames = null;
		fdStatus = null;
		fdBorrowExpireTime = null;
		sysWfBusinessForm = new LbpmProcessForm();
		attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttBorrow> getModelClass() {
		return SysAttBorrow.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.put("fdBorrowEffectiveTime",
					new FormConvertor_Common("fdBorrowEffectiveTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.addNoConvertProperty("docStatus");
			toModelPropertyMap.put("fdBorrowerIds",
					new FormConvertor_IDsToModelList("fdBorrowers",
							SysOrgElement.class));
			toModelPropertyMap.put("authReaderIds",
					new FormConvertor_IDsToModelList("authReaders",
							SysOrgElement.class));
			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 失效时间
	 * 
	 * @return
	 */
	public String getFdBorrowExpireTime() {
		return fdBorrowExpireTime;
	}

	/**
	 * 失效时间
	 * 
	 * @param fdBorrowExpireTime
	 */
	public void setFdBorrowExpireTime(String fdBorrowExpireTime) {
		this.fdBorrowExpireTime = fdBorrowExpireTime;
	}

	/**
	 * 是否有效期内
	 * 
	 * @return
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	/**
	 * 是否有效期内
	 * 
	 * @param fdStatus
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 借阅生效时间
	 */
	public String getFdBorrowEffectiveTime() {
		return this.fdBorrowEffectiveTime;
	}

	/**
	 * 借阅生效时间
	 */
	public void setFdBorrowEffectiveTime(String fdBorrowEffectiveTime) {
		this.fdBorrowEffectiveTime = fdBorrowEffectiveTime;
	}

	/**
	 * 借阅时长
	 */
	public String getFdDuration() {
		return this.fdDuration;
	}

	/**
	 * 借阅时长
	 */
	public void setFdDuration(String fdDuration) {
		this.fdDuration = fdDuration;
	}

	/**
	 * 借阅理由
	 */
	public String getFdReason() {
		return this.fdReason;
	}

	/**
	 * 借阅理由
	 */
	public void setFdReason(String fdReason) {
		this.fdReason = fdReason;
	}

	/**
	 * 阅读权限
	 */
	public String getFdReadEnable() {
		return this.fdReadEnable;
	}

	/**
	 * 阅读权限
	 */
	public void setFdReadEnable(String fdReadEnable) {
		this.fdReadEnable = fdReadEnable;
	}

	/**
	 * 下载权限
	 */
	public String getFdDownloadEnable() {
		return this.fdDownloadEnable;
	}

	/**
	 * 下载权限
	 */
	public void setFdDownloadEnable(String fdDownloadEnable) {
		this.fdDownloadEnable = fdDownloadEnable;
	}

	/**
	 * 拷贝权限
	 */
	public String getFdCopyEnable() {
		return this.fdCopyEnable;
	}

	/**
	 * 拷贝权限
	 */
	public void setFdCopyEnable(String fdCopyEnable) {
		this.fdCopyEnable = fdCopyEnable;
	}

	/**
	 * 打印权限
	 */
	public String getFdPrintEnable() {
		return this.fdPrintEnable;
	}

	/**
	 * 打印权限
	 */
	public void setFdPrintEnable(String fdPrintEnable) {
		this.fdPrintEnable = fdPrintEnable;
	}

	/**
	 * 借阅附件
	 */
	public String getFdAttId() {
		return this.fdAttId;
	}

	/**
	 * 借阅附件
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	/**
	 * 标题
	 */
	public String getDocSubject() {
		return this.docSubject;
	}

	/**
	 * 标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 借阅人员
	 */
	public String getFdBorrowerIds() {
		return this.fdBorrowerIds;
	}

	/**
	 * 借阅人员
	 */
	public void setFdBorrowerIds(String fdBorrowerIds) {
		this.fdBorrowerIds = fdBorrowerIds;
	}

	/**
	 * 借阅人员
	 */
	public String getFdBorrowerNames() {
		return this.fdBorrowerNames;
	}

	/**
	 * 借阅人员
	 */
	public void setFdBorrowerNames(String fdBorrowerNames) {
		this.fdBorrowerNames = fdBorrowerNames;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	@Override
    public LbpmProcessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}
}
