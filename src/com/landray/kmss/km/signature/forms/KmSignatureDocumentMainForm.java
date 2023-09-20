package com.landray.kmss.km.signature.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.signature.model.KmSignatureDocumentMain;

/**
 * 文档库表 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentMainForm extends ExtendForm {

	/**
	 * 自动编号
	 */
	protected String fdDocumentId = null;

	/**
	 * @return 自动编号
	 */
	public String getFdDocumentId() {
		return fdDocumentId;
	}

	/**
	 * @param documentid
	 *            自动编号
	 */
	public void setFdDocumentId(String fdDocumentId) {
		this.fdDocumentId = fdDocumentId;
	}

	/**
	 * 文档编号
	 */
	protected String fdRecordId = null;

	/**
	 * @return 文档编号
	 */
	public String getFdRecordId() {
		return fdRecordId;
	}

	/**
	 * @param recordid
	 *            文档编号
	 */
	public void setFdRecordId(String fdRecordId) {
		this.fdRecordId = fdRecordId;
	}

	/**
	 * 公文编号
	 */
	protected String fdDocNo = null;

	/**
	 * @return 公文编号
	 */
	public String getFdDocNo() {
		return fdDocNo;
	}

	/**
	 * @param docno
	 *            公文编号
	 */
	public void setFdDocNo(String fdDocNo) {
		this.fdDocNo = fdDocNo;
	}

	/**
	 * 用户名称
	 */
	protected String fdUserName = null;

	/**
	 * @return 用户名称
	 */
	public String getFdUserName() {
		return fdUserName;
	}

	/**
	 * @param username
	 *            用户名称
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 密级
	 */
	protected String fdSecurity = null;

	/**
	 * @return 密级
	 */
	public String getFdSecurity() {
		return fdSecurity;
	}

	/**
	 * @param security
	 *            密级
	 */
	public void setFdSecurity(String fdSecurity) {
		this.fdSecurity = fdSecurity;
	}

	/**
	 * 起草人
	 */
	protected String fdDraft = null;

	/**
	 * @return 起草人
	 */
	public String getFdDraft() {
		return fdDraft;
	}

	/**
	 * @param draft
	 *            起草人
	 */
	public void setFdDraft(String fdDraft) {
		this.fdDraft = fdDraft;
	}

	/**
	 * 审核人
	 */
	protected String fdAuditor = null;

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
	protected String fdTitle = null;

	/**
	 * @return 标题
	 */
	public String getFdTitle() {
		return fdTitle;
	}

	/**
	 * @param title
	 *            标题
	 */
	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	/**
	 * 抄送
	 */
	protected String fdCopyTo = null;

	/**
	 * @return 抄送
	 */
	public String getFdCopyTo() {
		return fdCopyTo;
	}

	/**
	 * @param copyto
	 *            抄送
	 */
	public void setFdCopyTo(String fdCopyTo) {
		this.fdCopyTo = fdCopyTo;
	}

	/**
	 * 主题
	 */
	protected String docSubject = null;

	/**
	 * @return 主题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param subject
	 *            主题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 打印份数
	 */
	protected String fdCopies = null;

	/**
	 * @return 打印份数
	 */
	public String getFdCopies() {
		return fdCopies;
	}

	/**
	 * @param copies
	 *            打印份数
	 */
	public void setFdCopies(String fdCopies) {
		this.fdCopies = fdCopies;
	}

	/**
	 * 日期时间
	 */
	protected String fdDateTime = null;

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDocumentId = null;
		fdRecordId = null;
		fdDocNo = null;
		fdUserName = null;
		fdSecurity = null;
		fdDraft = null;
		fdAuditor = null;
		fdTitle = null;
		fdCopyTo = null;
		docSubject = null;
		fdCopies = null;
		fdDateTime = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSignatureDocumentMain.class;
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
}
