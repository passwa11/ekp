package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleLabelList;

/**
 * 列表页签配置信息 Form
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelListForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

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
	 * 数据url
	 */
	protected String fdDataUrl = null;

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

	protected String fdCountUrl = null;
	
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
	 * 新建url
	 */
	protected String fdCreateUrl;

	public String getFdCreateUrl() {
		return fdCreateUrl;
	}

	public void setFdCreateUrl(String fdCreateUrl) {
		this.fdCreateUrl = fdCreateUrl;
	}

	/**
	 * 每页显示行数
	 */
	protected String fdRowsize = null;

	public String getFdRowsize() {
		return fdRowsize;
	}

	public void setFdRowsize(String fdRowsize) {
		this.fdRowsize = fdRowsize;
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

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdDataUrl = null;
		fdRowsize = null;
		fdModuleId = null;
		fdModuleName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaModuleLabelList.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdModuleId", new FormConvertor_IDToModel(
					"fdModule", PdaModuleConfigMain.class));
		}
		return toModelPropertyMap;
	}
}
