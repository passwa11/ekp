package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMapTplNav;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalMapTplNavForm extends ExtendForm {

	private static final long serialVersionUID = 1L;

	private String fdNavId;
	private String fdNavName;

	private String fdAttachmentId;

	public String getFdNavId() {
		return fdNavId;
	}

	public void setFdNavId(String fdNavId) {
		this.fdNavId = fdNavId;
	}

	public String getFdNavName() {
		return fdNavName;
	}

	public void setFdNavName(String fdNavName) {
		this.fdNavName = fdNavName;
	}

	public String getFdAttachmentId() {
		return fdAttachmentId;
	}

	public void setFdAttachmentId(String fdAttachmentId) {
		this.fdAttachmentId = fdAttachmentId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNavId = null;
		fdNavName = null;
		fdAttachmentId = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdNavId", new FormConvertor_IDToModel(
					"fdNav", SysPortalMapTplNav.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class<SysPortalMapTplNav> getModelClass() {
		return SysPortalMapTplNav.class;
	}

}
