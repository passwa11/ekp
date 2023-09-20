package com.landray.kmss.third.pda.model;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.pda.forms.PdaModuleLabelViewForm;

/**
 * 展示页面标签信息
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelView extends BaseModel implements
		InterceptFieldEnabled {

	/**
	 * 页签名称
	 */
	protected String fdName;

	/**
	 * @return 页签名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            页签名称
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
	 * 配置信息
	 */
	protected String fdCfgInfo;

	/**
	 * @return 配置信息
	 */
	public String getFdCfgInfo() {
		return (String) readLazyField("fdCfgInfo", fdCfgInfo);
	}

	/**
	 * @param fdCfgInfo
	 *            配置信息
	 */
	public void setFdCfgInfo(String fdCfgInfo) {
		this.fdCfgInfo = (String) writeLazyField("fdCfgInfo", this.fdCfgInfo,
				fdCfgInfo);
	}
	
	/**
	 * 嵌入内部URL
	 */
	private String fdExtendUrl;
	
	public String getFdExtendUrl() {
		return fdExtendUrl;
	}

	public void setFdExtendUrl(String fdExtendUrl) {
		this.fdExtendUrl = fdExtendUrl;
	}

	/**
	 * 展示页面配置信息
	 */
	protected PdaModuleConfigView fdCfgView;

	/**
	 * @return 展示页面配置信息
	 */
	public PdaModuleConfigView getFdCfgView() {
		return fdCfgView;
	}

	/**
	 * @param fdCfgView
	 *            展示页面配置信息
	 */
	public void setFdCfgView(PdaModuleConfigView fdCfgView) {
		this.fdCfgView = fdCfgView;
	}

	@Override
    public Class getFormClass() {
		return PdaModuleLabelViewForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCfgView.fdId", "fdCfgViewId");
			toFormPropertyMap.put("fdCfgView.fdKeyword", "fdCfgViewName");
		}
		return toFormPropertyMap;
	}
}
