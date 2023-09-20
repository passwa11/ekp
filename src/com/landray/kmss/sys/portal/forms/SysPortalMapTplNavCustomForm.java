package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMapTplNavCustom;
import com.landray.kmss.web.action.ActionMapping;

public class SysPortalMapTplNavCustomForm extends ExtendForm {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签名
	 */
	private String fdName = null;

	/**
	 * 排序号
	 */
	private String fdOrder = null;

	/**
	 * 附件主键
	 */
	private String fdAttachmentId = null;

	/**
	 * 内容
	 */
	protected String fdContent = null;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdAttachmentId() {
		return fdAttachmentId;
	}

	public void setFdAttachmentId(String fdAttachmentId) {
		this.fdAttachmentId = fdAttachmentId;
	}

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdContent = null;
		fdOrder = null;
		fdAttachmentId = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
		}
		return toModelPropertyMap;
	}

	@Override
	public Class<SysPortalMapTplNavCustom> getModelClass() {
		return SysPortalMapTplNavCustom.class;
	}

}
