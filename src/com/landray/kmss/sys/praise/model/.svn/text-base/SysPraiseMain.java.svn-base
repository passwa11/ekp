package com.landray.kmss.sys.praise.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.praise.forms.SysPraiseMainForm;

public class SysPraiseMain extends BaseCoreInnerModel {

	/**
	 * 赞 标识
	 */
	public static final String SYSPRAISEMAIN_PRAISE = "1";
	/**
	 * 踩 标识
	 */
	public static final String SYSPRAISEMAIN_NEGATIVE = "2";
	/*
	 * 点赞时间
	 */
	protected java.util.Date fdPraiseTime = new Date();
	/*
	 * 点赞人
	 */
	protected SysOrgPerson fdPraisePerson;
	/*
	 * 被点赞文档标题
	 */
	private String docSubject = null;

	/**
	 * 模块ID
	 */
	protected String fdModelId;

	/**
	 * @return 模块ID
	 */
	@Override
    public String getFdModelId() {
		return fdModelId;
	}

	/**
	 * @param fdModelId
	 *            模块ID
	 */
	@Override
    public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/**
	 * 模块名
	 */
	protected String fdModelName;

	/**
	 * @return 模块名
	 */
	@Override
    public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            模块名
	 */
	@Override
    public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public java.util.Date getFdPraiseTime() {
		return fdPraiseTime;
	}

	public void setFdPraiseTime(java.util.Date fdPraiseTime) {
		this.fdPraiseTime = fdPraiseTime;
	}

	public SysOrgPerson getFdPraisePerson() {
		return fdPraisePerson;
	}

	public void setFdPraisePerson(SysOrgPerson fdPraisePerson) {
		this.fdPraisePerson = fdPraisePerson;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 赞/踩 标识
	 */
	public String fdType;
	
	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	@Override
	public Class getFormClass() {
		return SysPraiseMainForm.class;
	}
	/**
	 * 操作ip
	 */
	protected String fdIp;

	/**
	 * @return 操作ip
	 */
	public String getFdIp() {
		return fdIp;
	}

	/**
	 * @param fdIp
	 *            操作ip
	 */
	public void setFdIp(String fdIp) {
		this.fdIp = fdIp;
	}
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPraisePerson.fdId", "fdPraisePersonId");
			toFormPropertyMap
					.put("fdPraisePerson.fdName", "fdPraisePersonName");
		}
		return toFormPropertyMap;
	}
}
