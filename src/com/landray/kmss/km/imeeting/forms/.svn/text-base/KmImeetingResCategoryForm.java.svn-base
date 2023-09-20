package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议室分类 Form
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingResCategoryForm extends SysSimpleCategoryAuthTmpForm {

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

	/**
	 * 上级分类的ID
	 */
	protected String fdParentId = null;

	/**
	 * @return 上级分类的ID
	 */
	@Override
    public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            上级分类的ID
	 */
	@Override
    public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * 上级分类的名称
	 */
	protected String fdParentName = null;

	/**
	 * @return 上级分类的名称
	 */
	@Override
    public String getFdParentName() {
		return fdParentName;
	}

	/**
	 * @param fdParentName
	 *            上级分类的名称
	 */
	@Override
    public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdParentId = null;
		fdParentName = null;
		defReaderIds = null;
		defReaderNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingResCategory.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", KmImeetingResCategory.class));
			toModelPropertyMap.put("defReaderIds",
					new FormConvertor_IDsToModelList("defReaders",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	/*
	 * 默认可访问者
	 */
	protected String defReaderIds = null;

	protected String defReaderNames = null;

	public String getDefReaderIds() {
		return defReaderIds;
	}

	public void setDefReaderIds(String defReaderIds) {
		this.defReaderIds = defReaderIds;
	}

	public String getDefReaderNames() {
		return defReaderNames;
	}

	public void setDefReaderNames(String defReaderNames) {
		this.defReaderNames = defReaderNames;
	}
}
