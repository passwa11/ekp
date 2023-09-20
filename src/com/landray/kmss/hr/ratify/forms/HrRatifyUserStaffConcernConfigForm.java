package com.landray.kmss.hr.ratify.forms;

import java.io.Serializable;
import java.util.List;

import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionForm;

public class HrRatifyUserStaffConcernConfigForm extends ActionForm
		implements Serializable {

	private List hrRatifyStaffConcernConfigFormList = new AutoArrayList(
			HrRatifyStaffConcernConfigForm.class);

	public List getHrRatifyStaffConcernConfigFormList() {
		return hrRatifyStaffConcernConfigFormList;
	}

	public void setHrRatifyStaffConcernConfigFormList(
			List hrRatifyStaffConcernConfigFormList) {
		this.hrRatifyStaffConcernConfigFormList = hrRatifyStaffConcernConfigFormList;
	}

	private String deleteIds = null;

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

}
