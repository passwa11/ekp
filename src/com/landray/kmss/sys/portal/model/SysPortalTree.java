package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalTreeForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 多级数菜单
 * 
 * @author 
 * @version 1.0 2013-09-23
 */
public class SysPortalTree extends BaseModel implements ISysAuthAreaModel,
		InterceptFieldEnabled {

	/**
	 * 名称
	 */
	protected String fdName;
	
	/**
	 * @return 名称
	 */
	public String getFdNameOri() {
		return fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}
	
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}
	/**
	 * 内容
	 */
	protected String fdContent;
	
	/**
	 * @return 内容
	 */
	public String getFdContent() {
		return (String) readLazyField("fdContent", fdContent);
	}
	
	/**
	 * @param fdContent 内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = (String) writeLazyField("fdContent",
				this.fdContent, fdContent);
	}

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;
	
	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}
	
	/**
	 * @param docAlterTime 最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	/**
	 * 创建人
	 */
	protected SysOrgElement docCreator;
	
	/**
	 * @return 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}
	
	/**
	 * @param docCreator 创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}
	
	/**
	 * 修改者
	 */
	protected SysOrgElement docAlteror;
	
	/**
	 * @return 修改者
	 */
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}
	
	/**
	 * @param docAlteror 修改者
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}
	
	/**
	 * 类型
	 */
	protected String fdType;
	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
		
	/*
	 * 可维护人员
	 */
	private List fdEditors = new ArrayList();
	public List getFdEditors() {
		return fdEditors;
	}

	public void setFdEditors(List fdEditors) {
		this.fdEditors = fdEditors;
	}
	@Override
    public Class getFormClass() {
		return SysPortalTreeForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
			toFormPropertyMap.put("fdEditors",
					new ModelConvertor_ModelListToString(
							"fdEditorIds:fdEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
    public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
    public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	
	// 匿名（0普通 1匿名）@author 吴进 by 20191125
	private Boolean fdAnonymous = Boolean.FALSE;

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
}
