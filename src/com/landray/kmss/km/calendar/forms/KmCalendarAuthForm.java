package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDToModel;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDsToModelList;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2013.11.12
 * 日程共享人员权限设置form
 * @author 孟磊 
 */
@SuppressWarnings("serial")
public class KmCalendarAuthForm extends ExtendForm {

	/*
	 * 创建人Id
	 */
	private String docCreateId = null;
	
	public String getDocCreateId() {
		return docCreateId;
	}

	public void setDocCreateId(String docCreateId) {
		this.docCreateId = docCreateId;
	}
	
	/*
	 * 创建人名称
	 */
	private String docCreateName = null;
	
	public String getDocCreateName() {
		return docCreateName;
	}

	public void setDocCreateName(String docCreateName) {
		this.docCreateName = docCreateName;
	}
	
	/*
	 * 创建时间
	 */
	private String docCreateTime = null;
	
	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 可阅读者
	 */
	protected String authReaderIds = null;

	public String getAuthReaderIds() {
		return authReaderIds;
	}

	public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}

	/*
	 * 可阅读者名称
	 */
	protected String authReaderNames = null;

	public String getAuthReaderNames() {
		return authReaderNames;
	}

	public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}

	/*
	 * 可编辑者
	 */
	protected String authEditorIds = null;

	public String getAuthEditorIds() {
		return authEditorIds;
	}

	public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	/*
	 * 可编辑者名称
	 */
	protected String authEditorNames = null;
	
	public String getAuthEditorNames() {
		return authEditorNames;
	}

	public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	/*
	 * 可维护者Id
	 */
	protected String authModifierIds = null;
	
	public String getAuthModifierIds() {
		return authModifierIds;
	}

	public void setAuthModifierIds(String authModifierIds) {
		this.authModifierIds = authModifierIds;
	}
	
	/*
	 * 可维护者名称
	 */
	protected String authModifierNames = null;
	
	public String getAuthModifierNames() {
		return authModifierNames;
	}

	public void setAuthModifierNames(String authModifierNames) {
		this.authModifierNames = authModifierNames;
	}

	private AutoArrayList kmCalendarAuthList_Form = new AutoArrayList(
			KmCalendarAuthListForm.class);

	public AutoArrayList getKmCalendarAuthList_Form() {
		return kmCalendarAuthList_Form;
	}

	public void
			setKmCalendarAuthList_Form(AutoArrayList kmCalendarAuthList_Form) {
		this.kmCalendarAuthList_Form = kmCalendarAuthList_Form;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.authReaderIds = null;
		this.authReaderNames = null;
		this.authEditorIds = null;
		this.authEditorNames = null;
		this.docCreateId = null;
		this.docCreateTime = null;
		this.authModifierNames = null;
		this.authModifierIds = null;
		kmCalendarAuthList_Form = new AutoArrayList(
				KmCalendarAuthListForm.class);
		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarAuth.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreateId",
					new KmCalendar_FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("authReaderIds",
					new KmCalendar_FormConvertor_IDsToModelList("authReaders",
							SysOrgElement.class));
			toModelPropertyMap.put("authEditorIds",
					new KmCalendar_FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
			toModelPropertyMap.put("authModifierIds",
					new KmCalendar_FormConvertor_IDsToModelList(
							"authModifiers", SysOrgElement.class));
			toModelPropertyMap.put("kmCalendarAuthList_Form",
					new FormConvertor_FormListToModelList("kmCalendarAuthList",
							"fdAuth"));
		}
		return toModelPropertyMap;
	}

}
