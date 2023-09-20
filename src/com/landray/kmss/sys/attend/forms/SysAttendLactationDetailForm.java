package com.landray.kmss.sys.attend.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 哺乳产前 Form
 * 
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendLactationDetailForm extends ExtendForm {

	private Date docCreateTime;

	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private SysOrgPerson docCreator;

	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	private Integer fdType;

	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	private Date fdStartTime;

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	private Date fdDate;

	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}

	private Date fdEndTime;

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		fdStartTime = null;
		fdEndTime = null;
		fdDate = null;
		fdType = null;
		docCreator = null;
		super.reset(mapping, request);
	}

	@Override
	public Class<SysAttendLactationDetailForm> getModelClass() {
		return SysAttendLactationDetailForm.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

}
