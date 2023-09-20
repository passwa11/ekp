package com.landray.kmss.km.signature.model;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.signature.forms.KmSignatureMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.doc.model.SysDocBaseInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;

/**
 * 印章库
 * 
 * @author weiby
 * @version 1.0 2014-12-13
 */
@SuppressWarnings("serial")
public class KmSignatureMain extends SysDocBaseInfo implements IAttachment,
		InterceptFieldEnabled // 大字段加入延时加载实现接口
{
	
	/**
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	/**
	 * 是否有效
	 * @return
	 */
	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
		return fdIsAvailable;
	}

	/**
	 * 是否有效
	 * @param fdIsAvailable
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 自动编号
	 */
	protected Integer fdSignatureId = 0;

	/**
	 * @return 自动编号
	 */
	public Integer getFdSignatureId() {
		return fdSignatureId;
	}

	/**
	 * @param fdSignatureId
	 *            自动编号
	 */
	public void setFdSignatureId(Integer fdSignatureId) {
		this.fdSignatureId = fdSignatureId;
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
	 * 用户密码
	 */
	protected String fdPassword;

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
	protected String fdMarkName;

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
	protected String fdMarkType;

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
	protected Blob fdMarkBody;

	/**
	 * @return 印章信息
	 */
	public Blob getFdMarkBody() {
		return (Blob) readLazyField("fdMarkBody", fdMarkBody);
	}

	/**
	 * @param fdMarkBody
	 *            印章信息
	 */
	public void setFdMarkBody(Blob fdMarkBody) {
		this.fdMarkBody = (Blob) writeLazyField("fdMarkBody", this.fdMarkBody,
				fdMarkBody);
	}

	/**
	 * 印章目录
	 */
	protected String fdMarkPath;

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
	protected Integer fdMarkSize;

	/**
	 * @return 印章大小
	 */
	public Integer getFdMarkSize() {
		return fdMarkSize;
	}

	/**
	 * @param fdMarkSize
	 *            印章大小
	 */
	public void setFdMarkSize(Integer fdMarkSize) {
		this.fdMarkSize = fdMarkSize;
	}

	/**
	 * 印章保存时间
	 */
	protected Date fdMarkDate;

	/**
	 * @return 印章保存时间
	 */
	public Date getFdMarkDate() {
		return fdMarkDate;
	}

	/**
	 * @param fdMarkDate
	 *            印章保存时间
	 */
	public void setFdMarkDate(Date fdMarkDate) {
		this.fdMarkDate = fdMarkDate;
	}

	@Override
	public Class getFormClass() {
		return KmSignatureMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
//			toFormPropertyMap.put("fdTemp.fdId", "fdTempId");
//			toFormPropertyMap.put("fdTemp.fdName", "fdTempName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdUsers",
					new ModelConvertor_ModelListToString(
							"fdUsersIds:fdUsersNames", "fdId:fdName"));

		}
		return toFormPropertyMap;
	}

	@Override
	public String getDocSubject() {
		return "签名印章";
	}

	protected java.lang.Long fdDocType;

	public java.lang.Long getFdDocType() {
		return fdDocType;
	}

	public void setFdDocType(java.lang.Long fdDocType) {
		this.fdDocType = fdDocType;
	}

	/*
	 * 是否有效
	 */
	private java.lang.Long docInForce = null;

	public java.lang.Long getDocInForce() {
		return docInForce;
	}

	public void setDocInForce(java.lang.Long docInForce) {
		this.docInForce = docInForce;
	}

	protected List<SysOrgElement> fdUsers;

	public List<SysOrgElement> getFdUsers() {
		return fdUsers;
	}

	public void setFdUsers(List<SysOrgElement> fdUsers) {
		this.fdUsers = fdUsers;
	}

//	/**
//	 * 签章分类
//	 */
//	protected KmSignatureCategory fdTemp;
//
//	/**
//	 * @return 签章分类
//	 */
//	public KmSignatureCategory getFdTemp() {
//		return fdTemp;
//	}
//
//	/**
//	 * @param fdTemp
//	 *            签章分类
//	 */
//	public void setFdTemp(KmSignatureCategory fdTemp) {
//		this.fdTemp = fdTemp;
//	}
	/**
	 * 是否默认
	 */
	private Boolean fdIsDefault;
	
	public Boolean getFdIsDefault() {
		if (fdIsDefault == null) {
            fdIsDefault = Boolean.FALSE;
        }
		return fdIsDefault;
	}

	public void setFdIsDefault(Boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	/**
	 * 是否免密签名
	 */
	private Boolean fdIsFreeSign;
	
	public Boolean getFdIsFreeSign() {
		if (fdIsFreeSign == null) {
            fdIsFreeSign = Boolean.FALSE;
        }
		return fdIsFreeSign;
	}

	public void setFdIsFreeSign(Boolean fdIsFreeSign) {
		this.fdIsFreeSign = fdIsFreeSign;
	}

	// 附件机制
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

}
