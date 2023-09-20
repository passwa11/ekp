package com.landray.kmss.third.pda.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.pda.forms.PdaTabViewLabelListForm;

public class PdaTabViewLabelList extends BaseModel {

	/**
	 * 功能区名称
	 */
	public String fdTabName;

	/**
	 * 排序号
	 */
	public Integer fdTabOrder;

	/**
	 * 状态
	 */
	public String fdStatus="1";

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
	 * 功能区id
	 */
	public PdaTabViewConfigMain fdPdaTabViewConfigMain;

	/**
	 * 某标签模块Id
	 */
	public PdaModuleConfigMain fdTabModule;

	public String getFdTabName() {
		return fdTabName;
	}

	public void setFdTabName(String fdTabName) {
		this.fdTabName = fdTabName;
	}

	public PdaModuleConfigMain getFdTabModule() {
		return fdTabModule;
	}

	public void setFdTabModule(PdaModuleConfigMain fdTabModule) {
		this.fdTabModule = fdTabModule;
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

	public String getFdTabTemplateClass() {
		return fdTabTemplateClass;
	}

	public void setFdTabTemplateClass(String fdTabTemplateClass) {
		this.fdTabTemplateClass = fdTabTemplateClass;
	}

	public String getFdTabModelName() {
		return fdTabModelName;
	}

	public void setFdTabModelName(String fdTabModelName) {
		this.fdTabModelName = fdTabModelName;
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

	@Override
    public Class getFormClass() {
		return PdaTabViewLabelListForm.class;
	}

	public PdaTabViewConfigMain getFdPdaTabViewConfigMain() {
		return fdPdaTabViewConfigMain;
	}

	public void setFdPdaTabViewConfigMain(
			PdaTabViewConfigMain fdPdaTabViewConfigMain) {
		this.fdPdaTabViewConfigMain = fdPdaTabViewConfigMain;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPdaTabViewConfigMain.fdId",
					"fdPdaTabViewConfigMainId");
			toFormPropertyMap.put("fdPdaTabViewConfigMain.fdName",
					"fdPdaTabViewConfigMainName");
			toFormPropertyMap.put("fdTabModule.fdId", "fdTabModuleId");
			toFormPropertyMap.put("fdTabModule.fdName", "fdTabModuleName");
		}
		return toFormPropertyMap;
	}
}
