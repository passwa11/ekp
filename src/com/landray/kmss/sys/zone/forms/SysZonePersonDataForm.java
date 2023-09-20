package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.zone.model.SysZonePersonData;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;

/**
 * 个人资料 Form
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataForm extends ExtendForm {
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
	 * 排序号
	 */
	protected String fdOrder;

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
	 * 所属个人资料目录的ID
	 */
	protected String fdDataCateId = null;

	/**
	 * @return 所属个人资料目录的ID
	 */
	public String getFdDataCateId() {
		return fdDataCateId;
	}

	/**
	 * @param fdDataCateId
	 *            所属个人资料目录的ID
	 */
	public void setFdDataCateId(String fdDataCateId) {
		this.fdDataCateId = fdDataCateId;
	}

	/**
	 * 所属个人资料目录的名称
	 */
	protected String fdDataCateName = null;

	/**
	 * @return 所属个人资料目录的名称
	 */
	public String getFdDataCateName() {
		return fdDataCateName;
	}

	/**
	 * @param fdDataCateName
	 *            所属个人资料目录的名称
	 */
	public void setFdDataCateName(String fdDataCateName) {
		this.fdDataCateName = fdDataCateName;
	}

	/**
	 * 所属员工的id
	 */
	protected String fdPersonId;

	/**
	 * 所属员工的id
	 * 
	 * @return
	 */
	public String getFdPersonId() {
		return fdPersonId;
	}

	/**
	 * 所属员工的id
	 * 
	 * @param fdPersonId
	 */
	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	// 机制开始

	// 机制结束
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docContent = null;
		fdDataCateId = null;
		fdDataCateName = null;
		fdName = null;
		fdPersonId = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePersonData.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdDataCateId", new FormConvertor_IDToModel(
					"fdDataCate", SysZonePersonDataCate.class));
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel(
					"fdPerson", SysZonePersonInfo.class));
		}
		return toModelPropertyMap;
	}
}
