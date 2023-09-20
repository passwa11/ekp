package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewLabelList;

public class PdaTabViewLabelListForm extends ExtendForm {

	/**
	 * 功能区名称
	 */
	protected String fdTabName;

	/**
	 * 排序号
	 */
	protected Integer fdTabOrder;

	/**
	 * 状态
	 */
	protected String fdStatus;

	/**
	 * 标签图标
	 */
	public String fdTabIcon;

	/**
	 * 标签url
	 */
	public String fdTabUrl;

	/**
	 * 标签类型
	 */
	public String fdTabType;

	/**
	 * 标签请求数据serviceBean
	 */
	public String fdTabBean;

	/**
	 * 标签主modelName
	 */
	public String fdTabModelName;

	/**
	 * 标签分类modelName
	 */
	public String fdTabTemplateClass;

	/**
	 * 某标签模块id
	 */
	public String fdTabModuleId;

	/**
	 * 某标签模块名称
	 */
	public String fdTabModuleName;

	/**
	 * 功能区id
	 */
	public String fdPdaTabViewConfigMainId;

	/**
	 * 功能区名称
	 */
	public String fdPdaTabViewConfigMainName;

	public String getFdTabName() {
		return fdTabName;
	}

	public void setFdTabName(String fdTabName) {
		this.fdTabName = fdTabName;
	}

	public Integer getFdTabOrder() {
		return fdTabOrder;
	}

	public void setFdTabOrder(Integer fdTabOrder) {
		this.fdTabOrder = fdTabOrder;
	}

	public String getFdTabIcon() {
		return fdTabIcon;
	}

	public void setFdTabIcon(String fdTabIcon) {
		this.fdTabIcon = fdTabIcon;
	}

	public String getFdTabUrl() {
		return fdTabUrl;
	}

	public void setFdTabUrl(String fdTabUrl) {
		this.fdTabUrl = fdTabUrl;
	}

	public String getFdTabType() {
		return fdTabType;
	}

	public void setFdTabType(String fdTabType) {
		this.fdTabType = fdTabType;
	}

	public String getFdTabBean() {
		return fdTabBean;
	}

	public void setFdTabBean(String fdTabBean) {
		this.fdTabBean = fdTabBean;
	}

	public String getFdTabModelName() {
		return fdTabModelName;
	}

	public void setFdTabModelName(String fdTabModelName) {
		this.fdTabModelName = fdTabModelName;
	}

	public String getFdTabModuleId() {
		return fdTabModuleId;
	}

	public void setFdTabModuleId(String fdTabModuleId) {
		this.fdTabModuleId = fdTabModuleId;
	}

	public String getFdTabModuleName() {
		return fdTabModuleName;
	}

	public void setFdTabModuleName(String fdTabModuleName) {
		this.fdTabModuleName = fdTabModuleName;
	}

	public String getFdTabTemplateClass() {
		return fdTabTemplateClass;
	}

	public void setFdTabTemplateClass(String fdTabTemplateClass) {
		this.fdTabTemplateClass = fdTabTemplateClass;
	}

	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	/**
	 * @param fdStatus
	 *            状态
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public Class getFormClass() {
		return PdaTabViewLabelListForm.class;
	}

	public String getFdPdaTabViewConfigMainId() {
		return fdPdaTabViewConfigMainId;
	}

	public void setFdPdaTabViewConfigMainId(String fdPdaTabViewConfigMainId) {
		this.fdPdaTabViewConfigMainId = fdPdaTabViewConfigMainId;
	}

	public String getFdPdaTabViewConfigMainName() {
		return fdPdaTabViewConfigMainName;
	}

	public void setFdPdaTabViewConfigMainName(String fdPdaTabViewConfigMainName) {
		this.fdPdaTabViewConfigMainName = fdPdaTabViewConfigMainName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTabName = null;
		fdTabOrder = 0;
		fdStatus = "1";
		fdTabIcon = null;
		fdTabUrl = null;
		fdTabType = null;
		fdTabBean = null;
		fdTabModelName = null;
		fdTabTemplateClass = null;
		fdPdaTabViewConfigMainId = null;
		fdPdaTabViewConfigMainName = null;
		fdTabModuleId = null;
		fdTabModuleName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaTabViewLabelList.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPdaTabViewConfigMainId",
					new FormConvertor_IDToModel("fdPdaTabViewConfigMain",
							PdaTabViewConfigMain.class));
			toModelPropertyMap.put("fdTabModuleId",
					new FormConvertor_IDToModel("fdTabModule",
							PdaModuleConfigMain.class));
		}
		return toModelPropertyMap;
	}
}
