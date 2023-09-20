package com.landray.kmss.third.pda.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.pda.forms.PdaModuleLabelListForm;

/**
 * 列表页签配置信息
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelList extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
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
	 * 数据url
	 */
	protected String fdDataUrl;

	/**
	 * @return 数据url
	 */
	public String getFdDataUrl() {
		return fdDataUrl;
	}

	/**
	 * @param fdDataUrl
	 *            数据url
	 */
	public void setFdDataUrl(String fdDataUrl) {
		this.fdDataUrl = fdDataUrl;
	}

	/**
	 * 数据条数请求url
	 */
	protected String fdCountUrl;

	
	public String getFdCountUrl() {
		return fdCountUrl;
	}

	public void setFdCountUrl(String fdCountUrl) {
		this.fdCountUrl = fdCountUrl;
	}

	/**
	 * 是否外部链接
	 */
	protected String fdIsLink = null;
	
	public String getFdIsLink() {
		return fdIsLink;
	}

	public void setFdIsLink(String fdIsLink) {
		this.fdIsLink = fdIsLink;
	}
	
	/**
	 * 每页显示行数
	 */
	protected String fdRowsize;

	public String getFdRowsize() {
		return fdRowsize;
	}

	public void setFdRowsize(String fdRowsize) {
		this.fdRowsize = fdRowsize;
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

	@Override
    public Class getFormClass() {
		return PdaModuleLabelListForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdModule.fdId", "fdModuleId");
			toFormPropertyMap.put("fdModule.fdName", "fdModuleName");
		}
		return toFormPropertyMap;
	}
}
