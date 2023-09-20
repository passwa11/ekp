package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalMenuForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 快捷方式
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenu extends BaseModel {

	/**
	 * 标题
	 */
	protected String docSubject;

	/**
	 * @return 标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
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
	 * @param docCreateTime
	 *            创建时间
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
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}


	/**
	 * 菜单配置项
	 */
	protected List<SysMportalMenuItem> fdSysMportalMenuItems;

	/**
	 * @return 菜单配置项
	 */
	public List<SysMportalMenuItem> getFdSysMportalMenuItems() {
		return fdSysMportalMenuItems;
	}

	/**
	 *            菜单配置项
	 */
	public void setFdSysMportalMenuItems(
			List<SysMportalMenuItem> fdSysMportalMenuItems) {
		this.fdSysMportalMenuItems = fdSysMportalMenuItems;
	}

	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
	
	
	private List fdEditors = new ArrayList();

	public List getFdEditors() {
		return fdEditors;
	}

	public void setFdEditors(List fdEditors) {
		this.fdEditors = fdEditors;
	}
	
	@Override
    public Class getFormClass() {
		return SysMportalMenuForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdSysMportalMenuItems",
					new ModelConvertor_ModelListToFormList(
							"fdSysMportalMenuItemForms"));
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdEditors",
					new ModelConvertor_ModelListToString(
							"fdEditorIds:fdEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
	
	@Override
	public void recalculateFields() {
		super.recalculateFields();
		int order = 0;
		for (SysMportalMenuItem item : this.getFdSysMportalMenuItems()) {
			order ++;
			item.setFdOrder(order);
		}
	}
}
