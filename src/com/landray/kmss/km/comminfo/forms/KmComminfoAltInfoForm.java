package com.landray.kmss.km.comminfo.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.sys.doc.forms.SysDocBaseInfoForm;

/**
 * 创建日期 2010-五月-10
 * 
 * @author 徐乃瑞
 */
public class KmComminfoAltInfoForm extends SysDocBaseInfoForm {

	/*
	 * 常用资料主表
	 */
	protected String comminfoMain = null;

	/*
	 * 修改人
	 */
	protected String docAlteror = null;

	@Override
    public Class getModelClass() {
		return KmComminfoMain.class;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.comminfoMain = null;
		this.docAlteror = null;
		this.docAlterTime = null;
		super.reset(mapping, request);
	}

	public String getComminfoMain() {
		return comminfoMain;
	}

	public void setComminfoMain(String comminfoMain) {
		this.comminfoMain = comminfoMain;
	}

	public String getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(String docAlteror) {
		this.docAlteror = docAlteror;
	}

}
