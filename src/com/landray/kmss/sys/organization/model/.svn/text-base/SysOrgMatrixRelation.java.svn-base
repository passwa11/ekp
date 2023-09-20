package com.landray.kmss.sys.organization.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixRelationForm;
import com.landray.kmss.sys.organization.util.SysOrgMatrixUtil;

/**
 * 矩阵关系表
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixRelation extends BaseModel
		implements Comparable<SysOrgMatrixRelation> {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * 所属矩阵
	 */
	private SysOrgMatrix fdMatrix;

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

	/**
	 * 条件值
	 */
	private String fdConditionalId;
	private Object fdConditionalValue;

	/**
	 * 条件值（多值，如：区间类型）
	 */
	private Object fdConditionalValue2;

	/**
	 * 结果值
	 */
	private List<SysOrgElement> fdResultValues;

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public SysOrgMatrix getFdMatrix() {
		return fdMatrix;
	}

	public void setFdMatrix(SysOrgMatrix fdMatrix) {
		this.fdMatrix = fdMatrix;
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

	public Object getFdConditionalValue() {
		return fdConditionalValue;
	}

	public void setFdConditionalValue(Object fdConditionalValue) {
		this.fdConditionalValue = fdConditionalValue;
	}

	public Object getFdConditionalValue2() {
		return fdConditionalValue2;
	}

	public void setFdConditionalValue2(Object fdConditionalValue2) {
		this.fdConditionalValue2 = fdConditionalValue2;
	}

	public List<SysOrgElement> getFdResultValues() {
		return fdResultValues;
	}

	public void setFdResultValues(List<SysOrgElement> fdResultValues) {
		this.fdResultValues = fdResultValues;
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

	/**
	 * 获取结果ID
	 * 
	 * @return
	 */
	public String getFdResultValueIds() {
		StringBuilder sb = new StringBuilder();
		if (fdResultValues != null && !fdResultValues.isEmpty()) {
			for (SysOrgElement elem : fdResultValues) {
				sb.append(";").append(elem.getFdId());
			}
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 获取结果名称
	 * 
	 * @return
	 */
	public String[] getFdResultValues(boolean needId) {
		StringBuilder names = new StringBuilder();
		StringBuilder ids = new StringBuilder();
		if (fdResultValues != null && !fdResultValues.isEmpty()) {
			for (SysOrgElement elem : fdResultValues) {
				names.append(";").append(SysOrgMatrixUtil.getElementName(elem));
				if (needId) {
					ids.append(";").append(elem.getFdId());
				}
			}
			names.deleteCharAt(0);
			if (needId) {
				ids.deleteCharAt(0);
			}
		}
		return new String[] { names.toString(), ids.toString() };
	}

	public String getFdResultValueNames() {
		return getFdResultValues(false)[0];
	}

	@Override
	public Class<?> getFormClass() {
		return SysOrgMatrixRelationForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdMatrix.fdId", "fdMatrixId");
			toFormPropertyMap.put("fdMatrix.fdName", "fdMatrixName");
			toFormPropertyMap.put("fdResultValues",
					new ModelConvertor_ModelListToString(
							"fdResultValueIds:fdResultValueNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	public String getFdNameOri() {
		return fdName;
	}

	@Override
	public int compareTo(SysOrgMatrixRelation o) {
		int i1 = this.getFdOrder() == null ? 999 : this.getFdOrder();
		int i2 = o.getFdOrder() == null ? 999 : o.getFdOrder();
		return i1 - i2;
	}

	public boolean isRange() {
		return this.fdType != null ? this.fdType.contains("Range") : false;
	}

}
