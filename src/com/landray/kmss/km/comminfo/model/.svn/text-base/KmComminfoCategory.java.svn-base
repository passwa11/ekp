package com.landray.kmss.km.comminfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.comminfo.forms.KmComminfoCategoryForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.ArrayUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料类别
 */
public class KmComminfoCategory extends SysSimpleCategoryAuthTmpModel {

	/*
	 * 类别名称
	 */
	protected String fdName;

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * 创建者
	 */
	protected SysOrgPerson docCreator = null;
	/*
	 * 类别排序号
	 */
	protected java.lang.Integer fdOrder;

	/**
	 * @return 返回 类别排序号
	 */
	@Override
	public java.lang.Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 类别排序号
	 */
	@Override
	public void setFdOrder(java.lang.Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 所有人可阅读标记
	 */
	protected Boolean authReaderFlag;

	@Override
	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthAllEditors())) {
            return new Boolean(true);
        } else {
            return new Boolean(false);
        }
	}

	@Override
	public void setAuthReaderFlag(Boolean authReaderFlag) {
	}

	/*
	 * 所有可编辑者
	 */
	protected List authAllEditors = new ArrayList();

	@Override
	public List getAuthAllEditors() {
		return authAllEditors;
	}

	@Override
	public void setAuthAllEditors(List authAllEditors) {
		this.authAllEditors = authAllEditors;
	}

	/*
	 * 所有可阅读者
	 */
	protected List authAllReaders = new ArrayList();

	@Override
	public List getAuthAllReaders() {
		return authAllReaders;
	}

	@Override
	public void setAuthAllReaders(List authAllReaders) {
		this.authAllReaders = authAllReaders;
	}

	/*
	 * 可编辑者
	 */
	protected List authEditors = new ArrayList();

	@Override
	public List getAuthEditors() {
		return authEditors;
	}

	@Override
	public void setAuthEditors(List authEditors) {
		this.authEditors = authEditors;
	}

	/**
	 * @return 返回 类别名称
	 */
	@Override
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 类别名称
	 */
	@Override
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Override
	public Class getFormClass() {
		return KmComminfoCategoryForm.class;
	}

	@Override
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	@Override
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	@Override
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	// ================ModelToForm转换开始=========================
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 创建者
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:deptLevelNames"));
		}
		return toFormPropertyMap;
	}

	// ================ModelToForm转换结束============================

}
