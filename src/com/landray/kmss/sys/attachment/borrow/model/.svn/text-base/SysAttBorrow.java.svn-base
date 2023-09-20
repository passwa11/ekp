package com.landray.kmss.sys.attachment.borrow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.borrow.forms.SysAttBorrowForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 附件传阅
 */
public class SysAttBorrow extends ExtendAuthModel implements ISysLbpmMainModel {

	private static final long serialVersionUID = 1L;

	private static ModelToFormPropertyMap toFormPropertyMap;

	private Date fdBorrowEffectiveTime;

	private Date fdBorrowExpireTime;

	private Long fdDuration;

	private String fdReason;

	private Boolean fdReadEnable;

	private Boolean fdDownloadEnable;

	private Boolean fdCopyEnable;

	private Boolean fdPrintEnable;

	private String fdStatus;

	/**
	 * 未生效
	 */
	public final static String STATUS_UNDO = "0";
	/**
	 * 借阅中
	 */
	public final static String STATUS_DOING = "1";
	/**
	 * 已过期
	 */
	public final static String STATUS_DONE = "2";

	/**
	 * 已关闭 <br>
	 * 手动关闭
	 */
	public final static String STATUS_CLOSE = "3";

	private String fdAttId;

	private String docSubject;

	private List<SysOrgElement> fdBorrowers = new ArrayList<SysOrgElement>();

	private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

	private AutoHashMap attachmentForms =
			new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public Class<SysAttBorrowForm> getFormClass() {
		return SysAttBorrowForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreateTime",
					new ModelConvertor_Common("docCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdBorrowEffectiveTime",
					new ModelConvertor_Common("fdBorrowEffectiveTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.addNoConvertProperty("authReaderFlag");
			toFormPropertyMap.addNoConvertProperty("authEditorFlag");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("fdBorrowers",
					new ModelConvertor_ModelListToString(
							"fdBorrowerIds:fdBorrowerNames", "fdId:fdName"));
			toFormPropertyMap.put("authReaders",
					new ModelConvertor_ModelListToString(
							"authReaderIds:authReaderNames", "fdId:fdName"));
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void recalculateFields() {
		super.recalculateFields();
		if (!getAuthReaderFlag()) {
			ArrayUtil.concatTwoList(getFdBorrowers(), authAllReaders);
			ArrayUtil.concatTwoList(getFdBorrowers(), authAllEditors);
			if (getDocCreator() != null
					&& !authAllEditors.contains(getDocCreator())) {
				authAllEditors.add(getDocCreator());
			}
			String status = getDocStatus();
			if (StringUtil.isNotNull(status) && status.charAt(0) >= '3') {
				ArrayUtil.concatTwoList(getFdBorrowers(), authAllReaders);
				ArrayUtil.concatTwoList(getFdBorrowers(), authAllEditors);
				if (getDocCreator() != null
						&& !authAllEditors.contains(getDocCreator())) {
					authAllEditors.add(getDocCreator());
				}
			}
		}
	}

	/**
	 * 过期时间
	 * 
	 * @return
	 */
	public Date getFdBorrowExpireTime() {
		return fdBorrowExpireTime;
	}

	/**
	 * 过期时间
	 * 
	 * @param fdBorrowExpireTime
	 */
	public void setFdBorrowExpireTime(Date fdBorrowExpireTime) {
		this.fdBorrowExpireTime = fdBorrowExpireTime;
	}

	/**
	 * 借阅状态
	 * 
	 * @return
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	/**
	 * 借阅状态
	 * 
	 * @param fdStatus
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 借阅生效时间
	 */
	public Date getFdBorrowEffectiveTime() {
		return this.fdBorrowEffectiveTime;
	}

	/**
	 * 借阅生效时间
	 */
	public void setFdBorrowEffectiveTime(Date fdBorrowEffectiveTime) {
		this.fdBorrowEffectiveTime = fdBorrowEffectiveTime;
	}

	/**
	 * 借阅时长
	 */
	public Long getFdDuration() {
		return this.fdDuration;
	}

	/**
	 * 借阅时长
	 */
	public void setFdDuration(Long fdDuration) {
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
	public Boolean getFdReadEnable() {
		return this.fdReadEnable;
	}

	/**
	 * 阅读权限
	 */
	public void setFdReadEnable(Boolean fdReadEnable) {
		this.fdReadEnable = fdReadEnable;
	}

	/**
	 * 下载权限
	 */
	public Boolean getFdDownloadEnable() {
		return this.fdDownloadEnable;
	}

	/**
	 * 下载权限
	 */
	public void setFdDownloadEnable(Boolean fdDownloadEnable) {
		this.fdDownloadEnable = fdDownloadEnable;
	}

	/**
	 * 拷贝权限
	 */
	public Boolean getFdCopyEnable() {
		return this.fdCopyEnable;
	}

	/**
	 * 拷贝权限
	 */
	public void setFdCopyEnable(Boolean fdCopyEnable) {
		this.fdCopyEnable = fdCopyEnable;
	}

	/**
	 * 打印权限
	 */
	public Boolean getFdPrintEnable() {
		return this.fdPrintEnable;
	}

	/**
	 * 打印权限
	 */
	public void setFdPrintEnable(Boolean fdPrintEnable) {
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
	@Override
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
	 * 借阅人员
	 */
	public List<SysOrgElement> getFdBorrowers() {
		return this.fdBorrowers;
	}

	/**
	 * 借阅人员
	 */
	public void setFdBorrowers(List<SysOrgElement> fdBorrowers) {
		this.fdBorrowers = fdBorrowers;
	}

	/**
	 * 返回 所有人可阅读标记
	 */
	@Override
    public Boolean getAuthReaderFlag() {
		return false;
	}

	@Override
    public LbpmProcessForm getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}
}
