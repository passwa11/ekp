package com.landray.kmss.km.comminfo.model;

import com.landray.kmss.km.comminfo.forms.KmComminfoAltInfoForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

/**
 * 创建日期 2010-五月-10
 * 
 * @author 徐乃瑞 资料修改信息
 */
public class KmComminfoAltInfo extends SysSimpleCategoryAuthTmpModel {

	/*
	 * 修改时间
	 */
	// protected Date docAlterTime;
	/*
	 * 修改人
	 */
	// protected SysOrgPerson docAlteror = null;
	/*
	 * 常用资料主表
	 */
	protected KmComminfoMain comminfoMain = null;

	public KmComminfoAltInfo() {
		super();
	}

	@Override
    public Class getFormClass() {
		return KmComminfoAltInfoForm.class;
	}

	/**
	 * @return 返回 修改时间
	 */
	// public Date getDocAlterTime() {
	// return docAlterTime;
	// }
	/**
	 * @param docAlterTime
	 *            要设置的 修改时间
	 */
	// public void setDocAlterTime(Date docAlterTime) {
	// this.docAlterTime = docAlterTime;
	// }
	/**
	 * @return 返回 常用资料主表
	 */
	public KmComminfoMain getComminfoMain() {
		return comminfoMain;
	}

	/**
	 * @param comminfoMain
	 *            要设置的 常用资料主表
	 */
	public void setComminfoMain(KmComminfoMain comminfoMain) {
		this.comminfoMain = comminfoMain;
	}

	/**
	 * @return 返回 修改人
	 */
	// public SysOrgPerson getDocAlteror() {
	// return docAlteror;
	// }
	/**
	 * @param docAlteror
	 *            要设置的 修改人
	 */
	// public void setDocAlteror(SysOrgPerson docAlteror) {
	// this.docAlteror = docAlteror;
	// }
}
