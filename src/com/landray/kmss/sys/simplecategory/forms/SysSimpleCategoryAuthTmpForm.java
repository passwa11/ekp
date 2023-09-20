package com.landray.kmss.sys.simplecategory.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

/**
 * 简单分类默认FORM实现
 * 
 * @author wubin
 * 
 */
public class SysSimpleCategoryAuthTmpForm extends ExtendAuthTmpForm implements
		ISysSimpleCategoryForm {

	@Override
    public Class getModelClass() {
		return SysSimpleCategoryAuthTmpModel.class;
	}

	/*
	 * 类别名称
	 */
	private String fdName = null;
	
	/*
	 * 类别描述
	 */
	private String fdDesc = null;

	/*
	 * 父类别ID
	 */
	private String fdParentId = null;

	/*
	 * 父类别名称
	 */
	private String fdParentName = null;

	/*
	 * 类别排序号
	 */
	private String fdOrder = null;

	/*
	 * 修改人ID
	 */
	private String docAlterorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 修改时间
	 */
	private String docAlterTime = null;

	/*
	 * 创建者ID
	 */
	private String docCreatorName = null;

	/*
	 * 是否继承父类别可维护者
	 */
	private String fdIsinheritMaintainer = null;

	/*
	 * 是否继承父类别可使用者
	 */
	private String fdIsinheritUser = null;

	/**
	 * @return 返回 类别名称
	 */
	@Override
    public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 类别名称
	 */
	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * @return 返回 类别描述
	 */
	@Override
    public String getFdDesc() {
		return fdDesc;
	}

	/**
	 * @param fdMark
	 *            要设置的 类别描述
	 */
	@Override
    public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/**
	 * @return 返回 父类别ID
	 */
	@Override
    public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            要设置的 父类别ID
	 */
	@Override
    public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * @return 返回 类别排序号
	 */
	@Override
    public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 类别排序号
	 */
	@Override
    public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * @return 返回 修改人ID
	 */
	@Override
    public String getDocAlterorName() {
		return docAlterorName;
	}

	/**
	 * @param docAlterorId
	 *            要设置的 修改人ID
	 */
	@Override
    public void setDocAlterorName(String docAlterorId) {
		this.docAlterorName = docAlterorId;
	}

	/**
	 * @return 返回 创建时间
	 */
	@Override
    public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	@Override
    public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 修改时间
	 */
	@Override
    public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 修改时间
	 */
	@Override
    public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 创建者ID
	 */
	@Override
    public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 创建者ID
	 */
	@Override
    public void setDocCreatorName(String docCreatorId) {
		this.docCreatorName = docCreatorId;
	}

	/**
	 * @return 返回 是否继承父类别可维护者
	 */
	@Override
    public String getFdIsinheritMaintainer() {
		return fdIsinheritMaintainer;
	}

	/**
	 * @param fdIsinheritMaintainer
	 *            要设置的 是否继承父类别可维护者
	 */
	@Override
    public void setFdIsinheritMaintainer(String fdIsinheritMaintainer) {
		this.fdIsinheritMaintainer = fdIsinheritMaintainer;
	}

	/**
	 * @return 返回 是否继承父类别可使用者
	 */
	@Override
    public String getFdIsinheritUser() {
		return fdIsinheritUser;
	}

	/**
	 * @param fdIsinheritUser
	 *            要设置的 是否继承父类别可使用者
	 */
	@Override
    public void setFdIsinheritUser(String fdIsinheritUser) {
		this.fdIsinheritUser = fdIsinheritUser;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdParentId = null;
		fdParentName = null;
		fdOrder = null;
		docAlterorName = null;
		docCreateTime = null;
		docAlterTime = null;
		docCreatorName = null;
		fdIsinheritMaintainer = null;
		fdIsinheritUser = null;
		authNotReaderFlag = "false";
		fdDesc = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	@Override
    public String getFdParentName() {
		return fdParentName;
	}

	@Override
    public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	/*
	 * 所有人不可阅读标记
	 */
	protected String authNotReaderFlag = "false";

	@Override
    public String getAuthNotReaderFlag() {
		return authNotReaderFlag;
	}
	
	@Override
    public void setAuthNotReaderFlag(String authNotReaderFlag) {
		this.authNotReaderFlag = authNotReaderFlag;
	}
	
}
