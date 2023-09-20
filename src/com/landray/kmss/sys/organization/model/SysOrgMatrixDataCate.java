package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixDataCateForm;

/**
 * 矩阵数据分组
 * 
 * @author panyh
 * @date 2020-05-11
 *
 */
public class SysOrgMatrixDataCate extends BaseModel implements Comparable<SysOrgMatrixDataCate> {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组名称
	 */
	private String fdName;

	/**
	 * 可维护者
	 */
	private SysOrgElement fdElement;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 所属矩阵
	 */
	private SysOrgMatrix fdMatrix;

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

	public SysOrgMatrix getHbmMatrix() {
		return fdMatrix;
	}

	public void setHbmMatrix(SysOrgMatrix fdMatrix) {
		this.fdMatrix = fdMatrix;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
	public Class<?> getFormClass() {
		return SysOrgMatrixDataCateForm.class;
	}

	public SysOrgElement getFdElement() {
		return fdElement;
	}

	public void setFdElement(SysOrgElement fdElement) {
		this.fdElement = fdElement;
	}

	public SysOrgElement getHbmElement() {
		return fdElement;
	}

	public void setHbmElement(SysOrgElement fdElement) {
		this.fdElement = fdElement;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@Override
	public int compareTo(SysOrgMatrixDataCate o) {
		int i1 = this.getFdOrder() == null ? 999 : this.getFdOrder();
		int i2 = o.getFdOrder() == null ? 999 : o.getFdOrder();
		return i1 - i2;
	}

	@Override
	public String getFdId() {
		return fdId;
	}

	@Override
	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdMatrix.fdId", "fdMatrixId");
			toFormPropertyMap.put("fdMatrix.fdName", "fdMatrixName");
			toFormPropertyMap.put("fdElement.fdId", "fdElementId");
			toFormPropertyMap.put("fdElement.fdName", "fdElementName");
		}
		return toFormPropertyMap;
	}

	public String getFdNameOri() {
		return fdName;
	}

}
