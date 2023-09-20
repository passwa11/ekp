package com.landray.kmss.km.signature.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.signature.model.KmSignatureCategory;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;

/**
 * 签章分类 Form
 * 
 * @author
 * @version 1.0 2014-08-10
 */
public class KmSignatureCategoryForm extends SysSimpleCategoryAuthTmpForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

	/**
	 * @return 名称
	 */
	@Override
    public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	@Override
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
	@Override
    public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	@Override
    public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSignatureCategory.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"hbmParent", KmSignatureCategory.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 层级Id
	 */
	protected String fdHierarchyId = null;

	/**
	 * 上级分类的ID
	 */
	protected String fdParentId = null;

	/**
	 * 上级分类的名称
	 */
	protected String fdParentName = null;

	public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	@Override
    public String getFdParentId() {
		return fdParentId;
	}

	@Override
    public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	@Override
    public String getFdParentName() {
		return fdParentName;
	}

	@Override
    public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}
}
