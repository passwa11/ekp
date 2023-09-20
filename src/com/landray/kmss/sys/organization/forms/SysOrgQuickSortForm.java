package com.landray.kmss.sys.organization.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseForm;
import com.landray.kmss.util.AutoArrayList;

public class SysOrgQuickSortForm extends BaseForm {

	/**
	 * 需排序的组织架构列表
	 */
	protected List<SysOrgQuickSortElement> elements = new AutoArrayList(
			SysOrgQuickSortElement.class);

	public List<SysOrgQuickSortElement> getElements() {
		return elements;
	}

	public void setElements(List<SysOrgQuickSortElement> elements) {
		this.elements = elements;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		elements = new AutoArrayList(SysOrgQuickSortElement.class);
	}
}
