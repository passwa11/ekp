package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 矩阵条件
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixRelationForm extends ExtendForm
		implements Comparable<SysOrgMatrixRelationForm> {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * 所属矩阵
	 */
	private String fdMatrixId;
	private String fdMatrixName;

	/**
	 * 字段名称（单列）
	 */
	private String fdFieldName;

	/**
	 * 字段名称（多列，如：区间类型）
	 */
	private String fdFieldName2;

	/**
	 * 条件(结果)类型
	 */
	private String fdType;

	/**
	 * 主数据(条件)类型
	 */
	private String fdMainDataType;
	private String fdMainDataText;

	/**
	 * 是否结果
	 */
	private Boolean fdIsResult = Boolean.FALSE;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 是否主键
	 */
	private Boolean fdIsPrimary = Boolean.FALSE;

	/**
	 * 条件值
	 */
	private String fdConditionalId;
	private String fdConditionalValue;

	/**
	 * 条件值（多值，如：区间类型）
	 */
	private String fdConditionalValue2;

	/**
	 * 结果值
	 */
	private String fdResultValueIds;
	private String fdResultValueNames;

	/**
	 * 数据总量（已经有数据的字段，不能删除）
	 */
	private Integer fdValueCount;

	/**
	 * 是否包含子级部门
	 */
	private Boolean fdIncludeSubDept = Boolean.FALSE;
	
	/**
	 * 是否唯一性校验
	 */
	private Boolean fdIsUnique = Boolean.FALSE;

	/**
	 * 列宽（矩阵数据页面可以调整某一列的宽度）
	 */
	private String fdWidth;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdMatrixId() {
		return fdMatrixId;
	}

	public void setFdMatrixId(String fdMatrixId) {
		this.fdMatrixId = fdMatrixId;
	}

	public String getFdMatrixName() {
		return fdMatrixName;
	}

	public void setFdMatrixName(String fdMatrixName) {
		this.fdMatrixName = fdMatrixName;
	}

	public String getFdFieldName() {
		return fdFieldName;
	}

	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	public String getFdFieldName2() {
		return fdFieldName2;
	}

	public void setFdFieldName2(String fdFieldName2) {
		this.fdFieldName2 = fdFieldName2;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdMainDataType() {
		return fdMainDataType;
	}

	public void setFdMainDataType(String fdMainDataType) {
		this.fdMainDataType = fdMainDataType;
	}

	public String getFdMainDataText() {
		return fdMainDataText;
	}

	public void setFdMainDataText(String fdMainDataText) {
		this.fdMainDataText = fdMainDataText;
	}

	public Boolean getFdIsResult() {
		return fdIsResult;
	}

	public void setFdIsResult(Boolean fdIsResult) {
		this.fdIsResult = fdIsResult;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public Boolean getFdIsPrimary() {
		return fdIsPrimary;
	}

	public void setFdIsPrimary(Boolean fdIsPrimary) {
		this.fdIsPrimary = fdIsPrimary;
	}

	public String getFdConditionalId() {
		return fdConditionalId;
	}

	public void setFdConditionalId(String fdConditionalId) {
		this.fdConditionalId = fdConditionalId;
	}

	public String getFdConditionalValue() {
		return fdConditionalValue;
	}

	public void setFdConditionalValue(String fdConditionalValue) {
		this.fdConditionalValue = fdConditionalValue;
	}

	public String getFdConditionalValue2() {
		return fdConditionalValue2;
	}

	public void setFdConditionalValue2(String fdConditionalValue2) {
		this.fdConditionalValue2 = fdConditionalValue2;
	}

	public String getFdResultValueIds() {
		return fdResultValueIds;
	}

	public void setFdResultValueIds(String fdResultValueIds) {
		this.fdResultValueIds = fdResultValueIds;
	}

	public String getFdResultValueNames() {
		return fdResultValueNames;
	}

	public void setFdResultValueNames(String fdResultValueNames) {
		this.fdResultValueNames = fdResultValueNames;
	}

	public Integer getFdValueCount() {
		return fdValueCount;
	}

	public void setFdValueCount(Integer fdValueCount) {
		this.fdValueCount = fdValueCount;
	}

	public Boolean getFdIncludeSubDept() {
		return fdIncludeSubDept;
	}

	public void setFdIncludeSubDept(Boolean fdIncludeSubDept) {
		this.fdIncludeSubDept = fdIncludeSubDept;
	}

	public Boolean getFdIsUnique() {
		return fdIsUnique;
	}

	public void setFdIsUnique(Boolean fdIsUnique) {
		this.fdIsUnique = fdIsUnique;
	}

	public String getFdWidth() {
		return fdWidth;
	}

	public void setFdWidth(String fdWidth) {
		this.fdWidth = fdWidth;
	}

	@Override
	public Class<?> getModelClass() {
		return SysOrgMatrixRelation.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdMatrixId = null;
		fdMatrixName = null;
		fdFieldName = null;
		fdFieldName2 = null;
		fdType = null;
		fdMainDataType = null;
		fdMainDataText = null;
		fdIsResult = Boolean.FALSE;
		fdOrder = null;
		fdIsPrimary = Boolean.FALSE;
		fdConditionalId = null;
		fdConditionalValue = null;
		fdConditionalValue2 = null;
		fdResultValueIds = null;
		fdResultValueNames = null;
		fdValueCount = null;
		fdIncludeSubDept = Boolean.FALSE;
		fdWidth = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdMatrixId",
					new FormConvertor_IDToModel("fdMatrix",
							SysOrgMatrix.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public int compareTo(SysOrgMatrixRelationForm o) {
		int i1 = this.getFdOrder() == null ? 999 : this.getFdOrder();
		int i2 = o.getFdOrder() == null ? 999 : o.getFdOrder();
		return i1 - i2;
	}

}
