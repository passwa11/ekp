package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSignPatch;
import com.landray.kmss.sys.attend.model.SysAttendSignPatchDetail;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-10-18
 */
public class SysAttendSignPatchDetailForm extends ExtendForm {

	private String fdPatchId;

	private String fdSignPersonId;

	private String fdSignPersonName;

	private String fdSignTime;

	public String getFdPatchId() {
		return fdPatchId;
	}

	public void setFdPatchId(String fdPatchId) {
		this.fdPatchId = fdPatchId;
	}

	public String getFdSignPersonId() {
		return fdSignPersonId;
	}

	public void setFdSignPersonId(String fdSignPersonId) {
		this.fdSignPersonId = fdSignPersonId;
	}

	public String getFdSignPersonName() {
		return fdSignPersonName;
	}

	public void setFdSignPersonName(String fdSignPersonName) {
		this.fdSignPersonName = fdSignPersonName;
	}

	public String getFdSignTime() {
		return fdSignTime;
	}

	public void setFdSignTime(String fdSignTime) {
		this.fdSignTime = fdSignTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPatchId = null;
		fdSignPersonId = null;
		fdSignPersonName = null;
		fdSignTime = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSignPersonId",
					new FormConvertor_IDToModel("fdSignPerson",
							SysOrgPerson.class));
			toModelPropertyMap.put("fdPatchId", new FormConvertor_IDToModel(
					"fdPatch", SysAttendSignPatch.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysAttendSignPatchDetail.class;
	}

}
