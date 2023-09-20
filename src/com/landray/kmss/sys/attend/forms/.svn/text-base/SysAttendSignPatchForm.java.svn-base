package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSignPatch;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-10-18
 */
public class SysAttendSignPatchForm extends ExtendForm {

	private String fdCateName;

	private String fdPatchPersonId;

	private String fdPatchPersonName;

	private String fdPatchTime;

	AutoArrayList fdPatchDetail = new AutoArrayList(
			SysAttendSignPatchDetailForm.class);

	public String getFdCateName() {
		return fdCateName;
	}

	public void setFdCateName(String fdCateName) {
		this.fdCateName = fdCateName;
	}

	public String getFdPatchPersonId() {
		return fdPatchPersonId;
	}

	public void setFdPatchPersonId(String fdPatchPersonId) {
		this.fdPatchPersonId = fdPatchPersonId;
	}

	public String getFdPatchPersonName() {
		return fdPatchPersonName;
	}

	public void setFdPatchPersonName(String fdPatchPersonName) {
		this.fdPatchPersonName = fdPatchPersonName;
	}

	public String getFdPatchTime() {
		return fdPatchTime;
	}

	public void setFdPatchTime(String fdPatchTime) {
		this.fdPatchTime = fdPatchTime;
	}

	public AutoArrayList getFdPatchDetail() {
		return fdPatchDetail;
	}

	public void setFdPatchDetail(AutoArrayList fdPatchDetail) {
		this.fdPatchDetail = fdPatchDetail;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdCateName = null;
		fdPatchPersonId = null;
		fdPatchPersonName = null;
		fdPatchTime = null;
		fdPatchDetail = new AutoArrayList(SysAttendSignPatchDetailForm.class);
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPatchPersonId",
					new FormConvertor_IDToModel("fdPatchPerson",
							SysOrgPerson.class));
			toModelPropertyMap.put("fdPatchDetail",
					new FormConvertor_FormListToModelList("fdPatchDetail",
							"fdPatch"));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysAttendSignPatch.class;
	}

}
