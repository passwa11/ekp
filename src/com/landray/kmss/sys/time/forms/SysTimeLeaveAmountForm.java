package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-12
 */
public class SysTimeLeaveAmountForm extends SysTimeImportForm
		implements ISysAuthAreaForm {

	/**
	 * 人员
	 */
	private String fdPersonId;
	private String fdPersonName;

	/**
	 * 年份
	 */
	private String fdYear;

	/**
	 * 额度明细
	 */
	private AutoArrayList fdAmountItems = new AutoArrayList(
			SysTimeLeaveAmountItemForm.class);

	private String docCreateTime;

	private String docCreatorId;

	private String docAlterorId;
	private String fdOperatorId;
	private String fdOperatorName;

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

	public AutoArrayList getFdAmountItems() {
		return fdAmountItems;
	}

	public void setFdAmountItems(AutoArrayList fdAmountItems) {
		this.fdAmountItems = fdAmountItems;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getFdOperatorId() {
		return fdOperatorId;
	}

	public void setFdOperatorId(String fdOperatorId) {
		this.fdOperatorId = fdOperatorId;
	}

	public String getFdOperatorName() {
		return fdOperatorName;
	}

	public void setFdOperatorName(String fdOperatorName) {
		this.fdOperatorName = fdOperatorName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonId = null;
		fdPersonName = null;
		fdYear = null;
		fdAmountItems = new AutoArrayList(SysTimeLeaveAmountItemForm.class);
		docCreateTime = null;
		docCreatorId = null;
		docAlterorId = null;
		authAreaId = null;
		authAreaName = null;
		fdOperatorId = null;
		fdOperatorName = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel(
					"fdPerson", SysOrgPerson.class));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgPerson.class));
			toModelPropertyMap.put("fdAmountItems",
					new FormConvertor_FormListToModelList("fdAmountItems",
							"fdAmount"));
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
			toModelPropertyMap.put("fdOperatorId", new FormConvertor_IDToModel(
					"fdOperator", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysTimeLeaveAmount.class;
	}

	// 所属场所ID
	protected String authAreaId = null;

	@Override
    public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
    public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	@Override
    public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
    public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}
}
