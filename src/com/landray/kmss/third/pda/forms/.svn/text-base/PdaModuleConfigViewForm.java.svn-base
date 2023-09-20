package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleConfigView;
import com.landray.kmss.util.AutoArrayList;

/**
 * 展示页面配置信息 Form
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigViewForm extends ExtendForm {

	/**
	 * 文档类名
	 */
	protected String fdName = null;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 关键字
	 */
	protected String fdKeyword = null;

	/**
	 * @return 关键字
	 */
	public String getFdKeyword() {
		return fdKeyword;
	}

	/**
	 * @param fdKeyword
	 *            关键字
	 */
	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 模型名
	 */
	protected String fdModelName = null;

	/**
	 * @return 模型名
	 */
	public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            模型名
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 模块ID的ID
	 */
	protected String fdModuleId = null;

	/**
	 * @return 模块ID的ID
	 */
	public String getFdModuleId() {
		return fdModuleId;
	}

	/**
	 * @param fdModuleId
	 *            模块ID的ID
	 */
	public void setFdModuleId(String fdModuleId) {
		this.fdModuleId = fdModuleId;
	}

	/**
	 * 模块ID的名称
	 */
	protected String fdModuleName = null;

	/**
	 * @return 模块ID的名称
	 */
	public String getFdModuleName() {
		return fdModuleName;
	}

	/**
	 * @param fdModuleName
	 *            模块ID的名称
	 */
	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}

	/**
	 * 阅读模式
	 */
	protected String fdReadingModel;

	public String getFdReadingModel() {
		return fdReadingModel;
	}

	public void setFdReadingModel(String fdReadingModel) {
		this.fdReadingModel = fdReadingModel;
	}

	/**
	 * 新闻阅读模式配置信息
	 */
	protected String fdNewsModelCfgInfo;

	public String getFdNewsModelCfgInfo() {
		return fdNewsModelCfgInfo;
	}

	public void setFdNewsModelCfgInfo(String fdNewsModelCfgInfo) {
		this.fdNewsModelCfgInfo = fdNewsModelCfgInfo;
	}

	/*
	 * 列表页签配置信息列表
	 */
	private AutoArrayList fdItems = new AutoArrayList(
			PdaModuleLabelViewForm.class);

	public AutoArrayList getFdItems() {
		return fdItems;
	}

	public void setFdItems(AutoArrayList fdItems) {
		this.fdItems = fdItems;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdKeyword = null;
		fdOrder = null;
		fdModelName = null;
		fdModuleId = null;
		fdModuleName = null;
		fdName = null;
		fdReadingModel = null;
		fdNewsModelCfgInfo = null;
		fdItems = new AutoArrayList(PdaModuleLabelViewForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaModuleConfigView.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdModuleId", new FormConvertor_IDToModel(
					"fdModule", PdaModuleConfigMain.class));
			toModelPropertyMap.put("fdItems",
					new FormConvertor_FormListToModelList("fdItems",
							"pdaModuleLabelView"));
		}
		return toModelPropertyMap;
	}
}
