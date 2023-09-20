package com.landray.kmss.third.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.pda.forms.PdaModuleConfigViewForm;

/**
 * 展示页面配置信息
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigView extends BaseModel implements
		InterceptFieldEnabled {

	/**
	 * 文档类名
	 */
	protected String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 关键字
	 */
	protected String fdKeyword;

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
	 * 模型名
	 */
	protected String fdModelName;

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
	 * 模块ID
	 */
	protected PdaModuleConfigMain fdModule;

	/**
	 * @return 模块ID
	 */
	public PdaModuleConfigMain getFdModule() {
		return fdModule;
	}

	/**
	 * @param fdModule
	 *            模块ID
	 */
	public void setFdModule(PdaModuleConfigMain fdModule) {
		this.fdModule = fdModule;
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
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
	 * 文档阅读模式配置信息
	 */
	protected String fdNewsModelCfgInfo;

	public String getFdNewsModelCfgInfo() {
		return (String) readLazyField("fdNewsModelCfgInfo", fdNewsModelCfgInfo);
	}

	public void setFdNewsModelCfgInfo(String fdNewsModelCfgInfo) {
		this.fdNewsModelCfgInfo = (String) writeLazyField("fdNewsModelCfgInfo",
				this.fdNewsModelCfgInfo, fdNewsModelCfgInfo);
	}

	/*
	 * 列表页签配置信息列表
	 */
	protected List<PdaModuleLabelView> fdItems = new ArrayList<PdaModuleLabelView>();

	public List<PdaModuleLabelView> getFdItems() {
		return fdItems;
	}

	public void setFdItems(List<PdaModuleLabelView> fdItems) {
		this.fdItems = fdItems;
	}

	@Override
    public Class getFormClass() {
		return PdaModuleConfigViewForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdModule.fdId", "fdModuleId");
			toFormPropertyMap.put("fdModule.fdName", "fdModuleName");
			toFormPropertyMap.put("fdItems",
					new ModelConvertor_ModelListToFormList("fdItems"));
		}
		return toFormPropertyMap;
	}
}
