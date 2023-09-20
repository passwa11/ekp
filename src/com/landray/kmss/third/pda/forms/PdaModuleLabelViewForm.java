package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigView;
import com.landray.kmss.third.pda.model.PdaModuleLabelView;

/**
 * 展示页面标签信息 Form
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelViewForm extends ExtendForm {

	/**
	 * 页签名称
	 */
	protected String fdName = null;

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
	 * 配置信息
	 */
	protected String fdCfgInfo = null;

	/**
	 * @return 配置信息
	 */
	public String getFdCfgInfo() {
		return fdCfgInfo;
	}

	/**
	 * @param fdCfgInfo
	 *            配置信息
	 */
	public void setFdCfgInfo(String fdCfgInfo) {
		this.fdCfgInfo = fdCfgInfo;
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
	 * 展示页面配置信息的ID
	 */
	protected String fdCfgViewId = null;

	/**
	 * @return 展示页面配置信息的ID
	 */
	public String getFdCfgViewId() {
		return fdCfgViewId;
	}

	/**
	 * @param fdCfgViewId
	 *            展示页面配置信息的ID
	 */
	public void setFdCfgViewId(String fdCfgViewId) {
		this.fdCfgViewId = fdCfgViewId;
	}

	/**
	 * 展示页面配置信息的名称
	 */
	protected String fdCfgViewName = null;

	/**
	 * @return 展示页面配置信息的名称
	 */
	public String getFdCfgViewName() {
		return fdCfgViewName;
	}

	/**
	 * @param fdCfgViewName
	 *            展示页面配置信息的名称
	 */
	public void setFdCfgViewName(String fdCfgViewName) {
		this.fdCfgViewName = fdCfgViewName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdCfgInfo = null;
		fdCfgViewId = null;
		fdCfgViewName = null;
		fdExtendUrl= null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaModuleLabelView.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCfgViewId", new FormConvertor_IDToModel(
					"fdCfgView", PdaModuleConfigView.class));
		}
		return toModelPropertyMap;
	}
}
