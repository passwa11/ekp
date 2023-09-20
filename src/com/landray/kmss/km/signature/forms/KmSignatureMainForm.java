package com.landray.kmss.km.signature.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.doc.forms.SysDocBaseInfoForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 印章库 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
@SuppressWarnings("serial")
public class KmSignatureMainForm extends SysDocBaseInfoForm implements
		IAttachmentForm {

	/**
	 * 是否有效
	 */
	private String fdIsAvailable = "true";

	/**
	 * 是否有效
	 * @return
	 */
	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	/**
	 * 是否有效
	 * @param fdIsAvailable
	 */
	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
	
	/**
	 * 自动编号
	 */
	protected String fdSignatureId = null;

	/**
	 * @return 自动编号
	 */
	public String getFdSignatureId() {
		return fdSignatureId;
	}

	/**
	 * @param fdSignatureId
	 *            自动编号
	 */
	public void setFdSignatureId(String fdSignatureId) {
		this.fdSignatureId = fdSignatureId;
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
	 * @param fdUserName
	 *            用户名称
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 用户密码
	 */
	protected String fdPassword = null;

	/**
	 * @return 用户密码
	 */
	public String getFdPassword() {
		return fdPassword;
	}

	/**
	 * @param fdPassword
	 *            用户密码
	 */
	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}

	/**
	 * 印章名称
	 */
	protected String fdMarkName = null;

	/**
	 * @return 印章名称
	 */
	public String getFdMarkName() {
		return fdMarkName;
	}

	/**
	 * @param fdMarkName
	 *            印章名称
	 */
	public void setFdMarkName(String fdMarkName) {
		this.fdMarkName = fdMarkName;
	}

	/**
	 * 印章类型
	 */
	protected String fdMarkType = null;

	/**
	 * @return 印章类型
	 */
	public String getFdMarkType() {
		return fdMarkType;
	}

	/**
	 * @param fdMarkType
	 *            印章类型
	 */
	public void setFdMarkType(String fdMarkType) {
		this.fdMarkType = fdMarkType;
	}

	/**
	 * 印章信息
	 */
	protected String markBody = null;

	/**
	 * @return 印章信息
	 */
	public String getMarkBody() {
		return markBody;
	}

	/**
	 * @param markbody
	 *            印章信息
	 */
	public void setMarkBody(String markBody) {
		this.markBody = markBody;
	}

	/**
	 * 印章目录
	 */
	protected String fdMarkPath = null;

	/**
	 * @return 印章目录
	 */
	public String getFdMarkPath() {
		return fdMarkPath;
	}

	/**
	 * @param fdMarkPath
	 *            印章目录
	 */
	public void setFdMarkPath(String fdMarkPath) {
		this.fdMarkPath = fdMarkPath;
	}

	/**
	 * 印章大小
	 */
	protected String fdMarkSize = null;

	/**
	 * @return 印章大小
	 */
	public String getFdMarkSize() {
		return fdMarkSize;
	}

	/**
	 * @param fdMarkSize
	 *            印章大小
	 */
	public void setFdMarkSize(String fdMarkSize) {
		this.fdMarkSize = fdMarkSize;
	}

	/**
	 * 印章保存时间
	 */
	protected String fdMarkDate = null;

	/**
	 * @return 印章保存时间
	 */
	public String getFdMarkDate() {
		return fdMarkDate;
	}

	/**
	 * @param fdMarkDate
	 *            印章保存时间
	 */
	public void setFdMarkDate(String fdMarkDate) {
		this.fdMarkDate = fdMarkDate;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIsAvailable = "true";
		fdSignatureId = null;
		fdUserName = null;
		fdPassword = null;
		fdMarkName = null;
		fdMarkType = null;
		markBody = null;
		fdMarkPath = null;
		fdMarkSize = null;
		fdMarkDate = null;
		fdDocType = null;
		fdUsersNames = null;
		fdUsersIds = null;
//		fdTempId = null;
//		fdTempName = null;
		docCreatorId = null;
		docCreatorName = UserUtil.getUserName(request);
		docCreateTime = DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
		fdIsDefault = false;
		fdIsFreeSign = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSignatureMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// toModelPropertyMap.put("docCreatorId", new
			// FormConvertor_IDToModel(
			// "docCreator", SysOrgPerson.class));
//			toModelPropertyMap.put("fdTempId", new FormConvertor_IDToModel(
//					"fdTemp", KmSignatureCategory.class));
			toModelPropertyMap.put("fdUsersIds",
					new FormConvertor_IDsToModelList("fdUsers",
							SysOrgElement.class));
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
		}
		return toModelPropertyMap;
	}

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	protected String fdDocType;

	public String getFdDocType() {
		return fdDocType;
	}

	public void setFdDocType(String fdDocType) {
		this.fdDocType = fdDocType;
	}

	/*
	 * 是否有效
	 */
	private String docInForce = null;

	public String getDocInForce() {
		return docInForce;
	}

	public void setDocInForce(String docInForce) {
		this.docInForce = docInForce;
	}

	protected String fdUsersNames = null;

	protected String fdUsersIds = null;

	public String getFdUsersNames() {
		return fdUsersNames;
	}

	public void setFdUsersNames(String fdUsersNames) {
		this.fdUsersNames = fdUsersNames;
	}

	public String getFdUsersIds() {
		return fdUsersIds;
	}

	public void setFdUsersIds(String fdUsersIds) {
		this.fdUsersIds = fdUsersIds;
	}

//	/**
//	 * 签章分类的ID
//	 */
//	protected String fdTempId = null;
//
//	/**
//	 * @return 签章分类的ID
//	 */
//	public String getFdTempId() {
//		return fdTempId;
//	}
//
//	/**
//	 * @param fdTempId
//	 *            签章分类的ID
//	 */
//	public void setFdTempId(String fdTempId) {
//		this.fdTempId = fdTempId;
//	}
//
//	/**
//	 * 签章分类的名称
//	 */
//	protected String fdTempName = null;
//
//	/**
//	 * @return 签章分类的名称
//	 */
//	public String getFdTempName() {
//		return fdTempName;
//	}
//
//	/**
//	 * @param fdTempName
//	 *            签章分类的名称
//	 */
//	public void setFdTempName(String fdTempName) {
//		this.fdTempName = fdTempName;
//	}

	/**
	 * 是否默认
	 */
	private Boolean fdIsDefault = new Boolean(false);
	
	public Boolean getFdIsDefault() {
		return fdIsDefault;
	}

	public void setFdIsDefault(Boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	/**
	 * 是否免密签名
	 */
	private String fdIsFreeSign = null;

	public String getFdIsFreeSign() {
		return fdIsFreeSign;
	}

	public void setFdIsFreeSign(String fdIsFreeSign) {
		this.fdIsFreeSign = fdIsFreeSign;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

}
