package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.zone.model.SysZonePerDataTempl;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;

/**
 * 个人资料目录模版设置 Form
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePerDataTemplForm extends ExtendForm {

	/**
	 * 目录名
	 */
	protected String fdName = null;

	/**
	 * @return 目录名
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            目录名
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 内容
	 */
	protected String docContent = null;

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
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
	 * 所属个人资料目录id
	 */
	protected String fdPersonDataCateId;

	/**
	 * 所属个人资料目录id
	 * 
	 * @return
	 */
	public String getFdPersonDataCateId() {
		return fdPersonDataCateId;
	}

	/**
	 * 所属个人资料目录id
	 * 
	 * @param fdPersonDataCateId
	 */
	public void setFdPersonDataCateId(String fdPersonDataCateId) {
		this.fdPersonDataCateId = fdPersonDataCateId;
	}

	/**
	 * 所属个人资料目录名称
	 */
	protected String fdPersonDataCateName;

	/**
	 * 所属个人资料目录名称
	 * 
	 * @return
	 */
	public String getFdPersonDataCateName() {
		return fdPersonDataCateName;
	}

	/**
	 * 所属个人资料目录名称
	 * 
	 * @param fdPersonDataCateName
	 */
	public void setFdPersonDataCateName(String fdPersonDataCateName) {
		this.fdPersonDataCateName = fdPersonDataCateName;
	}

	// 机制开始

	// 机制结束
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docContent = null;
		fdOrder = null;
		fdPersonDataCateId = null;
		fdPersonDataCateName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePerDataTempl.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonDataCateId",
					new FormConvertor_IDToModel("fdPersonDataCate",
							SysZonePersonDataCate.class));
		}
		return toModelPropertyMap;
	}
}
