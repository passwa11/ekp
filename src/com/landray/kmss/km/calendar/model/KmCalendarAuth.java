package com.landray.kmss.km.calendar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarAuthForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 创建日期 2013.11.12 日程共享人员权限设置model
 * 
 * @author 孟磊
 */
@SuppressWarnings("serial")
public class KmCalendarAuth extends BaseModel implements ISysNotifyModel {

	/*
	 * 创建时间
	 */
	protected Date docCreateTime;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 创建人
	 */
	protected SysOrgPerson docCreator = null;

	public KmCalendarAuth() {
		super();
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	/*
	 * 可阅读者
	 */
	@SuppressWarnings("unchecked")
	protected List authReaders = new ArrayList();

	@SuppressWarnings("unchecked")
	public List getAuthReaders() {
		return authReaders;
	}

	@SuppressWarnings("unchecked")
	public void setAuthReaders(List authReaders) {
		this.authReaders = authReaders;
	}

	/*
	 * 可创建者
	 */
	@SuppressWarnings("unchecked")
	protected List authEditors = new ArrayList();

	@SuppressWarnings("unchecked")
	public List getAuthEditors() {
		return authEditors;
	}

	@SuppressWarnings("unchecked")
	public void setAuthEditors(List authEditors) {
		this.authEditors = authEditors;
	}

	/*
	 * 可维护者
	 */
	@SuppressWarnings("unchecked")
	protected List authModifiers = new ArrayList();

	@SuppressWarnings("unchecked")
	public List getAuthModifiers() {
		return authModifiers;
	}

	@SuppressWarnings("unchecked")
	public void setAuthModifiers(List authModifiers) {
		this.authModifiers = authModifiers;
	}

	private List<KmCalendarAuthList> kmCalendarAuthList;

	public List<KmCalendarAuthList> getKmCalendarAuthList() {
		return kmCalendarAuthList;
	}

	public void
			setKmCalendarAuthList(List<KmCalendarAuthList> kmCalendarAuthList) {
		this.kmCalendarAuthList = kmCalendarAuthList;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdName", "docCreateName");
			toFormPropertyMap.put("docCreator.fdId", "docCreateId");
			toFormPropertyMap.put("authReaders",
					new ModelConvertor_ModelListToString(
							"authReaderIds:authReaderNames", "fdId:deptLevelNames"));
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:deptLevelNames"));
			toFormPropertyMap
					.put("authModifiers", new ModelConvertor_ModelListToString(
							"authModifierIds:authModifierNames", "fdId:deptLevelNames"));
			toFormPropertyMap.put("kmCalendarAuthList",
					new ModelConvertor_ModelListToFormList(
							"kmCalendarAuthList_Form"));
		}
		return toFormPropertyMap;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarAuthForm.class;
	}
}
