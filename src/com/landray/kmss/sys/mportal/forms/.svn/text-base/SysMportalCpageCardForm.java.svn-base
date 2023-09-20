package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.model.SysMportalCpageCard;
import com.landray.kmss.web.action.ActionMapping;

public class SysMportalCpageCardForm extends ExtendForm {

	private String fdName;

	private String fdOrder;

	private String sysMportalCpageId;

	private String sysMportalCpageName;

	private String sysMportalCardId;

	private String sysMportalCardName;

	private String fdMargin;

	public String getFdMargin() {
		return fdMargin;
	}

	public void setFdMargin(String fdMargin) {
		this.fdMargin = fdMargin;
	}

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

	public String getSysMportalCpageId() {
		return sysMportalCpageId;
	}

	public void setSysMportalCpageId(String sysMportalCpageId) {
		this.sysMportalCpageId = sysMportalCpageId;
	}

	public String getSysMportalCpageName() {
		return sysMportalCpageName;
	}

	public void setSysMportalCpageName(String sysMportalCpageName) {
		this.sysMportalCpageName = sysMportalCpageName;
	}

	public String getSysMportalCardId() {
		return sysMportalCardId;
	}

	public void setSysMportalCardId(String sysMportalCardId) {
		this.sysMportalCardId = sysMportalCardId;
	}

	public String getSysMportalCardName() {
		return sysMportalCardName;
	}

	public void setSysMportalCardName(String sysMportalCardName) {
		this.sysMportalCardName = sysMportalCardName;
	}

	@Override
	public Class getModelClass() {
		return SysMportalCpageCard.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdMargin = null;
		sysMportalCpageId = null;
		sysMportalCpageName = null;
		sysMportalCardId = null;
		sysMportalCardName = null;
		super.reset(mapping, request);
	}

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysMportalCpageId",
					new FormConvertor_IDToModel("sysMportalCpage",
							SysMportalCpage.class));

			toModelPropertyMap.put("sysMportalCardId",
					new FormConvertor_IDToModel("sysMportalCard",
							SysMportalCard.class));
		}
		return toModelPropertyMap;
	}
}
