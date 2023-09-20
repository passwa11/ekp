package com.landray.kmss.km.signature.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.signature.forms.KmSignatureDocumentMainForm;

/**
 * 文档库表
 * 
 * @author weiby
 * @version 1.0 2014-12-13
 */
public class KmSignatureDocumentMain extends BaseModel {

	/**
	 * 自动编号
	 */
	protected Integer fdDocumentId = 0;

	/**
	 * @return 自动编号
	 */
	public Integer getFdDocumentId() {
		return fdDocumentId;
	}

	/**
	 * @param fdDocumentId
	 *            自动编号
	 */
	public void setFdDocumentId(Integer fdDocumentId) {
		this.fdDocumentId = fdDocumentId;
	}

	/**
	 * 文档编号
	 */
	protected String fdRecordId;

	/**
	 * @return 文档编号
	 */
	public String getFdRecordId() {
		return fdRecordId;
	}

	/**
	 * @param fdRecordId
	 *            文档编号
	 */
	public void setFdRecordId(String fdRecordId) {
		this.fdRecordId = fdRecordId;
	}

	/**
	 * 公文编号
	 */
	protected String fdDocNo;

	/**
	 * @return 公文编号
	 */
	public String getFdDocNo() {
		return fdDocNo;
	}

	/**
	 * @param fdDocNo
	 *            公文编号
	 */
	public void setFdDocNo(String fdDocNo) {
		this.fdDocNo = fdDocNo;
	}

	/**
	 * 用户名称
	 */
	protected String fdUserName;

	/**
	 * @return 用户名称
	 */
	public String getFdUserName() {
		return fdUserName;
	}

	/**
	 * @param fdUserName
	 *            用户名称
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 密级
	 */
	protected String fdSecurity;

	/**
	 * @return 密级
	 */
	public String getFdSecurity() {
		return fdSecurity;
	}

	/**
	 * @param fdSecurity
	 *            密级
	 */
	public void setFdSecurity(String fdSecurity) {
		this.fdSecurity = fdSecurity;
	}

	/**
	 * 起草人
	 */
	protected String fdDraft;

	/**
	 * @return 起草人
	 */
	public String getFdDraft() {
		return fdDraft;
	}

	/**
	 * @param fdDraft
	 *            起草人
	 */
	public void setFdDraft(String fdDraft) {
		this.fdDraft = fdDraft;
	}

	/**
	 * 审核人
	 */
	protected String fdAuditor;

	/**
	 * @return 审核人
	 */
	public String getFdAuditor() {
		return fdAuditor;
	}

	/**
	 * @param fdAuditor
	 *            审核人
	 */
	public void setFdAuditor(String fdAuditor) {
		this.fdAuditor = fdAuditor;
	}

	/**
	 * 标题
	 */
	protected String fdTitle;

	/**
	 * @return 标题
	 */
	public String getFdTitle() {
		return fdTitle;
	}

	/**
	 * @param fdTitle
	 *            标题
	 */
	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	/**
	 * 抄送
	 */
	protected String fdCopyTo;

	/**
	 * @return 抄送
	 */
	public String getFdCopyTo() {
		return fdCopyTo;
	}

	/**
	 * @param fdCopyTo
	 *            抄送
	 */
	public void setFdCopyTo(String fdCopyTo) {
		this.fdCopyTo = fdCopyTo;
	}

	/**
	 * 主题
	 */
	protected String docSubject;

	/**
	 * @return 主题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            主题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 打印份数
	 */
	protected String fdCopies;

	/**
	 * @return 打印份数
	 */
	public String getFdCopies() {
		return fdCopies;
	}

	/**
	 * @param fdCopies
	 *            打印份数
	 */
	public void setFdCopies(String fdCopies) {
		this.fdCopies = fdCopies;
	}

	/**
	 * 日期时间
	 */
	protected String fdDateTime;

	/**
	 * @return 日期时间
	 */
	public String getFdDateTime() {
		return fdDateTime;
	}

	/**
	 * @param fdDateTime
	 *            日期时间
	 */
	public void setFdDateTime(String fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	@Override
    public Class getFormClass() {
		return KmSignatureDocumentMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
